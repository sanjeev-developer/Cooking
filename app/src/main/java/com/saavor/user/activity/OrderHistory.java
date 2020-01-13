package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.Cancelorder;
import com.saavor.user.Model.CartItem;
import com.saavor.user.Model.DashApiHit;
import com.saavor.user.Model.NonCustomizableList;
import com.saavor.user.Model.NotificationList;
import com.saavor.user.Model.NotificationReadHit;
import com.saavor.user.Model.NotificationResponse;
import com.saavor.user.Model.OrderHistoryReceive;
import com.saavor.user.Model.OrderList;
import com.saavor.user.Model.ReorderHit;
import com.saavor.user.Model.ReorderReturn;
import com.saavor.user.Model.TestDish;
import com.saavor.user.Model.TestModel;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.adapter.NotificationAdapter;
import com.saavor.user.adapter.OrderHistoryAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.firebaseservices.MyFirebaseMessagingService;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static com.saavor.user.R.id.ll_no_notification;
import static com.saavor.user.activity.DashBoard.notcount;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;


public class OrderHistory extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_orderHistory;
    private OrderHistoryAdapter orderHistoryAdapter;
    ArrayList<OrderList> orderLists = new ArrayList<>();
    private OrderHistoryReceive orderHistoryReceive = new OrderHistoryReceive();
    private Calendar calendar;
    private int mYear, mMonth, mDay;
    private String monthname;
    String datedata;
    ReorderReturn reorderReturn = new ReorderReturn();
    public Integer profile_id = 0;
    Integer adt = 0;
    int averagedeltime = 10;
    RecyclerView.LayoutManager mLayoutManager;
    int KitchenDelivery = 0;
    StringBuilder stringBuilder;
    ArrayList<NotificationList> notificationdata = new ArrayList<>();
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    LinearLayout LL_no_order;
    static Button click;
    boolean kitchen_del_radius = false;
    String ulyxcharge = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_ohistory);

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        basicfetch();
        mOnResultReceived = this;

        click = (Button) findViewById(R.id.realtime_click);
        click.setOnClickListener(this);
        //setting delivery pref
        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        LL_no_order = (LinearLayout) findViewById(R.id.ll_no_order);

        stringBuilder = new StringBuilder();
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        switch (mMonth + 1) {
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

        datedata = monthname + " " + mDay + ", " + mYear;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ohistory);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_ohistory);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ohistory);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        recyclerView_orderHistory = (RecyclerView) findViewById(R.id.recycle_orderHistory);

        //intialization of navigation
        navintial();

        try {
            settingdatanav();

        } catch (Exception e) {
            e.printStackTrace();
        }
        navorder.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navorder.setTextColor(getResources().getColor(R.color.white));

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

        //  handler.removeCallbacksAndMessages(null);

        switch (v.getId()) {

            case R.id.realtime_click:

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dashApiHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
                            dashApiHit.setSessionToken(basicInformation.getSessionToken().toString());
                            dashApiHit.setUserId(basicInformation.getUserId().toString());

                            String jsonString = gson.toJson(dashApiHit, DashApiHit.class).toString();
                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                            oChangePsswordApi.setRequestSource(RequestSource.orderhistory);

                            oChangePsswordApi.executePostRequest(API.GetOrderHistory(), jsonString);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


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
//                intent = new Intent(this, OrderHistory.class);
//                this.startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
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
                dialog = new Dialog(OrderHistory.this);
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

    public void hittingapi() {

        try {
            dashApiHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
            dashApiHit.setSessionToken(basicInformation.getSessionToken().toString());
            dashApiHit.setUserId(basicInformation.getUserId().toString());

            mProgressDialog.setMessage("Fetching order history");
            load_dialog.show();

            String jsonString = gson.toJson(dashApiHit, DashApiHit.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.setRequestSource(RequestSource.orderhistory);

            oChangePsswordApi.executePostRequest(API.GetOrderHistory(), jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dispatchString(final RequestSource from, String what) {

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(OrderHistory.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {
            String t = what;

            if (from.toString().equalsIgnoreCase("orderhistory")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    orderHistoryReceive = gson.fromJson(jsonString, OrderHistoryReceive.class);
                    System.out.println(">>>>" + what);
                    String check = orderHistoryReceive.getReturnCode();
                    final String message = orderHistoryReceive.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LL_no_order.setVisibility(View.GONE);
                                orderLists = orderHistoryReceive.getOrderList();
                                Collections.reverse(orderLists);
                                orderHistoryAdapter = new OrderHistoryAdapter(OrderHistory.this, orderLists, datedata);
                                mLayoutManager = new LinearLayoutManager(OrderHistory.this);
                                recyclerView_orderHistory.setLayoutManager(mLayoutManager);
                                recyclerView_orderHistory.setItemAnimator(new DefaultItemAnimator());
                                recyclerView_orderHistory.setAdapter(orderHistoryAdapter);

                                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                                oInsertUpdateApi.setRequestSource(RequestSource.notification_oh);
                                oInsertUpdateApi.executeGetRequest(API.notificationlist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                LL_no_order.setVisibility(View.VISIBLE);

                            }
                        });
                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(OrderHistory.this, message);

                            }
                        });
                    } else if (check.equals("5")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();

                                // handler.removeCallbacksAndMessages(null);
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
                            // redirect(OrderHistory.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("cancelorder")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    orderHistoryReceive = gson.fromJson(jsonString, OrderHistoryReceive.class);
                    System.out.println(">>>>" + what);
                    String check = orderHistoryReceive.getReturnCode();
                    final String message = orderHistoryReceive.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                hittingapi();

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(OrderHistory.this, message);

                            }
                        });
                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(OrderHistory.this, message);

                            }
                        });
                    } else if (check.equals("5")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                //  handler.removeCallbacksAndMessages(null);
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
                            //redirect(OrderHistory.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("Reorder")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    reorderReturn = gson.fromJson(jsonString, ReorderReturn.class);
                    System.out.println(">>>>" + what);
                    String check = reorderReturn.getReturnCode();
                    final String message = reorderReturn.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //basickitcheninfo();

                                if (reorderReturn.getKitchenInfo().getDealList().isEmpty()) {

                                } else {
                                    mTabel.putString("dis_MinAmount", "" + reorderReturn.getKitchenInfo().getDealList().get(0).getMinAmount());
                                    mTabel.putString("discount_type", "" + reorderReturn.getKitchenInfo().getDealList().get(0).getDealType());
                                    mTabel.putString("DealDiscount", "" + reorderReturn.getKitchenInfo().getDealList().get(0).getDealDiscount());
                                    mTabel.commit();
                                }
                                addtocart();

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(OrderHistory.this, message);

                            }
                        });
                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(OrderHistory.this, message);

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
                            //redirect(OrderHistory.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("addtocart")) {


                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    todayReturn = gson.fromJson(jsonString, TodayReturn.class);
                    System.out.println(">>>>what" + what);
                    String check = todayReturn.getReturnCode();
                    final String message = todayReturn.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                basicInformation.setAdt(adt);
                                mBasicInformation = gson.toJson(basicInformation);
                                mTabel.putString("basicinformation", mBasicInformation);
                                mTabel.commit();

                                //  handler.removeCallbacksAndMessages(null);
                                intent = new Intent(OrderHistory.this, Chart.class);
                                intent.putExtra("kitchentitle", reorderReturn.getCartInfo().getKitchenName());
                                intent.putExtra("reorder", true);
                                intent.putExtra("rdeladdress", reorderReturn.getCartInfo().getCustomerAddress());
                                intent.putExtra("risdel", "" + KitchenDelivery);
                                intent.putExtra("rprofileid", "" + profile_id);
                                intent.putExtra("rkitchendelcharge", "" + reorderReturn.getCartInfo().getDeliveryFee().toString());

                                OrderHistory.this.startActivity(intent);
                                load_dialog.cancel();
                            }
                        });


                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(OrderHistory.this, message);
                                load_dialog.cancel();
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(OrderHistory.this, message);
                            }
                        });

                    } else if (check.equals("5")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                // handler.removeCallbacksAndMessages(null);
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("Sessionexp", 1);
                                startActivity(intent);
                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("notification_oh")) {

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

                                load_dialog.cancel();

                                load_dialog.cancel();
                                notcount = 0;
                                for (int i = 0; i < notificationdata.size(); i++) {

                                    if (notificationdata.get(i).getIsRead() == 0) {
                                        notcount++;
                                    }
                                }

                                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_notification);
                                if (notcount > 0) {
                                    frameLayout.setVisibility(View.VISIBLE);
                                    navnotcount.setText("" + notcount);
                                } else {
                                    frameLayout.setVisibility(View.INVISIBLE);
                                }
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
    }

    public void showCustomDialog(final String s, final Integer profileId, Integer avgDeliveryTime, Integer isKitchenDelivery, final String customerAddress) {

        adt = avgDeliveryTime;
        KitchenDelivery = isKitchenDelivery;

        //dialog intialization
        dialog = new Dialog(OrderHistory.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.reorder_diag);
        dialog.setCancelable(false);

        Button yes = (Button) dialog.findViewById(R.id.yes_reorder);
        Button no = (Button) dialog.findViewById(R.id.not_reorder);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                profile_id = profileId;

                ReorderHit reorderHit = new ReorderHit();
                reorderHit.setCompleteAddress("" + customerAddress);
                reorderHit.setCreateDate(date_format);
                reorderHit.setDeliveryFrom("10:00AM");
                reorderHit.setDeliveryTo("11:00AM");
                reorderHit.setFoodOrderId(s);
                reorderHit.setOrderDate(date_format);
                reorderHit.setIsDelivery("" + KitchenDelivery);
                reorderHit.setProfileId(profileId);
                reorderHit.setSessionToken(basicInformation.getSessionToken());
                reorderHit.setUserId(basicInformation.getUserId());

                mProgressDialog.setMessage("Please Wait");
                load_dialog.show();

                String jsonString = gson.toJson(reorderHit, ReorderHit.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.Reorder);
                oChangePsswordApi.executePostRequest(API.reorder(), jsonString);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void cancelorder(final Integer foodOrderId) {

        //dialog intialization
        dialog = new Dialog(OrderHistory.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.cancel_diag);
        dialog.setCancelable(false);

        Button yes = (Button) dialog.findViewById(R.id.yes_cancel);
        Button no = (Button) dialog.findViewById(R.id.not_cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Cancelorder cancel = new Cancelorder();
                cancel.setCurrentDate(date_format);
                cancel.setUserId(basicInformation.getUserId());
                cancel.setSessionToken(basicInformation.getSessionToken());
                cancel.setFoodOrderId("" + foodOrderId);

                mProgressDialog.setMessage("Canceling your order");
                load_dialog.show();

                String jsonString = gson.toJson(cancel, Cancelorder.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.cancelorder);
                oChangePsswordApi.executePostRequest(API.cancelorder(), jsonString);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.cancel();
            }
        });

        dialog.show();

    }


    public void addtocart() {
        DashBoard.totalprice = 0;
        double subtotal = 0;
        storenotcustomizable.clear();
        ArrayList<TestDish> testDishes = new ArrayList<>();

        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems = reorderReturn.getCartInfo().getCartItems();

        for (int j = 0; j < cartItems.size(); j++) {

            NonCustomizableList nonCustomizableList = new NonCustomizableList();

            nonCustomizableList.setDishId(cartItems.get(j).getDishId());
            nonCustomizableList.setPrice(cartItems.get(j).getPrice());
            nonCustomizableList.setCalories(cartItems.get(j).getCalories());
            nonCustomizableList.setIsCustomizable(cartItems.get(j).getIsCustomizable());
            nonCustomizableList.setItemsName(cartItems.get(j).getCustomItemsName());
            nonCustomizableList.setItemsCost(cartItems.get(j).getCustomItemsCost());
            nonCustomizableList.setDishname(cartItems.get(j).getDishName());


            if (cartItems.get(j).getQuantity() > cartItems.get(j).getAvailableQty()) {
                nonCustomizableList.setQuantity(cartItems.get(j).getAvailableQty());
                cartItems.get(j).setQuantity(cartItems.get(j).getAvailableQty());
            } else {
                nonCustomizableList.setQuantity(cartItems.get(j).getQuantity());
            }
            nonCustomizableList.setAvilquantity(cartItems.get(j).getAvailableQty());
            nonCustomizableList.setPreprationtime(cartItems.get(j).getPreparingTime());

            if (cartItems.get(j).getCustomItemsCost().toString().equals("")) {
                double totalamount = 0;
                totalamount = totalamount + cartItems.get(j).getPrice();
                nonCustomizableList.setUpdatedprice(totalamount);
                subtotal = subtotal + totalamount * cartItems.get(j).getQuantity();
            } else {

                String check = "" + cartItems.get(j).getCustomItemsCost();
                int evaluate = check.indexOf('~');

                if (evaluate == -1) {
                    double totalamount = 0;
                    totalamount = cartItems.get(j).getQuantity() * cartItems.get(j).getPrice() + Float.parseFloat(cartItems.get(j).getCustomItemsCost());
                    nonCustomizableList.setUpdatedprice(totalamount);
                    subtotal = subtotal + totalamount;

                } else {
                    try {
                        float z = 0;
                        String[] aSplit = check.split("~");

                        for (int i = 0; i < aSplit.length; i++) {
                            float d = Float.parseFloat(aSplit[i]);
                            z = z + d;
                        }
                        double t = cartItems.get(j).getPrice() + z;
                        nonCustomizableList.setUpdatedprice(t);
                        subtotal = subtotal + t * cartItems.get(j).getQuantity();


                    } catch (Exception e)

                    {
                        System.out.println("final" + e);
                    }
                }
            }

            //saving object only for first time
            storenotcustomizable.add(nonCustomizableList);
        }


        DashBoard.totalprice = subtotal;


        for (int i = 0; i < storenotcustomizable.size(); i++) {
            TestDish testDish = new TestDish();
            testDish.setCalories(storenotcustomizable.get(i).getCalories());
            testDish.setDishId(storenotcustomizable.get(i).getDishId());
            testDish.setIsCustomizable(storenotcustomizable.get(i).getIsCustomizable());
            testDish.setPrice(storenotcustomizable.get(i).getPrice());
            testDish.setQuantity(storenotcustomizable.get(i).getQuantity());
            testDish.setItemsName(storenotcustomizable.get(i).getItemsName());
            testDish.setItemsCost(storenotcustomizable.get(i).getItemsCost());
            testDishes.add(testDish);
        }


        try {
            mProgressDialog.setMessage("Adding Your Items To Your Cart");
            load_dialog.show();

            String testdishesStr = gson.toJson(testDishes);

            TestModel testModel = new TestModel();
            testModel.setCompleteAddress("" + reorderReturn.getCartInfo().getCustomerAddress());
            testModel.setContactNumber("");
            testModel.setCreateDate("" + date_format);
            testModel.setDeliveryFrom("10:00PM");
            testModel.setDeliveryTo("11:00PM");

            if (reorderReturn.getKitchenInfo().getIsDelivery() == 1) {
                testModel.setIsDelivery("1");
                testModel.setDeliveryFee("" + reorderReturn.getKitchenInfo().getDeliveryCharges());
            } else if (reorderReturn.getKitchenInfo().getIsKitchenDelivery() == 1) {
                testModel.setIsDelivery("1");

                try {
                    String myString = "" + reorderReturn.getKitchenInfo().getUlyxDeliveryFee();
                    String[] aSplit = myString.split("-");
                    ulyxcharge = aSplit[1].trim();
                    testModel.setDeliveryFee("" + aSplit[1].trim());
                } catch (Exception e) {
                    ulyxcharge = "";
                }
            } else {
                testModel.setIsDelivery("0");
                testModel.setDeliveryFee("0");
            }

            basickitcheninfo();


            testModel.setUserName("" + reorderReturn.getCartInfo().getUserName());
            testModel.setOrderDate("" + deliverydetailspref.getString("Deliverydate", ""));
            testModel.setSessionToken("" + basicInformation.getSessionToken());
            testModel.setOrderDishes(testdishesStr);
            testModel.setProfileId("" + profile_id);
            testModel.setTotalAmount("" + subtotal);
            testModel.setUserId("" + basicInformation.getUserId());
            testModel.setCookingInstruction("");

            basicupdate();

            testModel.setDiscount("0");
            testModel.setDeliveryInstruction("");
            testModel.setPromoCode("");
            testModel.setSalesTax("0");
            testModel.setServiceCharges("0");
            testModel.setTipAmount("0");
            testModel.setOrderID("");

            String jsonString = gson.toJson(testModel, TestModel.class).toString();
            System.out.println("addtocart" + jsonString);
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.setRequestSource(RequestSource.addtocart);
            oChangePsswordApi.executePostRequest(API.addtocart(), jsonString);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        hittingapi();

    }

    public void moveorderdetails(String s, Integer isKitchenDelivery) {
        //  handler.removeCallbacksAndMessages(null);
        intent = new Intent(OrderHistory.this, OrderDetails.class);
        intent.putExtra("foodorderid", s);
        intent.putExtra("iskitchend", isKitchenDelivery);
        OrderHistory.this.startActivity(intent);
    }

    public static void firerealtime() {

        System.out.println(">>>>done...");

        Handler refresh = new Handler(Looper.getMainLooper());
        refresh.post(new Runnable() {
            public void run() {
                try {
                    click.performClick();
                } catch (Exception e) {

                }


            }
        });

    }

    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(OrderHistory.this);
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

                hittingapi();
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

    public void basickitcheninfo() {
        BasicKitchenInfo basicKitInfo = new BasicKitchenInfo();
        basicKitInfo.setKitchenProileid(reorderReturn.getKitchenInfo().getProfileId().toString());
        basicKitInfo.setKitchenDelCharge(reorderReturn.getKitchenInfo().getDeliveryCharges().toString());
        basicKitInfo.setKitchenminamount(reorderReturn.getKitchenInfo().getMinOrderAmount());
        basicKitInfo.setKitaddress(reorderReturn.getKitchenInfo().getCompleteAddress());
        basicKitInfo.setFreeDeliveryLimitAmount(reorderReturn.getKitchenInfo().getFreeDeliveryLimitAmount());
        basicKitInfo.setMinamountfordis(reorderReturn.getKitchenInfo().getMinOrderAmount());
        basicKitInfo.setDistance(reorderReturn.getKitchenInfo().getDistance());
        basicKitInfo.setDropOffFee(reorderReturn.getKitchenInfo().getDropOffFee());
        basicKitInfo.setFlatRate(reorderReturn.getKitchenInfo().getFlatRate());
        basicKitInfo.setUlyxDeliveryFee(ulyxcharge.toString().trim());
        basicKitInfo.setVehicleTypeId(reorderReturn.getKitchenInfo().getVehicleTypeId());
        basicKitInfo.setPickUpFee(reorderReturn.getKitchenInfo().getPickUpFee());
        basicKitInfo.setIskitchendelivery(reorderReturn.getKitchenInfo().getIsKitchenDelivery());
        basicKitInfo.setIsdelivery(reorderReturn.getKitchenInfo().getIsDelivery());
        basicKitInfo.setDistance(reorderReturn.getRouteDistance());


        if (reorderReturn.getKitchenInfo().getLatitude() == null || reorderReturn.getKitchenInfo().getLatitude().equals("")) {
            basicKitInfo.setKitchen_lat(18.5515699);
            basicKitInfo.setKitchen_long(73.9031373);
        } else {
            basicKitInfo.setKitchen_lat(Double.parseDouble(reorderReturn.getKitchenInfo().getLatitude()));
            basicKitInfo.setKitchen_long(Double.parseDouble(reorderReturn.getKitchenInfo().getLongitude()));

        }

        basicKitInfo.setKitchendelradius(reorderReturn.getKitchenInfo().getDeliveryRadius());
        basicKitInfo.setIskitchendelivery(reorderReturn.getKitchenInfo().getIsKitchenDelivery());
        basicKitInfo.setIsdelivery(reorderReturn.getKitchenInfo().getIsDelivery());


        String data = gson.toJson(basicKitInfo);
        mTabel.putString("basickitcheninfo", data);
        mTabel.commit();
    }

    public void basicupdate() {
        basicInformation.setUserCityName("" + reorderReturn.getUserCityName());
        basicInformation.setUserLatitude("" + reorderReturn.getUserLatitude());
        basicInformation.setUserLongitude("" + reorderReturn.getUserLongitude());

        mBasicInformation = gson.toJson(basicInformation);
        mTabel.putString("basicinformation", mBasicInformation);
        mTabel.commit();
    }

}

