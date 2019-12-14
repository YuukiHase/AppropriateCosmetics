/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.contants.DataContaints;
import com.appropicatecosmetic.contants.SkinContaints;
import com.appropicatecosmetic.entity.TblConcern;
import com.appropicatecosmetic.entity.TblProduct;
import com.appropicatecosmetic.entity.TblSkinType;
import com.appropicatecosmetic.entity.TblUser;
import com.appropicatecosmetic.utils.DBUtils;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author PhuCV
 */
public class ProductDAO extends BaseDAO<TblProduct> {

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

    public synchronized TblProduct getProductByID(String id) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblProduct> product = em.createNamedQuery("TblProduct.findByProductId", TblProduct.class)
                    .setParameter("productId", id)
                    .getResultList();
            if (product != null && !product.isEmpty()) {
                return product.get(0);
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

    public synchronized TblProduct saveProductWhileCrawling(TblProduct product) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<TblProduct> listProduct = em.createNamedQuery("TblProduct.findByProductLink", TblProduct.class)
                    .setParameter("productLink", product.getProductLink())
                    .getResultList();
            if (listProduct != null && !listProduct.isEmpty()) {
                if (product.getProductLink().equals(listProduct.get(0).getImageLink())) {
                    return listProduct.get(0);
                } else {
                    product.setProductId(listProduct.get(0).getProductId());
                    TblProduct updatedProduct = update(product);
                    TblUser defaultUser = UserDAO.getInstance().checkDefaultUser();
                    double defaultPoint = calculatePointForDefaultUser(updatedProduct);
                    RecommanDAO.getInstance().insertAndUpdateRecomand(updatedProduct, defaultUser, defaultPoint);
                    List<TblUser> listuser = UserDAO.getInstance().getAllUser();
                    for (TblUser tblUser : listuser) {
                        if (!tblUser.getFullName().equalsIgnoreCase("defaultUser")
                                && !tblUser.getTblConcernCollection().isEmpty()
                                && !tblUser.getTblSkinTypeCollection().isEmpty()) {
                            double point = calculatePointForUser(updatedProduct, tblUser);
                            RecommanDAO.getInstance().insertAndUpdateRecomand(updatedProduct, tblUser, point);
                        }
                    }
                    return updatedProduct;
                }
            }
            // create product create recommand point
            TblProduct createdProduct = create(product);
            TblUser defaultUser = UserDAO.getInstance().checkDefaultUser();
            double defaultPoint = calculatePointForDefaultUser(createdProduct);
            RecommanDAO.getInstance().insertAndUpdateRecomand(createdProduct, defaultUser, defaultPoint);
            List<TblUser> listuser = UserDAO.getInstance().getAllUser();
            for (TblUser tblUser : listuser) {
                if (!tblUser.getFullName().equalsIgnoreCase("defaultUser")
                        && !tblUser.getTblConcernCollection().isEmpty()
                        && !tblUser.getTblSkinTypeCollection().isEmpty()) {
                    double point = calculatePointForUser(createdProduct, tblUser);
                    RecommanDAO.getInstance().insertAndUpdateRecomand(createdProduct, tblUser, point);
                }
            }
            return createdProduct;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public synchronized double calculatePointForDefaultUser(TblProduct product) {
        double point = 0;

        // tinh diem theo skintype
        for (TblSkinType tblSkinType : product.getTblSkinTypeCollection()) {

            // moi loai da +2
            if (tblSkinType.getSkinTypeName().equalsIgnoreCase(SkinContaints.LISTSKINTYPES.get(0))) {
                point = 2;
                break;
            } //da hon hop
            else if (tblSkinType.getSkinTypeName().equalsIgnoreCase(SkinContaints.LISTSKINTYPES.get(1))) {
                point += 1.5;
            } else {
                point += 1;
            }
        }

        // cang nhieu cong dung cong nhieu diem
        for (TblConcern tblConcern : product.getTblConcernCollection()) {
            point += 0.1;
        }

        // cong diem
        if (product.getOrigin().toLowerCase().contains(DataContaints.KOREA)) {
            point += 0.04;
        } else if (product.getOrigin().toLowerCase().contains(DataContaints.JAPAN)) {
            point += 0.03;
        } else if (product.getOrigin().toLowerCase().contains(DataContaints.AMERICA)) {
            point += 0.02;
        } else if (product.getOrigin().toLowerCase().contains(DataContaints.UNKNOWN)) {
            // khong cong diem cho san pham k xac dinh nguon goc
        } else {
            point += 0.01;
        }

        if (product.getVolume().toLowerCase().contains(DataContaints.UNKNOWN)) {
            /// khong cong diem cho san pham k co khoi luong
        } else {
            point += 0.01;
        }
        return point;
    }

    public synchronized double calculatePointForUser(TblProduct product, TblUser user) {
        double point = 0;
        int dataType = 0;
        String userSkinType = "";
        for (TblSkinType tblSkinType : user.getTblSkinTypeCollection()) {
            if (tblSkinType.getSkinTypeName().equalsIgnoreCase("Da nhạy cảm")) {
                dataType = 1;
            }
            userSkinType = tblSkinType.getSkinTypeName();
        }

        // tinh diem theo skintype
        for (TblSkinType tblSkinType : product.getTblSkinTypeCollection()) {

            // neu user là da nhay cam, moi loai da +5
            if (tblSkinType.getSkinTypeName().equalsIgnoreCase(SkinContaints.LISTSKINTYPES.get(0))) {
                point = 5;
                break;
            } // neu user k phai la da nhay cam, da hon hop +2
            else if (dataType == 0 && tblSkinType.getSkinTypeName().equalsIgnoreCase(SkinContaints.LISTSKINTYPES.get(1))) {
                point += 2;
            } else if (userSkinType.equalsIgnoreCase(tblSkinType.getSkinTypeName())) {
                point += 10;
            }
        }

        // so sanh data user va data product de tinh diem
        boolean concernCheck;
        boolean minusPointsCheck = false;
        for (TblConcern tblUserConcern : user.getTblConcernCollection()) {
            concernCheck = false;
            for (TblConcern tblProductConcern : product.getTblConcernCollection()) {
                if (tblUserConcern.getConcernName().equalsIgnoreCase(tblProductConcern.getConcernName())) {
                    concernCheck = true;
                }
            }

            // thieu 1cong dung tru 0.2
            if (!concernCheck) {
                minusPointsCheck = true;
                point -= 0.1;
            } else {
                point += 0.2;
            }
        }

        // đủ hết +1 thêm điểm
        if (!minusPointsCheck) {
            point += 1;
        }

        // cong diem
        if (product.getOrigin().toLowerCase().contains(DataContaints.KOREA)) {
            point += 0.04;
        } else if (product.getOrigin().toLowerCase().contains(DataContaints.JAPAN)) {
            point += 0.03;
        } else if (product.getOrigin().toLowerCase().contains(DataContaints.AMERICA)) {
            point += 0.02;
        } else if (product.getOrigin().toLowerCase().contains(DataContaints.UNKNOWN)) {
            // khong cong diem cho san pham k xac dinh nguon goc
        } else {
            point += 0.01;
        }

        if (product.getVolume().toLowerCase().contains(DataContaints.UNKNOWN)) {
            /// khong cong diem cho san pham k co khoi luong
        } else {
            point += 0.01;
        }
        return point;
    }
}
