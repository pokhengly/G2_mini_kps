package org.example.Model;

import java.util.ArrayList;

public class ProductImplement {
    public static ArrayList<Product> productionData = new ArrayList<>();
    public ArrayList<Product> productDB = new ArrayList<>();

    public ArrayList<Product> getProductDB() {
        return productDB;
    }

    public void insertToDB(Product product) {
        productDB.add(product);
    }


}
