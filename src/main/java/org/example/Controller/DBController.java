package org.example.Controller;

import org.example.Model.Product;
import org.example.Model.ProductImplement;

import java.sql.*;

public class DBController {
    public static void insertData(String col1, Double col2, int col3) {
        String url = "jdbc:postgresql://localhost:5432/stock";
        String user = "postgres";
        String password = "123";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO product (name, price, qty) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, col1);
                preparedStatement.setDouble(2, col2);
                preparedStatement.setInt(3, col3);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }

    public static void getAllData() {
        String url = "jdbc:postgresql://localhost:5432/stock";
        String user = "postgres";
        String password = "123";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM product";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    float price = resultSet.getFloat("price");
                    int qty = resultSet.getInt("qty");
                    String date = resultSet.getString("date");
                    ProductImplement.productionData.add(new Product(id, name, price, qty, date));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void removeData(int id) {
        String sql = "DELETE FROM product where id = ?";
        String url = "jdbc:postgresql://localhost:5432/stock";
        String user = "postgres";
        String password = "123";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void updateData(int id, String name, double price, int qty) {
        String sql = "UPDATE product SET name = ?, price = ?, qty = ? WHERE id = ?";
        String url = "jdbc:postgresql://localhost:5432/stock";
        String user = "postgres";
        String password = "123";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, qty);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

}
