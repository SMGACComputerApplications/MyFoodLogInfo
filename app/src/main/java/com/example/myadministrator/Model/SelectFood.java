package com.example.myadministrator.Model;

public class SelectFood {
    String carbocall,energycall,fatcall,fibrecall,foodimage,netcall,proteincall,quantity,recipename,sizeof,quantitysize;

    public String getQuantitysize() {
        return quantitysize;
    }

    public void setQuantitysize(String quantitysize) {
        this.quantitysize = quantitysize;
    }

    public SelectFood(String carbocall, String energycall, String fatcall, String fibrecall, String foodimage, String netcall, String proteincall, String quantity, String recipename, String sizeof, String quantitysize) {

        this.carbocall = carbocall;
        this.energycall = energycall;
        this.fatcall = fatcall;
        this.fibrecall = fibrecall;
        this.foodimage = foodimage;
        this.netcall = netcall;
        this.proteincall = proteincall;
        this.quantity = quantity;
        this.recipename = recipename;
        this.sizeof = sizeof;
    }

    public SelectFood() {
    }

    public String getCarbocall() {
        return carbocall;
    }

    public void setCarbocall(String carbocall) {
        this.carbocall = carbocall;
    }

    public String getEnergycall() {
        return energycall;
    }

    public void setEnergycall(String energycall) {
        this.energycall = energycall;
    }

    public String getFatcall() {
        return fatcall;
    }

    public void setFatcall(String fatcall) {
        this.fatcall = fatcall;
    }

    public String getFibrecall() {
        return fibrecall;
    }

    public void setFibrecall(String fibrecall) {
        this.fibrecall = fibrecall;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getNetcall() {
        return netcall;
    }

    public void setNetcall(String netcall) {
        this.netcall = netcall;
    }

    public String getProteincall() {
        return proteincall;
    }

    public void setProteincall(String proteincall) {
        this.proteincall = proteincall;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getSizeof() {
        return sizeof;
    }

    public void setSizeof(String sizeof) {
        this.sizeof = sizeof;
    }
}
