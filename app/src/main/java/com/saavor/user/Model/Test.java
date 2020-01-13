package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 14/11/17.
 */

public class Test {


    @SerializedName("ProfileId")
    @Expose
    private Integer profileId;
    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
