package com.codegnan.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.codegnan.model.Chef;

public class ChefDAO {

    // ADD CHEF
    public boolean addChef(Chef chef) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO chefs(name) VALUES (?)"
            );
            ps.setString(1, chef.getName());
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // UPDATE CHEF
    public boolean updateChef(Chef chef) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE chefs SET name=? WHERE chef_id=?"
            );
            ps.setString(1, chef.getName());
            ps.setInt(2, chef.getChefId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // DELETE CHEF
    public boolean deleteChef(int chefId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM chefs WHERE chef_id=?"
            );
            ps.setInt(1, chefId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // GET ALL CHEFS
    public List<Chef> getAllChefs() {
        List<Chef> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM chefs");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chef c = new Chef();
                c.setChefId(rs.getInt("chef_id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
