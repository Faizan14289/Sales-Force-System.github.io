package com.example.customer.ModelClasses;

public class sales_man {


    String name;
    String cnic;
    String latitude;
    String longitude;
    String phone;
    String password;
public sales_man(){}
    public sales_man(String cnic, String latitude, String longitude){
        this.cnic = cnic;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public sales_man(String name, String cnic, String latitude, String longitude, String phone, String password) {
        this.name = name;
        this.cnic = cnic;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
