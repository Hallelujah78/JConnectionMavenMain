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

    }
}
