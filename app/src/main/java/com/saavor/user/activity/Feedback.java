package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.FeedbackModel;
import com.saavor.user.Model.SignupReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.saavor.user.activity.DashBoard.notcount;

public class Feedback extends BaseActivity implements View.OnClickListener, OnResultReceived {

    EditText mFeedback;
    Button mFeedback_submit;
    private ImageView mNavFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_feedback);

        basicfetch();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_feedback);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_feedback);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view) {

//                try {
//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }

            public void onDrawerOpened(View drawerView) {


            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        mFeedback = (EditText) findViewById(R.id.edt_feedbox);
        mFeedback_submit = (Button) findViewById(R.id.edt_feedsubmit);
        mFeedback_submit.setOnClickListener(this);

        mOnResultReceived = this;

        basicfetch();
        //navigation drawer text intilization

        navigationlayout = (LinearLayout) findViewById(R.id.ll_nav_view);
        navusername = (TextView) navigationlayout.findViewById(R.id.txt_nav_username);
        navhome = (TextView) navigationlayout.findViewById(R.id.txt_nav_home);
        navpic= (ImageView) navigationlayout.findViewById(R.id.img_user_nav);
        //intialization of navigation
        navintial ();

        try
        {
            settingdatanav();

        }catch (Exception e)
        {

        }

        navfeedback.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navfeedback.setTextColor(getResources().getColor(R.color.white));

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
        navnotcount.setText(""+notcount);
        txt_nav_BookingHistory.setOnClickListener(this);


        DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        };

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

                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.txt_nav_about:
                intent = new Intent(this, About.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_logout:

                //dialog intialization
                dialog = new Dialog(Feedback.this);
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

            case R.id.edt_feedsubmit:
                if (mFeedback.getText().toString().equals("")) {
                    displayAlert(this, "Please fill feedback first");
                } else if ((mFeedback.getText().length() < 29)) {
                    displayAlert(this, "Feedback must be between 30 - 150 words");
                } else {

                    load_dialog.show();
                    feedbackModel.setFeedback(mFeedback.getText().toString());
                    feedbackModel.setSessionToken(basicInformation.getSessionToken().toString());
                    feedbackModel.setUserId(basicInformation.getUserId().toString());
                    feedbackModel.setCreateDate(date_format);

                    String jsonString = gson.toJson(feedbackModel, FeedbackModel.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.feedbackapi(), jsonString);
                }
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
    public void dispatchString(RequestSource from, String what) {
 if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Feedback.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

            try {

                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();


                gson = new Gson();
                signupReturn = gson.fromJson(jsonString, SignupReturn.class);
                System.out.println(">>>>" + what);
                String check = signupReturn.getReturnCode();
                final String message = signupReturn.getReturnMessage();


                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();

                            // displayAlert(Personal_Details.this, "" + message);
                           // displayAlert(Feedback.this, "" + message);
                            submitdiag();
                            mFeedback.setText("");

                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(Feedback.this, message);
                        }
                    });


                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(Feedback.this, message);
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


            } catch (Exception e) {
                System.out.println(">>>>" + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        //redirect(Feedback.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }

            Log.e("response", "" + what);
        }
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Feedback.this);
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
                    if (mFeedback.getText().toString().equals("")) {
                        displayAlert(Feedback.this, "Please fill feedback first");
                    } else if ((mFeedback.getText().length() < 29)) {
                        displayAlert(Feedback.this, "Feedback must be between 30 - 150 words");
                    } else {

                        load_dialog.show();
                        feedbackModel.setFeedback(mFeedback.getText().toString());
                        feedbackModel.setSessionToken(basicInformation.getSessionToken().toString());
                        feedbackModel.setUserId(basicInformation.getUserId().toString());
                        feedbackModel.setCreateDate(date_format);

                        String jsonString = gson.toJson(feedbackModel, FeedbackModel.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.feedbackapi(), jsonString);
                    }


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

    public void submitdiag()
    {
        //dialog intialization
        dialog = new Dialog(Feedback.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.feedback_diag);
        dialog.setCancelable(false);

        Button okplaced = (Button) dialog.findViewById(R.id.ok_feedback);

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();

            }
        });

        dialog.show();
    }

}


