package com.example.dinoghost.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dinoghost.databinding.ItemCartBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.viewholder.CartItemViewHolder;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartItemViewHolder> {
    private List<Cart> cartList;
    private Callback callback;

    public CartAdapter(List<Cart> cartList, Callback callback) {
        this.cartList = cartList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemViewHolder(
                ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.binding.productName.setText(cart.getName());
        holder.binding.productType.setText(String.format("#%s", cart.getType()));
        holder.binding.productPrice.setText(String.format(Locale.US, "$%d", cart.getPrice()));
        holder.binding.quantity.setText(String.valueOf(cart.getQuantity()));
        Glide.with(holder.binding.getRoot())
                .load(cart.getImage())
                .into(holder.binding.productImage);

        holder.binding.quantityInc.setOnClickListener(v -> {
            int p = holder.getAdapterPosition();
            cartList.get(p).increaseQuantity();
            callback.notifyItemChanged(p, cartList.get(p), cartList.isEmpty());
            notifyItemChanged(p);
        });

        holder.binding.quantityDec.setOnClickListener(v -> {
            int p = holder.getAdapterPosition();
            Cart currentItem = cartList.get(p);
            if (currentItem.getQuantity() - 1 == 0) {
                cartList.remove(p);
                callback.notifyItemRemoved(p, currentItem, cartList.isEmpty());
                notifyItemRemoved(p);
            } else {
                currentItem.decreaseQuantity();
                callback.notifyItemChanged(p, currentItem, cartList.isEmpty());
                notifyItemChanged(p);
            }
        });

        holder.binding.productRemove.setOnClickListener(v -> {
            int p = holder.getAdapterPosition();
            Cart currentItem = cartList.get(p);
            cartList.remove(p);
            callback.notifyItemRemoved(p, currentItem, cartList.isEmpty());
            notifyItemRemoved(p);
        });
    }

    @Override
    public int getItemCount() {
        return cartList == null ? 0 : cartList.size();
    }

    public interface Callback {
        void notifyItemChanged(int index, Cart item, boolean isEmpty);
        void notifyItemRemoved(int index, Cart item, boolean isEmpty);
    }
}