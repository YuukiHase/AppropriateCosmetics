/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PhuCV
 */
@Entity
@Table(name = "tblRecommand", catalog = "AppropicateCosmetic", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRecommand.findAll", query = "SELECT t FROM TblRecommand t")
    , @NamedQuery(name = "TblRecommand.findByRecommandId", query = "SELECT t FROM TblRecommand t WHERE t.recommandId = :recommandId")
    , @NamedQuery(name = "TblRecommand.findByProductPoint", query = "SELECT t FROM TblRecommand t WHERE t.productPoint = :productPoint")})
public class TblRecommand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "recommandId", nullable = false, length = 36)
    private String recommandId;
    @Column(name = "productPoint")
    private Integer productPoint;
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @ManyToOne
    private TblProduct productId;
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @ManyToOne
    private TblUser userId;

    public TblRecommand() {
    }

    public TblRecommand(String recommandId) {
        this.recommandId = recommandId;
    }

    public String getRecommandId() {
        return recommandId;
    }

    public void setRecommandId(String recommandId) {
        this.recommandId = recommandId;
    }

    public Integer getProductPoint() {
        return productPoint;
    }

    public void setProductPoint(Integer productPoint) {
        this.productPoint = productPoint;
    }

    public TblProduct getProductId() {
        return productId;
    }

    public void setProductId(TblProduct productId) {
        this.productId = productId;
    }

    public TblUser getUserId() {
        return userId;
    }

    public void setUserId(TblUser userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recommandId != null ? recommandId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRecommand)) {
            return false;
        }
        TblRecommand other = (TblRecommand) object;
        if ((this.recommandId == null && other.recommandId != null) || (this.recommandId != null && !this.recommandId.equals(other.recommandId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.appropicatecosmetic.entity.TblRecommand[ recommandId=" + recommandId + " ]";
    }
    
}
