package com.cookbook.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.cookbook.dao.RecipeDAO;
import com.cookbook.model.Recipe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/recipes")
public class RecipeRecommenderServlet extends HttpServlet {
    private final RecipeDAO recipeDAO = new RecipeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show all recipes (or empty search page)
        try {
            List<Recipe> recipes = recipeDAO.findAll();
            req.setAttribute("recipes", recipes);
            req.getRequestDispatcher("/recipes.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Accepts an ingredients parameter from a form:
     * - "ingredients" (comma separated) OR multiple parameters named "ingredients"
     * Returns recipes containing ALL provided ingredients.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String[] multi = req.getParameterValues("ingredients");
            String comma = req.getParameter("ingredients"); // fallback single input
            Set<String> names = new LinkedHashSet<>();

            if (multi != null && multi.length > 0) {
                for (String s : multi) if (s != null && !s.isBlank()) names.add(s.trim());
            } else if (comma != null && !comma.isBlank()) {
                String[] parts = comma.split(",");
                for (String p : parts) if (!p.isBlank()) names.add(p.trim());
            }

            List<Recipe> recipes;
            if (names.isEmpty()) {
                recipes = Collections.emptyList();
            } else {
                recipes = recipeDAO.findByIngredientNames(names);
            }

            req.setAttribute("recipes", recipes);
            req.setAttribute("searchIngredients", String.join(", ", names));
            req.getRequestDispatcher("/recipes.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
