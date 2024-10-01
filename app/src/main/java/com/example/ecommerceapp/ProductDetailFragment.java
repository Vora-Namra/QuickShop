package com.example.ecommerceapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {
    private static final String ARG_PRODUCT_ID = "productId";

    private int productId;
    private TextView tvProductName, tvProductDescription, tvProductPrice;
    private ImageView ivProductImage;
    private Button btnAddToCart;
    private DatabaseHelper dbHelper;

    public static ProductDetailFragment newInstance(int productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);


        dbHelper = new DatabaseHelper(getContext());

        if (getArguments() != null) {
            productId = getArguments().getInt(ARG_PRODUCT_ID);
        }

        tvProductName = view.findViewById(R.id.tv_product_name);
        tvProductDescription = view.findViewById(R.id.tv_product_description);
        tvProductPrice = view.findViewById(R.id.tv_product_price);
        ivProductImage = view.findViewById(R.id.iv_product);
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);

        loadProductDetails();

        btnAddToCart.setOnClickListener(v -> addToCart());

        return view;
    }

    private void loadProductDetails() {
        Product product = dbHelper.getProductById(productId);

        if (product != null) {
            tvProductName.setText(product.getName());
            tvProductDescription.setText(product.getDescription());
            tvProductPrice.setText(String.format("$%.2f", product.getPrice()));


            Picasso.get()
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.ic_error_image)
                    .into(ivProductImage);
        }
    }


    private void addToCart() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);


        if (userId != -1) {
            long result = dbHelper.addToCart(userId, productId, 1); // Adding one item to cart

            if (result != -1) {
                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Please log in to add items to cart", Toast.LENGTH_SHORT).show();
        }
    }
}
