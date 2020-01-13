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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.saavor.user.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.saavor.user.chefserver.BookingHistoryActivity;

import static com.saavor.user.activity.DashBoard.notcount;
import static com.saavor.user.activity.DashBoard.soicialintegration;

public class Account extends BaseActivity implements View.OnClickListener {

    LinearLayout profiledetails, socialprofiles, changepassword;
    View socialview, passwordview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        //fetching device token
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseMessaging.getInstance().subscribeToTopic("test");
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.e("Notification Token", "" + token);
        }


        // initializing navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_acc);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_acc);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);


        profiledetails = (LinearLayout) findViewById(R.id.ll_acc_pd);
        socialprofiles = (LinearLayout) findViewById(R.id.ll_acc_sp);
        changepassword = (LinearLayout) findViewById(R.id.ll_acc_cp);
        socialview = (View) findViewById(R.id.social_view);
        passwordview = (View) findViewById(R.id.view_account);


        if (soicialintegration) {
            changepassword.setVisibility(View.GONE);
            passwordview.setVisibility(View.GONE);
        } else {
            socialprofiles.setVisibility(View.GONE);
            socialview.setVisibility(View.GONE);
        }

        profiledetails.setOnClickListener(this);
        socialprofiles.setOnClickListener(this);
        changepassword.setOnClickListener(this);

        basicfetch();
        //intialization of navigation
        navintial();

        //setting data on navigation drawer(like image and name)
        try {
            settingdatanav();

        } catch (Exception e) {

        }

        //setting highlight which option is selected in navigation drawer
        navacc.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navacc.setTextColor(getResources().getColor(R.color.white));


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

            case R.id.ll_acc_pd:
                intent = new Intent(this, Personal_Details.class);
                this.startActivity(intent);
                break;

            case R.id.ll_acc_sp:

                intent = new Intent(this, SocialAccounts.class);
                this.startActivity(intent);
                break;

            case R.id.ll_acc_cp:

                intent = new Intent(this, ChangePassword.class);
                this.startActivity(intent);
                break;

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

                drawer.closeDrawer(GravityCompat.START);
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
                dialog = new Dialog(Account.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.logout_layout);
                dialog.setCancelable(true);

                Button logout = (Button) dialog.findViewById(R.id.logout_diag);
                Button stay = (Button) dialog.findViewById(R.id.stay_diag);

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


    //logout function to clear all data saved temporary
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
}