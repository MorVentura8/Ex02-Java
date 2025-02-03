package com.example.ex02.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex02.Classes.CartItem;
import com.example.ex02.Classes.FirebaseAuthManager;
import com.example.ex02.Classes.ShoppingCart;
import com.example.ex02.Classes.Users;
import com.example.ex02.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {


    private TextView totalPriceText;
    private TextView userDetailsText;
    private TextView orderSummaryText;
    private Button btnPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        // Initialize views
        totalPriceText = view.findViewById(R.id.totalPriceText);
        userDetailsText = view.findViewById(R.id.userDetailsText);
        orderSummaryText = view.findViewById(R.id.orderSummaryText);
        btnPayment = view.findViewById(R.id.btnPayment);

        // Display user details
        displayUserDetails();

        // Display order summary
        displayOrderSummary();

        // Set total price
        totalPriceText.setText("Total Price: ₪" + ShoppingCart.getTotalPrice());

        // Handle payment button click
        btnPayment.setOnClickListener(v -> {
            processPayment();
        });

        return view;
    }

    private void displayUserDetails() {
        Users currentUser = FirebaseAuthManager.getCurrentUser();
        if (currentUser != null) {
            String userDetails = "User Details:\n" +
                    "Username: " + currentUser.getUserName() + "\n" +
                    "Phone: " + currentUser.getPhoneNumber();
            userDetailsText.setText(userDetails);
        }
    }

    private void displayOrderSummary() {
        StringBuilder summary = new StringBuilder();
        ArrayList<CartItem> items = ShoppingCart.getItems();

        for (CartItem item : items) {
            summary.append(item.getProduct().getName())
                    .append(" x").append(item.getQuantity())
                    .append(" - ₪").append(item.getTotalPrice())
                    .append("\n");
        }

        orderSummaryText.setText(summary.toString());
    }

    private void processPayment() {
        Users currentUser = FirebaseAuthManager.getCurrentUser();
        if (currentUser != null && !ShoppingCart.getItems().isEmpty()) {
            saveOrderToFirebase(currentUser);
            Toast.makeText(getContext(), "Order processed successfully!", Toast.LENGTH_SHORT).show();
            ShoppingCart.clearCart();
            // תיקון הניווט - חזרה לחנות
            Navigation.findNavController(getView()).navigate(R.id.action_checkoutFragment_to_storeFragment);
        } else {
            Toast.makeText(getContext(), "Error processing order", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOrderToFirebase(Users user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders");
        String orderId = ordersRef.push().getKey();

        if (orderId != null) {
            HashMap<String, Object> orderData = new HashMap<>();
            orderData.put("userId", user.getUserName());
            orderData.put("orderDate", System.currentTimeMillis());
            orderData.put("totalAmount", ShoppingCart.getTotalPrice());
            orderData.put("userPhone", user.getPhoneNumber());

            // Add order items
            ArrayList<Map<String, Object>> itemsList = new ArrayList<>();
            for (CartItem item : ShoppingCart.getItems()) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("productName", item.getProduct().getName());
                itemMap.put("quantity", item.getQuantity());
                itemMap.put("price", item.getProduct().getPrice());
                itemMap.put("totalPrice", item.getTotalPrice());
                itemsList.add(itemMap);
            }
            orderData.put("items", itemsList);

            ordersRef.child(orderId).setValue(orderData);
        }
    }
}