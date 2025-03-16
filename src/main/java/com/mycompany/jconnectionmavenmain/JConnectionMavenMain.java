/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.jconnectionmavenmain;

import java.util.Optional;

/**
 *
 * @author gavan
 */
public class JConnectionMavenMain {

    public static void main(String[] args) {

        // Create new DbManager instance.
        DbManager dbMgr = new DbManager();

        // Create a new player to add to the database.
        Player player = new Player("joe", "joe@email.com");

        // Add the player to the database.
        dbMgr.create(player); // The 'create' method creates a temporary connection.

        // See if Alice is in the database.
        Optional<Player> optionalPlayer = dbMgr.read("alice@email.com");


        /*
        Update the Alice Player instance and update the database.
        Code fails silently if optionalPlayer is empty, which is fine.
         */
        optionalPlayer.ifPresent(p -> {
            // Update email.
            p.setEmail("alison@email.com");
            // Update name.
            p.setUsername("Alison");

            // Update database.
            dbMgr.update(p);

        });

        // Check for an email that doesn't exist.
        optionalPlayer = dbMgr.read("doozy@email.com");
        if (!optionalPlayer.isPresent()) {
            System.out.println("\nPlayer not found!");
        }

        // Create a new player.
        player = new Player("doozy", "doozy@email.com");

        // Add doozy@email.com
        dbMgr.create(player);
        System.out.println("\nDoozy has been added to database!");

        // Delete doozy@email.com
        int rowsAffected = dbMgr.delete(player);
        // Notify user if item was deleted or not.
        if (rowsAffected > 0) {
            System.out.println("Player " + player.getEmail() + " successfully deleted.");
        } else {
            System.out.println("No item found in database with email " + player.getEmail() + ". Database not updated.");
        }

        // Create player to delete.
        player = new Player("woozy", "woozy@email.com");

        // Delete a player that doesn't exist in the database.
        rowsAffected = dbMgr.delete(player);
        // Notify user if item was deleted or not.
        if (rowsAffected > 0) {
            System.out.println("Player " + player.getEmail() + " successfully deleted.");
        } else {
            System.out.println("No item found in database with email " + player.getEmail() + ". Database not updated.");
        }
    }
}
