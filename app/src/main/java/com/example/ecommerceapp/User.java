package com.example.ecommerceapp;

public class User {
    private int id;
    private String username;
    private String email;
    private String address; // New
    private String phone;   // New

    public User(int id, String username, String email, String address, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address; // New
        this.phone = phone;     // New
    }

    // Getters and Setters for all fields, including address and phone

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address; // New
    }

    public String getPhone() {
        return phone;   // New
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) { // New
        this.address = address; // New
    }

    public void setPhone(String phone) {     // New
        this.phone = phone;     // New
    }
}
