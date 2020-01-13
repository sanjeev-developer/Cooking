package com.saavor.user.Model;

public class ChefList {

    private String ChefId;
    private String FirstName;
    private String LastName;
    private String MI;
    private String Price;
    private String ProfileImagePath;
    private String StarRating;

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMI() {
        return MI;
    }

    public void setMI(String MI) {
        this.MI = MI;
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

    public String getStarRating() {
        return StarRating;
    }

    public void setStarRating(String starRating) {
        StarRating = starRating;
    }
}
