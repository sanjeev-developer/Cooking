package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 09/09/17.
 */

public class CartInfo
{
    @SerializedName("CartId")
    @Expose
    private Integer cartId;
    @SerializedName("CartItems")
    @Expose
    private ArrayList<CartItem> cartItems = null;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("DealDiscountType")
    @Expose
    private String dealDiscountType;
    @SerializedName("DealDiscountValue")
    @Expose
    private Double dealDiscountValue;
    @SerializedName("DealOrderMinAmount")
    @Expose
    private Double dealOrderMinAmount;
    @SerializedName("DeliveryFee")
    @Expose
    private Double deliveryFee;
    @SerializedName("DeliveryFrom")
    @Expose
    private String deliveryFrom;
    @SerializedName("DeliveryTime")
    @Expose
    private String deliveryTime;
    @SerializedName("DeliveryTo")
    @Expose
    private String deliveryTo;
    @SerializedName("Discount")
    @Expose
    private Double discount;
    @SerializedName("FreeDeliveryLimitAmount")
    @Expose
    private Double freeDeliveryLimitAmount;
    @SerializedName("IsDelivery")
    @Expose
    private Integer isDelivery;
    @SerializedName("IsKitchenDelivery")
    @Expose
    private Integer isKitchenDelivery;
    @SerializedName("IsOpen")
    @Expose
    private String isOpen;
    @SerializedName("KitchenAddress")
    @Expose
    private String kitchenAddress;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;
    @SerializedName("MinOrderAmount")
    @Expose
    private Double minOrderAmount;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderID")
    @Expose
    private String orderID;
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("SalesPercentage")
    @Expose
    private Double salesPercentage;
    @SerializedName("SalesTax")
    @Expose
    private Double salesTax;
    @SerializedName("ServiceCharges")
    @Expose
    private Double serviceCharges;
    @SerializedName("TotalAmount")
    @Expose
    private Double totalAmount;
    @SerializedName("UserName")
    @Expose
    private String userName;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDealDiscountType() {
        return dealDiscountType;
    }

    public void setDealDiscountType(String dealDiscountType) {
        this.dealDiscountType = dealDiscountType;
    }

    public Double getDealDiscountValue() {
        return dealDiscountValue;
    }

    public void setDealDiscountValue(Double dealDiscountValue) {
        this.dealDiscountValue = dealDiscountValue;
    }

    public Double getDealOrderMinAmount() {
        return dealOrderMinAmount;
    }

    public void setDealOrderMinAmount(Double dealOrderMinAmount) {
        this.dealOrderMinAmount = dealOrderMinAmount;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public Double getFreeDeliveryLimitAmount() {
        return freeDeliveryLimitAmount;
    }

    public void setFreeDeliveryLimitAmount(Double freeDeliveryLimitAmount) {
        this.freeDeliveryLimitAmount = freeDeliveryLimitAmount;
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    public Integer getIsKitchenDelivery() {
        return isKitchenDelivery;
    }

    public void setIsKitchenDelivery(Integer isKitchenDelivery) {
        this.isKitchenDelivery = isKitchenDelivery;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
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

    public Double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(Double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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
