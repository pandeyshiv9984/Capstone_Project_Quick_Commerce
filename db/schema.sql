-- Create the grocery_app database
CREATE DATABASE IF NOT EXISTS grocery_app;

-- Use the newly created database
USE grocery_app;

-- Table to store user information
CREATE TABLE IF NOT EXISTS `USERS` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `password_hash` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(20) UNIQUE,
    `role` ENUM('customer','admin','delivery') DEFAULT 'customer',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table to store categories for grocery items
CREATE TABLE IF NOT EXISTS `CATEGORIES` (
    `category_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT
);

-- Table to store all the grocery items
CREATE TABLE IF NOT EXISTS `ITEMS` (
    `item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `price` DECIMAL(10, 2) NOT NULL,
    `stock` INT NOT NULL DEFAULT 0,
    `image_url` VARCHAR(255),
    `category_id` INT,
    FOREIGN KEY (`category_id`) REFERENCES `CATEGORIES`(`category_id`)
);

-- Table to store user addresses
CREATE TABLE IF NOT EXISTS `ADDRESSES` (
    `address_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `label` VARCHAR(50),
    `full_address` VARCHAR(255) NOT NULL,
    `latitude` DECIMAL(10, 8),
    `longitude` DECIMAL(11, 8),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `USERS`(`user_id`)
);

-- Table to store shopping carts for users
CREATE TABLE IF NOT EXISTS `CARTS` (
    `cart_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `USERS`(`user_id`),
    UNIQUE KEY uq_user_cart (user_id)
);

-- Table to store items within a user's cart
CREATE TABLE IF NOT EXISTS `CART_ITEMS` (
    `cart_item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `cart_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    FOREIGN KEY (`cart_id`) REFERENCES `CARTS`(`cart_id`),
    FOREIGN KEY (`item_id`) REFERENCES `ITEMS`(`item_id`),
    UNIQUE KEY uq_cart_item (cart_id, item_id)
);

-- Table to store coupons or discount codes
CREATE TABLE IF NOT EXISTS `COUPONS` (
    `coupon_id` INT AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(50) UNIQUE NOT NULL,
    `description` VARCHAR(255),
    `discount_percent` DECIMAL(5, 2) NOT NULL,
    `expiry_date` DATE,
    `active` BOOLEAN DEFAULT TRUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table to store user orders
CREATE TABLE IF NOT EXISTS `ORDERS` (
    `order_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `address_id` INT NOT NULL,
    `coupon_id` INT,
    `total_amount` DECIMAL(10, 2) NOT NULL,
    `discount` DECIMAL(10, 2) DEFAULT 0.00,
    `status` ENUM('pending','placed','shipped','delivered','cancelled') DEFAULT 'pending',
    `eta_minutes` INT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `USERS`(`user_id`),
    FOREIGN KEY (`address_id`) REFERENCES `ADDRESSES`(`address_id`),
    FOREIGN KEY (`coupon_id`) REFERENCES `COUPONS`(`coupon_id`)
);

-- Table to store the items included in a specific order
CREATE TABLE IF NOT EXISTS `ORDER_ITEMS` (
    `order_item_id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` INT NOT NULL,
    `item_id` INT NOT NULL,
    `quantity` INT NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES `ORDERS`(`order_id`),
    FOREIGN KEY (`item_id`) REFERENCES `ITEMS`(`item_id`),
    UNIQUE KEY uq_order_item (order_id, item_id)
);
