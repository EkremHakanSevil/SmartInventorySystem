package com.inventory.model;

import java.sql.Timestamp;

public class StockMovement {
    private int movementId;
    private int productId;
    private String movementType; // "IN" veya "OUT"
    private int quantity;
    private String notes;
    private Timestamp movementDate;

    public StockMovement() {}

    public StockMovement(int productId, String movementType, int quantity, String notes) {
        this.productId = productId;
        this.movementType = movementType;
        this.quantity = quantity;
        this.notes = notes;
    }

    public int getMovementId() { return movementId; }
    public void setMovementId(int movementId) { this.movementId = movementId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getMovementDate() { return movementDate; }
    public void setMovementDate(Timestamp movementDate) { this.movementDate = movementDate; }
}