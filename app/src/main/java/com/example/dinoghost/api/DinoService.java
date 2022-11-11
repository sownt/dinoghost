package com.example.dinoghost.api;

import com.example.dinoghost.model.Product;
import com.example.dinoghost.model.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DinoService {
    @GET("/products")
    Call<ProductResponse> getProductList();
}
