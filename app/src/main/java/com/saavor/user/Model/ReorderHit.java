package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 14/11/17.
 */

public class ReorderHit {

    @SerializedName("CompleteAddress")
    @Expose
    private String completeAddress;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("DeliveryFrom")
    @Expose
    private String deliveryFrom;
    @SerializedName("DeliveryTo")
    @Expose
    private String deliveryTo;
    @SerializedName("FoodOrderId")
    @Expose
    private String foodOrderId;
    @SerializedName("IsDelivery")
    @Expose
    private String isDelivery;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("UserId")
    @Expose
    private String userId;

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(String foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;

    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

}
