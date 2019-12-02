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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuCV
 */
@Entity
@Table(name = "tblCategory", catalog = "AppropicateCosmetic", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCategory.findAll", query = "SELECT t FROM TblCategory t")
    , @NamedQuery(name = "TblCategory.findByCategoryId", query = "SELECT t FROM TblCategory t WHERE t.categoryId = :categoryId")
    , @NamedQuery(name = "TblCategory.findByCategoryName", query = "SELECT t FROM TblCategory t WHERE t.categoryName = :categoryName")})
public class TblCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "categoryId", nullable = false, length = 36)
    private String categoryId;
    @Column(name = "categoryName", length = 2147483647)
    private String categoryName;
    @OneToMany(mappedBy = "categoryId")
    private Collection<TblProduct> tblProductCollection;

    public TblCategory() {
    }

    public TblCategory(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @XmlTransient
    public Collection<TblProduct> getTblProductCollection() {
        return tblProductCollection;
    }

    public void setTblProductCollection(Collection<TblProduct> tblProductCollection) {
        this.tblProductCollection = tblProductCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCategory)) {
            return false;
        }
        TblCategory other = (TblCategory) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appropicatecosmetic.entity.TblCategory[ categoryId=" + categoryId + " ]";
    }
    
}
