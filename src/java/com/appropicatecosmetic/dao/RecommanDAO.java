/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblProduct;
import com.appropicatecosmetic.entity.TblRecommand;
import com.appropicatecosmetic.entity.TblUser;
import com.appropicatecosmetic.utils.DBUtils;
import com.appropicatecosmetic.utils.TextUtils;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author PhuCV
 */
public class RecommanDAO extends BaseDAO<TblRecommand> {

    public RecommanDAO() {
    }

    private static RecommanDAO instance;
    private static final Object LOCK = new Object();

    public static RecommanDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new RecommanDAO();
            }
        }
        return instance;
    }

    public synchronized TblRecommand insertAndUpdateRecomand(TblProduct product, TblUser user, double point) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblRecommand> Recommand = em.createNativeQuery("SELECT * FROM TblRecommand t WHERE t.productId = ? and t.userId= ? ", TblRecommand.class)
                    .setParameter(1, product.getProductId())
                    .setParameter(2, user.getUserId())
                    .getResultList();
            if (Recommand.isEmpty()) {
                TblRecommand tblRecommand = new TblRecommand(TextUtils.getUUID());
                tblRecommand.setProductId(product);
                tblRecommand.setUserId(user);
                tblRecommand.setProductPoint(point);
                return create(tblRecommand);
            } else {
                Recommand.get(0).setProductPoint(point);
                return update(Recommand.get(0));
            }
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
