package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.BookingInsert;
import com.saavor.user.Model.CustomDishHit;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.Model.GetCompanyProfile;
import com.saavor.user.Model.KitchenInfoHit;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.Model.OrderDetailsHit;
import com.saavor.user.Model.OrderDetailsReceive;
import com.saavor.user.Model.PlaceOrder;
import com.saavor.user.Model.RealTimeHit;
import com.saavor.user.Model.RealtimeReturn;
import com.saavor.user.Model.Realtimedata;
import com.saavor.user.Model.TestDish;
import com.saavor.user.Model.TestModel;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.Utils.Utils;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.chefserver.ChefCartActivity;
import com.saavor.user.chefserver.ChefHutActivity;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.saavor.user.activity.Chart.sub;
import static com.saavor.user.activity.Chart.total_address;
import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.finaladdress;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;
import static com.saavor.user.activity.DashBoard.totalprice;

public class CompletePayment extends BaseActivity implements View.OnClickListener, OnResultReceived {
    LinearLayout paynow, lldebit, llpaypal;
    Button placeorder;
    private static ImageView checkdebit, checkpaypal, back;
    String kitchen_del_charge;
    private String kitchenprofileid;
    private static Boolean debit = false, paypal = false;
    private static int cardid;
    private static String stripeid;
    PlaceOrder placeOrder = new PlaceOrder();
    TextView total_amount;
    private Calendar calendar;
    private int CalendarHour, CalendarMinute;
    String min = "";
    String format;
    String time = "";
    String timeto = "";
    String orderID = "", cooking_instructions, delivery_instructions;
    String tip = "";
    String servicecharge = "";
    String salestax = "";
    String t;
    String rdeladdress = "";
    String promotxt = "";
    double promodis = 0;
    double discount = 0;
    boolean reorder = false;
    String Username = "", contact = "", rprofileid = "", rdelfee = "", TipType, salestaxper;
    String preparingtime = "";
    int pt = 0, isdel;
    int ptdummy = 0;
    Date deliveryfrom;
    Date deliveryto;
    String delfrom = "";
    String delto = "";
    String deltime = "";
    StringBuilder stringBuilder;
    ImageView addcard;
    RealtimeReturn realtimeReturn;
    boolean cancelorder = false;
    Dialog internet_diag;
    public ArrayList<Realtimedata> realtimedatase = new ArrayList<Realtimedata>();

    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_payment);
        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();

        mOnResultReceived = this;
        //setting delivery pref
        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        stringBuilder = new StringBuilder();
        realtimeReturn = new RealtimeReturn();
        // paypal = false;
        debit = false;
        t = getIntent().getStringExtra("totalamount");
        cooking_instructions = "" + getIntent().getStringExtra("cooking_instructions");
        delivery_instructions = "" + getIntent().getStringExtra("delivery_instructions");
        servicecharge = getIntent().getStringExtra("servicecharge");
        promotxt = getIntent().getStringExtra("PromoCodetxt");
        tip = getIntent().getStringExtra("tip");
        salestax = getIntent().getStringExtra("salestax");
        salestaxper = getIntent().getStringExtra("salestaxpercentage");
        TipType = getIntent().getStringExtra("tiptype");
        promodis = getIntent().getDoubleExtra("PromoCodeDiscount", 0);
        discount = getIntent().getDoubleExtra("dealdiscount", 0);
        reorder = getIntent().getBooleanExtra("reorder", false);
        Username = getIntent().getStringExtra("username");
        contact = getIntent().getStringExtra("contact");
        isdel = getIntent().getIntExtra("isdelivery", 0);



        //dialog intialization
        internet_diag = new Dialog(CompletePayment.this);
        internet_diag.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        internet_diag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        internet_diag.setContentView(R.layout.no_internet);
        internet_diag.setCancelable(false);


        if (reorder) {
            rprofileid = getIntent().getStringExtra("rprofileid");
            rdelfee = getIntent().getStringExtra("rdelfee");
            rdeladdress = getIntent().getStringExtra("rdeladdresss");
        }

        orderID = getIntent().getStringExtra("orderID");
        total_amount = (TextView) findViewById(R.id.total_amount_p);

        if (getIntent().getBooleanExtra("BookingInsert", false)) {
            total_amount.setText("$" + t);
        } else {
            total_amount.setText("" + t);
        }

        paynow = (LinearLayout) findViewById(R.id.ll_paynow);
//      llcod= (LinearLayout) findViewById(R.id.ll_cod);
        lldebit = (LinearLayout) findViewById(R.id.ll_debit);
        // llpaypal = (LinearLayout) findViewById(R.id.ll_paypal);
        placeorder = (Button) findViewById(R.id.but_place_order);

        //  checkcod= (ImageView) findViewById(R.id.img_tick_cod);
        checkdebit = (ImageView) findViewById(R.id.img_tick_debit);
        //  checkpaypal = (ImageView) findViewById(R.id.img_tick_paypal);
        back = (ImageView) findViewById(R.id.tool_back_completep);
        addcard = (ImageView) findViewById(R.id.img_addcard);


        placeorder.setOnClickListener(this);
        addcard.setOnClickListener(this);
        lldebit.setOnClickListener(this);
        //  llpaypal.setOnClickListener(this);
        back.setOnClickListener(this);

        if (getIntent().getBooleanExtra("BookingInsert", false)) {
            placeorder.setText("Book Now");
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.ll_debit:

                Intent intent = new Intent(CompletePayment.this, CardBook.class);
                intent.putExtra("choosecard", 1);
                CompletePayment.this.startActivity(intent);

                break;

            case R.id.img_addcard:

                Intent intentt = new Intent(CompletePayment.this, PaymentMethod.class);
                CompletePayment.this.startActivity(intentt);

                break;


            case R.id.but_place_order:


                if (debit) {

                    load_dialog.show();
                    basicfetch();
                    placeOrder.setCardId("" + cardid);
                    placeOrder.setStripeCardId(stripeid);
                    if (getIntent().getBooleanExtra("BookingInsert", false)) {
                        BookingInsertApi();
                    } else {

                        for (int k = 0; k < storenotcustomizable.size(); k++) {
                            stringBuilder.append(storenotcustomizable.get(k).getDishId() + "~");
                        }


                        String finalString = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);

                        //fetching default
                        String dataa = mDatabase.getString("defaultfilter", "");
                        kitchenSearch = gson.fromJson(dataa, KitchenSearch.class);

                        RealTimeHit realTimeHit = new RealTimeHit();
                        realTimeHit.setCurrentDate(kitchenSearch.getDeliveryDate());
                        realTimeHit.setSessionToken(basicInformation.getSessionToken());
                        realTimeHit.setUserId(basicInformation.getUserId());
                        realTimeHit.setDishIds(finalString);

                        String jsonString = gson.toJson(realTimeHit, RealTimeHit.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.setRequestSource(RequestSource.realtime);
                        oChangePsswordApi.executePostRequest(API.realtime(), jsonString);
                    }

                } else {
                    displayAlert(CompletePayment.this, "Please select payment method");
                }

                break;

            case R.id.tool_back_completep:
                finish();
                break;

        }
    }

    private void BookingInsertApi() {

        BookingInsert bookingInsert = new BookingInsert();
        bookingInsert.setBookingID("");
        bookingInsert.setBookingSlots(getIntent().getStringExtra("BookingSlots"));
        bookingInsert.setBookingsId("0");
        bookingInsert.setBusinessType(getIntent().getStringExtra("BusinessType"));
        bookingInsert.setCardId(String.valueOf(cardid));
        bookingInsert.setChefId(getIntent().getStringExtra("ChefId"));
        bookingInsert.setContactName(getIntent().getStringExtra("ContactName"));
        bookingInsert.setContactNumber(getIntent().getStringExtra("ContactNumber"));
        bookingInsert.setCookingInstructions(getIntent().getStringExtra("CookingInstructions"));
        bookingInsert.setCurrentDate(DateFormat.getDateTimeInstance().format(new Date()));
        bookingInsert.setCustomerId(basicInformation.getCoustmerid());
        bookingInsert.setDealDiscount(getIntent().getStringExtra("DealDiscount"));
        bookingInsert.setDishList(getIntent().getStringExtra("DishList"));
        bookingInsert.setEndDate("");
        bookingInsert.setEventType(getIntent().getStringExtra("EventType"));
        bookingInsert.setLocation(getIntent().getStringExtra("Location"));
        bookingInsert.setNumberOfGuest(getIntent().getStringExtra("NumberOfGuest"));
        bookingInsert.setPromoCode(getIntent().getStringExtra("PromoCode"));
        bookingInsert.setPromoCodeDiscount(getIntent().getStringExtra("PromoCodeDiscount"));
        bookingInsert.setSalesTax(getIntent().getStringExtra("SalesTax"));
        bookingInsert.setSalesTaxPercentage(getIntent().getStringExtra("SalesTaxPercentage"));
        bookingInsert.setSessionToken(basicInformation.getSessionToken());
        bookingInsert.setStartDate("");
        bookingInsert.setStripeCardId(stripeid);
        bookingInsert.setTipAmount(getIntent().getStringExtra("TipAmount"));
        bookingInsert.setTotalAmount(getIntent().getStringExtra("TotalAmount"));
        bookingInsert.setUserId(basicInformation.getUserId());

        load_dialog.show();
        String jsonString = oGson.toJson(bookingInsert, BookingInsert.class).toString();
        PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.BookingInsert);
        oInsertUpdateApi.executePostRequest(API.fBookingInsert(), jsonString);

    }

    @Override
    public void onBackPressed() {
        // do nothing.
        finish();
    }

    public void hitplace() {

        setcurrentime();

        // fetching default
        String data = mDatabase.getString("basickitcheninfo", "");
        basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);

        kitchen_del_charge = basicKitInfo.getKitchenDelCharge();
        kitchenprofileid = basicKitInfo.getKitchenProileid();

        ArrayList<TestDish> testDishes = new ArrayList<>();
        ArrayList<CustomDishHit> customDishstore = new ArrayList<>();

        if (storenotcustomizable.isEmpty()) {

        } else {
            for (int i = 0; i < storenotcustomizable.size(); i++) {
                TestDish testDish = new TestDish();
                testDish.setCalories(storenotcustomizable.get(i).getCalories());
                testDish.setDishId(storenotcustomizable.get(i).getDishId());
                testDish.setIsCustomizable(storenotcustomizable.get(i).getIsCustomizable());
                testDish.setPrice(storenotcustomizable.get(i).getPrice());
                testDish.setQuantity(storenotcustomizable.get(i).getQuantity());
                testDish.setItemsName(storenotcustomizable.get(i).getItemsName());
                testDish.setItemsCost(storenotcustomizable.get(i).getItemsCost());
                testDishes.add(testDish);


                preparingtime = storenotcustomizable.get(i).getPreprationtime();

                Log.e("preparingtime", "" + preparingtime);

                pt = Integer.parseInt(preparingtime);

                Log.e("afterconvert", "" + pt);

                if (ptdummy < pt) {
                    ptdummy = pt;
                }

                if (ptdummy < 10) {
                    ptdummy = 10;
                }
            }
        }

        //adding avergae delivery time with prepration time and add their min
        int apptime = ptdummy + basicInformation.getAdt();

        //adding avergae delivery time with prepration time in current date
        Calendar deliveryestimate = Calendar.getInstance();
        System.out.println(deliveryestimate.getTime());
        deliveryestimate.add(Calendar.MINUTE, apptime);
        System.out.println("doooooo" + deliveryestimate.getTime());

        //converting user delivery time format to required format
        DateFormat parser = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);
        String startDate = deliverydetailspref.getString("Deliverydate", "");
        Date userdeltime = null;


        try {
            //converting user delivery time format to required format
            userdeltime = parser.parse(startDate);
            Date appdeltime = deliveryestimate.getTime();

            //checking validation
            if (userdeltime.after(appdeltime)) {

                printDifference(appdeltime, userdeltime);
            } else if (userdeltime.before(appdeltime)) {

                deliveryfrom = deliveryestimate.getTime();
                deliveryestimate.add(Calendar.MINUTE, 15);
                deliveryto = deliveryestimate.getTime();
                printDifference(deliveryfrom, deliveryto);
            } else if (userdeltime.equals(appdeltime)) {
                System.out.println("Date1 is equal Date2");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        String testdishesStr = gson.toJson(testDishes);
        String testaddon = gson.toJson(customDishstore);

        double total = DashBoard.totalprice;

        String value = t;
        value = value.substring(1);

        if (reorder) {
            placeOrder.setCompleteAddress("" + total_address);
            placeOrder.setProfileId("" + rprofileid);

            if (basicKitInfo.getIsdelivery() == 1) {
                placeOrder.setDeliveryFee("" + basicKitInfo.getKitchenDelCharge());
            } else if (basicKitInfo.getIskitchendelivery() == 1) {
                placeOrder.setDeliveryFee("" + basicKitInfo.getUlyxDeliveryFee());
            } else {
                placeOrder.setDeliveryFee("0");
            }

        } else {
            placeOrder.setCompleteAddress("" + total_address);
            placeOrder.setProfileId("" + kitchenprofileid);
            placeOrder.setDeliveryFee("" + kitchen_del_charge);
        }
        placeOrder.setIsDelivery("" + isdel);
        placeOrder.setContactNumber("" + contact);
        placeOrder.setCreateDate("" + date_format);
        placeOrder.setUserName("" + Username);
        placeOrder.setOrderDate("" + date_format);
        placeOrder.setSessionToken("" + basicInformation.getSessionToken());
        placeOrder.setOrderDishes(testdishesStr);
        placeOrder.setTotalAmount("" + value);
        placeOrder.setUserId("" + basicInformation.getUserId());
        placeOrder.setCookingInstruction("" + cooking_instructions);
        placeOrder.setCustomDishes("");
        placeOrder.setDiscount("0");
        placeOrder.setDeliveryInstruction("" + delivery_instructions);
        placeOrder.setPromoCode("" + promotxt);
        placeOrder.setCustomerId("" + basicInformation.getCoustmerid());
        placeOrder.setDeliveryFrom("" + delfrom);
        placeOrder.setDeliveryTo("" + delto);
        placeOrder.setOrderID(orderID);
        placeOrder.setSalesTax("" + salestax);
        placeOrder.setTipType("" + TipType);
        placeOrder.setServiceCharges(servicecharge);
        placeOrder.setTipAmount("" + tip);
        placeOrder.setPromoCodeDiscount("" + promodis);
        placeOrder.setDealDiscount("" + discount);
        placeOrder.setDeliveryTime(deltime);
        placeOrder.setSalesTaxPercentage(salestaxper);
        placeOrder.setDropOffFee(basicKitInfo.getDropOffFee());
        placeOrder.setUserState(basicInformation.getUserCityName());
        placeOrder.setUserLatitude(basicInformation.getUserLatitude());
        placeOrder.setUserLongitude(basicInformation.getUserLongitude());
        placeOrder.setFlatRate(basicKitInfo.getFlatRate());
        placeOrder.setRouteDistance(basicKitInfo.getDistance());
        placeOrder.setVehicleTypeId(basicKitInfo.getVehicleTypeId());
        placeOrder.setSubTotal(sub);


        String jsonString = gson.toJson(placeOrder, PlaceOrder.class).toString();
        System.out.println("" + jsonString);
        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
        oChangePsswordApi.setRequestSource(RequestSource.placeorder);
        oChangePsswordApi.executePostRequest(API.placeorder(), jsonString);

    }


    @Override
    public void dispatchString(RequestSource from, final String what) {

        //load_dialog.cancel();
        // load_dialog.cancel();
        ServerResult = what;

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(CompletePayment.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else if (from.toString().equalsIgnoreCase("realtime")) {

            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                realtimeReturn = gson.fromJson(jsonString, RealtimeReturn.class);
                System.out.println(">>>>what" + what);
                String check = realtimeReturn.getReturnCode();
                final String message = realtimeReturn.getReturnMessage();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            realtimedatase.clear();
                            String myString = realtimeReturn.getLeftQtys();
                            String[] aSplit = myString.split(",");

                            for (int i = 0; i < aSplit.length; i++) {
                                String datasplit = aSplit[i];
                                String[] dataarray = datasplit.split("-");

                                Realtimedata realtimedata = new Realtimedata();
                                realtimedata.setDishid(dataarray[1]);
                                realtimedata.setDishquantity(dataarray[0]);
                                realtimedatase.add(realtimedata);
                            }

                            checkquantity();

                        }
                    });


                } else if (check.equals("0")) {
                    load_dialog.cancel();

                } else if (check.equals("-1")) {
                    load_dialog.cancel();

                } else if (check.equals("5")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //handler.removeCallbacksAndMessages(null);

                    }
                });
            }
        } else if (from.toString().equalsIgnoreCase("BookingInsert")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final JSONObject result;
                    try {
                        result = new JSONObject(what);
                        if (result.getString("ReturnCode").equals("1")) {

                            load_dialog.cancel();

                            TextView message = (TextView) dialog.findViewById(R.id.text_empty);
                            message.setText("Booking placed successfully.");
                            Button okplaced = (Button) dialog.findViewById(R.id.ok_placed);

                            okplaced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();
                                    Intent intent = new Intent(CompletePayment.this, BookingHistoryActivity.class);
                                    CompletePayment.this.startActivity(intent);
                                }
                            });

                            dialog.show();
                            //displayMessage(CompletePayment.this, "Booking placed successfully.");
                        } else {
                            displayAlert(CompletePayment.this, result.getString("ReturnMessage"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        load_dialog.cancel();
                    }
                }
            });

        } else {
            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                todayReturn = gson.fromJson(jsonString, TodayReturn.class);
                System.out.println(">>>>" + what);
                String check = todayReturn.getReturnCode();
                final String message = todayReturn.getReturnMessage();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();

                            dialog = new Dialog(CompletePayment.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.thanku_dialog);
                            dialog.setCancelable(false);

                            Button okplaced = (Button) dialog.findViewById(R.id.ok_placed);

                            okplaced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();

                                    storenotcustomizable.clear();
                                    totalprice = 0;
                                    TotalItem = 0;
                                    finaladdress = "";

                                    Intent intent = new Intent(CompletePayment.this, OrderHistory.class);
                                    CompletePayment.this.startActivity(intent);
                                }
                            });

                            dialog.show();

                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            dialog = new Dialog(CompletePayment.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.alert_diag);
                            dialog.setCancelable(false);

                            Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
                            TextView alertext = (TextView) dialog.findViewById(R.id.text_alert);

                            alertext.setText(message);

                            okplaced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();
                                    finaladdress = "";
                                    intent = new Intent(CompletePayment.this, DashBoard.class);
                                    CompletePayment.this.startActivity(intent);
                                }
                            });

                            dialog.show();


                        }
                    });

                } else if (check.equals("-1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            displayAlert(CompletePayment.this, message);
                        }
                    });

                } else if (check.equals("5")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }
                    });
                }


            } catch (Exception e) {
                System.out.println(">>>>" + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        //  displayAlert(CompletePayment.this, "please check your internet connection");
                        nointernet();
                    }
                });
            }

            Log.e("response", "" + what);

        }
    }


    public static void placeorderdebit(Integer cardId, String stripeCardId) {
        debit = true;
        //   paypal = false;
        checkdebit.setVisibility(View.VISIBLE);
//        checkpaypal.setVisibility(View.GONE);
        cardid = cardId;
        stripeid = stripeCardId;


    }

    public void setcurrentime() {
        calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);

        if (CalendarMinute < 9) {
            min = "0" + CalendarMinute;
        } else {
            min = "" + CalendarMinute;
        }

        if (CalendarHour == 0) {

            CalendarHour += 12;

            format = "AM";
        } else if (CalendarHour == 12) {

            format = "PM";

        } else if (CalendarHour > 12) {

            CalendarHour -= 12;

            format = "PM";
        } else {

            format = "AM";
        }

        time = CalendarHour + ":" + min + " " + format;
        timeto = CalendarHour + 1 + ":" + min + " " + format;
    }

    public void printDifference(java.util.Date startDate, java.util.Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        String myString = "" + startDate;
        String time = "";
        String[] aSplit = myString.split("\\s+");
        time = aSplit[3];

        System.out.println(">>>>>" + time);


        String[] timeSplit = time.split(":");
        String hour = "";
        String min = "";

        hour = timeSplit[0];
        min = timeSplit[1];
        System.out.println(">>>>>" + hour);
        System.out.println(">>>>>" + min);
        String conversion = "" + hour + ":" + min;
        System.out.println(">>>>>" + conversion);

        try {
            String _24HourTime = conversion;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));
            delfrom = "" + _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String endatestr = "" + endDate;
        String endtime = "";
        String[] endsplit = endatestr.split("\\s+");
        endtime = endsplit[3];

        System.out.println(">>>>>" + time);


        String[] endtimeSplit = endtime.split(":");
        String endhour = "";
        String endmin = "";

        endhour = endtimeSplit[0];
        endmin = endtimeSplit[1];
        System.out.println(">>>>>" + endhour);
        System.out.println(">>>>>" + endmin);
        String endconversion = "" + endhour + ":" + endmin;
        System.out.println(">>>>>" + endconversion);

        try {
            String _24HourTime = endconversion;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));

            delto = "" + _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        deltime = "" + endDate;

        SimpleDateFormat formatter5 = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.ENGLISH);
        String formats1 = formatter5.format(endDate);
        System.out.println(formats1);
        deltime = formats1;


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        String value = "" + elapsedDays + " " + elapsedHours + " " + elapsedMinutes;
        System.out.println(">>>>>>>>>" + value);

        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }

    public void checkquantity() {
        for (int i = 0; i < storenotcustomizable.size(); i++) {
            int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());


            for (int k = 0; k < realtimedatase.size(); k++) {
                int iD2 = Integer.valueOf(realtimedatase.get(k).getDishid());

                if (iD1 == iD2) {

                    if (storenotcustomizable.get(i).getQuantity() > Integer.valueOf(realtimedatase.get(k).getDishquantity())) {
                        cancelorder = true;
                    }
                }
            }
        }

        if (cancelorder) {
            //dialog intialization
            dialog = new Dialog(CompletePayment.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.sold_out_diag);
            dialog.setCancelable(false);

            Button okplaced = (Button) dialog.findViewById(R.id.ok_sold);

            okplaced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                    intent = new Intent(CompletePayment.this, DashBoard.class);
                    CompletePayment.this.startActivity(intent);
                }
            });

            dialog.show();
        } else {
            hitplace();
        }
    }

    public void nointernet() {
        Button settings = (Button) internet_diag.findViewById(R.id.not_settings);
        Button retry = (Button) internet_diag.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internet_diag.cancel();

                if (debit) {

                    load_dialog.show();
                    basicfetch();
                    placeOrder.setCardId("" + cardid);
                    placeOrder.setStripeCardId(stripeid);
                    if (getIntent().getBooleanExtra("BookingInsert", false)) {
                        BookingInsertApi();
                    } else {

                        for (int k = 0; k < storenotcustomizable.size(); k++) {
                            stringBuilder.append(storenotcustomizable.get(k).getDishId() + "~");
                        }


                        String finalString = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);

                        //fetching default
                        String dataa = mDatabase.getString("defaultfilter", "");
                        kitchenSearch = gson.fromJson(dataa, KitchenSearch.class);

                        RealTimeHit realTimeHit = new RealTimeHit();
                        realTimeHit.setCurrentDate(kitchenSearch.getDeliveryDate());
                        realTimeHit.setSessionToken(basicInformation.getSessionToken());
                        realTimeHit.setUserId(basicInformation.getUserId());
                        realTimeHit.setDishIds(finalString);

                        String jsonString = gson.toJson(realTimeHit, RealTimeHit.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.setRequestSource(RequestSource.realtime);
                        oChangePsswordApi.executePostRequest(API.realtime(), jsonString);
                    }

                } else {
                    displayAlert(CompletePayment.this, "Please select payment method");
                }


            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internet_diag.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        internet_diag.show();

    }
}
