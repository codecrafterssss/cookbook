package com.cookbook.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cookbook.dao.ChefDAO;
import com.cookbook.dao.IngredientDAO;
import com.cookbook.dao.RecipeDAO;
import com.cookbook.model.Ingredient;
import com.cookbook.model.Recipe;
import com.cookbook.model.RecipeIngredient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/recipes")
public class AdminRecipeServlet extends HttpServlet {
    private final RecipeDAO recipeDAO = new RecipeDAO();
    private final IngredientDAO ingredientDAO = new IngredientDAO();
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
                // show add form
                req.setAttribute("chefs", chefDAO.findAll());
                req.setAttribute("ingredients", ingredientDAO.findAll());
                req.getRequestDispatcher("/admin/add-recipe.jsp").forward(req, resp);
            } else if ("edit".equalsIgnoreCase(action)) {
                String id = req.getParameter("id");
                if (id == null) { resp.sendError(400, "id required"); return; }
                Recipe recipe = recipeDAO.findByIdWithIngredients(Integer.parseInt(id));
             // create comma-separated ingredient names
                String joined = recipe.getRecipeIngredients().stream()
                    .map(ri -> ri.getIngredient() != null ? ri.getIngredient().getName() : "")
                    .filter(s -> !s.isBlank())
                    .collect(java.util.stream.Collectors.joining(", "));
                req.setAttribute("ingredientNames", joined);
                req.setAttribute("recipe", recipe);

                req.setAttribute("recipe", recipe);
                req.setAttribute("chefs", chefDAO.findAll());
                req.setAttribute("ingredients", ingredientDAO.findAll());
                req.getRequestDispatcher("/admin/update-recipe.jsp").forward(req, resp);
            } else if ("delete".equalsIgnoreCase(action)) {
                String id = req.getParameter("id");
                if (id != null) {
                    recipeDAO.delete(Integer.parseInt(id));
                }
                resp.sendRedirect(req.getContextPath() + "/admin/recipes");
            } else {
                // list
                List<Recipe> list = recipeDAO.findAll();
                req.setAttribute("recipes", list);
                req.getRequestDispatcher("/admin/view-recipes.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Handles create (action=create) and update (action=update).
     * Expects form fields:
     *  title, instructions, cookTime, totalCalories, chefId, ingredients (comma separated or multi)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkAdmin(req, resp)) return;
        String action = req.getParameter("action");
        try {
            if ("create".equalsIgnoreCase(action)) {
                Recipe recipe = readRecipeFromRequest(req);
                recipeDAO.create(recipe);
                resp.sendRedirect(req.getContextPath() + "/admin/recipes");
            } else if ("update".equalsIgnoreCase(action)) {
                String idStr = req.getParameter("id");
                if (idStr == null) { resp.sendError(400, "id required"); return; }
                int id = Integer.parseInt(idStr);
                Recipe recipe = readRecipeFromRequest(req);
                recipe.setRecipeId(id);
                recipeDAO.update(recipe);
                resp.sendRedirect(req.getContextPath() + "/admin/recipes");
            } else {
                resp.sendError(400, "Unknown action");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private Recipe readRecipeFromRequest(HttpServletRequest req) throws Exception {
        String title = req.getParameter("title");
        String instructions = req.getParameter("instructions");
        Integer cookTime = parseIntegerOrNull(req.getParameter("cookTime"));
        Integer totalCalories = parseIntegerOrNull(req.getParameter("totalCalories"));
        Integer chefId = parseIntegerOrNull(req.getParameter("chefId"));

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setInstructions(instructions);
        recipe.setCookTime(cookTime);
        recipe.setTotalCalories(totalCalories);
        recipe.setChefId(chefId);

        // ingredients: either multi param or comma-separated names/ids
        List<RecipeIngredient> ris = new ArrayList<>();
        String[] ingParams = req.getParameterValues("ingredientId");
        if (ingParams != null && ingParams.length > 0) {
            for (String s : ingParams) {
                if (s == null || s.isBlank()) continue;
                // may be id or name; try id first
                try {
                    int iid = Integer.parseInt(s);
                    Ingredient ing = ingredientDAO.findById(iid);
                    if (ing != null) {
                        RecipeIngredient ri = new RecipeIngredient();
                        ri.setIngredientId(iid);
                        ri.setIngredient(ing);
                        ris.add(ri);
                    }
                } catch (NumberFormatException nfe) {
                    // treat as name
                    Ingredient ing = ingredientDAO.createIfNotExists(s.trim());
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setIngredientId(ing.getIngredientId());
                    ri.setIngredient(ing);
                    ris.add(ri);
                }
            }
        } else {
            String comma = req.getParameter("ingredients");
            if (comma != null && !comma.isBlank()) {
                String[] parts = comma.split(",");
                for (String p : parts) {
                    String name = p.trim();
                    if (name.isEmpty()) continue;
                    Ingredient ing = ingredientDAO.createIfNotExists(name);
                    RecipeIngredient ri = new RecipeIngredient();
                    ri.setIngredientId(ing.getIngredientId());
                    ri.setIngredient(ing);
                    ris.add(ri);
                }
            }
        }
        recipe.setRecipeIngredients(ris);
        return recipe;
    }

    private Integer parseIntegerOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }
}
