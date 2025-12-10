package com.codegnan.controller;


import java.io.IOException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.codegnan.dao.RecipeDAO;
import com.codegnan.model.Recipe;

@WebServlet("/ingredient-recommender")
public class RecipeRecommenderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {

        String[] ingredientIdsStr = req.getParameterValues("ingredientIds");
        List<Integer> ingredientIds = new ArrayList<>();
        if (ingredientIdsStr != null) {
            for (String id : ingredientIdsStr) {
                ingredientIds.add(Integer.parseInt(id));
            }
        }

        RecipeDAO dao = new RecipeDAO();
        List<Recipe> recipes = dao.getRecipesByIngredients(ingredientIds);

        req.setAttribute("recipes", recipes);
        req.getRequestDispatcher("recipes.jsp").forward(req, resp);
    }
}
