package org.example.Model;

import java.util.Arrays;

public class Product {
    private static int currentId = 0;
    private int id;
    private String name;

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int currentId) {
        Product.currentId = currentId;
    }

    private double price;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    private int qty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product() {};

    public Product(int id, String name, double price, int qty, String date) {
//        this.id = ++currentId;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.date = date;
        this.id = id;
    }

    public Product(String name, double price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "id: " + this.id +
                " name: " + this.name +
                " price: " + this.price +
                " qty: " + this.qty +
                " imported date: " + Arrays.stream(this.getDate().split(" "))
                .findFirst()
                .orElse("");
    }
}
