/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jconnectionmavenmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author gavan
 */
public class DbManager {

    private String url = "jdbc:mysql://localhost:3306/chess";

    private String user = "root";

    private String password = "";

    /**
     * Default constructor for DbManager.
     */
    public DbManager() {
        super();
    }

    /**
     *
     *
     */
    public DbManager(String url) {
        super();
        this.url = url;
    }

    public DbManager(String url, String user) {
        super();
        this.url = url;
        this.user = user;
    }

    public DbManager(String url, String user, String password) {
        super();
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Get a connection.
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Inserts a given Player instance into the database. The method creates a
     * temporary database connection. This is not efficient and connection
     * pooling should be used, for example, HikariCP.
     *
     * @param player The Player instance to add to insert in the database.
     */
    public void create(Player player) {
        // Get a new connection.
        try (Connection conn = getConnection()) {

            // Create query string.
            String createQuery = "insert into PLAYERS(username, email) values (?, ?)";

            // Prepare the statement.
            var prepStatement = conn.prepareStatement(createQuery);
            // Set the username.
            prepStatement.setString(1, player.getUsername());
            // Set the email.
            prepStatement.setString(2, player.getEmail());

            // Execute the create query.
            prepStatement.execute();

        } catch (SQLException e) {
            // Inform user of the error.
            System.out.println("An SQLException occurred when trying to create a new player in the database: " + player.toString());
            // Print stack trace - do not use in production.
            e.printStackTrace();
        }
    }

    /**
     * Query the database
     *
     * @param email
     * @return a Player instance.
     */
    public Optional<Player> read(String email) {

        // Create the query.
        String query = "SELECT * FROM PLAYERS WHERE email = ?";
        // Set player to null.
        Player player = null;

        // Get a new connection.
        try (
                Connection conn = getConnection(); // Get the connection.
                 var prepStatement = conn.prepareStatement(query) // Prepare the statement.
                ) {
            // Set the email string of prepstatement.
            prepStatement.setString(1, email);
            // Execute the query.
            ResultSet rs = prepStatement.executeQuery();

            if (rs.next()) {
                // ResultSet is not empty, create a player.

                player = new Player(rs.getString("username"), rs.getString("email"), rs.getInt("id"));
            } else {
                // ResultSet is empty.
                return Optional.of(player);
            }

            return Optional.of(player);
        } catch (SQLException e) {
            // Inform user of the error.
            System.out.println("An SQLException occurred when trying to create a new player in the database: " + player.toString());
            // Print stack trace - do not use in production.
            e.printStackTrace();

        }
        return Optional.empty();
    }

    // get all
    // get specific
    // update specific
    // delete all
}
