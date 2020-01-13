package com.saavor.user.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.saavor.user.R;

public class FreeMeals extends BaseActivity implements View.OnClickListener {

    ImageView mNavFreeMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_promotions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_promotion);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_promotion);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_promotion);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        basicfetch();
        //intialization of navigation
        navintial ();

        try
        {
            settingdatanav();

        }catch (Exception e)
        {

        }

//        navfreemeal.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
//        navfreemeal.setTextColor(getResources().getColor(R.color.white));

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

//            case R.id.txt_nav_meal:
//
//                drawer.closeDrawer(GravityCompat.START);
//                break;

            case R.id.txt_nav_badges:
//                intent = new Intent(this, Badges.class);
//                this.startActivity(intent);
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
                AlertDialog.Builder mDialog = new AlertDialog.Builder(FreeMeals.this);

                mDialog.setMessage("Are you sure you want to Logout?")
                        .setCancelable(true)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        logOut();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

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
}