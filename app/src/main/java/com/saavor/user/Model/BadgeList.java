package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 20/01/18.
 */

public class BadgeList {

    @SerializedName("BadgeAmountType")
    @Expose
    private String badgeAmountType;
    @SerializedName("BadgeImage")
    @Expose
    private String badgeImage;
    @SerializedName("BadgeName")
    @Expose
    private String badgeName;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("EarnedPoints")
    @Expose
    private Integer earnedPoints;
    @SerializedName("TargetPoints")
    @Expose
    private Integer targetPoints;

    public String getBadgeAmountType() {
        return badgeAmountType;
    }

    public void setBadgeAmountType(String badgeAmountType) {
        this.badgeAmountType = badgeAmountType;
    }

    public String getBadgeImage() {
        return badgeImage;
    }

    public void setBadgeImage(String badgeImage) {
        this.badgeImage = badgeImage;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(Integer earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public Integer getTargetPoints() {
        return targetPoints;
    }

    public void setTargetPoints(Integer targetPoints) {
        this.targetPoints = targetPoints;
    }
}
