package com.example.dinoghost.model;

public class OrderRequest {
    private Order[] orders;

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public OrderRequest(Order[] orders) {
        this.orders = orders;
    }

    public OrderRequest() {
    }

    public static class Order {
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

        public Order(String code, int quantity) {
            this.code = code;
            this.quantity = quantity;
        }

        public Order() {
        }
    }
}
