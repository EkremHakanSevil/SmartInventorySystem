package com.inventory.util;

import java.sql.Connection;
import java.sql.Statement;

public class TableInit {
    public static void main(String[] args) {
        String dropSql = "DROP TABLE IF EXISTS stock_movements CASCADE; " +
                         "DROP TABLE IF EXISTS products CASCADE;";

        String createProducts = "CREATE TABLE products (" +
                                "    product_id SERIAL PRIMARY KEY," +
                                "    product_name VARCHAR(100) NOT NULL," +
                                "    category VARCHAR(50)," +
                                "    stock_quantity INT DEFAULT 0," +
                                "    min_threshold INT DEFAULT 5," +
                                "    unit_price NUMERIC(10, 2)" +
                                ");";

        String createMovements = "CREATE TABLE stock_movements (" +
                                 "    movement_id SERIAL PRIMARY KEY," +
                                 "    product_id INT," +
                                 "    movement_type VARCHAR(10) NOT NULL," +
                                 "    quantity INT NOT NULL," +
                                 "    notes TEXT," +
                                 "    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                 "    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE" +
                                 ");";

        String insertMock = "INSERT INTO products (product_name, category, stock_quantity, min_threshold, unit_price) VALUES " +
                            "('Laptop', 'Elektronik', 15, 5, 25000.00), " +
                            "('Kablosuz Mouse', 'Aksesuar', 4, 10, 450.00);" +
                            "INSERT INTO stock_movements (product_id, movement_type, quantity, notes) VALUES " +
                            "(1, 'IN', 15, 'Ilk stok girisi'), " +
                            "(2, 'IN', 4, 'Ilk stok girisi');";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(dropSql);
            stmt.execute(createProducts);
            stmt.execute(createMovements);
            stmt.execute(insertMock);

            System.out.println("-> PK ve FK iliskili tablolar ile test verileri basariyla sifirlanip olusturuldu!");

        } catch (Exception e) {
            System.err.println("[Hata] Tablolar olusturulamadi: " + e.getMessage());
        }
    }
}