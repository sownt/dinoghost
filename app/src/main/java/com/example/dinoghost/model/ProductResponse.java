package com.example.dinoghost.model;

import java.util.List;

public class ProductResponse {
    private List<Product> data;

    public ProductResponse(List<Product> data) {
        this.data = data;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}