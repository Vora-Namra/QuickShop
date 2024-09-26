package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // Load either HomeFragment or LoginFragment based on login status
        if (isLoggedIn()) {
            loadFragment(new HomeFragment());
            bottomNavigationView.setVisibility(View.VISIBLE); // Show bottom navigation
        } else {
            loadFragment(new LoginFragment());
            bottomNavigationView.setVisibility(View.GONE); // Hide bottom navigation for login
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                } else if (item.getItemId() == R.id.nav_cart) {
                    selectedFragment = new CartFragment();
                } else if (item.getItemId() == R.id.nav_orders) {
                    selectedFragment = new OrderFragment();
                }

                // Load the selected fragment and ensure the bottom navigation is visible
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    bottomNavigationView.setVisibility(View.VISIBLE); // Show bottom navigation
                }

                return true;
            };

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        // Check if the fragment is LoginFragment or RegisterFragment to hide the bottom navigation
        if (fragment instanceof LoginFragment || fragment instanceof RegisterFragment) {
            bottomNavigationView.setVisibility(View.GONE); // Hide bottom navigation
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE); // Show bottom navigation
        }
    }

    // Method to check if the user is logged in
    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
