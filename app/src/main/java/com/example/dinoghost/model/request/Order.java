package com.example.dinoghost.model.request;

public class Order {
    private Shipping shippingInformation;
    private Product[] products;

    public Shipping getShippingInformation() {
        return shippingInformation;
    }

    public void setShippingInformation(Shipping shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public Order() {
    }

    public Order(Shipping shippingInformation, Product[] products) {
        this.shippingInformation = shippingInformation;
        this.products = products;
    }

    public static class Product {
        private String code;
        private int quantity;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Product() {
        }

        public Product(String code, int quantity) {
            this.code = code;
            this.quantity = quantity;
        }
    }
    public static class Shipping {
        private String fullName;
        private String email;
        private String phone;
        private String address;
        private String shippingMethod;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShippingMethod() {
            return shippingMethod;
        }

        public void setShippingMethod(String shippingMethod) {
            this.shippingMethod = shippingMethod;
        }

        public Shipping() {
        }

        public Shipping(String fullName, String email, String phone, String address, String shippingMethod) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.shippingMethod = shippingMethod;
        }
    }
}
