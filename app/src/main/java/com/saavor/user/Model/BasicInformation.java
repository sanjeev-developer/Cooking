package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 02/05/17.
 */

public class BasicInformation {

    @SerializedName("UserId")
    @Expose
    private String userId;

    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;

    private String FirstName;
    private String DateOfBirth;
    private String LastName;
    private String MI;
    private String MobileNumber;
    private String ModifyDate;
    private String facebookid;

    public String getUserLatitude() {
        return UserLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        UserLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return UserLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        UserLongitude = userLongitude;
    }

    public String getUserCityName() {
        return UserCityName;
    }

    public void setUserCityName(String userCityName) {
        UserCityName = userCityName;
    }

    private String UserLatitude;
    private String UserLongitude;
    private String UserCityName;


    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getGoogleid() {
        return googleid;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }

    private String googleid;


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public int getAdt() {
        return adt;
    }

    public void setAdt(int adt) {
        this.adt = adt;
    }

    public double getPreprationtime() {
        return preprationtime;
    }

    public void setPreprationtime(double preprationtime) {
        this.preprationtime = preprationtime;
    }

    private String Refferal;
    private String Email;
    private String coustmerid;
    private String datetime;
    private String deliverytime;
    private int adt;
    private double preprationtime;

    public String getCoustmerid() {
        return coustmerid;
    }

    public void setCoustmerid(String coustmerid) {
        this.coustmerid = coustmerid;
    }

    public String getRefferal() {
        return Refferal;
    }

    public void setRefferal(String refferal) {
        Refferal = refferal;
    }

    public String getProfileId() {
        return ProfileId;
    }

    public void setProfileId(String profileId) {
        ProfileId = profileId;
    }

    private String userprofile;
    private String password;
    private String ProfileId;

    public String getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(String userprofile) {
        this.userprofile = userprofile;}

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {return userId;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
