package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 30/08/17.
 */

public class KitchenTodayDish {

    @SerializedName("AvailableQty")
    @Expose
    private Integer availableQty;
    @SerializedName("Calories")
    @Expose
    private String calories;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CuisineName")
    @Expose
    private String cuisineName;
    @SerializedName("CustomDishItems")
    @Expose
    private ArrayList<CustomDishItem> customDishItems = null;
    @SerializedName("Customizable")
    @Expose
    private Integer customizable;
    @SerializedName("DishDesc")
    @Expose
    private String dishDesc;
    @SerializedName("DishId")
    @Expose
    private Integer dishId;
    @SerializedName("DishMenuId")
    @Expose
    private Integer dishMenuId;
    @SerializedName("DishName")
    @Expose
    private String dishName;
    @SerializedName("Ingredeients")
    @Expose
    private String ingredeients;
    @SerializedName("IsFavDish")
    @Expose
    private Integer isFavDish;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;
    @SerializedName("MenuTitle")
    @Expose
    private String menuTitle;
    @SerializedName("OrderCloseTime")
    @Expose
    private String orderCloseTime;

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    @SerializedName("PreparingTime")

    @Expose
    private String preparingTime;
    @SerializedName("Price")
    @Expose
    private float price;
    @SerializedName("SpiceLevel")
    @Expose
    private String spiceLevel;

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    @SerializedName("ProfileId")
    @Expose

    private Integer profileId;
    @SerializedName("ServiceList")
    @Expose
    private String serviceList;

    public Integer getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public ArrayList<CustomDishItem> getCustomDishItems() {
        return customDishItems;
    }

    public void setCustomDishItems(ArrayList<CustomDishItem> customDishItems) {
        this.customDishItems = customDishItems;
    }

    public Integer getCustomizable() {
        return customizable;
    }

    public void setCustomizable(Integer customizable) {
        this.customizable = customizable;
    }

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getDishMenuId() {
        return dishMenuId;
    }

    public void setDishMenuId(Integer dishMenuId) {
        this.dishMenuId = dishMenuId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getIngredeients() {
        return ingredeients;
    }

    public void setIngredeients(String ingredeients) {
        this.ingredeients = ingredeients;
    }

    public Integer getIsFavDish() {
        return isFavDish;
    }

    public void setIsFavDish(Integer isFavDish) {
        this.isFavDish = isFavDish;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(String orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

}
