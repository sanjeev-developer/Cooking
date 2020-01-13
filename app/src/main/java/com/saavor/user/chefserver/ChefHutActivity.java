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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.saavor.user.Model.GetCompanyProfile;
import com.saavor.user.Model.TeamList;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Login;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.adapter.TeamListAdapter;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ChefHutActivity extends BaseActivity implements OnResultReceived, View.OnClickListener {

    RecyclerView rv_team;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView action_back;
    ImageView img_heart1_C;
    ImageView img_heart2_C;
    ImageView img_heart3_C;
    ImageView img_heart4_C;
    ImageView img_heart5_C;
    ImageView profilePicIv;

    TextView txt_review;
    TextView txt_cuisines;
    TextView txt_services;
    TextView txtBookings;
    TextView txt_Address;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;

    ProgressBar imageLoader;

    ArrayList<TeamList> aLTeamList = new ArrayList<TeamList>();
    TeamListAdapter oTeamListAdapter;
    TextView txt_company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_hut);
        InitializeInterface();
    }

    private void InitializeInterface() {
        rv_team = (RecyclerView) findViewById(R.id.rv_team);
        rv_team.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(ChefHutActivity.this);
        rv_team.setLayoutManager(mLayoutManager);
        rv_team.setItemAnimator(new DefaultItemAnimator());

        txt_review=(TextView)findViewById(R.id.txt_review);
        txt_cuisines=(TextView)findViewById(R.id.txt_cuisines);
        txt_services=(TextView)findViewById(R.id.txt_services);
        txtBookings=(TextView)findViewById(R.id.txtBookings);
        txt_Address=(TextView)findViewById(R.id.txt_Address);
        txt_company_name=(TextView)findViewById(R.id.txt_company_name);

        action_back = (ImageView) findViewById(R.id.action_back);
        img_heart1_C = (ImageView) findViewById(R.id.img_heart1_C);
        img_heart2_C = (ImageView) findViewById(R.id.img_heart2_C);
        img_heart3_C = (ImageView) findViewById(R.id.img_heart3_C);
        img_heart4_C = (ImageView) findViewById(R.id.img_heart4_C);
        img_heart5_C = (ImageView) findViewById(R.id.img_heart5_C);
        profilePicIv = (ImageView) findViewById(R.id.profilePicIv);
        imageLoader = (ProgressBar) findViewById(R.id.imageLoader);
        action_back.setOnClickListener(this);

        txt_company_name.setText(getIntent().getStringExtra("HutName").replace("Working for ",""));

        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();

        basicfetch();

        load_dialog.show();
        GetCompanyProfile oGetCompanyProfile = new GetCompanyProfile();
        oGetCompanyProfile.setSessionToken(basicInformation.getSessionToken());
        oGetCompanyProfile.setCurrentDate(date_format);
        oGetCompanyProfile.setParentId(getIntent().getStringExtra("ParentId"));
        oGetCompanyProfile.setUserId((basicInformation.getUserId()));

        String jsonString = oGson.toJson(oGetCompanyProfile, GetCompanyProfile.class).toString();
        PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.GetCompanyProfile);
        oInsertUpdateApi.executePostRequest(API.fGetCompanyProfile(), jsonString);

    }

    public void FinishActivity(){
        finish();
    }

    @Override
    public void dispatchString(RequestSource from,final String what) {
        load_dialog.cancel();
        ServerResult = what;

        if (from.toString().equalsIgnoreCase("GetCompanyProfile")) {
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
                            try {
                                result = new JSONObject(what);
                                if (result.getString("ReturnCode").equals("1")) {
                                    JSONObject ProfileInfo = result.getJSONObject("ProfileInfo");
                                    JSONArray JaTeamList = ProfileInfo.getJSONArray("TeamList");

                                    txt_review.setText(ProfileInfo.getString("TotalReviews").toString() + " Reviews");
                                    txt_cuisines.setText(ProfileInfo.getString("BusinessName").toString());
                                    txt_services.setText(ProfileInfo.getString("BusinessType").toString());
                                    txtBookings.setText(ProfileInfo.getString("TotalBookings").toString());
                                    txt_Address.setText(ProfileInfo.getString("Address").toString());

                                    for (int m = 0; m < JaTeamList.length(); m++) {
                                        TeamList oTeamList = new TeamList();
                                        oTeamList.setBusinessType(JaTeamList.getJSONObject(m).getString("BusinessType"));
                                        oTeamList.setChefId(JaTeamList.getJSONObject(m).getString("ChefId"));
                                        oTeamList.setCuisineList(JaTeamList.getJSONObject(m).getString("CuisineList"));
                                        oTeamList.setPrice(JaTeamList.getJSONObject(m).getString("Price"));
                                        oTeamList.setProfileImagePath(JaTeamList.getJSONObject(m).getString("ProfileImagePath"));
                                        oTeamList.setMinBookingAmount(JaTeamList.getJSONObject(m).getString("MinBookingAmount"));
                                        oTeamList.setUserName(JaTeamList.getJSONObject(m).getString("UserName"));
                                        aLTeamList.add(oTeamList);
                                    }
                                    oTeamListAdapter=new TeamListAdapter(ChefHutActivity.this,aLTeamList);
                                    rv_team.setAdapter(oTeamListAdapter);

                                    if (!ProfileInfo.getString("ProfileImagePath").equalsIgnoreCase("")) {
                                        imageLoader.setVisibility(View.VISIBLE);
                                        Picasso.with(mContext).invalidate("http://saavorapi.parkeee.net/" + ProfileInfo.getString("ProfileImagePath"));
                                        Picasso.with(profilePicIv.getContext())
                                                .load("http://saavorapi.parkeee.net/" + ProfileInfo.getString("ProfileImagePath"))
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
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

                                    if (ProfileInfo.getString("StarRating").equalsIgnoreCase("1")) {
                                        img_heart1_C.setImageResource(R.drawable.redreview);
                                    } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("2")) {
                                        img_heart1_C.setImageResource(R.drawable.redreview);
                                        img_heart2_C.setImageResource(R.drawable.redreview);
                                    } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("3")) {
                                        img_heart1_C.setImageResource(R.drawable.redreview);
                                        img_heart2_C.setImageResource(R.drawable.redreview);
                                        img_heart3_C.setImageResource(R.drawable.redreview);
                                    } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("4")) {
                                        img_heart1_C.setImageResource(R.drawable.redreview);
                                        img_heart2_C.setImageResource(R.drawable.redreview);
                                        img_heart3_C.setImageResource(R.drawable.redreview);
                                        img_heart4_C.setImageResource(R.drawable.redreview);
                                    } else if (ProfileInfo.getString("StarRating").equalsIgnoreCase("5")) {
                                        img_heart1_C.setImageResource(R.drawable.redreview);
                                        img_heart2_C.setImageResource(R.drawable.redreview);
                                        img_heart3_C.setImageResource(R.drawable.redreview);
                                        img_heart4_C.setImageResource(R.drawable.redreview);
                                        img_heart5_C.setImageResource(R.drawable.redreview);
                                    }
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


            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
        }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(ChefHutActivity.this);
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

                load_dialog.show();
                GetCompanyProfile oGetCompanyProfile = new GetCompanyProfile();
                oGetCompanyProfile.setSessionToken(basicInformation.getSessionToken());
                oGetCompanyProfile.setCurrentDate(date_format);
                oGetCompanyProfile.setParentId(getIntent().getStringExtra("ParentId"));
                oGetCompanyProfile.setUserId((basicInformation.getUserId()));

                String jsonString = oGson.toJson(oGetCompanyProfile, GetCompanyProfile.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetCompanyProfile);
                oInsertUpdateApi.executePostRequest(API.fGetCompanyProfile(), jsonString);

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
