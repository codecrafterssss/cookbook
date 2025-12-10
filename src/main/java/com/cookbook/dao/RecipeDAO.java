package com.cookbook.dao;

import com.cookbook.model.Recipe;
import com.cookbook.model.RecipeIngredient;
import com.cookbook.util.DBConnectionManager;
import com.cookbook.model.Ingredient;
import com.cookbook.model.Chef;

import java.sql.*;
import java.util.*;

/**
 * Recipe DAO with methods to:
 * - create/update/delete recipe
 * - read recipe with its ingredients
 * - find recipes by a set of ingredient names (simple recommender)
 *
 * Note: This implementation does not use transactions for multi-step operations — consider wrapping in transactions.
 */
public class RecipeDAO {

    private final IngredientDAO ingredientDAO = new IngredientDAO();
    private final ChefDAO chefDAO = new ChefDAO();

    public Recipe create(Recipe recipe) throws Exception {
        String sql = "INSERT INTO recipes (title, instructions, cook_time, total_calories, chef_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getInstructions());
            ps.setObject(3, recipe.getCookTime(), Types.INTEGER);
            ps.setObject(4, recipe.getTotalCalories(), Types.INTEGER);
            if (recipe.getChefId() != null) ps.setInt(5, recipe.getChefId()); else ps.setNull(5, Types.INTEGER);
            int affected = ps.executeUpdate();
            if (affected == 0) return null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    recipe.setRecipeId(rs.getInt(1));
                }
            }
        }

        // insert recipe ingredients (if any)
        if (recipe.getRecipeIngredients() != null && !recipe.getRecipeIngredients().isEmpty()) {
            addRecipeIngredients(recipe.getRecipeId(), recipe.getRecipeIngredients());
        }
        return recipe;
    }

    private void addRecipeIngredients(Integer recipeId, List<RecipeIngredient> ingredients) throws Exception {
        String sql = "INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (?, ?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            for (RecipeIngredient ri : ingredients) {
                ps.setInt(1, recipeId);
                ps.setInt(2, ri.getIngredientId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public boolean update(Recipe recipe) throws Exception {
        String sql = "UPDATE recipes SET title = ?, instructions = ?, cook_time = ?, total_calories = ?, chef_id = ? WHERE recipe_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getInstructions());
            ps.setObject(3, recipe.getCookTime(), Types.INTEGER);
            ps.setObject(4, recipe.getTotalCalories(), Types.INTEGER);
            if (recipe.getChefId() != null) ps.setInt(5, recipe.getChefId()); else ps.setNull(5, Types.INTEGER);
            ps.setInt(6, recipe.getRecipeId());
            boolean updated = ps.executeUpdate() > 0;

            // simple approach: delete existing recipe_ingredients and re-add from recipe object
            String deleteSql = "DELETE FROM recipe_ingredients WHERE recipe_id = ?";
            try (PreparedStatement psDel = c.prepareStatement(deleteSql)) {
                psDel.setInt(1, recipe.getRecipeId());
                psDel.executeUpdate();
            }
            if (recipe.getRecipeIngredients() != null && !recipe.getRecipeIngredients().isEmpty()) {
                addRecipeIngredients(recipe.getRecipeId(), recipe.getRecipeIngredients());
            }
            return updated;
        }
    }

    public boolean delete(int recipeId) throws Exception {
        // delete child rows first
        String delRI = "DELETE FROM recipe_ingredients WHERE recipe_id = ?";
        String delRecipe = "DELETE FROM recipes WHERE recipe_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps1 = c.prepareStatement(delRI);
             PreparedStatement ps2 = c.prepareStatement(delRecipe)) {
            ps1.setInt(1, recipeId);
            ps1.executeUpdate();

            ps2.setInt(1, recipeId);
            return ps2.executeUpdate() > 0;
        }
    }

    public Recipe findByIdWithIngredients(int recipeId) throws Exception {
        String sql = "SELECT r.recipe_id, r.title, r.instructions, r.cook_time, r.total_calories, r.chef_id, c.name as chef_name " +
                "FROM recipes r LEFT JOIN chefs c ON r.chef_id = c.chef_id WHERE r.recipe_id = ?";
        Recipe recipe = null;
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    recipe = new Recipe();
                    recipe.setRecipeId(rs.getInt("recipe_id"));
                    recipe.setTitle(rs.getString("title"));
                    recipe.setInstructions(rs.getString("instructions"));
                    recipe.setCookTime((Integer) rs.getObject("cook_time"));
                    recipe.setTotalCalories((Integer) rs.getObject("total_calories"));
                    Integer chefId = (Integer) rs.getObject("chef_id");
                    recipe.setChefId(chefId);
                    if (chefId != null) recipe.setChef(new Chef(chefId, rs.getString("chef_name")));
                }
            }
        }
        if (recipe != null) {
            // load ingredients
            String sqlRI = "SELECT ri.ingredient_id, i.name FROM recipe_ingredients ri JOIN ingredients i ON ri.ingredient_id = i.ingredient_id WHERE ri.recipe_id = ?";
            try (Connection c = DBConnectionManager.getConnection();
                 PreparedStatement ps = c.prepareStatement(sqlRI)) {
                ps.setInt(1, recipeId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        RecipeIngredient ri = new RecipeIngredient();
                        ri.setRecipeId(recipeId);
                        ri.setIngredientId(rs.getInt("ingredient_id"));
                        ri.setIngredient(new Ingredient(rs.getInt("ingredient_id"), rs.getString("name")));
                        recipe.getRecipeIngredients().add(ri);
                    }
                }
            }
        }
        return recipe;
    }

    public List<Recipe> findAll() throws Exception {
        String sql = "SELECT recipe_id, title, instructions, cook_time, total_calories, chef_id FROM recipes ORDER BY title";
        List<Recipe> list = new ArrayList<>();
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Recipe r = new Recipe();
                r.setRecipeId(rs.getInt("recipe_id"));
                r.setTitle(rs.getString("title"));
                r.setInstructions(rs.getString("instructions"));
                r.setCookTime((Integer) rs.getObject("cook_time"));
                r.setTotalCalories((Integer) rs.getObject("total_calories"));
                r.setChefId((Integer) rs.getObject("chef_id"));
                list.add(r);
            }
        }
        return list;
    }

    /**
     * Simple ingredient-based recommender:
     * Returns recipes that contain ALL of the provided ingredient names (case-insensitive).
     * It looks up ingredient_ids first and then finds recipes that have all those ingredient_ids.
     *
     * If ingredientNames is empty, returns empty list.
     */
    public List<Recipe> findByIngredientNames(Collection<String> ingredientNames) throws Exception {
        if (ingredientNames == null || ingredientNames.isEmpty()) return Collections.emptyList();

        // Map ingredient name -> id (ignore names that don't exist)
        List<Integer> ids = new ArrayList<>();
        for (String name : ingredientNames) {
            Ingredient ing = ingredientDAO.findByName(name);
            if (ing != null) ids.add(ing.getIngredientId());
        }
        if (ids.isEmpty()) return Collections.emptyList();

        // Build SQL to find recipes having all ingredient ids using GROUP BY + HAVING
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ri.recipe_id FROM recipe_ingredients ri WHERE ri.ingredient_id IN (");
        for (int i = 0; i < ids.size(); i++) {
            sb.append("?");
            if (i < ids.size() - 1) sb.append(",");
        }
        sb.append(") GROUP BY ri.recipe_id HAVING COUNT(DISTINCT ri.ingredient_id) = ?");
        List<Integer> recipeIds = new ArrayList<>();
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sb.toString())) {
            int idx = 1;
            for (Integer id : ids) ps.setInt(idx++, id);
            ps.setInt(idx, ids.size());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) recipeIds.add(rs.getInt("recipe_id"));
            }
        }

        // Load recipes by id
        List<Recipe> result = new ArrayList<>();
        for (Integer rid : recipeIds) {
            Recipe r = findByIdWithIngredients(rid);
            if (r != null) result.add(r);
        }
        return result;
    }
}
