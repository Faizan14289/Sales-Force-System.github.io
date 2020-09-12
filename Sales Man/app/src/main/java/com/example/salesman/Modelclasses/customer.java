package com.example.salesman.Modelclasses;

public class customer {


    String name;
    String cnic;
    String latitude;
    String longitude;
    String phone;
    String password;
    String address;
public customer(){}
    public customer(String cnic, String latitude, String longitude){
        this.cnic = cnic;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public customer(String name, String cnic, String latitude, String longitude, String phone, String password,String address) {
        this.name = name;
        this.cnic = cnic;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.password = password;
        this.address=address;
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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
