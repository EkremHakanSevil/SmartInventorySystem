-- Smart Inventory System Database Schema

DROP TABLE IF EXISTS stock_movements CASCADE;
DROP TABLE IF EXISTS products CASCADE;

-- Products (Ana Tablo - PK)
CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    stock_quantity INT DEFAULT 0,
    min_threshold INT DEFAULT 5,
    unit_price NUMERIC(10, 2)
);

-- Stock Movements (Ýliþkili Tablo - PK & FK)
CREATE TABLE stock_movements (
    movement_id SERIAL PRIMARY KEY,
    product_id INT,
    movement_type VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    notes TEXT,
    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);