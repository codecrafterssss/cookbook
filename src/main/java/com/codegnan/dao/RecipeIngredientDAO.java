package com.codegnan.dao;


import java.sql.*;

public class RecipeIngredientDAO {

    public boolean addIngredientToRecipe(int recipeId, int ingredientId) {

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO recipe_ingredients(recipe_id, ingredient_id) VALUES (?,?)"
            );

            ps.setInt(1, recipeId);
            ps.setInt(2, ingredientId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }

    public boolean removeIngredientsByRecipe(int recipeId) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM recipe_ingredients WHERE recipe_id=?"
            );

            ps.setInt(1, recipeId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }
}
