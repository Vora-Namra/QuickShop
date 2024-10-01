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

        imageUrl = formatUrl(imageUrl);

        String finalImageUrl = imageUrl;
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.ic_error_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e(TAG, "Failed to load image: " + finalImageUrl, e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "Image loaded successfully: " + finalImageUrl);
                        return false;
                    }
                })
                .timeout(10000)
                .into(imageView)
                .clearOnDetach();
    }

    private static String formatUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            Log.e(TAG, "Image URL is empty or null.");
            return "";
        }

        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            Log.e(TAG, "Invalid image URL: " + imageUrl);
        }

        Log.d(TAG, "Formatted URL: " + imageUrl);
        return imageUrl;
    }
}
