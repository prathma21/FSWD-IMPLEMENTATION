package com.homefinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Step 1 of JDBC: connect to the database.
 * Connection (interface) is created by DriverManager (class).
 */
public class DBConnection {

    // jdbc:mysql://host:port/databaseName
    private static final String URL = "jdbc:mysql://localhost:3306/homefinder";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password_here";   // <-- CHANGE THIS

    // Every call opens a NEW connection. The caller must close it
    // (we use try-with-resources in PropertyDAO so it closes automatically).
    public static Connection getConnection() throws SQLException {
        // Old tutorials write Class.forName("com.mysql.cj.jdbc.Driver") here.
        // It is not needed in modern JDBC: the driver registers itself.
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
