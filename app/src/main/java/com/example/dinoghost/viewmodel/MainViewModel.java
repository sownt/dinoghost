package com.example.dinoghost.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dinoghost.BR;
import com.example.dinoghost.adapter.CartAdapter;
import com.example.dinoghost.adapter.ProductPagerAdapter;

public class MainViewModel extends BaseObservable {
    private int cart = 0;
    private boolean isLoaded = false;
    private ProductPagerAdapter adapter;

    @Bindable
    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
        notifyPropertyChanged(BR.cart);
    }

    @Bindable
    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
        notifyPropertyChanged(BR.loaded);
    }

    @Bindable
    public ProductPagerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ProductPagerAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    public MainViewModel(int cart, boolean isLoaded, ProductPagerAdapter adapter) {
        this.cart = cart;
        this.isLoaded = isLoaded;
        this.adapter = adapter;
    }
}
