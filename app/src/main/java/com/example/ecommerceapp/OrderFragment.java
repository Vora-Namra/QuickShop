package com.example.ecommerceapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        dbHelper = new DatabaseHelper(getContext());

        recyclerView = view.findViewById(R.id.recycler_view_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadOrders();

        return view;
    }

    private void loadOrders() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            List<Order> orders = dbHelper.getUserOrders(userId);
            orderAdapter = new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);
        }
    }
}