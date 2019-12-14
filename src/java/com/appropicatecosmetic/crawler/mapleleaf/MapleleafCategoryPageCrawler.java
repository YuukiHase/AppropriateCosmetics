/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.mapleleaf;

import com.appropicatecosmetic.contants.DataContaints;
import com.appropicatecosmetic.crawler.BaseCrawler;
import com.appropicatecosmetic.crawler.BaseThread;
import com.appropicatecosmetic.dao.CategoryDAO;
import com.appropicatecosmetic.dao.ProductDAO;
import com.appropicatecosmetic.dto.Model;
import com.appropicatecosmetic.entity.TblCategory;
import com.appropicatecosmetic.entity.TblProduct;
import com.appropicatecosmetic.utils.ElementChecker;
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
public class MapleleafCategoryPageCrawler extends BaseCrawler implements Runnable {

    private String url;
    private String categoryName;

    public MapleleafCategoryPageCrawler(ServletContext context, String url, String categoryName) {
        super(context);
        this.url = url;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String document = getModelListDocument(reader);
            document = TextUtils.refineHtml(document);
            List<String> modelLink = getModelLinks(document);
            for (String link : modelLink) {
                MapleleafModelCrawler mapleleafModelCrawler = new MapleleafModelCrawler(DataContaints.MAPLELINK + link, categoryName, getContext());
                Model model = mapleleafModelCrawler.getModel();
                TblCategory category = CategoryDAO.getInstance()
                        .saveCategoryWhileCrawling(model.getCategory());

                TblProduct product = new TblProduct(TextUtils.getUUID(), model.getName(), model.getPrice(),
                        model.getImageLink(), model.getProductLink(),
                        model.getDetail(), model.getOrigin(), model.getVolume(), model.getBrand(),
                        model.getConcerns(), model.getSkinTypes(), category);
                ProductDAO.getInstance().saveProductWhileCrawling(product);
            }
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (IOException | XMLStreamException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getModelListDocument(BufferedReader reader) throws IOException {
        String line = "";
        String document = "<doc>";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<div class=\"collection-grid\">")) {
                isStart = true;
            }
            if (isStart && line.contains("<div class=\"e-newletter\">")) {
                break;
            }
            if (isStart) {
                document += line.trim();
            }
        }
        return document;
    }

    private List<String> getModelLinks(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        XMLEvent event = null;
        List<String> links = new ArrayList<>();
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "a")) {
                    String link = getHref(startElement);
                    if (!link.contains("javascript") && !isDuplicate(links, link)) {
                        links.add(link);
                    }
                }
            }
        }
        return links;
    }

    private String getHref(StartElement element) {
        Attribute href = element.getAttributeByName(new QName("href"));
        return href == null ? "" : href.getValue();
    }

    private boolean isDuplicate(List<String> list, String link) {
        for (String string : list) {
            if (link.equals(string)) {
                return true;
            }
        }
        return false;
    }
}
