/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.maihan;

import com.appropicatecosmetic.contants.SkinContaints;
import com.appropicatecosmetic.crawler.BaseCrawler;
import com.appropicatecosmetic.dto.Model;
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
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author PhuCV
 */
public class MaihanModelCrawler extends BaseCrawler {
    
    private String pageUrl;
    private String category;
    
    public MaihanModelCrawler(String pageUrl, String category, ServletContext context) {
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
        }
        return model;
    }
    
    private Model stAXParserForModel(String document)
            throws XMLStreamException, UnsupportedEncodingException {
        
        document = TextUtils.refineHtml(document);
        
        String brand = getBrand(parseStringToXMLEventReader(document)).trim();
        String name = getName(parseStringToXMLEventReader(document), brand).trim();
        int price = getPrice(parseStringToXMLEventReader(document));
        String link = getImageLink(parseStringToXMLEventReader(document));
        String detail2 = getDetail2(parseStringToXMLEventReader(document)).trim();
        String detail1 = getDetail1(parseStringToXMLEventReader(document)).trim();
        String orgin = getOrigin(parseStringToXMLEventReader(document)).trim();
        String volume = getVolume(parseStringToXMLEventReader(document)).trim();
        String skins = getSkinType(parseStringToXMLEventReader(document)).trim();
        List<String> skinConcern = new ArrayList<>();
        List<String> skinTypes = new ArrayList<>();
        for (String concern : SkinContaints.LISTSKINCONSERN) {
            if (detail1.toLowerCase().contains(concern.toLowerCase()) 
                    || detail2.toLowerCase().contains(concern.toLowerCase())) {
                skinConcern.add(concern);
            }
        }
        
        for (String skin : SkinContaints.LISTSKINTYPES) {
            if (detail1.toLowerCase().contains(skin.toLowerCase()) 
                    || detail2.toLowerCase().contains(skin.toLowerCase())
                    || skins.toLowerCase().contains(skin.toLowerCase())) {
                skinTypes.add(skin);
            }
        }
        Model model = new Model(brand, name, category, price, link, pageUrl, detail1, orgin, volume, skinTypes, skinConcern);        
        return model;
    }
    
    private String getModelDocument(BufferedReader reader) throws IOException {
        String document = "<model>";
        String line = "";
        boolean isStart = false;

        // Get product brard
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("breadcrumb")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</ol>")) {
                break;
            }
        }
        isStart = false;

        // get another data
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<div class=\"top_2\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("<div class=\"top_3\">")) {
                break;
            }
        }
        isStart = false;

        // get product detail
        int count = 0;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<div class=\"tab_content\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (line.contains("<div class=\"row tabs_item\"")) {
                count++;
            }
            if (isStart && count == 2) {
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
                break;
            }
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "li", "class", "li")) {
                    eventReader.next();
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
                break;
            }
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "h1", "class", "text_1")) {
                    XMLEvent value = (XMLEvent) eventReader.next();
                    name = value.asCharacters().getData();
                    if (name.contains(brand)) {
                        name = name.replace(brand, "\b");
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
                break;
            }
            
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "span", "class", "text_5") || ElementChecker.isElementWith(startElement, "span", "class", "text_5 xxx")) {
                    XMLEvent value = (XMLEvent) eventReader.next();
                    price = value.asCharacters().getData();
                    price = price.replace(".", "");
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
                break;
            }
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "img", "alt", "One")) {
                    link = getHref(startElement);
                    return link;
                }
                if (ElementChecker.isElementWith(startElement, "img")) {
                    link = getHref(startElement);
                }
            }
        }
        return link;
    }
    
    private String getHref(StartElement element) {
        Attribute href = element.getAttributeByName(new QName("src"));
        return href == null ? "" : href.getValue();
    }
    
    private String getDetail2(XMLEventReader eventReader) {
        XMLEvent event = null;
        String data = "";
        boolean isStart = false;
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                break;
            }
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "class", "top_3")) {
                    isStart = true;
                }
                
            }
            if (isStart && event.isCharacters()) {
                data += " " + event;
            }
        }
        return data;
    }
    
    private String getDetail1(XMLEventReader eventReader) {
        XMLEvent event = null;
        String data = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                break;
            }
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "div", "class", "col-xs-12 col-sm-7 col-md-7 col-lg-7")) {
                    eventReader.next();
                    XMLEvent value = (XMLEvent) eventReader.next();
                    if (value.isCharacters()) {
                        data = value.asCharacters().getData();
                        return data;
                    }
                }
            }
        }
        return data;
    }
    
    private String getOrigin(XMLEventReader eventReader) {
        XMLEvent event = null;
        String data = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                break;
            }
            if (event.isCharacters()) {
                Characters character = event.asCharacters();
                if (character.getData().contains("Xuất xứ")) {
                    eventReader.next();
                    XMLEvent value = (XMLEvent) eventReader.next();
                    if (value.isCharacters()) {
                        data = value.asCharacters().getData();
                        return data;
                    }
                }
            }
        }
        return data;
    }
    
    private String getVolume(XMLEventReader eventReader) {
        XMLEvent event = null;
        String data = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                break;
            }
            if (event.isCharacters()) {
                Characters character = event.asCharacters();
                if (character.getData().contains("Quy cách")) {
                    eventReader.next();
                    XMLEvent value = (XMLEvent) eventReader.next();
                    if (value.isCharacters()) {
                        data = value.asCharacters().getData();
                        return data;
                    }
                }
            }
        }
        return data;
    }
    
    private String getSkinType(XMLEventReader eventReader) {
        XMLEvent event = null;
        String data = "";
        while (eventReader.hasNext()) {
            try {
                event = (XMLEvent) eventReader.next();
            } catch (Exception e) {
                break;
            }
            if (event.isCharacters()) {
                Characters character = event.asCharacters();
                if (character.getData().contains("Loại da:")) {
                    eventReader.next();
                    XMLEvent value = (XMLEvent) eventReader.next();
                    if (value.isCharacters()) {
                        data = value.asCharacters().getData();
                        return data;
                    }
                }
            }
        }
        return data;
    }
}
