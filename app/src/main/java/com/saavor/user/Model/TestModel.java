package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestModel {

    @SerializedName("CompleteAddress")
    @Expose
    private String completeAddress;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("CookingInstruction")
    @Expose
    private String cookingInstruction;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("DeliveryFee")
    @Expose
    private String deliveryFee;
    @SerializedName("DeliveryFrom")
    @Expose
    private String deliveryFrom;
    @SerializedName("TipType")
    @Expose
    private String tipType;

    public String getTipType() {
        return tipType;
    }

    public void setTipType(String tipType) {
        this.tipType = tipType;
    }

    @SerializedName("DeliveryInstruction")

    @Expose
    private String deliveryInstruction;
    @SerializedName("DeliveryTo")
    @Expose
    private String deliveryTo;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("IsDelivery")
    @Expose
    private String isDelivery;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderDishes")
    @Expose
    private String orderDishes;
    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("PromoCode")
    @Expose
    private String promoCode;
    @SerializedName("SalesTax")
    @Expose
    private String salesTax;
    @SerializedName("ServiceCharges")
    @Expose
    private String serviceCharges;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("TipAmount")
    @Expose
    private String tipAmount;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserName")
    @Expose
    private String userName;

    @SerializedName("OrderID")
    @Expose
    private String OrderID;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCookingInstruction() {
        return cookingInstruction;
    }

    public void setCookingInstruction(String cookingInstruction) {
        this.cookingInstruction = cookingInstruction;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryFrom() {
        return deliveryFrom;
    }

    public void setDeliveryFrom(String deliveryFrom) {
        this.deliveryFrom = deliveryFrom;
    }

    public String getDeliveryInstruction() {
        return deliveryInstruction;
    }

    public void setDeliveryInstruction(String deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
    }

    public String getDeliveryTo() {
        return deliveryTo;
    }

    public void setDeliveryTo(String deliveryTo) {
        this.deliveryTo = deliveryTo;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getIsDelivery() {
        return isDelivery;
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

    public String getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(String orderDishes) {
        this.orderDishes = orderDishes;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public String getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(String serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(String tipAmount) {
        this.tipAmount = tipAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
