package com.example.dinoghost.model;

public class Product {
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
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
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

    public Product(String name, String code, String type, int price, String image) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.price = price;
        this.image = image;
    }
}
