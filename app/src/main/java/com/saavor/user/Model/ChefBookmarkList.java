package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 17/02/18.
 */

public class ChefBookmarkList {

    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("AvailableFrom")
    @Expose
    private String availableFrom;
    @SerializedName("AvailableTo")
    @Expose
    private String availableTo;
    @SerializedName("BusinessName")
    @Expose
    private String businessName;
    @SerializedName("ChefId")
    @Expose
    private Integer chefId;
    @SerializedName("ChefTiming")
    @Expose
    private String chefTiming;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("IsOpen")
    @Expose
    private Integer isOpen;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("MI")
    @Expose
    private String mI;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("ProfileImagePath")
    @Expose
    private String profileImagePath;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserType")
    @Expose
    private String userType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getChefId() {
        return chefId;
    }

    public void setChefId(Integer chefId) {
        this.chefId = chefId;
    }

    public String getChefTiming() {
        return chefTiming;
    }

    public void setChefTiming(String chefTiming) {
        this.chefTiming = chefTiming;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMI() {
        return mI;
    }

    public void setMI(String mI) {
        this.mI = mI;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
