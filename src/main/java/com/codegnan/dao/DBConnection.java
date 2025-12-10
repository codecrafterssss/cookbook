package com.codegnan.dao;



import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) return connection;

        try {
            Properties props = new Properties();
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
