package com.example.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView tvUsername, tvEmail;
    private EditText etAddress, etPhone;
    private Button btnLogout, btnUpdate;
    private DatabaseHelper dbHelper;
    private ImageView ivEmailSupport, ivCallSupport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new DatabaseHelper(getContext());

        tvUsername = view.findViewById(R.id.tv_username);
        tvEmail = view.findViewById(R.id.tv_email);
        etAddress = view.findViewById(R.id.et_address);
        etPhone = view.findViewById(R.id.et_phone);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnUpdate = view.findViewById(R.id.btn_update);  // New button for call support

        loadUserProfile();

        btnLogout.setOnClickListener(v -> logout());
        btnUpdate.setOnClickListener(v -> updateProfile());
        ivEmailSupport = view.findViewById(R.id.iv_email_support);
        ivCallSupport = view.findViewById(R.id.iv_call_support);

        // Set onClick listeners for the ImageViews
        ivEmailSupport.setOnClickListener(v -> openEmailSupport()); // Open email client
        ivCallSupport.setOnClickListener(v -> openCallSupport());   // Open phone dialer// Open phone dialer

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
                etAddress.setText(user.getAddress());
                etPhone.setText(user.getPhone());
            }
        }
    }

    private void logout() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

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
            Toast.makeText(getContext(),"Profile Updated Successfully",Toast.LENGTH_LONG).show();
        }
    }

    private void openEmailSupport() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:support@ecommerceapp.com")); // Email address
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
        startActivity(Intent.createChooser(emailIntent, "Send email via:"));
    }

    private void openCallSupport() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:+1234567890")); // Support phone number
        startActivity(callIntent);
    }
}
