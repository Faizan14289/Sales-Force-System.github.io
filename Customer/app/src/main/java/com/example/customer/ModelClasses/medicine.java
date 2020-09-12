package com.example.customer.ModelClasses;

public class medicine {
    String key;
    String name;
    String price;
    String quantity;

    public medicine() {
    }

    public medicine(String name, String price, String quantity,String key) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
