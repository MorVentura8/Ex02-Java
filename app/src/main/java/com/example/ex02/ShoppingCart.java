package com.example.ex02;

import java.util.ArrayList;

public class ShoppingCart {
    private static ArrayList<CartItem> items = new ArrayList<>();

    // הוספת מוצר לעגלה
    public static void addItem(Product product, int quantity) {
        items.add(new CartItem(product, quantity));
    }

    // קבלת כל הפריטים בעגלה
    public static ArrayList<CartItem> getItems() {
        return items;
    }

    // חישוב המחיר הכולל של העגלה
    public static int getTotalPrice() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // ניקוי העגלה
    public static void clearCart() {
        items.clear();
    }
}
