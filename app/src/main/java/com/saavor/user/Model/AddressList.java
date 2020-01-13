package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 22/06/17.
 */

public class AddressList {

    @SerializedName("AddressId")
    @Expose
    private Integer addressId;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("AddressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("AddressType")
    @Expose
    private String addressType;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("CountryName")
    @Expose
    private String countryName;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("Instructions")
    @Expose
    private String instructions;
    @SerializedName("IsDefault")
    @Expose
    private Integer isDefault;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Locality")
    @Expose
    private String locality;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("SessionToken")
    @Expose
    private Integer sessionToken;
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @SerializedName("ZipCode")
    @Expose
    private String zipCode;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(Integer sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
