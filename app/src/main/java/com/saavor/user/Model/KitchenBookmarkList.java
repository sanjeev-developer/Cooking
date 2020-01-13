package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 17/02/18.
 */

public class KitchenBookmarkList {

    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("AvailableFrom")
    @Expose
    private String availableFrom;
    @SerializedName("AvailableTo")
    @Expose
    private String availableTo;
    @SerializedName("IsOpen")
    @Expose
    private Integer isOpen;
    @SerializedName("KitichenTiming")
    @Expose
    private String kitichenTiming;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("ProfileImagePath")
    @Expose
    private String profileImagePath;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("kitchenName")
    @Expose
    private String kitchenName;

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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getKitichenTiming() {
        return kitichenTiming;
    }

    public void setKitichenTiming(String kitichenTiming) {
        this.kitichenTiming = kitichenTiming;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
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

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

}
