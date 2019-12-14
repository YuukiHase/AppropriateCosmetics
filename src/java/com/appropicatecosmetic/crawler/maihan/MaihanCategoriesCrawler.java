/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.maihan;

import com.appropicatecosmetic.crawler.BaseCrawler;
import com.appropicatecosmetic.utils.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MaihanCategoriesCrawler extends BaseCrawler {

    public MaihanCategoriesCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCategories(String url) {
        try (BufferedReader reader = getBufferedReaderForURL(url)) {
            String document = getCategoryDocument(reader);
            return stAXParserForCategories(document);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(MaihanCategoriesCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getCategoryDocument(final BufferedReader reader) throws IOException {
        String line = "";
        String document = "<doc>";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (isStart && line.contains("</div>")) {
                break;
            }
            if (isStart) {
                document += line.trim();
            }
            if (!isStart && line.contains("<a href=\"https://www.maihan.vn/theo-buoc-cham-soc/\"")) {
                isStart = true;
            }
        }
        isStart = false;
        while ((line = reader.readLine()) != null) {
            if (isStart && line.contains("</ul>")) {
                break;
            }
            if (!isStart && line.contains("<a href=\"https://www.maihan.vn/tam-trang/\"")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
        }
        isStart = false;
        while ((line = reader.readLine()) != null) {
            if (isStart && line.contains("</ul>")) {
                break;
            }
            if (!isStart && line.contains("<a href=\"https://www.maihan.vn/vien-uong-dep-da/\"")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
        }
        isStart = false;
        while ((line = reader.readLine()) != null) {
            if (isStart && line.contains("</ul>")) {
                break;
            }
            if (!isStart && line.contains("<a href=\"https://www.maihan.vn/son-moi/\"")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
        }
        return document;
    }

    public Map<String, String> stAXParserForCategories(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        document = TextUtils.refineHtml(document);
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> categories = new HashMap<>();

        while (eventReader.hasNext()) {
            XMLEvent event = null;
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute href = startElement.getAttributeByName(new QName("href"));
                    String link = href.getValue();
                    event = (XMLEvent) eventReader.next();
                    Characters categoryNameChars = event.asCharacters();

                    categories.put(link, categoryNameChars.getData());
                }
            }
        }
        return categories;
    }
}
