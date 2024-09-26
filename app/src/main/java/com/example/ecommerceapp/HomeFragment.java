package com.example.ecommerceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        productList = dbHelper.getAllProducts();  // Load products from DB

        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                // Handle product click, e.g., open ProductDetailFragment
                openProductDetailFragment(product.getId());
            }
        });

        recyclerView.setAdapter(productAdapter);

        return view;
    }

    private void openProductDetailFragment(int productId) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(productId);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
