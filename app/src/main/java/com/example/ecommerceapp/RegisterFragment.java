package com.example.ecommerceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {
    private EditText etUsername, etEmail, etPassword, etConfirmPassword, etAddress, etPhone;
    private Button btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(getContext());

        // Get references to the input fields
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        etAddress = view.findViewById(R.id.et_address);  // Added for address input
        etPhone = view.findViewById(R.id.et_phone);      // Added for phone input
        btnRegister = view.findViewById(R.id.btn_register);

        // Set the register button click listener
        btnRegister.setOnClickListener(v -> register());

        return view;
    }

    private void register() {
        // Get the input from the fields
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String address = etAddress.getText().toString().trim(); // Get the address input
        String phone = etPhone.getText().toString().trim();     // Get the phone input

        // Check if any field is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the new user to the database, including address and phone
        long result = dbHelper.addUser(username, email, password, address, phone);

        // Check if the user was successfully added
        if (result != -1) {
            Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
            // Navigate back to LoginFragment after successful registration
            ((MainActivity) getActivity()).loadFragment(new LoginFragment());
        } else {
            Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
