/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.jconnectionmavenmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author gavan
 */
public class JConnectionMavenMain {

    public static void main(String[] args) {

        // Chess database: username, email
        String url = "jdbc:mysql://localhost:3306/chess";

        String user = "root";

        String password = "";

        // Database driver class
        String dbDriver = "com.mysql.cj.jdbc.Driver"; // can be read from config

        try {

//            Class.forName(dbDriver); not needed from JDBC 4.0 onwards
            Connection conn = DriverManager.getConnection(url, user, password);
            // Check if connected.
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Successfully connected to database!");
            } else {
                System.out.println("❌ Connection failed.");
            }
            // Do stuff
            Statement stmt = conn.createStatement();
            String insertQuery = "insert into PLAYERS(username, email) values ('Pwnzor55', 'gavan@email.com')";
            stmt.execute(insertQuery); // close connection
        } catch (SQLException e) {
            System.out.println("Something went wrong connecting to the database ...");
            e.printStackTrace();
        }

    }
}
