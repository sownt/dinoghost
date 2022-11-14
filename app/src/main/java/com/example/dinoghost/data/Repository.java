package com.example.dinoghost.data;

import android.content.Context;

import androidx.room.Room;

import com.example.dinoghost.data.local.CartDatabase;
import com.example.dinoghost.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Repository() {}
    public static List<Cart> getCartItems() {
        return CartSingleton.INSTANCE;
    }
    public static CartDatabase getCartDb(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), CartDatabase.class, "cart.db")
                .allowMainThreadQueries()
                .build();
    }
    public static class CartSingleton {
        private static final List<Cart> INSTANCE = new ArrayList<>();
    }
}
