package com.saavor.user.Model;

/**
 * Created by a123456 on 24/04/17.
 */

public class Delivery {

    private String IsDelivery;
    private String IsKitchenDelivery;
    private String FreeDeliveryLimitAmount;
    private String DeliveryCharges;
    private String CreateDate;
    private String KitchenId;
    private String MinOrderAmount;
    private String AvgDeliveryTime;
    private String DeliveryRadius;
    private String ProfileId;

    public String getMinOrderAmount() {
        return MinOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        MinOrderAmount = minOrderAmount;
    }

    public String getAvgDeliveryTime() {
        return AvgDeliveryTime;
    }

    public void setAvgDeliveryTime(String avgDeliveryTime) {
        AvgDeliveryTime = avgDeliveryTime;
    }

    public String getFreeDeliveryLimitAmount() {
        return FreeDeliveryLimitAmount;
    }

    public void setFreeDeliveryLimitAmount(String freeDeliveryLimitAmount) {
        FreeDeliveryLimitAmount = freeDeliveryLimitAmount;
    }

    public String getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }

    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getKitchenId() {
        return KitchenId;
    }


    public void setKitchenId(String kitchenId) {
        KitchenId = kitchenId;
    }

    public String getIsDelevery() {
        return IsDelivery;
    }

    public void setIsDelevery(String isDelevery) {
        IsDelivery = isDelevery;
    }

    public String getIsKitchenDelevery() {
        return IsKitchenDelivery;
    }

    public void setIsKitchenDelevery(String isKitchenDelevery) {
        IsKitchenDelivery = isKitchenDelevery;
    }

    public String getDeleveryRadius() {
        return DeliveryRadius;
    }

    public void setDeleveryRadius(String deleveryRadius) {
        DeliveryRadius = deleveryRadius;
    }
}
