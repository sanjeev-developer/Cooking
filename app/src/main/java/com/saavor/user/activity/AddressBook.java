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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.AddressList;
import com.saavor.user.Model.AddressListt;
import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.R;
import com.saavor.user.adapter.AddressBookAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saavor.user.processor.PostApiClient;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.saavor.user.activity.DashBoard.Dialogdata;
import static com.saavor.user.activity.DashBoard.notcount;

public class AddressBook extends BaseActivity implements View.OnClickListener, OnResultReceived {

    Button add_address;
    ImageView back_Abook;
    AddressBookAdapter addressBookAdapter;
    RecyclerView addressbook;
    private ArrayList<AddressList> addlist = new ArrayList<AddressList>();
    LinearLayout LL_addlist, LL_splash, LL_fromcart;
    private ImageView addbook_add, back;
    public int LinearSelection=0;
    String deleteadd = "";
    private int dash=0;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean fromcartclick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_add_book);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addbook);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_addbook);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_addbook);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);
        back = (ImageView) findViewById(R.id.back_address_book);
        back.setOnClickListener(this);

        basicfetch();
        mOnResultReceived = this;

        LL_fromcart=(LinearLayout) findViewById(R.id.ll_fromcartclick);
        fromcartclick=getIntent().getBooleanExtra("fromcartclick", false);

        if(fromcartclick)
        {
            LL_fromcart.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);

        }

//        try {
//            int backchoice = 0;
//
//            backchoice = getIntent().getIntExtra("backchoice", 0);
//            dash = getIntent().getIntExtra("backchoice", 0);
//
//
//            if (backchoice == 1) {
//                toolbar.setNavigationIcon(null);
//                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                back.setVisibility(View.VISIBLE);
//                LinearSelection=1;
//
//            } else {
//                back.setVisibility(View.GONE);
//            }
//
//
//        } catch (Exception e) {
//
//        }


        add_address = (Button) findViewById(R.id.but_add_address);
        add_address.setOnClickListener(this);

        LL_addlist = (LinearLayout) findViewById(R.id.ll_addlist);
        LL_splash = (LinearLayout) findViewById(R.id.ll_add_book_splash);

        addbook_add = (ImageView) findViewById(R.id.tool_addbook_add);
        addbook_add.setOnClickListener(this);

        //intialization of navigation
        navintial();

        //setting data on navigation drawer(like image and name)
        try
        {
            settingdatanav();


        }catch (Exception e)
        {

        }

        navaddress.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navaddress.setTextColor(getResources().getColor(R.color.white));
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

            case R.id.but_add_address:

                intent = new Intent(AddressBook.this, AddAddress.class);
                AddressBook.this.startActivity(intent);

                break;


            case R.id.back_address_book:

                finish();
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

            case R.id.tool_addbook_add:
                intent = new Intent(this, AddAddress.class);
                this.startActivity(intent);
                break;


            case R.id.txt_nav_address:

                drawer.closeDrawer(GravityCompat.START);
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
                dialog = new Dialog(AddressBook.this);
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
    protected void onResume() {
        super.onResume();


        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            load_dialog.show();
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
            oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);
            String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
            String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();

        } catch (Exception e) {

        }

    }

    @Override
    public void dispatchString(RequestSource from, String what) {
if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(AddressBook.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

            if (from.toString().equalsIgnoreCase("FetchAdd")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    addressListt = gson.fromJson(jsonString, AddressListt.class);
                    System.out.println(">>>>" + what);
                    String check = addressListt.getReturnCode();
                    final String message = addressListt.getReturnMessage();
                    addlist = addressListt.getAddressList();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                addressbook = (RecyclerView) findViewById(R.id.recycle_addbook);
                                addressBookAdapter = new AddressBookAdapter(AddressBook.this, addlist, LinearSelection);
                                mLayoutManager = new LinearLayoutManager(AddressBook.this);
                                addressbook.setLayoutManager(mLayoutManager);
                                addressbook.setAdapter(addressBookAdapter);


                                addbook_add.setVisibility(View.VISIBLE);
                                LL_addlist.setVisibility(View.VISIBLE);
                                LL_splash.setVisibility(View.GONE);


                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                load_dialog.cancel();
                                addbook_add.setVisibility(View.INVISIBLE);
                                LL_addlist.setVisibility(View.GONE);
                                LL_splash.setVisibility(View.VISIBLE);

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();
                                displayAlert(AddressBook.this, "" + message);
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
//                            redirect(AddressBook.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);
            } else if (from.toString().equalsIgnoreCase("DeleteAdd")) {

                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    addressListt = gson.fromJson(jsonString, AddressListt.class);
                    System.out.println(">>>>" + what);
                    String check = addressListt.getReturnCode();
                    final String message = addressListt.getReturnMessage();
                    addlist = addressListt.getAddressList();


                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    String fetchhomearray = sharedPrefs.getString("AddressHome", null);
                                    Type type = new TypeToken<ArrayList<String>>() {
                                    }.getType();
                                    homeaddress = gson.fromJson(fetchhomearray, type);
                                    homeaddress.remove(deleteadd);
                                    String json = gson.toJson(homeaddress);
                                    editor.putString("AddressHome", json);
                                    editor.commit();

                                } catch (Exception e) {
                                    System.out.println(e);
                                }

                                try {
                                    String fetchhomearray = sharedPrefs.getString("AddressWork", null);
                                    Type type = new TypeToken<ArrayList<String>>() {
                                    }.getType();
                                    workaddress = gson.fromJson(fetchhomearray, type);
                                    workaddress.remove(deleteadd);
                                    String json = gson.toJson(workaddress);
                                    editor.putString("AddressWork", json);
                                    editor.commit();

                                } catch (Exception e) {
                                    System.out.println(e);
                                }


                                Dialogdata.clear();
                                mDatabase = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                mTabel = mDatabase.edit();
                                mTabel.remove("dialogdata");
                                mTabel.commit();


                                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                                oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                                oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);
                                String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
                                String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                load_dialog.cancel();
                                displayAlert(AddressBook.this, message);

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();
                                displayAlert(AddressBook.this, "" + message);
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
                           // redirect(AddressBook.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

       if(fromcartclick)
       {
        finish();
       }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(AddressBook.this);
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
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                    oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);

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

    public void deletediag(final String AddID, final String s)
    {
        //dialog intialization
        dialog = new Dialog(AddressBook.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.cancel_diag);
        dialog.setCancelable(false);

        Button yes = (Button) dialog.findViewById(R.id.yes_cancel);
        Button no = (Button) dialog.findViewById(R.id.not_cancel);
        TextView Title = (TextView) dialog.findViewById(R.id.cancel_title);
        ImageView image = (ImageView) dialog.findViewById(R.id.cancel_image);
        TextView sub_title = (TextView) dialog.findViewById(R.id.text_empty);

        Title.setText("Delete");
        image.setImageResource(R.drawable.delete_add);

        sub_title.setText("Are you sure you want to remove this address?");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                load_dialog.show();

                deleteadd=s;

                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.executeGetRequest(API.deleteAddress() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken()+ "/" +AddID);
                oInsertUpdateApi.setRequestSource(RequestSource.DeleteAdd);
                String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken()+ "/" +AddID;
                String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken()+ "/" +AddID;

                System.out.println(">>>>>>>"+fullapi);
                System.out.println(">>>>>>>"+fullapii);

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

}