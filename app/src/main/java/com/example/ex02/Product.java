package com.example.ex02;

public class Product {
    private String name;
    private String price;
    private int id;
    private int quantity;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    // קונסטרקטור עבור מוצר עם כמות
    public Product(String name, String price, int id, Integer quantity) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.quantity = quantity;
    }

    // קונסטרקטור עבור מוצר עם תמונה
    public Product(String name, int image, String price, int id) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.id = id;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

