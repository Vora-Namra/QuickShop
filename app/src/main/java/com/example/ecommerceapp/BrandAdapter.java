package com.example.ecommerceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<Integer> brandImages;

    public BrandAdapter(List<Integer> brandImages) {
        this.brandImages = brandImages;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        holder.brandImageView.setImageResource(brandImages.get(position));
    }

    @Override
    public int getItemCount() {
        return brandImages.size();
    }

    static class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImageView;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImageView = itemView.findViewById(R.id.image_view_brand);
        }
    }
}
