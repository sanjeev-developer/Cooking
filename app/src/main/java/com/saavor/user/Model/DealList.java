package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 01/08/17.
 */

public class DealList {

    @SerializedName("CreateDate")
    @Expose
    private Object createDate;
    @SerializedName("DealDiscount")
    @Expose
    private Double dealDiscount;
    @SerializedName("DealId")
    @Expose
    private Integer dealId;
    @SerializedName("DealStatus")
    @Expose
    private Object dealStatus;
    @SerializedName("DealTitle")
    @Expose
    private String dealTitle;
    @SerializedName("DealType")
    @Expose
    private Object dealType;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DiscountType")
    @Expose
    private String discountType;
    @SerializedName("DishList")
    @Expose
    private Object dishList;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("IsAllDishes")
    @Expose
    private Integer isAllDishes;
    @SerializedName("StartDate")
    @Expose
    private String startDate;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    @SerializedName("MinAmount")
    @Expose
    private Integer minAmount;

    public Integer getProfileId() {
        return ProfileId;
    }

    public void setProfileId(Integer profileId) {
        ProfileId = profileId;
    }

    @SerializedName("ProfileId")
    @Expose

    private Integer ProfileId;

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Double getDealDiscount() {
        return dealDiscount;
    }

    public void setDealDiscount(Double dealDiscount) {
        this.dealDiscount = dealDiscount;
    }

    public Integer getDealId() {
        return dealId;
    }

    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }

    public Object getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Object dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public Object getDealType() {
        return dealType;
    }

    public void setDealType(Object dealType) {
        this.dealType = dealType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Object getDishList() {
        return dishList;
    }

    public void setDishList(Object dishList) {
        this.dishList = dishList;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getIsAllDishes() {
        return isAllDishes;
    }

    public void setIsAllDishes(Integer isAllDishes) {
        this.isAllDishes = isAllDishes;
    }

    public String getStartDate() {
        return startDate;
    }
}
