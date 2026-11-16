package com.homefinder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/** Run this FIRST to check that your setup works. */
public class TestConnection {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            DatabaseMetaData info = con.getMetaData();
            System.out.println("Connected successfully!");
            System.out.println("Database : " + info.getDatabaseProductName() + " " + info.getDatabaseProductVersion());
            System.out.println("Driver   : " + info.getDriverName());
            System.out.println("URL      : " + info.getURL());
        } catch (SQLException e) {
            System.out.println("Connection FAILED: " + e.getMessage());
            System.out.println("Check: MySQL running? password correct? database 'homefinder' created?");
        }
    }
}
