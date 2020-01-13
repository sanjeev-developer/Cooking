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
import android.widget.LinearLayout;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.ReviewList;
import com.saavor.user.Model.ReviewReceive;
import com.saavor.user.R;
import com.saavor.user.adapter.ReviewRatingAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewRating extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_rating;
    private ReviewRatingAdapter reviewRatingAdapter;
    ReviewReceive reviewReceive = new ReviewReceive();
    LinearLayout LL_no_review;
    ArrayList<ReviewList> reviewdata = new ArrayList<>();

    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rating);

        LL_no_review = (LinearLayout) findViewById(R.id.ll_no_review);
        back = (ImageView) findViewById(R.id.tool_back_rr);
        back.setOnClickListener(this);

        mOnResultReceived = this;
        basicfetch();

        recyclerView_rating = (RecyclerView) findViewById(R.id.recycle_review_rating);

        try {
            // fetching default
            String data = mDatabase.getString("basickitcheninfo", "");
            basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);

            load_dialog.show();
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.executeGetRequest(API.getkitchenreviews() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + basicKitInfo.getKitchenProileid());
            System.out.println(">>>>>>");
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(ReviewRating.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {
            String t = what;

            try {

                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                reviewReceive = gson.fromJson(jsonString, ReviewReceive.class);
                System.out.println(">>>>" + what);
                String check = reviewReceive.getReturnCode();
                final String message = reviewReceive.getReturnMessage();


                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LL_no_review.setVisibility(View.GONE);
                            reviewdata = reviewReceive.getReviewList();
                            reviewRatingAdapter = new ReviewRatingAdapter(ReviewRating.this, reviewdata);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ReviewRating.this);
                            recyclerView_rating.setLayoutManager(mLayoutManager);
                            recyclerView_rating.setItemAnimator(new DefaultItemAnimator());
                            recyclerView_rating.setAdapter(reviewRatingAdapter);
                            load_dialog.cancel();
                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LL_no_review.setVisibility(View.VISIBLE);
                            load_dialog.cancel();
                        }
                    });
                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(ReviewRating.this, message);

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
                       // redirect(ReviewRating.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }


        }

    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(ReviewRating.this);
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
                    // fetching default
                    String data = mDatabase.getString("basickitcheninfo", "");
                    basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);

                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.executeGetRequest(API.getkitchenreviews() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + basicKitInfo.getKitchenProileid());
                    System.out.println(">>>>>>");
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

