
package com.saavor.user.Model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryStateList {

    @SerializedName("CountryList")
    @Expose
    private ArrayList<CountryList> countryList = null;
    @SerializedName("ReturnCode")
    @Expose
    private String returnCode;
    @SerializedName("ReturnMessage")
    @Expose
    private String returnMessage;

    public ArrayList<CountryList> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<CountryList> countryList) {
        this.countryList = countryList;
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
