package com.codegnan.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.codegnan.dao.RecipeDAO;
import com.codegnan.model.Recipe;

@WebServlet("/calorie-planner")
public class CaloriePlannerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Get calorie input
        String cal = req.getParameter("maxCalories");
        int maxCalories = Integer.parseInt(cal);

        // DAO Call
        RecipeDAO dao = new RecipeDAO();
        List<Recipe> recipes = dao.getRecipesByCalories(maxCalories);

        // Send to JSP
        req.setAttribute("recipes", recipes);
        req.getRequestDispatcher("calorie-planner.jsp").forward(req, resp);
    }
}
