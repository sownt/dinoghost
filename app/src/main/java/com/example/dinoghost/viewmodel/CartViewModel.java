package com.example.dinoghost.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dinoghost.BR;

import java.util.Locale;

public class CartViewModel extends BaseObservable {
    private String title;
    private int price;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getPrice() {
        return String.format(Locale.US, "$%d", price);
    }

    public void setPrice(int price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public CartViewModel(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
