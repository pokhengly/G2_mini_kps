package org.example;

import org.example.Controller.DBController;
import org.example.Controller.ProductController;
import org.example.Model.ProductImplement;
import org.example.View.ProductView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DBController.getAllData();
        ProductView view = new ProductView();
        ProductImplement model = new ProductImplement();
        ProductController controller = new ProductController(view, model);
        controller.displayProduct();
    }
}