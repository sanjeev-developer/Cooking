package com.saavor.user.chefserver;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.ChefSearch;
import com.saavor.user.Model.ChefSearchList;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Bookmark;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.adapter.TopChefsAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopChefsActivity extends BaseActivity implements View.OnClickListener, OnResultReceived {

    ImageView action_back;
    RecyclerView recycle_top_chef;
    TopChefsAdapter topChefAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String ServerResult;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;

    private ArrayList<ChefSearchList> aListChefSearchList;
    TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_chefs);
        InitializeInterface();
    }


    private void InitializeInterface() {
        basicfetch();

        action_back = (ImageView) findViewById(R.id.action_back);
        action_back.setOnClickListener(this);

        recycle_top_chef = (RecyclerView) findViewById(R.id.recycle_top_chef);
        recycle_top_chef.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(TopChefsActivity.this);
        recycle_top_chef.setLayoutManager(mLayoutManager);
        recycle_top_chef.setItemAnimator(new DefaultItemAnimator());
        txtContent=(TextView)findViewById(R.id.txtContent);

        oGson = new Gson();
        mOnResultReceived = this;
        mContext = this;

        DilalogModel dilalogModel = new DilalogModel();
        String dailogdata = mDatabase.getString("dialogdata", "");
        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

        if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
            txtContent.setText("Top Chef's near you");
        }else if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Server")){
            txtContent.setText("Top Server's near you");
        }else if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Bartender")){
            txtContent.setText("Top Bartender's near you");
        }


        ChefSearch oChefSearch = new ChefSearch();
        SharedPreferences shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
        if (!shared.getString("SortBy", "").equalsIgnoreCase("")) {
            oChefSearch.setSessionToken(basicInformation.getSessionToken());
            oChefSearch.setUserId(basicInformation.getUserId());
            oChefSearch.setBookingDate(shared.getString("BookingDateTime", ""));
            if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
                oChefSearch.setCuisineList(shared.getString("Cuisine", "").replace(" ", ""));
            }else{
                oChefSearch.setCuisineList("");
            }

            oChefSearch.setCurrentDate(date_format);
            oChefSearch.setDistance("0");
            oChefSearch.setEndIndex("100");
            oChefSearch.setStartIndex("0");
            oChefSearch.setLatitude(String.valueOf(dilalogModel.getLat()));
            oChefSearch.setLongitude(String.valueOf(dilalogModel.getLongi()));
            oChefSearch.setGender(shared.getString("QuickFilter", ""));
            oChefSearch.setRediusFrom(shared.getString("MinRadius", ""));
            oChefSearch.setRediusTo(shared.getString("MaxRadius", ""));
            oChefSearch.setSearchText("");

            if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
                oChefSearch.setServiceType(shared.getString("Service", ""));
            }else{
                oChefSearch.setServiceType("");
            }


            oChefSearch.setSortBy(shared.getString("SortBy", ""));
            oChefSearch.setUserType(getIntent().getStringExtra("UserType"));
        } else {
            oChefSearch.setSessionToken(basicInformation.getSessionToken());
            oChefSearch.setUserId(basicInformation.getUserId());
            oChefSearch.setBookingDate(date_format);
            oChefSearch.setCuisineList("");
            oChefSearch.setCurrentDate(date_format);
            oChefSearch.setDistance("0");
            oChefSearch.setEndIndex("100");
            oChefSearch.setStartIndex("0");
            oChefSearch.setLatitude(String.valueOf(dilalogModel.getLat()));
            oChefSearch.setLongitude(String.valueOf(dilalogModel.getLongi()));
            oChefSearch.setGender("Any Gender");
            oChefSearch.setRediusFrom("0");
            oChefSearch.setRediusTo("60");
            oChefSearch.setSearchText("");
            oChefSearch.setServiceType("");
            oChefSearch.setSortBy("Distance");
            oChefSearch.setUserType(getIntent().getStringExtra("UserType"));
        }
        load_dialog.show();

        String jsonString = oGson.toJson(oChefSearch, ChefSearch.class).toString();
        PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.ChefSearch);
        oInsertUpdateApi.executePostRequest(API.fChefSearch(), jsonString);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.action_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void dispatchString(RequestSource from, final String what) {
        load_dialog.cancel();
        ServerResult = what;

        if (from.toString().equalsIgnoreCase("ChefSearch")) {
            load_dialog.cancel();
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

                            JSONObject Jsonobject;
                            JSONArray JaChefList;
                            try {
                                Jsonobject = new JSONObject(what);
                                aListChefSearchList = new ArrayList<ChefSearchList>();
                                if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("1")) {
                                    JaChefList = Jsonobject.getJSONArray("ChefList");
                                    for (int m = 0; m < JaChefList.length(); m++) {
                                        ChefSearchList oChefSearchList = new ChefSearchList();
                                        oChefSearchList.setBookingRadius(JaChefList.getJSONObject(m).getString("BookingRadius"));
                                        oChefSearchList.setBusinessName(JaChefList.getJSONObject(m).getString("BusinessName"));
                                        oChefSearchList.setBusinessType(JaChefList.getJSONObject(m).getString("BusinessType"));
                                        oChefSearchList.setChefCode(JaChefList.getJSONObject(m).getString("ChefCode"));
                                        oChefSearchList.setChefId(JaChefList.getJSONObject(m).getString("ChefId"));
                                        oChefSearchList.setCuisineList(JaChefList.getJSONObject(m).getString("CuisineList"));
                                        oChefSearchList.setFirstName(JaChefList.getJSONObject(m).getString("FirstName"));
                                        oChefSearchList.setIsDeal(JaChefList.getJSONObject(m).getString("IsDeal"));
                                        oChefSearchList.setIsOpen(JaChefList.getJSONObject(m).getString("IsOpen"));
                                        oChefSearchList.setLastName(JaChefList.getJSONObject(m).getString("LastName"));
                                        oChefSearchList.setMI(JaChefList.getJSONObject(m).getString("MI"));
                                        oChefSearchList.setPrice(JaChefList.getJSONObject(m).getString("Price"));
                                        oChefSearchList.setProfileImagePath(JaChefList.getJSONObject(m).getString("ProfileImagePath"));
                                        oChefSearchList.setServiceList(JaChefList.getJSONObject(m).getString("ServiceList"));
                                        oChefSearchList.setStarRating(JaChefList.getJSONObject(m).getString("StarRating"));
                                        oChefSearchList.setVerifiedBookings(JaChefList.getJSONObject(m).getString("VerifiedBookings"));
                                        aListChefSearchList.add(oChefSearchList);
                                    }
                                    topChefAdapter = new TopChefsAdapter(TopChefsActivity.this, aListChefSearchList,true);
                                    recycle_top_chef.setAdapter(topChefAdapter);
                                }
                                else if (Jsonobject.getString("ReturnCode").equals("5")) {
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
                                    topChefAdapter = new TopChefsAdapter(TopChefsActivity.this, aListChefSearchList,true);
                                    recycle_top_chef.setAdapter(topChefAdapter);
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

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(TopChefsActivity.this);
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

                oGson = new Gson();

                DilalogModel dilalogModel = new DilalogModel();
                String dailogdata = mDatabase.getString("dialogdata", "");
                dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
                    txtContent.setText("Top Chef's near you");
                }else if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Server")){
                    txtContent.setText("Top Server's near you");
                }else if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Bartender")){
                    txtContent.setText("Top Bartender's near you");
                }


                ChefSearch oChefSearch = new ChefSearch();
                SharedPreferences shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
                if (!shared.getString("SortBy", "").equalsIgnoreCase("")) {
                    oChefSearch.setSessionToken(basicInformation.getSessionToken());
                    oChefSearch.setUserId(basicInformation.getUserId());
                    oChefSearch.setBookingDate(shared.getString("BookingDateTime", ""));
                    if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
                        oChefSearch.setCuisineList(shared.getString("Cuisine", "").replace(" ", ""));
                    }else{
                        oChefSearch.setCuisineList("");
                    }

                    oChefSearch.setCurrentDate(date_format);
                    oChefSearch.setDistance("0");
                    oChefSearch.setEndIndex("100");
                    oChefSearch.setStartIndex("0");
                    oChefSearch.setLatitude(String.valueOf(dilalogModel.getLat()));
                    oChefSearch.setLongitude(String.valueOf(dilalogModel.getLongi()));
                    oChefSearch.setGender(shared.getString("QuickFilter", ""));
                    oChefSearch.setRediusFrom(shared.getString("MinRadius", ""));
                    oChefSearch.setRediusTo(shared.getString("MaxRadius", ""));
                    oChefSearch.setSearchText("");

                    if(getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
                        oChefSearch.setServiceType(shared.getString("Service", ""));
                    }else{
                        oChefSearch.setServiceType("");
                    }


                    oChefSearch.setSortBy(shared.getString("SortBy", ""));
                    oChefSearch.setUserType(getIntent().getStringExtra("UserType"));
                } else {
                    oChefSearch.setSessionToken(basicInformation.getSessionToken());
                    oChefSearch.setUserId(basicInformation.getUserId());
                    oChefSearch.setBookingDate(date_format);
                    oChefSearch.setCuisineList("");
                    oChefSearch.setCurrentDate(date_format);
                    oChefSearch.setDistance("0");
                    oChefSearch.setEndIndex("100");
                    oChefSearch.setStartIndex("0");
                    oChefSearch.setLatitude(String.valueOf(dilalogModel.getLat()));
                    oChefSearch.setLongitude(String.valueOf(dilalogModel.getLongi()));
                    oChefSearch.setGender("Any Gender");
                    oChefSearch.setRediusFrom("0");
                    oChefSearch.setRediusTo("60");
                    oChefSearch.setSearchText("");
                    oChefSearch.setServiceType("");
                    oChefSearch.setSortBy("Distance");
                    oChefSearch.setUserType(getIntent().getStringExtra("UserType"));
                }
                load_dialog.show();

                String jsonString = oGson.toJson(oChefSearch, ChefSearch.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.ChefSearch);
                oInsertUpdateApi.executePostRequest(API.fChefSearch(), jsonString);

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
