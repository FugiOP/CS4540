package com.example.calvin.workout.models;

/**
 * Created by leona on 8/4/2017.
 */

public class userProfile {

    int feet;
    int inches;
    String name;
    String gender;
    //assumed in pounds
    int weight;

    public userProfile(int feet, int inches, String name, String gender, int weight) {
        this.feet = feet;
        this.inches = inches;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
    }



    public int getFeet() {
        return feet;
    }

    public void setFeet(int feet) {
        this.feet = feet;
    }

    public int getInches() {
        return inches;
    }

    public void setInches(int inches) {
        this.inches = inches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
