package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 03/08/17.
 */

public class KitchensDealList {

    @SerializedName("BusinessType")
    @Expose
    private String businessType;
    @SerializedName("DealTitle")
    @Expose
    private String dealTitle;
    @SerializedName("KitchenId")
    @Expose
    private Integer kitchenId;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("ProfileImagePath")
    @Expose
    private String profileImagePath;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
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
}
