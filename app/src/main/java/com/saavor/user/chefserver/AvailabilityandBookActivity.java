package com.saavor.user.chefserver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saavor.user.Model.ChefSearch;
import com.saavor.user.Model.GetChefProfile;
import com.saavor.user.Model.Slots;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.CardBook;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.adapter.PickSlotAdapter;
import com.saavor.user.chefserver.filters.CuisineLists;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AvailabilityandBookActivity extends BaseActivity implements View.OnClickListener, OnResultReceived {

    RecyclerView rv_pickSlot;
    PickSlotAdapter oPickSlotAdapter;
    public static LinearLayout llBookForFullDay;
    public static ImageView imgBookForDay;
    Button btn_next;
    ImageView action_back;
    boolean retry_api=false;


    ArrayList<Slots> aLSlots = new ArrayList<Slots>();

    ImageView imgBack;
    ImageView imgNext;

    TextView txtDate;

    SharedPreferences shared;
    int count = 0;
    int removedCount = 0;

    LinearLayout ll_parentView;
    LinearLayout ll_NoSlot;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;

    ArrayList<Boolean> aLBoolean = new ArrayList<Boolean>();
    ArrayList<String> slotStr = new ArrayList<String>();

    SharedPreferences.Editor editor1;
    ArrayList<Slots> aLAddedSlots = new ArrayList<Slots>();

    ArrayList<String> arrayListBookedSlots = new ArrayList<>();

    boolean IsFullDayAvail = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availabilityand_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitializeInterface();
    }

    private void InitializeInterface() {
        basicfetch();

        SharedPreferences preferencesSLOTS_LIST = getSharedPreferences("SLOTS_LIST", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesSLOTS_LIST.edit();
        editor.clear();
        editor.commit();

        editor1 = getSharedPreferences("SLOTS_LIST", MODE_PRIVATE).edit();

        mContext = this;
        oGson = new Gson();
        mOnResultReceived = this;

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);

        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgNext.setOnClickListener(this);

        txtDate = (TextView) findViewById(R.id.txtDate);

        rv_pickSlot = (RecyclerView) findViewById(R.id.rv_pickSlot);
        rv_pickSlot.setNestedScrollingEnabled(false);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv_pickSlot.setLayoutManager(layoutManager);
        rv_pickSlot.setItemAnimator(new DefaultItemAnimator());


        llBookForFullDay = (LinearLayout) findViewById(R.id.llBookForFullDay);
        llBookForFullDay.setOnClickListener(this);

        imgBookForDay = (ImageView) findViewById(R.id.imgBookForDay);
        action_back = (ImageView) findViewById(R.id.action_back);
        action_back.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        ll_parentView = (LinearLayout) findViewById(R.id.ll_parentView);
        ll_NoSlot=(LinearLayout)findViewById(R.id.ll_NoSlot);

        shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
        if (!shared.getString("BookingDateTime", "").equalsIgnoreCase("") && !shared.getString("BookingDateTime", "").equalsIgnoreCase("Select Date & Time")) {
            txtDate.setText(parseDateToddMMyyyy2(shared.getString("BookingDateTime", "")));
        }else {
            txtDate.setText(parseDateToddMMyyyy2(date_format));
        }


        if (getIntent().getStringExtra("AvailableFrom").equalsIgnoreCase("")) {
            ll_parentView.setVisibility(View.GONE);
            ll_NoSlot.setVisibility(View.VISIBLE);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
            try {
                Date Date1 = sdf.parse(getIntent().getStringExtra("AvailableFrom").toLowerCase());
                Date Date2 = sdf.parse(getIntent().getStringExtra("AvailableTo").toLowerCase());
                long mills = Date1.getTime() - Date2.getTime();
                long millis = Math.abs(mills);
                int hours = (int) millis / (1000 * 60 * 60);

                slotStr = new ArrayList<String>();
                aLSlots = new ArrayList<Slots>();
                aLBoolean = new ArrayList<Boolean>();
                String availableFromDateStr = getTodaysDate() + " " + getIntent().getStringExtra("AvailableFrom");
                if (hours > 0) {
                    SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH);
                    Date startDate = df.parse(availableFromDateStr);
                    for (int i = 0; i < hours; i++) {

                        Calendar c = Calendar.getInstance();
                        c.setTime(startDate);
                        c.add(Calendar.HOUR, 1);

                        availableFromDateStr = df.format(startDate);

                        String finalAvailableFromDateStr = parseDateToddMMyyyy(availableFromDateStr);

                        String finalAvailableToDateStr = parseDateToddMMyyyy(df.format(c.getTime()));

                        Slots oslots = new Slots();
                        oslots.setStartTime(txtDate.getText().toString() + " " + finalAvailableFromDateStr);
                        oslots.setEndTime(txtDate.getText().toString() + " " + finalAvailableToDateStr);

                        aLSlots.add(oslots);
                        aLBoolean.add(false);

                        slotStr.add(finalAvailableFromDateStr + " - " + finalAvailableToDateStr);
                        startDate = c.getTime();


                        String dtStart = txtDate.getText().toString();
                        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                        Calendar cal = null;
                        try {
                            Date date = format.parse(dtStart);
                            cal = Calendar.getInstance();
                            cal.setTime(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (System.currentTimeMillis() > cal.getTimeInMillis()) {
                            if (System.currentTimeMillis() > c.getTimeInMillis()) {
                                IsFullDayAvail = false;
                                arrayListBookedSlots.add(finalAvailableFromDateStr + " - " + finalAvailableToDateStr);
                            }
                        }
                    }


                    String BookedSlots = getIntent().getStringExtra("BookedSlots");
                    if(!BookedSlots.equalsIgnoreCase("")){
                        Type listOfBecons = new TypeToken<List<Slots>>() {}.getType();
                        ArrayList<Slots> mSavedBeaconList = new Gson().fromJson(BookedSlots, listOfBecons);
                        for (int m = 0; m < mSavedBeaconList.size(); m++) {
                            IsFullDayAvail = false;
                            arrayListBookedSlots.add(mSavedBeaconList.get(m).getStartTime() + " - " + mSavedBeaconList.get(m).getEndTime());
                        }
                    }

                    if(Arrays.asList(aLBoolean).contains(false)){
                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,false);
                    }else{
                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,true);
                    }

                   // oPickSlotAdapter = new PickSlotAdapter(this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots);
                    rv_pickSlot.setAdapter(oPickSlotAdapter);
                }else{
                    ll_parentView.setVisibility(View.GONE);
                    ll_NoSlot.setVisibility(View.VISIBLE);
                }

                if(aLSlots.size() <= arrayListBookedSlots.size())
                {
                    btn_next.setVisibility(View.GONE);
                }
                else
                {
                    btn_next.setVisibility(View.VISIBLE);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (!IsFullDayAvail) {
            llBookForFullDay.setVisibility(View.GONE);
        }else{
            llBookForFullDay.setVisibility(View.VISIBLE);
        }

    }

    public String parseDateToddMMyyyy3(String time) {
        String inputPattern = "dd-MMM-yyyy hh:mm:ss a";
        String outputPattern = "MMM dd, yyyy";
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
            String outputPattern2 = "MMM dd, yyyy";
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

    public String parseDateToddMMyyyy2(String time) {
        String inputPattern = "MMM dd,yyyy hh:mm a";
        String outputPattern = "MMM dd, yyyy";
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
            String outputPattern2 = "MMM dd, yyyy";
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

    public String getTodaysDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "MMM dd, yyyy hh:mm a";
        String outputPattern = "HH:mm";
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
            String outputPattern2 = "MMM dd, yyyy";
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

    public String getTime(String time) {
        String inputPattern = "hh:mm a";
        String outputPattern = "HH:mm";
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgBack:

                retry_api=false;
                backbuttonapi();
                break;

            case R.id.imgNext:

                retry_api=true;
                nextbuttonapi();

                break;

            case R.id.action_back:
                onBackPressed();
                break;

            case R.id.btn_next:

                oPickSlotAdapter.checkvalidate();

                break;

            case R.id.llBookForFullDay:
                if (llBookForFullDay.getTag().equals("0")) {
                    llBookForFullDay.setTag("1");
                    imgBookForDay.setImageResource(R.drawable.selected);
//                    for (int m = 0; m < aLBoolean.size(); m++) {
//                        aLBoolean.set(m, true);
//                    }
//
//                    //  stored slots arrayList
//                    if (aLSlots != null) {
//                        for (int m = 0; m < aLSlots.size(); m++) {
//                            Slots obj = new Slots();
//                            obj.setStartTime(aLSlots.get(m).getStartTime());
//                            obj.setEndTime(aLSlots.get(m).getEndTime());
//                            aLAddedSlots.add(obj);
//                        }
//                        Type listOfBeconss = new TypeToken<List<Slots>>() {
//                        }.getType();
//                        String strBecons = new Gson().toJson(aLAddedSlots, listOfBeconss);
//                        editor1.putString("LIST", strBecons).apply();
//                        editor1.commit();
//                    }
//
//                    if(Arrays.asList(aLBoolean).contains(false)){
//                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,false);
//                    }else{
//                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,true);
//                    }
//                   // oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots);
//                    rv_pickSlot.setAdapter(oPickSlotAdapter);

                    oPickSlotAdapter.bookall(true);


                } else {
                    llBookForFullDay.setTag("0");
                    oPickSlotAdapter.bookall(false);
                    imgBookForDay.setImageResource(R.drawable.dselected);
//                    for (int m = 0; m < aLBoolean.size(); m++) {
//                        aLBoolean.set(m, false);
//                    }
//
//                    // Get stored slots arrayList
//                    aLAddedSlots = new ArrayList<Slots>();
//                    SharedPreferences shared2 = getSharedPreferences("SLOTS_LIST", MODE_PRIVATE);
//                    Type listOfBecons = new TypeToken<List<Slots>>() {
//                    }.getType();
//                    ArrayList<Slots> mSavedBeaconList = new Gson().fromJson(shared2.getString("LIST", ""), listOfBecons);
//                    if (mSavedBeaconList != null) {
//                        for (int m = 0; m < mSavedBeaconList.size(); m++) {
//                            Slots obj = new Slots();
//                            obj.setStartTime(mSavedBeaconList.get(m).getStartTime());
//                            obj.setEndTime(mSavedBeaconList.get(m).getEndTime());
//                            aLAddedSlots.add(obj);
//                        }
//                    }
//
//                    //  removed slots arrayList
//                    if (aLSlots != null) {
//                        for (int m = 0; m < aLSlots.size(); m++) {
//                            for (int j = 0; j < aLAddedSlots.size(); j++) {
//                                Slots obj = aLAddedSlots.get(j);
//                                if (obj.getStartTime().equals(aLSlots.get(m).getStartTime())) {
//                                    //found, delete.
//                                    aLAddedSlots.remove(j);
//                                    removedCount++;
//                                } else {
//                                    String obaj = "";
//                                }
//                            }
//                        }
//
//                        Type listOfBeconss = new TypeToken<List<Slots>>() {
//                        }.getType();
//                        String strBecons = new Gson().toJson(aLAddedSlots, listOfBeconss);
//                        editor1.putString("LIST", strBecons).apply();
//                        editor1.commit();
//                    }
//
//
//                    if(Arrays.asList(aLBoolean).contains(false)){
//                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,false);
//                    }else{
//                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,true);
//                    }
//
//                    //oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots);
//                    rv_pickSlot.setAdapter(oPickSlotAdapter);
                }
                break;
        }
    }


    public String DateToddMMyyyy(String time) {
        String inputPattern = "MMM dd, yyyy hh:mm";
        String outputPattern = "MMM dd, yyyy";
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

    @Override
    public void dispatchString(RequestSource from, final String what) {
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
                case "5":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }});

                    break;
                case "-3":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        //    Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });

                    break;

                default:

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final JSONObject result;
                            try {
                                result = new JSONObject(what);
                                if (result.getString("ReturnCode").equals("1")) {

                                    ArrayList<Slots> FilterAlList = new ArrayList<Slots>();

                                    // Get stored slots arrayList
                                    SharedPreferences shared = getSharedPreferences("SLOTS_LIST", MODE_PRIVATE);
                                    Type listOfBecons = new TypeToken<List<Slots>>() {
                                    }.getType();
                                    ArrayList<Slots> mSavedBeaconList = new Gson().fromJson(shared.getString("LIST", ""), listOfBecons);

                                    if (mSavedBeaconList != null) {
                                        for (int m = 0; m < mSavedBeaconList.size(); m++) {
                                            if (DateToddMMyyyy(mSavedBeaconList.get(m).getStartTime()).equalsIgnoreCase(txtDate.getText().toString())) {
                                                Slots objSlots = new Slots();
                                                objSlots.setEndTime(mSavedBeaconList.get(m).getEndTime());
                                                objSlots.setStartTime(mSavedBeaconList.get(m).getStartTime());
                                                FilterAlList.add(objSlots);
                                            }
                                        }
                                    }

                                    JSONObject ProfileInfo;
                                    JSONArray BookingSlots;
                                    ProfileInfo = result.getJSONObject("ProfileInfo");
                                    BookingSlots = ProfileInfo.getJSONArray("BookingSlots");
                                    arrayListBookedSlots = new ArrayList<String>();
                                    for (int m = 0; m < BookingSlots.length(); m++) {
                                        IsFullDayAvail = false;
                                     /*   arrayListBookedSlots.add(getTime(BookingSlots.getJSONObject(m).getString("SlotStartTime")) + " - " + getTime(BookingSlots.getJSONObject(m).getString("SlotEndTime")));*/
                                        arrayListBookedSlots.add((BookingSlots.getJSONObject(m).getString("SlotStartTime")) + " - " + (BookingSlots.getJSONObject(m).getString("SlotEndTime")));
                                    }

                                    if (!ProfileInfo.getString("AvailableFrom").toLowerCase().equalsIgnoreCase("")) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
                                        try {
                                            Date Date1 = sdf.parse(ProfileInfo.getString("AvailableFrom").toLowerCase());
                                            Date Date2 = sdf.parse(ProfileInfo.getString("AvailableTo").toLowerCase());
                                            long mills = Date1.getTime() - Date2.getTime();
                                            long millis = Math.abs(mills);
                                            int hours = (int) millis / (1000 * 60 * 60);

                                            slotStr = new ArrayList<String>();
                                            aLSlots = new ArrayList<Slots>();
                                            aLBoolean = new ArrayList<Boolean>();

                                            String availableFromDateStr = getTodaysDate() + " " + ProfileInfo.getString("AvailableFrom");
                                            if (hours > 0) {
                                                SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH);
                                                Date startDate = df.parse(availableFromDateStr);
                                                for (int i = 0; i < hours; i++) {

                                                    Calendar c = Calendar.getInstance();
                                                    c.setTime(startDate);
                                                    c.add(Calendar.HOUR, 1);

                                                    availableFromDateStr = df.format(startDate);

                                                    String finalAvailableFromDateStr = parseDateToddMMyyyy(availableFromDateStr);

                                                    String finalAvailableToDateStr = parseDateToddMMyyyy(df.format(c.getTime()));


                                                    Slots oslots = new Slots();
                                                    oslots.setStartTime(txtDate.getText().toString() + " " + finalAvailableFromDateStr);
                                                    oslots.setEndTime(txtDate.getText().toString() + " " + finalAvailableToDateStr);

                                                    aLSlots.add(oslots);

                                                    slotStr.add(finalAvailableFromDateStr + " - " + finalAvailableToDateStr);

                                                    startDate = c.getTime();


                                                    String dtStart = txtDate.getText().toString();
                                                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                                                    Calendar cal = null;
                                                    try {
                                                        Date date = format.parse(dtStart);
                                                        cal = Calendar.getInstance();
                                                        cal.setTime(date);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    aLBoolean.add(false);
                                                    if (System.currentTimeMillis() > cal.getTimeInMillis()) {
                                                        if (System.currentTimeMillis() > c.getTimeInMillis()) {
                                                           /* aLSlots.remove(oslots);
                                                            slotStr.remove(finalAvailableFromDateStr + " - " + finalAvailableToDateStr);*/
                                                            IsFullDayAvail = false;
                                                            arrayListBookedSlots.add(finalAvailableFromDateStr + " - " + finalAvailableToDateStr);
                                                        } else {
                                                            for (int n = 0; n < FilterAlList.size(); n++) {
                                                                if (FilterAlList.get(n).getStartTime().equalsIgnoreCase(txtDate.getText().toString() + " " + finalAvailableFromDateStr) && FilterAlList.get(n).getEndTime().equalsIgnoreCase(txtDate.getText().toString() + " " + finalAvailableToDateStr)) {
                                                                    aLBoolean.set(i, true);
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        for (int n = 0; n < FilterAlList.size(); n++) {
                                                            if (FilterAlList.get(n).getStartTime().equalsIgnoreCase(txtDate.getText().toString() + " " + finalAvailableFromDateStr) && FilterAlList.get(n).getEndTime().equalsIgnoreCase(txtDate.getText().toString() + " " + finalAvailableToDateStr)) {
                                                                aLBoolean.set(i, true);
                                                            }
                                                        }
                                                    }
                                                }
                                                if (!IsFullDayAvail) {
                                                    llBookForFullDay.setVisibility(View.GONE);
                                                }else{
                                                    llBookForFullDay.setVisibility(View.VISIBLE);
                                                }

                                                ll_parentView.setVisibility(View.VISIBLE);
                                                ll_NoSlot.setVisibility(View.GONE);


                                                if(Arrays.asList(aLBoolean).contains(false)){
                                                    oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,false);
                                                }else{
                                                    oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,true);
                                                }


                                                rv_pickSlot.setAdapter(oPickSlotAdapter);
                                            }else{
                                                ll_parentView.setVisibility(View.GONE);
                                                ll_NoSlot.setVisibility(View.VISIBLE);
                                            }

                                            if(aLSlots.size() <= arrayListBookedSlots.size())
                                            {
                                                btn_next.setVisibility(View.GONE);
                                            }
                                            else
                                            {
                                                btn_next.setVisibility(View.VISIBLE);
                                            }

                                            boolean allTrue = true;
                                            for (boolean b : aLBoolean) {
                                                if (!b) {
                                                    allTrue = false;
                                                    break;
                                                }
                                            }

                                            if (allTrue) {
                                                llBookForFullDay.setTag("1");
                                                imgBookForDay.setImageResource(R.drawable.selected);
                                            } else {
                                                llBookForFullDay.setTag("0");
                                                imgBookForDay.setImageResource(R.drawable.dselected);
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        ll_parentView.setVisibility(View.GONE);
                                        ll_NoSlot.setVisibility(View.VISIBLE);
                                        ArrayList<String> slotStr = new ArrayList<String>();
                                        aLSlots = new ArrayList<Slots>();
                                        aLBoolean = new ArrayList<Boolean>();
                                        oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,false);
                                        rv_pickSlot.setAdapter(oPickSlotAdapter);
                                    }

                                    //oPickSlotAdapter.dataset();
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
                                else {
                                    ll_parentView.setVisibility(View.GONE);
                                    ll_NoSlot.setVisibility(View.VISIBLE);
                                    aLSlots = new ArrayList<Slots>();
                                    aLBoolean = new ArrayList<Boolean>();
                                    ArrayList<String> slotStr = new ArrayList<String>();
                                    oPickSlotAdapter = new PickSlotAdapter(AvailabilityandBookActivity.this, slotStr, aLSlots, txtDate.getText().toString(), aLBoolean, arrayListBookedSlots,false);
                                    rv_pickSlot.setAdapter(oPickSlotAdapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }
        }
    }

    public void nointernet()
    {
        //dialog intialization
        final Dialog dialog = new Dialog(AvailabilityandBookActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.chart_no_internet);
        dialog.setCancelable(false);

        Button settings=(Button)dialog.findViewById(R.id.not_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        dialog.show();

    }

    public void nextbuttonapi()
    {
        IsFullDayAvail = true;
        count++;
        if (count <= 30) {
            llBookForFullDay.setTag("0");
            imgBookForDay.setImageResource(R.drawable.dselected);

            imgBack.setVisibility(View.VISIBLE);
            SimpleDateFormat format;
                    /*if(!shared.getString("BookingDateTime", "").equalsIgnoreCase("")&&!shared.getString("BookingDateTime", "").equalsIgnoreCase("Select Date & Time")){
                         format = new SimpleDateFormat("MM-dd-yyyy hh:mm",Locale.ENGLISH);
                    }else{
                        format = new SimpleDateFormat("MMM dd,yyyy hh:mm aa",Locale.ENGLISH);
                    }*/

            format = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);
            try {
                Date date = null;
                if (!shared.getString("BookingDateTime", "").equalsIgnoreCase("") && !shared.getString("BookingDateTime", "").equalsIgnoreCase("Select Date & Time")) {
                    date = format.parse(shared.getString("BookingDateTime", ""));
                } else {
                    date = format.parse(date_format);
                }
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, count);

                SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                String formattedDate = df.format(c.getTime());
                txtDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            load_dialog.show();
            GetChefProfile oGetChefProfile = new GetChefProfile();
            oGetChefProfile.setSessionToken(basicInformation.getSessionToken());
            oGetChefProfile.setUserId(basicInformation.getUserId());
            oGetChefProfile.setCurrentDate(txtDate.getText().toString());
            oGetChefProfile.setChefId(getIntent().getStringExtra("ChefId"));

            String jsonString = oGson.toJson(oGetChefProfile, GetChefProfile.class).toString();
            PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
            oInsertUpdateApi.setRequestSource(RequestSource.GetChefProfile);
            oInsertUpdateApi.executePostRequest(API.fGetChefProfile(), jsonString);
        }

        if (count < 30) {
        } else {
            imgNext.setVisibility(View.INVISIBLE);
        }
    }

    public void backbuttonapi()
    {
        IsFullDayAvail = true;
        imgNext.setVisibility(View.VISIBLE);
        count--;
        if (count >= 0) {

            llBookForFullDay.setTag("0");
            imgBookForDay.setImageResource(R.drawable.dselected);

            SimpleDateFormat format2;
                   /* if(!shared.getString("BookingDateTime", "").equalsIgnoreCase("")&&!shared.getString("BookingDateTime", "").equalsIgnoreCase("Select Date & Time")){
                        format2 = new SimpleDateFormat("MM-dd-yyyy hh:mm",Locale.ENGLISH);
                    }else{
                        format2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a",Locale.ENGLISH);
                    }*/
            format2 = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);


            try {
                Date date = null;
                if (!shared.getString("BookingDateTime", "").equalsIgnoreCase("") && !shared.getString("BookingDateTime", "").equalsIgnoreCase("Select Date & Time")) {
                    date = format2.parse(shared.getString("BookingDateTime", ""));
                } else {
                    date = format2.parse(date_format);
                }
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, count);

                SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                String formattedDate = df.format(c.getTime());


                txtDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            load_dialog.show();
            GetChefProfile oGetChefProfile = new GetChefProfile();
            oGetChefProfile.setSessionToken(basicInformation.getSessionToken());
            oGetChefProfile.setUserId(basicInformation.getUserId());
            oGetChefProfile.setCurrentDate(txtDate.getText().toString());
            oGetChefProfile.setChefId(getIntent().getStringExtra("ChefId"));

            String jsonString = oGson.toJson(oGetChefProfile, GetChefProfile.class).toString();
            PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
            oInsertUpdateApi.setRequestSource(RequestSource.GetChefProfile);
            oInsertUpdateApi.executePostRequest(API.fGetChefProfile(), jsonString);
        }

        if (count > 0) {
        } else {
            imgBack.setVisibility(View.INVISIBLE);
        }


    }

    public void nextbutton(boolean decision) {


        if(decision)
        {
            SharedPreferences shared = getSharedPreferences("SLOTS_LIST", MODE_PRIVATE);
            String Value = (shared.getString("LIST", ""));
            if (Value.equalsIgnoreCase("") || Value.equalsIgnoreCase("[]")) {
                //  Toast.makeText(getApplication(), "Please select Slot!", Toast.LENGTH_SHORT).show();
                displayAlert(AvailabilityandBookActivity.this, "Please select Slot!");
            } else {
                if (getIntent().getStringExtra("BusinessType").equalsIgnoreCase("Chef")) {
                    Intent intent = new Intent(AvailabilityandBookActivity.this, ChefsMenuActivity.class);
                    intent.putExtra("ChefId", getIntent().getStringExtra("ChefId"));
                    intent.putExtra("UserName", getIntent().getStringExtra("UserName"));
                    intent.putExtra("SaleTaxPercentage", getIntent().getStringExtra("SaleTaxPercentage"));
                    intent.putExtra("DealDiscount", getIntent().getStringExtra("DealDiscount"));
                    intent.putExtra("DiscountType", getIntent().getStringExtra("DiscountType"));
                    intent.putExtra("Price", getIntent().getStringExtra("Price"));
                    intent.putExtra("MinAmount", getIntent().getStringExtra("MinAmount"));
                    intent.putExtra("BusinessType", getIntent().getStringExtra("BusinessType"));
                    intent.putStringArrayListExtra("EventsList", getIntent().getExtras().getStringArrayList("EventsList"));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AvailabilityandBookActivity.this, ChefCartActivity.class);
                    intent.putExtra("ChefId", getIntent().getStringExtra("ChefId"));
                    intent.putExtra("BusinessType", getIntent().getStringExtra("BusinessType"));
                    intent.putExtra("SaleTaxPercentage", getIntent().getStringExtra("SaleTaxPercentage"));
                    intent.putExtra("DealDiscount", getIntent().getStringExtra("DealDiscount"));
                    intent.putExtra("DiscountType", getIntent().getStringExtra("DiscountType"));
                    intent.putExtra("Price", getIntent().getStringExtra("Price"));
                    intent.putExtra("MinAmount", getIntent().getStringExtra("MinAmount"));
                    intent.putStringArrayListExtra("EventsList", getIntent().getExtras().getStringArrayList("EventsList"));
                    startActivity(intent);
                }

            }
        }
        else
        {
            displayAlert(AvailabilityandBookActivity.this, "Please select Slot!");
        }

    }
}
