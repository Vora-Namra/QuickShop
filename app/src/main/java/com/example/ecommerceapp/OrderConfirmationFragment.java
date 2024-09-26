package com.example.ecommerceapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

public class OrderConfirmationFragment extends Fragment {
    private TextView tvOrderConfirmation;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);

        tvOrderConfirmation = view.findViewById(R.id.tv_order_confirmation);
        tvOrderConfirmation.setText("Your order has been placed successfully!");

        return view;
    }
}
