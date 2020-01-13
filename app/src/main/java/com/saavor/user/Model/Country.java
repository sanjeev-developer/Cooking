package com.saavor.user.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by a123456 on 24/01/18.
 */

public class Country {

    @SerializedName("CountryName")
    @Expose
    public String countryName;
    @SerializedName("StateList")
    @Expose
    public ArrayList<StateList> stateList = null;

    public String getCountryName() {
        return countryName;
    }

    public ArrayList<StateList> getStateList() {
        return stateList;
    }

    public void setStateList(ArrayList<StateList> stateList) {
        this.stateList = stateList;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}