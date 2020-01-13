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

import com.saavor.user.Model.BookmarkList;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.KitchenBookmarkList;
import com.saavor.user.R;
import com.saavor.user.adapter.BookmarkAdapter;
import com.saavor.user.adapter.ChefBookmarkAdap;
import com.saavor.user.adapter.NotificationAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.notcount;

public class Bookmark extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_bookmark ,recycle_bookmark_chef;
    private BookmarkAdapter bookmarkAdapter;
    ChefBookmarkAdap chefBookmarkAdap;
    private BookmarkList booklist ;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout LL_no_book;
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    ArrayList<KitchenBookmarkList>kitchenBookmarkLists =new ArrayList<KitchenBookmarkList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_bookmark);

        //intializing all elements in ui

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bookmark);
        setSupportActionBar(toolbar);

        //setting delivery pref
        deliverydetailspref=getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        booklist=new BookmarkList();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_bookmark);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_bookmark);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        basicfetch();
        mOnResultReceived = this;
        recyclerView_bookmark = (RecyclerView) findViewById(R.id.recycle_bookmark);
        recycle_bookmark_chef = (RecyclerView) findViewById(R.id.recycle_bookmark_chef);
        LL_no_book= (LinearLayout) findViewById(R.id.ll_no_book);

        //intialization of navigation
        navintial();

        //setting data on navigation drawer(like image and name)
        try
        {
            settingdatanav();


        } catch (Exception e) {

        }

        navbook.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navbook.setTextColor(getResources().getColor(R.color.white));

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

                drawer.closeDrawer(GravityCompat.START);
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
                intent = new Intent(this, About.class);
                this.startActivity(intent);
                break;

            case R.id.txt_nav_logout:

                //dialog intialization
                dialog = new Dialog(Bookmark.this);
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
    public void dispatchString(RequestSource from, String what) {

         if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //load_dialog.cancel();
                    load_dialog.cancel();
                    displayAlert(Bookmark.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
        try {

            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();

            gson = new Gson();
            booklist = gson.fromJson(jsonString, BookmarkList.class);
            System.out.println(">>>>" + what);
            String check = booklist.getReturnCode();
            final String message = booklist.getReturnMessage();
            kitchenBookmarkLists =booklist.getKitchenBookmarkList();

            if (check.equals("1")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                      //  load_dialog.cancel();
                        load_dialog.cancel();
                        LL_no_book.setVisibility(View.GONE);

                        if(kitchenBookmarkLists.isEmpty() && booklist.getChefBookmarkList().isEmpty())
                        {
                            LL_no_book.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if(!(kitchenBookmarkLists.isEmpty()))
                            {
                                recyclerView_bookmark.setVisibility(View.VISIBLE);
                                bookmarkAdapter = new BookmarkAdapter(Bookmark.this,kitchenBookmarkLists );
                                mLayoutManager = new LinearLayoutManager(Bookmark.this);
                                recyclerView_bookmark.setNestedScrollingEnabled(false);
                                recyclerView_bookmark.setFocusable(false);
                                recyclerView_bookmark.setLayoutManager(mLayoutManager);
                                recyclerView_bookmark.setAdapter(bookmarkAdapter);
//
//                                bookmarkAdapter = new BookmarkAdapter(Bookmark.this,kitchenBookmarkLists );
//                                recyclerView_bookmark.setLayoutManager(new LinearLayoutManager(Bookmark.this, LinearLayoutManager.VERTICAL, true));
//                                recyclerView_bookmark.setAdapter(bookmarkAdapter);
                            }

                            if(!(booklist.getChefBookmarkList().isEmpty()))
                            {
                                recycle_bookmark_chef.setVisibility(View.VISIBLE);
                                chefBookmarkAdap = new ChefBookmarkAdap(Bookmark.this,booklist.getChefBookmarkList());
                                mLayoutManager = new LinearLayoutManager(Bookmark.this);
                                recycle_bookmark_chef.setNestedScrollingEnabled(false);
                                recycle_bookmark_chef.setFocusable(false);
                                recycle_bookmark_chef.setLayoutManager(mLayoutManager);
                                recycle_bookmark_chef.setAdapter(chefBookmarkAdap);
                            }
                        }

                    }
                });
            } else if (check.equals("0")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(kitchenBookmarkLists.isEmpty() && booklist.getChefBookmarkList().isEmpty())
                        {
                            LL_no_book.setVisibility(View.VISIBLE);
                        }
                        //load_dialog.cancel();
                        load_dialog.cancel();

                    }
                });

            }

            else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // load_dialog.cancel();
                        load_dialog.cancel();
                        // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();
                        displayAlert(Bookmark.this, ""+message);
                    }
                });
            }

            else if (check.equals("5")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  load_dialog.cancel();
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
                  //  load_dialog.cancel();
                    load_dialog.cancel();
                   // redirect(Bookmark.this, "No internet access. Please turn on cellular data or use wifi.");
                    nointernet();
                }
            });
        }
    }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    @Override
    protected void onResume() {


        try {
            load_dialog.show();
            bookmarkViewModel.setSessionToken(basicInformation.getSessionToken().toString());
            bookmarkViewModel.setUserId(basicInformation.getUserId().toString());
            bookmarkViewModel.setCurrentDate(""+deliverydetailspref.getString("Deliverydate", ""));

            String jsonString = gson.toJson(bookmarkViewModel, BookmarkViewModel.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.executePostRequest(API.viewbookmark(), jsonString);

        } catch (Exception e) {

        }

        super.onResume();
    }

    // no internet pop-up
    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Bookmark.this);
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
                  //  load_dialog.show();
                    load_dialog.show();
                    bookmarkViewModel.setSessionToken(basicInformation.getSessionToken().toString());
                    bookmarkViewModel.setUserId(basicInformation.getUserId().toString());
                    bookmarkViewModel.setCurrentDate(""+deliverydetailspref.getString("Deliverydate", ""));

                    String jsonString = gson.toJson(bookmarkViewModel, BookmarkViewModel.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.viewbookmark(), jsonString);

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


}