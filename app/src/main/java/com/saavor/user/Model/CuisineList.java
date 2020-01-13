package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 03/08/17.
 */

public class CuisineList {

    @SerializedName("CuisineId")
    @Expose
    public Integer cuisineId;
    @SerializedName("CuisineName")
    @Expose
    public String cuisineName;

    private int status=0;

    public Integer getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Integer cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
