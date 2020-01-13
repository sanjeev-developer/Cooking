package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.saavor.user.Classes.GPSTracker;
import com.saavor.user.Classes.GeocodingLocation;
import com.saavor.user.Model.AddressList;
import com.saavor.user.Model.AddressListt;
import com.saavor.user.Model.BestOfferNodelReturn;
import com.saavor.user.Model.Bestoffermodel;
import com.saavor.user.Model.ChefList;
import com.saavor.user.Model.ChefSearch;
import com.saavor.user.Model.ChefSearchList;
import com.saavor.user.Model.CountryList;
import com.saavor.user.Model.DashApiHit;
import com.saavor.user.Model.DashitReturn;
import com.saavor.user.Model.DealList;
import com.saavor.user.Model.DeliveryTypeDetails;
import com.saavor.user.Model.DialogData;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.Model.GetChefWithRating;
import com.saavor.user.Model.KitchenList;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.Model.KitchenSearchReturn;
import com.saavor.user.Model.KitchensDealList;
import com.saavor.user.Model.LoginDataReturn;
import com.saavor.user.Model.NonCustomizableList;
import com.saavor.user.Model.NotificationList;
import com.saavor.user.Model.NotificationResponse;
import com.saavor.user.R;
import com.saavor.user.Utils.Utils;
import com.saavor.user.adapter.AddlistAdapter;
import com.saavor.user.adapter.DashRecycleAdapter;
import com.saavor.user.adapter.DealAroundAdapterAdapter;
import com.saavor.user.adapter.OfferDashAdapter;
import com.saavor.user.adapter.RecommendedForYouAdapter;
import com.saavor.user.adapter.TopChefsAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.chefserver.ChefFiltersActivity;
import com.saavor.user.chefserver.TopChefsActivity;
import com.saavor.user.chefserver.adapter.ChefOffersAdapter;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.saavor.user.activity.Cusine.cusinenames;


public class DashBoard extends BaseActivity implements LocationListener, AdapterView.OnItemSelectedListener, View.OnClickListener, OnResultReceived, AdapterView.OnItemClickListener, GoogleApiClient.OnConnectionFailedListener {

    public ArrayList<DealList> arrayofpager = new ArrayList<>();
    private ArrayList<AddressList> addlist = new ArrayList<AddressList>();
    private ArrayList<KitchenList> kitchensearcharray = new ArrayList<KitchenList>();
    private ArrayList<KitchensDealList> bestofferarray = new ArrayList<>();
    private RecyclerView recyclerView_Dash, horizontalrec;
    private Dialog dialog;
    private Dialog dash_dialog;
    private DashRecycleAdapter dashRecycleAdapter;
    private DealAroundAdapterAdapter dealAroundAdapterAdapter;
    private RecyclerView recycleOffer, recaddlist;
    private OfferDashAdapter offerDashAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText search;
    private ImageView toolfilter, currentloc, clear_pop;
    private ImageView spinneropen, dialogsubmit;
    private AutoCompleteTextView mAct_Location;
    private Button delivery, pickup;
    private LocationManager locationManager;
    private Location location;
    private TextView typing, sellall_offer, seeall_kitchen, dash_loc, on_kitchen, on_deals, loadingaddress, addlist_dialg, dashsearchgone, search_cancel;
    public double latitude;
    GPSTracker gpsTracker;
    KitchenSearch kitchenSearch;
    public double longitude;
    private LinearLayout LL_dashtotal, LL_dealsAY, LL_best_offer, LL_topkitchen, LL_dialog, ll_child;
    private View view;
    DateFormat datebeforeFormat;
    LinearLayout sc_kitchen, LL_resultfound;
    private Date CurrentDate, deliverydatee;
    public static String localityuser = "";
  //  public static String deliveryinstruct = "";
    ArrayList<NotificationList> notificationdata = new ArrayList<>();
    private Boolean b_topkitchen = true;
    private Boolean b_bestoffer = true;
    private Boolean b_seeall_offer = true;
    private Boolean b_seeall_kitchen = true;
    private Boolean onetimehit = false;
    private Boolean kitchensearch = true;
    private Boolean dailog_d_p = true;
    public static CountryList countryList;
    public static Boolean deliverytypestatus = false;
    public static TextView cusine_type;
    public static String finaladdress = "";

    private GoogleApiClient mGoogleApiClient;
    // flag for GPS Status
    boolean isGPSEnabled = false;
    private static String adresschoose = "";
    public AddlistAdapter addlistAdapter;
    private SearchView searchView;
    // flag for network status
    boolean isNetworkEnabled = false;
    boolean deal = false;
    public static ArrayList<DialogData> Dialogdata = new ArrayList<DialogData>();
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    public static boolean soicialintegration = false;
    public static ArrayList<NonCustomizableList> storenotcustomizable = new ArrayList<NonCustomizableList>();
    public static int TotalItem = 0;
    public static double totalprice = 0;
    public static int notcount = 0;
    public static ArrayList<DeliveryTypeDetails> coustmerdetails = new ArrayList<DeliveryTypeDetails>();

    TextView txtSeeAllTopChef, txtTopChef;
    private Button btnKitchen;
    private Button btnChef;
    private Button btnServer;
    private Button btnBartenders;

    private LinearLayout llKitchen;
    private LinearLayout llChef;

    RecommendedForYouAdapter recommendedAdapter;
    TopChefsAdapter topChefAdapter;

    private RecyclerView rv_chef_recommended;
    private RecyclerView rv_top_chefs;
    private RecyclerView rv_best_offer_chef;

    boolean IsChef;
    boolean CSB_filter_status = false;

    Context mContext;
    OnResultReceived mOnResultReceived;
    Gson oGson;

    String ServerResult;

    ArrayList<ChefList> aListChefList = new ArrayList<ChefList>();
    ChefOffersAdapter ChefoffersAdapter;
    ArrayList<ChefSearchList> aListChefSearchList;

    LinearLayout ll_topchef;
    LinearLayout ll_recommended_for_you;
    private boolean IsChefDataAvail;
    LinearLayout ll_best_deal_chef;
    boolean filter_status = false;
    public static String UserType = "Chef";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naview_dash);

        storenotcustomizable.clear();
        totalprice = 0;
        TotalItem = 0;
        finaladdress = "";

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            // showGPSDisabledAlertToUser();
        }
        //setting delivery pref
        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        MultiDex.install(this);

        Dialogdata.clear();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(DashBoard.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);


        //fetching user basic infromation
        basicfetch();

        //dialog intialization
        dialog = new Dialog(DashBoard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_gps_layout);
        dialog.setCancelable(false);



        //dialog intialization
        dash_dialog = new Dialog(DashBoard.this);
        dash_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dash_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dash_dialog.setContentView(R.layout.no_internet);
        dash_dialog.setCancelable(false);

        Button settings = (Button) dash_dialog.findViewById(R.id.not_settings);
        Button retry = (Button) dash_dialog.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dash_dialog.cancel();
                hittingintial();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dash_dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });


        //dialog intialization
//        dialog = new Dialog(DashBoard.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.no_internet);
//        dialog.setCancelable(false);

        // delievryfetch();

        //saving session
        mTabel.putBoolean("session", true);
        mTabel.commit();

        txtSeeAllTopChef = (TextView) findViewById(R.id.txtSeeAllTopChef);
        txtSeeAllTopChef.setOnClickListener(this);

        txtTopChef = (TextView) findViewById(R.id.txtTopChef);

        ll_child = (LinearLayout) findViewById(R.id.ll_child);
        search_cancel = (TextView) findViewById(R.id.txt_cancel);
        LL_resultfound = (LinearLayout) findViewById(R.id.ll_resultfound);
        sc_kitchen = (LinearLayout) findViewById(R.id.sc_no_kitchen);
        dashsearchgone = (TextView) findViewById(R.id.txt_dashsearchgone);
        LL_dealsAY = (LinearLayout) findViewById(R.id.ll_dealsAY);
        LL_best_offer = (LinearLayout) findViewById(R.id.ll_best_offer);
        LL_topkitchen = (LinearLayout) findViewById(R.id.ll_topkitchen);
        LL_dialog = (LinearLayout) findViewById(R.id.ll_dialog);
        typing = (TextView) findViewById(R.id.txt_typing);
        on_kitchen = (TextView) findViewById(R.id.txt_onkitchen);
        sellall_offer = (TextView) findViewById(R.id.txt_seeall_offer);
        seeall_kitchen = (TextView) findViewById(R.id.txt_seeall_kitchen);
        seeall_kitchen.setOnClickListener(this);
        dash_loc = (TextView) findViewById(R.id.txt_dash_loc);
        cusine_type = (TextView) findViewById(R.id.txt_cusine_type);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        sellall_offer.setOnClickListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_dashboard);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dashboard);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };

        drawer.setDrawerListener(toggle);

        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_navicon);
        mOnResultReceived = this;

        recyclerView_Dash = (RecyclerView) findViewById(R.id.recycle_dash);
        recyclerView_Dash.setNestedScrollingEnabled(false);

        toolfilter = (ImageView) findViewById(R.id.img_filter_dash);
        spinneropen = (ImageView) findViewById(R.id.img_spinneropen);

        toolfilter.setOnClickListener(this);
        spinneropen.setOnClickListener(this);
        LL_dialog.setOnClickListener(this);
        dashsearchgone.setOnClickListener(this);
        search_cancel.setOnClickListener(this);

        //intialization of navigation
        navintial();

        navhome.setBackgroundColor(getResources().getColor(R.color.nextbutcolor));
        navhome.setTextColor(getResources().getColor(R.color.white));


        navusername.setOnClickListener(this);
        navhome.setOnClickListener(this);
        navbook.setOnClickListener(this);
        navfavdish.setOnClickListener(this);
        navorder.setOnClickListener(this);
        navrefferal.setOnClickListener(this);
        //navfreemeal.setOnClickListener(this);
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

        search = (EditText) findViewById(R.id.edt_search_dash);

        try {
            settingdatanav();

        } catch (Exception e) {
            e.printStackTrace();
        }

        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            try {
                                hideSoftKeyboard(DashBoard.this);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                          //  dashsearchgone.setVisibility(View.VISIBLE);
                            if (IsChef) {
                               load_dialog.show();
                                IsChefDataAvail = false;
                                //Get ChefSearch.......
                                DilalogModel dilalogModel = new DilalogModel();
                                String dailogdata = mDatabase.getString("dialogdata", "");
                                dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);
                                ChefSearch oChefSearch = new ChefSearch();
                                SharedPreferences shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
                                if (!shared.getString("SortBy", "").equalsIgnoreCase("")) {
                                    oChefSearch.setSessionToken(basicInformation.getSessionToken());
                                    oChefSearch.setUserId(basicInformation.getUserId());
                                    oChefSearch.setBookingDate(shared.getString("BookingDateTime", "")); //
                                    oChefSearch.setCuisineList(shared.getString("Cuisine", "").replace(" ", ""));
                                    if (UserType.equalsIgnoreCase("Chef")) {
                                        oChefSearch.setCuisineList(shared.getString("Cuisine", "").replace(" ", ""));
                                    } else {
                                        oChefSearch.setCuisineList("");
                                    }
                                    oChefSearch.setCurrentDate(date_format);
                                    oChefSearch.setDistance("0");
                                    oChefSearch.setEndIndex("100");
                                    oChefSearch.setStartIndex("0");
                                    oChefSearch.setLatitude(String.valueOf(dilalogModel.getLat()));
                                    oChefSearch.setLongitude(String.valueOf(dilalogModel.getLongi()));
                                    oChefSearch.setGender(shared.getString("QuickFilter", ""));
                                    oChefSearch.setRediusFrom(shared.getString("MinRadius", ""));
                                    oChefSearch.setRediusTo(shared.getString("MaxRadius", ""));
                                    oChefSearch.setSearchText(search.getText().toString().trim()); //
                                   // oChefSearch.setServiceType("");
                                    if (UserType.equalsIgnoreCase("Chef")) {
                                        oChefSearch.setServiceType(shared.getString("Service", ""));
                                    } else {
                                        oChefSearch.setServiceType("");
                                    }
                                    oChefSearch.setSortBy(shared.getString("SortBy", ""));
                                    oChefSearch.setUserType(UserType);
                                } else {
                                    oChefSearch.setSessionToken(basicInformation.getSessionToken());
                                    oChefSearch.setUserId(basicInformation.getUserId());
                                    oChefSearch.setBookingDate(DateFormat.getDateTimeInstance().format(new Date()));
                                    oChefSearch.setCuisineList("");
                                    oChefSearch.setCurrentDate(date_format);
                                    oChefSearch.setDistance("0");
                                    oChefSearch.setEndIndex("100");
                                    oChefSearch.setStartIndex("0");
                                    oChefSearch.setLatitude(String.valueOf(dilalogModel.getLat()));
                                    oChefSearch.setLongitude(String.valueOf(dilalogModel.getLongi()));
                                    oChefSearch.setGender("Any Gender");
                                    oChefSearch.setRediusFrom("0");
                                    oChefSearch.setRediusTo("60");
                                    oChefSearch.setSearchText(search.getText().toString().trim());
                                    oChefSearch.setServiceType("");
                                    oChefSearch.setSortBy("Distance");
                                    oChefSearch.setUserType(UserType);
                                }
                                String jsonString = oGson.toJson(oChefSearch, ChefSearch.class).toString();
                                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                                oInsertUpdateApi.setRequestSource(RequestSource.ChefSearch);
                                oInsertUpdateApi.executePostRequest(API.fChefSearch(), jsonString);
                            } else {
                                commonhit();
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        KeyboardVisibilityEvent.setEventListener(DashBoard.this, new

                KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {

                        System.out.println(">>>" + isOpen);

                        if (isOpen) {
                            LL_dealsAY.setVisibility(View.GONE);
                            LL_best_offer.setVisibility(View.GONE);
                            kitchensearch = false;
                        } else {
                            kitchensearch = true;
                            if (b_topkitchen) {

                                if (arrayofpager.size() > 1) {
                                    LL_dealsAY.setVisibility(View.VISIBLE);

                                } else {
                                    LL_dealsAY.setVisibility(View.GONE);
                                }
                            }
                            if (b_bestoffer) {
                                LL_best_offer.setVisibility(View.VISIBLE);

                            }
                        }
                    }
                });

        recycleOffer = (RecyclerView) findViewById(R.id.recycle_offer);
        recycleOffer.setNestedScrollingEnabled(false);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (search.getText().toString().equals("")) {
                    dashsearchgone.setVisibility(View.VISIBLE);

                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                    commonhit();
                }
            }
        });


        try

        {

            int fire = getIntent().getIntExtra("onetime", 0);

            if (fire == 1) {
                mTabel.remove("dialogdata");
                mTabel.commit();

                onetimehit = true;
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }


        InitializeInterface();

    }


    private void InitializeInterface() {
        btnKitchen = (Button) findViewById(R.id.btnKitchen);
        btnKitchen.setOnClickListener(this);

        btnChef = (Button) findViewById(R.id.btnChef);
        btnChef.setOnClickListener(this);

        btnServer = (Button) findViewById(R.id.btnServer);
        btnServer.setOnClickListener(this);

        btnBartenders = (Button) findViewById(R.id.btnBartenders);
        btnBartenders.setOnClickListener(this);

        llKitchen = (LinearLayout) findViewById(R.id.llKitchen);
        llChef = (LinearLayout) findViewById(R.id.llChef);

        ll_topchef = (LinearLayout) findViewById(R.id.ll_topchef);
        ll_recommended_for_you = (LinearLayout) findViewById(R.id.ll_recommended_for_you);
        ll_best_deal_chef = (LinearLayout) findViewById(R.id.ll_best_deal_chef);

        rv_chef_recommended = (RecyclerView) findViewById(R.id.rv_chef_recommended);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DashBoard.this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rv_chef_recommended.setLayoutManager(layoutManager);

        rv_top_chefs = (RecyclerView) findViewById(R.id.rv_top_chefs);
        rv_top_chefs.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(DashBoard.this);
        rv_top_chefs.setLayoutManager(mLayoutManager);
        rv_top_chefs.setItemAnimator(new DefaultItemAnimator());

        rv_best_offer_chef = (RecyclerView) findViewById(R.id.rv_best_offer_chef);
        rv_best_offer_chef.setNestedScrollingEnabled(false);

        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.txtSeeAllTopChef:
                Intent intent = new Intent(DashBoard.this, TopChefsActivity.class);
                intent.putExtra("UserType", UserType);
                startActivity(intent);
                break;


            case R.id.btnChef:
                dailog_d_p=false;
                CheckFiltersValues(false);
                txtTopChef.setText("Top Chef's near you");
                UserType = "Chef";
                dashsearchgone.setText("Type to search for a chef");
                search.setText("");
                sc_kitchen.setVisibility(View.GONE);
                IsChef = true;
                llKitchen.setVisibility(View.GONE);
                llChef.setVisibility(View.VISIBLE);
                try {
                    llChef.addView(ll_child);
                } catch (Exception e) {

                }

                load_dialog.show();
                btnKitchen.setBackgroundResource(R.drawable.filterback);
                btnKitchen.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnChef.setBackgroundResource(R.drawable.filterpressed);
                btnChef.setTextColor(ContextCompat.getColor(this, R.color.white));
                btnServer.setBackgroundResource(R.drawable.filterback);
                btnServer.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnBartenders.setBackgroundResource(R.drawable.filterback);
                btnBartenders.setTextColor(ContextCompat.getColor(this, R.color.black));
                GetChefWithRating oGetChefWithRating = new GetChefWithRating();
                oGetChefWithRating.setSessionToken(basicInformation.getSessionToken());
                oGetChefWithRating.setUserId(basicInformation.getUserId());
                oGetChefWithRating.setDistance("10");
                oGetChefWithRating.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                oGetChefWithRating.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                oGetChefWithRating.setCurrentDate(date_format);
                oGetChefWithRating.setUserType(UserType);
                String jsonString = oGson.toJson(oGetChefWithRating, GetChefWithRating.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetChefWithRating);
                oInsertUpdateApi.executePostRequest(API.fGetChefWithRating(), jsonString);
                break;


            case R.id.btnServer:
                dailog_d_p=false;
                CheckFiltersValues(false);
                txtTopChef.setText("Top Server's near you");
                UserType = "Server";
                dashsearchgone.setText("Type to search for a server");
                search.setText("");
                sc_kitchen.setVisibility(View.GONE);
                IsChef = true;
                llKitchen.setVisibility(View.GONE);
                llChef.setVisibility(View.VISIBLE);
                try {
                    llChef.addView(ll_child);
                } catch (Exception e) {

                }

               load_dialog.show();
                btnKitchen.setBackgroundResource(R.drawable.filterback);
                btnKitchen.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnChef.setBackgroundResource(R.drawable.filterback);
                btnChef.setTextColor(ContextCompat.getColor(this, R.color.black));

                btnServer.setBackgroundResource(R.drawable.filterpressed);
                btnServer.setTextColor(ContextCompat.getColor(this, R.color.white));

                btnBartenders.setBackgroundResource(R.drawable.filterback);
                btnBartenders.setTextColor(ContextCompat.getColor(this, R.color.black));
                GetChefWithRating oGetChefWithRating2 = new GetChefWithRating();
                oGetChefWithRating2.setSessionToken(basicInformation.getSessionToken());
                oGetChefWithRating2.setUserId(basicInformation.getUserId());
                oGetChefWithRating2.setDistance("10");
                oGetChefWithRating2.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                oGetChefWithRating2.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                oGetChefWithRating2.setCurrentDate(date_format);
                oGetChefWithRating2.setUserType(UserType);
                String jsonString2 = oGson.toJson(oGetChefWithRating2, GetChefWithRating.class).toString();
                PostApiClient oInsertUpdateApi2 = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi2.setRequestSource(RequestSource.GetChefWithRating);
                oInsertUpdateApi2.executePostRequest(API.fGetChefWithRating(), jsonString2);
                break;


            case R.id.btnBartenders:
                dailog_d_p=false;
                CheckFiltersValues(false);
                txtTopChef.setText("Top Bartender's near you");
                UserType = "Bartender";
                dashsearchgone.setText("Type to search for a bartenders");
                search.setText("");

                sc_kitchen.setVisibility(View.GONE);
                IsChef = true;
                llKitchen.setVisibility(View.GONE);
                llChef.setVisibility(View.VISIBLE);

                try {
                    llChef.addView(ll_child);
                } catch (Exception e) {

                }
               load_dialog.show();
                btnKitchen.setBackgroundResource(R.drawable.filterback);
                btnKitchen.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnChef.setBackgroundResource(R.drawable.filterback);
                btnChef.setTextColor(ContextCompat.getColor(this, R.color.black));

                btnServer.setBackgroundResource(R.drawable.filterback);
                btnServer.setTextColor(ContextCompat.getColor(this, R.color.black));

                btnBartenders.setBackgroundResource(R.drawable.filterpressed);
                btnBartenders.setTextColor(ContextCompat.getColor(this, R.color.white));

                GetChefWithRating oGetChefWithRating3 = new GetChefWithRating();
                oGetChefWithRating3.setSessionToken(basicInformation.getSessionToken());
                oGetChefWithRating3.setUserId(basicInformation.getUserId());
                oGetChefWithRating3.setDistance("10");
                oGetChefWithRating3.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                oGetChefWithRating3.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                oGetChefWithRating3.setCurrentDate(date_format);
                oGetChefWithRating3.setUserType(UserType);
                String jsonString3 = oGson.toJson(oGetChefWithRating3, GetChefWithRating.class).toString();
                PostApiClient oInsertUpdateApi3 = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi3.setRequestSource(RequestSource.GetChefWithRating);
                oInsertUpdateApi3.executePostRequest(API.fGetChefWithRating(), jsonString3);
                break;


            case R.id.btnKitchen:
                dailog_d_p=true;
                CheckFiltersValues(true);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                search.setText("");
                dashsearchgone.setText("Type to search for a food, dish or a cuisine");
                sc_kitchen.setVisibility(View.GONE);
                IsChef = false;
                llKitchen.setVisibility(View.VISIBLE);
                llChef.setVisibility(View.GONE);
                llChef.removeView(ll_child);
                btnKitchen.setBackgroundResource(R.drawable.filterpressed);
                btnKitchen.setTextColor(ContextCompat.getColor(this, R.color.white));

                btnChef.setBackgroundResource(R.drawable.filterback);
                btnChef.setTextColor(ContextCompat.getColor(this, R.color.black));

                btnServer.setBackgroundResource(R.drawable.filterback);
                btnServer.setTextColor(ContextCompat.getColor(this, R.color.black));

                btnBartenders.setBackgroundResource(R.drawable.filterback);
                btnBartenders.setTextColor(ContextCompat.getColor(this, R.color.black));

                commonhit();
                break;


            case R.id.ll_dialog:

                try {

                    showpopup();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.txt_nav_username:
                intent = new Intent(this, UserInfo.class);
                this.startActivity(intent);

                break;

            case R.id.txt_dashsearchgone:
                dashsearchgone.setVisibility(View.GONE);

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.txt_seeall_offer:
                intent = new Intent(this, SeeAllDeal.class);
                this.startActivity(intent);

                break;
            case R.id.txt_nav_home:

                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.txt_nav_bookmark:
                intent = new Intent(this, Bookmark.class);
                this.startActivity(intent);

                break;

            case R.id.txt_nav_dishes:
                intent = new Intent(this, Favourite.class);
                this.startActivity(intent);

                break;

            case R.id.txt_cancel:

                search.setText("");
                dashsearchgone.setVisibility(View.VISIBLE);
                if (IsChef) {

                   load_dialog.show();
                    GetChefWithRating oGetChefWithRating4 = new GetChefWithRating();
                    oGetChefWithRating4.setSessionToken(basicInformation.getSessionToken());
                    oGetChefWithRating4.setUserId(basicInformation.getUserId());
                    oGetChefWithRating4.setDistance("10");
                    oGetChefWithRating4.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                    oGetChefWithRating4.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                    oGetChefWithRating4.setCurrentDate(date_format);
                    oGetChefWithRating4.setUserType(UserType);
                    String jsonString4 = oGson.toJson(oGetChefWithRating4, GetChefWithRating.class).toString();
                    PostApiClient oInsertUpdateApi4 = new PostApiClient(mOnResultReceived);
                    oInsertUpdateApi4.setRequestSource(RequestSource.GetChefWithRating);
                    oInsertUpdateApi4.executePostRequest(API.fGetChefWithRating(), jsonString4);
                } else {
                    commonhit();
                }
                try {
                    hideSoftKeyboard(DashBoard.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                dialog = new Dialog(DashBoard.this);
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

                if (IsChef) {
                    intent = new Intent(this, ChefFiltersActivity.class);
                    intent.putExtra("UserType", UserType);
                    this.startActivity(intent);
                } else {
                    intent = new Intent(this, Filter.class);
                    this.startActivity(intent);
                }
                break;

            case R.id.txt_seeall_kitchen:
                intent = new Intent(this, SeeAllKitchen.class);
                this.startActivity(intent);

                break;
        }
    }

    private void logOut() {

        SharedPreferences settings = this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        mTabel.putBoolean("session", false);
        mTabel.commit();

        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });


        intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        search.setText("");

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (!IsChef) {
                CheckFiltersValues(true);
                //  Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                if (onetimehit) {
                    deliveryeditor.putString("Deliverydate", date_format);
                    deliveryeditor.commit();
                    deliverytypestatus = true;
                    hittingintial();
                } else {
                    commonhit();
                }
            } else {
                llKitchen.setVisibility(View.GONE);
                sc_kitchen.setVisibility(View.GONE);
                CheckFiltersValues(false);


                load_dialog.show();
                GetChefWithRating oGetChefWithRating = new GetChefWithRating();
                oGetChefWithRating.setSessionToken(basicInformation.getSessionToken());
                oGetChefWithRating.setUserId(basicInformation.getUserId());
                oGetChefWithRating.setDistance("10");
                oGetChefWithRating.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                oGetChefWithRating.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                oGetChefWithRating.setCurrentDate(date_format);
                oGetChefWithRating.setUserType(UserType);
                String jsonString = oGson.toJson(oGetChefWithRating, GetChefWithRating.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.GetChefWithRating);
                oInsertUpdateApi.executePostRequest(API.fGetChefWithRating(), jsonString);
            }
        } else {
            showGPSDisabledAlertToUser();
        }

    }

    @Override
    public void dispatchString(final RequestSource from, final String what) {


        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                     load_dialog.cancel();
                    displayAlert(DashBoard.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (from.toString().equalsIgnoreCase("DashboardDeals")) {

                        try {
                            JSONObject Jsonobject = new JSONObject(what);
                            String jsonString = Jsonobject.toString();

                            gson = new Gson();
                            dashitReturn = gson.fromJson(jsonString, DashitReturn.class);
                            System.out.println(">>>>DashboardDeals response" + what);
                            String check = dashitReturn.getReturnCode();

                            // final String message = signupReturn.getReturnMessage();

                            if (check.equals("1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        b_topkitchen = true;
                                        LL_dealsAY.setVisibility(View.VISIBLE);
                                         load_dialog.cancel();
                                        arrayofpager = dashitReturn.getDealList();

                                        //TabLayout tabLayout = (TabLayout) findViewById(R.id.vp_dash_tabdot);
                                        horizontalrec = (RecyclerView) findViewById(R.id.deal_rec);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(DashBoard.this, LinearLayoutManager.HORIZONTAL, true);
                                        layoutManager.setReverseLayout(true);
                                        layoutManager.setStackFromEnd(true);
                                        horizontalrec.setLayoutManager(layoutManager);
                                        dealAroundAdapterAdapter = new DealAroundAdapterAdapter(DashBoard.this, arrayofpager, 1, deal);
                                        horizontalrec.setAdapter(dealAroundAdapterAdapter);

                                        notificationcount();
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        b_topkitchen = false;
                                         load_dialog.cancel();
                                        // displayAlert(DashBoard.this,"im here"+ message);
                                        LL_dealsAY.setVisibility(View.GONE);
                                        notificationcount();
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

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });


                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }

                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     load_dialog.cancel();
                                    // redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                   // nointernet();
                                    dash_dialog.show();
                                }
                            });
                        }

                        Log.e("response", "" + what);
                    } else if (from.toString().equalsIgnoreCase("UserInfo")) {
                        try {

                            JSONObject Jsonobject = new JSONObject(what);
                            String jsonString = Jsonobject.toString();

                            gson = new Gson();
                            loginDataReturn = gson.fromJson(jsonString, LoginDataReturn.class);
                            String check = loginDataReturn.getReturnCode();

                            if (check.equals("1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (loginDataReturn.getUserInfo().getFacebookId().toString().equals("") && loginDataReturn.getUserInfo().getGoogleId().toString().equals("")) {
                                            soicialintegration = false;
                                        } else {
                                            soicialintegration = true;
                                        }

                                        //saving user info to database
                                        savingbasic();

                                        // setting into navigation
                                        try {
                                            settingdatanav();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                                        oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                                        oInsertUpdateApi.setRequestSource(RequestSource.firstdashitadd);
                                        String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
                                        String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }

                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //dash_dialog.cancel();
                                    }
                                });
                            } else if (check.equals("-1")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       // dash_dialog.cancel();
                                    }
                                });

                            } else if (check.equals("5")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });


                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }

                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     load_dialog.cancel();
                                    //redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                   // nointernet();
                                    dash_dialog.show();
                                }
                            });
                        }
                    } else if (from.toString().equalsIgnoreCase("bestoffer")) {
                        try {

                            JSONObject Jsonobject = new JSONObject(what);
                            String jsonString = Jsonobject.toString();

                            gson = new Gson();
                            bestOfferNodelReturn = gson.fromJson(jsonString, BestOfferNodelReturn.class);
                            String check = bestOfferNodelReturn.getReturnCode();

                            if (check.equals("1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();
                                        b_bestoffer = true;
                                        b_seeall_offer = true;
                                        LL_best_offer.setVisibility(View.VISIBLE);
                                        try {
                                            bestofferarray = new ArrayList<KitchensDealList>();
                                            bestofferarray = bestOfferNodelReturn.getKitchensDealList();

                                            if (bestofferarray.size() > 6) {
                                                sellall_offer.setVisibility(View.VISIBLE);
                                            } else {
                                                sellall_offer.setVisibility(View.INVISIBLE);
                                            }

                                            offerDashAdapter = new OfferDashAdapter(DashBoard.this, bestofferarray);
                                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                                            recycleOffer.setLayoutManager(layoutManager);
                                            recycleOffer.setItemAnimator(new DefaultItemAnimator());
                                            recycleOffer.setAdapter(offerDashAdapter);

                                            stopService(new Intent(DashBoard.this, GPSTracker.class));
                                        } catch (Exception e) {
                                            stopService(new Intent(DashBoard.this, GPSTracker.class));
                                        }

//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        b_bestoffer = false;
                                        b_seeall_offer = false;
                                         load_dialog.cancel();
                                        LL_best_offer.setVisibility(View.GONE);

                                        stopService(new Intent(DashBoard.this, GPSTracker.class));
                                    }
                                });
                            } else if (check.equals("-1")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();
                                    }
                                });
                                stopService(new Intent(DashBoard.this, GPSTracker.class));

                            } else if (check.equals("5")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });


                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }

                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     load_dialog.cancel();
                                    //   redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                    //nointernet();
                                    dash_dialog.show();
                                    stopService(new Intent(DashBoard.this, GPSTracker.class));
                                }
                            });
                        }
                    } else if (from.toString().equalsIgnoreCase("firstdashitadd")) {
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

//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }

                                        //fetching default
                                        String data = mDatabase.getString("defaultfilter", "");
                                        kitchenSearch = gson.fromJson(data, KitchenSearch.class);

                                        if (kitchenSearch == null || data.equals("")) {
                                            kitchenSearch = new KitchenSearch();

                                            kitchenSearch.setCostForOne("" + 0);
                                            kitchenSearch.setDeliveryFrom("" + 0);
                                            kitchenSearch.setDeliveryTo("" + 60);
                                            kitchenSearch.setDistance("" + 5);
                                            kitchenSearch.setStartIndex("" + 0);
                                            kitchenSearch.setEndIndex("" + 4);
                                            kitchenSearch.setIsBookMarked("" + 0);
                                            kitchenSearch.setIsDiscount("" + 0);
                                            kitchenSearch.setIsVegetarian("" + 0);
                                            kitchenSearch.setMinimumOrder("" + 0);
                                            kitchenSearch.setServiceType("");
                                            kitchenSearch.setKitchenType("");
                                            kitchenSearch.setSortBy("Distance");
                                            kitchenSearch.setDeliveryDate(date_format);
                                            kitchenSearch.setCuisineList("");

                                            mFilterdefault = gson.toJson(kitchenSearch);
                                            mTabel.putString("defaultfilter", mFilterdefault);
                                            mTabel.commit();
                                        }

                                        DilalogModel dilalogModel;
                                        for (int i = 0; i < addlist.size(); i++) {
                                            DialogData dialogdata = new DialogData();
                                            dialogdata.setLocality(addlist.get(i).getLocality());
                                            String fulladdress = addlist.get(i).getAddressLine1() + ", " + addlist.get(i).getAddressLine2() + ", " + addlist.get(i).getCityName() + ", " + addlist.get(i).getStateName() + ", " + addlist.get(i).getCountryName() + ", " + addlist.get(i).getZipCode();
                                            dialogdata.setDeladdress(fulladdress);
                                            dialogdata.setLat(Double.parseDouble(addlist.get(i).getLatitude()));
                                            dialogdata.setLongi(Double.parseDouble(addlist.get(i).getLongitude()));
                                            dialogdata.setType(addlist.get(i).getAddressType());
                                            dialogdata.setCity(addlist.get(i).getCityName());
                                            dialogdata.setZip(addlist.get(i).getZipCode());
                                            dialogdata.setCountry(addlist.get(i).getCountryName());
                                            Dialogdata.add(dialogdata);
                                        }

                                        if (Dialogdata.isEmpty()) {
                                            kitchenSearch.setLatitude("" + latitude);
                                            kitchenSearch.setLongitude("" + longitude);
                                            getlocation();

                                        } else {
                                            dilalogModel = new DilalogModel();
                                            dilalogModel.setAddress(Dialogdata.get(Dialogdata.size() - 1).getLocality());
                                            dilalogModel.setDeladdress(Dialogdata.get(Dialogdata.size() - 1).getDeladdress());
                                            dilalogModel.setLat(Dialogdata.get(Dialogdata.size() - 1).getLat());
                                            dilalogModel.setLongi(Dialogdata.get(Dialogdata.size() - 1).getLongi());
                                            dilalogModel.setCity(Dialogdata.get(Dialogdata.size() - 1).getCity());


                                            basicInformation.setUserLatitude("" + dilalogModel.getLat());
                                            basicInformation.setUserLongitude("" + dilalogModel.getLongi());
                                            basicInformation.setUserCityName(dilalogModel.getCity());

                                            mBasicInformation = gson.toJson(basicInformation);
                                            mTabel.putString("basicinformation", mBasicInformation);
                                            mTabel.commit();

                                            String dialogdata = gson.toJson(dilalogModel);
                                            mTabel.putString("dialogdata", dialogdata);
                                            mTabel.commit();

                                            try {
                                                kitchenSearch.setLatitude("" + Dialogdata.get(Dialogdata.size() - 1).getLat());
                                                kitchenSearch.setLongitude("" + Dialogdata.get(Dialogdata.size() - 1).getLongi());
                                            } catch (Exception e) {
                                                System.out.println(">>>>>>>>" + e);
                                            }


                                            // Log.i("saavor", "loc 1" + Dialogdata.get(Dialogdata.size() - 1).getAdd1() + ", " + Dialogdata.get(Dialogdata.size() - 1).getCity() + ", " + Dialogdata.get(Dialogdata.size() - 1).getState());

                                            dash_loc.setText(Dialogdata.get(Dialogdata.size() - 1).getLocality());
                                            localityuser = Dialogdata.get(Dialogdata.size() - 1).getLocality();

//                                            if(Dialogdata.get(Dialogdata.size() - 1).getAdd2() == null || Dialogdata.get(Dialogdata.size() - 1).getAdd2() == "")
//                                            {
// total_address = Dialogdata.get(Dialogdata.size() - 1).getAdd1() + ", " + Dialogdata.get(Dialogdata.size() - 1).getCity() + ", " + Dialogdata.get(Dialogdata.size() - 1).getState()+ ", " + Dialogdata.get(Dialogdata.size() - 1).getCountry() + ", " + Dialogdata.get(Dialogdata.size() - 1).getZip();
//                                            }
//                                            else
//                                            {
//                                                total_address = Dialogdata.get(Dialogdata.size() - 1).getAdd1() + ", " + Dialogdata.get(Dialogdata.size() - 1).getAdd2()+ ", " +Dialogdata.get(Dialogdata.size() - 1).getCity() + ", " + Dialogdata.get(Dialogdata.size() - 1).getState()+ ", " + Dialogdata.get(Dialogdata.size() - 1).getCountry() + ", " + Dialogdata.get(Dialogdata.size() - 1).getZip();
//                                            }
//

                                           //
                                            // .
                                            //
                                            // deliveryinstruct=Dialogdata.get(Dialogdata.size() - 1).getde();


//                                            coustmerdetails.get(0).setAdd_1(Dialogdata.get(Dialogdata.size() - 1).getAdd1());
//                                            coustmerdetails.get(0).setAdd_2(Dialogdata.get(Dialogdata.size() - 1).getAdd2());
//                                            coustmerdetails.get(0).setDel_city(Dialogdata.get(Dialogdata.size() - 1).getCity());
//                                            coustmerdetails.get(0).setDel_state(Dialogdata.get(Dialogdata.size() - 1).getState());
//                                            coustmerdetails.get(0).setDel_country(Dialogdata.get(Dialogdata.size() - 1).getCountry());
//                                            coustmerdetails.get(0).setZip(Dialogdata.get(Dialogdata.size() - 1).getZip());
//                                            coustmerdetails.get(0).setDel_type(Dialogdata.get(Dialogdata.size() - 1).getType());
//                                            coustmerdetails.get(0).setDelivery_Address(Dialogdata.get(Dialogdata.size() - 1).getDeladdress());
//                                            coustmerdetails.get(0).setDelivery_locality(Dialogdata.get(Dialogdata.size() - 1).getLocality());

                                            deliveryeditor.putString("Deliveryaddress", Dialogdata.get(Dialogdata.size() - 1).getLocality());
                                            deliveryeditor.commit();
//                                            deliveryTypeDetails.setDeliveryAddress(Dialogdata.get(Dialogdata.size() - 1).getLocality());
//                                            commitdelivery();


                                            try {
                                                kitchenSearch.setCostForOne("" + 0);
                                                kitchenSearch.setDeliveryFrom("" + 0);
                                                kitchenSearch.setDeliveryTo("" + 60);
                                                kitchenSearch.setDistance("" + 5);
                                                kitchenSearch.setStartIndex("" + 0);
                                                kitchenSearch.setEndIndex("" + 5);
                                                kitchenSearch.setIsBookMarked("" + 0);
                                                kitchenSearch.setIsDiscount("" + 0);
                                                kitchenSearch.setIsDelivery(1);
                                                kitchenSearch.setIsVegetarian("" + 0);
                                                kitchenSearch.setMinimumOrder("" + 0);
                                                kitchenSearch.setServiceType("");
                                                kitchenSearch.setKitchenType("");
                                                kitchenSearch.setSortBy("Distance");
                                                kitchenSearch.setCuisineList("");
                                                kitchenSearch.setDeliveryDate(date_format);
                                                kitchenSearch.setCurrentDate(date_format);

                                                kitchenSearch.setUserId(basicInformation.getUserId().toString());
                                                kitchenSearch.setSessionToken(basicInformation.getSessionToken());
                                                kitchenSearch.setSearchText(search.getText().toString());

                                                mFilterdefault = gson.toJson(kitchenSearch);
                                                mTabel.putString("defaultfilter", mFilterdefault);
                                                mTabel.commit();

                                                String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                                                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                                oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
                                                oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);


                                            } catch (Exception e) {

                                            }
                                        }

                                    }
                                });

                            } else if (check.equals("0")) {
                                try {
                                    getlocation();
                                    kitchenSearch.setLatitude("" + latitude);
                                    kitchenSearch.setLongitude("" + longitude);
                                    kitchenSearch.setCostForOne("" + 0);
                                    kitchenSearch.setDeliveryFrom("" + 0);
                                    kitchenSearch.setDeliveryTo("" + 60);
                                    kitchenSearch.setDistance("" + 5);
                                    kitchenSearch.setStartIndex("" + 0);
                                    kitchenSearch.setEndIndex("" + 5);
                                    kitchenSearch.setIsBookMarked("" + 0);
                                    kitchenSearch.setIsDiscount("" + 0);
                                    kitchenSearch.setIsVegetarian("" + 0);
                                    kitchenSearch.setMinimumOrder("" + 0);
                                    kitchenSearch.setServiceType("");
                                    kitchenSearch.setKitchenType("");
                                    kitchenSearch.setIsDelivery(1);
                                    kitchenSearch.setSortBy("Distance");
                                    kitchenSearch.setCuisineList("");
                                    kitchenSearch.setDeliveryDate(date_format);
                                    kitchenSearch.setCurrentDate(date_format);


                                    kitchenSearch.setUserId(basicInformation.getUserId().toString());
                                    kitchenSearch.setSessionToken(basicInformation.getSessionToken());
                                    kitchenSearch.setSearchText(search.getText().toString());

                                    mFilterdefault = gson.toJson(kitchenSearch);
                                    mTabel.putString("defaultfilter", mFilterdefault);
                                    mTabel.commit();

                                    String jsonStringg = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                    oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
                                    oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonStringg);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                     load_dialog.cancel();
                                }


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
                                stopService(new Intent(DashBoard.this, GPSTracker.class));

                            } else if (check.equals("5")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });


                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }


                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     load_dialog.cancel();
                                    // redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                   // nointernet();
                                    dash_dialog.show();
                                    stopService(new Intent(DashBoard.this, GPSTracker.class));
                                }
                            });
                        }
                    } else if (from.toString().equalsIgnoreCase("KitchenSearchdata")) {
                        try {
                            JSONObject Jsonobject = new JSONObject(what);
                            String jsonString = Jsonobject.toString();

                            gson = new Gson();
                            kitchenSearchReturn = gson.fromJson(jsonString, KitchenSearchReturn.class);
                            System.out.println(">>>>" + what);
                            String check = kitchenSearchReturn.getReturnCode();
                            final String message = kitchenSearchReturn.getReturnMessage();

                            if (check.equals("1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                        sc_kitchen.setVisibility(View.GONE);
                                        LL_resultfound.setVisibility(View.VISIBLE);
                                        LL_topkitchen.setVisibility(View.VISIBLE);
                                        typing.setText("Top Kitchens near you");
                                        kitchensearcharray = new ArrayList<KitchenList>();
                                        kitchensearcharray = kitchenSearchReturn.getKitchenList();
                                        System.out.println(">>>>>" + kitchensearcharray);

                                        if (kitchensearcharray.size() > 3) {
                                            seeall_kitchen.setVisibility(View.VISIBLE);
                                        } else {
                                            seeall_kitchen.setVisibility(View.INVISIBLE);
                                        }

                                        dashRecycleAdapter = new DashRecycleAdapter(DashBoard.this, kitchensearcharray);
                                        mLayoutManager = new LinearLayoutManager(DashBoard.this);
                                        recyclerView_Dash.setLayoutManager(mLayoutManager);
                                        recyclerView_Dash.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView_Dash.setAdapter(dashRecycleAdapter);

                                        notificationcount();

                                        hittingapi();

                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LL_topkitchen.setVisibility(View.INVISIBLE);
                                        sc_kitchen.setVisibility(View.VISIBLE);
                                        LL_resultfound.setVisibility(View.GONE);
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

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });


                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }

                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                     load_dialog.cancel();
                                   // nointernet();
                                    dash_dialog.show();
                                    //  redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                }
                            });
                        }
                    } else if (from.toString().equalsIgnoreCase("FetchAdd")) {
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
//
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }

                                        Dialogdata.clear();
                                         load_dialog.cancel();

                                        for (int i = 0; i < addlist.size(); i++) {
                                            DialogData dialogdata = new DialogData();
                                            dialogdata.setLat(Double.parseDouble(addlist.get(i).getLatitude()));
                                            dialogdata.setLongi(Double.parseDouble(addlist.get(i).getLongitude()));
                                            dialogdata.setLocality(addlist.get(i).getLocality());
                                            String fulladdress = addlist.get(i).getAddressLine1() + ", " + addlist.get(i).getAddressLine2() + ", " + addlist.get(i).getCityName() + ", " + addlist.get(i).getStateName() + ", " + addlist.get(i).getCountryName() + ", " + addlist.get(i).getZipCode();
                                            dialogdata.setDeladdress(fulladdress);
                                            dialogdata.setType(addlist.get(i).getAddressType());
                                            dialogdata.setCity(addlist.get(i).getCityName());
                                            dialogdata.setZip(addlist.get(i).getZipCode());
                                            dialogdata.setCountry(addlist.get(i).getCountryName());

                                            Dialogdata.add(dialogdata);
                                        }

                                        if (addlist.isEmpty()) {
                                            getlocation();
                                            addlist_dialg.setVisibility(View.GONE);
                                            loadingaddress.setVisibility(View.GONE);
                                            recaddlist.setVisibility(View.GONE);
                                        } else {
                                            DilalogModel dilalogModel = new DilalogModel();
                                            dilalogModel.setAddress(Dialogdata.get(Dialogdata.size() - 1).getLocality());
                                            dilalogModel.setDeladdress(Dialogdata.get(Dialogdata.size() - 1).getDeladdress());
                                            dilalogModel.setLat(Dialogdata.get(Dialogdata.size() - 1).getLat());
                                            dilalogModel.setLongi(Dialogdata.get(Dialogdata.size() - 1).getLongi());
                                            dilalogModel.setCity(Dialogdata.get(Dialogdata.size() - 1).getCity());

                                            basicInformation.setUserLatitude("" + dilalogModel.getLat());
                                            basicInformation.setUserLongitude("" + dilalogModel.getLongi());
                                            basicInformation.setUserCityName(dilalogModel.getCity());

                                            String dialogdata = gson.toJson(dilalogModel);
                                            mTabel.putString("dialogdata", dialogdata);
                                            mTabel.commit();

                                            //fetching dialog location
                                            String dailogdata = mDatabase.getString("dialogdata", "");
                                            dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                                            Log.i("SAAVOR", "add 1" + dilalogModel.getAddress());


                                            mAct_Location.setText(dilalogModel.getAddress());

                                            loadingaddress.setVisibility(View.GONE);
                                            addlist_dialg.setVisibility(View.VISIBLE);
                                            recaddlist.setVisibility(View.VISIBLE);

                                            addlistAdapter = new AddlistAdapter(DashBoard.this);
                                            mLayoutManager = new LinearLayoutManager(DashBoard.this);
                                            recaddlist.setLayoutManager(mLayoutManager);
                                            recaddlist.setItemAnimator(new DefaultItemAnimator());
                                            recaddlist.setAdapter(addlistAdapter);
                                        }


                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        recaddlist.setVisibility(View.GONE);
                                        loadingaddress.setVisibility(View.GONE);
                                         load_dialog.cancel();
                                    }
                                });

                            } else if (check.equals("-1")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();
                                        loadingaddress.setVisibility(View.GONE);
                                        recaddlist.setVisibility(View.GONE);
                                    }
                                });
                            } else if (check.equals("5")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                         load_dialog.cancel();

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });


                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }

                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                     load_dialog.cancel();
                                //    nointernet();
                                    dash_dialog.show();
                                    // redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                }
                            });
                        }
                    } else if (from.toString().equalsIgnoreCase("afteraddnew")) {
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

//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }

                                        Dialogdata.clear();

                                        for (int i = 0; i < addlist.size(); i++) {
                                            DialogData dialogdata = new DialogData();

                                            dialogdata.setLat(Double.parseDouble(addlist.get(i).getLatitude()));
                                            dialogdata.setLongi(Double.parseDouble(addlist.get(i).getLongitude()));
                                            dialogdata.setLocality(addlist.get(i).getLocality());
                                            String fulladdress = addlist.get(i).getAddressLine1() + ", " + addlist.get(i).getAddressLine2() + ", " + addlist.get(i).getCityName() + ", " + addlist.get(i).getStateName() + ", " + addlist.get(i).getCountryName() + ", " + addlist.get(i).getZipCode();
                                            dialogdata.setDeladdress(fulladdress);
                                            dialogdata.setType(addlist.get(i).getAddressType());
                                            dialogdata.setCity(addlist.get(i).getCityName());
                                            dialogdata.setZip(addlist.get(i).getZipCode());
                                            dialogdata.setCountry(addlist.get(i).getCountryName());
                                            Dialogdata.add(dialogdata);
                                        }

                                        if (addlist.isEmpty()) {
                                            getlocation();

                                            //fetching default
                                            String data = mDatabase.getString("defaultfilter", "");
                                            kitchenSearch = gson.fromJson(data, KitchenSearch.class);

                                            //fetching dialog location
                                            DilalogModel dilalogModel = new DilalogModel();
                                            String dailogdata = mDatabase.getString("dialogdata", "");
                                            dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                                            String datee = date_format;
                                            kitchenSearch.setCurrentDate(datee);
                                            kitchenSearch.setLatitude("" + dilalogModel.getLat());
                                            kitchenSearch.setLongitude("" + dilalogModel.getLongi());
                                            kitchenSearch.setUserId(basicInformation.getUserId().toString());
                                            kitchenSearch.setSessionToken(basicInformation.getSessionToken());
                                            kitchenSearch.setSearchText(search.getText().toString());

                                            String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                            oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
                                            oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);

                                        } else {
                                            DilalogModel dilalogModel = new DilalogModel();
                                            dilalogModel.setAddress(Dialogdata.get(Dialogdata.size() - 1).getLocality());
                                            dilalogModel.setDeladdress(Dialogdata.get(Dialogdata.size() - 1).getDeladdress());
                                            dilalogModel.setLat(Dialogdata.get(Dialogdata.size() - 1).getLat());
                                            dilalogModel.setLongi(Dialogdata.get(Dialogdata.size() - 1).getLongi());
                                            dilalogModel.setCity(Dialogdata.get(Dialogdata.size() - 1).getCity());


//                                            coustmerdetails.get(0).setAdd_1(Dialogdata.get(Dialogdata.size() - 1).getAdd1());
//                                            coustmerdetails.get(0).setAdd_2(Dialogdata.get(Dialogdata.size() - 1).getAdd2());
//                                            coustmerdetails.get(0).setDel_city(Dialogdata.get(Dialogdata.size() - 1).getCity());
//                                            coustmerdetails.get(0).setDel_state(Dialogdata.get(Dialogdata.size() - 1).getState());
//                                            coustmerdetails.get(0).setDel_country(Dialogdata.get(Dialogdata.size() - 1).getCountry());
//                                            coustmerdetails.get(0).setZip(Dialogdata.get(Dialogdata.size() - 1).getZip());
//                                            coustmerdetails.get(0).setDel_type(Dialogdata.get(Dialogdata.size() - 1).getType());
//                                            coustmerdetails.get(0).setDelivery_Address(Dialogdata.get(Dialogdata.size() - 1).getDeladdress());
//                                            coustmerdetails.get(0).setDelivery_locality(Dialogdata.get(Dialogdata.size() - 1).getLocality());


                                            basicInformation.setUserLatitude("" + dilalogModel.getLat());
                                            basicInformation.setUserLongitude("" + dilalogModel.getLongi());
                                            basicInformation.setUserCityName(dilalogModel.getCity());


                                            mBasicInformation = gson.toJson(basicInformation);
                                            mTabel.putString("basicinformation", mBasicInformation);
                                            mTabel.commit();


                                            String dialogdata = gson.toJson(dilalogModel);
                                            mTabel.putString("dialogdata", dialogdata);
                                            mTabel.commit();

                                            //fetching dialog location
                                            String dailogdata = mDatabase.getString("dialogdata", "");
                                            dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                                            Log.i("SAAVOR", "add 1" + dilalogModel.getAddress());
                                            dash_loc.setText(dilalogModel.getAddress());
                                            localityuser = dilalogModel.getAddress();

//                                            if(Dialogdata.get(Dialogdata.size() - 1).getAdd2() == null || Dialogdata.get(Dialogdata.size() - 1).getAdd2() == "")
//                                            {
//                                                total_address = Dialogdata.get(Dialogdata.size() - 1).getAdd1() + ", " + Dialogdata.get(Dialogdata.size() - 1).getCity() + ", " + Dialogdata.get(Dialogdata.size() - 1).getState()+ ", " + Dialogdata.get(Dialogdata.size() - 1).getCountry() + ", " + Dialogdata.get(Dialogdata.size() - 1).getZip();
//                                            }
//                                            else
//                                            {
//                                                total_address = Dialogdata.get(Dialogdata.size() - 1).getAdd1() + ", " + Dialogdata.get(Dialogdata.size() - 1).getAdd2()+ ", " +Dialogdata.get(Dialogdata.size() - 1).getCity() + ", " + Dialogdata.get(Dialogdata.size() - 1).getState()+ ", " + Dialogdata.get(Dialogdata.size() - 1).getCountry() + ", " + Dialogdata.get(Dialogdata.size() - 1).getZip();
//                                            }




                                            //fetching default
                                            String data = mDatabase.getString("defaultfilter", "");
                                            kitchenSearch = gson.fromJson(data, KitchenSearch.class);

                                            //fetching dialog location
                                            DilalogModel dilalogModell = new DilalogModel();
                                            String dailogdataa = mDatabase.getString("dialogdata", "");
                                            dilalogModell = gson.fromJson(dailogdata, DilalogModel.class);

                                            String datee = date_format;
                                            kitchenSearch.setCurrentDate(datee);
                                            kitchenSearch.setLatitude("" + dilalogModell.getLat());
                                            kitchenSearch.setLongitude("" + dilalogModell.getLongi());
                                            kitchenSearch.setUserId(basicInformation.getUserId().toString());
                                            kitchenSearch.setSessionToken(basicInformation.getSessionToken());
                                            kitchenSearch.setSearchText(search.getText().toString());

                                            String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                            oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
                                            oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);
                                        }
                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getlocation();
                                        //fetching default
                                        String data = mDatabase.getString("defaultfilter", "");
                                        kitchenSearch = gson.fromJson(data, KitchenSearch.class);

                                        //fetching dialog location
                                        DilalogModel dilalogModel = new DilalogModel();
                                        String dailogdata = mDatabase.getString("dialogdata", "");
                                        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                                        String datee = date_format;
                                        kitchenSearch.setCurrentDate(datee);
                                        kitchenSearch.setLatitude("" + dilalogModel.getLat());
                                        kitchenSearch.setLongitude("" + dilalogModel.getLongi());
                                        kitchenSearch.setUserId(basicInformation.getUserId().toString());
                                        kitchenSearch.setSessionToken(basicInformation.getSessionToken());
                                        kitchenSearch.setSearchText(search.getText().toString());

                                        String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                        oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
                                        oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);
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

                                        SharedPreferences settings = DashBoard.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                        settings.edit().clear().commit();

                                        mTabel.putBoolean("session", false);
                                        mTabel.commit();

                                        SharedPreferences.Editor editor = DashBoard.this.getSharedPreferences("SocialLogin", Context.MODE_PRIVATE).edit();
                                        editor.clear();
                                        editor.commit();
                                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>() {
                                                    @Override
                                                    public void onResult(Status status) {

                                                    }
                                                });
                                        intent = new Intent(DashBoard.this, MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        DashBoard.this.startActivity(intent);
                                    }
                                });
                            }


                        } catch (Exception e) {
                            System.out.println(">>>>" + e);
                             load_dialog.cancel();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    } else if (from.toString().equalsIgnoreCase("Address")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject Jsonobject;
                                    Jsonobject = new JSONObject(what);
                                    JSONArray jsonArray = Jsonobject.getJSONArray("results");
                                    String City = null;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        JSONObject geometryObject = jsonObject.getJSONObject("geometry");
                                        JSONObject locationObject = geometryObject.getJSONObject("location");
                                        String lat = locationObject.getString("lat");
                                        String lng = locationObject.getString("lng");
                                        JSONArray jsonArray1 = null;
                                        jsonArray1 = Jsonobject.getJSONArray("address_components");


                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONArray ja_types = jsonArray1.getJSONObject(j).getJSONArray("types");
                                            for (int k = 0; k < ja_types.length(); k++) {
                                                if (ja_types.get(0).equals("locality")) {
                                                    City = jsonArray1.getJSONObject(j).getString("long_name");

                                                }
                                            }
                                        }

                                        DilalogModel dilalogModel = new DilalogModel();
                                        dilalogModel.setAddress(mAct_Location.getText().toString());
                                        dilalogModel.setLat(Double.parseDouble(lat));
                                        dilalogModel.setLongi(Double.parseDouble(lng));
                                        dilalogModel.setCity(City);

                                        basicInformation.setUserLatitude("" + dilalogModel.getLat());
                                        basicInformation.setUserLongitude("" + dilalogModel.getLongi());
                                        basicInformation.setUserCityName(dilalogModel.getCity());

                                        mBasicInformation = gson.toJson(basicInformation);
                                        mTabel.putString("basicinformation", mBasicInformation);
                                        mTabel.commit();


                                        String dialogdata = gson.toJson(dilalogModel);
                                        mTabel.putString("dialogdata", dialogdata);
                                        mTabel.commit();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                     load_dialog.cancel();

                                }

                            }
                        });
                    } else if (from.toString().equalsIgnoreCase("notcount")) {
                        String doo = what;
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

//
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }

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

                                        bestofferhit();
                                    }
                                });
                            } else if (check.equals("0")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        bestofferhit();
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
                                    // redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                                  //  nointernet();
                                    dash_dialog.show();
                                }
                            });
                        }


                    } else if (from.toString().equalsIgnoreCase("GetChefWithRating")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject Jsonobject;
                                JSONArray JaChefList;
                                try {
                                    Jsonobject = new JSONObject(what);

                                    if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("-5")) {
                                         load_dialog.cancel();
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        startActivity(intent);
                                    } else if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("1")) {
                                       //  load_dialog.cancel();
//                                         try
//                                         {
//                                             dialog.dismiss();
//                                         }catch(Exception t)
//                                         {
//
//                                         }
                                        JaChefList = Jsonobject.getJSONArray("ChefList");
                                        aListChefList = new ArrayList<ChefList>();
                                        for (int m = 0; m < JaChefList.length(); m++) {
                                            ChefList oChefList = new ChefList();
                                            oChefList.setChefId(JaChefList.getJSONObject(m).getString("ChefId"));
                                            oChefList.setFirstName(JaChefList.getJSONObject(m).getString("FirstName"));
                                            oChefList.setLastName(JaChefList.getJSONObject(m).getString("LastName"));
                                            oChefList.setMI(JaChefList.getJSONObject(m).getString("MI"));
                                            oChefList.setPrice(JaChefList.getJSONObject(m).getString("Price"));
                                            oChefList.setProfileImagePath(JaChefList.getJSONObject(m).getString("ProfileImagePath"));
                                            oChefList.setStarRating(JaChefList.getJSONObject(m).getString("StarRating"));
                                            aListChefList.add(oChefList);
                                        }
                                        ll_recommended_for_you.setVisibility(View.VISIBLE);

                                        recommendedAdapter = new RecommendedForYouAdapter(mContext, aListChefList);
                                        rv_chef_recommended.setAdapter(recommendedAdapter);
                                        IsChefDataAvail = true;

                                    } else {
                                        recommendedAdapter = new RecommendedForYouAdapter(mContext, aListChefList);
                                        rv_chef_recommended.setAdapter(recommendedAdapter);
                                        ll_recommended_for_you.setVisibility(View.GONE);

                                    }

                                    DilalogModel dilalogModel = new DilalogModel();
                                    String dailogdata = mDatabase.getString("dialogdata", "");
                                    dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

                                    GetChefWithRating oGetChefWithRating = new GetChefWithRating();
                                    oGetChefWithRating.setSessionToken(basicInformation.getSessionToken());
                                    oGetChefWithRating.setUserId(basicInformation.getUserId());
                                    oGetChefWithRating.setDistance("10");
                                    oGetChefWithRating.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                                    oGetChefWithRating.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                                    oGetChefWithRating.setUserType(UserType);
                                    oGetChefWithRating.setCurrentDate(date_format);

                                    String jsonString = oGson.toJson(oGetChefWithRating, GetChefWithRating.class).toString();
                                    PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                                    oInsertUpdateApi.setRequestSource(RequestSource.GetChefWithDeals);
                                    oInsertUpdateApi.executePostRequest(API.fGetChefWithDeals(), jsonString);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                  //  nointernet();
                                    dash_dialog.show();
                                     load_dialog.cancel();

                                }
                            }
                        });
                    } else if (from.toString().equalsIgnoreCase("GetChefWithDeals")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject Jsonobject;
                                JSONArray JaChefList;
                                try {
                                    Jsonobject = new JSONObject(what);

                                    if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("-5")) {
                                         load_dialog.cancel();
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        startActivity(intent);
                                    } else if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("1")) {

                                      //   load_dialog.cancel();
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                        JaChefList = Jsonobject.getJSONArray("ChefList");
                                        aListChefList = new ArrayList<ChefList>();
                                        for (int m = 0; m < JaChefList.length(); m++) {
                                            ChefList oChefList = new ChefList();
                                            oChefList.setChefId(JaChefList.getJSONObject(m).getString("ChefId"));
                                            oChefList.setFirstName(JaChefList.getJSONObject(m).getString("FirstName"));
                                            oChefList.setLastName(JaChefList.getJSONObject(m).getString("LastName"));
                                            oChefList.setMI(JaChefList.getJSONObject(m).getString("MI"));
                                            oChefList.setPrice(JaChefList.getJSONObject(m).getString("DealTitle"));
                                            oChefList.setProfileImagePath(JaChefList.getJSONObject(m).getString("ProfileImagePath"));
                                            oChefList.setStarRating("");
                                            aListChefList.add(oChefList);
                                        }
                                        ll_best_deal_chef.setVisibility(View.VISIBLE);

                                        ChefoffersAdapter = new ChefOffersAdapter(DashBoard.this, aListChefList);
                                        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getApplicationContext(), 3);
                                        rv_best_offer_chef.setLayoutManager(layoutManager2);
                                        rv_best_offer_chef.setItemAnimator(new DefaultItemAnimator());
                                        rv_best_offer_chef.setAdapter(ChefoffersAdapter);

                                        IsChefDataAvail = true;
                                    } else {
                                        ChefoffersAdapter = new ChefOffersAdapter(DashBoard.this, aListChefList);
                                        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getApplicationContext(), 3);
                                        rv_best_offer_chef.setLayoutManager(layoutManager2);
                                        rv_best_offer_chef.setItemAnimator(new DefaultItemAnimator());
                                        rv_best_offer_chef.setAdapter(ChefoffersAdapter);
                                        ll_best_deal_chef.setVisibility(View.GONE);
                                    }


                                    // Get ChefSearch.......
                                    DilalogModel dilalogModel = new DilalogModel();
                                    String dailogdata = mDatabase.getString("dialogdata", "");
                                    dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);


                                    ChefSearch oChefSearch = new ChefSearch();
                                    SharedPreferences shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
                                    if (!shared.getString("SortBy", "").equalsIgnoreCase("")) {
                                        oChefSearch.setSessionToken(basicInformation.getSessionToken());
                                        oChefSearch.setUserId(basicInformation.getUserId());
                                        oChefSearch.setBookingDate(shared.getString("BookingDateTime", ""));
                                        oChefSearch.setCuisineList(shared.getString("Cuisine", "").replace(" ", ""));
                                        oChefSearch.setCurrentDate(date_format);
                                        oChefSearch.setDistance("0");
                                        oChefSearch.setEndIndex("100");
                                        oChefSearch.setStartIndex("0");
                                        oChefSearch.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                                        oChefSearch.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                                        oChefSearch.setGender(shared.getString("QuickFilter", ""));
                                        oChefSearch.setRediusFrom(shared.getString("MinRadius", ""));
                                        oChefSearch.setRediusTo(shared.getString("MaxRadius", ""));
                                        oChefSearch.setSearchText("");
                                        oChefSearch.setServiceType(shared.getString("Service", ""));
                                        oChefSearch.setSortBy(shared.getString("SortBy", ""));
                                        oChefSearch.setUserType(UserType);
                                    } else {
                                        oChefSearch.setSessionToken(basicInformation.getSessionToken());
                                        oChefSearch.setUserId(basicInformation.getUserId());
                                        oChefSearch.setBookingDate(date_format);
                                        oChefSearch.setCuisineList("");
                                        oChefSearch.setCurrentDate(date_format);
                                        oChefSearch.setDistance("0");
                                        oChefSearch.setEndIndex("100");
                                        oChefSearch.setStartIndex("0");
                                        oChefSearch.setLatitude(String.valueOf(basicInformation.getUserLatitude()));
                                        oChefSearch.setLongitude(String.valueOf(basicInformation.getUserLongitude()));
                                        oChefSearch.setGender("Any Gender");
                                        oChefSearch.setRediusFrom("0");
                                        oChefSearch.setRediusTo("60");
                                        oChefSearch.setSearchText("");
                                        oChefSearch.setServiceType("");
                                        oChefSearch.setSortBy("Distance");
                                        oChefSearch.setUserType(UserType);
                                    }

                                    String jsonString = oGson.toJson(oChefSearch, ChefSearch.class).toString();
                                    PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                                    oInsertUpdateApi.setRequestSource(RequestSource.ChefSearch);
                                    oInsertUpdateApi.executePostRequest(API.fChefSearch(), jsonString);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                   // nointernet();
                                    dash_dialog.show();
                                     load_dialog.cancel();

                                }
                            }
                        });
                    } else if (from.toString().equalsIgnoreCase("ChefSearch")) {

                       //  load_dialog.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                JSONObject Jsonobject;
                                JSONArray JaChefList;
                                try {
                                    Jsonobject = new JSONObject(what);
                                    aListChefSearchList = new ArrayList<ChefSearchList>();

                                    if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("-5")) {
                                         load_dialog.cancel();
//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("Sessionexp", 1);
                                        startActivity(intent);
                                    } else if (Jsonobject.getString("ReturnCode").equalsIgnoreCase("1")) {


//                                        try
//                                        {
//                                            dialog.dismiss();
//                                        }catch(Exception t)
//                                        {
//
//                                        }
                                        JaChefList = Jsonobject.getJSONArray("ChefList");
                                        for (int m = 0; m < JaChefList.length(); m++) {
                                            ChefSearchList oChefSearchList = new ChefSearchList();
                                            oChefSearchList.setBookingRadius(JaChefList.getJSONObject(m).getString("BookingRadius"));
                                            oChefSearchList.setBusinessName(JaChefList.getJSONObject(m).getString("BusinessName"));
                                            oChefSearchList.setBusinessType(JaChefList.getJSONObject(m).getString("BusinessType"));
                                            oChefSearchList.setChefCode(JaChefList.getJSONObject(m).getString("ChefCode"));
                                            oChefSearchList.setChefId(JaChefList.getJSONObject(m).getString("ChefId"));
                                            oChefSearchList.setCuisineList(JaChefList.getJSONObject(m).getString("CuisineList"));
                                            oChefSearchList.setFirstName(JaChefList.getJSONObject(m).getString("FirstName"));
                                            oChefSearchList.setIsDeal(JaChefList.getJSONObject(m).getString("IsDeal"));
                                            oChefSearchList.setIsOpen(JaChefList.getJSONObject(m).getString("IsOpen"));
                                            oChefSearchList.setLastName(JaChefList.getJSONObject(m).getString("LastName"));
                                            oChefSearchList.setMI(JaChefList.getJSONObject(m).getString("MI"));
                                            oChefSearchList.setPrice(JaChefList.getJSONObject(m).getString("Price"));
                                            oChefSearchList.setProfileImagePath(JaChefList.getJSONObject(m).getString("ProfileImagePath"));
                                            oChefSearchList.setServiceList(JaChefList.getJSONObject(m).getString("ServiceList"));
                                            oChefSearchList.setStarRating(JaChefList.getJSONObject(m).getString("StarRating"));
                                            oChefSearchList.setVerifiedBookings(JaChefList.getJSONObject(m).getString("VerifiedBookings"));
                                            aListChefSearchList.add(oChefSearchList);
                                        }
                                        ll_topchef.setVisibility(View.VISIBLE);

                                        topChefAdapter = new TopChefsAdapter(DashBoard.this, aListChefSearchList, false);
                                        rv_top_chefs.setAdapter(topChefAdapter);
                                        IsChefDataAvail = true;

                                        if (aListChefSearchList.size() > 3) {
                                            txtSeeAllTopChef.setVisibility(View.VISIBLE);
                                        } else {
                                            txtSeeAllTopChef.setVisibility(View.INVISIBLE);
                                        }

                                    } else {
                                        topChefAdapter = new TopChefsAdapter(DashBoard.this, aListChefSearchList, false);
                                        rv_top_chefs.setAdapter(topChefAdapter);
                                        ll_topchef.setVisibility(View.GONE);
                                    }

                                     load_dialog.cancel();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                     load_dialog.cancel();
                                   // nointernet();
                                    dash_dialog.show();

                                }

                                if (!IsChefDataAvail) {
                                    //  load_dialog.cancel();
                                    sc_kitchen.setVisibility(View.VISIBLE);
                                    llChef.setVisibility(View.GONE);
                                } else {
                                    llChef.setVisibility(View.VISIBLE);
                                    sc_kitchen.setVisibility(View.GONE);
                                    //  load_dialog.cancel();
                                }
                            }
                        });
                    }

                }
            });
        }
    }

    public void savingbasic() {
        basicInformation.setFirstName(loginDataReturn.getUserInfo().getFirstName().toString());
        basicInformation.setLastName(loginDataReturn.getUserInfo().getLastName().toString());
        basicInformation.setMobileNumber(loginDataReturn.getUserInfo().getMobileNo().toString());
        basicInformation.setDateOfBirth(loginDataReturn.getUserInfo().getDOB().toString());
        basicInformation.setMI(loginDataReturn.getUserInfo().getDOB().toString());
        basicInformation.setUserprofile(loginDataReturn.getUserInfo().getProfilePicPath());
        basicInformation.setSessionToken(loginDataReturn.getUserInfo().getSessionToken().toString());
        basicInformation.setRefferal(loginDataReturn.getUserInfo().getReferralCode().toString());
        basicInformation.setEmail(loginDataReturn.getUserInfo().getEmail().toString());
        basicInformation.setCoustmerid(loginDataReturn.getUserInfo().getCustomerId().toString());
        basicInformation.setFacebookid(loginDataReturn.getUserInfo().getFacebookId().toString());
        basicInformation.setGoogleid(loginDataReturn.getUserInfo().getGoogleId().toString());
        basicInformation.setUserId(loginDataReturn.getUserInfo().getUserId());

        mBasicInformation = gson.toJson(basicInformation);
        mTabel.putString("basicinformation", mBasicInformation);
        mTabel.commit();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    public void showpopup() {

        //dialog intialization
        dialog = new Dialog(DashBoard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.searchpopup);
        dialog.setCancelable(true);

        mAct_Location = (AutoCompleteTextView) dialog.findViewById(R.id.edt_location_popup);
        delivery = (Button) dialog.findViewById(R.id.but_search_del);
        pickup = (Button) dialog.findViewById(R.id.but_search_pick);
        loadingaddress = (TextView) dialog.findViewById(R.id.txt_loading_add);
        addlist_dialg = (TextView) dialog.findViewById(R.id.txt_addlist_dialg);

        dialogsubmit = (ImageView) dialog.findViewById(R.id.dialog_clear);
        currentloc = (ImageView) dialog.findViewById(R.id.img_gps_loc);
        LL_dashtotal = (LinearLayout) dialog.findViewById(R.id.ll_dashtotal);
        clear_pop = (ImageView) dialog.findViewById(R.id.img_text_clear);
        LinearLayout d_p= (LinearLayout) dialog.findViewById(R.id.ll_dailog_d_p);
        Button alternate_view =(Button) dialog.findViewById(R.id.alternate_view_pop);
        view = LL_dashtotal;

        recaddlist = (RecyclerView) dialog.findViewById(R.id.rec_addlist);
        recyclerView_Dash.setNestedScrollingEnabled(false);
        //recycle_dash_delivery.setNestedScrollingEnabled(false);

        if(dailog_d_p)
        {
            d_p.setVisibility(View.VISIBLE);
            alternate_view.setVisibility(View.GONE);
        }
        else
        {   alternate_view.setVisibility(View.VISIBLE);
            d_p.setVisibility(View.GONE);
        }

        if (DashBoard.Dialogdata.isEmpty()) {
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
            oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);
            String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
            String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
            Log.i("SAAVOR", "add 0");
        } else {
            addlist_dialg.setVisibility(View.VISIBLE);
            loadingaddress.setVisibility(View.GONE);
            addlistAdapter = new AddlistAdapter(DashBoard.this);
            mLayoutManager = new LinearLayoutManager(DashBoard.this);
            recaddlist.setLayoutManager(mLayoutManager);
            recaddlist.setItemAnimator(new DefaultItemAnimator());
            recaddlist.setAdapter(addlistAdapter);
        }

        //fetching dialog location
        DilalogModel dilalogModel = new DilalogModel();
        String dailogdata = mDatabase.getString("dialogdata", "");
        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

        if (dailogdata.equals("")) {
            mAct_Location.setText(gpsTracker.getAddressLine(this) + ", " + gpsTracker.getLocality(this) + ", " + gpsTracker.getState(this) + ", " + gpsTracker.getCountryName(this));
        } else {
            mAct_Location.setText(dash_loc.getText().toString());
        }

        //setting the delivery and pickup
        if (deliverytypestatus) {
            delivery.setBackgroundResource(R.drawable.popredlayout);
            pickup.setBackgroundResource(R.drawable.poprightlayout);

            delivery.setTextColor(getResources().getColor(R.color.white));
            pickup.setTextColor(getResources().getColor(R.color.accent));

        } else {
            delivery.setBackgroundResource(R.drawable.searchpopleft);
            pickup.setBackgroundResource(R.drawable.searchpopright);

            delivery.setTextColor(getResources().getColor(R.color.accent));
            pickup.setTextColor(getResources().getColor(R.color.white));
        }


        mAct_Location.setThreshold(1);
        mAct_Location.setOnItemClickListener(this);
        mAct_Location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAct_Location.setAdapter(new PlacesAutoCompleteAdapter(DashBoard.this, android.R.layout.simple_list_item_1));
                mAct_Location.requestFocus();
                InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr2.showSoftInput(mAct_Location, 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        currentloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();

                    getlocation();
                    commonhit();
                } else {
                    try {
                        dialog.dismiss();
                    } catch (Exception t) {

                    }
                    showGPSDisabledAlertToUser();
                }

            }
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delivery.setBackgroundResource(R.drawable.popredlayout);
                pickup.setBackgroundResource(R.drawable.poprightlayout);

                delivery.setTextColor(getResources().getColor(R.color.white));
                pickup.setTextColor(getResources().getColor(R.color.accent));

                deliverytypestatus = true;

            }
        });
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delivery.setBackgroundResource(R.drawable.searchpopleft);
                pickup.setBackgroundResource(R.drawable.searchpopright);

                delivery.setTextColor(getResources().getColor(R.color.accent));
                pickup.setTextColor(getResources().getColor(R.color.white));

                deliverytypestatus = false;

            }
        });

        clear_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAct_Location.setText("");
            }
        });

        dialogsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(mAct_Location.getText().toString(), getApplicationContext(), new GeocoderHandler());
                Log.i("saavor", "loc 2" + mAct_Location.getText().toString());

                dash_loc.setText(mAct_Location.getText().toString());
                localityuser = mAct_Location.getText().toString();

                deliveryeditor.putString("Deliveryaddress", mAct_Location.getText().toString());
                deliveryeditor.commit();
//                deliveryTypeDetails.setDeliveryAddress(mAct_Location.getText().toString());
//                commitdelivery();

                 load_dialog.cancel();

                try {

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    //   hideSoftKeyboard(DashBoard.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                commonhit();


            }
        });

        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(mAct_Location.getText().toString(), getApplicationContext(), new GeocoderHandler());
        Log.i("saavor", "loc 2" + mAct_Location.getText().toString());

        dash_loc.setText(mAct_Location.getText().toString());
        localityuser = "" + mAct_Location.getText().toString();

        deliveryeditor.putString("Deliveryaddress", mAct_Location.getText().toString());
        deliveryeditor.commit();
//                deliveryTypeDetails.setDeliveryAddress(mAct_Location.getText().toString());
//                commitdelivery();

         load_dialog.cancel();
//        try
//        {
//            dialog.dismiss();
//        }catch(Exception t)
//        {
//
//        }

        try {

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            //   hideSoftKeyboard(DashBoard.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        commonhit();
    }


    public void getlocation() {

        //fetching current lat lang
        location = getLoc();

        // Getting LocationManager object
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            startService(new Intent(this, GPSTracker.class));
            // check if GPS enabled

            Log.i("saavor", "latitude now" + latitude + "longitude " + longitude);

            gpsTracker = new GPSTracker(this, this, latitude, longitude, location);


            String stringLatitude = String.valueOf(gpsTracker.latitude);
            String stringLongitude = String.valueOf(gpsTracker.longitude);

            if (stringLatitude == "0.0" || stringLongitude == "0.0") {
                redirect(DashBoard.this, "Unable to determine your location\n Please make sure your location services are turned on in your device's settings and try again");
            } else {
                //setting total loc
                try {
//                    String setloc;
//                    if (gpsTracker.getAddressLine(this) != null) {
//                        setloc = gpsTracker.getAddressLine(this);
//                    }

                    // dash_loc.setText(gpsTracker.getAddressLine(this) + ", " + gpsTracker.getLocality(this) + ", " + gpsTracker.getState(this) + ", " + gpsTracker.getCountryName(this));
                    dash_loc.setText(gpsTracker.getAddressLine(this));

                    localityuser = gpsTracker.getAddressLine(this);
                    // dash_loc.setText(if(gpsTracker.getAddressLine(this) == null) {}+ ", " + gpsTracker.getLocality(this) + ", " + gpsTracker.getState(this) + ", " + gpsTracker.getCountryName(this));

                    DilalogModel dilalogModel = new DilalogModel();
                    dilalogModel.setLat(latitude);
                    dilalogModel.setLongi(longitude);
                    dilalogModel.setCity(gpsTracker.getLocality(this));
                    //dilalogModel.setAddress(gpsTracker.getAddressLine(this) + ", " + gpsTracker.getLocality(this) + ", " + gpsTracker.getState(this) + ", " + gpsTracker.getCountryName(this));
                    dilalogModel.setAddress(gpsTracker.getAddressLine(this));
                    String dialogdata = gson.toJson(dilalogModel);
                    mTabel.putString("dialogdata", dialogdata);
                    mTabel.commit();

                    basicInformation.setUserLatitude("" + latitude);
                    basicInformation.setUserLongitude("" + longitude);
                    basicInformation.setUserCityName(gpsTracker.getLocality(this));

                    mBasicInformation = gson.toJson(basicInformation);
                    mTabel.putString("basicinformation", mBasicInformation);
                    mTabel.commit();

                    //  deliveryeditor.putString("Deliveryaddress", gpsTracker.getAddressLine(this) + ", " + gpsTracker.getLocality(this) + ", " + gpsTracker.getState(this) + ", " + gpsTracker.getCountryName(this));
                    deliveryeditor.putString("Deliveryaddress", gpsTracker.getAddressLine(this));
                    deliveryeditor.commit();

                    try {
                        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    try
//                    {
//                        dialog.dismiss();
//                    }catch(Exception t)
//                    {
//
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showGPSDisabledAlertToUser() {

//        //dialog intialization
//        dialog = new Dialog(DashBoard.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.no_gps_layout);
//        dialog.setCancelable(true);

        Button settings = (Button) dialog.findViewById(R.id.gps_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                DashBoard.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        dialog.show();
    }


    public void hittingapi() {

        try {
            //getting date
            calander = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("MMM dd,yyyy hh:mm aa");
            Date = simpledateformat.format(calander.getTime());
            Log.e("dateeeee", "" + Date);
            date_format = Date.replace("a.m", "AM").replace("p.m", "PM");
            if (date_format.contains(".")) {
                date_format = date_format.substring(0, date_format.length() - 1);
            }

            Log.e("string", "" + date_format);

            //fetching dialog location
            DilalogModel dilalogModel = new DilalogModel();
            String dailogdata = mDatabase.getString("dialogdata", "");
            dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

            if (dailogdata.equals("")) {
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                oInsertUpdateApi.setRequestSource(RequestSource.afteraddnew);
            } else {
                dash_loc.setText("" + dilalogModel.getAddress());
                localityuser = "" + dilalogModel.getAddress();
                deliveryeditor.putString("Deliveryaddress", dilalogModel.getAddress());
                deliveryeditor.commit();
//                deliveryTypeDetails.setDeliveryAddress("" + dilalogModel.getAddress());
//                commitdelivery();

                dashApiHit.setCurrentDate(date_format);
                dashApiHit.setDistance("5");
                dashApiHit.setSessionToken(basicInformation.getSessionToken().toString());
                dashApiHit.setUserId(basicInformation.getUserId().toString());
                dashApiHit.setLatitude("" + dilalogModel.getLat());
                dashApiHit.setLongitude("" + dilalogModel.getLongi());

                mProgressDialog.setMessage("Fetching Information...");

                String jsonString = gson.toJson(dashApiHit, DashApiHit.class).toString();
                Log.i("Saavor", "DashboardDeals request" + jsonString);
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.DashboardDeals);
                oChangePsswordApi.executePostRequest(API.dashboarddeals(), jsonString);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        this.location = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Location getLoc() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                showGPSDisabledAlertToUser();
            } else {
                //this.canGetLocation = true;
                if (isNetworkEnabled) {
                    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public void commonhit() {


        try {
            dialog.dismiss();
        } catch (Exception t) {

        }

        mProgressDialog.setMessage("Fetching Information...");
       load_dialog.show();

        //fetching dialog location
        DilalogModel dilalogModel = new DilalogModel();
        String dailogdata = mDatabase.getString("dialogdata", "");
        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

        if (dailogdata.equals("")) {
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
            oInsertUpdateApi.setRequestSource(RequestSource.afteraddnew);
            String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
            String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();

        } else {
            dash_loc.setText("" + dilalogModel.getAddress());
            localityuser = "" + dilalogModel.getAddress();

            deliverydetails = mDatabase.getString("deliverydetails", "");
            deliveryeditor.putString("Deliveryaddress", dilalogModel.getAddress());
            deliveryeditor.commit();

            //fetching default
            String data = mDatabase.getString("defaultfilter", "");
            kitchenSearch = gson.fromJson(data, KitchenSearch.class);


            basicInformation.setUserLatitude("" + dilalogModel.getLat());
            basicInformation.setUserLongitude("" + dilalogModel.getLongi());
            basicInformation.setUserCityName(dilalogModel.getCity());

            mBasicInformation = gson.toJson(basicInformation);
            mTabel.putString("basicinformation", mBasicInformation);
            mTabel.commit();

            if (deliverytypestatus) {
                kitchenSearch.setIsDelivery(1);
            } else {
                kitchenSearch.setIsDelivery(0);
            }

            String datee = date_format;
            kitchenSearch.setCurrentDate(datee);
            kitchenSearch.setLatitude("" + dilalogModel.getLat());
            kitchenSearch.setLongitude("" + dilalogModel.getLongi());
            kitchenSearch.setUserId(basicInformation.getUserId().toString());
            kitchenSearch.setSessionToken(basicInformation.getSessionToken());

            if (search.getText().toString().trim().equals("")) {
                kitchenSearch.setSearchText("");
            } else {
                kitchenSearch.setSearchText(search.getText().toString());
            }


            if (IsChef) {

                GetChefWithRating oGetChefWithRating3 = new GetChefWithRating();
                oGetChefWithRating3.setSessionToken(basicInformation.getSessionToken());
                oGetChefWithRating3.setUserId(basicInformation.getUserId());
                oGetChefWithRating3.setDistance("10");
                oGetChefWithRating3.setLatitude(String.valueOf(dilalogModel.getLat()));
                oGetChefWithRating3.setLongitude(String.valueOf(dilalogModel.getLongi()));
                oGetChefWithRating3.setCurrentDate(date_format);
                oGetChefWithRating3.setUserType(UserType);
                String jsonString3 = oGson.toJson(oGetChefWithRating3, GetChefWithRating.class).toString();
                PostApiClient oInsertUpdateApi3 = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi3.setRequestSource(RequestSource.GetChefWithRating);
                oInsertUpdateApi3.executePostRequest(API.fGetChefWithRating(), jsonString3);
            } else {
                String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
                oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);
            }


//            String jsonString = gson.toJson(kitchenSearch, KitchenSearch.class).toString();
//            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
//            oChangePsswordApi.setRequestSource(RequestSource.KitchenSearchdata);
//            oChangePsswordApi.executePostRequest(API.KitchenSearch(), jsonString);
        }
    }

    public void bestofferhit() {
        //fetching dialog location
        DilalogModel dilalogModel = new DilalogModel();
        String dailogdata = mDatabase.getString("dialogdata", "");
        dilalogModel = gson.fromJson(dailogdata, DilalogModel.class);

        if (dailogdata.equals("")) {
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
            oInsertUpdateApi.setRequestSource(RequestSource.afteraddnew);
            String fullapi = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
            String fullapii = API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();

        } else {
            dash_loc.setText("" + dilalogModel.getAddress());
            localityuser = "" + dilalogModel.getAddress();
            deliveryeditor.putString("Deliveryaddress", dilalogModel.getAddress());
            deliveryeditor.commit();

            bestoffermodel.setDistance("" + 5);
            bestoffermodel.setCurrentDate("" + date_format);
            bestoffermodel.setEndIndex("" + 7);
            bestoffermodel.setLatitude("" + dilalogModel.getLat());
            bestoffermodel.setSessionToken(basicInformation.getSessionToken());
            bestoffermodel.setLongitude("" + dilalogModel.getLongi());
            bestoffermodel.setStartIndex("" + 0);
            bestoffermodel.setUserId(basicInformation.getUserId());

            String jsonString = gson.toJson(bestoffermodel, Bestoffermodel.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.setRequestSource(RequestSource.bestoffer);
            oChangePsswordApi.executePostRequest(API.bestoffer(), jsonString);
        }
    }

    public void hittingintial() {
        // Getting LocationManager object
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            location = getLoc();

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                // hitting for basic user info
                mProgressDialog.setMessage("Fetching Information...");
                load_dialog.show();
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.UserInfo);
                oInsertUpdateApi.executeGetRequest(API.userinfo() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
            }
        } else {

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    @Override


//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

//    public static void getadd(String add) {
//        Log.i("SAAVOR", "add 4" + add);
//        mAct_Location.setText(add);
//    }

    public class GeocoderHandler extends android.os.Handler {

        @Override
        public void handleMessage(Message message) {
            double lat, longi;
            String address, city;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    lat = bundle.getDouble("lat");
                    longi = bundle.getDouble("long");
                    address = bundle.getString("address");
                    city = bundle.getString("city");

                    DilalogModel dilalogModel = new DilalogModel();
                    dilalogModel.setAddress(address);
                    dilalogModel.setLat(lat);
                    dilalogModel.setLongi(longi);
                    dilalogModel.setCity(city);

                    basicInformation.setUserLatitude("" + dilalogModel.getLat());
                    basicInformation.setUserLongitude("" + dilalogModel.getLongi());
                    basicInformation.setUserCityName(dilalogModel.getCity());

                    mBasicInformation = gson.toJson(basicInformation);
                    mTabel.putString("basicinformation", mBasicInformation);
                    mTabel.commit();


                    String dialogdata = gson.toJson(dilalogModel);
                    mTabel.putString("dialogdata", dialogdata);
                    mTabel.commit();

                    commonhit();

                    break;

                default:

            }
        }
    }


    public void CheckFiltersValues(boolean IsKitchen) {

        if (IsKitchen) {
            //checking filter status

            //fetching default
            String data = mDatabase.getString("defaultfilter", "");
            kitchenSearch = gson.fromJson(data, KitchenSearch.class);

            if (kitchenSearch == null || data.equals("")) {
                kitchenSearch = new KitchenSearch();

                kitchenSearch.setCostForOne("" + 0);
                kitchenSearch.setDeliveryFrom("" + 0);
                kitchenSearch.setDeliveryTo("" + 60);
                kitchenSearch.setDistance("" + 5);
                kitchenSearch.setStartIndex(
                        "" + 0);
                kitchenSearch.setEndIndex(
                        "" + 4);
                kitchenSearch.setIsBookMarked("" + 0);
                kitchenSearch.setIsDiscount("" + 0);
                kitchenSearch.setIsVegetarian("" + 0);
                kitchenSearch.setMinimumOrder("" + 0);
                kitchenSearch.setServiceType("");
                kitchenSearch.setKitchenType("");
                kitchenSearch.setSortBy("Distance");
                kitchenSearch.setDeliveryDate(date_format);
                kitchenSearch.setCuisineList("");

                mFilterdefault = gson.toJson(kitchenSearch);
                mTabel.putString("defaultfilter", mFilterdefault);
                mTabel.commit();
            } else {
                String breakfast = "Breakfast";
                String lunch = "Lunch";
                String Dinner = "Dinner";
                String Brunch = "Brunch";


                datebeforeFormat = new SimpleDateFormat("MMM dd,yyyy");

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

                if (deliverydatee == null || deliverydatee.equals("")) {
                    try {
                        deliverydatee = datebeforeFormat.parse(date_format);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!(cusinenames == null || cusinenames.equals(""))) {
                    filter_status = true;
                } else if (kitchenSearch.getIsBookMarked().equals("1") || kitchenSearch.getIsVegetarian().equals("1") || kitchenSearch.getIsDiscount().equals("1")) {
                    filter_status = true;
                } else if (kitchenSearch.getServiceType().equals(breakfast) || kitchenSearch.getServiceType().equals(lunch) || kitchenSearch.getServiceType().equals(Dinner) || kitchenSearch.getServiceType().equals(Brunch)) {
                    filter_status = true;
                } else if (kitchenSearch.getSortBy().equals("Rating")) {
                    filter_status = true;
                } else if (kitchenSearch.getKitchenType().equals("Home Kitchen") || kitchenSearch.getKitchenType().equals("Restaurant")) {
                    filter_status = true;
                } else if (!(kitchenSearch.getDeliveryFrom().equals("0") && kitchenSearch.getDeliveryTo().equals("60"))) {
                    filter_status = true;
                } else if (!(kitchenSearch.getCostForOne().equals("0"))) {
                    filter_status = true;
                } else if (!(kitchenSearch.getMinimumOrder().equals("0"))) {
                    filter_status = true;
                } else if (!(deliverydatee.equals(CurrentDate))) {
                    filter_status = true;
                } else {
                    filter_status = false;
                }
                if (filter_status) {
                    toolfilter.setImageResource(R.drawable.filter_selected);

                } else {
                    toolfilter.setImageResource(R.drawable.filtericon);
                }
            }
        } else {

            //checking filter status
            //fetching default
            CSB_filter_status = false;
            SharedPreferences shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
            if (!shared.getString("SortBy", "").equalsIgnoreCase("")) {
                String BookingDateTime = DateToddMMyyyy(shared.getString("BookingDateTime", ""));
                if (!shared.getString("MinRadius", "").equalsIgnoreCase("0") || !shared.getString("MaxRadius", "").equalsIgnoreCase("60")) {
                    CSB_filter_status = true;
                } else if (!shared.getString("Service", "").equalsIgnoreCase("")) {
                    CSB_filter_status = true;
                } else if (!shared.getString("Cuisine", "").equalsIgnoreCase("")) {
                    CSB_filter_status = true;
                } else if (!shared.getString("SortBy", "").equalsIgnoreCase("Distance")) {
                    CSB_filter_status = true;
                } else if (!shared.getString("QuickFilter", "").equalsIgnoreCase("Any Gender")) {
                    CSB_filter_status = true;
                } else if (!(DateToddMMyyyy2(date_format).equals(BookingDateTime)) && BookingDateTime != null) {
                    CSB_filter_status = true;
                }
            } else {
                CSB_filter_status = false;
            }

            if (CSB_filter_status) {
                toolfilter.setImageResource(R.drawable.filter_selected);

            } else {
                toolfilter.setImageResource(R.drawable.filtericon);
            }

            //==================================================================================

        }

    }


    public String DateToddMMyyyy(String time) {
        String inputPattern = "MMM dd, yyyy hh:mm a";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String DateToddMMyyyy2(String time) {
        String inputPattern = "MMM dd,yyyy hh:mm a";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public void notificationcount() {
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.executeGetRequest(API.notificationlist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
        oInsertUpdateApi.setRequestSource(RequestSource.notcount);
    }

//    public void nointernet() {
//
//        dash_dialog.show();
//
//    }

//    public void showdiag() {
//
//        //dialog intialization
//        dash_dialog = new Dialog(DashBoard.this);
//        dash_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dash_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dash_dialog.setContentView(R.layout.loading_dialog);
//        dash_dialog.setCancelable(true);
//
//        dash_dialog.show();
//
//    }
//
//    public void dismiss_diag()
//    {
//        dash_dialog.cancel();
//
//    }
}

