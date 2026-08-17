package com.inventory.main;

import com.inventory.dao.ProductDAO;
import com.inventory.model.Product;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Akýllý Depo & Stok Yönetim Sistemi Baþlatýlýyor ===\n");

        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();

        System.out.println("--- Veritabanýndaki Güncel Ürünler ---");
        for (Product p : products) {
            System.out.println(p);
        }
    }
}