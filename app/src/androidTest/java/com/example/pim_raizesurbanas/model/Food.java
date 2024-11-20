package com.example.pim_raizesurbanas.model;

public class Food {
    int imgFood;
    String foodNome;
    String foodDescription;
    String price;

    public int getImgFood() {
        return imgFood;
    }

    public void setImgFood(int imgFood) {
        this.imgFood = imgFood;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodNome() {
        return foodNome;
    }

    public void setFoodNome(String foodNome) {
        this.foodNome = foodNome;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
