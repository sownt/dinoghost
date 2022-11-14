package com.example.dinoghost.api;

import com.example.dinoghost.Constants;
import com.example.dinoghost.model.OrderRequest;
import com.example.dinoghost.model.OrderResponse;
import com.example.dinoghost.model.Product;
import com.example.dinoghost.model.ProductResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DinoService {
    DinoService dino = new Retrofit.Builder()
            .baseUrl(Constants.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(DinoService.class);

    @GET("/products")
    Observable<ProductResponse> getObservableProduct();

    @POST("/purchase")
    Call<OrderResponse> purchase(@Body OrderRequest request);
}
