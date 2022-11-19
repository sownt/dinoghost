package com.example.dinoghost.viewmodel;

import com.example.dinoghost.model.Product;

public class ProductViewModel {
    private String name;
    private String code;
    private String type;
    private int price;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return String.format("Item No. %s", code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return String.format("Item Type: %s", type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return String.format("$%d", price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
