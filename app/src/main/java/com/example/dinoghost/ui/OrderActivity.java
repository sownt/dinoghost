package com.example.dinoghost.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.api.DinoService;
import com.example.dinoghost.data.local.CartDataSource;
import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.databinding.ActivityOrderBinding;
import com.example.dinoghost.model.request.Order;
import com.example.dinoghost.viewmodel.OrderViewModel;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {
    private ActivityOrderBinding binding;
    private OrderViewModel viewModel;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        boolean status = bundle.getBoolean("success", false);

        viewModel = new OrderViewModel(status);
        binding.setViewModel(viewModel);

        binding.actionButton.setOnClickListener(v -> {
            if (status) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CartDataSource.getInstance().clear();
                CartDatabase.getInstance(this.getApplicationContext()).cartDao().reset()
                                .subscribeOn(Schedulers.io())
                                        .subscribe();
                startActivity(intent);
            } else {
                onBackPressed();
//                Order request = new Gson().fromJson(bundle.getString("request"), Order.class);
//                binding.actionButton.setEnabled(false);
//                DinoService.dino.purchase(request)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new SingleObserver<com.example.dinoghost.model.response.Order>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//                                disposable = d;
//                            }
//
//                            @Override
//                            public void onSuccess(com.example.dinoghost.model.response.@NonNull Order order) {
//                                if (order.getSuccess()) {
//                                    viewModel.setStatus(true);
//                                }
//                                binding.actionButton.setEnabled(true);
//                                disposable.dispose();
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//                                binding.actionButton.setEnabled(true);
//                                disposable.dispose();
//                            }
//                        });
            }
        });
    }
}
