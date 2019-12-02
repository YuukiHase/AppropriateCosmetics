/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblCategory;
import com.appropicatecosmetic.utils.TextUtils;
import java.util.List;

/**
 *
 * @author PhuCV
 */
public class CategoryDAO extends BaseDAO<TblCategory> {

    public CategoryDAO() {
    }

    private static CategoryDAO instance;
    private static final Object LOCK = new Object();

    public static CategoryDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CategoryDAO();
            }
        }
        return instance;
    }

    public synchronized TblCategory saveCategoryWhileCrawling(String name) {
        List<TblCategory> listCategory = getAll("TblCategory.findAll");
        TblCategory cate = null;
        double HighPercentage = 0;
        for (TblCategory tblCategory : listCategory) {
            if (name.equals(tblCategory.getCategoryName())) {
                return tblCategory;
            } else {
                double percentage = TextUtils.getMatchingPercentage(name.toLowerCase(), tblCategory.getCategoryName().toLowerCase());
                if (percentage > HighPercentage) {
                    HighPercentage = percentage;
                    cate = tblCategory;
                }
            }
        }
        TblCategory tblCategory = new TblCategory();
        if (HighPercentage < 0.6) {
            tblCategory.setCategoryName(name);
            tblCategory.setCategoryId(TextUtils.getUUID());
            return create(tblCategory);
        }
        return cate;
    }
}
