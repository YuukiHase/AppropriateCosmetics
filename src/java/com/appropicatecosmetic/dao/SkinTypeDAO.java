/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblConcern;
import com.appropicatecosmetic.entity.TblSkinType;
import com.appropicatecosmetic.utils.DBUtils;
import com.appropicatecosmetic.utils.TextUtils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author PhuCV
 */
public class SkinTypeDAO extends BaseDAO<TblSkinType> {

    public SkinTypeDAO() {
    }

    private static SkinTypeDAO instance;
    private static final Object LOCK = new Object();

    public static SkinTypeDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new SkinTypeDAO();
            }
        }
        return instance;
    }
    public synchronized TblSkinType getAndInsertIfNewSkinType(String name) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblSkinType> skinTypes = em.createNamedQuery("TblSkinType.findBySkinTypeName", TblSkinType.class)
                    .setParameter("skinTypeName", name)
                    .getResultList();
            if (skinTypes != null && !skinTypes.isEmpty()) {
                return skinTypes.get(0);
            }
            
            TblSkinType skinType = new TblSkinType();
            skinType.setSkinTypeId(TextUtils.getUUID());
            skinType.setSkinTypeName(name);
            return create(skinType);
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
