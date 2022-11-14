package com.example.dinoghost.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dinoghost.model.Cart;

@Database(entities = Cart.class, version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
