package com.example.myadministrator.Model;

public class separatecalculation {
    String carbo,energy,fat,fibre,net,protein,quantity,image;

    public String getEnergy() {
        return energy;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
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

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public separatecalculation() {
    }

    public separatecalculation(String carbo, String energy, String fat, String fibre, String net, String protein, String quantity, String image) {
        this.carbo = carbo;
        this.energy = energy;
        this.fat = fat;
        this.fibre = fibre;
        this.net = net;
        this.protein = protein;
        this.quantity = quantity;
        this.image = image;
    }
}
