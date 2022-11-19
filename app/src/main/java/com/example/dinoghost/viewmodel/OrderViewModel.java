package com.example.dinoghost.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dinoghost.BR;
import com.example.dinoghost.model.request.Order;

public class OrderViewModel extends BaseObservable {
    private boolean status;

    @Bindable
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public OrderViewModel(boolean status) {
        this.status = status;
    }
}
