package com.example.ecommerceapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ecommerceapp.R;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;

public class ImageLoader {

    private static final String TAG = "ImageLoader";

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        if (imageView == null) {
            Log.e(TAG, "ImageView is null.");
            return;
        }

        // Validate and format the URL
        imageUrl = formatUrl(imageUrl);

        // Load the image using Glide with retry logic and error handling
        String finalImageUrl = imageUrl;
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_image) // Placeholder while loading
                        .error(R.drawable.ic_error_image)          // Error image if loading fails
                        .diskCacheStrategy(DiskCacheStrategy.ALL)  // Cache strategy
                )
                .listener(new RequestListener<Drawable>() { // Listener for debugging errors
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e(TAG, "Failed to load image: " + finalImageUrl, e);
                        return false; // Return false to let Glide handle the error image display
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "Image loaded successfully: " + finalImageUrl);
                        return false; // Return false to let Glide handle the image display
                    }
                })
                .timeout(10000) // Increase timeout if network is slow
                .into(imageView)
                .clearOnDetach();
    }

    // Method to validate and format the URL
    private static String formatUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            Log.e(TAG, "Image URL is empty or null.");
            return ""; // Return an empty string if the URL is invalid
        }

        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            Log.e(TAG, "Invalid image URL: " + imageUrl);
        }

        Log.d(TAG, "Formatted URL: " + imageUrl);
        return imageUrl;
    }
}
