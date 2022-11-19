package com.example.dinoghost.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dinoghost.model.Cart;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart")
    Single<List<Cart>> select();
    @Query("DELETE FROM cart")
    Completable reset();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Cart cart);
    @Update()
    Completable update(Cart cart);
    @Delete
    Completable delete(Cart cart);
}
