/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.crawler.maihan;

import com.appropicatecosmetic.crawler.BaseCrawler;
import javax.servlet.ServletContext;

/**
 *
 * @author PhuCV
 */
public class MaihanModelCrawler extends BaseCrawler{
    private String pageUrl;
    private String category;

    public MaihanModelCrawler(String pageUrl, String category, ServletContext context) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
    }

    public void getModel() {
        
    }
    
}
