package com.example.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView tvTotalPrice;
    private Button btnPlaceOrder;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        dbHelper = new DatabaseHelper(getContext());

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnPlaceOrder = view.findViewById(R.id.btn_place_order);

        loadCartItems();

        btnPlaceOrder.setOnClickListener(v -> placeOrder());

        return view;
    }

    private void loadCartItems() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            List<CartItem> cartItems = dbHelper.getCartItems(userId);
            cartAdapter = new CartAdapter(cartItems);
            recyclerView.setAdapter(cartAdapter);

            updateTotalPrice(cartItems);
        }
    }

    private void updateTotalPrice(List<CartItem> cartItems) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        tvTotalPrice.setText(String.format("Total: $%.2f", total));
    }
    private void placeOrder() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            List<CartItem> cartItems = dbHelper.getCartItems(userId);
            if (!cartItems.isEmpty()) {
                // Create order
                double totalPrice = calculateTotalPrice(cartItems);
                long orderId = dbHelper.createOrder(userId, totalPrice, getCurrentDate());


                for (CartItem item : cartItems) {
                    dbHelper.addOrderItem(orderId, item.getProductId(), item.getQuantity());
                }


                dbHelper.clearCart(userId);


                Intent intent = new Intent(getContext(), SplashScreenActivity.class);
                startActivity(intent);

                Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    private String getCurrentDate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.now().toString();
        } else {

            return java.text.DateFormat.getDateInstance().format(new java.util.Date());
        }
    }



    private void showOrderConfirmation() {
        ((MainActivity) getActivity()).loadFragment(new OrderConfirmationFragment());
    }
}