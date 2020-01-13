package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 20/04/17.
 */

public class LoginData {

    private String EmailId;
    private String FacebookId;
    private String GoogleId;
    private String Password;
    private String CreateDate;
    private String FirstName;
    private String LastName;
    @SerializedName("DeviceToken")
    @Expose
    private String deviceToken;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @SerializedName("DeviceType")
    @Expose
    private String deviceType;


    public String getCreateDate() {

        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

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

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }


    public String getFacebookId() {
        return FacebookId;
    }

    public void setFacebookId(String facebookId) {
        FacebookId = facebookId;
    }

    public String getGoogleId() {
        return GoogleId;
    }

    public void setGoogleId(String googleId) {
        GoogleId = googleId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
