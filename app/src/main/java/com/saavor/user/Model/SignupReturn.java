
package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupReturn {


    @SerializedName("IsSocialAccount")
    @Expose
    private String IsSocialAccount;

    public String getIsSocialAccount() {
        return IsSocialAccount;
    }

    public void setIsSocialAccount(String isSocialAccount) {
        IsSocialAccount = isSocialAccount;
    }

    @SerializedName("ReferralCode")
    @Expose

    private String ReferralCode;


    public String getReferralCode() {
        return ReferralCode;
    }

    public void setReferralCode(String referralCode) {
        ReferralCode = referralCode;
    }

    @SerializedName("ReturnCode")
    @Expose

    private String returnCode;

    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    @SerializedName("SessionToken")
    @Expose
    private String sessionToken;

    @SerializedName("UserId")
    @Expose
    private String userId;

    private String ProfilePicPath;

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

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePicPath() {
        return ProfilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        ProfilePicPath = profilePicPath;
    }
}
