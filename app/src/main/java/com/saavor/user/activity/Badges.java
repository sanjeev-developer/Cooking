package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.saavor.user.Model.BadgeList;
import com.saavor.user.Model.BadgesData;
import com.saavor.user.Model.BadgesHit;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.PostApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.saavor.user.activity.DashBoard.notcount;

public class Badges extends BaseActivity implements View.OnClickListener, OnResultReceived {

    BadgesData badgesData;
    private Dialog dialog;
    @BindView(R.id.gr_newbie)
    ImageView grnewbie;
    @BindView(R.id.gr_explorer)
    ImageView gr_explorer;
    @BindView(R.id.gr_acheiver)
    ImageView gr_acheiver;
    @BindView(R.id.gr_star)
    ImageView gr_star;
    @BindView(R.id.gr_superstar)
    ImageView gr_superstar;
    @BindView(R.id.bf_newbie)
    ImageView bf_newbie;
    @BindView(R.id.bf_explorer)
    ImageView bf_explorer;
    @BindView(R.id.bf_acheiver)
    ImageView bf_acheiver;
    @BindView(R.id.bf_star)
    ImageView bf_star;
    @BindView(R.id.bf_superstar)
    ImageView bf_superstar;
    @BindView(R.id.fp_newbie)
    ImageView fp_newbie;
    @BindView(R.id.fp_explorer)
    ImageView fp_explorer;
    @BindView(R.id.fp_acheiver)
    ImageView fp_acheiver;
    @BindView(R.id.fp_star)
    ImageView fp_star;
    @BindView(R.id.fp_superstar)
    ImageView fp_superstar;
    @BindView(R.id.cj_newbie)
    ImageView cj_newbie;
    @BindView(R.id.cj_explorer)
    ImageView cj_explorer;
    @BindView(R.id.cj_acheiver)
    ImageView cj_acheiver;
    @BindView(R.id.cj_star)
    ImageView cj_star;
    @BindView(R.id.cj_superstar)
    ImageView cj_superstar;
    @BindView(R.id.bb_newbie)
    ImageView bb_newbie;
    @BindView(R.id.bb_explorer)
    ImageView bb_explorer;
    @BindView(R.id.bb_acheiver)
    ImageView bb_acheiver;
    @BindView(R.id.bb_star)
    ImageView bb_star;
    @BindView(R.id.bb_superstar)
    ImageView bb_superstar;
    @BindView(R.id.rr_newbie)
    ImageView rr_newbie;
    @BindView(R.id.rr_explorer)
    ImageView rr_explorer;
    @BindView(R.id.rr_acheiver)
    ImageView rr_acheiver;
    @BindView(R.id.rr_star)
    ImageView rr_star;
    @BindView(R.id.rr_superstar)
    ImageView rr_superstar;
    @BindView(R.id.sb_newbie)
    ImageView sb_newbie;
    @BindView(R.id.sb_explorer)
    ImageView sb_explorer;
    @BindView(R.id.sb_acheiver)
    ImageView sb_acheiver;
    @BindView(R.id.sb_star)
    ImageView sb_star;
    @BindView(R.id.sb_superstar)
    ImageView sb_superstar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_badges);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_badges);
        setSupportActionBar(toolbar);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_badges);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_badges);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        ButterKnife.bind(this);

        basicfetch();
        //intialization of navigation
        navintial ();
        mOnResultReceived = this;


        //setting data on navigation drawer(like image and name)
        try
        {
            settingdatanav();


        }catch (Exception e)
        {

        }

        badgesData = new BadgesData();

        //setting click listener on badges
        grnewbie.setOnClickListener(this);
        gr_explorer.setOnClickListener(this);
        gr_acheiver.setOnClickListener(this);
        gr_star.setOnClickListener(this);
        gr_superstar.setOnClickListener(this);

        bf_newbie.setOnClickListener(this);
        bf_explorer.setOnClickListener(this);
        bf_acheiver.setOnClickListener(this);
        bf_star.setOnClickListener(this);
        bf_superstar.setOnClickListener(this);

        fp_newbie.setOnClickListener(this);
        fp_explorer.setOnClickListener(this);
        fp_acheiver.setOnClickListener(this);
        fp_star.setOnClickListener(this);
        fp_superstar.setOnClickListener(this);

        cj_newbie.setOnClickListener(this);
        cj_explorer.setOnClickListener(this);
        cj_acheiver.setOnClickListener(this);
        cj_star.setOnClickListener(this);
        cj_superstar.setOnClickListener(this);

        bb_newbie.setOnClickListener(this);
        bb_explorer.setOnClickListener(this);
        bb_acheiver.setOnClickListener(this);
        bb_star.setOnClickListener(this);
        bb_superstar.setOnClickListener(this);

        rr_newbie.setOnClickListener(this);
        rr_explorer.setOnClickListener(this);
        rr_acheiver.setOnClickListener(this);
        rr_star.setOnClickListener(this);
        rr_superstar.setOnClickListener(this);

        sb_newbie.setOnClickListener(this);
        sb_explorer.setOnClickListener(this);
        sb_acheiver.setOnClickListener(this);
        sb_star.setOnClickListener(this);
        sb_superstar.setOnClickListener(this);

        navbadges.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navbadges.setTextColor(getResources().getColor(R.color.white));

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
        navnotcount.setText(""+notcount);

        load_dialog.show();
        BadgesHit badgesHit = new BadgesHit();
        badgesHit.setSessionToken(basicInformation.getSessionToken());
        badgesHit.setUserId(basicInformation.getUserId());

        String jsonString = gson.toJson(badgesHit, BadgesHit.class).toString();
        PostApiClient loginApi = new PostApiClient(mOnResultReceived);
        loginApi.executePostRequest(API.badgeshit(), jsonString);
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

                drawer.closeDrawer(GravityCompat.START);

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
                dialog = new Dialog(Badges.this);
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


            case R.id.gr_newbie:
                badgedailog(badgesData.getBageCategoryList().get(0).getBadgeList().get(0),R.drawable.newbie_enabled,R.drawable.newbie_disabled);
                break;

            case R.id.gr_explorer:
                badgedailog(badgesData.getBageCategoryList().get(0).getBadgeList().get(1), R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.gr_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(0).getBadgeList().get(2), R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.gr_star:
                badgedailog(badgesData.getBageCategoryList().get(0).getBadgeList().get(3), R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.gr_superstar:
                badgedailog(badgesData.getBageCategoryList().get(0).getBadgeList().get(4), R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;


            case R.id.bf_newbie:
                badgedailog(badgesData.getBageCategoryList().get(1).getBadgeList().get(0), R.drawable.newbie_enabled, R.drawable.newbie_disabled);
                break;

            case R.id.bf_explorer:
                badgedailog(badgesData.getBageCategoryList().get(1).getBadgeList().get(1), R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.bf_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(1).getBadgeList().get(2), R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.bf_star:
                badgedailog(badgesData.getBageCategoryList().get(1).getBadgeList().get(3), R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.bf_superstar:
                badgedailog(badgesData.getBageCategoryList().get(1).getBadgeList().get(4), R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;


            case R.id.fp_newbie:
                badgedailog(badgesData.getBageCategoryList().get(2).getBadgeList().get(0), R.drawable.newbie_enabled, R.drawable.newbie_disabled);
                break;

            case R.id.fp_explorer:
                badgedailog(badgesData.getBageCategoryList().get(2).getBadgeList().get(1),R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.fp_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(2).getBadgeList().get(2), R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.fp_star:
                badgedailog(badgesData.getBageCategoryList().get(2).getBadgeList().get(3),R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.fp_superstar:
                badgedailog(badgesData.getBageCategoryList().get(2).getBadgeList().get(4),  R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;

            case R.id.cj_newbie:
                badgedailog(badgesData.getBageCategoryList().get(3).getBadgeList().get(0), R.drawable.newbie_enabled, R.drawable.newbie_disabled);
                break;

            case R.id.cj_explorer:
                badgedailog(badgesData.getBageCategoryList().get(3).getBadgeList().get(1), R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.cj_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(3).getBadgeList().get(2),  R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.cj_star:
                badgedailog(badgesData.getBageCategoryList().get(3).getBadgeList().get(3),R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.cj_superstar:
                badgedailog(badgesData.getBageCategoryList().get(3).getBadgeList().get(4), R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;

            case R.id.bb_newbie:
                badgedailog(badgesData.getBageCategoryList().get(4).getBadgeList().get(0), R.drawable.newbie_enabled, R.drawable.newbie_disabled);
                break;

            case R.id.bb_explorer:
                badgedailog(badgesData.getBageCategoryList().get(4).getBadgeList().get(1),  R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.bb_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(4).getBadgeList().get(2), R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.bb_star:
                badgedailog(badgesData.getBageCategoryList().get(4).getBadgeList().get(3), R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.bb_superstar:
                badgedailog(badgesData.getBageCategoryList().get(4).getBadgeList().get(4), R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;

            case R.id.rr_newbie:
                badgedailog(badgesData.getBageCategoryList().get(6).getBadgeList().get(0), R.drawable.newbie_enabled, R.drawable.newbie_disabled);
                break;

            case R.id.rr_explorer:
                badgedailog(badgesData.getBageCategoryList().get(6).getBadgeList().get(1), R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.rr_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(6).getBadgeList().get(2), R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.rr_star:
                badgedailog(badgesData.getBageCategoryList().get(6).getBadgeList().get(3),  R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.rr_superstar:
                badgedailog(badgesData.getBageCategoryList().get(6).getBadgeList().get(4),  R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;

            case R.id.sb_newbie:
                badgedailog(badgesData.getBageCategoryList().get(5).getBadgeList().get(0), R.drawable.newbie_enabled, R.drawable.newbie_disabled);
                break;

            case R.id.sb_explorer:
                badgedailog(badgesData.getBageCategoryList().get(5).getBadgeList().get(1),R.drawable.explorer_enabled, R.drawable.explorer_disabled);
                break;

            case R.id.sb_acheiver:
                badgedailog(badgesData.getBageCategoryList().get(5).getBadgeList().get(2),R.drawable.achiever_enabled, R.drawable.achiever_disabled);
                break;

            case R.id.sb_star:
                badgedailog(badgesData.getBageCategoryList().get(5).getBadgeList().get(3), R.drawable.star_enabled, R.drawable.star_disabled);
                break;

            case R.id.sb_superstar:
                badgedailog(badgesData.getBageCategoryList().get(5).getBadgeList().get(4), R.drawable.superstar_enabled, R.drawable.superstar_disabled);
                break;

        }
    }

    private void logOut()
    {
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
                    displayAlert(Badges.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {
                    try {

                        JSONObject Jsonobject = new JSONObject(what);
                        String jsonString = Jsonobject.toString();

                        badgesData = gson.fromJson(jsonString, BadgesData.class);
                        System.out.println(">>>>" + jsonString);
                        gson = new Gson();

                        String check = badgesData.getReturnCode();
                        System.out.println(">>>>" + check);

                        if (check.equals("1")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    load_dialog.cancel();

                            if(badgesData.getBageCategoryList().get(0).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(0).getEarnedPoints())
                            {
                                try
                                {
                                    grnewbie.setImageResource(R.drawable.newbie_disabled);
                                }catch(Exception e)
                                {
                                    System.out.println(">>>>>>>>>>>>>"+e);
                                }

                            }
                            else
                            {
                                grnewbie.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(0).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(1).getEarnedPoints())
                            {
                                gr_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                gr_explorer.setImageResource(R.drawable.explorer_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(0).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(2).getEarnedPoints())
                            {
                                gr_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                gr_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(0).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(3).getEarnedPoints())
                            {
                                gr_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                gr_star.setImageResource(R.drawable.star_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(0).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(4).getEarnedPoints())
                            {
                                gr_superstar.setImageResource(R.drawable.superstar_disabled);
                            }
                            else
                            {
                                gr_superstar.setImageResource(R.drawable.superstar_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(1).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(1).getBadgeList().get(0).getEarnedPoints())
                            {
                                bf_newbie.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                bf_newbie.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(1).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(1).getBadgeList().get(1).getEarnedPoints())
                            {
                                bf_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                bf_explorer.setImageResource(R.drawable.explorer_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(1).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(1).getBadgeList().get(2).getEarnedPoints())
                            {
                                bf_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                bf_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(1).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(1).getBadgeList().get(3).getEarnedPoints())
                            {
                                bf_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                bf_star.setImageResource(R.drawable.star_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(1).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(1).getBadgeList().get(4).getEarnedPoints())
                            {
                                bf_superstar.setImageResource(R.drawable.superstar_disabled);
                            }
                            else
                            {
                                bf_superstar.setImageResource(R.drawable.superstar_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(2).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(2).getBadgeList().get(0).getEarnedPoints())
                            {
                                fp_newbie.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                fp_newbie.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(2).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(2).getBadgeList().get(1).getEarnedPoints())
                            {
                                fp_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                fp_explorer.setImageResource(R.drawable.explorer_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(2).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(2).getBadgeList().get(2).getEarnedPoints())
                            {
                                fp_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                fp_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(2).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(2).getBadgeList().get(3).getEarnedPoints())
                            {
                                fp_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                fp_star.setImageResource(R.drawable.star_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(2).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(2).getBadgeList().get(4).getEarnedPoints())
                            {
                                fp_superstar.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                fp_superstar.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(3).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(3).getBadgeList().get(0).getEarnedPoints())
                            {
                                cj_newbie.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                cj_newbie.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(3).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(3).getBadgeList().get(1).getEarnedPoints())
                            {
                                cj_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                cj_explorer.setImageResource(R.drawable.explorer_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(3).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(3).getBadgeList().get(2).getEarnedPoints())
                            {
                                cj_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                cj_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(3).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(3).getBadgeList().get(3).getEarnedPoints())
                            {
                                cj_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                cj_star.setImageResource(R.drawable.star_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(3).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(3).getBadgeList().get(4).getEarnedPoints())
                            {
                                cj_superstar.setImageResource(R.drawable.superstar_disabled);
                            }
                            else
                            {
                                cj_superstar.setImageResource(R.drawable.superstar_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(4).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(4).getBadgeList().get(0).getEarnedPoints())
                            {
                                bb_newbie.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                bb_newbie.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(4).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(4).getBadgeList().get(1).getEarnedPoints())
                            {
                                bb_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                bb_explorer.setImageResource(R.drawable.explorer_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(4).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(4).getBadgeList().get(2).getEarnedPoints())
                            {
                                bb_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                bb_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(4).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(4).getBadgeList().get(3).getEarnedPoints())
                            {
                                bb_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                bb_star.setImageResource(R.drawable.star_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(4).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(4).getBadgeList().get(4).getEarnedPoints())
                            {
                                bb_superstar.setImageResource(R.drawable.superstar_disabled);
                            }
                            else
                            {
                                bb_superstar.setImageResource(R.drawable.superstar_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(5).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(5).getBadgeList().get(0).getEarnedPoints())
                            {
                                sb_newbie.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                sb_newbie.setImageResource(R.drawable.newbie_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(5).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(5).getBadgeList().get(1).getEarnedPoints())
                            {
                                sb_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                sb_explorer.setImageResource(R.drawable.explorer_enabled);
                            }
                            if(badgesData.getBageCategoryList().get(5).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(5).getBadgeList().get(2).getEarnedPoints())
                            {
                                sb_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                sb_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(5).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(5).getBadgeList().get(3).getEarnedPoints())
                            {
                                sb_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                sb_star.setImageResource(R.drawable.star_enabled);
                            }
                            if(badgesData.getBageCategoryList().get(5).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(5).getBadgeList().get(4).getEarnedPoints())
                            {
                                sb_superstar.setImageResource(R.drawable.superstar_disabled);
                            }
                            else
                            {
                                sb_superstar.setImageResource(R.drawable.superstar_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(6).getBadgeList().get(0).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(0).getEarnedPoints())
                            {
                                rr_newbie.setImageResource(R.drawable.newbie_disabled);
                            }
                            else
                            {
                                rr_newbie.setImageResource(R.drawable.newbie_enabled);
                            }
                            if(badgesData.getBageCategoryList().get(6).getBadgeList().get(1).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(1).getEarnedPoints())
                            {
                                rr_explorer.setImageResource(R.drawable.explorer_disabled);
                            }
                            else
                            {
                                rr_explorer.setImageResource(R.drawable.explorer_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(6).getBadgeList().get(2).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(2).getEarnedPoints())
                            {
                                rr_acheiver.setImageResource(R.drawable.achiever_disabled);
                            }
                            else
                            {
                                rr_acheiver.setImageResource(R.drawable.achiever_enabled);
                            }
                            if(badgesData.getBageCategoryList().get(6).getBadgeList().get(3).getTargetPoints()>badgesData.getBageCategoryList().get(0).getBadgeList().get(3).getEarnedPoints())
                            {
                                rr_star.setImageResource(R.drawable.star_disabled);
                            }
                            else
                            {
                                rr_star.setImageResource(R.drawable.star_enabled);
                            }

                            if(badgesData.getBageCategoryList().get(6).getBadgeList().get(4).getTargetPoints()>badgesData.getBageCategoryList().get(6).getBadgeList().get(4).getEarnedPoints())
                            {
                                rr_superstar.setImageResource(R.drawable.superstar_disabled);
                            }
                            else
                            {
                                rr_superstar.setImageResource(R.drawable.superstar_enabled);
                            }

                        }
            });

                    } else if (check.equals("0")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(Badges.this, "" + loginDataReturn.getReturnMessage());

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(Badges.this, loginDataReturn.getReturnMessage());
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
                } catch (JSONException e) {
                    System.out.println("" + e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            nointernet();
                            // displayAlert(Login.this, "Something went wrong! Please try again!");
                        }
                    });
                }
            }

        }

    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(Badges.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings = (Button) dialog.findViewById(R.id.not_settings);
        Button retry = (Button) dialog.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                load_dialog.show();
                BadgesHit badgesHit = new BadgesHit();
                badgesHit.setSessionToken(basicInformation.getSessionToken());
                badgesHit.setUserId(basicInformation.getUserId());

                String jsonString = gson.toJson(badgesHit, BadgesHit.class).toString();
                PostApiClient loginApi = new PostApiClient(mOnResultReceived);
                loginApi.executePostRequest(API.badgeshit(), jsonString);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        dialog.show();
    }

    public void badgedailog(BadgeList badgeList, int img_enabled, int img_disabled)
    {
        // Create custom dialog object
        dialog = new Dialog(Badges.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.badges_dialog);

        ImageView badge_img = (ImageView) dialog.findViewById(R.id.badge_diag_img);
        TextView diag_title = (TextView) dialog.findViewById(R.id.badge_diag_title);
        TextView diag_sub_title = (TextView) dialog.findViewById(R.id.badge_subtitle_diag);
        TextView diag_desc = (TextView) dialog.findViewById(R.id.badge_desc);
        TextView diag_earn_total = (TextView) dialog.findViewById(R.id.badge_earn_total);
        ProgressBar diag_progress= (ProgressBar) dialog.findViewById(R.id.badge_progress);

        diag_progress.setProgress(badgeList.getEarnedPoints());
        diag_progress.setMax(badgeList.getTargetPoints());

        if(badgeList.getTargetPoints()>badgeList.getEarnedPoints())
        {
            badge_img.setImageResource(img_disabled);
        }
        else
        {
            badge_img.setImageResource(img_enabled);
        }

        diag_title.setText(badgeList.getCategoryName());
        diag_sub_title.setText(badgeList.getBadgeName());
        diag_desc.setText(badgeList.getDescription());
        diag_earn_total.setText(badgeList.getEarnedPoints()+"/"+badgeList.getTargetPoints());

        dialog.show();
    }
}