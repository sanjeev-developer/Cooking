package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 09/09/17.
 */

public class OrderList {


    @SerializedName("AvgDeliveryTime")
    @Expose
    private Integer avgDeliveryTime;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @SerializedName("RejectReason")
    @Expose
    private String rejectReason;
    @SerializedName("FoodOrderId")
    @Expose
    private Integer foodOrderId;
    @SerializedName("IsReOrder")
    @Expose
    private Integer isReOrder;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getIsReview() {
        return isReview;
    }

    public void setIsReview(Integer isReview) {
        this.isReview = isReview;
    }

    @SerializedName("KitchenName")
    @Expose

    private String kitchenName;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderId")
    @Expose
    private String orderId;
    @SerializedName("IsReview")
    @Expose
    private Integer isReview;

    public Integer getIsUlyxReview() {
        return isUlyxReview;
    }

    public void setIsUlyxReview(Integer isUlyxReview) {
        this.isUlyxReview = isUlyxReview;
    }

    @SerializedName("IsUlyxReview")
    @Expose
    private Integer isUlyxReview;

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    @SerializedName("PreparingTime")

    @Expose
    private String preparingTime;

    public Integer getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Integer avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public Integer getIsKitchenDelivery() {
        return isKitchenDelivery;
    }

    public void setIsKitchenDelivery(Integer isKitchenDelivery) {
        this.isKitchenDelivery = isKitchenDelivery;
    }

    @SerializedName("OrderNumber")

    @Expose
    private String orderNumber;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("TotalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("IsKitchenDelivery")
    @Expose
    private Integer isKitchenDelivery;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(Integer foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    public Integer getIsReOrder() {
        return isReOrder;
    }

    public void setIsReOrder(Integer isReOrder) {
        this.isReOrder = isReOrder;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
