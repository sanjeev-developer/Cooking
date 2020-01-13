package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.CustomDishHit;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.Model.DishList;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.Model.NonCustomizableList;
import com.saavor.user.Model.TestDish;
import com.saavor.user.Model.TestModel;
import com.saavor.user.Model.TodayMenuHit;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.Model.TodaysMenu;
import com.saavor.user.R;
import com.saavor.user.adapter.KitchenAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;
import static com.saavor.user.activity.DashBoard.totalprice;

public class Kitchen extends BaseActivity implements View.OnClickListener, OnResultReceived {

//    private KitchenAdapter kitchenAdapter;
//    RecyclerView.LayoutManager mLayoutManager;
    private ImageView back_kitchen, nextdaydish, previousdaydish;
    private TextView mCheckout, date_title;
    TextView mMenuTitle, kit_title_today;
    TextView text, totalitems;
    int isdeliverykitchen=0;


    ArrayList<DishList> dishdetails = new ArrayList<>();
    LinearLayout LL_checkout;
    Date Startdate, Enddate;
    LinearLayout LL_no_dish;
   // Date deliverydate = null;


    String kitchen_del_charge;
    String kitchenprofileid;
    String k;
    Integer orderdishid;
    boolean ordernow = false;
    boolean cartitems =false;
    Date deliverydatee=null;
    DateFormat datebeforeFormat;
    Date hitdelivery=null;
    DateFormat dateFormat;
    Calendar c;
    String orderdatemove="";

    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        //fetching user basic infromation
        basicfetch();

        mOnResultReceived = this;

        //setting delivery pref
        deliverydetailspref=getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        // fetching default
        String data = mDatabase.getString("basickitcheninfo", "");
        basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);

        if(basicKitInfo.getIsdelivery() == 1)
        {
                kitchen_del_charge = basicKitInfo.getKitchenDelCharge();
            isdeliverykitchen=1;
        }
        else if(basicKitInfo.getIskitchendelivery() == 1)
        {
            kitchen_del_charge = basicKitInfo.getUlyxDeliveryFee();
            isdeliverykitchen=1;
        }
        else
        {
            kitchen_del_charge = "";
            isdeliverykitchen=0;
        }

        kitchenprofileid = basicKitInfo.getKitchenProileid();
        kit_title_today = (TextView) findViewById(R.id.txt_kit_title_today);
        date_title = (TextView) findViewById(R.id.txt_date_title);
        totalitems = (TextView) findViewById(R.id.total_items);
        back_kitchen = (ImageView) findViewById(R.id.tool_back_kitchen);
        nextdaydish = (ImageView) findViewById(R.id.img_nextdish);
        previousdaydish= (ImageView) findViewById(R.id.img_previousdish);
        mCheckout = (TextView) findViewById(R.id.but_checkout_kitchen);
        LL_checkout= (LinearLayout) findViewById(R.id.ll_checkout);
        LL_no_dish= (LinearLayout) findViewById(R.id.ll_no_dish);

        LL_checkout.setOnClickListener(this);
        mCheckout.setOnClickListener(this);
        back_kitchen.setOnClickListener(this);
        nextdaydish.setOnClickListener(this);
        previousdaydish.setOnClickListener(this);

        k = getIntent().getStringExtra("kitchentitle");
        kit_title_today.setText(k);
        orderdishid = getIntent().getIntExtra("dishid", 0);
        ordernow=getIntent().getBooleanExtra("reorder" , false);


        dateFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
        Enddate = new Date();
        c = Calendar.getInstance();
        c.setTime(Enddate);
        c.add(Calendar.DATE, 6);
        Enddate = c.getTime();
        System.out.println(dateFormat.format(Enddate));
        try {
            Enddate = dateFormat.parse(dateFormat.format(Enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Startdate=dateFormat.parse(date_format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            deliverydatee = dateFormat.parse(deliverydetailspref.getString("Deliverydate", ""));
            orderdatemove=deliverydetailspref.getString("Deliverydate", "");

        } catch (ParseException e) {
            e.printStackTrace();
        }


         datebeforeFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);

        try {
            hitdelivery = datebeforeFormat.parse(deliverydetailspref.getString("Deliverydate", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (deliverydatee.after(Startdate)) {
            spacedate(deliverydetailspref.getString("Deliverydate", ""));

            orderdatemove = deliverydetailspref.getString("Deliverydate", "");
        }

        if (deliverydatee.before(Startdate)) {
            System.out.println("Date1 is before Date2");

            orderdatemove=date_format;
        }

        if (deliverydatee.equals(Startdate)) {
            date_title.setText("TODAY'S MENU");

            orderdatemove=date_format;
        }


        if(ordernow)
        {
            try {
                basicInformation.getSessionToken();
                basicInformation.getUserId();
                //hitting api
                todayMenuHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
                todayMenuHit.setProfileId("" + getIntent().getDoubleExtra("kitchenprofile" , 0 ));
                todayMenuHit.setSessionToken(basicInformation.getSessionToken());
                todayMenuHit.setUserId(basicInformation.getUserId());

                mProgressDialog.setMessage("Fetching Today's Menu");
                load_dialog.show();

                String jsonString = gson.toJson(todayMenuHit, TodayMenuHit.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.todaysmenu);
                oChangePsswordApi.executePostRequest(API.todaysmenu(), jsonString);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else
        {
            try {
                basicInformation.getSessionToken();
                basicInformation.getUserId();
                //hitting api
                todayMenuHit.setCurrentDate(orderdatemove);
                todayMenuHit.setProfileId("" + kitchenprofileid);
                todayMenuHit.setSessionToken(basicInformation.getSessionToken());
                todayMenuHit.setUserId(basicInformation.getUserId());

                mProgressDialog.setMessage("Fetching Today's Menu");
                load_dialog.show();

                String jsonString = gson.toJson(todayMenuHit, TodayMenuHit.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.todaysmenu);
                oChangePsswordApi.executePostRequest(API.todaysmenu(), jsonString);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.but_checkout_kitchen:

                addtocart();

                break;

            case R.id.img_previousdish:

                storenotcustomizable.clear();
                totalprice = 0;
                TotalItem = 0;
                mCheckout.setText("$0.00");
                totalitems.setText("0 items");

                if (deliverydatee.after(Startdate)) {
                    System.out.println("Date1 is after Date2");

                    c.setTime(hitdelivery);
                    c.add(Calendar.DATE, -1);
                    hitdelivery = c.getTime();
                    System.out.println(datebeforeFormat.format(hitdelivery));


                    c.setTime(deliverydatee);
                    c.add(Calendar.DATE, -1);
                    deliverydatee = c.getTime();

                    spacedate(datebeforeFormat.format(hitdelivery));
                    orderdatemove= datebeforeFormat.format(hitdelivery);

                    if (deliverydatee.equals(Startdate)) {
                        System.out.println("Date1 is equal Date2");
                        date_title.setText("TODAY'S MENU");
                    }

                    //hitting api
                    todayMenuHit.setCurrentDate(datebeforeFormat.format(hitdelivery));
                    todayMenuHit.setProfileId("" + kitchenprofileid);
                    todayMenuHit.setSessionToken(basicInformation.getSessionToken());
                    todayMenuHit.setUserId(basicInformation.getUserId());

                    mProgressDialog.setMessage("Loading Menu");
                    load_dialog.show();

                    String jsonString = gson.toJson(todayMenuHit, TodayMenuHit.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.setRequestSource(RequestSource.todaysmenu);
                    oChangePsswordApi.executePostRequest(API.todaysmenu(), jsonString);
                }

                if (deliverydatee.before(Startdate)) {
                    System.out.println("Date1 is before Date2");
                }


                break;
            case R.id.img_nextdish:

                storenotcustomizable.clear();
                totalprice = 0;
                TotalItem = 0;
                mCheckout.setText("$0.00");
                totalitems.setText("0 items");

                if (deliverydatee.after(Enddate)) {
                    System.out.println("Date1 is after Date2");
                }

                if (deliverydatee.before(Enddate)) {
                    System.out.println("Date1 is before Date2");

                    c.setTime(hitdelivery);
                    c.add(Calendar.DATE, 1);
                    hitdelivery = c.getTime();
                    System.out.println(datebeforeFormat.format(hitdelivery));

                    c.setTime(deliverydatee);
                    c.add(Calendar.DATE, 1);
                    deliverydatee = c.getTime();

                    spacedate(datebeforeFormat.format(hitdelivery));
                    orderdatemove= datebeforeFormat.format(hitdelivery);

                    if (deliverydatee.equals(Enddate)) {
                        System.out.println("Date1 is equal Date2");
                    }

                    //hitting api
                    todayMenuHit.setCurrentDate(datebeforeFormat.format(hitdelivery));
                    todayMenuHit.setProfileId("" + kitchenprofileid);
                    todayMenuHit.setSessionToken(basicInformation.getSessionToken());
                    todayMenuHit.setUserId(basicInformation.getUserId());

                    mProgressDialog.setMessage("Loading Menu");
                    load_dialog.show();

                    String jsonString = gson.toJson(todayMenuHit, TodayMenuHit.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.setRequestSource(RequestSource.todaysmenu);
                    oChangePsswordApi.executePostRequest(API.todaysmenu(), jsonString);

                }

                break;
            case R.id.ll_checkout:

                addtocart();

                break;

            case R.id.tool_back_kitchen:

                if(ordernow)
                {
                    intent = new Intent(getApplicationContext(), Favourite.class);
                    startActivity(intent);

                }
                else
                {
                    intent = new Intent(getApplicationContext(), XYZKitchen.class);
                    startActivity(intent);

                }


                break;


        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(getApplicationContext(), XYZKitchen.class);
        intent.putExtra("Sessionexp",1);
        startActivity(intent);
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

         if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Kitchen.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
        if (from.toString().equalsIgnoreCase("todaysmenu")) {

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

                            LL_no_dish.setVisibility(View.GONE);

                            // Layout inflater
                            LayoutInflater layoutInflater = getLayoutInflater();
                            View view;

                            // Parent layout
                            LinearLayout parentLayout = (LinearLayout) findViewById(R.id.layout);
                            parentLayout.removeAllViews();

                            ArrayList<TodaysMenu> dishdata = new ArrayList<>();
                            dishdata = todayReturn.getTodaysMenu();
                            //   kit_title_today.setText(todayReturn.getTodaysMenu().get);

                            if(TotalItem<=0) {

                                cartitems=false;
                                mCheckout.setText("$0.00");
                                totalitems.setText("0 items");
                            }
                            else
                            {
                                cartitems=true;
                                totalitems.setText(TotalItem+" items");

                                DecimalFormat df = new DecimalFormat("####0.00");
                                System.out.println("Value: " + df.format(totalprice));

                                mCheckout.setText("$"+df.format(totalprice));
                            }

                            for (int i = 0; i < dishdata.size(); i++) {
                                // Add the layout to the parent layout
                                view = layoutInflater.inflate(R.layout.child, parentLayout, false);
                                mMenuTitle = (TextView) view.findViewById(R.id.menu_title);
                                mMenuTitle.setText(dishdata.get(i).getMenuTitle());

                                dishdetails = new ArrayList<DishList>();
                                dishdetails = todayReturn.getTodaysMenu().get(i).getDishList();

                                RecyclerView recyclerView_kitchen = (RecyclerView) view.findViewById(R.id.recycle_kitchen);
                                recyclerView_kitchen.setNestedScrollingEnabled(false);
                                recyclerView_kitchen.setFocusable(false);

                                KitchenAdapter kitchenAdapter = new KitchenAdapter(Kitchen.this, dishdetails ,ordernow, orderdishid, cartitems);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Kitchen.this);
                                recyclerView_kitchen.setLayoutManager(mLayoutManager);
                                recyclerView_kitchen.setItemAnimator(new DefaultItemAnimator());
                                recyclerView_kitchen.setAdapter(kitchenAdapter);

                                String y = mCheckout.getText().toString();
                                kitchenAdapter.receivedata(y.substring(2));

                                // Add the text view to the parent layout
                                parentLayout.addView(view);

                            }

                            load_dialog.cancel();
                        }
                    });


                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LL_no_dish.setVisibility(View.VISIBLE);
                           // displayAlert(Kitchen.this, message);
                            load_dialog.cancel();
                        }
                    });

                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                        }
                    });

                }


                else if (check.equals("5")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp",1);
                            startActivity(intent);
                        }
                    });}

            } catch (Exception e) {
                System.out.println(">>>>" + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        //redirect(Kitchen.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }

            Log.e("response", "" + what);

        } else if (from.toString().equalsIgnoreCase("addtocart")) {


            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                todayReturn = gson.fromJson(jsonString, TodayReturn.class);
                System.out.println(">>>>what" + what);
                String check = todayReturn.getReturnCode();
                final String message = todayReturn.getReturnMessage();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            delievryfetch();
//
//                            deliveryTypeDetails.setDeliveryDate(datebeforeFormat.format(hitdelivery));
//                            mDeliveryDetails = gson.toJson(deliveryTypeDetails);
//                            mTabel.putString("deliverydetails", mDeliveryDetails);
//                            mTabel.commit();

//                            deliveryeditor.putString("Deliverydate", datebeforeFormat.format(hitdelivery));
//                            deliveryeditor.commit();

                          //  orderdatemove=datebeforeFormat.format(hitdelivery);

                            intent = new Intent(Kitchen.this, Chart.class);
                            intent.putExtra("kitchentitle", k);
                            intent.putExtra("movdate", orderdatemove);
                            Kitchen.this.startActivity(intent);
                            load_dialog.cancel();
                        }
                    });


                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayAlert(Kitchen.this, message);
                            load_dialog.cancel();
                        }
                    });

                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            displayAlert(Kitchen.this, message);
                        }
                    });

                }


                else if (check.equals("5")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp",1);
                            startActivity(intent);
                        }
                    });}


            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        nointernet();
                    }
                });
            }


        }
    }}

    public void senddata(int t, double result) {
        totalitems.setText(t + " items");
        mCheckout.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});

        String sentence = ""+result;
        String search  = "-";

        if ( sentence.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {

            System.out.println("I found the keyword");
            mCheckout.setText("$0.00");


        } else {

            System.out.println("not found");
            mCheckout.setText("$" + String.format("%.02f", result));

        }

       // mCheckout.setText("$" + String.format("%.02f", result));
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;


        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("(([1-9]{1}[0-9]{0," + (digitsBeforeZero - 1) + "})?||[0]{1})((\\.[0-9]{0," + digitsAfterZero + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }

    public void addtocart()
    {
        ArrayList<TestDish> testDishes = new ArrayList<>();
        ArrayList<CustomDishHit> customDishstore = new ArrayList<>();


        if (storenotcustomizable.isEmpty()) {
            displayAlert(Kitchen.this, "Please select item from the menu.");
        } else {
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
                }
            }

            try {
                mProgressDialog.setMessage("Adding Your Items To Your Cart");
                load_dialog.show();

//                String dell = mDatabase.getString("deliverydetails", "");
//                deliveryTypeDetails = gson.fromJson(dell, DeliveryTypeDetails.class);

                //fetching data from filters
                mFilterdefault = mDatabase.getString("defaultfilter", "");
                kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

                String testdishesStr = gson.toJson(testDishes);

                String data = mDatabase.getString("basickitcheninfo", "");
                basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);


                DilalogModel dilalogModel = new DilalogModel();
                String dailogdata = mDatabase.getString("dialogdata", "");
                dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                TestModel testModel = new TestModel();
                if (dailogdata.equals(""))
                {
                    testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));

                } else {

                    if(dilalogModel.getDeladdress() == null || dilalogModel.getDeladdress().equals(""))
                    {
                        testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));
                    }
                    else
                    {
                        testModel.setCompleteAddress("" + dilalogModel.getDeladdress());
                    }

                }


                double total = totalprice;

                //testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));
                testModel.setContactNumber("");
                testModel.setCreateDate("" + date_format);
                testModel.setDeliveryFrom("10:00PM");
                testModel.setDeliveryTo("11:00PM");
                testModel.setIsDelivery(""+isdeliverykitchen);
                testModel.setUserName("" + basicInformation.getFirstName()+" "+basicInformation.getFirstName());
                testModel.setOrderDate(orderdatemove);
                testModel.setSessionToken("" + basicInformation.getSessionToken());
                testModel.setOrderDishes(testdishesStr);
                testModel.setProfileId("" + kitchenprofileid);
                testModel.setTotalAmount("" + total);
                testModel.setUserId("" + basicInformation.getUserId());
                testModel.setCookingInstruction("");

                if(kitchen_del_charge==null ||
                        kitchen_del_charge.equals(""))
                {
                    testModel.setDeliveryFee("0");
                }
                else
                {
                    testModel.setDeliveryFee("" + kitchen_del_charge);
                }

                testModel.setDiscount("0");
                testModel.setDeliveryInstruction("");
                testModel.setPromoCode("");
                testModel.setSalesTax("0");
                testModel.setServiceCharges("0");
                testModel.setTipAmount("0");
                testModel.setOrderID("");

                String jsonString = gson.toJson(testModel, TestModel.class).toString();
                System.out.println("addtocart" + jsonString);
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.addtocart);
                oChangePsswordApi.executePostRequest(API.addtocart(), jsonString);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void spacedate(String deliveryDate)
    {
        try {

            String dateStr = deliveryDate;

            DateFormat srcDf = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);

            // parse the date string into Date object
            Date date = srcDf.parse(dateStr);

            DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

            // format the date into another format
            dateStr = destDf.format(date);

            System.out.println("Converted date is : " + dateStr);
            date_title.setText(""+dateStr);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Kitchen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings=(Button)dialog.findViewById(R.id.not_settings);
        Button retry=(Button)dialog.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                storenotcustomizable.clear();
                TotalItem = 0;
                totalprice = 0;

                try {
                    if(ordernow)
                    {
                        try {
                            basicInformation.getSessionToken();
                            basicInformation.getUserId();
                            //hitting api
                            todayMenuHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
                            todayMenuHit.setProfileId("" + getIntent().getDoubleExtra("kitchenprofile" , 0 ));
                            todayMenuHit.setSessionToken(basicInformation.getSessionToken());
                            todayMenuHit.setUserId(basicInformation.getUserId());

                            mProgressDialog.setMessage("Fetching Today's Menu");
                            load_dialog.show();

                            String jsonString = gson.toJson(todayMenuHit, TodayMenuHit.class).toString();
                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                            oChangePsswordApi.setRequestSource(RequestSource.todaysmenu);
                            oChangePsswordApi.executePostRequest(API.todaysmenu(), jsonString);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    else
                    {
                        try {
                            basicInformation.getSessionToken();
                            basicInformation.getUserId();
                            //hitting api
                            todayMenuHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
                            todayMenuHit.setProfileId("" + kitchenprofileid);
                            todayMenuHit.setSessionToken(basicInformation.getSessionToken());
                            todayMenuHit.setUserId(basicInformation.getUserId());

                            mProgressDialog.setMessage("Fetching Today's Menu");
                            load_dialog.show();

                            String jsonString = gson.toJson(todayMenuHit, TodayMenuHit.class).toString();
                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                            oChangePsswordApi.setRequestSource(RequestSource.todaysmenu);
                            oChangePsswordApi.executePostRequest(API.todaysmenu(), jsonString);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {

                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        dialog.show();

    }


}

