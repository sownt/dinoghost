package com.example.dinoghost.data.local;

import com.example.dinoghost.model.Cart;

import java.util.ArrayList;

public class CartDataSource extends ArrayList<Cart> {
    private CartDataSource() {}

    public int getSize() {
        int size = 0;
        for (Cart cart : this) {
            size += cart.getQuantity();
        }
        return size;
    }

    public int getPrice() {
        int price = 0;
        for (Cart cart : this) {
            price += cart.getPrice() * cart.getQuantity();
        }
        return price;
    }

    public static CartDataSource getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        public static final CartDataSource INSTANCE = new CartDataSource();
    }
}
