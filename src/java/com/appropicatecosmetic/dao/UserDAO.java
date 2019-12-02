/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblUser;

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
}
