package com.cookbook.dao;

import com.cookbook.model.Chef;
import com.cookbook.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChefDAO {

    public Chef create(Chef chef) throws Exception {
        String sql = "INSERT INTO chefs (name) VALUES (?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, chef.getName());
            int affected = ps.executeUpdate();
            if (affected == 0) return null;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    chef.setChefId(rs.getInt(1));
                    return chef;
                }
            }
            return null;
        }
    }

    public Chef findById(int chefId) throws Exception {
        String sql = "SELECT chef_id, name FROM chefs WHERE chef_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, chefId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Chef(rs.getInt("chef_id"), rs.getString("name"));
                }
                return null;
            }
        }
    }

    public List<Chef> findAll() throws Exception {
        String sql = "SELECT chef_id, name FROM chefs ORDER BY name";
        List<Chef> list = new ArrayList<>();
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Chef(rs.getInt("chef_id"), rs.getString("name")));
            }
        }
        return list;
    }

    public boolean update(Chef chef) throws Exception {
        String sql = "UPDATE chefs SET name = ? WHERE chef_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, chef.getName());
            ps.setInt(2, chef.getChefId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int chefId) throws Exception {
        String sql = "DELETE FROM chefs WHERE chef_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, chefId);
            return ps.executeUpdate() > 0;
        }
    }
}
