package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.saavor.user.Model.BestOfferNodelReturn;
import com.saavor.user.Model.Bestoffermodel;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.Model.KitchensDealList;
import com.saavor.user.R;
import com.saavor.user.adapter.SeeAllDealAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class SeeAllDeal extends BaseActivity implements OnResultReceived, View.OnClickListener {


    private ArrayList<KitchensDealList> bestofferarray = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView sellall;
    private  SeeAllDealAdapter seeAllDealAdapter;
    private ImageView back_alldeal;

    @Override
    public void onBackPressed()
    {

        intent = new Intent(SeeAllDeal.this, DashBoard.class);
        SeeAllDeal.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_deal);

        basicfetch();
        mOnResultReceived = this;

        sellall = (RecyclerView) findViewById(R.id.recycle_alldeal);
        back_alldeal= (ImageView) findViewById(R.id.tool_back_alldeal);
        back_alldeal.setOnClickListener(this);

        //fetching dialog location
        DilalogModel dilalogModel = new DilalogModel();
        String dailogdata = mDatabase.getString("dialogdata", "");
        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

        bestoffermodel.setDistance("" + 5);
        bestoffermodel.setCurrentDate("" + date_format);
        bestoffermodel.setEndIndex("" + 20);
        bestoffermodel.setLatitude("" + dilalogModel.getLat());
        bestoffermodel.setSessionToken(basicInformation.getSessionToken());
        bestoffermodel.setLongitude("" + dilalogModel.getLongi());
        bestoffermodel.setStartIndex("" + 0);
        bestoffermodel.setUserId(basicInformation.getUserId());

        load_dialog.show();

        String jsonString = gson.toJson(bestoffermodel, Bestoffermodel.class).toString();
        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
        oChangePsswordApi.setRequestSource(RequestSource.bestoffer);
        oChangePsswordApi.executePostRequest(API.bestoffer(), jsonString);
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

       if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(SeeAllDeal.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

            try {

                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                bestOfferNodelReturn = gson.fromJson(jsonString, BestOfferNodelReturn.class);
                String check = bestOfferNodelReturn.getReturnCode();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                bestofferarray = new ArrayList<KitchensDealList>();
                                bestofferarray = bestOfferNodelReturn.getKitchensDealList();

                                seeAllDealAdapter = new SeeAllDealAdapter(SeeAllDeal.this, bestofferarray);
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                                sellall.setLayoutManager(layoutManager);
                                sellall.setItemAnimator(new DefaultItemAnimator());
                                sellall.setAdapter(seeAllDealAdapter);

                                load_dialog.cancel();


                            } catch (Exception e) {

                            }
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
                }
                else if (check.equals("5")) {
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
                       // redirect(SeeAllDeal.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();

                    }
                });
            }

        }
    }
    @Override
    public void onClick(View v) {

        intent = new Intent(SeeAllDeal.this, DashBoard.class);
        SeeAllDeal.this.startActivity(intent);
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(SeeAllDeal.this);
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

                try {
                    //fetching dialog location
                    DilalogModel dilalogModel = new DilalogModel();
                    String dailogdata = mDatabase.getString("dialogdata", "");
                    dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                    bestoffermodel.setDistance("" + 5);
                    bestoffermodel.setCurrentDate("" + date_format);
                    bestoffermodel.setEndIndex("" + 20);
                    bestoffermodel.setLatitude("" + dilalogModel.getLat());
                    bestoffermodel.setSessionToken(basicInformation.getSessionToken());
                    bestoffermodel.setLongitude("" + dilalogModel.getLongi());
                    bestoffermodel.setStartIndex("" + 0);
                    bestoffermodel.setUserId(basicInformation.getUserId());

                    load_dialog.show();

                    String jsonString = gson.toJson(bestoffermodel, Bestoffermodel.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.setRequestSource(RequestSource.bestoffer);
                    oChangePsswordApi.executePostRequest(API.bestoffer(), jsonString);


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
