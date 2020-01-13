package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.saavor.user.Model.LoginDataReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

public class UserInfo extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private ImageView ui_pic;
    private TextView nameui,miui,lnameui,mobileui,emailui,dateui,editui;
    private String fname,mi,lname,mobile,email,date,userpic;
    private Button viewimage;

    Handler handler;
    ProgressBar progress_personalinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_userinfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_userinfo);
        setSupportActionBar(toolbar);


        ui_pic= (ImageView) findViewById(R.id.img_user_ui);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_userinfo);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_userinfo);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        basicfetch();
        mOnResultReceived=this;
        nameui= (TextView) findViewById(R.id.txt_nameui);
        miui= (TextView) findViewById(R.id.txt_miui);
        lnameui= (TextView) findViewById(R.id.txt_lnameui);
        mobileui= (TextView) findViewById(R.id.txt_mobileui);
        emailui= (TextView) findViewById(R.id.txt_emailui);
        dateui= (TextView) findViewById(R.id.txt_dateui);
        editui= (TextView) findViewById(R.id.txt_edit_ui);
        progress_personalinfo= (ProgressBar) findViewById(R.id.progressBar_personalinfo);

        navigationlayout = (LinearLayout) findViewById(R.id.ll_nav_view);
        //intialization of navigation
        navintial ();

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            settingdatanav();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

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


        viewimage= (Button) findViewById(R.id.but_viewpicui);
        viewimage.setOnClickListener(this);
        editui.setOnClickListener(this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progress_personalinfo.setVisibility(View.GONE);

            }
        }, 4000);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_edit_ui: /** Start a new Activity signup.java */
                intent = new Intent(this, Personal_Details.class);
                intent.putExtra("fromedit", true);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_username:

                drawer.closeDrawer(GravityCompat.START);

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


            case R.id.img_filter_dash:
                intent = new Intent(this, Filter.class);
                this.startActivity(intent);
                break;

            case R.id.but_viewpicui:
                intent = new Intent(this, ViewImage.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_logout:
                //dialog intialization
                dialog = new Dialog(UserInfo.this);
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


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            load_dialog.show();
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.executeGetRequest(API.userinfo()+basicInformation.getUserId()+"/"+basicInformation.getSessionToken());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {



      if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(UserInfo.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
        System.out.println(">>>>"+what);

        try {

            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();
            //System.out.println(">>>>"+jsonString);

            gson = new Gson();
            loginDataReturn= gson.fromJson(jsonString, LoginDataReturn.class);
            String check = loginDataReturn.getReturnCode();

            if(check.equals("1"))
            {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        settingdata();
                    }
                });
            }

            else if(check.equals("0"))
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(UserInfo.this, ""+loginDataReturn.getReturnMessage());
                    }
                });
            }

            else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(UserInfo.this,loginDataReturn.getReturnMessage());
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
        }
        catch (Exception e)
        {
            System.out.println(">>>>"+e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    redirect(UserInfo.this, "No internet access. Please turn on cellular data or use wifi.");
                }
            });
        }

    }}

    private void settingdata() {

        fname=loginDataReturn.getUserInfo().getFirstName().toString();
        lname=loginDataReturn.getUserInfo().getLastName().toString();
        mi=loginDataReturn.getUserInfo().getMI().toString();
        mobile=loginDataReturn.getUserInfo().getMobileNo().toString();
        date=loginDataReturn.getUserInfo().getDOB().toString();
        email=loginDataReturn.getUserInfo().getEmail().toString();
        userpic=loginDataReturn.getUserInfo().getProfilePicPath();


        savingbasic();

        //setting first name
        if(fname.isEmpty() || fname=="" || fname==null)
        {

        }
        else
        {
            nameui.setText(fname);
        }

        //setting last name
        if(lname.isEmpty() || lname=="" || lname==null)
        {

        }
        else
        {
            lnameui.setText(lname);
        }

        //setting mi

        if(mi.isEmpty() || mi=="" || mi==null)
        {

        }
        else
        {
            miui.setText(mi);
        }

        //setting mobile no
        if(mobile.isEmpty() || mobile=="" || mobile==null)
        {

        }
        else
        {
            mobileui.setText(mobile);
        }

        //setting dob
        if(date.isEmpty() || date=="" || date==null)
        {

        }
        else
        {
            if (date.contains("-")){
                String month="", date1="",year="";

                String[] aSplit = date.split("-");

                month=aSplit[0];
                date1=aSplit[1];
                year=aSplit[2];


                dateui.setText(month+" "+date1+","+year);

            }else{
                dateui.setText(""+date);
            }

        }

        //setting email
        if(email.isEmpty() || email=="" || email==null)
        {

        }
        else
        {
            emailui.setText(email);
        }

        //setting profile image
        if(userpic.isEmpty() || userpic=="" || userpic==null)
        {

        }
        else
        {
            try {

                String internetUrl = "http://saavorapi.parkeee.net/" +userpic;

                Glide.with(this).load(internetUrl).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progress_personalinfo.setVisibility(View.GONE);
                        viewimage.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progress_personalinfo.setVisibility(View.GONE);
                        viewimage.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                        .into(ui_pic);


            } catch (Exception e) {
                ui_pic.setImageResource(R.drawable.usericonpd);
            }
        }

        }


    public void savingbasic() {
        basicInformation.setFirstName(fname);
        basicInformation.setLastName(lname);
        basicInformation.setMobileNumber(mobile);
        basicInformation.setDateOfBirth(date);
        basicInformation.setMI(mi);
        basicInformation.setUserprofile(userpic);

        mBasicInformation = gson.toJson(basicInformation);
        mTabel.putString("basicinformation", mBasicInformation);
        mTabel.commit();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    private void logOut() {

        SharedPreferences settings = this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        mTabel.putBoolean("session", false);
        mTabel.commit();

        intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
