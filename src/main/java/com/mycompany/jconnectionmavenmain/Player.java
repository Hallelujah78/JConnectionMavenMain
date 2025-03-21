/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jconnectionmavenmain;

/**
 *
 * @author gavan
 */
public class Player {

    private String username;
    private String email;
    private int id;

    /**
     * Default constructor for Player.
     */
    public Player() {
        super();
    }

    /**
     * Constructor for Player.
     *
     * @param username The player's username.
     * @param email The player's email.
     */
    public Player(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }

    public Player(String username, String email, int id) {
        super();
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "username: " + username + ", email: " + email + ", id: " + id;
    }

}
