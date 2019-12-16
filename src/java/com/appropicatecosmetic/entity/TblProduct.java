/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author PhuCV
 */
@Entity
@Table(name = "tblProduct", catalog = "AppropicateCosmetic", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblProduct.findAll", query = "SELECT t FROM TblProduct t")
    , @NamedQuery(name = "TblProduct.findByProductId", query = "SELECT t FROM TblProduct t WHERE t.productId = :productId")
    , @NamedQuery(name = "TblProduct.findByName", query = "SELECT t FROM TblProduct t WHERE t.name = :name")
    , @NamedQuery(name = "TblProduct.findByPrice", query = "SELECT t FROM TblProduct t WHERE t.price = :price")
    , @NamedQuery(name = "TblProduct.findByImageLink", query = "SELECT t FROM TblProduct t WHERE t.imageLink = :imageLink")
    , @NamedQuery(name = "TblProduct.findByProductLink", query = "SELECT t FROM TblProduct t WHERE t.productLink = :productLink")
    , @NamedQuery(name = "TblProduct.findByDetail", query = "SELECT t FROM TblProduct t WHERE t.detail = :detail")
    , @NamedQuery(name = "TblProduct.findByOrigin", query = "SELECT t FROM TblProduct t WHERE t.origin = :origin")
    , @NamedQuery(name = "TblProduct.findByVolume", query = "SELECT t FROM TblProduct t WHERE t.volume = :volume")})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tblProduct", propOrder = {
    "brand",
    "name",
    "price",
    "imageLink",
    "productLink",
    "detail",
    "origin",
    "volume",
    "productId",
    "tblConcernCollection",
    "tblSkinTypeCollection",
    "tblRecommandCollection",
    "categoryId"
})
public class TblProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "productId", nullable = false, length = 36)
    private String productId;
    @XmlElement(required = true)
    @Column(name = "name", length = 2147483647)
    private String name;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    @Column(name = "price")
    private Integer price;
    @XmlElement(required = true)
    @Column(name = "imageLink", length = 2147483647)
    private String imageLink;
    @XmlElement(required = true)
    @Column(name = "productLink", length = 2147483647)
    private String productLink;
    @XmlElement(required = true)
    @Column(name = "detail", length = 2147483647)
    private String detail;
    @XmlElement(required = true)
    @Column(name = "origin", length = 2147483647)
    private String origin;
    @XmlElement(required = true)
    @Column(name = "volume", length = 2147483647)
    private String volume;
    @XmlElement(required = true)
    @Column(name = "brand", length = 2147483647)
    private String brand;
    @JoinTable(name = "MappingProductConcern", joinColumns = {
        @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "concernId", referencedColumnName = "concernId", nullable = false)})
    @ManyToMany
    private Collection<TblConcern> tblConcernCollection;
    @JoinTable(name = "MappingProductSkinType", joinColumns = {
        @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "skinTypeId", referencedColumnName = "skinTypeId", nullable = false)})
    @ManyToMany
    private Collection<TblSkinType> tblSkinTypeCollection;
    @OneToMany(mappedBy = "productId")
    private Collection<TblRecommand> tblRecommandCollection;
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @ManyToOne
    private TblCategory categoryId;

    public TblProduct() {
    }

    public TblProduct(String productId, String name, Integer price, String imageLink, String productLink, String detail, String origin, String volume, String brand, Collection<TblConcern> tblConcernCollection, Collection<TblSkinType> tblSkinTypeCollection, TblCategory categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageLink = imageLink;
        this.productLink = productLink;
        this.detail = detail;
        this.origin = origin;
        this.volume = volume;
        this.brand = brand;
        this.tblConcernCollection = tblConcernCollection;
        this.tblSkinTypeCollection = tblSkinTypeCollection;
        this.categoryId = categoryId;
    }

    public TblProduct(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @XmlTransient
    public Collection<TblConcern> getTblConcernCollection() {
        return tblConcernCollection;
    }

    public void setTblConcernCollection(Collection<TblConcern> tblConcernCollection) {
        this.tblConcernCollection = tblConcernCollection;
    }

    @XmlTransient
    public Collection<TblSkinType> getTblSkinTypeCollection() {
        return tblSkinTypeCollection;
    }

    public void setTblSkinTypeCollection(Collection<TblSkinType> tblSkinTypeCollection) {
        this.tblSkinTypeCollection = tblSkinTypeCollection;
    }

    @XmlTransient
    public Collection<TblRecommand> getTblRecommandCollection() {
        return tblRecommandCollection;
    }

    public void setTblRecommandCollection(Collection<TblRecommand> tblRecommandCollection) {
        this.tblRecommandCollection = tblRecommandCollection;
    }

    public TblCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(TblCategory categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblProduct)) {
            return false;
        }
        TblProduct other = (TblProduct) object;
        if ( //this.productId.equals(other.productId)
                this.name.equals(other.name)
                && this.price == other.price
                && this.imageLink.equals(other.imageLink)
                && this.productLink.equals(other.productLink)
                && this.detail.equals(other.detail)
                && this.origin.equals(other.origin)
                && this.volume.equals(other.volume)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "com.appropicatecosmetic.entity.TblProduct[ productId=" + productId + " ]";
    }

}
