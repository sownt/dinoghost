package com.example.dinoghost.model.response;

public class Product extends Response<com.example.dinoghost.model.Product[]> {
    public Product(Boolean success, String message, com.example.dinoghost.model.Product[] data) {
        super(success, message, data);
    }
}
