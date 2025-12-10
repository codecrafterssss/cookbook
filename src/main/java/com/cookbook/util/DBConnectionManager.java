package com.cookbook.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

/**
 * Simple connection manager that reads db.properties from classpath (resources/).
 * db.properties example:
 *   jdbc.url=jdbc:mysql://localhost:3306/cookbook
 *   jdbc.username=root
 *   jdbc.password=secret
 *   jdbc.driver=com.mysql.cj.jdbc.Driver
 */
public class DBConnectionManager {

    private static final Properties props = new Properties();
    static {
        try (InputStream in = DBConnectionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
                String driver = props.getProperty("jdbc.driver");
                if (driver != null && !driver.isBlank()) {
                    Class.forName(driver);
                }
            } else {
                throw new RuntimeException("db.properties not found on classpath");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB properties", e);
        }
    }

    public static Connection getConnection() throws Exception {
        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.username");
        String pass = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
