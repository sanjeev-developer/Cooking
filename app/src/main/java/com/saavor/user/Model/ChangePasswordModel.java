package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 16/06/17.
 */

public class ChangePasswordModel {

    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;

    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;


    private String Password;
    private String UserId;
    private String SessionToken;


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSessionToken() {
        return SessionToken;
    }

    public void setSessionToken(String sessionToken) {
        SessionToken = sessionToken;
    }
}
