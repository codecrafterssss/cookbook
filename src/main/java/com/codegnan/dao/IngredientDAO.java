package com.codegnan.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.codegnan.model.Ingredient;

public class IngredientDAO {

    // ADD INGREDIENT
    public boolean addIngredient(Ingredient ing) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ingredients(name) VALUES (?)"
            );
            ps.setString(1, ing.getName());
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // UPDATE INGREDIENT
    public boolean updateIngredient(Ingredient ing) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE ingredients SET name=? WHERE ingredient_id=?"
            );
            ps.setString(1, ing.getName());
            ps.setInt(2, ing.getIngredientId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // DELETE INGREDIENT
    public boolean deleteIngredient(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM ingredients WHERE ingredient_id=?"
            );
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // GET ALL INGREDIENTS
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ingredients");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient i = new Ingredient();
                i.setIngredientId(rs.getInt("ingredient_id"));
                i.setName(rs.getString("name"));
                list.add(i);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }
}
