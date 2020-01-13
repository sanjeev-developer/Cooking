package com.saavor.user.Model;

/**
 * Created by user on 19-12-2017.
 */

public class ChefDishList {

    private String DishId;
    private String DishName;
    private String Calories;
    private String Category;
    private String CuisineName;
    private String Description;
    private String Discription;
    private String Ingredeients;
    private String PreparingTime;

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCuisineName() {
        return CuisineName;
    }

    public void setCuisineName(String cuisineName) {
        CuisineName = cuisineName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getIngredeients() {
        return Ingredeients;
    }

    public void setIngredeients(String ingredeients) {
        Ingredeients = ingredeients;
    }

    public String getPreparingTime() {
        return PreparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        PreparingTime = preparingTime;
    }

    public String getDishId() {
        return DishId;
    }

    public void setDishId(String dishId) {
        DishId = dishId;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }
}
