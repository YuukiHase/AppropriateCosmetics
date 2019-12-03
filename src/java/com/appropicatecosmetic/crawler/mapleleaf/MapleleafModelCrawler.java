/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.mapleleaf;

import com.appropicatecosmetic.contants.SkinContaints;
import com.appropicatecosmetic.crawler.BaseCrawler;
import com.appropicatecosmetic.dao.ConcernDAO;
import com.appropicatecosmetic.dao.SkinTypeDAO;
import com.appropicatecosmetic.dto.Model;
import com.appropicatecosmetic.entity.TblConcern;
import com.appropicatecosmetic.entity.TblSkinType;
import com.appropicatecosmetic.utils.ElementChecker;
import com.appropicatecosmetic.utils.EscapseHTMLUtils;
import com.appropicatecosmetic.utils.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author PhuCV
 */
public class MapleleafModelCrawler extends BaseCrawler {

    private String pageUrl;
    private String category;

    public MapleleafModelCrawler(String pageUrl, String category, ServletContext context) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
    }

    public Model getModel() {
        BufferedReader reader = null;
        Model model = null;
        try {
            reader = getBufferedReaderForURL(pageUrl);
            String document = getModelDocument(reader);
            return stAXParserForModel(document);
        } catch (IOException | XMLStreamException ex) {
            ex.printStackTrace();
        }
        return model;
    }

    private Model stAXParserForModel(String document)
            throws XMLStreamException, UnsupportedEncodingException {
        document = TextUtils.refineHtml(document);
        String brand = getBrand(parseStringToXMLEventReader(document)).trim();
        String name = getName(parseStringToXMLEventReader(document), brand).trim();
        int price = getPrice(parseStringToXMLEventReader(document));
        String link = "http:" + getImageLink(parseStringToXMLEventReader(document));
        String detail = getDetail(parseStringToXMLEventReader(document));
        String volume = getVolume(name);

        List<TblConcern> skinConcern = new ArrayList<>();
        List<TblSkinType> skinTypes = new ArrayList<>();
        for (String concern : SkinContaints.LISTSKINCONSERN) {
            if (name.toLowerCase().contains(concern.toLowerCase())
                    || detail.toLowerCase().contains(concern.toLowerCase())) {
                TblConcern concern1 = ConcernDAO.getInstance().getAndInsertIfNewConsern(concern);
                skinConcern.add(concern1);
            }
        }
        for (String skin : SkinContaints.LISTSKINTYPES) {
            if (name.toLowerCase().contains(skin.toLowerCase())
                    || detail.toLowerCase().contains(skin.toLowerCase())) {
                TblSkinType skinType = SkinTypeDAO.getInstance().getAndInsertIfNewSkinType(skin);
                skinTypes.add(skinType);
            }
        }
        if (name.contains(volume)) {
            name = name.replace(volume, "").trim();
        }
        Model model = new Model(brand, name, category, price, link, pageUrl, detail, "Chưa Xác Định", volume, skinTypes, skinConcern);
        return model;
    }

    private String getModelDocument(BufferedReader reader) throws IOException {
        String document = "<model>";
        String line = "";
        boolean isStart = false;

        // Get data
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("class=\"product-single product-inner\"")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("<div class=\"e-newletter\"")) {
                break;
            }
        }
        return EscapseHTMLUtils.encodeHtml(document);
    }

    private String getBrand(XMLEventReader eventReader) {
        XMLEvent event = null;
        String brand = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "span", "class", "product-vendor")) {
                    XMLEvent value = (XMLEvent) eventReader.next();
                    return value.asCharacters().getData();
                }
            }
        }
        return brand;
    }

    private String getName(XMLEventReader eventReader, String brand) {
        XMLEvent event = null;
        String name = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "h1", "class", "product-name")) {
                    XMLEvent value = (XMLEvent) eventReader.next();
                    name = value.asCharacters().getData();
                    if (name.contains(brand)) {
                        name = name.replace(brand, "");
                    }
                    return name;
                }
            }
        }
        return name;
    }

    private int getPrice(XMLEventReader eventReader) {
        XMLEvent event = null;
        String price = "0";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "span", "id", "ProductPrice")) {
                    XMLEvent value = (XMLEvent) eventReader.next();
                    price = value.asCharacters().getData();
                    price = price.replace(",", "");
                    price = price.replace("₫", "");
                    return Integer.parseInt(price);
                }
            }
        }
        return Integer.parseInt(price);
    }

    private String getImageLink(XMLEventReader eventReader) {
        XMLEvent event = null;
        String link = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "img", "id", "ProductPhotoImg")) {
                    link = getHref(startElement);
                    return link;
                }
            }
        }
        return link;
    }

    private String getHref(StartElement element) {
        Attribute href = element.getAttributeByName(new QName("src"));
        return href == null ? "" : href.getValue();
    }

    private String getDetail(XMLEventReader eventReader) {
        XMLEvent event = null;
        String data = "";
        boolean isStart = false;
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "id", "tab-1")) {
                    isStart = true;
                }
            }
            if (event.isCharacters()) {
                if (event.asCharacters().getData().equals("Sản phẩm liên quan")) {
                    break;
                }
            }
            if (isStart && event.isCharacters()) {
                data += " " + event;
            }
        }
        return data;
    }

    private String getVolume(String name) {
        String data = "Chưa Xác Định";
        String[] a = name.split(" ");
        for (int i = 0; i < a.length; i++) {
            if (a[i].contains("ml")) {
                data = a[i];
            }
            if (a[i].contains("lít")) {
                data = a[i - 1] + " lít";
            }
            if (a[i].contains("miếng")) {
                data = a[i - 1] + " miếng";
            }
        }
        return data;
    }
}
