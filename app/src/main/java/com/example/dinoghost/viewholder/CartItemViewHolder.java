package com.example.dinoghost.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinoghost.databinding.ItemCartBinding;

public class CartItemViewHolder extends RecyclerView.ViewHolder {
    public ItemCartBinding binding;

    public CartItemViewHolder(@NonNull ItemCartBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
