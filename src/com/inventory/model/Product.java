package com.inventory.model;

public class Product {
    private int id;
    private String name;
    private String category;
    private int stockQuantity;
    private int minThreshold;
    private double unitPrice;

    public Product() {}

    public Product(int id, String name, String category, int stockQuantity, int minThreshold, double unitPrice) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.minThreshold = minThreshold;
        this.unitPrice = unitPrice;
    }

    public Product(String name, String category, int stockQuantity, int minThreshold, double unitPrice) {
        this.name = name;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.minThreshold = minThreshold;
        this.unitPrice = unitPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public int getMinThreshold() { return minThreshold; }
    public void setMinThreshold(int minThreshold) { this.minThreshold = minThreshold; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    @Override
    public String toString() {
        return "Product [ID=" + id + ", Ýsim=" + name + ", Kategori=" + category + 
               ", Stok=" + stockQuantity + ", Kritik Eþik=" + minThreshold + 
               ", Fiyat=" + unitPrice + " TL]";
    }
}