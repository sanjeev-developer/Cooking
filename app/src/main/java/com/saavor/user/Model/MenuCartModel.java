package com.saavor.user.Model;

import java.util.ArrayList;

/**
 * Created by a123456 on 06/09/17.
 */

public class MenuCartModel {

    public double totalPrice;
    public double totalitem;

    public ArrayList<CustomizableList> customizableLists ;

    public ArrayList<NonCustomizableList> noncustomizableLists ;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(double totalitem) {
        this.totalitem = totalitem;
    }

    public ArrayList<CustomizableList> getCustomizableLists() {
        return customizableLists;
    }

    public void setCustomizableLists(ArrayList<CustomizableList> customizableLists) {
        this.customizableLists = customizableLists;
    }

    public ArrayList<NonCustomizableList> getNoncustomizableLists() {
        return noncustomizableLists;
    }

    public void setNoncustomizableLists(ArrayList<NonCustomizableList> noncustomizableLists) {
        this.noncustomizableLists = noncustomizableLists;
    }
}
