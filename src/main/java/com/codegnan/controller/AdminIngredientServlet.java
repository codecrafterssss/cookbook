package com.codegnan.controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.codegnan.dao.IngredientDAO;
import com.codegnan.model.Ingredient;

@WebServlet("/admin/ingredients")
public class AdminIngredientServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {
        IngredientDAO dao = new IngredientDAO();
        List<Ingredient> ingredients = dao.getAllIngredients();

        req.setAttribute("ingredients", ingredients);
        req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        IngredientDAO dao = new IngredientDAO();

        if ("add".equals(action)) {
            Ingredient ing = new Ingredient();
            ing.setName(req.getParameter("name"));
            dao.addIngredient(ing);
        } else if ("update".equals(action)) {
            Ingredient ing = new Ingredient();
            ing.setIngredientId(Integer.parseInt(req.getParameter("id")));
            ing.setName(req.getParameter("name"));
            dao.updateIngredient(ing);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.deleteIngredient(id);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/ingredients");
    }
}
