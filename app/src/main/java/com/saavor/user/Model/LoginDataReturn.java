package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 20/04/17.
 */

public class LoginDataReturn {

    @SerializedName("IsSocialAccount")
    @Expose
    private String IsSocialAccount;

    public String getIsSocialAccount() {
        return IsSocialAccount;
    }

    public void setIsSocialAccount(String isSocialAccount) {
        IsSocialAccount = isSocialAccount;
    }

    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("UserInfo")
    @Expose

    private UserInfo userInfo;



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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}