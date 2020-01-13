package com.saavor.user.Model;

/**
 * Created by a123456 on 07/09/17.
 */

public class AddOn {

    public String addon_name;
    public double addon_price;
    public int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddon_name() {
        return addon_name;
    }

    public void setAddon_name(String addon_name) {
        this.addon_name = addon_name;
    }

    public double getAddon_price() {
        return addon_price;
    }

    public void setAddon_price(double addon_price) {
        this.addon_price = addon_price;
    }
}
