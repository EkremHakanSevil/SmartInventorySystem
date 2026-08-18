package com.inventory.model;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private int stockQuantity;
    private int minThreshold;
    private BigDecimal unitPrice;

    // Boþ Constructor (DAO iþlemleri için)
    public Product() {
    }

    // Parametreli Constructor (Yeni ürün ekleme için)
    public Product(String productName, String category, int stockQuantity, int minThreshold, BigDecimal unitPrice) {
        this.productName = productName;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.minThreshold = minThreshold;
        this.unitPrice = unitPrice;
    }

    // Tam Constructor (Tüm alanlarla)
    public Product(int productId, String productName, String category, int stockQuantity, int minThreshold, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.minThreshold = minThreshold;
        this.unitPrice = unitPrice;
    }

    // Getter ve Setter Metotlarý
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}