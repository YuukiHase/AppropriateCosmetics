/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.contants;

import java.io.File;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author PhuCV
 */
public class DataContaints {

    private static final String XMLFILE = "/WEB-INF/containts.xml";
    public static final String UNKNOWN = "Không xác định";
    public static final String KOREA = "Hàn quốc";
    public static final String JAPAN = "Nhật Bản";
    public static final String AMERICA = "Mỹ";

    private static DataContaints instance;
    private static final Object LOCK = new Object();

    public DataContaints() {
    }

    public static DataContaints getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new DataContaints();
            }
        }
        return instance;
    }

    public String getMaihanlink(ServletContext context) {
        try {
            String realPath = context.getRealPath("/");
            String filePath = realPath + XMLFILE;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File(filePath);
            Document doc = db.parse(f);
            if (doc != null) {
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpath = xpf.newXPath();
                Node node = (Node) xpath.evaluate("//maihan", doc, XPathConstants.NODE);
                if (node != null) {
                    return node.getTextContent();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public String getMaplelink(ServletContext context) {
        try {
            String realPath = context.getRealPath("/");
            String filePath = realPath + XMLFILE;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File(filePath);
            Document doc = db.parse(f);
            if (doc != null) {
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpath = xpf.newXPath();
                Node node = (Node) xpath.evaluate("//maple", doc, XPathConstants.NODE);
                if (node != null) {
                    return node.getTextContent();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public String getMapleLinkData(ServletContext context) {
        try {
            String realPath = context.getRealPath("/");
            String filePath = realPath + XMLFILE;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File(filePath);
            Document doc = db.parse(f);
            if (doc != null) {
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpath = xpf.newXPath();
                Node node = (Node) xpath.evaluate("//maplelink", doc, XPathConstants.NODE);
                if (node != null) {
                    return node.getTextContent();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
