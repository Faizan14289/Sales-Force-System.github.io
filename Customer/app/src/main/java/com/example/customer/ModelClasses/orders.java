package com.example.customer.ModelClasses;
public class orders {

    String saleManId;
    String customerId;
    String orderId;
    String price;
    String priceStatus;
    String status;
    String date;
    String time;

    public orders() {
    }

    public orders( String saleManId,String customerId,String orderId, String price, String status, String date, String time) {
        this.customerId= customerId;
        this.saleManId=saleManId;
        this.orderId = orderId;
        this.price = price;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    public String getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        this.priceStatus = priceStatus;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}