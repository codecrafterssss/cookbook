package com.cookbook.controller;

import java.io.IOException;
import java.util.List;

import com.cookbook.dao.IngredientDAO;
import com.cookbook.model.Ingredient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/ingredients")
public class AdminIngredientServlet extends HttpServlet {
    private final IngredientDAO ingredientDAO = new IngredientDAO();

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
                req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
            } else if ("delete".equalsIgnoreCase(action)) {
                String id = req.getParameter("id");
                if (id != null) ingredientDAO.delete(Integer.parseInt(id));
                resp.sendRedirect(req.getContextPath() + "/admin/ingredients");
            } else {
                List<Ingredient> list = ingredientDAO.findAll();
                req.setAttribute("ingredients", list);
                req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Create new ingredient (expects "name")
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
            ingredientDAO.createIfNotExists(name.trim());
            resp.sendRedirect(req.getContextPath() + "/admin/ingredients");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
