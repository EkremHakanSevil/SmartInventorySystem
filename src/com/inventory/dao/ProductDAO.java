package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY product_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategory(rs.getString("category"));
                p.setStockQuantity(rs.getInt("stock_quantity"));
                p.setMinThreshold(rs.getInt("min_threshold"));
                p.setUnitPrice(rs.getBigDecimal("unit_price"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Urunler listelenirken hata: " + e.getMessage());
        }
        return list;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (product_name, category, stock_quantity, min_threshold, unit_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getCategory());
            pstmt.setInt(3, product.getStockQuantity());
            pstmt.setInt(4, product.getMinThreshold());
            pstmt.setBigDecimal(5, product.getUnitPrice());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Urun eklenirken hata: " + e.getMessage());
            return false;
        }
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE LOWER(product_name) LIKE ? OR LOWER(category) LIKE ? ORDER BY product_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword.toLowerCase() + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("product_id"));
                    p.setProductName(rs.getString("product_name"));
                    p.setCategory(rs.getString("category"));
                    p.setStockQuantity(rs.getInt("stock_quantity"));
                    p.setMinThreshold(rs.getInt("min_threshold"));
                    p.setUnitPrice(rs.getBigDecimal("unit_price"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Arama islemi basarisiz: " + e.getMessage());
        }
        return list;
    }

    public void loadSampleData() {
        String sql = "INSERT INTO products (product_name, category, stock_quantity, min_threshold, unit_price) VALUES (?, ?, ?, ?, ?)";
        
        Object[][] sampleProducts = {
            {"Asus ROG Zephyrus G16", "Bilgisayar", 15, 5, new java.math.BigDecimal("68500.00")},
            {"Logitech G Pro X Superlight 2", "Aksesuar", 3, 8, new java.math.BigDecimal("5400.00")},
            {"Samsung 990 PRO 2TB SSD", "Donanim", 25, 10, new java.math.BigDecimal("7200.00")},
            {"LG 27GP850-B 2K Monitor", "Monitor", 2, 5, new java.math.BigDecimal("12900.00")},
            {"Corsair RM850x 850W PSU", "Donanim", 10, 4, new java.math.BigDecimal("5950.00")}
        };

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (Object[] p : sampleProducts) {
                pstmt.setString(1, (String) p[0]);
                pstmt.setString(2, (String) p[1]);
                pstmt.setInt(3, (int) p[2]);
                pstmt.setInt(4, (int) p[3]);
                pstmt.setBigDecimal(5, (java.math.BigDecimal) p[4]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("-> 5 adet ornek urun basariyla eklendi!");
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Toplu yukleme basarisiz: " + e.getMessage());
        }
    }
}