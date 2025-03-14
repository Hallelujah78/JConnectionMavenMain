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
        Player player = new Player("jim", "jim@email.com");

        // Add the player to the database.
        dbMgr.create(player); // The 'create' method creates a temporary connection.

        // See if Alice is in the database.
        Optional<Player> optionalPlayer = dbMgr.read("alice@email.com");

        // Does the optional contain a player?
        if (optionalPlayer.isPresent()) {
            // Print the email.
            System.out.println(optionalPlayer.get().getEmail());
        }
    }
}
