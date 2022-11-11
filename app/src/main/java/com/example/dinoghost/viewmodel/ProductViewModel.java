package com.example.dinoghost.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dinoghost.BR;
import com.example.dinoghost.model.Product;

public class ProductViewModel extends BaseObservable {
    private String name;
    private String code;
    private String type;
    private int price;
    private String image;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getCode() {
        return String.format("Item No. %s", code);
    }

    public void setCode(String code) {
        this.code = code;
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public String getType() {
        return String.format("Item Type: %s", type);
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getPrice() {
        return String.format("$%d", price);
    }

    public void setPrice(int price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    public ProductViewModel(String name, String code, String type, int price, String image) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    public ProductViewModel(Product product) {
        this(product.getName(), product.getCode(), product.getType(), product.getPrice(), product.getImage());
    }

    public ProductViewModel() {
    }
}
