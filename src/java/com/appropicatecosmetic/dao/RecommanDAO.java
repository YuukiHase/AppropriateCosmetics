/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.entity.TblRecommand;

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
}
