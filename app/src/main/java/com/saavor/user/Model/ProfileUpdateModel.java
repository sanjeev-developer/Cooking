package com.saavor.user.Model;

/**
 * Created by a123456 on 19/06/17.
 */

public class ProfileUpdateModel
{
    private String FirstName;
    private String LastName;
    private String MI;
    private String MobileNumber;
    private String ModifyDate;
    private String DateOfBirth;
    private String ImageBase64String;
    private String Latitude;
    private String Longitude;
    private String SessionToken;
    private String UserId;


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMI() {
        return MI;
    }

    public void setMI(String MI) {
        this.MI = MI;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getImageBase64String() {
        return ImageBase64String;
    }

    public void setImageBase64String(String imageBase64String) {
        ImageBase64String = imageBase64String;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
