package com.saavor.user.Model;

/**
 * Created by a123456 on 20/06/17.
 */

public class AddModel {

    private String AddressId;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressType;
    private String CityName;
    private String CountryName;
    private String CreateDate;
    private String Instructions;
    private String IsDefault;
    private String Latitude;
    private String Location;
    private String Longitude;
    private String SessionToken;
    private String StateName;
    private String UserId;
    private String ZipCode;

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    private String Locality;

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(String isDefault) {
        IsDefault = isDefault;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
