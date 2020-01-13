package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.BookmarkModel;
import com.saavor.user.Model.DealList;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.Model.KitchenInfoHit;
import com.saavor.user.Model.KitchenInfoReturn;
import com.saavor.user.Model.SignupReturn;
import com.saavor.user.Model.TestDish;
import com.saavor.user.Model.TestModel;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.adapter.DealAroundAdapterAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;
import static com.saavor.user.activity.DashBoard.totalprice;


public class XYZKitchen extends BaseActivity implements View.OnClickListener, OnResultReceived, View.OnTouchListener {

    private ArrayList<DealList> arrayofpager = new ArrayList<>();
    private ImageView back, cart, bookselected, bookunselected, share, call;
    private LinearLayout LLRating, LLBookmark, LL_deal_gone, LL_deliveryshow, deliveryDetailsll, ll_far_kitchen;
    private Button butOrder;
    JSONObject Jsonobject;
    private GestureDetector gestureDetector;
    RecyclerView horizontalrecinfo;
    // TabLayout tabLayout;
    private int position = 0;
    FrameLayout mImageView;
    ViewGroup mRoot;
    private float mXDelta;
    private float mYDelta;
    int lastAction = 0;
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    boolean fromall = false;
    boolean fromdeal = false;
    DateFormat datebeforeFormat;
    private Date CurrentDate, deliverydatee;
    String HitDate;
    boolean closefuture, fromfav;
    String ulyxcharge = "";
    String ulyxchargee = "";
    boolean kitchen_del_radius=false;

    boolean deal = false;
    ScrollView Scrollbar;
    int book = 0;
    private Dialog dialog;
    private ImageView heart1, heart2, heart3, heart4, heart5, kitimage, img_cart;
    private TextView kitchentool, cusine, services, deliverytime, minorder,
            deliveryfee, kitaddress, kitstatus, kittime, review, cartCountTV, end_date, dealdesc;
    LinearLayout LL_singledeal, ll_cant_del_loc;

    //FrameLayout r;
    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onResume() {
        super.onResume();

        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TotalItem <= 0) {

            mImageView.setVisibility(View.GONE);

        } else {
            mImageView.setVisibility(View.VISIBLE);
            cartCountTV.setText("" + TotalItem);
        }

        kitcheninfohit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyzkitchen);

        basicfetch();
        mOnResultReceived = this;
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        //setting delivery pref
        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        datebeforeFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);

        try {
            CurrentDate = datebeforeFormat.parse(date_format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            deliverydatee = datebeforeFormat.parse(deliverydetailspref.getString("Deliverydate", ""));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (deliverydatee.after(CurrentDate)) {

            HitDate = deliverydetailspref.getString("Deliverydate", "");
            closefuture = true;

        }

        if (deliverydatee.before(CurrentDate)) {
            System.out.println("Date1 is before Date2");
            closefuture = false;
            HitDate = date_format;
        }

        if (deliverydatee.equals(CurrentDate)) {
            // previousdaydish.setVisibility(View.INVISIBLE);
            closefuture = false;
            HitDate = date_format;

        }

        ll_cant_del_loc = (LinearLayout) findViewById(R.id.ll_cant_del_loc);
        ll_far_kitchen = (LinearLayout) findViewById(R.id.ll_far_kitchen);
        mImageView = (FrameLayout) findViewById(R.id.content_main_image_view);
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (gestureDetector.onTouchEvent(event)) {

                    addtocart();

                    // single tap
                    return true;
                } else {
                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    int screenHight = displaymetrics.heightPixels;
                    int screenWidth = displaymetrics.widthPixels;

                    float newX, newY;

                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:

                            mXDelta = mImageView.getX() - event.getRawX();
                            mYDelta = mImageView.getY() - event.getRawY();
                            lastAction = MotionEvent.ACTION_DOWN;

                            break;

                        case MotionEvent.ACTION_MOVE:

                            newX = event.getRawX() + mXDelta;
                            newY = event.getRawY() + mYDelta;

                            // check if the view out of screen
                            if ((newX <= 0 || newX >= screenWidth - mImageView.getWidth()) || (newY <= 0 || newY >= screenHight - mImageView.getHeight())) {
                                lastAction = MotionEvent.ACTION_MOVE;
                                break;
                            }

                            mImageView.setX(newX);
                            mImageView.setY(newY);

                            lastAction = MotionEvent.ACTION_MOVE;

                            break;

                        case MotionEvent.ACTION_UP:


                            break;

                        case MotionEvent.ACTION_CANCEL:

                            break;

                        default:
                            return false;
                    }
                }

                return true;
            }
        });


        bookselected = (ImageView) findViewById(R.id.img_bookmark_selected);
        heart1 = (ImageView) findViewById(R.id.img_heart1_k);
        heart2 = (ImageView) findViewById(R.id.img_heart2_k);
        heart3 = (ImageView) findViewById(R.id.img_heart3_k);
        heart4 = (ImageView) findViewById(R.id.img_heart4_k);
        heart5 = (ImageView) findViewById(R.id.img_heart5_k);
        kitimage = (ImageView) findViewById(R.id.img_kitchen_image);
        img_cart = (ImageView) findViewById(R.id.img_cart_xyzkitchen);

        kitchentool = (TextView) findViewById(R.id.txt_kitchen_tool);
        dealdesc = (TextView) findViewById(R.id.txt_dealdesc);
        cusine = (TextView) findViewById(R.id.txt_cusines_kitchen);
        services = (TextView) findViewById(R.id.txt_services);
        review = (TextView) findViewById(R.id.txt_review);
        deliverytime = (TextView) findViewById(R.id.txt_deliverytime);
        minorder = (TextView) findViewById(R.id.txt_min_order);
        deliveryfee = (TextView) findViewById(R.id.txt_delivery_fee);
        kitaddress = (TextView) findViewById(R.id.txt_address_kit);
        kitstatus = (TextView) findViewById(R.id.txt_status);
        kittime = (TextView) findViewById(R.id.txt_kitchen_time);
        cartCountTV = (TextView) findViewById(R.id.cartCountTv);
        end_date = (TextView) findViewById(R.id.txt_end_date);
        deliveryDetailsll = (LinearLayout) findViewById(R.id.deliveryDetailsll);


        bookunselected = (ImageView) findViewById(R.id.img_bookmark_unselected);
        share = (ImageView) findViewById(R.id.img_xyz_share);
        call = (ImageView) findViewById(R.id.img_xyx_call);
        bookselected.setOnClickListener(this);
        bookunselected.setOnClickListener(this);
        share.setOnClickListener(this);
        call.setOnClickListener(this);
        cusine.setOnClickListener(this);

        LL_singledeal = (LinearLayout) findViewById(R.id.ll_singledeal);
        LL_deal_gone = (LinearLayout) findViewById(R.id.ll_deal_gone);
        LL_deliveryshow = (LinearLayout) findViewById(R.id.ll_deliveryinfo);
        // tabLayout= (TabLayout) findViewById(R.id.vp_kitchen_tabdot);

        back = (ImageView) findViewById(R.id.tool_back_xyz);
        // cart = (ImageView) findViewById(R.id.img_cart_xyzkitchen);
        //  frame = (RelativeLayout) findViewById(R.id.frame);
        LLRating = (LinearLayout) findViewById(R.id.ll_rating_xyz);
        LLBookmark = (LinearLayout) findViewById(R.id.ll_book_xyz);

        butOrder = (Button) findViewById(R.id.but_order_xyz);

        back.setOnClickListener(this);
        //   cart.setOnClickListener(this);
        LLRating.setOnClickListener(this);
        LLBookmark.setOnClickListener(this);
        butOrder.setOnClickListener(this);
        LL_singledeal.setOnClickListener(this);

        position = getIntent().getIntExtra("profileid", 0);
        book = getIntent().getIntExtra("decision", 0);
        fromall = getIntent().getBooleanExtra("fromall", false);
        fromdeal = getIntent().getBooleanExtra("fromdeal", false);
        fromfav = getIntent().getBooleanExtra("fromfav", false);

        if (position == 0) {
            // fetching default
            String data = mDatabase.getString("basickitcheninfo", "");
            basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);
            position = Integer.valueOf(basicKitInfo.getKitchenProileid());

        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.tool_back_xyz:


                if (TotalItem <= 0) {
                    storenotcustomizable.clear();
                    totalprice = 0;
                    TotalItem = 0;

                    if (fromdeal) {
                        intent = new Intent(XYZKitchen.this, SeeAllDeal.class);
                        XYZKitchen.this.startActivity(intent);
                    } else if (fromfav) {
                        intent = new Intent(XYZKitchen.this, Favourite.class);
                        XYZKitchen.this.startActivity(intent);
                    } else if (fromall) {
                        intent = new Intent(XYZKitchen.this, SeeAllKitchen.class);
                        XYZKitchen.this.startActivity(intent);

                    } else if (book == 1) {
                        intent = new Intent(XYZKitchen.this, Bookmark.class);
                        XYZKitchen.this.startActivity(intent);
                    } else {
                        intent = new Intent(XYZKitchen.this, DashBoard.class);
                        XYZKitchen.this.startActivity(intent);
                    }

                } else {


                    //dialog intialization
                    dialog = new Dialog(XYZKitchen.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.empty_cart_layout);
                    dialog.setCancelable(true);

                    Button no = (Button) dialog.findViewById(R.id.not_empty);
                    Button yes = (Button) dialog.findViewById(R.id.yes_empty);
                    ImageView cnacel = (ImageView) dialog.findViewById(R.id.empty_clear);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                            storenotcustomizable.clear();
                            totalprice = 0;
                            TotalItem = 0;

                            if (fromall) {
                                finish();

                            } else if (book == 1) {
                                finish();
                            } else {
                                intent = new Intent(XYZKitchen.this, DashBoard.class);
                                XYZKitchen.this.startActivity(intent);
                            }

                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });

                    cnacel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }


                break;

            case R.id.img_xyz_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Use my referral code " + basicInformation.getRefferal().toString() + " to sign up for Saavor." + " " + "Download the App " + "https://play.google.com/store");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.img_xyx_call:

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + kitchenInfoReturn.getKitchenInfo().getMobileNo()));
                XYZKitchen.this.startActivity(callIntent);
                break;

            case R.id.content_main_image_view:


                break;

            case R.id.txt_cusines_kitchen:

                intent = new Intent(getApplicationContext(), Testing.class);
                intent.putExtra("cusinelist", kitchenInfoReturn.getKitchenInfo().getCuisineList());
                startActivity(intent);

                break;


            case R.id.img_bookmark_unselected:

                load_dialog.show();
                bookmarkModel.setSessionToken(basicInformation.getSessionToken().toString());
                bookmarkModel.setUserId(basicInformation.getUserId().toString());
                bookmarkModel.setProfileId("" + kitchenInfoReturn.getKitchenInfo().getProfileId());


                mProgressDialog.setMessage("Add to Bookmark");
                load_dialog.show();

                String jsonString = gson.toJson(bookmarkModel, BookmarkModel.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.executePostRequest(API.addbookmark(), jsonString);
                oChangePsswordApi.setRequestSource(RequestSource.BookmarkAdd);


                break;

            case R.id.img_bookmark_selected:

                mProgressDialog.setMessage("Remove from Bookmark");
                load_dialog.show();
                GetApiClient addbookmark = new GetApiClient(mOnResultReceived);
                addbookmark.executeGetRequest(API.deletebookmark() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + kitchenInfoReturn.getKitchenInfo().getProfileId());
                addbookmark.setRequestSource(RequestSource.Bookmarkdelete);

                break;

            case R.id.ll_rating_xyz:

                intent = new Intent(this, ReviewRating.class);
                this.startActivity(intent);

                break;

            case R.id.ll_singledeal:
                // Create custom dialog object
                dialog = new Dialog(XYZKitchen.this);
                // Include dialog.xml file
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dealdialog);

                // set values for custom dialog components - text, image and button
                TextView title = (TextView) dialog.findViewById(R.id.txt_title);
                TextView desc = (TextView) dialog.findViewById(R.id.txt_desc);
                TextView discount = (TextView) dialog.findViewById(R.id.txt_discount);
                TextView from = (TextView) dialog.findViewById(R.id.txt_from);
                TextView to = (TextView) dialog.findViewById(R.id.txt_to);


                title.setText(kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getDealTitle());
                desc.setText(kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getDescription());


                discount.setText("Discount $" + String.format("%.02f", kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getDealDiscount()) + " on minimum Order Of $" + kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getMinAmount());
                //discount.setText("Discount $" + String.format("%.02f", dealDiscount)+"on minimum Order Of $"+kitchenInfoReturn.getKitchenInfo().getMinOrderAmount());

                String sdate = "" + kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getStartDate();
                SimpleDateFormat spf = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.ENGLISH);
                Date newDate = null;
                try {
                    newDate = spf.parse(sdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                spf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa", Locale.ENGLISH);
                sdate = spf.format(newDate);
                System.out.println(sdate);

                from.setText(sdate);

                String edate = "" + kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getEndDate();
                SimpleDateFormat epf = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.ENGLISH);
                Date enewDate = null;
                try {
                    enewDate = epf.parse(edate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                epf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa", Locale.ENGLISH);
                edate = epf.format(enewDate);
                System.out.println(edate);

                to.setText(edate);

                dialog.show();

                break;

            case R.id.but_order_xyz:

                intent = new Intent(this, Kitchen.class);
                intent.putExtra("kitchentitle", kitchenInfoReturn.getKitchenInfo().getKitchenName().toString());
                this.startActivity(intent);

                break;
        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {
        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(XYZKitchen.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {
            String a = what;


            if (from.toString().equalsIgnoreCase("BookmarkAdd"))

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
                                bookselected.setVisibility(View.VISIBLE);
                                bookunselected.setVisibility(View.GONE);


                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(XYZKitchen.this, message);

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                // Toast.makeText(XYZKitchen.this, "" + message, Toast.LENGTH_LONG).show();
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
                            //  redirect(XYZKitchen.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }


            else if (from.toString().equalsIgnoreCase("Bookmarkdelete")) {
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
                                bookselected.setVisibility(View.GONE);
                                bookunselected.setVisibility(View.VISIBLE);

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(XYZKitchen.this, message);
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                //Toast.makeText(XYZKitchen.this, "" + message, Toast.LENGTH_LONG).show();
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
                            //  redirect(XYZKitchen.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);
            } else if (from.toString().equalsIgnoreCase("kitcheninfo")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    kitchenInfoReturn = gson.fromJson(jsonString, KitchenInfoReturn.class);
                    System.out.println(">>>>" + what);
                    String check = kitchenInfoReturn.getReturnCode();
                    final String message = kitchenInfoReturn.getReturnMessage();

                    try {
                        mtimingslots = gson.toJson(kitchenInfoReturn);
                        mTabel.remove("timingslots");
                        mTabel.commit();
                    } catch (Exception e) {

                    }

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                mtimingslots = gson.toJson(kitchenInfoReturn);
                                mTabel.putString("timingslots", mtimingslots);
                                mTabel.commit();


                                String value2 = kitchenInfoReturn.getKitchenInfo().getKitchenName();
                                String result = upperCaseFirst(value2);
                                result = upperCaseFirst(value2);
                                System.out.println(result);
                                kitchentool.setText(result);

                                basicInformation.setAdt(kitchenInfoReturn.getKitchenInfo().getAvgDeliveryTime());
                                mBasicInformation = gson.toJson(basicInformation);
                                mTabel.putString("basicinformation", mBasicInformation);
                                mTabel.commit();

                                cusine.setText(kitchenInfoReturn.getKitchenInfo().getCuisineList());
                                services.setText(kitchenInfoReturn.getKitchenInfo().getServiceList().toString());
                                deliverytime.setText("" + kitchenInfoReturn.getKitchenInfo().getAvgDeliveryTime() + " mins");
                                minorder.setText("$" + String.format("%.02f", kitchenInfoReturn.getKitchenInfo().getMinOrderAmount()));
                                deliveryfee.setText("$" + String.format("%.02f", kitchenInfoReturn.getKitchenInfo().getDeliveryCharges()));
                                kitaddress.setText(kitchenInfoReturn.getKitchenInfo().getCompleteAddress());


                                if (kitchenInfoReturn.getKitchenInfo().getIsOpen() == 0) {
                                    kitstatus.setTextColor(getResources().getColor(R.color.tooltitle));

                                    if (closefuture) {
                                        kitstatus.setText("Closed");
                                        kittime.setText("");
                                    } else {
                                        kitstatus.setText("Close Now");
                                        kittime.setText("");
                                    }


                                } else {
                                    kitstatus.setTextColor(getResources().getColor(R.color.greenhighlight));

                                    if (closefuture) {
                                        kitstatus.setText("Open");

                                        if (kitchenInfoReturn.getKitchenInfo().getAvailableFrom().equals("")) {
                                            kittime.setText(" 24hrs");
                                        } else {
                                            kittime.setText(" " + kitchenInfoReturn.getKitchenInfo().getAvailableFrom() + " to " + kitchenInfoReturn.getKitchenInfo().getAvailableTo());
                                        }
                                    } else {
                                        kitstatus.setText("Now Open");

                                        if (kitchenInfoReturn.getKitchenInfo().getAvailableFrom().equals("")) {
                                            kittime.setText(" 24hrs");
                                        } else {
                                            kittime.setText(" " + kitchenInfoReturn.getKitchenInfo().getAvailableFrom() + " to " + kitchenInfoReturn.getKitchenInfo().getAvailableTo());
                                        }
                                    }
                                }

                                review.setText(kitchenInfoReturn.getKitchenInfo().getTotalReviews() + " reviews");

                                if (kitchenInfoReturn.getKitchenInfo().getIsBookMark() == 1) {
                                    bookselected.setVisibility(View.VISIBLE);
                                    bookunselected.setVisibility(View.GONE);
                                } else {
                                    bookselected.setVisibility(View.GONE);
                                    bookunselected.setVisibility(View.VISIBLE);
                                }

                                arrayofpager = kitchenInfoReturn.getKitchenInfo().getDealList();

                                if (arrayofpager.isEmpty()) {
                                    LL_deal_gone.setVisibility(View.GONE);
                                    //  LL_nodeal.setVisibility(View.VISIBLE);
                                    mTabel.putString("dis_MinAmount", "0");
                                    mTabel.putString("discount_type", "");
                                    mTabel.putString("DealDiscount", "0");
                                    mTabel.commit();

                                } else {

                                    if (arrayofpager.size() == 1) {
                                        //  LL_nodeal.setVisibility(View.GONE);
                                        LL_deal_gone.setVisibility(View.VISIBLE);
                                        horizontalrecinfo = (RecyclerView) findViewById(R.id.deal_rec_info);
                                        horizontalrecinfo.setVisibility(View.GONE);
                                        LL_singledeal.setVisibility(View.VISIBLE);
                                        deal = true;
                                        dealdesc.setText(kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getDealTitle());

                                        String myString = kitchenInfoReturn.getKitchenInfo().getDealList().get(0).getEndDate();
                                        String title1date;

                                        String[] aSplit = myString.split(" ");

                                        title1date = aSplit[0];

                                        String firstWord = "", restOfString = "", third = "";
                                        String[] aSplitt = title1date.split("-");

                                        firstWord = aSplitt[0];
                                        restOfString = aSplitt[1];
                                        third = aSplitt[2];

                                        System.out.println(firstWord + " " + restOfString + ", " + third);

                                        if (firstWord.equals("01")) {
                                            firstWord = "Jan";
                                        } else if (firstWord.equals("02")) {
                                            firstWord = "Feb";
                                        } else if (firstWord.equals("03")) {
                                            firstWord = "Mar";
                                        } else if (firstWord.equals("04")) {
                                            firstWord = "Apr";
                                        } else if (firstWord.equals("05")) {
                                            firstWord = "May";
                                        } else if (firstWord.equals("06")) {
                                            firstWord = "Jun";
                                        } else if (firstWord.equals("07")) {
                                            firstWord = "Jul";
                                        } else if (firstWord.equals("08")) {
                                            firstWord = "Aug";
                                        } else if (firstWord.equals("09")) {
                                            firstWord = "Sep";
                                        } else if (firstWord.equals("10")) {
                                            firstWord = "Oct";
                                        } else if (firstWord.equals("11")) {
                                            firstWord = "Nov";
                                        } else if (firstWord.equals("12")) {
                                            firstWord = "Dec";
                                        }

                                        end_date.setText(firstWord + " " + restOfString + ", " + third);


                                        mTabel.putString("dis_MinAmount", ""+arrayofpager.get(0).getMinAmount());
                                        mTabel.putString("discount_type", arrayofpager.get(0).getDiscountType());
                                        mTabel.putString("DealDiscount", ""+arrayofpager.get(0).getDealDiscount());
                                        mTabel.commit();


                                    } else {
                                        LL_deal_gone.setVisibility(View.VISIBLE);
                                        LL_singledeal.setVisibility(View.GONE);

                                        deal = true;

                                        horizontalrecinfo = (RecyclerView) findViewById(R.id.deal_rec_info);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(XYZKitchen.this, LinearLayoutManager.HORIZONTAL, true);
                                        layoutManager.setReverseLayout(true);
                                        layoutManager.setStackFromEnd(true);
                                        horizontalrecinfo.setLayoutManager(layoutManager);
                                        horizontalrecinfo.setAdapter(new DealAroundAdapterAdapter(XYZKitchen.this, arrayofpager, 2, deal));
                                    }
                                }

                                int star = kitchenInfoReturn.getKitchenInfo().getStarRating();
                                switch (star) {
                                    case 0: {
                                        heart1.setBackgroundResource(R.drawable.greyheart);
                                        heart2.setBackgroundResource(R.drawable.greyheart);
                                        heart3.setBackgroundResource(R.drawable.greyheart);
                                        heart4.setBackgroundResource(R.drawable.greyheart);
                                        heart5.setBackgroundResource(R.drawable.greyheart);
                                    }
                                    break;

                                    case 1: {
                                        heart1.setBackgroundResource(R.drawable.redreview);
                                        heart2.setBackgroundResource(R.drawable.greyheart);
                                        heart3.setBackgroundResource(R.drawable.greyheart);
                                        heart4.setBackgroundResource(R.drawable.greyheart);
                                        heart5.setBackgroundResource(R.drawable.greyheart);
                                    }
                                    break;

                                    case 2: {
                                        heart1.setBackgroundResource(R.drawable.redreview);
                                        heart2.setBackgroundResource(R.drawable.redreview);
                                        heart3.setBackgroundResource(R.drawable.greyheart);
                                        heart4.setBackgroundResource(R.drawable.greyheart);
                                        heart5.setBackgroundResource(R.drawable.greyheart);
                                    }
                                    break;

                                    case 3: {
                                        heart1.setBackgroundResource(R.drawable.redreview);
                                        heart2.setBackgroundResource(R.drawable.redreview);
                                        heart3.setBackgroundResource(R.drawable.redreview);
                                        heart4.setBackgroundResource(R.drawable.greyheart);
                                        heart5.setBackgroundResource(R.drawable.greyheart);
                                    }
                                    break;

                                    case 4: {
                                        heart1.setBackgroundResource(R.drawable.redreview);
                                        heart2.setBackgroundResource(R.drawable.redreview);
                                        heart3.setBackgroundResource(R.drawable.redreview);
                                        heart4.setBackgroundResource(R.drawable.redreview);
                                        heart5.setBackgroundResource(R.drawable.greyheart);
                                    }
                                    break;

                                    case 5: {
                                        heart1.setBackgroundResource(R.drawable.redreview);
                                        heart2.setBackgroundResource(R.drawable.redreview);
                                        heart3.setBackgroundResource(R.drawable.redreview);
                                        heart4.setBackgroundResource(R.drawable.redreview);
                                        heart5.setBackgroundResource(R.drawable.redreview);
                                    }
                                    break;
                                }


                                if (kitchenInfoReturn.getKitchenInfo().getIsDelivery() == 1) {
                                    LL_deliveryshow.setVisibility(View.VISIBLE);
                                    deliveryDetailsll.setVisibility(View.VISIBLE);
                                    basicKitInfo.setIsdelivery(1);
                                    basicKitInfo.setIskitchendelivery(0);

                                } else {

                                    if (kitchenInfoReturn.getKitchenInfo().getIsKitchenDelivery() == 1) {
                                        LL_deliveryshow.setVisibility(View.VISIBLE);
                                        deliveryDetailsll.setVisibility(View.VISIBLE);
                                        basicKitInfo.setIskitchendelivery(1);
                                        basicKitInfo.setIsdelivery(0);

                                        String myString = "" + kitchenInfoReturn.getKitchenInfo().getUlyxDeliveryFee();

                                        try {
                                            String[] aSplit = myString.split("-");
                                            ulyxcharge = aSplit[1].trim().toString();
                                            ulyxchargee = aSplit[0].trim().toString();
                                        } catch (Exception e) {
                                            ulyxcharge = "";
                                        }

                                        deliveryfee.setText("$" + ulyxchargee + " - " + "$" + ulyxcharge);

                                    } else {
                                        deliveryDetailsll.setVisibility(View.GONE);
                                    }

                                }

                                if (kitchenInfoReturn.getKitchenInfo().getIsOpen() == 1) {


                                    if (kitchenInfoReturn.getKitchenInfo().getIsDelivery() == 1 || kitchenInfoReturn.getKitchenInfo().getIsKitchenDelivery() == 1) {

                                        //> kitchenInfoReturn.getKitchenInfo().getDistance()

                                        boolean show =false;

                                        if (50 < kitchenInfoReturn.getKitchenInfo().getDistance())
                                        {
                                            ll_far_kitchen.setVisibility(View.VISIBLE);
                                            butOrder.setVisibility(View.GONE);

                                        } else
                                            {
                                                show=true;
                                                butOrder.setVisibility(View.VISIBLE);
                                        }

                                        if(show)
                                        {
                                            if (kitchenInfoReturn.getKitchenInfo().getDeliveryRadius()<= kitchenInfoReturn.getKitchenInfo().getDistance())
                                            {
                                                ll_cant_del_loc.setVisibility(View.VISIBLE);
                                                kitchen_del_radius=true;
                                            }
                                        }

                                    } else {
                                        butOrder.setVisibility(View.VISIBLE);
                                    }


                                } else {
                                    butOrder.setVisibility(View.GONE);
                                }

                                basickitcheninfo();


                            }
                        });

                        load_dialog.cancel();

                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                // Toast.makeText(XYZKitchen.this, "" + message, Toast.LENGTH_LONG).show();

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                //  Toast.makeText(XYZKitchen.this, "" + message, Toast.LENGTH_LONG).show();
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
                            //  redirect(XYZKitchen.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);
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

                                basicInformation.setAdt(kitchenInfoReturn.getKitchenInfo().getAvgDeliveryTime());
                                mBasicInformation = gson.toJson(basicInformation);
                                mTabel.putString("basicinformation", mBasicInformation);
                                mTabel.commit();

                                intent = new Intent(XYZKitchen.this, Chart.class);
                                intent.putExtra("kitchentitle", kitchenInfoReturn.getKitchenInfo().getKitchenName());
                                intent.putExtra("directcart", true);
                                intent.putExtra("rdeladdress", deliverydetailspref.getString("Deliveryaddress", ""));
                                intent.putExtra("risdel", "" + kitchenInfoReturn.getKitchenInfo().getIsDelivery());
                                intent.putExtra("rprofileid", "" + kitchenInfoReturn.getKitchenInfo().getProfileId());
                                intent.putExtra("rkitchendelcharge", "" + kitchenInfoReturn.getKitchenInfo().getDeliveryCharges());

                                XYZKitchen.this.startActivity(intent);
                                load_dialog.cancel();
                            }
                        });


                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(XYZKitchen.this, message);
                                load_dialog.cancel();
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(XYZKitchen.this, message);
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
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                        }
                    });
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        // do nothing.

        if (TotalItem <= 0) {
            storenotcustomizable.clear();
            totalprice = 0;
            TotalItem = 0;

            if (fromdeal) {
                intent = new Intent(XYZKitchen.this, SeeAllDeal.class);
                XYZKitchen.this.startActivity(intent);
            } else if (fromfav) {
                intent = new Intent(XYZKitchen.this, Favourite.class);
                XYZKitchen.this.startActivity(intent);
            } else if (fromall) {
                intent = new Intent(XYZKitchen.this, SeeAllKitchen.class);
                XYZKitchen.this.startActivity(intent);

            } else if (book == 1) {
                intent = new Intent(XYZKitchen.this, Bookmark.class);
                XYZKitchen.this.startActivity(intent);
            } else {
                intent = new Intent(XYZKitchen.this, DashBoard.class);
                XYZKitchen.this.startActivity(intent);
            }

        } else {

            //dialog intialization
            dialog = new Dialog(XYZKitchen.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.empty_cart_layout);
            dialog.setCancelable(true);

            Button no = (Button) dialog.findViewById(R.id.not_empty);
            Button yes = (Button) dialog.findViewById(R.id.yes_empty);
            ImageView cnacel = (ImageView) dialog.findViewById(R.id.empty_clear);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                    storenotcustomizable.clear();
                    totalprice = 0;
                    TotalItem = 0;

                    if (fromall) {
                        intent = new Intent(XYZKitchen.this, SeeAllKitchen.class);
                        XYZKitchen.this.startActivity(intent);

                    } else if (book == 1) {
                        intent = new Intent(XYZKitchen.this, Bookmark.class);
                        XYZKitchen.this.startActivity(intent);
                    } else {
                        intent = new Intent(XYZKitchen.this, DashBoard.class);
                        XYZKitchen.this.startActivity(intent);
                    }

                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            cnacel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    public void showpop(String dealTitle, String description, Double dealDiscount, String startDate, String endDate) throws ParseException {

        // Create custom dialog object
        dialog = new Dialog(XYZKitchen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Include dialog.xml file
        dialog.setContentView(R.layout.dealdialog);

        // set values for custom dialog components - text, image and button
        TextView title = (TextView) dialog.findViewById(R.id.txt_title);
        TextView desc = (TextView) dialog.findViewById(R.id.txt_desc);
        TextView discount = (TextView) dialog.findViewById(R.id.txt_discount);
        TextView from = (TextView) dialog.findViewById(R.id.txt_from);
        TextView to = (TextView) dialog.findViewById(R.id.txt_to);

        title.setText(dealTitle);

        desc.setText(description);

        discount.setText("Discount $" + String.format("%.02f", dealDiscount) + " on minimum Order Of $" + String.format("%.02f", kitchenInfoReturn.getKitchenInfo().getMinOrderAmount()));
        //  discount.setText("Discount $"+dealDiscount);

        String sdate = "" + startDate;
        SimpleDateFormat spf = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.ENGLISH);
        Date newDate = spf.parse(sdate);
        spf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa", Locale.ENGLISH);
        sdate = spf.format(newDate);
        System.out.println(sdate);

        from.setText(sdate);

        String edate = "" + startDate;
        SimpleDateFormat epf = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.ENGLISH);
        Date enewDate = epf.parse(edate);
        epf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa", Locale.ENGLISH);
        edate = epf.format(enewDate);
        System.out.println(edate);

        to.setText(edate);

        dialog.show();
    }

    public void kitcheninfohit() {
        kitchenInfoHit.setSessionToken(basicInformation.getSessionToken());
        kitchenInfoHit.setCurrentDate(HitDate);
        kitchenInfoHit.setProfileId("" + position);
        kitchenInfoHit.setUserId(basicInformation.getUserId());
        kitchenInfoHit.setUserLatitude(basicInformation.getUserLatitude());
        kitchenInfoHit.setUserLongitude(basicInformation.getUserLongitude());
        kitchenInfoHit.setUserCityName(basicInformation.getUserCityName());

        if (basicInformation.getUserCityName() == null || basicInformation.getUserCityName().equals("")) {
            //  Toast.makeText(XYZKitchen.this, "city not found ", Toast.LENGTH_LONG).show();
        }

        mProgressDialog.setMessage("loading kitchen info");
        load_dialog.show();

        String jsonString = gson.toJson(kitchenInfoHit, KitchenInfoHit.class).toString();
        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
        oChangePsswordApi.setRequestSource(RequestSource.kitcheninfo);
        oChangePsswordApi.executePostRequest(API.KitchenInfo(), jsonString);
    }

    public static String upperCaseFirst(String value) {
        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    public void basickitcheninfo() {
        basicKitInfo.setKitchenProileid(kitchenInfoReturn.getKitchenInfo().getProfileId().toString());
        basicKitInfo.setKitchenDelCharge(kitchenInfoReturn.getKitchenInfo().getDeliveryCharges().toString());
        basicKitInfo.setKitchenminamount(kitchenInfoReturn.getKitchenInfo().getMinOrderAmount());
        basicKitInfo.setKitaddress(kitchenInfoReturn.getKitchenInfo().getCompleteAddress());
        basicKitInfo.setFreeDeliveryLimitAmount(kitchenInfoReturn.getKitchenInfo().getFreeDeliveryLimitAmount());
        basicKitInfo.setMinamountfordis(kitchenInfoReturn.getKitchenInfo().getMinOrderAmount());
        basicKitInfo.setDistance(kitchenInfoReturn.getKitchenInfo().getDistance());
        basicKitInfo.setDropOffFee(kitchenInfoReturn.getKitchenInfo().getDropOffFee());
        basicKitInfo.setFlatRate(kitchenInfoReturn.getKitchenInfo().getFlatRate());
        basicKitInfo.setUlyxDeliveryFee(ulyxcharge.toString().trim());
        basicKitInfo.setVehicleTypeId(kitchenInfoReturn.getKitchenInfo().getVehicleTypeId());
        basicKitInfo.setPickUpFee(kitchenInfoReturn.getKitchenInfo().getPickUpFee());

        if(kitchenInfoReturn.getKitchenInfo().getLatitude()== null || kitchenInfoReturn.getKitchenInfo().getLatitude().equals(""))
        {
            basicKitInfo.setKitchen_lat(18.5515699);
            basicKitInfo.setKitchen_long(73.9031373);
        }
        else
        {
            basicKitInfo.setKitchen_lat(Double.parseDouble(kitchenInfoReturn.getKitchenInfo().getLatitude()));
            basicKitInfo.setKitchen_long(Double.parseDouble(kitchenInfoReturn.getKitchenInfo().getLongitude()));

        }

        basicKitInfo.setKitchendelradius(kitchenInfoReturn.getKitchenInfo().getDeliveryRadius());


        if(kitchen_del_radius)
        {
            basicKitInfo.setIskitchendelivery(0);
            basicKitInfo.setIsdelivery(0);
        }
        else
        {
            basicKitInfo.setIskitchendelivery(kitchenInfoReturn.getKitchenInfo().getIsKitchenDelivery());
            basicKitInfo.setIsdelivery(kitchenInfoReturn.getKitchenInfo().getIsDelivery());
        }

        String data = gson.toJson(basicKitInfo);
        mTabel.putString("basickitcheninfo", data);
        mTabel.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHight = displaymetrics.heightPixels;
        int screenWidth = displaymetrics.widthPixels;

        float newX, newY;

        boolean touchAndSwipe = true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                mXDelta = mImageView.getX() - event.getRawX();
                mYDelta = mImageView.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;

                break;

            case MotionEvent.ACTION_MOVE:

                newX = event.getRawX() + mXDelta;
                newY = event.getRawY() + mYDelta;

                // check if the view out of screen
                if ((newX <= 0 || newX >= screenWidth - mImageView.getWidth()) || (newY <= 0 || newY >= screenHight - mImageView.getHeight())) {
                    lastAction = MotionEvent.ACTION_MOVE;
                    break;
                }

                mImageView.setX(newX);
                mImageView.setY(newY);

                lastAction = MotionEvent.ACTION_MOVE;

                break;

            case MotionEvent.ACTION_UP:


                if (Math.abs(mXDelta) < 40) {
                    // Swipe
                    addtocart();
                } else {
                    return true;
                }

                break;

            case MotionEvent.ACTION_CANCEL:

                break;

            default:
                return false;
        }
        return true;
    }


    public void addtocart() {
        ArrayList<TestDish> testDishes = new ArrayList<>();
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
            mProgressDialog.setMessage("Loading please wait...");
            load_dialog.show();
            String testdishesStr = gson.toJson(testDishes);
            TestModel testModel = new TestModel();

            DilalogModel dilalogModel = new DilalogModel();
            String dailogdata = mDatabase.getString("dialogdata", "");
            dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

            if (dailogdata.equals(""))
            {
               // testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));
                testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));

            } else {

                if(dilalogModel.getDeladdress() == null || dilalogModel.getDeladdress().equals(""))
                {
                    testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));
                }
                else
                {
                    testModel.setCompleteAddress("" + dilalogModel.getDeladdress());
                }

            }

           // testModel.setCompleteAddress("" + deliverydetailspref.getString("Deliveryaddress", ""));
            testModel.setContactNumber("");
            testModel.setCreateDate("" + date_format);
            testModel.setDeliveryFrom("10:00PM");
            testModel.setDeliveryTo("11:00PM");
            testModel.setIsDelivery("" + kitchenInfoReturn.getKitchenInfo().getIsDelivery());
            testModel.setUserName("" + basicInformation.getFirstName() + " " + basicInformation.getLastName());
            testModel.setOrderDate("" + HitDate);
            testModel.setSessionToken("" + basicInformation.getSessionToken());
            testModel.setOrderDishes(testdishesStr);
            testModel.setProfileId("" + kitchenInfoReturn.getKitchenInfo().getProfileId());
            testModel.setTotalAmount("" + totalprice);
            testModel.setUserId("" + basicInformation.getUserId());
            testModel.setCookingInstruction("");
            testModel.setDeliveryFee("" + kitchenInfoReturn.getKitchenInfo().getDeliveryCharges());
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

    private class SingleTapConfirm implements GestureDetector.OnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(XYZKitchen.this);
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

                if (TotalItem <= 0) {

                    mImageView.setVisibility(View.GONE);

                } else {
                    mImageView.setVisibility(View.VISIBLE);
                    cartCountTV.setText("" + TotalItem);
                }

                kitcheninfohit();

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

}