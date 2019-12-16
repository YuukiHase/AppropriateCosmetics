/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.utils;

import com.appropicatecosmetic.entity.TblProduct;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Admin
 */
public class JAXBUtils {

    private static final String XMLFILE = "/WEB-INF/model.xsd";

    public static TblProduct Unmarshaller(Document doc, String path) throws JAXBException, ParserConfigurationException, SAXException, IOException {
        JAXBContext jc = JAXBContext.newInstance(TblProduct.class);
        Unmarshaller u = jc.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(path));
        u.setSchema(schema);
        u.setEventHandler(new ValidationEventHandler() {

            @Override
            public boolean handleEvent(ValidationEvent event) {
                if (event.getSeverity() == ValidationEvent.FATAL_ERROR
                        || event.getSeverity() == ValidationEvent.ERROR) {
//                    ValidationEventLocator locator = event.getLocator();
//                    System.out.println("Invalid document" + locator.getURL());
//                    System.out.println("error: " + event.getMessage());
//                    System.out.println("Error at column" + locator.getColumnNumber()
//                            + ", line " + locator.getLineNumber());
                    return false;
                }
                return true;
            }
        });
        DOMSource domSource = new DOMSource(doc);
        TblProduct result = (TblProduct) u.unmarshal(domSource);
        return result;
    }

    public static Document Marshaller(TblProduct model) throws JAXBException, ParserConfigurationException, SAXException, IOException {
        JAXBContext jc = JAXBContext.newInstance(TblProduct.class);
        Marshaller mar = jc.createMarshaller();
        StringWriter sw = new StringWriter();
        mar.marshal(model, sw);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(sw.toString())));
        return doc;
    }

    public static TblProduct validateProduct(TblProduct model, ServletContext context) {
        try {
            String realPath = context.getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = Marshaller(model);
            return Unmarshaller(doc, filePath);
        } catch (Exception e) {
        }
        return null;
    }
}
