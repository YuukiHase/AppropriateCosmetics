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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PhuCV
 */
@Entity
@Table(name = "tblUser", catalog = "AppropicateCosmetic", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUser.findAll", query = "SELECT t FROM TblUser t")
    , @NamedQuery(name = "TblUser.findByUserId", query = "SELECT t FROM TblUser t WHERE t.userId = :userId")
    , @NamedQuery(name = "TblUser.findByFullName", query = "SELECT t FROM TblUser t WHERE t.fullName = :fullName")
    , @NamedQuery(name = "TblUser.findByPassword", query = "SELECT t FROM TblUser t WHERE t.password = :password")})
public class TblUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userId", nullable = false, length = 200)
    private String userId;
    @Column(name = "fullName", length = 2147483647)
    private String fullName;
    @Column(name = "password", length = 2147483647)
    private String password;
    @JoinTable(name = "MappingUserConcern", joinColumns = {
        @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "concernId", referencedColumnName = "concernId", nullable = false)})
    @ManyToMany
    private Collection<TblConcern> tblConcernCollection;
    @JoinTable(name = "MappingUserSkinType", joinColumns = {
        @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "skinTypeId", referencedColumnName = "skinTypeId", nullable = false)})
    @ManyToMany
    private Collection<TblSkinType> tblSkinTypeCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<TblRecommand> tblRecommandCollection;

    public TblUser() {
    }

    public TblUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUser)) {
            return false;
        }
        TblUser other = (TblUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appropicatecosmetic.entity.TblUser[ userId=" + userId + " ]";
    }

}
