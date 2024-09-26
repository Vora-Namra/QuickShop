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

    // Factory method to create a new instance of this fragment using the provided product ID.
    public static ProductDetailFragment newInstance(int productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId); // Pass the product ID to the fragment
        fragment.setArguments(args);
        return fragment; // Return the fragment with the arguments set
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        // Initialize DatabaseHelper to access product details
        dbHelper = new DatabaseHelper(getContext());

        // Retrieve the product ID passed to the fragment
        if (getArguments() != null) {
            productId = getArguments().getInt(ARG_PRODUCT_ID);
        }

        // Find and initialize the views
        tvProductName = view.findViewById(R.id.tv_product_name);
        tvProductDescription = view.findViewById(R.id.tv_product_description);
        tvProductPrice = view.findViewById(R.id.tv_product_price);
        ivProductImage = view.findViewById(R.id.iv_product_image);
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);

        // Load the product details based on the retrieved product ID
        loadProductDetails();

        // Set an onClickListener to handle adding the product to the cart
        btnAddToCart.setOnClickListener(v -> addToCart());

        return view; // Return the fragment's root view
    }

    // Method to load and display the product details based on the product ID
    private void loadProductDetails() {
        // Retrieve the product details from the database
        Product product = dbHelper.getProductById(productId);

        // Check if the product exists and populate the views with the product's data
        if (product != null) {
            tvProductName.setText(product.getName());
            tvProductDescription.setText(product.getDescription());
            tvProductPrice.setText(String.format("$%.2f", product.getPrice()));

            // Load the product image using the ImageLoader utility class
            ImageLoader.loadImage(getContext(), product.getImageUrl(), ivProductImage);
        }
    }

    // Method to handle adding the product to the cart
    private void addToCart() {
        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        // Check if the user is logged in
        if (userId != -1) {
            // Add the product to the cart for the logged-in user
            long result = dbHelper.addToCart(userId, productId, 1); // Adding one item to cart
            // Display a success or failure message based on the result
            if (result != -1) {
                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Prompt the user to log in if not logged in
            Toast.makeText(getContext(), "Please log in to add items to cart", Toast.LENGTH_SHORT).show();
        }
    }
}
