package com.example.ecommerceapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class DealsAdapter extends PagerAdapter {

    private List<Integer> dealsImages;

    public DealsAdapter(List<Integer> dealsImages) {
        this.dealsImages = dealsImages;
    }

    @Override
    public int getCount() {
        return dealsImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_deal, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView = view.findViewById(R.id.image_view_deal);
        imageView.setImageResource(dealsImages.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
