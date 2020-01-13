package com.saavor.user.chefserver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saavor.user.Model.ChefDishList;
import com.saavor.user.Model.GetChefMenus;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.adapter.ChefsMenuAdapter;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChefsMenuActivity extends BaseActivity implements View.OnClickListener, OnResultReceived {

    ExpandableListView exp_lv_chef_menu;
    ImageView action_back;
    ChefsMenuAdapter oChefsMenuAdapter;
    LinearLayout ll_checkout;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;

    private List<String> parentHeaderInformation;

    public static LinearLayout llViewCart;
    public static TextView total_items;

    public static boolean IsItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefs_menu);
        InitializeInterface();
    }

    private void InitializeInterface() {
        exp_lv_chef_menu = (ExpandableListView) findViewById(R.id.exp_lv_chef_menu);
        action_back = (ImageView) findViewById(R.id.action_back);
        action_back.setOnClickListener(this);


       /* parentHeaderInformation = new ArrayList<String>();
        parentHeaderInformation.add("Rice & Biryani");
        parentHeaderInformation.add("Starters");
        parentHeaderInformation.add("Veg.");*/

        /* HashMap<String, List<String>> allChildItems = returnGroupedChildItems();
       oChefsMenuAdapter=new ChefsMenuAdapter(this,parentHeaderInformation,allChildItems);
        exp_lv_chef_menu.setAdapter(oChefsMenuAdapter);*/

        ll_checkout = (LinearLayout) findViewById(R.id.ll_checkout);
        ll_checkout.setOnClickListener(this);

        llViewCart = (LinearLayout) findViewById(R.id.llViewCart);
        total_items = (TextView) findViewById(R.id.total_items);

        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();

        llViewCart.setVisibility(View.GONE);
        total_items.setText("");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.action_back:
                onBackPressed();
                break;

            case R.id.ll_checkout:
                IsItemClick=false;
                Intent intent1 =new Intent(ChefsMenuActivity.this, ChefCartActivity.class);
                intent1.putExtra("SaleTaxPercentage", getIntent().getStringExtra("SaleTaxPercentage"));
                intent1.putExtra("DealDiscount", getIntent().getStringExtra("DealDiscount"));
                intent1.putExtra("DiscountType", getIntent().getStringExtra("DiscountType"));
                intent1.putExtra("Price", getIntent().getStringExtra("Price"));
                intent1.putExtra("ChefId", getIntent().getStringExtra("ChefId"));
                intent1.putExtra("MinAmount", getIntent().getStringExtra("MinAmount"));
                intent1.putExtra("BusinessType", getIntent().getStringExtra("BusinessType"));
                intent1.putStringArrayListExtra("EventsList", getIntent().getExtras().getStringArrayList("EventsList"));
                startActivity(intent1);
                break;
        }
    }

    private HashMap<String, List<String>> returnGroupedChildItems() {

        HashMap<String, List<String>> childContent = new HashMap<String, List<String>>();

        List<String> c1 = new ArrayList<String>();
        c1.add("Plain Rice");
        c1.add("Jira Rice");
        c1.add("Veg Biryani");
        c1.add("Paneer Tikka Biryani");

        List<String> c2 = new ArrayList<String>();
        c2.add("Paneer Tikka");
        c2.add("Baked Samosas");

        List<String> c3 = new ArrayList<String>();
        c3.add("Paneer Tikka");
        c3.add("Baked Samosas");
        c3.add("Plain Rice");
        c3.add("Jira Rice");

        childContent.put(parentHeaderInformation.get(0), c1);
        childContent.put(parentHeaderInformation.get(1), c2);
        childContent.put(parentHeaderInformation.get(2), c3);
        return childContent;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!IsItemClick){
            load_dialog.show();
            basicfetch();
            GetChefMenus oGetChefMenus = new GetChefMenus();
            oGetChefMenus.setCurrentDate(date_format);
            oGetChefMenus.setSessionToken(basicInformation.getSessionToken());
            oGetChefMenus.setUserId(basicInformation.getUserId());
            oGetChefMenus.setChefId(getIntent().getStringExtra("ChefId"));

            String jsonString = oGson.toJson(oGetChefMenus, GetChefMenus.class).toString();
            PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
            oInsertUpdateApi.setRequestSource(RequestSource.GetChefMenus);
            oInsertUpdateApi.executePostRequest(API.fGetChefMenus(), jsonString);

            IsItemClick=false;
        }


    }

    @Override
    public void dispatchString(RequestSource from, String what) {
        load_dialog.cancel();
        ServerResult = what;

        if (from.toString().equalsIgnoreCase("GetChefMenus")) {
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
                    try {
                        final JSONObject obj = new JSONObject(what);
                        if (obj.getString("ReturnCode").equals("1")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONArray MenuList;
                                        MenuList = obj.getJSONArray("MenuList");
                                        parentHeaderInformation = new ArrayList<String>();
                                        HashMap<String, List<ChefDishList>> childContent = new HashMap<String, List<ChefDishList>>();
                                        for (int m = 0; m < MenuList.length(); m++) {
                                            parentHeaderInformation.add(MenuList.getJSONObject(m).getString("DishMenu"));
                                            JSONArray DishList = MenuList.getJSONObject(m).getJSONArray("DishList");
                                            List<ChefDishList> c1 = new ArrayList<ChefDishList>();
                                            for (int i = 0; i < DishList.length(); i++) {
                                                ChefDishList oChefDishList = new ChefDishList();
                                                oChefDishList.setDishId(DishList.getJSONObject(i).getString("DishId"));
                                                oChefDishList.setDishName(DishList.getJSONObject(i).getString("DishName"));

                                                oChefDishList.setCalories(DishList.getJSONObject(i).getString("Calories"));
                                                oChefDishList.setCategory(DishList.getJSONObject(i).getString("Category"));
                                                oChefDishList.setCuisineName(DishList.getJSONObject(i).getString("CuisineName"));
                                                oChefDishList.setDescription(DishList.getJSONObject(i).getString("Description"));
                                                oChefDishList.setIngredeients(DishList.getJSONObject(i).getString("Ingredeients"));
                                                oChefDishList.setDishName(DishList.getJSONObject(i).getString("DishName"));
                                                oChefDishList.setPreparingTime(DishList.getJSONObject(i).getString("PreparingTime"));
                                                oChefDishList.setDiscription("");

                                                c1.add(oChefDishList);
                                                childContent.put(parentHeaderInformation.get(m), c1);
                                            }
                                        }

                                        oChefsMenuAdapter = new ChefsMenuAdapter(ChefsMenuActivity.this, parentHeaderInformation, childContent,getIntent().getStringExtra("UserName"));
                                        exp_lv_chef_menu.setAdapter(oChefsMenuAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(ChefsMenuActivity.this);
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

                basicfetch();
                GetChefMenus oGetChefMenus = new GetChefMenus();
                oGetChefMenus.setCurrentDate(date_format);
                oGetChefMenus.setSessionToken(basicInformation.getSessionToken());
                oGetChefMenus.setUserId(basicInformation.getUserId());
                oGetChefMenus.setChefId(getIntent().getStringExtra("ChefId"));

                String jsonString = oGson.toJson(oGetChefMenus, GetChefMenus.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetChefMenus);
                oInsertUpdateApi.executePostRequest(API.fGetChefMenus(), jsonString);

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
