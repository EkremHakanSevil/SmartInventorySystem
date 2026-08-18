package com.inventory.service;

import com.inventory.dao.ProductDAO;
import com.inventory.dao.StockMovementDAO;
import com.inventory.model.Product;
import com.inventory.model.StockMovement;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private ProductDAO productDAO;
    private StockMovementDAO stockMovementDAO;

    public InventoryService() {
        this.productDAO = new ProductDAO();
        this.stockMovementDAO = new StockMovementDAO();
    }

    public List<Product> listAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> getLowStockAlerts() {
        List<Product> all = productDAO.getAllProducts();
        List<Product> lowStock = new ArrayList<>();

        for (Product p : all) {
            if (p.getStockQuantity() <= p.getMinThreshold()) {
                lowStock.add(p);
            }
        }
        return lowStock;
    }

    public List<Product> search(String keyword) {
        return productDAO.searchProducts(keyword);
    }

    public List<StockMovement> getMovementHistory() {
        return stockMovementDAO.getAllMovements();
    }
}