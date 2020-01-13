package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a123456 on 01/09/17.
 */

public class DeliverySlot {

    @SerializedName("AvailableFrom")
    @Expose
    private String availableFrom;
    @SerializedName("AvailableTo")
    @Expose
    private String availableTo;
    @SerializedName("DayId")
    @Expose
    private Integer dayId;

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }
}
