package com.example.dinoghost.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProductPagerAdapter pagerAdapter;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), new ArrayList<>());
        binding.pager2.setAdapter(pagerAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DinoService service = retrofit.create(DinoService.class);

        Call<ProductResponse> getProductList = service.getProductList();
        getProductList.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                products = response.body().getData();
                pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), getLifecycle(), products);
                binding.pager2.setAdapter(pagerAdapter);
                binding.dotsIndicator.attachTo(binding.pager2);
                setContentView(binding.getRoot());
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

        binding.cart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }
}