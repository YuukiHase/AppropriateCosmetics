/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appropicatecosmetic.servlet;

import com.appropicatecosmetic.dao.ProductDAO;
import com.appropicatecosmetic.dao.UserDAO;
import com.appropicatecosmetic.dao.XmlDAO;
import com.appropicatecosmetic.entity.TblUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author Admin
 */
public class DetailServlet extends HttpServlet {

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
        String productId = request.getParameter("productId");

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("USERID");
        if (userId == null) {
            userId = UserDAO.getInstance().checkDefaultUser().getUserId();
        } else {
            TblUser user = UserDAO.getInstance().getUserById(userId);
            if (user.getTblConcernCollection().isEmpty() && user.getTblSkinTypeCollection().isEmpty()) {
                userId = UserDAO.getInstance().checkDefaultUser().getUserId();
            }
        }
        try {
            XmlDAO xmlDAO = new XmlDAO();
            String result = xmlDAO.getProductDetail(productId);
            String listConcern = xmlDAO.getListConcernById(productId);
            String listSkintype = xmlDAO.getListSkinTypeById(productId);
            String top5samecategory = xmlDAO.getTop5ProductSameCategory(userId,
                    ProductDAO.getInstance().getProductByID(productId).getCategoryId().getCategoryId());
            String top5Product = xmlDAO.getTop5Recommend(userId);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document detail = db.parse(new InputSource(new StringReader(result)));
            Document docConcerns = db.parse(new InputSource(new StringReader(listConcern)));
            Document docSkintypes = db.parse(new InputSource(new StringReader(listSkintype)));
            Document docTop5samecategory = db.parse(new InputSource(new StringReader(top5samecategory)));
            Document docTop5Product = db.parse(new InputSource(new StringReader(top5Product)));

            session.setAttribute("DETAILPRODUCT", detail);
            session.setAttribute("DETAILPRODUCTCONCERNS", docConcerns);
            session.setAttribute("DETAILPRODUCTSKINTYPES", docSkintypes);
            session.setAttribute("TOP5SAMECATE", docTop5samecategory);
            session.setAttribute("TOP5PRODUCT", docTop5Product);
            request.getRequestDispatcher("detail.jsp").forward(request, response);
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
