package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dinoghost.adapter.CartAdapter;
import com.example.dinoghost.data.Repository;
import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.databinding.ActivityCartBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.viewmodel.CartViewModel;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private CartViewModel viewModel;
    private List<Cart> cartList;
    private CartDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        viewModel = new CartViewModel(
                "Shopping Cart (0 Item)",
                0
        );

        db = Repository.getCartDb(this);
        cartList = db.cartDao().loadCart();
        updateCart();

        if (cartList == null || cartList.size() == 0) return;
        int totalPrice = 0;
        for (Cart c : cartList) totalPrice += c.getPrice() * c.getQuantity();
        viewModel.setTitle(String.format(Locale.US,"Shopping Cart (%d Item)", cartList.size()));
        viewModel.setPrice(totalPrice);
        binding.setCart(viewModel);

        binding.cartRcv.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRcv.setAdapter(new CartAdapter(db, viewModel, binding));
        setListener();
    }

    private void setListener() {
        binding.cartDismiss.setOnClickListener(v -> super.onBackPressed());
        binding.cartContinue.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, ShippingActivity.class)));
    }

    private void updateCart() {
        if (cartList == null || cartList.size() == 0) {
            binding.cartRcv.setVisibility(View.GONE);
            binding.cartEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.cartRcv.setVisibility(View.VISIBLE);
            binding.cartEmpty.setVisibility(View.GONE);
        }
    }
}
