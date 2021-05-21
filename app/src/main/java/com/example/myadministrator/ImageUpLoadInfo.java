package com.example.myadministrator;

class ImageUpLoadInfo {

    public String imageURL;
    public String imagerecipe;
    public String imageQunatity,imageUnit,imageEnergy,imageCarbo,imageProtein,imageFat,imageFibre,imageNet;

    public ImageUpLoadInfo()
    {

    }
    public ImageUpLoadInfo(String RecipeName,String ImagePic,String Quantity,String Units,String Energy,String Carbo,String Protein,String Fat,String Fibre,String Net){
        this.imagerecipe=RecipeName;
        this.imageURL=ImagePic;
        this.imageQunatity=Quantity;
        this.imageUnit=Units;
        this.imageEnergy=Energy;
        this.imageCarbo=Carbo;
        this.imageProtein=Protein;
        this.imageFat=Fat;
        this.imageFibre=Fibre;
        imageNet=Net;

    }
    public String getImagerecipe(){
        return imagerecipe;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getImageQunatity(){
        return imageQunatity;
    }

    public String getImageUnit() {
        return imageUnit;
    }

    public String getImageEnergy() {
        return imageEnergy;
    }

    public String getImageCarbo() {
        return imageCarbo;
    }

    public String getImageProtein() {
        return imageProtein;
    }

    public String getImageFat() {
        return imageFat;
    }

    public String getImageFibre() {
        return imageFibre;
    }

    public String getImageNet() {
        return imageNet;
    }
}
