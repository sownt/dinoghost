package com.example.dinoghost.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dinoghost.model.Cart;

@Database(entities = Cart.class, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
    public static CartDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, CartDatabase.class, "cart.db").build();
    }
}
