package com.cookbook.dao;

import com.cookbook.model.Ingredient;
import com.cookbook.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {

    public Ingredient create(Ingredient ingredient) throws Exception {
        String sql = "INSERT INTO ingredients (name) VALUES (?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ingredient.getName());
            int affected = ps.executeUpdate();
            if (affected == 0) return null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ingredient.setIngredientId(rs.getInt(1));
                    return ingredient;
                }
            }
            return null;
        }
    }

    /**
     * Finds ingredient by name (case-insensitive match).
     */
    public Ingredient findByName(String name) throws Exception {
        String sql = "SELECT ingredient_id, name FROM ingredients WHERE LOWER(name) = LOWER(?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ingredient(rs.getInt("ingredient_id"), rs.getString("name"));
                }
                return null;
            }
        }
    }

    /**
     * Create ingredient if not exists and return the persisted entity.
     */
    public Ingredient createIfNotExists(String name) throws Exception {
        Ingredient existing = findByName(name);
        if (existing != null) return existing;
        return create(new Ingredient(name));
    }

    public Ingredient findById(int id) throws Exception {
        String sql = "SELECT ingredient_id, name FROM ingredients WHERE ingredient_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ingredient(rs.getInt("ingredient_id"), rs.getString("name"));
                }
                return null;
            }
        }
    }

    public List<Ingredient> findAll() throws Exception {
        String sql = "SELECT ingredient_id, name FROM ingredients ORDER BY name";
        List<Ingredient> list = new ArrayList<>();
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Ingredient(rs.getInt("ingredient_id"), rs.getString("name")));
            }
        }
        return list;
    }

    public boolean delete(int ingredientId) throws Exception {
        String sql = "DELETE FROM ingredients WHERE ingredient_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ingredientId);
            return ps.executeUpdate() > 0;
        }
    }
}
