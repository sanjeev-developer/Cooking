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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.saavor.user.R;
import com.saavor.user.chefserver.BookingHistoryActivity;

import static com.saavor.user.activity.DashBoard.notcount;


public class About extends BaseActivity implements View.OnClickListener {

    ImageView term,content,privacy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_about);
        basicfetch();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);


        //intilaization of all elements

        term= (ImageView) findViewById(R.id.about_term);
        content= (ImageView) findViewById(R.id.about_policy);
        privacy= (ImageView) findViewById(R.id.about_content);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_about);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_about);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        //intialization of navigation
        navintial ();

        //setting data on navigation drawer(like image and name)
        try
        {
            settingdatanav();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        //setting highlight which option is selected in navigation drawer
        navabout.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navabout.setTextColor(getResources().getColor(R.color.white));

        //listeners of navigation drawer
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
        navnotcount.setText(""+notcount);
        txt_nav_BookingHistory.setOnClickListener(this);


        term.setOnClickListener(this);
        content.setOnClickListener(this);
        privacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.about_term:

                intent = new Intent(this, FaqWebview.class);
                intent.putExtra("webdata","http://demohelpdesk.saavor.io/user/termsofservice");
                intent.putExtra("titledata","Terms of Services");
                intent.putExtra("fromabout",true);
                this.startActivity(intent);
                break;

            case R.id.about_policy:

                intent = new Intent(this, FaqWebview.class);
                intent.putExtra("webdata","http://demohelpdesk.saavor.io/user/privacypolicy");
                intent.putExtra("titledata","Privacy Policy");
                intent.putExtra("fromabout",true);
                this.startActivity(intent);
                break;

            case R.id.about_content:
                intent = new Intent(this, FaqWebview.class);
                intent.putExtra("webdata","http://demohelpdesk.saavor.io/user/contentpolicy");
                intent.putExtra("titledata","Content Policy");
                intent.putExtra("fromabout",true);
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
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.txt_nav_logout:
                //dialog intialization
                dialog = new Dialog(About.this);
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
