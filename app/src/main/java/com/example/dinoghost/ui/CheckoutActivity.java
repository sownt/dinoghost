package com.example.dinoghost.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dinoghost.Constants;
import com.example.dinoghost.api.DinoService;
import com.example.dinoghost.data.Repository;
import com.example.dinoghost.databinding.ActivityCheckoutBinding;
import com.example.dinoghost.model.Cart;
import com.example.dinoghost.model.OrderRequest;
import com.example.dinoghost.model.OrderResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {
    private ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();

        binding.finish.setOnClickListener(v -> {
            makeOrder();
        });
    }

    private void makeOrder() {
        List<Cart> cartList = Repository.getCartDb(this).cartDao().loadCart();
        List<OrderRequest.Order> orderList = new ArrayList<>();
        for (Cart c : cartList) {
            orderList.add(new OrderRequest.Order(c.getCode(), c.getQuantity()));
        }
        OrderRequest request = new OrderRequest(orderList.toArray(new OrderRequest.Order[0]));

        DinoService service = new Retrofit.Builder()
                .baseUrl(Constants.API_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                                .build()
                                        .create(DinoService.class);

        service.purchase(request).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                Log.d("Purchase", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("Purchase", t.getMessage(), t);
            }
        });
    }
}
