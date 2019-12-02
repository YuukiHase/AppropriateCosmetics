/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.dto.Model;
import com.appropicatecosmetic.entity.TblProduct;
import com.appropicatecosmetic.utils.DBUtils;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author PhuCV
 */
public class ProductDAO extends BaseDAO<TblProduct>{

    public ProductDAO() {
    }

    private static ProductDAO instance;
    private static final Object LOCK = new Object();

    public static ProductDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductDAO();
            }
        }
        return instance;
    }
    
    public synchronized TblProduct saveProductWhileCrawling(TblProduct product) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblProduct> listProduct = em.createNamedQuery("TblProduct.findByProductLink", TblProduct.class)
                    .setParameter("productLink", product.getProductLink())
                    .getResultList();
            if (listProduct != null && !listProduct.isEmpty()) {
                product.setProductId(listProduct.get(0).getProductId());
                if (product.equals(listProduct.get(0))) {
                    return listProduct.get(0);
                }else{
                    //goi recommand tinh diem
                    update(product);
                }
            }
            //goi recommand tinh diem
            return create(product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
