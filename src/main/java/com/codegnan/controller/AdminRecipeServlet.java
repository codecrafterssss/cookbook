package com.codegnan.controller;


import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.codegnan.dao.RecipeDAO;
import com.codegnan.model.Recipe;

@WebServlet("/admin/recipes")
public class AdminRecipeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {

        RecipeDAO dao = new RecipeDAO();
        List<Recipe> recipes = dao.getAllRecipes();

        req.setAttribute("recipes", recipes);
        req.getRequestDispatcher("/admin/view-recipes.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {
        // handle add/update/delete based on action parameter
        String action = req.getParameter("action");
        RecipeDAO dao = new RecipeDAO();

        if ("add".equals(action)) {
            Recipe recipe = new Recipe();
            recipe.setTitle(req.getParameter("title"));
            recipe.setInstructions(req.getParameter("instructions"));
            recipe.setCookTime(Integer.parseInt(req.getParameter("cookTime")));
            recipe.setTotalCalories(Integer.parseInt(req.getParameter("totalCalories")));
            recipe.setChefId(Integer.parseInt(req.getParameter("chefId")));

            dao.addRecipe(recipe);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/recipes");
    }
}
