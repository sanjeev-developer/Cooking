package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.saavor.user.Classes.GPSTracker;
import com.saavor.user.Classes.GeocodingLocation;
import com.saavor.user.Model.AddModel;
import com.saavor.user.Model.AddressList;
import com.saavor.user.Model.AddressListt;
import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.CountryList;
import com.saavor.user.Model.SignupReturn;
import com.saavor.user.R;
import com.saavor.user.adapter.ChooseAddAdapter;
import com.saavor.user.adapter.CityAdapter;
import com.saavor.user.adapter.SpinnerAdapter;
import com.saavor.user.adapter.StateSpinner;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.ChefCartActivity;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.saavor.user.activity.DashBoard.Dialogdata;
import static com.saavor.user.activity.DashBoard.countryList;
import static com.saavor.user.activity.DashBoard.localityuser;


public class AddAddress extends BaseActivity implements View.OnClickListener, OnResultReceived, DrawerLayout.DrawerListener, AdapterView.OnItemClickListener {

    // AdapterView.OnItemClickListener

    private EditText mAddress2, mInstruction;
    private Button mHome, mWork, mOther, mSave, mCancel;
    public String txt_address;
    private double addressLat, addressLong;
    private ImageView back;
    Boolean Is_address2 = false;
    public double latitude;
    public double longitude;
    public boolean from_chef = false;
    public Spinner sp_city, sp_country, sp_state;
    String t;
    private boolean fireapi = false;
    private String addtype = "";
    private JSONObject Jsonobject;
    private double kitchen_lat = 0;
    private double kitchen_long = 0;
    private double booking_radius = 0;
    private double chef_lat = 0;
    private double chef_long = 0;
    private double kitchen_del_radius = 0;
    public OnResultReceived mOnResultReceived;
    private EditText mAct_Address1, mAct_zip, act_loc, otheredit;
    private PostApiClient oChangePsswordApi;
    private AutoCompleteTextView autocompletetextview;
    private RelativeLayout LL_autocomplete;
    private LinearLayout LL_city, LL_state, LL_country;
    TextView cancelsearch, txtcity, txtcountry, txtstate, txt_message;
    private ImageView locationIv;
    boolean isedit = false;
    String addid;
    boolean dec = false;
    boolean othertype = false;
    LinearLayout otheraa;
    int country_position, state_position, city_position;
    private ArrayList<AddressList> addlist = new ArrayList<AddressList>();
    int country_index = 0;
    int state_index = 0;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout LL_total, ll_choose_case, ll_confirm, ll_button_view;
    View total;
    String lat = "";
    String lng = "";
    String cityname = "";
    String statename = "";
    String countryname = "";
    String business_type = "";
    boolean fromcart = false;
    boolean fromcartclick = false;
    RecyclerView recyclerView;
    ArrayList<String> citydata = new ArrayList<String>();
    LinearLayout ll_cant_del_loc;
    String get_city, get_state, get_counrty, get_zipcode, get_alternate_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        //fetching basic user info
        basicfetch();
        mOnResultReceived = this;

        //intiliazing shared
        sharedPrefs = getSharedPreferences("ADDPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        gson = new Gson();

        fromcartclick = getIntent().getBooleanExtra("fromcartclick", false);

        LL_total = (LinearLayout) findViewById(R.id.ll_total);
        //locationRl = (RelativeLayout) findViewById(R.id.locationRl);
        // ll_fromcart = (LinearLayout) findViewById(R.id.ll_fromcart);
        total = LL_total;

        LL_city = (LinearLayout) findViewById(R.id.ll_city);
        LL_state = (LinearLayout) findViewById(R.id.ll_state);
        LL_country = (LinearLayout) findViewById(R.id.ll_country);
        ll_cant_del_loc = (LinearLayout) findViewById(R.id.ll_cant_del_loc);

        ll_button_view = (LinearLayout) findViewById(R.id.ll_button_view);
        ll_choose_case = (LinearLayout) findViewById(R.id.ll_choose_case);
        ll_confirm = (LinearLayout) findViewById(R.id.ll_confirm);

        ll_confirm.setOnClickListener(this);

        txt_message = (TextView) findViewById(R.id.txt_message);
        txtcity = (TextView) findViewById(R.id.txt_city);
        txtcountry = (TextView) findViewById(R.id.txt_country);
        txtstate = (TextView) findViewById(R.id.txt_state);

        LL_city.setOnClickListener(this);
        LL_state.setOnClickListener(this);
        LL_country.setOnClickListener(this);


        sp_city = (Spinner) findViewById(R.id.spinner_city);
        sp_country = (Spinner) findViewById(R.id.spinner_country);
        sp_state = (Spinner) findViewById(R.id.spinner_state);


//        sp_city.setOnItemSelectedListener(this);
//        sp_state.setOnItemSelectedListener(this);
//        sp_country.setOnItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rec_addlist_choose);
        // new_locality = (TextView) findViewById(R.id.new_locality);
        cancelsearch = (TextView) findViewById(R.id.cancel_search);
        LL_autocomplete = (RelativeLayout) findViewById(R.id.ll_autocomplete);

        mHome = (Button) findViewById(R.id.but_home_address);
        mWork = (Button) findViewById(R.id.but_work_address);
        mOther = (Button) findViewById(R.id.but_other_address);
        mSave = (Button) findViewById(R.id.btn_save_add);
        mCancel = (Button) findViewById(R.id.btn_cancel_add);
        back = (ImageView) findViewById(R.id.tool_back_add_address);
        //  gpsloc = (ImageView) findViewById(R.id.img_gps_loc);

        mHome.setOnClickListener(this);
        mWork.setOnClickListener(this);
        mOther.setOnClickListener(this);
        back.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        //    gpsloc.setOnClickListener(this);
        mOnResultReceived = this;

        otheredit = (EditText) findViewById(R.id.othertype);
        otheraa = (LinearLayout) findViewById(R.id.ll_other);
        mAddress2 = (EditText) findViewById(R.id.edt_address2);
        mInstruction = (EditText) findViewById(R.id.edt_instruction_address);
        //  mAct_country = (EditText) findViewById(R.id.act_country);
        //    mAct_state = (EditText) findViewById(R.id.act_state);
        //     mAct_city = (EditText) findViewById(R.id.act_city);
        mAct_zip = (EditText) findViewById(R.id.act_zip);
        mAct_Address1 = (EditText) findViewById(R.id.act_address1);
        act_loc = (AutoCompleteTextView) findViewById(R.id.act_loc);
        act_loc.setOnClickListener(this);
        locationIv = (ImageView) findViewById(R.id.locationIv);

        locationIv.setOnClickListener(this);


        mAct_Address1.setFilters(new InputFilter[]{EMOJI_FILTER});
        mAddress2.setFilters(new InputFilter[]{EMOJI_FILTER});
        mAddress2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mAddress2.setFocusableInTouchMode(true);
                LL_autocomplete.setVisibility(View.GONE);

                return false;
            }
        });

        mInstruction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mInstruction.setFocusableInTouchMode(true);
                LL_autocomplete.setVisibility(View.GONE);

                return false;
            }
        });

        mAct_Address1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                LL_autocomplete.setVisibility(View.GONE);

                return false;
            }
        });
//        ll_city.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                mInstruction.setFocusableInTouchMode(true);
//                LL_autocomplete.setVisibility(View.GONE);
//
//                return false;
//            }
//        });
//        ll_state.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                mInstruction.setFocusableInTouchMode(true);
//                LL_autocomplete.setVisibility(View.GONE);
//
//                return false;
//            }
//        });
//        ll_country.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                mInstruction.setFocusableInTouchMode(true);
//                LL_autocomplete.setVisibility(View.GONE);
//
//                return false;
//            }
//        });


        autocompletetextview = (AutoCompleteTextView) findViewById(R.id.autocomplete_textview);
        autocompletetextview.setThreshold(1);
        autocompletetextview.setOnClickListener(this);

        mAct_Address1.setOnClickListener(this);
        //  mAct_city.setOnClickListener(this);
        //   mAct_state.setOnClickListener(this);
        // mAct_country.setOnClickListener(this);
        mAct_zip.setOnClickListener(this);
        cancelsearch.setOnClickListener(this);

        autocompletetextview.setOnItemClickListener(this);

        autocompletetextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_address = autocompletetextview.getText().toString();
                autocompletetextview.setAdapter(new PlacesAutoCompleteAdapter(AddAddress.this, android.R.layout.simple_list_item_1));
                autocompletetextview.requestFocus();
                InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr2.showSoftInput(autocompletetextview, 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        fromcart = getIntent().getBooleanExtra("fromcart", false);
        from_chef = getIntent().getBooleanExtra("from_chef", false);


        if (fromcart) {
            // fetching default
            String data = mDatabase.getString("basickitcheninfo", "");

            if (data.equals("")) {

            } else {
                basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);
                kitchen_lat = basicKitInfo.getKitchen_lat();
                kitchen_long = basicKitInfo.getKitchen_long();
                kitchen_del_radius = basicKitInfo.getKitchendelradius();
            }

        } else if (from_chef) {
            chef_lat = Double.parseDouble(mDatabase.getString("cheflat", ""));
            chef_long = Double.parseDouble(mDatabase.getString("cheflong", ""));
            business_type = mDatabase.getString("businesstype", "");
            booking_radius = Double.parseDouble(mDatabase.getString("booking_radius", ""));
        }


//        if (fromcart) {
//          //  locationRl.setVisibility(View.GONE);
//           // ll_fromcart.setVisibility(View.VISIBLE);
//          //  new_locality.setText(localityuser);
//
//            String Url = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=" + "/" + localityuser;
//            System.out.println("*****" + Url);
//
//            //hitting api
//            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
//            oInsertUpdateApi.setRequestSource(RequestSource.Address);
//            oInsertUpdateApi.executeGetRequest(Url);
//        }


        isedit = getIntent().getBooleanExtra("isedit", false);

        if (isedit) {

            mSave.setText("Update");

            //set data on spinner
            countryname = getIntent().getStringExtra("country");
            txtcountry.setText(countryname);

            statename = getIntent().getStringExtra("state");
            txtstate.setText(statename);

            cityname = getIntent().getStringExtra("city");
            txtcity.setText(cityname);

            mAct_Address1.setText(getIntent().getStringExtra("addline1"));
            mAct_zip.setText(getIntent().getStringExtra("zip"));
            mAddress2.setText(getIntent().getStringExtra("addline2"));
            mInstruction.setText(getIntent().getStringExtra("Instructions"));
            act_loc.setText(getIntent().getStringExtra("locality"));
            lat = getIntent().getStringExtra("lat");
            lng = getIntent().getStringExtra("long");

            if (lat.equals("") && lng.equals("")) {
                dec = false;

            } else {
                dec = true;
            }

            addid = getIntent().getStringExtra("addid");
            addtype = getIntent().getStringExtra("type");

            if (addtype.equals("Home")) {
                mHome.setBackgroundResource(R.drawable.filterpressed);
                mWork.setBackgroundResource(R.drawable.filterback);
                mOther.setBackgroundResource(R.drawable.filterback);

                mHome.setTextColor(getResources().getColor(R.color.white));
                mWork.setTextColor(getResources().getColor(R.color.darkgrey));
                mOther.setTextColor(getResources().getColor(R.color.darkgrey));

            } else if (addtype.equals("Work")) {
                mHome.setBackgroundResource(R.drawable.filterback);
                mWork.setBackgroundResource(R.drawable.filterpressed);
                mOther.setBackgroundResource(R.drawable.filterback);

                mHome.setTextColor(getResources().getColor(R.color.darkgrey));
                mWork.setTextColor(getResources().getColor(R.color.white));
                mOther.setTextColor(getResources().getColor(R.color.darkgrey));

            } else {
                othertype=true;
                otheraa.setVisibility(View.VISIBLE);
                otheredit.setText(addtype);
                mHome.setBackgroundResource(R.drawable.filterback);
                mWork.setBackgroundResource(R.drawable.filterback);
                mOther.setBackgroundResource(R.drawable.filterpressed);

                mHome.setTextColor(getResources().getColor(R.color.darkgrey));
                mWork.setTextColor(getResources().getColor(R.color.darkgrey));
                mOther.setTextColor(getResources().getColor(R.color.white));
            }


            if (countryList == null || countryList.getCountries().isEmpty()) {
                mProgressDialog.setMessage("Loading please wait...");
                load_dialog.show();
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.countrylist);
                oInsertUpdateApi.executeGetRequest(API.countrylist());
            } else {


                ArrayList<String> countrydata = new ArrayList<String>();
                for (int i = 0; i < DashBoard.countryList.getCountries().size(); i++) {
                    countrydata.add(DashBoard.countryList.getCountries().get(i).getCountryName().toString());
                    if (DashBoard.countryList.getCountries().get(i).getCountryName().equals(countryname)) {
                        country_index = i;
                        country_position = i;
                    }
                }

                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(AddAddress.this, countrydata);
                sp_country.setAdapter(spinnerAdapter);


                ArrayList<String> statedata = new ArrayList<String>();

                for (int i = 0; i < DashBoard.countryList.getCountries().get(country_index).getStateList().size(); i++) {
                    statedata.add(DashBoard.countryList.getCountries().get(country_index).getStateList().get(i).getStateName().toString());

                    if (DashBoard.countryList.getCountries().get(country_index).getStateList().get(i).getStateName().equals(statename)) {
                        state_index = i;
                        state_position = i;
                    }
                }

                StateSpinner stateSpinner = new StateSpinner(AddAddress.this, statedata);
                sp_state.setAdapter(stateSpinner);


                ArrayList<String> citydata = new ArrayList<String>();

                String myString = DashBoard.countryList.getCountries().get(country_index).getStateList().get(state_index).getCities().toString();
                String[] aSplit = myString.split(",");

                for (int i = 0; i < aSplit.length; i++) {
                    citydata.add(aSplit[i]);
                }

                CityAdapter cityAdapter = new CityAdapter(AddAddress.this, citydata);
                sp_city.setAdapter(cityAdapter);

            }
        } else {


            try {
                if (countryList == null || countryList.getCountries().isEmpty()) {
                    mProgressDialog.setMessage("Loading please wait...");
                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.setRequestSource(RequestSource.countrylist);
                    oInsertUpdateApi.executeGetRequest(API.countrylist());
                } else {
                    //continue
                    ArrayList<String> countrydata = new ArrayList<String>();

                    for (int i = 0; i < DashBoard.countryList.getCountries().size(); i++) {
                        countrydata.add(DashBoard.countryList.getCountries().get(i).getCountryName().toString());
                    }
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(AddAddress.this, countrydata);
                    sp_country.setAdapter(spinnerAdapter);

                    if (fromcart || from_chef) {
                        load_dialog.show();
                        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                        oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                        oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);

                    }
                }

            } catch (Exception e) {

//            mProgressDialog.setMessage("Loading please wait...");
//            load_dialog.show();
//            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
//            oInsertUpdateApi.setRequestSource(RequestSource.countrylist);
//            oInsertUpdateApi.executeGetRequest(API.countrylist());
            }

        }

        if (fromcart) {
            act_loc.setText(localityuser);
            String Url = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=" + "/" + localityuser;
            System.out.println("*****" + Url);

            lat = "0";
            lng = "0";

            //hitting api
            GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
            oInsertUpdateApi.setRequestSource(RequestSource.Address);
            oInsertUpdateApi.executeGetRequest(Url);
        }


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_save_add:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fetchKitchen();
                break;

            case R.id.cancel_search:
                LL_autocomplete.setVisibility(View.GONE);
                autocompletetextview.setText("");

                try {
                    hideSoftKeyboard(AddAddress.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tool_back_add_address:

                if (fromcart || fromcartclick || from_chef) {
                    finish();
                } else {
                    intent = new Intent(this, AddressBook.class);
                    this.startActivity(intent);
                }

                break;

            case R.id.btn_cancel_add:

                if (fromcart || fromcartclick || from_chef) {
                    finish();
                } else {
                    intent = new Intent(this, AddressBook.class);
                    this.startActivity(intent);
                }

                break;

            case R.id.but_home_address:

                mHome.setBackgroundResource(R.drawable.filterpressed);
                mWork.setBackgroundResource(R.drawable.filterback);
                mOther.setBackgroundResource(R.drawable.filterback);

                mHome.setTextColor(getResources().getColor(R.color.white));
                mWork.setTextColor(getResources().getColor(R.color.darkgrey));
                mOther.setTextColor(getResources().getColor(R.color.darkgrey));

                addtype = "Home";
                othertype = false;

                otheraa.setVisibility(View.GONE);

                break;

            case R.id.locationIv:

                try {
                    // Acquire a reference to the system Location Manager
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    // Define a listener that responds to location updates
                    LocationListener locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            // Called when a new location is found by the network location provider.
                            addressLat = location.getLatitude();
                            addressLong = location.getLongitude();

                            lat = "" + addressLat;
                            lng = "" + addressLong;
                            //  Log.i(Constants.TAG,"Your Location is:" + addressLat + "--" + addressLong);

                            if (lat.equals("") && lng.equals("")) {
                                dec = false;
                            } else {
                                dec = true;
                            }
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onProviderDisabled(String provider) {
                        }
                    };
                    // Register the listener with the Location Manager to receive location updates
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getAdressDetails();

                break;
            case R.id.act_loc:

                autocompletetextview.setAdapter(new PlacesAutoCompleteAdapter(AddAddress.this, android.R.layout.simple_list_item_1));
                autocompletetextview.setText("");
                autocompletetextview.requestFocus();
                LL_autocomplete.setVisibility(View.VISIBLE);
                // headerRL.setVisibility(View.GONE);
                InputMethodManager mgr11 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr11.showSoftInput(autocompletetextview, 0);
                break;

            case R.id.but_work_address:


                mHome.setBackgroundResource(R.drawable.filterback);
                mWork.setBackgroundResource(R.drawable.filterpressed);
                mOther.setBackgroundResource(R.drawable.filterback);

                mHome.setTextColor(getResources().getColor(R.color.darkgrey));
                mWork.setTextColor(getResources().getColor(R.color.white));
                mOther.setTextColor(getResources().getColor(R.color.darkgrey));

                addtype = "Work";
                othertype = false;
                otheraa.setVisibility(View.GONE);

                break;

            case R.id.but_other_address:


                mHome.setBackgroundResource(R.drawable.filterback);
                mWork.setBackgroundResource(R.drawable.filterback);
                mOther.setBackgroundResource(R.drawable.filterpressed);

                mHome.setTextColor(getResources().getColor(R.color.darkgrey));
                mWork.setTextColor(getResources().getColor(R.color.darkgrey));
                mOther.setTextColor(getResources().getColor(R.color.white));

                otheraa.setVisibility(View.VISIBLE);
                othertype = true;

                break;

            case R.id.autocomplete_textview:
                txt_address = autocompletetextview.getText().toString();
                autocompletetextview.setAdapter(new PlacesAutoCompleteAdapter(AddAddress.this, android.R.layout.simple_list_item_1));
                autocompletetextview.requestFocus();
                InputMethodManager mgr10 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr10.showSoftInput(autocompletetextview, 0);

                break;

            case R.id.ll_city:


                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (txtstate.getText().toString().equals("Select state")) {
                    displayAlert(AddAddress.this, "Please select state first");
                } else {
                    sp_city.performClick();
                }

                break;

            case R.id.ll_country:


                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(">>run>>>");

                sp_country.performClick();
                break;

            case R.id.ll_state:


                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (txtcountry.getText().toString().equals("Select country")) {
                    displayAlert(AddAddress.this, "Please select country first");
                } else {

                    sp_state.performClick();
                }
                break;

            case R.id.ll_confirm:


                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fetchKitchen();
                break;
        }
    }

    // getting all result after api hit
    @Override
    public void dispatchString(RequestSource from, final String what) {

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(AddAddress.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {

            if (from.toString().equalsIgnoreCase("Address")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Jsonobject = new JSONObject(what);
                            JSONArray jsonArray = Jsonobject.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = jsonObject.getJSONArray("address_components");
                                String formatted_address = jsonObject.getString("formatted_address");

                                act_loc.setText(formatted_address);

                                JSONObject geometryObject = jsonObject.getJSONObject("geometry");
                                JSONObject locationObject = geometryObject.getJSONObject("location");
                                lat = locationObject.getString("lat");
                                lng = locationObject.getString("lng");


                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONArray ja_types = jsonArray1.getJSONObject(j).getJSONArray("types");
                                    for (int k = 0; k < ja_types.length(); k++) {

                                        if (ja_types.get(0).equals("locality")) {
                                            get_city = jsonArray1.getJSONObject(j).getString("long_name");


                                        } else if (ja_types.get(0).equals("administrative_area_level_1")) {
                                            get_state = jsonArray1.getJSONObject(j).getString("long_name");


                                        } else if (ja_types.get(0).equals("country")) {
                                            get_counrty = jsonArray1.getJSONObject(j).getString("long_name");


                                        } else if (ja_types.get(0).equals("postal_code")) {
                                            get_zipcode = jsonArray1.getJSONObject(j).getString("long_name");
                                            mAct_zip.setText(get_zipcode);

                                        } else if (ja_types.get(0).equals("neighborhood")) {
                                            get_alternate_city = jsonArray1.getJSONObject(j).getString("long_name");
                                        }
                                    }
                                }

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(autocompletetextview.getWindowToken(), 0);

                                if (lat == null || lat.equals("") || lng == null || lng.equals("")) {
                                    dec = false;

                                    if (fromcart) {
                                        GeocodingLocation locationAddress = new GeocodingLocation();
                                        locationAddress.getAddressFromLocation(localityuser, getApplicationContext(), new GeocoderHandler());
                                        Log.i("saavor", "loc 2" + act_loc.getText().toString());
                                    } else {
                                        GeocodingLocation locationAddress = new GeocodingLocation();
                                        locationAddress.getAddressFromLocation(act_loc.getText().toString(), getApplicationContext(), new GeocoderHandler());
                                        Log.i("saavor", "loc 2" + act_loc.getText().toString());
                                    }

                                } else {
                                    dec = true;
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (from.toString().equalsIgnoreCase("saveaddress")) {
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

                                submitdiag();

                                //   displayAlert(AddAddress.this, "" + message);
                                stopService(new Intent(AddAddress.this, GPSTracker.class));

                                //saving address in database
                                gson = new Gson();

                                // System.out.println(">>>>."+mLocation.getText().toString());
                                String b = mAct_Address1.getText().toString() + ", " + txtcity.getText().toString() + ", " + txtstate.getText().toString() + ", " + txtcountry.getText();

                                //saving address in database
                                if (addtype.equals("Home")) {
                                    String fetchhomearray = sharedPrefs.getString("AddressHome", null);


                                    if (fetchhomearray == null) {
                                        homeaddress.add(b);

                                        String json = gson.toJson(homeaddress);
                                        editor.putString("AddressHome", json);
                                        editor.commit();
                                    } else {
                                        Type type = new TypeToken<ArrayList<String>>() {
                                        }.getType();
                                        homeaddress = gson.fromJson(fetchhomearray, type);

                                        homeaddress.add(b);

                                        String json = gson.toJson(homeaddress);
                                        editor.putString("AddressHome", json);
                                        editor.commit();
                                    }

                                } else if (addtype.equals("Work")) {

                                    String fetchhomearray = sharedPrefs.getString("AddressWork", null);


                                    if (fetchhomearray == null) {
                                        workaddress.add(b);

                                        String json = gson.toJson(workaddress);
                                        editor.putString("AddressWork", json);
                                        editor.commit();
                                    } else {
                                        Type type = new TypeToken<ArrayList<String>>() {
                                        }.getType();
                                        workaddress = gson.fromJson(fetchhomearray, type);
                                        workaddress.add(b);

                                        String json = gson.toJson(workaddress);
                                        editor.putString("AddressWork", json);
                                        editor.commit();
                                    }
                                }


                                Dialogdata.clear();
                                mDatabase = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                mTabel = mDatabase.edit();
                                mTabel.remove("dialogdata");
                                mTabel.commit();


                                try {
                                    hideSoftKeyboard(AddAddress.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(AddAddress.this, message);


                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(AddAddress.this, message);

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
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);
            } else if (from.toString().equalsIgnoreCase("countrylist")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    DashBoard.countryList = new CountryList();
                    DashBoard.countryList = gson.fromJson(jsonString, CountryList.class);
                    System.out.println(">>>>" + what);
                    String check = DashBoard.countryList.getReturnCode();
                    final String message = DashBoard.countryList.getReturnMessage();


                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();

                                if (isedit) {

                                    ArrayList<String> countrydata = new ArrayList<String>();


                                    for (int i = 0; i < DashBoard.countryList.getCountries().size(); i++) {
                                        countrydata.add(DashBoard.countryList.getCountries().get(i).getCountryName().toString());
                                        if (DashBoard.countryList.getCountries().get(i).getCountryName().equals(countryname)) {
                                            country_index = i;
                                            country_position = i;
                                        }
                                    }

                                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(AddAddress.this, countrydata);
                                    sp_country.setAdapter(spinnerAdapter);


                                    ArrayList<String> statedata = new ArrayList<String>();

                                    for (int i = 0; i < DashBoard.countryList.getCountries().get(country_index).getStateList().size(); i++) {
                                        statedata.add(DashBoard.countryList.getCountries().get(country_index).getStateList().get(i).getStateName().toString());

                                        if (DashBoard.countryList.getCountries().get(country_index).getStateList().get(i).getStateName().equals(statename)) {
                                            state_index = i;
                                            state_position = i;
                                        }
                                    }

                                    StateSpinner stateSpinner = new StateSpinner(AddAddress.this, statedata);
                                    sp_state.setAdapter(stateSpinner);


                                    ArrayList<String> citydata = new ArrayList<String>();

                                    String myString = DashBoard.countryList.getCountries().get(country_index).getStateList().get(state_index).getCities().toString();
                                    String[] aSplit = myString.split(",");

                                    for (int i = 0; i < aSplit.length; i++) {
                                        citydata.add(aSplit[i]);
                                    }

                                    CityAdapter cityAdapter = new CityAdapter(AddAddress.this, citydata);
                                    sp_city.setAdapter(cityAdapter);


                                } else {
                                    ArrayList<String> countrydata = new ArrayList<String>();

                                    for (int i = 0; i < DashBoard.countryList.getCountries().size(); i++) {
                                        countrydata.add(DashBoard.countryList.getCountries().get(i).getCountryName().toString());
                                    }

                                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(AddAddress.this, countrydata);
                                    sp_country.setAdapter(spinnerAdapter);
                                }


                                if (fromcart || from_chef) {
                                    load_dialog.show();
                                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                                    oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                                    oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);

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
                            // redirect(AddAddress.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
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

                                load_dialog.cancel();
                                ChooseAddAdapter chooseAddAdapter = new ChooseAddAdapter(addlist, AddAddress.this);
                                mLayoutManager = new LinearLayoutManager(AddAddress.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(chooseAddAdapter);

                                ll_button_view.setVisibility(View.GONE);
                                ll_confirm.setVisibility(View.VISIBLE);
                                ll_choose_case.setVisibility(View.VISIBLE);
                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                mProgressDialog.setMessage("Fetching your address list");
                                ll_button_view.setVisibility(View.GONE);
                                ll_confirm.setVisibility(View.VISIBLE);
                                ll_choose_case.setVisibility(View.GONE);
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
                                intent = new Intent(AddAddress.this, MainActivity.class);
                                intent.putExtra("Sessionexp", 1);
                                AddAddress.this.startActivity(intent);
                            }
                        });
                    }

                } catch (Exception e) {
                    System.out.println(">>>>" + e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            nointernet();
                            // redirect(DashBoard.this, "No internet access. Please turn on cellular data or use wifi.");
                        }
                    });
                }
            }
        }
    }


    // checking validation for checking address
    public void fetchKitchen()
    {
        if (act_loc.getText().toString().equals("")) {
            displayAlert(this, "Location can't be blank");
        } else if (mAct_Address1.getText().toString().equals("")) {
            displayAlert(this, "Address1 Can't be blank");
        } else if (txtcity.getText().toString().equals("Select city") || txtcity.getText().toString().equals("")) {
            displayAlert(this, "Select your city first");
        } else if (txtstate.getText().toString().equals("Select state") || txtstate.getText().toString().equals("")) {
            displayAlert(this, "Select your state first");
        } else if (mAct_zip.getText().toString().equals("") || mAct_zip.getText().toString().equals("Zip")) {
            displayAlert(this, "Zip code Can't be blank");
        } else if (txtcountry.getText().toString().equals("Select country") || txtcountry.getText().toString().equals("")) {
            displayAlert(this, "Select your country first");
        } else if (othertype) {
            if (otheredit.getText().toString().equals("")) {
                displayAlert(this, "Please enter other address type");
            } else {

                addtype = otheredit.getText().toString();
                hitaddress();
            }
        } else if (addtype == "") {
            displayAlert(this, "Please Select Address Type");
        } else {
            hitaddress();
        }
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("++++++" + txt_address);
        String append = txt_address;
        act_loc.setText(txt_address);

        String Url = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=" + "/" + append;
        System.out.println("*****" + Url);

        lat = "0";
        lng = "0";

        //hitting api
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.setRequestSource(RequestSource.Address);
        oInsertUpdateApi.executeGetRequest(Url);
    }

    @Override
    public void onBackPressed()
    {
        // do nothing.
        if (fromcart || fromcartclick) {
            finish();
        } else {
            intent = new Intent(this, AddressBook.class);
            this.startActivity(intent);
        }
    }

    //function to fetch address data from it lat and long
    private void getAdressDetails() {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(addressLat, addressLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                //  Log.i(Constants.TAG,"address"+address);
                act_loc.setText("" + address);
            } else {
                // utilities.displayAlert(KitchenDetailsActivity.this,"Unable to access location!Please try after some time!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //setting value in country adapter
    public void setvalue(String s, int position)
    {
        sp_country.requestFocus();

        if (s == null || s.equals("")) {
            txtcountry.setText("Select country");
            txtstate.setText("Select state");
            txtcity.setText("Select city");

        } else {

            txtcountry.setText(s);
            country_position = position;

            //clearing state and city
            txtstate.setText("Select state");
            txtcity.setText("Select city");

            ArrayList<String> statedata = new ArrayList<String>();

            for (int i = 0; i < DashBoard.countryList.getCountries().get(position).getStateList().size(); i++) {
                statedata.add(DashBoard.countryList.getCountries().get(position).getStateList().get(i).getStateName().toString());
            }

            StateSpinner spinnerAdapter = new StateSpinner(AddAddress.this, statedata);
            sp_state.setAdapter(spinnerAdapter);
        }
    }


    //setting value in state adapter
    public void stateselected(String s, int position) {


        sp_state.requestFocus();

        if (s == null || s.equals("")) {
            //clearing state and city
            txtstate.setText("Select state");
            txtcity.setText("Select city");
        } else {
            txtstate.setText(s);
            state_position = position;

            //clearing state and city
            txtcity.setText("Select city");

            citydata = new ArrayList<String>();

            String myString = DashBoard.countryList.getCountries().get(country_position).getStateList().get(state_position).getCities().toString();
            String[] aSplit = myString.split(",");

            for (int i = 0; i < aSplit.length; i++) {
                citydata.add(aSplit[i]);
            }

            CityAdapter spinnerAdapter = new CityAdapter(AddAddress.this, citydata);
            sp_city.setAdapter(spinnerAdapter);
        }
    }


    //setting value in city adapter
    public void cityselected(String s, int position) {
        sp_city.requestFocus();

        if (s == null || s.equals("")) {
            txtcity.setText("Select city");
        } else {
            txtcity.setText(s);
            city_position = position;
            CityAdapter spinnerAdapter = new CityAdapter(AddAddress.this, citydata);
            sp_city.setAdapter(spinnerAdapter);
        }
    }


    //checking wether the user location is in the radius of kitchen
    public void sendrecadd(String s, String locality, String lati, String longi) {

        boolean kitchen_bool = false;
        boolean chef_bool = false;


        Location locationA = new Location("point A");
        Location locationB = new Location("point B");

        if (fromcart) {
            locationA.setLatitude(kitchen_lat);
            locationA.setLongitude(kitchen_long);

        } else if (from_chef) {
            locationA.setLatitude(chef_lat);
            locationA.setLongitude(chef_long);
        }

        locationB.setLatitude(Double.parseDouble(lati));
        locationB.setLongitude(Double.parseDouble(longi));

        float distance = locationA.distanceTo(locationB);

        double miles = distance * 0.00062137119;
        System.out.println("Miles: " + miles);
        System.out.println("meter" + distance);

        if (fromcart) {
            if (kitchen_del_radius <= miles) {
                ll_cant_del_loc.setVisibility(View.VISIBLE);
                kitchen_bool = true;

            } else {
                ll_cant_del_loc.setVisibility(View.GONE);
                kitchen_bool = false;
            }

            if (!kitchen_bool) {

                finish();
                Chart.setnewadd(s, locality);

            }

        } else if (from_chef) {

            if (booking_radius <= miles) {

                txt_message.setText("This " + business_type + " is not available at your location");
                ll_cant_del_loc.setVisibility(View.VISIBLE);
                chef_bool = true;

            } else {
                ll_cant_del_loc.setVisibility(View.GONE);
                chef_bool = false;
            }

            if (!chef_bool) {

                finish();
                ChefCartActivity.setnewadd(s, locality);
            }
        }
    }


    //fetching lat and long on the behalf of address
    public class GeocoderHandler extends android.os.Handler {

        @Override
        public void handleMessage(Message message) {
            double latt, longi;
            String address;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    latt = bundle.getDouble("lat");
                    longi = bundle.getDouble("long");
                    address = bundle.getString("address");

                    lat = "" + latt;
                    lng = "" + longi;

                    if (lat.equals("") && lng.equals("")) {
                        dec = false;
                    } else {
                        dec = true;
                    }


                    break;

                default:

            }
        }
    }


    //alert to show gps disconnected
    private void showGPSDisabledAlertToUser() {
        //dialog intialization
        dialog = new Dialog(AddAddress.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_gps_layout);
        dialog.setCancelable(false);

        Button settings = (Button) dialog.findViewById(R.id.gps_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                AddAddress.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        dialog.show();
    }

    public void hitaddress() {
        if (lat == null || lat.equals("") || lng == null || lng.equals("")) {

            //dialog intialization
            dialog = new Dialog(AddAddress.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.alert_diag);
            dialog.setCancelable(false);

            Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
            TextView alertext = (TextView) dialog.findViewById(R.id.text_alert);

            alertext.setText("unable to fetch your location please try again after sometime");

            okplaced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                    finish();

                }
            });

            dialog.show();

        } else {

            if (from_chef || fromcart) {

                boolean kitchen_bool = false;
                boolean chef_bool = false;


                Location locationA = new Location("point A");
                Location locationB = new Location("point B");

                if (fromcart) {
                    locationA.setLatitude(kitchen_lat);
                    locationA.setLongitude(kitchen_long);

                } else if (from_chef) {
                    locationA.setLatitude(chef_lat);
                    locationA.setLongitude(chef_long);
                }

                if (lat == null || lat.equals("0") || lat.equals("")) {
                    displayAlert(AddAddress.this, "Please select your location again");
                } else {

                    locationB.setLatitude(Double.parseDouble(lat));
                    locationB.setLongitude(Double.parseDouble(lng));

                    float distance = locationA.distanceTo(locationB);

                    double miles = distance * 0.00062137119;
                    System.out.println("Miles: " + miles);
                    System.out.println("meter" + distance);

                    if (fromcart) {
                        if (kitchen_del_radius <= miles) {
                            ll_cant_del_loc.setVisibility(View.VISIBLE);
                            kitchen_bool = true;

                        } else {
                            ll_cant_del_loc.setVisibility(View.GONE);
                            kitchen_bool = false;
                        }

                        if (!kitchen_bool) {

                            hitting_saveadddress();

                        }

                    } else if (from_chef) {

                        if (booking_radius <= miles) {
                            ll_cant_del_loc.setVisibility(View.VISIBLE);
                            chef_bool = true;

                        } else {
                            ll_cant_del_loc.setVisibility(View.GONE);
                            chef_bool = false;
                        }

                        if (!chef_bool) {

                            hitting_saveadddress();
                        }
                    }
                }

            } else {

                hitting_saveadddress();

            }

        }
    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE) {
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }
    }

    // dailog after submit successfull address on server
    public void submitdiag() {
        //dialog intialization
        dialog = new Dialog(AddAddress.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.adress_added_diag);
        dialog.setCancelable(false);

        Button okplaced = (Button) dialog.findViewById(R.id.ok_add);

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                finish();

                if (fromcart) {

                    if (mAddress2.getText().toString() == null || mAddress2.getText().toString().equals("")) {
                        Chart.setnewadd(mAct_Address1.getText().toString() + ", " + txtcity.getText().toString() + ", " + txtstate.getText().toString() + ", " + txtcountry.getText().toString() + ", " + mAct_zip.getText().toString(), act_loc.getText().toString());
                    } else {
                        Chart.setnewadd(mAct_Address1.getText().toString() + ", " + mAddress2.getText().toString() + ", " + txtcity.getText().toString() + ", " + txtstate.getText().toString() + ", " + txtcountry.getText().toString() + ", " + mAct_zip.getText().toString(), act_loc.getText().toString());
                    }

                } else if (from_chef) {

                    if (mAddress2.getText().toString() == null || mAddress2.getText().toString().equals("")) {
                        ChefCartActivity.setnewadd(mAct_Address1.getText().toString() + ", " + txtcity.getText().toString() + ", " + txtstate.getText().toString() + ", " + txtcountry.getText().toString() + ", " + mAct_zip.getText().toString(), act_loc.getText().toString());
                    } else {
                        ChefCartActivity.setnewadd(mAct_Address1.getText().toString() + ", " + mAddress2.getText().toString() + ", " + txtcity.getText().toString() + ", " + txtstate.getText().toString() + ", " + txtcountry.getText().toString() + ", " + mAct_zip.getText().toString(), act_loc.getText().toString());
                    }
                }
            }
        });
        dialog.show();
    }


    //no internet dialog
    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(AddAddress.this);
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
                fetchKitchen();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                AddAddress.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        dialog.show();
    }

    //hitting api for saving address
    public void hitting_saveadddress() {
        load_dialog.show();
        addModel.setAddressType(addtype);
        addModel.setAddressLine1(mAct_Address1.getText().toString());
        addModel.setAddressLine2(mAddress2.getText().toString());
        addModel.setCityName(txtcity.getText().toString());
        addModel.setCountryName(txtcountry.getText().toString());
        addModel.setStateName(txtstate.getText().toString());
        addModel.setUserId(basicInformation.getUserId().toString());
        addModel.setZipCode(mAct_zip.getText().toString());
        addModel.setSessionToken(basicInformation.getSessionToken().toString());
        addModel.setIsDefault("0");

        if (isedit) {
            addModel.setAddressId("" + addid);
        } else {
            addModel.setAddressId("0");
        }

        addModel.setLocation("");
        addModel.setCreateDate(date_format);
        addModel.setLatitude(lat);
        addModel.setLongitude(lng);
        addModel.setLocality(act_loc.getText().toString());
        addModel.setInstructions(mInstruction.getText().toString());

        String jsonString = gson.toJson(addModel, AddModel.class).toString();
        oChangePsswordApi = new PostApiClient(mOnResultReceived);
        oChangePsswordApi.executePostRequest(API.addaddressapi(), jsonString);
        oChangePsswordApi.setRequestSource(RequestSource.saveaddress);
    }
}
