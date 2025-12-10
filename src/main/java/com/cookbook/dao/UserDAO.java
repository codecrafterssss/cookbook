package com.cookbook.dao;

import com.cookbook.model.User;
import com.cookbook.util.DBConnectionManager;

import java.sql.*;

/**
 * Basic User DAO. Passwords are handled as plain text here for brevity — hash in production.
 */
public class UserDAO {

    public boolean create(User user) throws Exception {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) user.setUserId(rs.getInt(1));
            }
            return true;
        }
    }

    public User findByUsername(String username) throws Exception {
        String sql = "SELECT user_id, username, password, role FROM users WHERE username = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
                return null;
            }
        }
    }

    public boolean validateLogin(String username, String password) throws Exception {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPass = rs.getString("password");
                    return password != null && password.equals(dbPass);
                }
                return false;
            }
        }
    }

    // Additional utility: find by id, update role, delete
    public User findById(int userId) throws Exception {
        String sql = "SELECT user_id, username, password, role FROM users WHERE user_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
                return null;
            }
        }
    }

    public boolean update(User user) throws Exception {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE user_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getUserId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int userId) throws Exception {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        }
    }
}
