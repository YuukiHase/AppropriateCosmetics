
package com.appropicatecosmetic.utils;

import com.appropicatecosmetic.entity.TblProduct;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the huyvq.demo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TblProduct_QNAME = new QName("", "tblProduct");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: huyvq.demo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ModelItem }
     * 
     */
    public TblProduct createModelItem() {
        return new TblProduct();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModelItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tblProduct")
    public JAXBElement<TblProduct> createTblProduct(TblProduct value) {
        return new JAXBElement<TblProduct>(_TblProduct_QNAME, TblProduct.class, null, value);
    }

}
