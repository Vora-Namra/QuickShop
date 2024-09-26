package com.example.ecommerceapp;  // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Duration for splash screen (3 seconds)
    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Set the content view to your splash screen layout

        // Handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Finish the splash activity so it's removed from the back stack
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
