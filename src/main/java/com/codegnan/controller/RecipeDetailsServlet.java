package com.codegnan.controller;



import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.codegnan.dao.RecipeDAO;
import com.codegnan.model.Recipe;

@WebServlet("/recipe-details")
public class RecipeDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp)
            throws ServletException, IOException {

        int recipeId = Integer.parseInt(req.getParameter("id"));
        RecipeDAO dao = new RecipeDAO();
        Recipe recipe = dao.getRecipeById(recipeId);

        req.setAttribute("recipe", recipe);
        req.getRequestDispatcher("recipe-details.jsp").forward(req, resp);
    }
}

