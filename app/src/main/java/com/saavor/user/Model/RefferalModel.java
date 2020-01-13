package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 16/06/17.
 */

public class RefferalModel {

    private String ReferralCode;
    private String ReferralType;
    private String UserId;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;

    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;


    public String getReferralCode() {
        return ReferralCode;
    }

    public void setReferralCode(String referralCode) {
        ReferralCode = referralCode;
    }

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

    public String getReferralType() {
        return ReferralType;

    }

    public void setReferralType(String referralType) {
        ReferralType = referralType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
