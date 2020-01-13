package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 11/09/17.
 */

public class OrderDetails {

    @SerializedName("CardId")
    @Expose
    private Integer cardId;

    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @SerializedName("DeliveryDate")

    @Expose
    private String deliveryDate;

    public Double getDealDiscount() {
        return dealDiscount;
    }

    public void setDealDiscount(Double dealDiscount) {
        this.dealDiscount = dealDiscount;
    }

    @SerializedName("CookingInstruction")
    @Expose
    private String cookingInstruction;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;

    @SerializedName("DealDiscount")
    @Expose
    private Double dealDiscount;
    @SerializedName("DeliveryFee")
    @Expose
    private Double deliveryFee;
    @SerializedName("PaymentCardNumber")
    @Expose
    private String paymentCardNumber;

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    public String getPaymentCardType() {
        return paymentCardType;
    }

    public void setPaymentCardType(String paymentCardType) {
        this.paymentCardType = paymentCardType;
    }

    @SerializedName("PaymentCardType")

    @Expose
    private String paymentCardType;
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
    private Double discount;
    @SerializedName("IsDelivery")
    @Expose
    private Integer isDelivery;
    @SerializedName("IsOpen")
    @Expose
    private Integer isOpen;
    @SerializedName("KitchenAddress")
    @Expose
    private String kitchenAddress;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderDishes")
    @Expose
    private ArrayList<OrderDish> orderDishes = null;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("PromoCode")
    @Expose
    private String promoCode;
    @SerializedName("SalesPercentage")
    @Expose
    private Double salesPercentage;
    @SerializedName("SalesTax")
    @Expose
    private Double salesTax;
    @SerializedName("ServiceCharges")
    @Expose
    private Double serviceCharges;
    @SerializedName("TipAmount")
    @Expose
    private Double tipAmount;

    public Double getPromoCodeDiscount() {
        return promoCodeDiscount;
    }

    public void setPromoCodeDiscount(Double promoCodeDiscount) {
        this.promoCodeDiscount = promoCodeDiscount;
    }

    @SerializedName("TotalAmount")

    @Expose
    private Double totalAmount;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("PromoCodeDiscount")
    @Expose
    private Double promoCodeDiscount;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getKitchenAddress() {
        return kitchenAddress;
    }

    public void setKitchenAddress(String kitchenAddress) {
        this.kitchenAddress = kitchenAddress;
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

    public ArrayList<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(ArrayList<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Double getSalesPercentage() {
        return salesPercentage;
    }

    public void setSalesPercentage(Double salesPercentage) {
        this.salesPercentage = salesPercentage;
    }

    public Double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Double salesTax) {
        this.salesTax = salesTax;
    }

    public Double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Double getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(Double tipAmount) {
        this.tipAmount = tipAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
