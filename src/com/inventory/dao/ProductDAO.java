package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("stock_quantity"),
                    rs.getInt("min_threshold"),
                    rs.getDouble("unit_price")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Ürünler listelenirken hata: " + e.getMessage());
        }
        return productList;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, category, stock_quantity, min_threshold, unit_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setInt(3, product.getStockQuantity());
            pstmt.setInt(4, product.getMinThreshold());
            pstmt.setDouble(5, product.getUnitPrice());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Ürün eklenirken hata: " + e.getMessage());
            return false;
        }
    }
}