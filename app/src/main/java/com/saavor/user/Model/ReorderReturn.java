package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 15/11/17.
 */

public class ReorderReturn  {
    @SerializedName("CartInfo")
    @Expose
    private CartInfo cartInfo;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;
    @SerializedName("RouteDistance")
    @Expose
    private Double routeDistance;

    public Double getRouteDistance() {
        return routeDistance;
    }

    public void setRouteDistance(Double routeDistance) {
        this.routeDistance = routeDistance;
    }

    public String getUserCityName() {
        return userCityName;
    }

    public void setUserCityName(String userCityName) {
        this.userCityName = userCityName;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }

    @SerializedName("UserCityName")
    @Expose
    private String userCityName;
    @SerializedName("UserLatitude")
    @Expose
    private Double userLatitude;
    @SerializedName("UserLongitude")
    @Expose
    private Double userLongitude;

    public KitchenInfo getKitchenInfo() {
        return kitchenInfo;
    }

    public void setKitchenInfo(KitchenInfo kitchenInfo) {
        this.kitchenInfo = kitchenInfo;
    }

    @SerializedName("kitchenInfo")
    @Expose
    private KitchenInfo kitchenInfo;

    public CartInfo getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(CartInfo cartInfo) {
        this.cartInfo = cartInfo;
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
}
