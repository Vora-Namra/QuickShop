package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);

        // Set the background color to orange
        findViewById(R.id.splash_background).setBackgroundColor(getResources().getColor(R.color.orange));

        // Display the success message
        textView.setText("Order placed successfully");

        // Redirect to OrderFragment after 3 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            intent.putExtra("fragment", "order");
            startActivity(intent);
            finish(); // Close the splash screen activity
        }, 3000); // 3000 milliseconds delay
    }
}
