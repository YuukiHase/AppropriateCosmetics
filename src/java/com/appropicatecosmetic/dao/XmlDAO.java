/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.dao;

import com.appropicatecosmetic.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PhuCV
 */
public class XmlDAO {

    public String getHomeRecommend(String userId) throws SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        String result = "";
        try {
            con = DBUtils.getMyConnection();
            if (con != null) {
                String sql = "select CAST(( select p.productId,p.name,p.price,imageLink,brand,categoryName" +
"				from tblProduct p, tblRecommand r ,tblCategory c" +
"				where p.productId=r.productId and p.categoryId = c.categoryId and r.userId = ? " +
"				order by r.productPoint desc for XML Path('product'), Root('products')) as NVARCHAR(max) ) AS XmlData";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        result += rs.getString("XmlData");
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    public String getListConcern() throws SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        String result = "";
        try {
            con = DBUtils.getMyConnection();
            if (con != null) {
                String sql = "select cast((select concernId,concernName from tblConcern"
                        + " for XML Path('concern'), Root('concerns'))as NVARCHAR(max)) as XmlData";
                stm = con.prepareStatement(sql);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        result += rs.getString("XmlData");
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    
    public String getListSkinType() throws SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        String result = "";
        try {
            con = DBUtils.getMyConnection();
            if (con != null) {
                String sql = "select cast((select skinTypeId,skinTypeName from tblSkinType "
                        + "for XML Path('skintype'), Root('skintypes'))as NVARCHAR(max)) as XmlData";
                stm = con.prepareStatement(sql);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        result += rs.getString("XmlData");
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    public String getListCategory() throws SQLException, Exception {
        Connection con = null;
        PreparedStatement stm = null;
        String result = "";
        try {
            con = DBUtils.getMyConnection();
            if (con != null) {
                String sql = "select cast((select categoryId,categoryName from tblCategory"
                        + " for XML Path('category'), Root('categories'))as NVARCHAR(max)) as XmlData";
                stm = con.prepareStatement(sql);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        result += rs.getString("XmlData");
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
