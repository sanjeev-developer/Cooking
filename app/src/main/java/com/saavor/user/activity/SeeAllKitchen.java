package com.saavor.user.activity;

import android.app.Dialog;
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

import com.saavor.user.Model.DilalogModel;
import com.saavor.user.Model.KitchenList;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.Model.KitchenSearchReturn;
import com.saavor.user.R;
import com.saavor.user.adapter.DashRecycleAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.deliverytypestatus;

public class SeeAllKitchen extends BaseActivity implements OnResultReceived, View.OnClickListener {

    private ArrayList<KitchenList> kitchensearcharray = new ArrayList<KitchenList>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView sellall;
    private DashRecycleAdapter dashRecycleAdapter;
    private ImageView back_allkit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_kitchen);


        basicfetch();
        mOnResultReceived = this;
        KitchenSearch kitchenSearch = new KitchenSearch();
        //fetching data from filters
        mFilterdefault = mDatabase.getString("defaultfilter", "");
        kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

        sellall = (RecyclerView) findViewById(R.id.recycle_allkitchen);
        back_allkit= (ImageView) findViewById(R.id.tool_back_allkit);
        back_allkit.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();


        //fetching default
        String data = mDatabase.getString("defaultfilter", "");
        kitchenSearch = gson.fromJson(data, KitchenSearch.class);

        if(kitchenSearch == null || data.equals(""))
        {
            KitchenSearch kitchenSearch= new KitchenSearch();

            kitchenSearch.setCostForOne(""+0);
            kitchenSearch.setDeliveryFrom(""+0);
            kitchenSearch.setDeliveryTo(""+60);
            kitchenSearch.setDistance(""+5);
            kitchenSearch.setStartIndex(""+0);
            kitchenSearch.setEndIndex(""+50);
            kitchenSearch.setIsBookMarked(""+0);
            kitchenSearch.setIsDiscount(""+0);
            kitchenSearch.setIsVegetarian(""+0);
            kitchenSearch.setMinimumOrder(""+0);
            kitchenSearch.setServiceType("");
            kitchenSearch.setKitchenType("");
            kitchenSearch.setSortBy("Distance");
            kitchenSearch.setDeliveryDate(date_format);
            kitchenSearch.setCuisineList("");

            mFilterdefault = gson.toJson(kitchenSearch);
            mTabel.putString("defaultfilter", mFilterdefault);
            mTabel.commit();
        }
        else
        {

            try {

                if (deliverytypestatus) {
                    kitchenSearch.setIsDelivery(1);
                } else {
                    kitchenSearch.setIsDelivery(0);
                }

//            kitchenSearch.setCostForOne("" + 0);
//            kitchenSearch.setDeliveryFrom("" + kitchenSearch.getDeliveryFrom());
//            kitchenSearch.setDeliveryTo("" + kitchenSearch.getDeliveryTo());
            kitchenSearch.setDistance("" + 5);
            kitchenSearch.setStartIndex("" + 0);
            kitchenSearch.setEndIndex("" + 100);
            kitchenSearch.setSearchText("");
//            kitchenSearch.setIsBookMarked("" + 0);
//            kitchenSearch.setIsDiscount("" + 0);
//            kitchenSearch.setIsVegetarian("" + 0);
//            kitchenSearch.setMinimumOrder("" + 0);
//            kitchenSearch.setServiceType("");
//            kitchenSearch.setKitchenType("");
            kitchenSearch.setSortBy(""+kitchenSearch.getSortBy());
//            kitchenSearch.setCuisineList("");
            //  kitchenSearch.setDeliveryDate(date_format);
            kitchenSearch.setCurrentDate(date_format);

            //fetching dialog location
            DilalogModel dilalogModel = new DilalogModel();
            String dailogdata = mDatabase.getString("dialogdata", "");
            dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

            kitchenSearch.setLatitude("" + dilalogModel.getLat());
            kitchenSearch.setLongitude("" + dilalogModel.getLongi());

            kitchenSearch.setUserId(basicInformation.getUserId().toString());
            kitchenSearch.setSessionToken(basicInformation.getSessionToken());
            kitchenSearch.setSearchText("");

            load_dialog.show();

            String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);


        } catch (Exception e) {

        }
    }}

    @Override
    public void dispatchString(RequestSource from, String what) {

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(SeeAllKitchen.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                kitchenSearchReturn = gson.fromJson(jsonString, KitchenSearchReturn.class);
                System.out.println(">>>>" + what);
                String check = kitchenSearchReturn.getReturnCode();
                final String message = kitchenSearchReturn.getReturnMessage();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            kitchensearcharray = kitchenSearchReturn.getKitchenList();
                            System.out.println(">>>>>" + kitchensearcharray);

                            AllKitchenAdapter allKitchenAdapter = new AllKitchenAdapter(SeeAllKitchen.this, kitchensearcharray);
                            mLayoutManager = new LinearLayoutManager(SeeAllKitchen.this);
                            sellall.setLayoutManager(mLayoutManager);
                            sellall.setItemAnimator(new DefaultItemAnimator());
                            sellall.setAdapter(allKitchenAdapter);

                            load_dialog.cancel();

                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

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
                        //redirect(SeeAllKitchen.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }
        }
    }
    @Override
    public void onClick(View v) {
        intent = new Intent(getApplicationContext(), DashBoard.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {

        intent = new Intent(SeeAllKitchen.this, DashBoard.class);
        SeeAllKitchen.this.startActivity(intent);
    }
    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(SeeAllKitchen.this);
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


                //fetching default
                String data = mDatabase.getString("defaultfilter", "");
                kitchenSearch = gson.fromJson(data, KitchenSearch.class);

                if(kitchenSearch == null || data.equals(""))
                {
                    KitchenSearch kitchenSearch= new KitchenSearch();

                    kitchenSearch.setCostForOne(""+0);
                    kitchenSearch.setDeliveryFrom(""+0);
                    kitchenSearch.setDeliveryTo(""+60);
                    kitchenSearch.setDistance(""+5);
                    kitchenSearch.setStartIndex(""+0);
                    kitchenSearch.setEndIndex(""+50);
                    kitchenSearch.setIsBookMarked(""+0);
                    kitchenSearch.setIsDiscount(""+0);
                    kitchenSearch.setIsVegetarian(""+0);
                    kitchenSearch.setMinimumOrder(""+0);
                    kitchenSearch.setServiceType("");
                    kitchenSearch.setKitchenType("");
                    kitchenSearch.setSortBy("Distance");
                    kitchenSearch.setDeliveryDate(date_format);
                    kitchenSearch.setCuisineList("");

                    mFilterdefault = gson.toJson(kitchenSearch);
                    mTabel.putString("defaultfilter", mFilterdefault);
                    mTabel.commit();
                }
                else
                {

                    try {

                        if (deliverytypestatus) {
                            kitchenSearch.setIsDelivery(1);
                        } else {
                            kitchenSearch.setIsDelivery(0);
                        }

//            kitchenSearch.setCostForOne("" + 0);
//            kitchenSearch.setDeliveryFrom("" + kitchenSearch.getDeliveryFrom());
//            kitchenSearch.setDeliveryTo("" + kitchenSearch.getDeliveryTo());
                        kitchenSearch.setDistance("" + 5);
                        kitchenSearch.setStartIndex("" + 0);
                        kitchenSearch.setEndIndex("" + 100);
                        kitchenSearch.setSearchText("");
//            kitchenSearch.setIsBookMarked("" + 0);
//            kitchenSearch.setIsDiscount("" + 0);
//            kitchenSearch.setIsVegetarian("" + 0);
//            kitchenSearch.setMinimumOrder("" + 0);
//            kitchenSearch.setServiceType("");
//            kitchenSearch.setKitchenType("");
//            kitchenSearch.setSortBy("Distance");
//            kitchenSearch.setCuisineList("");
                        //  kitchenSearch.setDeliveryDate(date_format);
                        kitchenSearch.setCurrentDate(date_format);

                        //fetching dialog location
                        DilalogModel dilalogModel = new DilalogModel();
                        String dailogdata = mDatabase.getString("dialogdata", "");
                        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                        kitchenSearch.setLatitude("" + dilalogModel.getLat());
                        kitchenSearch.setLongitude("" + dilalogModel.getLongi());

                        kitchenSearch.setUserId(basicInformation.getUserId().toString());
                        kitchenSearch.setSessionToken(basicInformation.getSessionToken());
                        kitchenSearch.setSearchText("");

                        load_dialog.show();

                        String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);


                    } catch (Exception e) {

                    }
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
