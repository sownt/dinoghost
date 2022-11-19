package com.example.dinoghost.viewmodel;

import java.util.Locale;

public class CartViewModel {
    private String title;
    private int price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return String.format(Locale.US, "$%d", price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CartViewModel(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
