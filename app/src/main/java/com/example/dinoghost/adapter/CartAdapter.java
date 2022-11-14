package com.example.dinoghost.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.databinding.ActivityCartBinding;
import com.example.dinoghost.databinding.ItemCartBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.viewholder.CartItemViewHolder;
import com.example.dinoghost.viewmodel.CartViewModel;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartItemViewHolder> {
    private List<Cart> cartList;
    private CartDatabase db;
    private CartViewModel viewModel;
    private ActivityCartBinding binding;

    public CartAdapter(CartDatabase db, CartViewModel viewModel, ActivityCartBinding binding) {
        this.db = db;
        this.viewModel = viewModel;
        this.cartList = db.cartDao().loadCart();
        this.binding = binding;
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
        Cart cart = cartList.get(holder.getAdapterPosition());
        holder.binding.productName.setText(cart.getName());
        holder.binding.productType.setText(String.format("#%s", cart.getType()));
        holder.binding.productPrice.setText(String.format(Locale.US, "$%d", cart.getPrice()));
        holder.binding.quantity.setText(String.valueOf(cart.getQuantity()));
        Glide.with(holder.binding.getRoot())
                .load(cart.getImage())
                .into(holder.binding.productImage);

        holder.binding.quantityInc.setOnClickListener(v -> {
            cart.setQuantity(cart.getQuantity() + 1);
            db.cartDao().update(cart);
            holder.binding.quantity.setText(String.valueOf(cart.getQuantity()));
            updateCart();
        });

        holder.binding.quantityDec.setOnClickListener(v -> {
            if (cart.getQuantity() - 1 == 0) {
                cartList.remove(cart);
                db.cartDao().delete(cart);
                notifyItemRemoved(holder.getAdapterPosition());
                updateCart();
                return;
            }
            cart.setQuantity(cart.getQuantity() - 1);
            db.cartDao().update(cart);
            holder.binding.quantity.setText(String.valueOf(cart.getQuantity()));
            updateCart();
        });

        holder.binding.productRemove.setOnClickListener(v -> {
            cartList.remove(cart);
            db.cartDao().delete(cart);
            notifyItemRemoved(holder.getAdapterPosition());
            updateCart();
        });
    }

    private void updateCart() {
        int totalPrice = 0;
        for (Cart c : cartList) totalPrice += c.getPrice() * c.getQuantity();
        viewModel.setTitle(String.format(Locale.US,"Shopping Cart (%d Item)", cartList.size()));
        viewModel.setPrice(totalPrice);
        if (cartList == null || cartList.size() == 0) {
            binding.cartRcv.setVisibility(View.GONE);
            binding.cartEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.cartRcv.setVisibility(View.VISIBLE);
            binding.cartEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cartList == null ? 0 : cartList.size();
    }
}