package com.example.dinoghost.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type;
    private String code;
    private int price;
    private String image;
    private int quantity = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart(Product product) {
        this(product, 1);
    }

    public Cart(Product product, int quantity) {
        this(product.getName(), product.getType(), product.getCode(), product.getPrice(), product.getImage(), quantity);
    }

    public Cart(String name, String type, String code, int price, String image, int quantity) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public Cart() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return price == cart.price && name.equals(cart.name) && Objects.equals(type, cart.type) && code.equals(cart.code) && Objects.equals(image, cart.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, code, price, image);
    }
}
