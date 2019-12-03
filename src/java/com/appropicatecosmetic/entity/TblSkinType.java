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
@Table(name = "tblSkinType", catalog = "AppropicateCosmetic", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSkinType.findAll", query = "SELECT t FROM TblSkinType t")
    , @NamedQuery(name = "TblSkinType.findBySkinTypeId", query = "SELECT t FROM TblSkinType t WHERE t.skinTypeId = :skinTypeId")
    , @NamedQuery(name = "TblSkinType.findBySkinTypeName", query = "SELECT t FROM TblSkinType t WHERE t.skinTypeName = :skinTypeName")})
public class TblSkinType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "skinTypeId", nullable = false, length = 36)
    private String skinTypeId;
    @Column(name = "skinTypeName", length = 2147483647)
    private String skinTypeName;
    @ManyToMany(mappedBy = "tblSkinTypeCollection")
    private Collection<TblUser> tblUserCollection;
    @ManyToMany(mappedBy = "tblSkinTypeCollection")
    private Collection<TblProduct> tblProductCollection;

    public TblSkinType() {
    }

    public TblSkinType(String skinTypeId) {
        this.skinTypeId = skinTypeId;
    }

    public String getSkinTypeId() {
        return skinTypeId;
    }

    public void setSkinTypeId(String skinTypeId) {
        this.skinTypeId = skinTypeId;
    }

    public String getSkinTypeName() {
        return skinTypeName;
    }

    public void setSkinTypeName(String skinTypeName) {
        this.skinTypeName = skinTypeName;
    }

    @XmlTransient
    public Collection<TblUser> getTblUserCollection() {
        return tblUserCollection;
    }

    public void setTblUserCollection(Collection<TblUser> tblUserCollection) {
        this.tblUserCollection = tblUserCollection;
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
        hash += (skinTypeId != null ? skinTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblSkinType)) {
            return false;
        }
        TblSkinType other = (TblSkinType) object;
        if ((this.skinTypeId == null && other.skinTypeId != null) || (this.skinTypeId != null && !this.skinTypeId.equals(other.skinTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appropicatecosmetic.entity.TblSkinType[ skinTypeId=" + skinTypeId + " ]";
    }

}
