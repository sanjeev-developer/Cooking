package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.FavDishList;
import com.saavor.user.Model.FavouriteReturn;
import com.saavor.user.Model.GetFavDish;
import com.saavor.user.R;
import com.saavor.user.adapter.FavoriteAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.saavor.user.activity.DashBoard.notcount;

public class Favourite extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_favourite;
    private FavoriteAdapter favoriteAdapter;
    LinearLayout LL_no_fav;
    DateFormat datebeforeFormat;
    private java.util.Date CurrentDate, deliverydatee;
    String HitDate;
    boolean closefuture, fromfav;
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    ArrayList<FavDishList> bookmark_title = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_fav_dish);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fav_dish);
        setSupportActionBar(toolbar);


        LL_no_fav= (LinearLayout) findViewById(R.id.ll_no_fav);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_favdish);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_favdish);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        basicfetch();

        mOnResultReceived = this;

        //intialization of navigation
        navintial ();

        try
        {
            settingdatanav();

        }catch (Exception e)
        {

        }

        navfavdish.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navfavdish.setTextColor(getResources().getColor(R.color.white));

        navusername.setOnClickListener(this);
        navhome.setOnClickListener(this);
        navbook.setOnClickListener(this);
        navfavdish.setOnClickListener(this);
        navorder.setOnClickListener(this);
        navrefferal.setOnClickListener(this);
        navbadges.setOnClickListener(this);
        navacc.setOnClickListener(this);
        navnotif.setOnClickListener(this);
        navaddress.setOnClickListener(this);
        navpayment.setOnClickListener(this);
        navfaq.setOnClickListener(this);
        navrate.setOnClickListener(this);
        navabout.setOnClickListener(this);
        navfeedback.setOnClickListener(this);
        navlogout.setOnClickListener(this);
        txt_nav_BookingHistory.setOnClickListener(this);
        navnotcount.setText(""+notcount);


        //setting delivery pref
        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        datebeforeFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);

        try {
            CurrentDate = datebeforeFormat.parse(date_format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            deliverydatee = datebeforeFormat.parse(deliverydetailspref.getString("Deliverydate", ""));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (deliverydatee.after(CurrentDate)) {

            HitDate = deliverydetailspref.getString("Deliverydate", "");
            closefuture = true;

        }

        if (deliverydatee.before(CurrentDate)) {
            System.out.println("Date1 is before Date2");
            closefuture = false;
            HitDate = date_format;
        }

        if (deliverydatee.equals(CurrentDate)) {
            // previousdaydish.setVisibility(View.INVISIBLE);
            closefuture = false;
            HitDate = date_format;

        }


        try
        {
            load_dialog.show();
            mProgressDialog.setMessage("Please wait...");
            GetFavDish getFavDish = new GetFavDish();
            getFavDish.setCurrentDate(HitDate);
            getFavDish.setSessionToken(""+basicInformation.getSessionToken());
            getFavDish.setUserId(""+basicInformation.getUserId());

            String jsonString = gson.toJson(getFavDish, GetFavDish.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.executePostRequest(API.favouriteapi(), jsonString);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case R.id.txt_nav_username:

                intent = new Intent(this, UserInfo.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_home:

                intent = new Intent(this, DashBoard.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_bookmark:

                intent = new Intent(this, Bookmark.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_dishes:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.txt_nav_order:
                intent = new Intent(this, OrderHistory.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_refferal:
                intent = new Intent(this, ReferralTwo.class);
                this.startActivity(intent);
                break;
//
            case R.id.txt_nav_BookingHistory:

                intent = new Intent(this, BookingHistoryActivity.class);
                this.startActivity(intent);

                break;

            case R.id.txt_nav_badges:
                intent = new Intent(this, Badges.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_acc:
                intent = new Intent(this, Account.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_notifi:
                intent = new Intent(this, Notifications.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_address:
                intent = new Intent(this, AddressBook.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_payment:
                intent = new Intent(this, CardBook.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_rate:
//                intent = new Intent(this, ReviewRating.class);
//                this.startActivity(intent);
                break;

            case R.id.txt_nav_faq:
                intent = new Intent(this, FAQ.class);
                intent.putExtra("webdata","http://demohelpdesk.saavor.io/faq/qalist");
                this.startActivity(intent);
                break;

            case R.id.txt_nav_feed:
                intent = new Intent(this, Feedback.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_about:
                intent = new Intent(this, About.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_logout:


                //dialog intialization
                dialog = new Dialog(Favourite.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.logout_layout);
                dialog.setCancelable(true);

                Button logout=(Button)dialog.findViewById(R.id.logout_diag);
                Button stay=(Button)dialog.findViewById(R.id.stay_diag);

                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logOut();
                        dialog.cancel();
                    }
                });

                stay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });


                dialog.show();



                break;

            case R.id.img_filter_dash:
                intent = new Intent(this, Filter.class);
                this.startActivity(intent);
                break;

        }
    }

    private void logOut() {

        SharedPreferences settings = this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        mTabel.putBoolean("session", false);
        mTabel.commit();

        intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

       if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Favourite.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

        try {
            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();

            gson = new Gson();
            favouriteReturn = gson.fromJson(jsonString, FavouriteReturn.class);
            System.out.println(">>>>" + what);
            String check = favouriteReturn.getReturnCode();
            final String message = favouriteReturn.getReturnMessage();



            if (check.equals("1")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LL_no_fav.setVisibility(View.GONE);
                        bookmark_title = favouriteReturn.getFavDishList();
                        favoriteAdapter = new FavoriteAdapter(Favourite.this, bookmark_title);
                        recyclerView_favourite=(RecyclerView) findViewById(R.id.recycle_favourite);
                        recyclerView_favourite.setLayoutManager(new LinearLayoutManager(Favourite.this, LinearLayoutManager.VERTICAL, true));
                        recyclerView_favourite.setAdapter(favoriteAdapter);
                        load_dialog.cancel();

                    }
                });
            } else if (check.equals("0")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LL_no_fav.setVisibility(View.VISIBLE);
                        load_dialog.cancel();

                    }
                });

            }
            else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                    }
                });
                displayAlert(Favourite.this, message);
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
                    nointernet();

                }
            });
        }
    }}

    public void reorder(Integer dishId, String dishName, String kitchenName, int profileid, Double deliveryCharges) {

        basicKitInfo.setKitchenProileid(""+profileid);
        basicKitInfo.setKitchenDelCharge(""+deliveryCharges);
        String data = gson.toJson(basicKitInfo);
        mTabel.putString("basickitcheninfo", data);
        mTabel.commit();

        Intent intent = new Intent(Favourite.this, XYZKitchen.class);
        intent.putExtra("kitchentitle",kitchenName);
        intent.putExtra("profileid",profileid);
        intent.putExtra("dishid",dishId);
        intent.putExtra("dishName",dishName);
        intent.putExtra("fromfav",true);
        Favourite.this.startActivity(intent);
    }


    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Favourite.this);
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

                try
                {
                    load_dialog.show();
                    mProgressDialog.setMessage("Please wait...");
                    GetFavDish getFavDish = new GetFavDish();
                    getFavDish.setCurrentDate(date_format);
                    getFavDish.setSessionToken(""+basicInformation.getSessionToken());
                    getFavDish.setUserId(""+basicInformation.getUserId());

                    String jsonString = gson.toJson(getFavDish, GetFavDish.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.favouriteapi(), jsonString);

                }catch(Exception e)
                {
                    e.printStackTrace();
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
