package com.example.ecommerceapp;

public class User {
    private int id;
    private String username;
    private String email;
    private String password; // Add this field for the four-parameter constructor

    // Constructor for 3 parameters (without password)
    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = null; // Set to null or some default value
    }

    // Constructor for 4 parameters (with password)
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password; // Add a getter for this field
    }
}
