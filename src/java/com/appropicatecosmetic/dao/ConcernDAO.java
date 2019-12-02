/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblConcern;
import com.appropicatecosmetic.utils.DBUtils;
import com.appropicatecosmetic.utils.TextUtils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author PhuCV
 */
public class ConcernDAO extends BaseDAO<TblConcern> {

    public ConcernDAO() {
    }

    private static ConcernDAO instance;
    private static final Object LOCK = new Object();

    public static ConcernDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ConcernDAO();
            }
        }
        return instance;
    }
    public synchronized TblConcern getAndInsertIfNewConsern(String name) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblConcern> concerns = em.createNamedQuery("TblConcern.findByConcernName", TblConcern.class)
                    .setParameter("concernName", name)
                    .getResultList();
            if (concerns != null && !concerns.isEmpty()) {
                return concerns.get(0);
            }
            
            TblConcern concern = new TblConcern();
            concern.setConcernId(TextUtils.getUUID());
            concern.setConcernName(name);
            return create(concern);
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
