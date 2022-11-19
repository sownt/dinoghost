package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.R;
import com.example.dinoghost.adapter.ProductPagerAdapter;
import com.example.dinoghost.api.DinoService;
import com.example.dinoghost.data.local.CartDataSource;
import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.databinding.ActivityMainBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProductPagerAdapter pagerAdapter;

    private List<Product> productList;
    private CartDataSource cartList;

    private List<Disposable> disposableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        bind();
        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyCartChanged();
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
        pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), productList);
        cartList = CartDataSource.getInstance();
        binding.pager2.setAdapter(pagerAdapter);
        binding.dotsIndicator.attachTo(binding.pager2);
        binding.cart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });
        binding.button.setOnClickListener(v -> {
            addToCart(productList.get(binding.pager2.getCurrentItem()));
        });
    }

    private void fetchData() {
        fetchProducts();
        fetchCart();
    }

    private void fetchProducts() {
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.layoutMain.setVisibility(View.GONE);
        // Get products from API
        DinoService.dino.getObservableProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<com.example.dinoghost.model.response.Product>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onSuccess(com.example.dinoghost.model.response.@NonNull Product product) {
                        productList = Arrays.asList(product.getData());
                        binding.shimmer.setVisibility(View.GONE);
                        binding.layoutMain.setVisibility(View.VISIBLE);
                        binding.pager2.setAdapter(new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), productList));
                        binding.dotsIndicator.attachTo(binding.pager2);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Fetch Products", e.getMessage(), e);
                    }
                });
    }

    private void fetchCart() {
        // Get cart data from Database
        cartList.clear();
        CartDatabase.getInstance(this.getApplicationContext()).cartDao().select()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Cart> carts) {
                        cartList.addAll(carts);
                        notifyCartChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Fetch Cart", e.getMessage(), e);
                    }
                });
    }

    private void addToCart(Product product) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).same(product)) {
                cartList.get(i).increaseQuantity();
                notifyCartChanged();
                showToast();
                return;
            }
        }
        cartList.add(new Cart(product));
        notifyCartChanged();
        showToast();
    }

    private void notifyCartChanged() {
        int size = cartList.getSize();
        if (size == 0) {
            binding.cartBadge.setVisibility(View.GONE);
        } else {
            binding.cartBadge.setVisibility(View.VISIBLE);
            binding.cartBadge.setText(String.valueOf(size));
        }
    }

    private void showToast() {
        View view = getLayoutInflater().inflate(R.layout.toast_notification, findViewById(R.id.toast_root));
        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 128);
        toast.show();
    }
}