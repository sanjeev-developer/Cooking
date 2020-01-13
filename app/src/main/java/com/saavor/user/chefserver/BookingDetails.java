package com.saavor.user.chefserver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.vision.text.Line;
import com.saavor.user.Model.GetBookingDetailHistory;
import com.saavor.user.Model.Slots;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.adapter.BookingDetailsAdapter;
import com.saavor.user.chefserver.adapter.ServiceDetailsAdapter;
import com.saavor.user.chefserver.needhelp.NeedHelpActivity;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class BookingDetails extends BaseActivity implements OnResultReceived {

    ImageView img_back_od;
    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;

    private TextView txtUser_name;
    private TextView txtUser_address;
    private TextView txt_Event_Name;
    private TextView txt_No_Guest;
    private TextView txt_Address;

    private TextView txt_CookingCharges;
    private TextView txt_SalesTax;
    private TextView txt_SalesTax_Value;
    private TextView txt_Discount;
    private TextView txt_tip;
    private TextView txt_Total;
    private TextView txtInstructions;
    private TextView txt_card_number;
    private TextView txt_BookingID;
    private TextView txt_SubTotal;
    private TextView chef_contact;

    private LinearLayout ll_ServiceDetails;

    private RecyclerView rv_items_in_cart;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> aLDishName;
    ServiceDetailsAdapter serviceDetailsAdapter;
    ArrayList<Slots> mSavedBeaconList;

    private RecyclerView rvBookingDetails;
    private BookingDetailsAdapter bookingDetailsAdapter;

    private LinearLayout ll_cooking_inst;
    private Button btn_order_need;

    String BookingId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        InitializeInterface();

        chef_contact=(TextView)findViewById(R.id.chef_contact);
        txtUser_name=(TextView)findViewById(R.id.txtUser_name);
        txtUser_address=(TextView)findViewById(R.id.txtUser_address);
        txt_Event_Name=(TextView)findViewById(R.id.txt_Event_Name);
        txt_No_Guest=(TextView)findViewById(R.id.txt_No_Guest);
        txt_Address=(TextView)findViewById(R.id.txt_Address);

        txt_CookingCharges=(TextView)findViewById(R.id.txt_CookingCharges);
        txt_Discount=(TextView)findViewById(R.id.txt_Discount);
        txt_tip=(TextView)findViewById(R.id.txt_tip);
        txt_SalesTax=(TextView)findViewById(R.id.txt_SalesTax);
        txt_Total=(TextView)findViewById(R.id.txt_Total);
        txtInstructions=(TextView)findViewById(R.id.txtInstructions);
        txt_card_number=(TextView)findViewById(R.id.txt_card_number);
        txt_BookingID=(TextView)findViewById(R.id.txt_BookingID);
        txt_SalesTax_Value=(TextView)findViewById(R.id.txt_SalesTax_Value);
        txt_SubTotal=(TextView)findViewById(R.id.txt_SubTotal);

        rv_items_in_cart=(RecyclerView)findViewById(R.id.rv_items_in_cart);
        rv_items_in_cart.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(BookingDetails.this);
        rv_items_in_cart.setLayoutManager(mLayoutManager);
        rv_items_in_cart.setItemAnimator(new DefaultItemAnimator());

        rvBookingDetails = (RecyclerView) findViewById(R.id.rvBookingDetails);
        rvBookingDetails.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(BookingDetails.this);
        rvBookingDetails.setLayoutManager(mLayoutManager);
        rvBookingDetails.setItemAnimator(new DefaultItemAnimator());

        ll_ServiceDetails=(LinearLayout)findViewById(R.id.ll_ServiceDetails);

        ll_cooking_inst=(LinearLayout)findViewById(R.id.ll_cooking_inst);

        btn_order_need=(Button)findViewById(R.id.btn_order_need);
        btn_order_need.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(BookingDetails.this, NeedHelpActivity.class);
                intent.putExtra("BookingId",BookingId);
                startActivity(intent);
            }
        });
    }

    private void InitializeInterface() {
        basicfetch();

        mContext=this;
        mOnResultReceived=this;
        oGson=new Gson();

        img_back_od = (ImageView) findViewById(R.id.img_back_od);
        img_back_od.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        GetBookingDetailHistory getBookingDetailHistory=new GetBookingDetailHistory();
        getBookingDetailHistory.setCurrentDate(date_format);
        getBookingDetailHistory.setUserId(basicInformation.getUserId());
        getBookingDetailHistory.setSessionToken(basicInformation.getSessionToken());
        getBookingDetailHistory.setBookingsId(getIntent().getStringExtra("BookingsId"));

        load_dialog.show();
        String jsonString = oGson.toJson(getBookingDetailHistory, GetBookingDetailHistory.class).toString();
        PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.GetBookingDetailHistory);
        oInsertUpdateApi.executePostRequest(API.fGetBookingDetailHistory(), jsonString);
    }

    @Override
    public void dispatchString(RequestSource from,  String what) {
        load_dialog.cancel();
        ServerResult = what;
        if (from.toString().equalsIgnoreCase("GetBookingDetailHistory")) {
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
                          //  Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });

                    break;

                case "5":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }});

                    break;


                default:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final JSONObject result;
                            final JSONObject JoBookingDetails;
                            final JSONArray JaDishList;
                            final JSONArray JaSlotList;

                            try {
                                result = new JSONObject(ServerResult);
                                if (result.getString("ReturnCode").equals("1"))
                                {
                                    aLDishName=new ArrayList<String>();
                                    mSavedBeaconList=new ArrayList<Slots>();
                                    JoBookingDetails=result.getJSONObject("BookingDetails");

                                    chef_contact.setText(JoBookingDetails.getString("ContactNumber"));
                                    txt_Address.setText(JoBookingDetails.getString("CustomerLocation"));
                                    txt_card_number.setText(JoBookingDetails.getString("PaymentCardNumber"));
                                    txt_CookingCharges.setText("$"+JoBookingDetails.getString("PricePerHour"));
                                    BookingId=""+JoBookingDetails.getInt("BookingsId");
                                    txt_BookingID.setText("Booking ID: "+JoBookingDetails.getString("BookingID"));
                                    txt_Discount.setText("$"+JoBookingDetails.getString("DealDiscount"));
                                    txt_Event_Name.setText(JoBookingDetails.getString("EventTitle"));
                                    txt_No_Guest.setText(JoBookingDetails.getString("NumberOfGuest"));
                                    txt_SalesTax.setText("$"+JoBookingDetails.getString("SalesTax"));
                                    txt_SalesTax_Value.setText("SalesTax("+JoBookingDetails.getString("SalesTaxPercentage")+"%)");
                                    txt_tip.setText("$"+JoBookingDetails.getString("TipAmount"));
                                    txt_Total.setText("$"+JoBookingDetails.getString("TotalAmount"));
                                    txt_SubTotal.setText("$"+JoBookingDetails.getString("SubTotal"));
                                    txtUser_address.setText(JoBookingDetails.getString("ChefAddress"));
                                    txtUser_name.setText(JoBookingDetails.getString("ChefName")+" ("+JoBookingDetails.getString("BusinessType")+")");
                                    txtInstructions.setText(JoBookingDetails.getString("CookingInstruction"));
                                    if(JoBookingDetails.getString("CookingInstruction").equalsIgnoreCase("")){
                                        ll_cooking_inst.setVisibility(View.GONE);

                                    }
                                    JaDishList=JoBookingDetails.getJSONArray("DishList");

                                    if(!JoBookingDetails.getString("BusinessType").equalsIgnoreCase("Chef")){
                                        ll_ServiceDetails.setVisibility(View.GONE);
                                    }

                                    for(int k=0;k<JaDishList.length();k++){
                                        aLDishName.add((k+1)+") "+JaDishList.getJSONObject(k).getString("DishName"));
                                    }

                                    serviceDetailsAdapter=new ServiceDetailsAdapter(BookingDetails.this,aLDishName);
                                    rv_items_in_cart.setAdapter(serviceDetailsAdapter);

                                    JaSlotList=JoBookingDetails.getJSONArray("SlotList");
                                    for(int m=0;m<JaSlotList.length();m++){
                                        Slots slots=new Slots();
                                        slots.setStartTime(JaSlotList.getJSONObject(m).getString("StartTime"));
                                        slots.setEndTime(JaSlotList.getJSONObject(m).getString("EndTime"));
                                        mSavedBeaconList.add(slots);
                                    }

                                    ArrayList<String> arrayListDates = new ArrayList<String>();
                                    ArrayList<String> arrayListSlots = new ArrayList<String>();
                                    ArrayList<String> arrayListSlots2 = new ArrayList<String>();

                                    for (int m = 0; m < mSavedBeaconList.size(); m++) {
                                        if (arrayListDates.contains(DateToddMMyyyy(mSavedBeaconList.get(m).getStartTime())) == false) {
                                            arrayListDates.add(DateToddMMyyyy(mSavedBeaconList.get(m).getEndTime()));
                                        }
                                    }

                              /*  for (int i = 0; i < arrayListDates.size(); i++) {
                                    String date = arrayListDates.get(i);
                                    String slot = "";
                                    for (int j = 0; j < mSavedBeaconList.size(); j++) {
                                        if (date.equalsIgnoreCase(DateToddMMyyyy(mSavedBeaconList.get(j).getStartTime()))) {
                                            String StartTime = (DateToddMMyyyy2(mSavedBeaconList.get(j).getStartTime()));
                                            String EndTime = (DateToddMMyyyy2(mSavedBeaconList.get(j).getEndTime()));
                                            if (slot.equalsIgnoreCase("")) {
                                                slot = StartTime + "-" + EndTime;
                                            } else {
                                                slot = slot + ", " + StartTime + "-" + EndTime;
                                            }
                                        }
                                    }
                                    arrayListSlots.add(slot);
                                }*/


                                    for (int i = 0; i < arrayListDates.size(); i++) {
                                        String date = arrayListDates.get(i);
                                        String slot = "";
                                        arrayListSlots=new ArrayList<>();
                                        for (int j = 0; j < mSavedBeaconList.size(); j++) {
                                            if (date.equalsIgnoreCase(DateToddMMyyyy(mSavedBeaconList.get(j).getStartTime()))) {
                                                String StartTime = (DateToddMMyyyy2(mSavedBeaconList.get(j).getStartTime()));
                                                String EndTime = (DateToddMMyyyy2(mSavedBeaconList.get(j).getEndTime()));
                                                if (slot.equalsIgnoreCase("")) {
                                                    slot = StartTime + "-" + EndTime;
                                                } else {
                                                    slot = slot + ", " + StartTime + "-" + EndTime;
                                                }
                                            }
                                        }
                                        arrayListSlots.add(slot);

                                        String startedDate=arrayListSlots.get(0).toString();
                                        startedDate=startedDate.substring(0, 8);

                                        String endDate=arrayListSlots.get(arrayListSlots.size()-1).toString();
                                        endDate=endDate.substring(endDate.length() - 8);

                                        arrayListSlots2.add(startedDate+" to "+endDate);
                                    }

                                    bookingDetailsAdapter = new BookingDetailsAdapter(BookingDetails.this, arrayListDates, arrayListSlots2,true);
                                    rvBookingDetails.setAdapter(bookingDetailsAdapter);

                                }
                                else if (result.getString("ReturnCode").equals("5")) {
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
                    });
                    break;
            }
        }


    }

    public String DateToddMMyyyy2(String time) {
        String inputPattern = "MM-dd-yyyy hh:mm a";
        String outputPattern = "hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);

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

    //01-01-2018 12:00 AM
    public String DateToddMMyyyy(String time) {
        String inputPattern = "MM-dd-yyyy hh:mm a";
        String outputPattern = "MMM, dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);

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

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(BookingDetails.this);
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

                GetBookingDetailHistory getBookingDetailHistory=new GetBookingDetailHistory();
                getBookingDetailHistory.setCurrentDate(date_format);
                getBookingDetailHistory.setUserId(basicInformation.getUserId());
                getBookingDetailHistory.setSessionToken(basicInformation.getSessionToken());
                getBookingDetailHistory.setBookingsId(getIntent().getStringExtra("BookingsId"));

                load_dialog.show();
                String jsonString = oGson.toJson(getBookingDetailHistory, GetBookingDetailHistory.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetBookingDetailHistory);
                oInsertUpdateApi.executePostRequest(API.fGetBookingDetailHistory(), jsonString);

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
