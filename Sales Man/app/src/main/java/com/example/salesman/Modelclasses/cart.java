package com.example.salesman.Modelclasses;

public class cart {
    String medId,medName,medPrice,medQuantity;

    public cart() {
    }

    public cart(String medId, String medName, String medPrice, String medQuantity) {
        this.medId = medId;
        this.medName = medName;
        this.medPrice = medPrice;
        this.medQuantity = medQuantity;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(String medPrice) {
        this.medPrice = medPrice;
    }

    public String getMedQuantity() {
        return medQuantity;
    }

    public void setMedQuantity(String medQuantity) {
        this.medQuantity = medQuantity;
    }
}
