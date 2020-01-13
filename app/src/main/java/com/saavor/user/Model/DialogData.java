package com.saavor.user.Model;

/**
 * Created by a123456 on 09/10/17.
 */

public class DialogData {

    String Add1;
    String Add2;
    String City;
    String State;
    String deladdress;
    double lat;
    String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    String zip;

    public String getDeladdress() {
        return deladdress;
    }

    public void setDeladdress(String deladdress) {
        this.deladdress = deladdress;
    }

    double longi;
    String type;

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    String Locality;


    public double getLat() {
        return lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLat(double lat) {

        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getAdd1() {
        return Add1;
    }

    public void setAdd1(String add1) {
        Add1 = add1;
    }

    public String getAdd2() {
        return Add2;
    }

    public void setAdd2(String add2) {
        Add2 = add2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
