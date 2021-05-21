package com.example.myadministrator.Model;

public class FoodList {
    String pid,image,recipename,quantity,quantitysizeno,quantitysize,energy,carbo,protein,fat,fibre,netcarb;


    public FoodList() {
    }

    public FoodList(String pid, String image, String recipename, String quantity, String quantitysizeno, String quantitysize, String energy, String carbo, String protein, String fat, String fibre, String netcarb) {
        this.pid = pid;
        this.image = image;
        this.recipename = recipename;
        this.quantity = quantity;
        this.quantitysizeno = quantitysizeno;
        this.quantitysize = quantitysize;
        this.energy = energy;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.fibre = fibre;
        this.netcarb = netcarb;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantitysizeno() {
        return quantitysizeno;
    }

    public void setQuantitysizeno(String quantitysizeno) {
        this.quantitysizeno = quantitysizeno;
    }

    public String getQuantitysize() {
        return quantitysize;
    }

    public void setQuantitysize(String quantitysize) {
        this.quantitysize = quantitysize;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFibre() {
        return fibre;
    }

    public void setFibre(String fibre) {
        this.fibre = fibre;
    }

    public String getNetcarb() {
        return netcarb;
    }

    public void setNetcarb(String netcarb) {
        this.netcarb = netcarb;
    }
}
