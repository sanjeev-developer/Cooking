package com.saavor.user.chefserver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.saavor.user.Model.BookingList;
import com.saavor.user.Model.GetBookingHistory;
import com.saavor.user.R;
import com.saavor.user.activity.About;
import com.saavor.user.activity.Account;
import com.saavor.user.activity.AddressBook;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Bookmark;
import com.saavor.user.activity.CardBook;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.activity.FaqWebview;
import com.saavor.user.activity.Favourite;
import com.saavor.user.activity.Feedback;
import com.saavor.user.activity.Filter;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.activity.Notifications;
import com.saavor.user.activity.OrderHistory;
import com.saavor.user.activity.ReferralTwo;
import com.saavor.user.activity.UserInfo;
import com.saavor.user.adapter.OrderHistoryAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.adapter.BookingHistoryAdapter;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class BookingHistoryActivity extends BaseActivity implements View.OnClickListener,OnResultReceived{


    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;
    private LinearLayout ll_no_history;
    private RecyclerView recycle_bookingHistory;

    BookingHistoryAdapter bookingHistoryAdapter;

    ArrayList<BookingList>AlBookingList;

    String Confirm="";
    private Calendar calendar;
    private int mYear, mMonth, mDay;
    private String monthname;
    String datedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_view_booking_history);
        InitializeInterface();

        try {
            settingdatanav();

        } catch (Exception e) {
            e.printStackTrace();
        }


        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        switch (mMonth+1) {
            case 1:
                monthname = "Jan";

                break;

            case 2:
                monthname = "Feb";

                break;

            case 3:
                monthname = "Mar";

                break;

            case 4:
                monthname = "Apr";

                break;

            case 5:
                monthname = "May";

                break;

            case 6:
                monthname = "Jun";

                break;

            case 7:
                monthname = "Jul";

                break;

            case 8:
                monthname = "Aug";

                break;

            case 9:
                monthname = "Sep";
                break;

            case 10:
                monthname = "Oct";
                break;

            case 11:
                monthname = "Nov";
                break;

            case 12:
                monthname = "Dec";

                break;
        }

        datedata =monthname+" "+mDay+", "+mYear;


    }

    private void InitializeInterface() {
        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();

        basicfetch();
        navintial();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_booking);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_booking);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_booking);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

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

        txt_nav_BookingHistory.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        txt_nav_BookingHistory.setTextColor(getResources().getColor(R.color.white));

        ll_no_history=(LinearLayout)findViewById(R.id.ll_no_history);
        recycle_bookingHistory=(RecyclerView)findViewById(R.id.recycle_bookingHistory);

        Confirm=getIntent().getStringExtra("Confirm");
        if(Confirm==null){
            Confirm="";
        }

        GetBookingHistory getBookingHistory=new GetBookingHistory();
        getBookingHistory.setCurrentDate(date_format);
        getBookingHistory.setSessionToken(basicInformation.getSessionToken());
        getBookingHistory.setUserId(basicInformation.getUserId());
        load_dialog.show();

        String jsonString = oGson.toJson(getBookingHistory, GetBookingHistory.class).toString();
        PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.GetBookingHistory);
        oInsertUpdateApi.executePostRequest(API.fGetBookingHistory(), jsonString);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_nav_BookingHistory:

                drawer.closeDrawer(GravityCompat.START);
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
//

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
                intent = new Intent(this, FaqWebview.class);
                intent.putExtra("webdata","http://demohelpdesk.saavor.io/faq/qalist");
                intent.putExtra("titledata","Faq");
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
                AlertDialog.Builder mDialog = new AlertDialog.Builder(BookingHistoryActivity.this);

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void dispatchString(RequestSource from, String what) {
        ServerResult = what;
        if (from.toString().equalsIgnoreCase("GetBookingHistory")) {
            switch (ServerResult) {
                case "-1":
                    load_dialog.cancel();
                case "-2":
                    load_dialog.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nointernet();
                        }
                    });
                    break;
                case "-3":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                         //   Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });

                    break;

                case "5":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }});

                    break;
                default:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final JSONObject result;
                            final JSONArray JaBookingList;
                            try {
                                result = new JSONObject(ServerResult);
                                if (result.getString("ReturnCode").equals("1")) {
                                    AlBookingList=new ArrayList<BookingList>();
                                    JaBookingList=result.getJSONArray("BookingList");

                                    for(int m=0;m<JaBookingList.length();m++){
                                        BookingList bookingList=new BookingList();
                                        bookingList.setBookingID(JaBookingList.getJSONObject(m).getString("BookingID"));
                                        bookingList.setBookingStatus(JaBookingList.getJSONObject(m).getString("BookingStatus"));
                                        bookingList.setBookingsId(JaBookingList.getJSONObject(m).getString("BookingsId"));
                                        bookingList.setBusinessName(JaBookingList.getJSONObject(m).getString("BusinessName"));
                                        bookingList.setChefName(JaBookingList.getJSONObject(m).getString("ChefName"));
                                        bookingList.setCustomerAddress(JaBookingList.getJSONObject(m).getString("CustomerAddress"));
                                        bookingList.setEventTitle(JaBookingList.getJSONObject(m).getString("EventTitle"));
                                        bookingList.setIsReOrder(JaBookingList.getJSONObject(m).getString("IsReOrder"));
                                        bookingList.setIsReview(JaBookingList.getJSONObject(m).getString("IsReview"));
                                        bookingList.setSlotDates(JaBookingList.getJSONObject(m).getString("SlotDates"));
                                        bookingList.setStarRating(JaBookingList.getJSONObject(m).getString("StarRating"));
                                        bookingList.setStatus(JaBookingList.getJSONObject(m).getString("Status"));
                                        bookingList.setTipType(JaBookingList.getJSONObject(m).getString("TipType"));
                                        bookingList.setTotalAmount(JaBookingList.getJSONObject(m).getString("TotalAmount"));
                                        bookingList.setIsClockIn(JaBookingList.getJSONObject(m).getString("IsClockIn"));
                                        bookingList.setIsClockOut(JaBookingList.getJSONObject(m).getString("IsClockOut"));
                                        bookingList.setIsClockOutConfirm(JaBookingList.getJSONObject(m).getString("IsClockOutConfirm"));
                                        bookingList.setIsClockInConfirm(JaBookingList.getJSONObject(m).getString("IsClockInConfirm"));
                                        bookingList.setIsArrived(JaBookingList.getJSONObject(m).getString("IsArrived"));
                                        AlBookingList.add(bookingList);

                                    }

                                    Collections.reverse(AlBookingList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BookingHistoryActivity.this);
                                    recycle_bookingHistory.setLayoutManager(mLayoutManager);
                                    recycle_bookingHistory.setItemAnimator(new DefaultItemAnimator());
                                    bookingHistoryAdapter=new BookingHistoryAdapter(BookingHistoryActivity.this,AlBookingList,Confirm,basicInformation.getSessionToken(),basicInformation.getUserId(),date_format, datedata);
                                    recycle_bookingHistory.setAdapter(bookingHistoryAdapter );

                                    load_dialog.cancel();
                                }
                                else if (result.getString("ReturnCode").equals("5")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    intent.putExtra("Sessionexp", 1);
                                                    startActivity(intent);
                                                }});
                                        }
                                    });
                                }
                                else {

                                    load_dialog.cancel();
                                    recycle_bookingHistory.setVisibility(View.GONE);
                                    ll_no_history.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                                load_dialog.cancel();
                            }
                        }
                    });
                    break;
            }
        }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(BookingHistoryActivity.this);
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

                GetBookingHistory getBookingHistory=new GetBookingHistory();
                getBookingHistory.setCurrentDate(date_format);
                getBookingHistory.setSessionToken(basicInformation.getSessionToken());
                getBookingHistory.setUserId(basicInformation.getUserId());
                load_dialog.show();

                String jsonString = oGson.toJson(getBookingHistory, GetBookingHistory.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetBookingHistory);
                oInsertUpdateApi.executePostRequest(API.fGetBookingHistory(), jsonString);


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

    public void hitapi() {
    }
}
