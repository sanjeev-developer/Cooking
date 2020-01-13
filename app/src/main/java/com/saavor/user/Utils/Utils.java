package com.saavor.user.Utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nariendersingh on 25/10/17.
 */

public class Utils {

    public static final String ORDER_STATUS_CUSTOMER_ADDRESS = "CustomerAddress";
    public static final String ORDER_STATUS_FOOD_ORDER_ID = "FoodOrderId";
    public static final String ORDER_STATUS_IS_REORDER = "IsReOrder";
    public static final String ORDER_STATUS_KITCHEN_NAME = "KitchenName";
    public static final String ORDER_STATUS_ORDER_DATE = "OrderDate";
    public static final String ORDER_STATUS_ORDER_ID = "OrderId";
    public static final String ORDER_STATUS_ORDER_NUMBER = "OrderNumber";
    public static final String ORDER_STATUS_ORDER_STATUS = "OrderStatus";
    public static final String ORDER_STATUS_PROFILE_ID = "ProfileId";
    public static final String ORDER_STATUS_TOTAL_AMOUNT = "TotalAmount";

    public void openPlayStoreToRate(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public String changeDateFormat(String dateStr) {
        //15-Jun-2017
        // String inputPattern = "yyyy-MM-dd";
        String inputPattern = "MMM-dd-yyyy";
        String outputPattern = "MMMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }
    public String changeDateTimeFormat(String dateStr) {
        //Nov-01-2017 02:47 PM
        // String inputPattern = "yyyy-MM-dd";
        String inputPattern = "MMM-dd-yyyy hh:mm a";
        String outputPattern = "MMMM dd, yyyy hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }


    public static String FetchCurrentDate(){
        String date_format="";
        String Date="";
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MM-dd-yyyy hh:mm aa", Locale.ENGLISH);
        Date = simpledateformat.format(calander.getTime());
        Log.e("dateeeee", "" + Date);
        date_format = Date.replace("a.m", "AM").replace("p.m", "PM");
        if (date_format.contains(".")) {
            date_format = date_format.substring(0, date_format.length() - 1);
        }
        return date_format;
    }
}
