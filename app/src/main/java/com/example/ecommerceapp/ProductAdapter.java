package com.example.ecommerceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.onProductClickListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product); // Correctly bind each product data
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private ImageView productImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(v -> {
                if (onProductClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onProductClickListener.onProductClick(productList.get(position));
                    }
                }
            });
        }

        public void bind(Product product) {
            productName.setText(product.getName());
            productPrice.setText(String.format("$%.2f", product.getPrice()));

            // Ensure each product image URL is correctly loaded into the ImageView
            ImageLoader.loadImage(itemView.getContext(), product.getImageUrl(), productImage);
        }
    }
}
