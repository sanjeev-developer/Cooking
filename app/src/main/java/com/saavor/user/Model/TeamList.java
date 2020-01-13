package com.saavor.user.Model;

/**
 * Created by user on 18-12-2017.
 */

public class TeamList {

    private String BusinessType;
    private String ChefId;
    private String CuisineList;
    private String Price;
    private String ProfileImagePath;
    private String MinBookingAmount;
    private String UserName;

    public String getMinBookingAmount() {
        return MinBookingAmount;
    }

    public void setMinBookingAmount(String minBookingAmount) {
        MinBookingAmount = minBookingAmount;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getCuisineList() {
        return CuisineList;
    }

    public void setCuisineList(String cuisineList) {
        CuisineList = cuisineList;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProfileImagePath() {
        return ProfileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        ProfileImagePath = profileImagePath;
    }
}
