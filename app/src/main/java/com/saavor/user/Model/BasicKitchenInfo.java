package com.saavor.user.Model;

/**
 * Created by a123456 on 11/09/17.
 */

public class BasicKitchenInfo {

    public String KitchenProileid;
    public String KitchenDelCharge;
    public double kitchenminamount;
    public int isdelivery;
    public int iskitchendelivery;
    public String kitaddress;
    public double FreeDeliveryLimitAmount;
    public double minamountfordis;
    public double kitchen_lat;
    public double kitchen_long;
    public double kitchendelradius;

    public double getKitchendelradius() {
        return kitchendelradius;
    }

    public void setKitchendelradius(double kitchendelradius) {
        this.kitchendelradius = kitchendelradius;
    }

    public double getKitchen_lat() {


        return kitchen_lat;
    }

    public void setKitchen_lat(double kitchen_lat) {
        this.kitchen_lat = kitchen_lat;
    }

    public double getKitchen_long() {
        return kitchen_long;
    }

    public void setKitchen_long(double kitchen_long) {
        this.kitchen_long = kitchen_long;
    }

    public int getIskitchendelivery() {
        return iskitchendelivery;
    }

    public void setIskitchendelivery(int iskitchendelivery) {
        this.iskitchendelivery = iskitchendelivery;
    }

    public Double distance;
    public Double dropOffFee;
    public Double flatRate;
    public Double pickUpFee;
    public Integer vehicleTypeId;
    public String userlat;

    public String getUserlat() {
        return userlat;
    }

    public void setUserlat(String userlat) {
        this.userlat = userlat;
    }

    public String getUserlong() {
        return userlong;
    }

    public void setUserlong(String userlong) {
        this.userlong = userlong;
    }

    public String userlong;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDropOffFee() {
        return dropOffFee;
    }

    public void setDropOffFee(Double dropOffFee) {
        this.dropOffFee = dropOffFee;
    }

    public Double getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(Double flatRate) {
        this.flatRate = flatRate;
    }

    public Double getPickUpFee() {
        return pickUpFee;
    }

    public void setPickUpFee(Double pickUpFee) {
        this.pickUpFee = pickUpFee;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getUlyxDeliveryFee() {
        return ulyxDeliveryFee;
    }

    public void setUlyxDeliveryFee(String ulyxDeliveryFee) {
        this.ulyxDeliveryFee = ulyxDeliveryFee;
    }

    private String ulyxDeliveryFee;

    public double getMinamountfordis() {
        return minamountfordis;
    }

    public void setMinamountfordis(double minamountfordis) {
        this.minamountfordis = minamountfordis;
    }

    public double getFreeDeliveryLimitAmount() {
        return FreeDeliveryLimitAmount;
    }

    public void setFreeDeliveryLimitAmount(double freeDeliveryLimitAmount) {
        FreeDeliveryLimitAmount = freeDeliveryLimitAmount;
    }

    public String getKitaddress() {
        return kitaddress;
    }

    public void setKitaddress(String kitaddress) {
        this.kitaddress = kitaddress;
    }

    public int getIsdelivery() {
        return isdelivery;
    }

    public void setIsdelivery(int isdelivery) {
        this.isdelivery = isdelivery;
    }

    public String getKitchenProileid() {
        return KitchenProileid;
    }

    public void setKitchenProileid(String kitchenProileid) {
        KitchenProileid = kitchenProileid;
    }

    public String getKitchenDelCharge() {
        return KitchenDelCharge;
    }

    public void setKitchenDelCharge(String kitchenDelCharge) {
        KitchenDelCharge = kitchenDelCharge;
    }

    public double getKitchenminamount() {
        return kitchenminamount;
    }

    public void setKitchenminamount(double kitchenminamount) {
        this.kitchenminamount = kitchenminamount;
    }


}
