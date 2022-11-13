package com.example.dinoghost.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.Constants;
import com.example.dinoghost.R;
import com.example.dinoghost.adapter.ProductPagerAdapter;
import com.example.dinoghost.api.DinoService;
import com.example.dinoghost.databinding.ActivityMainBinding;
import com.example.dinoghost.model.Product;
import com.example.dinoghost.model.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProductPagerAdapter pagerAdapter;
    private List<Product> products;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), products);
        binding.pager2.setAdapter(pagerAdapter);
        binding.dotsIndicator.attachTo(binding.pager2);
        setContentView(binding.getRoot());

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
                                    }
                                });

        binding.cart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}