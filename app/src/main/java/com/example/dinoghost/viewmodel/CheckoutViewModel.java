package com.example.dinoghost.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dinoghost.BR;
import com.example.dinoghost.data.local.CartDataSource;

import java.util.Locale;

public class CheckoutViewModel extends BaseObservable {
    private CartDataSource dataSource;
    private int shippingFee = 0;
    private int totalPrice = 0;

    @Bindable
    public CartDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(CartDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bindable
    public String getShippingFee() {
        return String.format(Locale.US, "$%d", shippingFee);
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
        this.totalPrice = dataSource.getPrice() + shippingFee;
        notifyPropertyChanged(BR.shippingFee);
        notifyPropertyChanged(BR.totalPrice);
    }

    @Bindable
    public String getPrice() {
        return String.format(Locale.US, "$%d", dataSource.getPrice());
    }

    @Bindable
    public String getTotalPrice() {
        return String.format(Locale.US, "$%d", totalPrice);
    }

    public CheckoutViewModel(CartDataSource dataSource, int shippingFee) {
        this.dataSource = dataSource;
        this.shippingFee = shippingFee;
        this.totalPrice = dataSource.getPrice();
    }
}
