/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dto;

import com.appropicatecosmetic.entity.TblConcern;
import com.appropicatecosmetic.entity.TblSkinType;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PhuCV
 */
public class Model implements Serializable {

    protected String brand;
    protected String name;
    protected String category;
    protected int price;
    protected String imageLink;
    protected String productLink;
    protected String detail;
    protected String origin;
    protected String volume;
    protected List<TblSkinType> skinTypes;
    protected List<TblConcern> concerns;

    public Model(String brand, String name, String category, int price, String imageLink, String productLink, String detail, String origin, String volume, List<TblSkinType> skinTypes, List<TblConcern> concerns) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageLink = imageLink;
        this.productLink = productLink;
        this.detail = detail;
        this.origin = origin;
        this.volume = volume;
        this.skinTypes = skinTypes;
        this.concerns = concerns;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public List<TblSkinType> getSkinTypes() {
        return skinTypes;
    }

    public void setSkinTypes(List<TblSkinType> skinTypes) {
        this.skinTypes = skinTypes;
    }

    public List<TblConcern> getConcerns() {
        return concerns;
    }

    public void setConcerns(List<TblConcern> concerns) {
        this.concerns = concerns;
    }

}
