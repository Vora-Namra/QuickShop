package com.example.ecommerceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView tvUsername, tvEmail;
    private EditText etAddress, etPhone;
    private Button btnLogout, btnUpdate;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new DatabaseHelper(getContext());

        tvUsername = view.findViewById(R.id.tv_username);
        tvEmail = view.findViewById(R.id.tv_email);
        etAddress = view.findViewById(R.id.et_address);
        etPhone = view.findViewById(R.id.et_phone);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnUpdate = view.findViewById(R.id.btn_update); // New button for update

        loadUserProfile();

        btnLogout.setOnClickListener(v -> logout());
        btnUpdate.setOnClickListener(v -> updateProfile());

        return view;
    }

    private void loadUserProfile() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            User user = dbHelper.getUserById(userId);
            if (user != null) {
                tvUsername.setText(user.getUsername());
                tvEmail.setText(user.getEmail());
                etAddress.setText(user.getAddress()); // Load address
                etPhone.setText(user.getPhone());     // Load phone
            }
        }
    }

    private void logout() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Navigate to LoginFragment
        ((MainActivity) getActivity()).loadFragment(new LoginFragment());
    }

    private void updateProfile() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId != -1) {
            String address = etAddress.getText().toString();
            String phone = etPhone.getText().toString();
            User user = dbHelper.getUserById(userId);
            dbHelper.updateUser(userId, user.getUsername(), user.getEmail(), address, phone);
            // Optionally, show a success message
        }
    }
}
