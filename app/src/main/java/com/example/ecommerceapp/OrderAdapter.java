package com.example.ecommerceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvTotalPrice, tvOrderDate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
        }

        public void bind(Order order) {
            tvOrderId.setText("Order ID: " + order.getId());
            tvTotalPrice.setText("Total: $" + String.format("%.2f", order.getTotalPrice()));
            tvOrderDate.setText("Date: " + order.getDate());
        }
    }
}
