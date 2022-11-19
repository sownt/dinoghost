package com.example.dinoghost.api;

import com.example.dinoghost.Constants;
import com.example.dinoghost.model.response.Order;
import com.example.dinoghost.model.response.Product;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
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
    Single<Product> getObservableProduct();

    @POST("/product/purchase")
    Single<Order> purchase(@Body com.example.dinoghost.model.request.Order request);
}
