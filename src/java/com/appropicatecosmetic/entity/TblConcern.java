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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuCV
 */
@Entity
@Table(name = "tblConcern", catalog = "AppropicateCosmetic", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblConcern.findAll", query = "SELECT t FROM TblConcern t")
    , @NamedQuery(name = "TblConcern.findByConcernId", query = "SELECT t FROM TblConcern t WHERE t.concernId = :concernId")
    , @NamedQuery(name = "TblConcern.findByConcernName", query = "SELECT t FROM TblConcern t WHERE t.concernName = :concernName")})
public class TblConcern implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "concernId", nullable = false, length = 36)
    private String concernId;
    @Column(name = "concernName", length = 2147483647)
    private String concernName;
    @JoinTable(name = "MappingProductConcern", joinColumns = {
        @JoinColumn(name = "concernId", referencedColumnName = "concernId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)})
    @ManyToMany
    private Collection<TblProduct> tblProductCollection;
    @JoinTable(name = "MappingUserConcern", joinColumns = {
        @JoinColumn(name = "concernId", referencedColumnName = "concernId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)})
    @ManyToMany
    private Collection<TblUser> tblUserCollection;

    public TblConcern() {
    }

    public TblConcern(String concernId) {
        this.concernId = concernId;
    }

    public String getConcernId() {
        return concernId;
    }

    public void setConcernId(String concernId) {
        this.concernId = concernId;
    }

    public String getConcernName() {
        return concernName;
    }

    public void setConcernName(String concernName) {
        this.concernName = concernName;
    }

    @XmlTransient
    public Collection<TblProduct> getTblProductCollection() {
        return tblProductCollection;
    }

    public void setTblProductCollection(Collection<TblProduct> tblProductCollection) {
        this.tblProductCollection = tblProductCollection;
    }

    @XmlTransient
    public Collection<TblUser> getTblUserCollection() {
        return tblUserCollection;
    }

    public void setTblUserCollection(Collection<TblUser> tblUserCollection) {
        this.tblUserCollection = tblUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (concernId != null ? concernId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblConcern)) {
            return false;
        }
        TblConcern other = (TblConcern) object;
        if ((this.concernId == null && other.concernId != null) || (this.concernId != null && !this.concernId.equals(other.concernId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appropicatecosmetic.entity.TblConcern[ concernId=" + concernId + " ]";
    }
    
}
