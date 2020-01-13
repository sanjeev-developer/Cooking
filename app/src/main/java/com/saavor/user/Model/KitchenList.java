package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class KitchenList  {

    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("AvgDeliveryTime")
    @Expose
    private Integer avgDeliveryTime;
    @SerializedName("BusinessType")
    @Expose
    private String businessType;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("CuisineList")
    @Expose
    private String cuisineList;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("DeliveryRadius")
    @Expose
    private Double deliveryRadius;
    @SerializedName("FaxNumber")
    @Expose
    private String faxNumber;
    @SerializedName("FreeDeliveryLimitAmount")
    @Expose
    private Double freeDeliveryLimitAmount;
    @SerializedName("IsDeal")
    @Expose
    private Integer isDeal;
    @SerializedName("IsDelivery")
    @Expose
    private Integer isDelivery;
    @SerializedName("IsKitchenDelivery")
    @Expose
    private Integer isKitchenDelivery;
    @SerializedName("KitchenId")
    @Expose
    private Integer kitchenId;
    @SerializedName("KitchenName")
    @Expose
    private String kitchenName;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("MinOrderAmount")
    @Expose
    private Double minOrderAmount;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("ProfileImagePath")
    @Expose
    private String profileImagePath;
    @SerializedName("ServiceList")
    @Expose
    private String serviceList;
    @SerializedName("StarRating")
    @Expose
    private Integer starRating;
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("Zipcode")
    @Expose
    private String zipcode;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Integer getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Integer avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(String cuisineList) {
        this.cuisineList = cuisineList;
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

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Double getFreeDeliveryLimitAmount() {
        return freeDeliveryLimitAmount;
    }

    public void setFreeDeliveryLimitAmount(Double freeDeliveryLimitAmount) {
        this.freeDeliveryLimitAmount = freeDeliveryLimitAmount;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
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

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
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

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    }
