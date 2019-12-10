/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblUser;
import com.appropicatecosmetic.utils.DBUtils;
import com.appropicatecosmetic.utils.TextUtils;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author PhuCV
 */
public class UserDAO extends BaseDAO<TblUser> {

    public UserDAO() {
    }

    private static UserDAO instance;
    private static final Object LOCK = new Object();

    public static UserDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new UserDAO();
            }
        }
        return instance;
    }

    public synchronized TblUser checkDefaultUser() {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblUser> listuser = em.createNamedQuery("TblUser.findByFullName", TblUser.class)
                    .setParameter("fullName", "defaultUser")
                    .getResultList();
            if (!listuser.isEmpty()) {
                return listuser.get(0);
            } else {
                TblUser tblUser = new TblUser();
                tblUser.setUserId(TextUtils.getUUID());
                tblUser.setFullName("defaultUser");
                return create(tblUser);
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
    
    public synchronized List<TblUser> getAllUser() {
        return getAll("TblUser.findAll");
    }
}
