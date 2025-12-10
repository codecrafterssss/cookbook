package com.codegnan.controller;


import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.codegnan.dao.ChefDAO;
import com.codegnan.model.Chef;

@WebServlet("/admin/chefs")
public class AdminChefServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {

        ChefDAO dao = new ChefDAO();
        List<Chef> chefs = dao.getAllChefs();

        req.setAttribute("chefs", chefs);
        req.getRequestDispatcher("/admin/chefs.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        ChefDAO dao = new ChefDAO();

        if ("add".equals(action)) {
            Chef chef = new Chef();
            chef.setName(req.getParameter("name"));
            dao.addChef(chef);
        } else if ("update".equals(action)) {
            Chef chef = new Chef();
            chef.setChefId(Integer.parseInt(req.getParameter("id")));
            chef.setName(req.getParameter("name"));
            dao.updateChef(chef);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.deleteChef(id);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/chefs");
    }
}
