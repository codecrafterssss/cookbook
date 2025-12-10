package com.cookbook.controller;

import java.io.IOException;

import com.cookbook.dao.RecipeDAO;
import com.cookbook.model.Recipe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/recipe-details")
public class RecipeDetailsServlet extends HttpServlet {
    private final RecipeDAO recipeDAO = new RecipeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Recipe id is required");
            return;
        }
        try {
            int recipeId = Integer.parseInt(id);
            Recipe recipe = recipeDAO.findByIdWithIngredients(recipeId);
            if (recipe == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
                return;
            }
            req.setAttribute("recipe", recipe);
            req.getRequestDispatcher("/recipe-details.jsp").forward(req, resp);
        } catch (NumberFormatException nfe) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recipe id");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
