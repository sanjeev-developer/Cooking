
package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("FacebookId")
    @Expose
    private String facebookId;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("GoogleId")
    @Expose
    private String googleId;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("MI")
    @Expose
    private String mI;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("ProfilePicPath")
    @Expose
    private String profilePicPath;
    @SerializedName("ReferralCode")
    @Expose
    private String referralCode;
    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("CustomerId")
    @Expose
    private String customerId;

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getmI() {
        return mI;
    }

    public void setmI(String mI) {
        this.mI = mI;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getMI() {
        return mI;
    }

    public void setMI(String mI) {
        this.mI = mI;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
