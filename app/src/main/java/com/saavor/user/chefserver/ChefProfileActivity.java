package com.saavor.user.chefserver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.saavor.user.Model.GetChefProfile;
import com.saavor.user.Model.Slots;
import com.saavor.user.Model.UserBookmarkInsert;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.CardBook;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.activity.XYZKitchen;
import com.saavor.user.adapter.ChefsDealsAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChefProfileActivity extends BaseActivity implements View.OnClickListener, OnResultReceived {


    ChefsDealsAdapter oChefsDealsAdapter;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;

    Button btn_book_online;

    ImageView action_back;
    ImageView profilePicIv;
    ImageView img_heart1;
    ImageView img_heart2;
    ImageView img_heart3;
    ImageView img_heart4;
    ImageView img_heart5;

    TextView txt_review;
    TextView txt_cuisines_chef;
    TextView txt_services_chef;
    TextView txtChefHut;
    TextView txt_chef_name;
    TextView txt_deliveryTime;
    TextView txt_delivery_fee;
    TextView txt_dealOffer;
    TextView txtValid ,txt_message_chef;

    ProgressBar imageLoader;

    LinearLayout ll_dealsParent;
    LinearLayout ll_business_parentView;

    int ParentId;
    String AvailableFrom;
    String AvailableTo;
    String ChefId;
    String SaleTaxPercentage = "";
    String DealDiscount = "";
    String DiscountType = "";
    String MinAmount = "";
    String MobileNo = "";

    private LinearLayout deal_layout ,ll_cant_del_chef;
    private LinearLayout ll_share;
    private LinearLayout ll_call;
    private LinearLayout ll_bookmark;
    private ImageView img_bookmark;
    private Dialog dialog;

    public static Activity activity;
    String BookedSlots = "";

    ArrayList<String> arEvents;
    String BusinessType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        basicfetch();
        InitializeInterface();

    }

    private void InitializeInterface() {
        activity = this;

        profilePicIv = (ImageView) findViewById(R.id.profilePicIv);
        img_heart1 = (ImageView) findViewById(R.id.img_heart1);
        img_heart2 = (ImageView) findViewById(R.id.img_heart2);
        img_heart3 = (ImageView) findViewById(R.id.img_heart3);
        img_heart4 = (ImageView) findViewById(R.id.img_heart4);
        img_heart5 = (ImageView) findViewById(R.id.img_heart5);
        img_bookmark = (ImageView) findViewById(R.id.img_bookmark);

        action_back = (ImageView) findViewById(R.id.action_back);
        action_back.setOnClickListener(this);

        txt_message_chef = (TextView) findViewById(R.id.txt_message_chef);
        txtChefHut = (TextView) findViewById(R.id.txtChefHut);
        txtChefHut.setOnClickListener(this);
        txt_review = (TextView) findViewById(R.id.txt_review);
        txt_cuisines_chef = (TextView) findViewById(R.id.txt_cuisines_chef);
        txt_services_chef = (TextView) findViewById(R.id.txt_services_chef);
        txt_chef_name = (TextView) findViewById(R.id.txt_chef_name);
        txt_deliveryTime = (TextView) findViewById(R.id.txt_deliveryTime);
        txt_delivery_fee = (TextView) findViewById(R.id.txt_delivery_fee);
        txt_dealOffer = (TextView) findViewById(R.id.txt_dealOffer);
        txtValid = (TextView) findViewById(R.id.txtValid);

        ll_cant_del_chef = (LinearLayout) findViewById(R.id.ll_cant_del_chef);
        ll_dealsParent = (LinearLayout) findViewById(R.id.ll_dealsParent);
        ll_business_parentView = (LinearLayout) findViewById(R.id.ll_business_parentView);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_call = (LinearLayout) findViewById(R.id.ll_call);
        ll_bookmark = (LinearLayout) findViewById(R.id.ll_bookmark);
        deal_layout = (LinearLayout) findViewById(R.id.deal_layout);
        deal_layout.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_call.setOnClickListener(this);
        ll_bookmark.setOnClickListener(this);

        btn_book_online = (Button) findViewById(R.id.btn_book_online);
        btn_book_online.setOnClickListener(this);

        imageLoader = new ProgressBar(this);

        // Create custom dialog object
        dialog = new Dialog(ChefProfileActivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dealdialog);

        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();

        load_dialog.show();
        GetChefProfile oGetChefProfile = new GetChefProfile();
        oGetChefProfile.setSessionToken(basicInformation.getSessionToken());
        oGetChefProfile.setUserId(basicInformation.getUserId());
        oGetChefProfile.setCurrentDate(date_format);
        oGetChefProfile.setChefId(getIntent().getStringExtra("ChefId"));
        String jsonString = oGson.toJson(oGetChefProfile, GetChefProfile.class).toString();
        PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.GetChefProfile);
        oInsertUpdateApi.executePostRequest(API.fGetChefProfile(), jsonString);
    }

    public void FinishActivity() {
        finish();
    }

    public String parseDateToddMMyyyy3(String time) {
        String inputPattern = "dd-MMM-yyyy hh:mm:ss a";
        String outputPattern = "MM-dd-yyyy hh:mm:ss a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (str == null) {
            String inputPattern2 = "MMM dd, yyyy hh:mm:ss a";
            String outputPattern2 = "MM-dd-yyyy hh:mm:ss a";
            SimpleDateFormat inputFormat2 = new SimpleDateFormat(inputPattern2, Locale.ENGLISH);
            SimpleDateFormat outputFormat2 = new SimpleDateFormat(outputPattern2, Locale.ENGLISH);
            try {
                date = inputFormat2.parse(time);
                str = outputFormat2.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return str;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.deal_layout:
                dialog.show();
                break;

            case R.id.ll_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Use my referral code " + basicInformation.getRefferal().toString() + " to sign up for Saavor." + " " + "Download the App " + "https://play.google.com/store");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
            case R.id.ll_call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + MobileNo));
                startActivity(callIntent);
                break;
            case R.id.ll_bookmark:
                if (img_bookmark.getTag().equals("0")) {
                    load_dialog.show();
                    img_bookmark.setTag("1");
                    img_bookmark.setImageResource(R.drawable.bookmarkselect);
                    UserBookmarkInsert userBookmarkInsert = new UserBookmarkInsert();
                    userBookmarkInsert.setUserId(basicInformation.getUserId());
                    userBookmarkInsert.setSessionToken(basicInformation.getSessionToken());
                    userBookmarkInsert.setChefId(ChefId);
                    userBookmarkInsert.setProfileId("0");

                    String jsonString = oGson.toJson(userBookmarkInsert, UserBookmarkInsert.class).toString();
                    PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                    oInsertUpdateApi.setRequestSource(RequestSource.UserBookmarkInsert);
                    oInsertUpdateApi.executePostRequest(API.fUserBookmarkInsert(), jsonString);
                } else {
                    img_bookmark.setTag("0");
                    img_bookmark.setImageResource(R.drawable.bookmarkkitchen);

                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.setRequestSource(RequestSource.UserBookmarkDeleteNew);
                    oInsertUpdateApi.executeGetRequest(API.fUserBookmarkDeleteNew() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + "0" + "/" + ChefId);
                }
                break;

            case R.id.btn_book_online:
                Intent intent = new Intent(ChefProfileActivity.this, AvailabilityandBookActivity.class);
                intent.putExtra("ParentId", String.valueOf(ParentId));
                intent.putExtra("AvailableFrom", AvailableFrom);
                intent.putExtra("AvailableTo", AvailableTo);
                intent.putExtra("ChefId", ChefId);
                intent.putExtra("UserName", txt_chef_name.getText().toString());
                intent.putExtra("BookedSlots", BookedSlots);
                intent.putExtra("SaleTaxPercentage", SaleTaxPercentage);
                intent.putExtra("DealDiscount", DealDiscount);
                intent.putExtra("DiscountType", DiscountType);
                intent.putExtra("MinAmount", MinAmount);
                intent.putExtra("BusinessType", BusinessType);
                intent.putStringArrayListExtra("EventsList", arEvents);
                intent.putExtra("Price", txt_delivery_fee.getText().toString().trim().replace("/Hr", ""));
                startActivity(intent);

                break;

            case R.id.action_back:
                finish();
                break;

            case R.id.txtChefHut:
                Intent intent1 = new Intent(ChefProfileActivity.this, ChefHutActivity.class);
                intent1.putExtra("ParentId", String.valueOf(ParentId));
                intent1.putExtra("HutName", txtChefHut.getText().toString());
                startActivity(intent1);

                break;
        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {
        load_dialog.cancel();
        ServerResult = what;

        if (from.toString().equalsIgnoreCase("GetChefProfile")) {
            switch (ServerResult) {
                case "-1":
                case "-2":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nointernet();
                        }
                    });
                    break;
                case "-3":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });
                    break;

                default:
                    try {
                        final JSONObject obj = new JSONObject(what);
                        if (obj.getString("ReturnCode").equals("1")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject ProfileInfo;
                                        JSONArray Events;
                                        JSONArray BookingSlots;
                                        ProfileInfo = obj.getJSONObject("ProfileInfo");



                                        BookingSlots = ProfileInfo.getJSONArray("BookingSlots");
                                        Events = ProfileInfo.getJSONArray("Events");
                                        ArrayList<Slots> aLAddedSlots = new ArrayList<Slots>();
                                        arEvents = new ArrayList<String>();
                                        for (int q = 0; q < Events.length(); q++) {
                                            arEvents.add(Events.getJSONObject(q).getString("EventName"));
                                        }

                                        for (int m = 0; m < BookingSlots.length(); m++) {
                                            Slots slots = new Slots();
                                            slots.setStartTime(BookingSlots.getJSONObject(m).getString("SlotStartTime"));
                                            slots.setEndTime(BookingSlots.getJSONObject(m).getString("SlotEndTime"));
                                            aLAddedSlots.add(slots);
                                        }
                                        if (aLAddedSlots.size() > 0) {
                                            Type listOfBecons = new TypeToken<List<Slots>>() {
                                            }.getType();
                                            BookedSlots = new Gson().toJson(aLAddedSlots, listOfBecons);
                                        }

                                        txt_chef_name.setText(ProfileInfo.getString("FirstName") + " " + ProfileInfo.getString("LastName") + "\n" + "(" + ProfileInfo.getString("BusinessType") + ")");
                                        if (ProfileInfo.getString("StarRating").equalsIgnoreCase("1")) {
                                            img_heart1.setImageResource(R.drawable.redreview);
                                        } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("2")) {
                                            img_heart1.setImageResource(R.drawable.redreview);
                                            img_heart2.setImageResource(R.drawable.redreview);
                                        } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("3")) {
                                            img_heart1.setImageResource(R.drawable.redreview);
                                            img_heart2.setImageResource(R.drawable.redreview);
                                            img_heart3.setImageResource(R.drawable.redreview);
                                        } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("4")) {
                                            img_heart1.setImageResource(R.drawable.redreview);
                                            img_heart2.setImageResource(R.drawable.redreview);
                                            img_heart3.setImageResource(R.drawable.redreview);
                                            img_heart4.setImageResource(R.drawable.redreview);
                                        } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("5")) {
                                            img_heart1.setImageResource(R.drawable.redreview);
                                            img_heart2.setImageResource(R.drawable.redreview);
                                            img_heart3.setImageResource(R.drawable.redreview);
                                            img_heart4.setImageResource(R.drawable.redreview);
                                            img_heart5.setImageResource(R.drawable.redreview);
                                        }
                                        txt_cuisines_chef.setText(ProfileInfo.getString("CuisineList"));
                                        txt_review.setText(ProfileInfo.getString("TotalReviews") + " Reviews");
                                        txt_services_chef.setText(ProfileInfo.getString("ServiceList"));
                                        txtChefHut.setText(ProfileInfo.getString("FirstName") + " " + ProfileInfo.getString("LastName") + "\n" + "(" + ProfileInfo.getString("BusinessType") + ")" + " Working for " + ProfileInfo.getString("BusinessName"));

                                        MobileNo = ProfileInfo.getString("BusinessName");


                                        if (!ProfileInfo.getString("ProfileImagePath").equalsIgnoreCase("")) {
                                            imageLoader.setVisibility(View.VISIBLE);
                                            Picasso.with(mContext).invalidate("http://saavorapi.parkeee.net/" + ProfileInfo.getString("ProfileImagePath"));
                                            Picasso.with(profilePicIv.getContext())
                                                    .load("http://saavorapi.parkeee.net/" + ProfileInfo.getString("ProfileImagePath"))
                                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                                    .placeholder(R.drawable.usericonpd)
                                                    .resize(96, 96).centerCrop().into(profilePicIv, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    imageLoader.setVisibility(View.GONE);
                                                }

                                                @Override
                                                public void onError() {
                                                }
                                            });
                                        }

                                        txt_delivery_fee.setText("$" + ProfileInfo.getString("Price") + "/Hr");
                                        txt_deliveryTime.setText(ProfileInfo.getString("TotalBookings"));

                                        AvailableFrom = ProfileInfo.getString("AvailableFrom");
                                        AvailableTo = ProfileInfo.getString("AvailableTo");
                                        ParentId = Integer.parseInt(ProfileInfo.getString("ParentId"));
                                        ChefId = ProfileInfo.getString("ChefId");
                                        SaleTaxPercentage = ProfileInfo.getString("SaleTaxPercentage");

                                        if (ProfileInfo.getInt("IsBookMark") > 0) {
                                            img_bookmark.setTag("1");
                                            img_bookmark.setImageResource(R.drawable.bookmarkselect);
                                        } else {
                                            img_bookmark.setTag("0");
                                            img_bookmark.setImageResource(R.drawable.bookmarkkitchen);
                                        }

                                        JSONArray JaDealList = ProfileInfo.getJSONArray("DealList");
                                        if (JaDealList.length() > 0) {
                                            for (int m = 0; m < JaDealList.length(); m++) {
                                                // set values for custom dialog components - text, image and button
                                                TextView title = (TextView) dialog.findViewById(R.id.txt_title);
                                                TextView desc = (TextView) dialog.findViewById(R.id.txt_desc);
                                                TextView discount = (TextView) dialog.findViewById(R.id.txt_discount);
                                                TextView from = (TextView) dialog.findViewById(R.id.txt_from);
                                                TextView to = (TextView) dialog.findViewById(R.id.txt_to);
                                                title.setText(JaDealList.getJSONObject(m).getString("DealTitle"));
                                                desc.setText(JaDealList.getJSONObject(m).getString("Description"));

                                                String textfile = "" + JaDealList.getJSONObject(m).getString("StartDate");

                                                Date apidate = null;
                                                DateFormat datebeforeFormat = new SimpleDateFormat("MM-dd-yyyy",Locale.ENGLISH);
                                                try {
                                                    apidate = datebeforeFormat.parse(textfile);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                                                // format the date into another format
                                                String dateStr = destDf.format(apidate);

                                                System.out.println("Converted date is : " + dateStr);

                                                from.setText(dateStr);


                                                String textfilee = "" + JaDealList.getJSONObject(m).getString("EndDate");

                                                Date apidatee = null;
                                                DateFormat datebeforeFormatt = new SimpleDateFormat("MM-dd-yyyy",Locale.ENGLISH);
                                                try {
                                                    apidatee = datebeforeFormatt.parse(textfilee);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                DateFormat destDff = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                                                // format the date into another format
                                                String dateStrr = destDff.format(apidatee);

                                                System.out.println("Converted date is : " + dateStrr);

                                                to.setText(dateStrr);


                                                if (JaDealList.getJSONObject(m).getString("DiscountType").equalsIgnoreCase("$")) {
                                                    txt_dealOffer.setText(JaDealList.getJSONObject(m).getString("DealTitle"));
                                                    discount.setText("Discount " + "$" + JaDealList.getJSONObject(m).getString("DealDiscount") + " on minimum Order Of " + "$" + JaDealList.getJSONObject(m).getString("MinAmount"));
                                                } else {
                                                    Double obj = Double.parseDouble(JaDealList.getJSONObject(m).getString("DealDiscount"));
                                                    txt_dealOffer.setText(JaDealList.getJSONObject(m).getString("DealTitle"));
                                                    discount.setText("Discount " + obj.intValue() + "%" + " on minimum Order Of " + "$" + JaDealList.getJSONObject(m).getString("MinAmount"));
                                                }

                                                txtValid.setText("Valid until " + parseDateToddMMyyyy(JaDealList.getJSONObject(m).getString("EndDate")));
                                                DealDiscount = JaDealList.getJSONObject(m).getString("DealDiscount");
                                                DiscountType = JaDealList.getJSONObject(m).getString("DiscountType");
                                                MinAmount = JaDealList.getJSONObject(m).getString("MinAmount");
                                            }
                                            ll_dealsParent.setVisibility(View.VISIBLE);
                                        } else {
                                            ll_dealsParent.setVisibility(View.GONE);
                                        }

                                        if (ParentId > 0) {
                                            ll_business_parentView.setVisibility(View.VISIBLE);
                                        } else {
                                            ll_business_parentView.setVisibility(View.GONE);
                                        }


                                        mTabel.putString("cheflat", ProfileInfo.getString("Latitude"));
                                        mTabel.putString("cheflong", ProfileInfo.getString("Longitude"));
                                        mTabel.putString("businesstype", ProfileInfo.getString("BusinessType"));
                                        BusinessType=ProfileInfo.getString("BusinessType");
                                        mTabel.putString("booking_radius", String.valueOf(ProfileInfo.getDouble("BookingRadius")));
                                        mTabel.commit();

                                        Location locationA = new Location("point A");
                                        Location locationB = new Location("point B");

                                        locationA.setLatitude(Double.parseDouble(basicInformation.getUserLatitude()));
                                        locationA.setLongitude(Double.parseDouble(basicInformation.getUserLongitude()));

                                        locationB.setLatitude(Double.parseDouble(ProfileInfo.getString("Latitude")));
                                        locationB.setLongitude(Double.parseDouble(ProfileInfo.getString("Longitude")));

                                        float distance = locationA.distanceTo(locationB);

                                        double miles = distance * 0.00062137119;
                                        System.out.println("Miles: " + miles);
                                        System.out.println("meter" + distance);

                                        boolean chef_bool = false;

                                        if (ProfileInfo.getDouble("BookingRadius") <= miles) {
                                            ll_cant_del_chef.setVisibility(View.VISIBLE);
                                            chef_bool = true;

                                        } else {
                                            ll_cant_del_chef.setVisibility(View.GONE);
                                            chef_bool = false;
                                        }

                                        if(chef_bool)
                                        {
                                            txt_message_chef.setText("This "+ProfileInfo.getString("BusinessType")+" is not available at your location");
                                            btn_book_online.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            if(ProfileInfo.getString("IsMenu").equalsIgnoreCase("0")&&ProfileInfo.getString("BusinessType").equalsIgnoreCase("Chef")){
                                                btn_book_online.setVisibility(View.GONE);
                                            }else{
                                                btn_book_online.setVisibility(View.VISIBLE);
                                            }

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else if (obj.getString("ReturnCode").equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        btn_book_online.setVisibility(View.GONE);
                                       // Toast.makeText(mContext, obj.getString("ReturnMessage"), Toast.LENGTH_SHORT).show();
                                        displayAlert(mContext, obj.getString("ReturnMessage"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        else if (obj.getString("ReturnCode").equals("5")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("Sessionexp", 1);
                                            startActivity(intent);
                                        }});
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        } else if (from.toString().equalsIgnoreCase("UserBookmarkInsert")) {

            switch (ServerResult) {
                case "-1":
                case "-2":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nointernet();
                        }
                    });
                    break;
                case "-3":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });

                    break;
                default:
                    break;
            }


        } else if (from.toString().equalsIgnoreCase("UserBookmarkDeleteNew")) {
            switch (ServerResult) {
                case "-1":
                case "-2":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nointernet();
                        }
                    });
                    break;
                case "-3":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                         //   Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });
                    break;

                default:
                    break;
            }
        }
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "MM-dd-yyyy hh:mm a";
        String outputPattern = "MMM, dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(ChefProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings = (Button) dialog.findViewById(R.id.not_settings);
        Button retry = (Button) dialog.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                load_dialog.show();
                GetChefProfile oGetChefProfile = new GetChefProfile();
                oGetChefProfile.setSessionToken(basicInformation.getSessionToken());
                oGetChefProfile.setUserId(basicInformation.getUserId());
                oGetChefProfile.setCurrentDate(date_format);
                oGetChefProfile.setChefId(getIntent().getStringExtra("ChefId"));
                String jsonString = oGson.toJson(oGetChefProfile, GetChefProfile.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetChefProfile);
                oInsertUpdateApi.executePostRequest(API.fGetChefProfile(), jsonString);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        dialog.show();

    }
}
