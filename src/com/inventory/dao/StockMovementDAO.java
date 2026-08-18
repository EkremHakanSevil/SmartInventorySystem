package com.inventory.dao;

import com.inventory.model.StockMovement;
import com.inventory.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO {

    public boolean recordStockMovement(int productId, String movementType, int quantity, String notes) {
        String insertMovementSql = "INSERT INTO stock_movements (product_id, movement_type, quantity, notes) VALUES (?, ?, ?, ?)";
        String updateProductSql = movementType.equalsIgnoreCase("IN")
                ? "UPDATE products SET stock_quantity = stock_quantity + ? WHERE product_id = ?"
                : "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Transaction baþlat

            // 1. Hareketi kaydet
            try (PreparedStatement pstmtMove = conn.prepareStatement(insertMovementSql)) {
                pstmtMove.setInt(1, productId);
                pstmtMove.setString(2, movementType.toUpperCase());
                pstmtMove.setInt(3, quantity);
                pstmtMove.setString(4, notes);
                pstmtMove.executeUpdate();
            }

            // 2. Ürün stoðunu güncelle
            int rowsUpdated;
            try (PreparedStatement pstmtProd = conn.prepareStatement(updateProductSql)) {
                pstmtProd.setInt(1, quantity);
                pstmtProd.setInt(2, productId);
                if (movementType.equalsIgnoreCase("OUT")) {
                    pstmtProd.setInt(3, quantity); // Yetersiz stok kontrolü
                }
                rowsUpdated = pstmtProd.executeUpdate();
            }

            if (rowsUpdated > 0) {
                conn.commit(); // Baþarýlýysa onayla
                return true;
            } else {
                conn.rollback(); // Yetersiz stok vb. durumlarda geri al
                System.err.println("[Hata] Yetersiz stok veya gecersiz urun ID!");
                return false;
            }

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("[DAO Hata] Stok hareketi islenemedi: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<StockMovement> getAllMovements() {
        List<StockMovement> list = new ArrayList<>();
        String sql = "SELECT * FROM stock_movements ORDER BY movement_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                StockMovement sm = new StockMovement();
                sm.setMovementId(rs.getInt("movement_id"));
                sm.setProductId(rs.getInt("product_id"));
                sm.setMovementType(rs.getString("movement_type"));
                sm.setQuantity(rs.getInt("quantity"));
                sm.setNotes(rs.getString("notes"));
                sm.setMovementDate(rs.getTimestamp("movement_date"));
                list.add(sm);
            }
        } catch (SQLException e) {
            System.err.println("[DAO Hata] Hareketler listelenemedi: " + e.getMessage());
        }
        return list;
    }
}