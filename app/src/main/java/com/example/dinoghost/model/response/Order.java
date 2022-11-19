package com.example.dinoghost.model.response;

public class Order extends Response {
    public Order(Boolean success, String message, Object data) {
        super(success, message, data);
    }
}
