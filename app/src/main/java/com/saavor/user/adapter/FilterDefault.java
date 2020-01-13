package com.saavor.user.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 07/08/17.
 */

public class FilterDefault {

    @SerializedName("CostForOne")
    @Expose
    private String costForOne;
    @SerializedName("CurrentDate")
    @Expose
    private String currentDate;
    @SerializedName("DeliveryFrom")
    @Expose
    private String deliveryFrom;
    @SerializedName("DeliveryTo")
    @Expose
    private String deliveryTo;
    @SerializedName("Distance")
    @Expose
    private String distance;
    @SerializedName("EndIndex")
    @Expose
    private String endIndex;
    @SerializedName("IsBookMarked")
    @Expose
    private String isBookMarked;
    @SerializedName("IsDiscount")
    @Expose
    private String isDiscount;
    @SerializedName("IsVegetarian")
    @Expose
    private String isVegetarian;
    @SerializedName("KitchenType")
    @Expose
    private String kitchenType;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("MinimumOrder")
    @Expose
    private String minimumOrder;
    @SerializedName("SearchText")
    @Expose
    private String searchText;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("StartIndex")
    @Expose
    private String startIndex;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    public String getCostForOne() {
        return costForOne;
    }

    public void setCostForOne(String costForOne) {
        this.costForOne = costForOne;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getDeliveryFrom() {
        return deliveryFrom;
    }

    public void setDeliveryFrom(String deliveryFrom) {
        this.deliveryFrom = deliveryFrom;
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public String getIsBookMarked() {
        return isBookMarked;
    }

    public void setIsBookMarked(String isBookMarked) {
        this.isBookMarked = isBookMarked;
    }

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String getIsVegetarian() {
        return isVegetarian;
    }

    public void setIsVegetarian(String isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(String minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
