/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jconnectionmavenmain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
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

        } catch (SQLIntegrityConstraintViolationException e) {
            // The record you are trying to create already exists.
            System.out.println("\nUnable to create record with email: " + player.getEmail() + ", and name: " + player.getUsername() + ". The email already exists!");

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
                return Optional.empty();
            }

            return Optional.of(player);
        } catch (SQLException e) {
            // Inform user of the error.
            System.out.println("An SQLException occurred when trying to read the player. Email " + email + " does not exist!");
            // Print stack trace - do not use in production.
            e.printStackTrace();

        }
        return Optional.empty();
    }

    /**
     * Inserts a given Player instance into the database. The method creates a
     * temporary database connection. This is not efficient and connection
     * pooling should be used, for example, HikariCP.
     *
     * @param player The Player instance to add to insert in the database.
     */
    public void update(Player player) {

        // Create query string.
        String updateQuery = "update PLAYERS set username = ?, email = ? where id = ?";

        // Get a new connection.
        try (
                Connection conn = getConnection(); // Get a connection.
                // Get the prepstatement.
                 var prepStatement = conn.prepareStatement(updateQuery)) {

            // Set the username.
            prepStatement.setString(1, player.getUsername());
            // Set the email.
            prepStatement.setString(2, player.getEmail());
            // Set the id.
            prepStatement.setInt(3, player.getId());

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
     * Deletes a given Player instance from the database. The method creates a
     * temporary database connection. This is not efficient and connection
     * pooling should be used, for example, HikariCP.
     *
     * @param player The Player instance to delete from the database.
     * @return int The number of rows affected by the delete operation. Returns
     * 0 if nothing was deleted.
     */
    public int delete(Player player) {

        // Create query string.
        String deleteQuery = "delete from PLAYERS where email = ?";

        // Get a new connection.
        try (
                Connection conn = getConnection(); // Get a connection.
                // Get the prepstatement.
                 var prepStatement = conn.prepareStatement(deleteQuery)) {

            // Set the email.
            prepStatement.setString(1, player.getEmail());

            // Execute the create query and see if deletion occurred.
            int rowsAffected = prepStatement.executeUpdate();
            return rowsAffected;

        } catch (SQLException e) {
            // Inform user of the error.
            System.out.println("An SQLException occurred when trying to delete player with email: " + player.getEmail());
            // Print stack trace - do not use in production.
            e.printStackTrace();
        }
        // Return 0 here - since we may not have returned from the try block.
        return 0;
    }

    /**
     * Query the database to retrieve all players.
     *
     *
     * @return a List of Player instances.
     */
    public List<Player> readAll() {

        // Create the query.
        String readAllQuery = "SELECT * FROM PLAYERS";
        // Set player to null.
        List<Player> players = new ArrayList<Player>();

        // Get a new connection.
        try (
                Connection conn = getConnection(); // Get the connection.
                 var prepStatement = conn.prepareStatement(readAllQuery) // Prepare the statement.
                ) {

            // Execute the query.
            ResultSet rs = prepStatement.executeQuery();

            while (rs.next()) {
                // ResultSet is not empty, create a player + add to players list.
                Player player = new Player(rs.getString("username"), rs.getString("email"), rs.getInt("id"));
                players.add(player);
            }
            return players;
        } catch (SQLException e) {
            // Inform user of the error.
            System.out.println("An SQLException occurred when trying to read players from the database.");
            // Print stack trace - do not use in production.
            e.printStackTrace();

        }
        return players;
    }

    /**
     * Query the database to see if a Player exists.
     *
     *
     * @return true if the player exists, false otherwise.
     */
    public boolean existsByEmail(String email) {

        // Create the query.
        String existsQuery = "SELECT COUNT(1) FROM PLAYERS WHERE email = ?";

        // Get a new connection.
        try (
                Connection conn = getConnection(); // Get the connection.
                 var prepStatement = conn.prepareStatement(existsQuery) // Prepare the statement.
                ) {

            // Set the email value.
            prepStatement.setString(1, email);
            // Execute the query.
            ResultSet rs = prepStatement.executeQuery();

            // If the record exists.
            if (rs.next() && rs.getInt(1) > 0) {
                // Return true.
                return true;
            }
            return false;

        } catch (SQLException e) {
            // Inform user of the error.
            System.out.println("An SQLException occurred when trying to read players from the database.");
            // Print stack trace - do not use in production.
            e.printStackTrace();

        }
        return false;
    }

    // get all - done
    // get specific - done
    // update specific - done
    // delete specific - done
    // delete all
}
