package com.codegnan.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.codegnan.model.Recipe;

public class RecipeDAO {

    // ----------------------------------------------------------------
    // 1. ADD NEW RECIPE
    // ----------------------------------------------------------------
    public int addRecipe(Recipe recipe) {
        int recipeId = -1;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO recipes(title, instructions, cook_time, total_calories, chef_id) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getInstructions());
            ps.setInt(3, recipe.getCookTime());
            ps.setInt(4, recipe.getTotalCalories());
            ps.setInt(5, recipe.getChefId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                recipeId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipeId;
    }

    // ----------------------------------------------------------------
    // 2. UPDATE RECIPE
    // ----------------------------------------------------------------
    public boolean updateRecipe(Recipe recipe) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE recipes SET title=?, instructions=?, cook_time=?, total_calories=?, chef_id=? WHERE recipe_id=?"
            );

            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getInstructions());
            ps.setInt(3, recipe.getCookTime());
            ps.setInt(4, recipe.getTotalCalories());
            ps.setInt(5, recipe.getChefId());
            ps.setInt(6, recipe.getRecipeId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ----------------------------------------------------------------
    // 3. DELETE RECIPE
    // ----------------------------------------------------------------
    public boolean deleteRecipe(int recipeId) {
        try {
            Connection con = DBConnection.getConnection();

            // First delete from recipe_ingredients table
            PreparedStatement ps1 = con.prepareStatement(
                "DELETE FROM recipe_ingredients WHERE recipe_id=?"
            );
            ps1.setInt(1, recipeId);
            ps1.executeUpdate();

            // Then delete recipe
            PreparedStatement ps2 = con.prepareStatement(
                "DELETE FROM recipes WHERE recipe_id=?"
            );
            ps2.setInt(1, recipeId);

            return ps2.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ----------------------------------------------------------------
    // 4. GET RECIPE BY ID
    // ----------------------------------------------------------------
    public Recipe getRecipeById(int recipeId) {
        Recipe recipe = null;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM recipes WHERE recipe_id=?"
            );
            ps.setInt(1, recipeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                recipe = new Recipe();
                recipe.setRecipeId(rs.getInt("recipe_id"));
                recipe.setTitle(rs.getString("title"));
                recipe.setInstructions(rs.getString("instructions"));
                recipe.setCookTime(rs.getInt("cook_time"));
                recipe.setTotalCalories(rs.getInt("total_calories"));
                recipe.setChefId(rs.getInt("chef_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipe;
    }

    // ----------------------------------------------------------------
    // 5. GET ALL RECIPES (for Admin Dashboard)
    // ----------------------------------------------------------------
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM recipes");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Recipe r = new Recipe();
                r.setRecipeId(rs.getInt("recipe_id"));
                r.setTitle(rs.getString("title"));
                r.setInstructions(rs.getString("instructions"));
                r.setCookTime(rs.getInt("cook_time"));
                r.setTotalCalories(rs.getInt("total_calories"));
                r.setChefId(rs.getInt("chef_id"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ----------------------------------------------------------------
    // 6. GET RECIPES BY CALORIES (Used in Calorie Planner)
    // ----------------------------------------------------------------
    public List<Recipe> getRecipesByCalories(int maxCalories) {
        List<Recipe> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM recipes WHERE total_calories <= ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, maxCalories);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Recipe r = new Recipe();
                r.setRecipeId(rs.getInt("recipe_id"));
                r.setTitle(rs.getString("title"));
                r.setInstructions(rs.getString("instructions"));
                r.setCookTime(rs.getInt("cook_time"));
                r.setTotalCalories(rs.getInt("total_calories"));
                r.setChefId(rs.getInt("chef_id"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ----------------------------------------------------------------
    // 7. GET RECIPES BY INGREDIENTS (Ingredient-based Recommender)
    // ----------------------------------------------------------------
    public List<Recipe> getRecipesByIngredients(List<Integer> ingredientIds) {
        List<Recipe> list = new ArrayList<>();

        if (ingredientIds == null || ingredientIds.isEmpty()) {
            return list;
        }

        try {
            Connection con = DBConnection.getConnection();

            String placeholders = String.join(",", Collections.nCopies(ingredientIds.size(), "?"));

            String query =
                "SELECT DISTINCT r.* " +
                "FROM recipes r " +
                "JOIN recipe_ingredients ri ON r.recipe_id = ri.recipe_id " +
                "WHERE ri.ingredient_id IN (" + placeholders + ")";

            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < ingredientIds.size(); i++) {
                ps.setInt(i + 1, ingredientIds.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Recipe r = new Recipe();
                r.setRecipeId(rs.getInt("recipe_id"));
                r.setTitle(rs.getString("title"));
                r.setCookTime(rs.getInt("cook_time"));
                r.setTotalCalories(rs.getInt("total_calories"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
