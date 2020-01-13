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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.R;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;

import static com.saavor.user.activity.DashBoard.notcount;

public class ReferralTwo extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private ImageView mNavReferral,share;
    private TextView mReff_code;
    TextView refname;
    LinearLayout ll_reffralscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_refferal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_refferal);
        setSupportActionBar(toolbar);

        mOnResultReceived=this;

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_refferaltwo);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_refferaltwo);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);


        basicfetch();

        refname= (TextView) findViewById(R.id.refferalname);
        refname.setText("Dear "+basicInformation.getFirstName().toString()+",\nSend gift to your friends.");

        //intialization of navigation
        navintial ();

        mReff_code= (TextView) findViewById(R.id.reff_code);
        mReff_code.setText(basicInformation.getRefferal().toString());
     //   mReff_code.setOnClickListener(this);

        try
        {
            settingdatanav();

        }catch (Exception e)
        {

        }

        navrefferal.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navrefferal.setTextColor(getResources().getColor(R.color.white));

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
        share= (ImageView) findViewById(R.id.img_share);
        share.setOnClickListener(this);
        txt_nav_BookingHistory.setOnClickListener(this);
        navnotcount.setText(""+notcount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }catch (Exception e)
        {

        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.img_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
               /* sendIntent.putExtra(Intent.EXTRA_TEXT, "here is savoors's share code "+basicInformation.getRefferal().toString()
                        +" with "+"https://play.google.com/store/apps/details?id=com.facebook.katana&hl=en");*/

                sendIntent.putExtra(Intent.EXTRA_TEXT, "I'm giving you a discount of $20 on your first order on the Saavor app. To confirm, use code 'asd908' to sign up. Enjoy! Details: playstorelink/asd908");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            case R.id.txt_nav_username:

                intent = new Intent(this, UserInfo.class);
                this.startActivity(intent);
                break;

            case R.id.reff_code:

//                Test test = new Test();
//                test.setProfileId(94);
//                test.setDeviceToken("B19ED19265069A538D374CED1E792BF20AAC06B2CDF964A1C422480E47CB8997");
//                test.setDeviceType("ios");
//                test.setMessage("Approved");
//
//                String jsonString = gson.toJson(test, Test.class).toString();
//                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
//                oChangePsswordApi.executePostRequest(API.test(), jsonString);

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

                drawer.closeDrawer(GravityCompat.START);
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
                dialog = new Dialog(ReferralTwo.this);
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

        System.out.println(">>>>"+what);

    }
}