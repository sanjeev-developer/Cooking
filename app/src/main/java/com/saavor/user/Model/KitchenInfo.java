package com.saavor.user.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KitchenInfo {

    @SerializedName("AvailableFrom")
    @Expose
    private String availableFrom;
    @SerializedName("AvailableTo")
    @Expose
    private String availableTo;
    @SerializedName("AvgDeliveryTime")
    @Expose
    private Integer avgDeliveryTime;
    @SerializedName("CartCount")
    @Expose
    private Integer cartCount;
    @SerializedName("CompleteAddress")
    @Expose
    private String completeAddress;
    @SerializedName("CuisineList")
    @Expose
    private String cuisineList;
    @SerializedName("DealList")
    @Expose
    private ArrayList<DealList> dealList = null;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("DeliveryRadius")
    @Expose
    private Double deliveryRadius;
    @SerializedName("DeliverySlots")
    @Expose
    public ArrayList<DeliverySlot> deliverySlots = null;

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("DropOffFee")
    @Expose
    private Double dropOffFee;
    @SerializedName("FlatRate")
    @Expose
    private Double flatRate;
    @SerializedName("FreeDeliveryLimitAmount")
    @Expose
    private Double freeDeliveryLimitAmount;
    @SerializedName("IsBookMark")
    @Expose
    private Integer isBookMark;
    @SerializedName("IsDelivery")
    @Expose
    private Integer isDelivery;
    @SerializedName("IsKitchenDelivery")
    @Expose
    private Integer isKitchenDelivery;
    @SerializedName("IsOpen")
    @Expose
    private Integer isOpen;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;

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

    @SerializedName("KitchenOpenStatus")
    @Expose

    private String kitchenOpenStatus;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
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

    public Double getPickUpFee() {
        return pickUpFee;
    }

    public void setPickUpFee(Double pickUpFee) {
        this.pickUpFee = pickUpFee;
    }

    public String getUlyxDeliveryFee() {
        return ulyxDeliveryFee;
    }

    public void setUlyxDeliveryFee(String ulyxDeliveryFee) {
        this.ulyxDeliveryFee = ulyxDeliveryFee;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    @SerializedName("KitchenTiming")
    @Expose

    private String kitchenTiming;
    @SerializedName("MinOrderAmount")
    @Expose
    private Double minOrderAmount;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("PickUpFee")
    @Expose
    private Double pickUpFee;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("ServiceList")
    @Expose
    private String serviceList;
    @SerializedName("StarRating")
    @Expose
    private Integer starRating;
    @SerializedName("TotalReviews")
    @Expose
    private Integer totalReviews;
    @SerializedName("UlyxDeliveryFee")
    @Expose
    private String ulyxDeliveryFee;
    @SerializedName("VehicleTypeId")
    @Expose
    private Integer vehicleTypeId;

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public Integer getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Integer avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(String cuisineList) {
        this.cuisineList = cuisineList;
    }

    public ArrayList<DealList> getDealList() {
        return dealList;
    }

    public void setDealList(ArrayList<DealList> dealList) {
        this.dealList = dealList;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Double getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(Double deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public ArrayList<DeliverySlot> getDeliverySlots() {
        return deliverySlots;
    }

    public void setDeliverySlots(ArrayList<DeliverySlot> deliverySlots) {
        this.deliverySlots = deliverySlots;
    }

    public Double getFreeDeliveryLimitAmount() {
        return freeDeliveryLimitAmount;
    }

    public void setFreeDeliveryLimitAmount(Double freeDeliveryLimitAmount) {
        this.freeDeliveryLimitAmount = freeDeliveryLimitAmount;
    }

    public Integer getIsBookMark() {
        return isBookMark;
    }

    public void setIsBookMark(Integer isBookMark) {
        this.isBookMark = isBookMark;
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

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public String getKitchenOpenStatus() {
        return kitchenOpenStatus;
    }

    public void setKitchenOpenStatus(String kitchenOpenStatus) {
        this.kitchenOpenStatus = kitchenOpenStatus;
    }

    public String getKitchenTiming() {
        return kitchenTiming;
    }

    public void setKitchenTiming(String kitchenTiming) {
        this.kitchenTiming = kitchenTiming;
    }

    public Double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(Double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }


}
