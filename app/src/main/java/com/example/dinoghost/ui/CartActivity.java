package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dinoghost.adapter.CartAdapter;
import com.example.dinoghost.data.local.CartDataSource;
import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.databinding.ActivityCartBinding;
import com.example.dinoghost.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity implements CartAdapter.Callback {
    private ActivityCartBinding binding;
    private CartDataSource cartList;
    private List<Disposable> disposableList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        cartList = CartDataSource.getInstance();
        setEmpty(cartList.size() == 0);
        bind();

        binding.cartDismiss.setOnClickListener(v -> super.onBackPressed());
        binding.cartContinue.setOnClickListener(v -> {
            if (!cartList.isEmpty()) startActivity(new Intent(CartActivity.this, ShippingActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Cart cart : cartList) {
            CartDatabase.getInstance(this.getApplicationContext()).cartDao().insert(cart)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }
        for (Disposable d : disposableList) d.dispose();
    }

    private void bind() {
        binding.cartRcv.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRcv.setAdapter(new CartAdapter(cartList, this));
    }

    private void setTitle() {
        binding.cartTitle.setText(String.format(Locale.US,"Shopping Cart (%d Item)", cartList.size()));
    }

    private void setPrice() {
        binding.cartTotal.setText(String.format(Locale.US, "$%d", cartList.getPrice()));
    }

    private void setEmpty(boolean isEmpty) {
        if (isEmpty) {
            binding.cartRcv.setVisibility(View.GONE);
            binding.cartEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.cartRcv.setVisibility(View.VISIBLE);
            binding.cartEmpty.setVisibility(View.GONE);
        }
        setTitle();
        setPrice();
    }

    @Override
    public void notifyItemChanged(int index, Cart item, boolean isEmpty) {
        setEmpty(isEmpty);
    }

    @Override
    public void notifyItemRemoved(int index, Cart item, boolean isEmpty) {
        setEmpty(isEmpty);
        CartDatabase.getInstance(this.getApplicationContext()).cartDao().delete(item)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
