package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.R;
import com.example.dinoghost.adapter.ProductPagerAdapter;
import com.example.dinoghost.api.DinoService;
import com.example.dinoghost.data.Repository;
import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.databinding.ActivityMainBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.model.Product;
import com.example.dinoghost.model.ProductResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProductPagerAdapter pagerAdapter;
    private List<Product> products;
    private List<Cart> cartList;
    private Disposable disposable;
    private CartDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setBinding();
        getData();
        setListener();
    }

    private void setBinding() {
        pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), products);
        binding.pager2.setAdapter(pagerAdapter);
        binding.dotsIndicator.attachTo(binding.pager2);
    }

    private void getData() {
        // Get products from API
        DinoService.dino.getObservableProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull ProductResponse productResponse) {
                        products = productResponse.getData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Call API", e.getMessage(), e);
                    }

                    @Override
                    public void onComplete() {
                        pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), products);
                        binding.pager2.setAdapter(pagerAdapter);
                        binding.dotsIndicator.attachTo(binding.pager2);
                        binding.shimmer.stopShimmer();
                        binding.shimmer.setVisibility(View.INVISIBLE);
                        binding.layoutMain.setVisibility(View.VISIBLE);
                    }
                });

        // Get cart data from Database
        db = Repository.getCartDb(this);
        cartList = db.cartDao().loadCart();
        updateBadgeNumber();
    }

    private void setListener() {
        binding.cart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        binding.button.setOnClickListener(v -> {
            buyItem(binding.pager2.getCurrentItem());
            updateBadgeNumber();
        });
    }

    private void buyItem(int currentItem) {
        if (products == null || products.size() <= currentItem || cartList == null) return;
        Cart newItem = new Cart(products.get(currentItem));
        for (int i = 0; i < cartList.size(); i++) {
            Cart c = cartList.get(i);
            if (c.equals(newItem)) {
                newItem.setId(c.getId());
                newItem.setQuantity(c.getQuantity() + 1);
                cartList.set(i, newItem);
                cartAdded();
                return;
            }
        }
        cartList.add(newItem);
        db.cartDao().insert(newItem);
        cartAdded();
    }

    private void updateBadgeNumber() {
        if (cartList == null || cartList.size() == 0) {
            binding.cartBadge.setVisibility(View.GONE);
            return;
        }
        int cartSize = 0;
        for (Cart c : cartList) cartSize += c.getQuantity();
        binding.cartBadge.setVisibility(View.VISIBLE);
        binding.cartBadge.setText(String.valueOf(cartSize));
    }

    private void cartAdded() {
        View view = getLayoutInflater().inflate(R.layout.toast_notification, findViewById(R.id.toast_root));
        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 64);
        toast.show();
    }

    private void saveData() {
        for (Cart c : cartList) {
            db.cartDao().update(c);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartList = db.cartDao().loadCart();
        updateBadgeNumber();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}