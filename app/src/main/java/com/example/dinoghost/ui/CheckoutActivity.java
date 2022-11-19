package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.R;
import com.example.dinoghost.api.DinoService;
import com.example.dinoghost.data.local.CartDataSource;
import com.example.dinoghost.databinding.ActivityCheckoutBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.model.request.Order;
import com.example.dinoghost.viewmodel.CheckoutViewModel;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CheckoutActivity extends AppCompatActivity {
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        viewModel = new CheckoutViewModel(CartDataSource.getInstance(), 0);
        binding.setViewModel(viewModel);

        Bundle bundle = getIntent().getExtras();
        List<Cart> cartList = CartDataSource.getInstance();

        Order.Product[] products = new Order.Product[cartList.size()];
        for (int i = 0; i < products.length; i++) {
            products[i] = new Order.Product(cartList.get(i).getCode(), cartList.get(i).getQuantity());
        }

        Order.Shipping shipping = new Order.Shipping();
        shipping.setFullName(bundle.getString("fullName"));
        shipping.setEmail(bundle.getString("email"));
        shipping.setPhone(bundle.getString("phone"));
        shipping.setAddress(bundle.getString("address"));
        shipping.setShippingMethod("Regular Delivery");

        binding.shippingMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.shipping_regular) {
                shipping.setShippingMethod("Regular Delivery");
                viewModel.setShippingFee(0);
            } else if (checkedId == R.id.shipping_express) {
                shipping.setShippingMethod("Express Delivery");
                viewModel.setShippingFee(10);
            } else if (checkedId == R.id.shipping_vip) {
                shipping.setShippingMethod("VIP Delivery");
                viewModel.setShippingFee(20);
            }
        });

        binding.finish.setOnClickListener(v -> {
            Order request = new Order(shipping, products);
            Bundle requestBundle = new Bundle();
            requestBundle.putString("request", new Gson().toJson(request));
            Log.d("Body", new Gson().toJson(request));
            DinoService.dino.purchase(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<com.example.dinoghost.model.response.Order>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onSuccess(com.example.dinoghost.model.response.@NonNull Order order) {
                            requestBundle.putBoolean("success", order.getSuccess());
                            navigate(order.getSuccess(), requestBundle);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.e("Order Failed", e.getMessage(), e);
                            requestBundle.putBoolean("success", false);
                            navigate(false, requestBundle);
                        }
                    });
        });
    }

    private void navigate(boolean isSuccess, Bundle request) {
        Intent intent = new Intent(CheckoutActivity.this, OrderActivity.class);
        intent.putExtras(request);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}
