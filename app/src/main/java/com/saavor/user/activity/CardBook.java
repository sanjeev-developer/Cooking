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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.saavor.user.Model.Bestoffermodel;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.CardList;
import com.saavor.user.Model.Cardlistreturn;
import com.saavor.user.Model.DeleteCardHit;
import com.saavor.user.R;
import com.saavor.user.adapter.CardBookAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;
import com.saavor.user.processor.PostApiClient;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.saavor.user.activity.DashBoard.notcount;

public class CardBook extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private Button mAddCard;
    private ImageView chooseadd, addcard, chooseback;
    RecyclerView cardbookrec;
    LinearLayout cardlist, emptylist, choose_list;
    private Cardlistreturn cardlistreturn = new Cardlistreturn();
    public static Boolean select = false;
    int choose = 0;
    CardBookAdapter cardBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_payment);

        //intializing all elements in ui

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_paymnet);
        setSupportActionBar(toolbar);

        basicfetch();

        mOnResultReceived = this;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_payment);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_payment);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);

        addcard = (ImageView) findViewById(R.id.tool_cardbook_add);
        chooseadd = (ImageView) findViewById(R.id.choose_cardbook_add);
        chooseback = (ImageView) findViewById(R.id.back_cardbook_add);
        mAddCard = (Button) findViewById(R.id.but_add_cardbook);
        cardlist = (LinearLayout) findViewById(R.id.ll_cardlist);
        choose_list = (LinearLayout) findViewById(R.id.ll_choose_card);
        emptylist = (LinearLayout) findViewById(R.id.ll_emptycardlist);
        mAddCard.setOnClickListener(this);
        addcard.setOnClickListener(this);

        //intialization of navigation
        navintial();


        //setting data on navigation drawer(like image and name)
        try
        {
            settingdatanav();

        } catch (Exception e) {

        }

        navpayment.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navpayment.setTextColor(getResources().getColor(R.color.white));

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
        chooseadd.setOnClickListener(this);
        chooseback.setOnClickListener(this);
        txt_nav_BookingHistory.setOnClickListener(this);
        navnotcount.setText(""+notcount);


        try {
            choose = getIntent().getIntExtra("choosecard", 0);
            if (choose == 1) {
                toolbar.setVisibility(View.GONE);
                choose_list.setVisibility(View.VISIBLE);
                select = true;
            }
        } catch (Exception e) {

        }

        cardbookrec = (RecyclerView) findViewById(R.id.recycle_addcardbook);
        cardbookrec.setLayoutManager(new LinearLayoutManager(CardBook.this, LinearLayoutManager.VERTICAL, true));

    }


    @Override
    protected void onResume() {
        super.onResume();
        mProgressDialog.setMessage("Fetching Your Card List");
        load_dialog.show();
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.fetchcardlist);
        oInsertUpdateApi.executeGetRequest(API.listcard() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_add_cardbook:

                intent = new Intent(this, PaymentMethod.class);
                this.startActivity(intent);
                break;

            case R.id.back_cardbook_add:

                finish();
                break;

            case R.id.tool_cardbook_add:

                intent = new Intent(this, PaymentMethod.class);
                this.startActivity(intent);

                break;

            case R.id.choose_cardbook_add:

                intent = new Intent(this, PaymentMethod.class);
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
//
//            case R.id.txt_nav_meal:
//                intent = new Intent(this, FreeMeals.class);
//                this.startActivity(intent);
//                break;

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

                drawer.closeDrawer(GravityCompat.START);
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
                dialog = new Dialog(CardBook.this);
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

    public void finishit() {
        select = false;
        finish();

    }


    @Override
    public void onBackPressed() {
        // do nothing.

        if (select) {
            finish();
        }
    }

    @Override
    public void dispatchString(RequestSource from, final String what) {

      if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(CardBook.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
            if (from.toString().equalsIgnoreCase("fetchcardlist")) {

            cardlistreturn = new Cardlistreturn();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {

                        JSONObject Jsonobject = new JSONObject(what);
                        String jsonString = Jsonobject.toString();


                        gson = new Gson();
                        cardlistreturn = gson.fromJson(jsonString, Cardlistreturn.class);
                        System.out.println(">>>>" + what);
                        String check = cardlistreturn.getReturnCode();
                        final String message = cardlistreturn.getReturnMessage();


                        if (check.equals("1")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    emptylist.setVisibility(View.GONE);
                                    cardlist.setVisibility(View.VISIBLE);
                                    addcard.setVisibility(View.VISIBLE);
                                    chooseadd.setVisibility(View.VISIBLE);
                                    ArrayList<CardList> cardLists = new ArrayList<CardList>();

                                    cardLists = cardlistreturn.getCardList();
                                    cardBookAdapter = new CardBookAdapter(CardBook.this, cardLists);

                                    cardbookrec.setAdapter(cardBookAdapter);
                                    load_dialog.cancel();


                                }
                            });
                        } else if (check.equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    emptylist.setVisibility(View.VISIBLE);
                                    cardlist.setVisibility(View.GONE);
                                    addcard.setVisibility(View.INVISIBLE);
                                    chooseadd.setVisibility(View.INVISIBLE);

                                    load_dialog.cancel();

                                }
                            });

                        } else if (check.equals("-1")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    load_dialog.cancel();
                                    // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();
                                    displayAlert(CardBook.this, "" + message);
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
                                emptylist.setVisibility(View.GONE);
                                cardlist.setVisibility(View.GONE);
                                addcard.setVisibility(View.INVISIBLE);
                                //redirect(CardBook.this, "No internet access. Please turn on cellular data or use wifi.");
                                nointernet();
                            }
                        });
                    }

                }


            });}

            else if(from.toString().equalsIgnoreCase("deletecard"))
            {
                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();


                    gson = new Gson();
                    cardlistreturn = gson.fromJson(jsonString, Cardlistreturn.class);
                    System.out.println(">>>>" + what);
                    String check = cardlistreturn.getReturnCode();
                    final String message = cardlistreturn.getReturnMessage();


                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                mProgressDialog.setMessage("Updating your list...");
                                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                                oInsertUpdateApi.setRequestSource(RequestSource.fetchcardlist);
                                oInsertUpdateApi.executeGetRequest(API.listcard() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());


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
                            nointernet();
                        }
                    });
                }
            }
        }
    }

    // no internet pop-up
    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(CardBook.this);
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

                mProgressDialog.setMessage("Fetching Your Card List");
                load_dialog.show();
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.executeGetRequest(API.listcard() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());

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

    //deleting api hit
    public void hitdelete(final Integer cardId, final String customerId, final String stripeCardId) {

                load_dialog.show();
                mProgressDialog.setMessage("Please wait...");

                DeleteCardHit deleteCardHit = new DeleteCardHit();
                deleteCardHit.setCardId(""+cardId);
                deleteCardHit.setCustomerId(""+customerId);
                deleteCardHit.setSessionToken(basicInformation.getSessionToken());
                deleteCardHit.setUserId(basicInformation.getUserId());
                deleteCardHit.setStripeCardId(""+stripeCardId);


                String jsonString = gson.toJson(deleteCardHit, DeleteCardHit.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.deletecard);
                oChangePsswordApi.executePostRequest(API.deletecard(), jsonString);
    }
}