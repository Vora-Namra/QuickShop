package com.example.ecommerceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewProducts;
    private RecyclerView recyclerViewBrands;
    private ProductAdapter productAdapter;
    private BrandAdapter brandAdapter;
    private List<Product> productList;
    private List<Integer> brandImages;
    private DatabaseHelper dbHelper;
    private ViewPager viewPager;
    private DealsAdapter dealsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Product RecyclerView
        recyclerViewProducts = view.findViewById(R.id.recycler_view_products);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Database Helper and fetch products
        dbHelper = new DatabaseHelper(getContext());
        productList = dbHelper.getAllProducts();  // Load products from DB

        // Ensure productList is not null before setting up the adapter
        if (productList != null && !productList.isEmpty()) {
            productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
                @Override
                public void onProductClick(Product product) {
                    // Handle product click, e.g., open ProductDetailFragment
                    openProductDetailFragment(product.getId());
                }
            });
            recyclerViewProducts.setAdapter(productAdapter);
        }

        // Setup for the Deals Slider
        viewPager = view.findViewById(R.id.view_pager_deals);
        dealsAdapter = new DealsAdapter(getDealsImages());
        viewPager.setAdapter(dealsAdapter);

        // Setup for the Brands RecyclerView
        recyclerViewBrands = view.findViewById(R.id.recycler_view_brands);
        recyclerViewBrands.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        brandImages = getBrandImages(); // Load brand images
        brandAdapter = new BrandAdapter(brandImages);
        recyclerViewBrands.setAdapter(brandAdapter);

        return view;
    }

    private void openProductDetailFragment(int productId) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(productId);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Method to return a list of drawable resources for deals
    private List<Integer> getDealsImages() {
        List<Integer> dealsImages = new ArrayList<>();
        dealsImages.add(R.drawable.quickshop);
        dealsImages.add(R.drawable.deal2);
        dealsImages.add(R.drawable.deal2);
        dealsImages.add(R.drawable.deal3);
        dealsImages.add(R.drawable.deal5);
        dealsImages.add(R.drawable.banner1);
        dealsImages.add(R.drawable.banner2);
        return dealsImages;
    }

    // Method to return a list of drawable resources for brands
    private List<Integer> getBrandImages() {
        List<Integer> brandImages = new ArrayList<>();
        brandImages.add(R.drawable.cat1);
        brandImages.add(R.drawable.cat2);
        brandImages.add(R.drawable.cat3);
        brandImages.add(R.drawable.cat4);
        brandImages.add(R.drawable.cat5);
        return brandImages;
    }
}
