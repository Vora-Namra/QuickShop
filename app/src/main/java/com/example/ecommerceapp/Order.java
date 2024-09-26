package com.example.ecommerceapp;

public class Order {
    private int id;
    private int userId;
    private double totalPrice;
    private String date;

    public Order(int id, int userId, double totalPrice, String date) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }
}
