package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 09/09/17.
 */

public class PlaceOrder {

    @SerializedName("CardId")
    @Expose
    private String cardId;
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
    @SerializedName("CustomDishes")
    @Expose
    private String customDishes;
    @SerializedName("TipType")
    @Expose
    private String tipType;

    public String getTipType() {
        return tipType;
    }

    public void setTipType(String tipType) {
        this.tipType = tipType;
    }

    @SerializedName("CustomerId")
    @Expose
    private String customerId;
    @SerializedName("DeliveryFee")
    @Expose
    private String deliveryFee;
    @SerializedName("DeliveryFrom")
    @Expose
    private String deliveryFrom;
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
    @SerializedName("SalesTax")
    @Expose
    private String salesTax;
    @SerializedName("SalesTaxPercentage")
    @Expose
    private String salesTaxPercentage;
    @SerializedName("ServiceCharges")
    @Expose
    private String serviceCharges;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("StripeCardId")
    @Expose
    private String stripeCardId;
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
    @SerializedName("DropOffFee")
    @Expose
    private Double dropOffFee;
    @SerializedName("UserState")
    @Expose
    private String userState;
    @SerializedName("SubTotal")
    @Expose
    private Double subTotal;

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getDropOffFee() {
        return dropOffFee;
    }

    public void setDropOffFee(Double dropOffFee) {
        this.dropOffFee = dropOffFee;
    }

    public Double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(Double flatRate) {
        this.flatRate = flatRate;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Double getRouteDistance() {
        return routeDistance;
    }

    public void setRouteDistance(Double routeDistance) {
        this.routeDistance = routeDistance;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    @SerializedName("FlatRate")

    @Expose
    private Double flatRate;
    @SerializedName("RouteDistance")
    @Expose
    private Double routeDistance;
    @SerializedName("UserLatitude")
    @Expose
    private String userLatitude;
    @SerializedName("UserLongitude")
    @Expose
    private String userLongitude;
    @SerializedName("VehicleTypeId")
    @Expose
    private Integer vehicleTypeId;

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @SerializedName("PromoCode")
    @Expose
    private String promoCode;
    @SerializedName("PromoCodeDiscount")
    @Expose
    private String promoCodeDiscount;
    @SerializedName("DeliveryTime")
    @Expose
    private String deliveryTime;

    public String getDealDiscount() {
        return dealDiscount;
    }

    public void setDealDiscount(String dealDiscount) {
        this.dealDiscount = dealDiscount;
    }

    @SerializedName("DealDiscount")
    @Expose

    private String dealDiscount;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public String getCustomDishes() {
        return customDishes;
    }

    public void setCustomDishes(String customDishes) {
        this.customDishes = customDishes;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getPromoCodeDiscount() {
        return promoCodeDiscount;
    }

    public void setPromoCodeDiscount(String promoCodeDiscount) {
        this.promoCodeDiscount = promoCodeDiscount;
    }

    public String getSalesTaxPercentage() {
        return salesTaxPercentage;
    }

    public void setSalesTaxPercentage(String salesTaxPercentage) {
        this.salesTaxPercentage = salesTaxPercentage;
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

    public String getStripeCardId() {
        return stripeCardId;
    }

    public void setStripeCardId(String stripeCardId) {
        this.stripeCardId = stripeCardId;
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
