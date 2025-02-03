package com.example.ex02.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex02.Classes.Product;
import com.example.ex02.Classes.ShoppingCart;
import com.example.ex02.R;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private ArrayList<Product> dataSet;

    public CustomeAdapter(ArrayList<Product> dataSet) {
        this.dataSet = dataSet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView productName;
        TextView price;
        ImageView imageView;
        Button btnDecrease;
        Button btnIncrease;
        TextView quantityText;
        Button btnAddToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewShow);
            productName = itemView.findViewById(R.id.textViewProductName);
            price = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageViewProduct);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            quantityText = itemView.findViewById(R.id.textViewQuantity);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {
        Product product = dataSet.get(position);

        holder.productName.setText(product.getName());
        holder.price.setText(product.getPrice() + " ₪");
        holder.imageView.setImageResource(product.getImage());

        holder.quantityText.setText("1");

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.quantityText.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    holder.quantityText.setText(String.valueOf(currentQuantity));
                    updateTotalPrice(holder, product.getPrice(), currentQuantity);
                }
            }
        });

        // Plus button listener
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(holder.quantityText.getText().toString());
                if (currentQuantity < 10) {  // Maximum 10 items
                    currentQuantity++;
                    holder.quantityText.setText(String.valueOf(currentQuantity));
                    updateTotalPrice(holder, product.getPrice(), currentQuantity);
                } else {
                    Toast.makeText(v.getContext(),
                            "Maximum quantity reached",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.quantityText.getText().toString());
                // הוספת המוצר לעגלה
                ShoppingCart.addItem(product, quantity);

                Toast.makeText(v.getContext(),
                        "Added " + quantity + " " + product.getName() + " to cart",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    private void updateTotalPrice(MyViewHolder holder, String basePrice, int quantity) {
        int totalPrice = Integer.parseInt(basePrice) * quantity;
        holder.price.setText(totalPrice + " ₪");
    }
}