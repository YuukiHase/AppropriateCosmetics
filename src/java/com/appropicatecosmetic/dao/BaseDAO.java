/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.utils.DBUtils;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author PhuCV
 * @param <T> object model
 */
public class BaseDAO<T> implements IGenericDAO<T> {

    protected Class<T> anonymousClass;

    public BaseDAO() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        anonymousClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public T create(T t) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public T update(T t) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public boolean delete(T t) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(t);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }

    @Override
    public List<T> getAll(String namedQuery) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            List<T> result = em.createNamedQuery(namedQuery, anonymousClass).getResultList();
            transaction.commit();
            return result;
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
