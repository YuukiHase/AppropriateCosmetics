/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.servlet;

import com.appropicatecosmetic.dao.ConcernDAO;
import com.appropicatecosmetic.dao.ProductDAO;
import com.appropicatecosmetic.dao.RecommanDAO;
import com.appropicatecosmetic.dao.SkinTypeDAO;
import com.appropicatecosmetic.dao.UserDAO;
import com.appropicatecosmetic.entity.TblConcern;
import com.appropicatecosmetic.entity.TblProduct;
import com.appropicatecosmetic.entity.TblRecommand;
import com.appropicatecosmetic.entity.TblSkinType;
import com.appropicatecosmetic.entity.TblUser;
import com.appropicatecosmetic.utils.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class SurveyServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String skintype = request.getParameter("skintype");
            String[] listConcern = request.getParameterValues("concern");
            String limit = request.getParameter("limit");

            List<TblSkinType> listSkintypes = new ArrayList();
            listSkintypes.add(SkinTypeDAO.getInstance().getSkinTypeById(skintype));

            List<TblConcern> listConcerns = new ArrayList();
            for (String concern : listConcern) {
                TblConcern tblconcern = ConcernDAO.getInstance().getConcernByID(concern);
                listConcerns.add(tblconcern);
            }
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute("USERID");
            TblUser user = UserDAO.getInstance().getUserById(userId);
            user.setTblConcernCollection(listConcerns);
            user.setTblSkinTypeCollection(listSkintypes);
            UserDAO.getInstance().update(user);

            List<TblProduct> listProduct = ProductDAO.getInstance().getAll("TblProduct.findAll");
            for (TblProduct tblProduct : listProduct) {
                double point = ProductDAO.getInstance().calculatePointForUser(tblProduct, user);
                RecommanDAO.getInstance().insertAndUpdateRecomand(tblProduct, user, point);
            }
            request.getRequestDispatcher("HomeServlet").forward(request, response);
        } catch (Exception e) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
