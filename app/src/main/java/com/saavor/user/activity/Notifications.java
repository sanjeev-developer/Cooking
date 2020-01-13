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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saavor.user.Model.Bestoffermodel;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.NotificationList;
import com.saavor.user.Model.NotificationReadHit;
import com.saavor.user.Model.NotificationResponse;
import com.saavor.user.R;
import com.saavor.user.adapter.NotificationAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;
import com.saavor.user.processor.PostApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static com.saavor.user.activity.DashBoard.notcount;

public class Notifications extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_orderdetails;
    private NotificationAdapter notificationAdapter;
    //near array
    ArrayList<NotificationList> notificationdata = new ArrayList<>();
    private ImageView mNavOH;
    private RecyclerView.LayoutManager mLayoutManager;
    StringBuilder stringBuilder;
    LinearLayout ll_no_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_notification);

        basicfetch();
        mOnResultReceived = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notification);
        setSupportActionBar(toolbar);
        stringBuilder = new StringBuilder();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_notification);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_notification);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        ll_no_notification= (LinearLayout) findViewById(R.id.ll_no_notification);

        load_dialog.show();
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.executeGetRequest(API.notificationlist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
        oInsertUpdateApi.setRequestSource(RequestSource.notificationlist);
        navintial();

        try {
            settingdatanav();

        } catch (Exception e) {

        }

        LinearLayout not = (LinearLayout) navigationlayout.findViewById(R.id.ll_not_draw);
        not.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navnotif.setTextColor(getResources().getColor(R.color.white));


        //listeners of navigation drawer

        navusername.setOnClickListener(this);
        navhome.setOnClickListener(this);
        navbook.setOnClickListener(this);
        navfavdish.setOnClickListener(this);
        navorder.setOnClickListener(this);
        navrefferal.setOnClickListener(this);
        // navfreemeal.setOnClickListener(this);
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
        navnotcount.setText("" + notcount);

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
                intent = new Intent(this, Favourite.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_order:

                intent = new Intent(this, OrderHistory.class);
                this.startActivity(intent);

                break;

            case R.id.txt_nav_refferal:
                intent = new Intent(this, ReferralTwo.class);
                this.startActivity(intent);
                break;

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
                drawer.closeDrawer(GravityCompat.START);
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
                intent.putExtra("webdata", "http://demohelpdesk.saavor.io/faq/qalist");
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
                dialog = new Dialog(Notifications.this);
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


        String doo = what;

        if (from.toString().equalsIgnoreCase("notificationlist")) {
            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                NotificationResponse notificationResponse = new NotificationResponse();
                notificationResponse = gson.fromJson(jsonString, NotificationResponse.class);
                System.out.println(">>>>" + what);
                String check = notificationResponse.getReturnCode();
                final String message = notificationResponse.getReturnMessage();
                notificationdata = notificationResponse.getNotificationList();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                             Collections.reverse(notificationdata);

                            ll_no_notification.setVisibility(View.GONE);
                            recyclerView_orderdetails = (RecyclerView) findViewById(R.id.recycle_notication);
                            notificationAdapter = new NotificationAdapter(Notifications.this, notificationdata);
                            mLayoutManager = new LinearLayoutManager(Notifications.this);
                            recyclerView_orderdetails.setLayoutManager(mLayoutManager);
                            recyclerView_orderdetails.setAdapter(notificationAdapter);

                            notcount = 0;
                            for (int i = 0; i < notificationdata.size(); i++) {
                                notcount++;

                                stringBuilder.append(notificationdata.get(i).getNotificationId() + ",");

                            }
                            if(notcount>0)
                            {
                                FrameLayout frameLayout=(FrameLayout)findViewById(R.id.frame_notification);
                                frameLayout.setVisibility(View.VISIBLE);
                                navnotcount.setText("" + notcount);
                            }

                            if (notificationdata.size() < 1) {
                                load_dialog.cancel();
                            } else {
                                String finalString = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);

                                NotificationReadHit notificationReadHit = new NotificationReadHit();
                                notificationReadHit.setIsRead(1);
                                notificationReadHit.setNotificationIds(finalString);
                                notificationReadHit.setSessionToken(basicInformation.getSessionToken());
                                notificationReadHit.setUserId(basicInformation.getUserId());

                                String jsonString = gson.toJson(notificationReadHit, NotificationReadHit.class).toString();
                                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                oChangePsswordApi.setRequestSource(RequestSource.notificationreadhit);
                                oChangePsswordApi.executePostRequest(API.notificationreadhit(), jsonString);
                            }


                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ll_no_notification.setVisibility(View.VISIBLE);
                            load_dialog.cancel();


                        }
                    });

                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();

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
                        //redirect(Notifications.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }


        } else if (from.toString().equalsIgnoreCase("notificationreadhit")) {
            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                NotificationResponse notificationResponse = new NotificationResponse();
                notificationResponse = gson.fromJson(jsonString, NotificationResponse.class);
                System.out.println(">>>>" + what);
                String check = notificationResponse.getReturnCode();
                final String message = notificationResponse.getReturnMessage();


                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            notcount = 0;
                            FrameLayout frameLayout=(FrameLayout)findViewById(R.id.frame_notification);
                            if(notcount>0)
                            {
                                frameLayout.setVisibility(View.VISIBLE);
                                navnotcount.setText("" + notcount);
                            }
                            else
                            {
                                frameLayout.setVisibility(View.INVISIBLE);
                            }

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
                            // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();

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
                        //redirect(Notifications.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }
        }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Notifications.this);
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
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.executeGetRequest(API.notificationlist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                oInsertUpdateApi.setRequestSource(RequestSource.notificationlist);

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