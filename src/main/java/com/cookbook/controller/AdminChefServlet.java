package com.cookbook.controller;

import java.io.IOException;
import java.util.List;

import com.cookbook.dao.ChefDAO;
import com.cookbook.model.Chef;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/chefs")
public class AdminChefServlet extends HttpServlet {
    private final ChefDAO chefDAO = new ChefDAO();

    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        Object u = session.getAttribute("user");
        if (u == null || !(u instanceof com.cookbook.model.User) ||
                !"ADMIN".equalsIgnoreCase(((com.cookbook.model.User) u).getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin role required");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req, resp)) return;
        String action = req.getParameter("action");
        try {
            if ("add".equalsIgnoreCase(action)) {
                req.getRequestDispatcher("/admin/chefs.jsp").forward(req, resp);
            } else if ("delete".equalsIgnoreCase(action)) {
                String id = req.getParameter("id");
                if (id != null) chefDAO.delete(Integer.parseInt(id));
                resp.sendRedirect(req.getContextPath() + "/admin/chefs");
            } else {
                List<Chef> list = chefDAO.findAll();
                req.setAttribute("chefs", list);
                req.getRequestDispatcher("/admin/chefs.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Create new chef (expects "name")
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req, resp)) return;
        String name = req.getParameter("name");
        if (name == null || name.isBlank()) {
            req.setAttribute("error", "Name is required");
            doGet(req, resp);
            return;
        }
        try {
            Chef c = new Chef();
            c.setName(name.trim());
            chefDAO.create(c);
            resp.sendRedirect(req.getContextPath() + "/admin/chefs");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
