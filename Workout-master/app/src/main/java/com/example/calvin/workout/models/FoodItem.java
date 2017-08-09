package com.example.calvin.workout.models;

/**
 * Created by leona on 8/8/2017.
 */

public class FoodItem {
    public String item_name;
    public String brand_name;
    public String nf_calories;

    public FoodItem(String item_name, String brand_name, String nf_calories) {
        this.item_name = item_name;
        this.brand_name = brand_name;
        this.nf_calories = nf_calories;
    }

    public String getItem_name() {
        return "Food: "+item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getBrand_name() {
        return "Brand: "+brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getNf_calories() {
        return "calories: "+nf_calories;
    }

    public void setNf_calories(String nf_calories) {
        this.nf_calories = nf_calories;
    }
}