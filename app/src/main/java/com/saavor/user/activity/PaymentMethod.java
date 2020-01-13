package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.saavor.user.Model.AddCardReturn;
import com.saavor.user.Model.CardDetailsReturn;
import com.saavor.user.Model.CardModel;
import com.saavor.user.Model.DeleteCardHit;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import morxander.editcard.EditCard;

public class PaymentMethod extends BaseActivity implements View.OnClickListener, View.OnTouchListener, OnResultReceived, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    //, AdapterView.OnItemSelectedListener   AdapterView.OnItemClickListener

    LinearLayout  ll_update, ll_save;
    private Button mAdd, mCancel, mUpdate, mDelete;
    private ImageView mBack;
    EditText mCvv, mNoc, mAddress2, mFname, mLName;
    private Spinner mCardmm, mCardyy;
    private ArrayList<String> arrayFormm = new ArrayList<>();
    private ArrayList<String> arrayForyy = new ArrayList<>();
    Boolean expmm = false;
    Boolean expyy = false;
    String mm, yy;
    EditCard mCardNumber;
    CardDetailsReturn cardDetailsReturn = new CardDetailsReturn();
    TextView titlepayment, cancelcard;
    AddCardReturn addCardReturn ;

    EditText  address1_card, city_card, state_card, zip_card, country_card;

    private Boolean mChoiceback = false;
    private Boolean mFetchKitchen = false;
    private String txt_address;
    private Boolean IsStreet = false;
    private Boolean IsRoute = false;
    private Boolean Is_City = false;
    private Boolean Is_State = false;
    private Boolean Is_Country = false;
    private Boolean IsZipcode = false;
    private Boolean Is_city1 = false;

    private String StreetNumber;
    private String Route;
    private String City;
    private String State;
    private String Country;
    private String ZipCode;
    private String cty1;
    private JSONObject Jsonobject;
    int details = 0;
    public OnResultReceived mOnResultReceived;
    private AutoCompleteTextView autocomplete_card;
    private DeleteCardHit deletecardhit = new DeleteCardHit();
    RelativeLayout LL_autocomplete_card;
    int choose=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        basicfetch();

        mOnResultReceived = this;
        addCardReturn = new AddCardReturn();

        choose = getIntent().getIntExtra("choosecard",0);

        cancelcard = (TextView) findViewById(R.id.cancel_card);
        LL_autocomplete_card = (RelativeLayout) findViewById(R.id.ll_autocomplete_card);
        ll_update = (LinearLayout) findViewById(R.id.LL_del_up);
        ll_save = (LinearLayout) findViewById(R.id.ll_save_carddetails);
        mAdd = (Button) findViewById(R.id.but_add_card);
        mCancel = (Button) findViewById(R.id.but_cancel_card);
        mUpdate = (Button) findViewById(R.id.but_update_card);
        mDelete = (Button) findViewById(R.id.but_delete_card);
        titlepayment = (TextView) findViewById(R.id.title_payment);
        mCardNumber = (EditCard) findViewById(R.id.edt_cardno);

        mCvv = (EditText) findViewById(R.id.edt_card_cvv);
        mNoc = (EditText) findViewById(R.id.edt_card_noc);

        mCardmm = (Spinner) findViewById(R.id.sp_cardmm);
        mCardyy = (Spinner) findViewById(R.id.sp_cardyy);


        mBack = (ImageView) findViewById(R.id.tool_back_carddetails);
        mBack.setOnClickListener(this);

        arrayFormm.add("MM");
        arrayFormm.add("1");
        arrayFormm.add("2");
        arrayFormm.add("3");
        arrayFormm.add("4");
        arrayFormm.add("5");
        arrayFormm.add("6");
        arrayFormm.add("7");
        arrayFormm.add("8");
        arrayFormm.add("9");
        arrayFormm.add("10");
        arrayFormm.add("11");
        arrayFormm.add("12");

        arrayForyy.add("YYYY");
        arrayForyy.add("2018");
        arrayForyy.add("2019");
        arrayForyy.add("2020");
        arrayForyy.add("2021");
        arrayForyy.add("2022");
        arrayForyy.add("2023");
        arrayForyy.add("2024");
        arrayForyy.add("2025");
        arrayForyy.add("2026");
        arrayForyy.add("2027");
        arrayForyy.add("2028");
        arrayForyy.add("2029");
        arrayForyy.add("2030");

        // Creating adapter for spinner
        ArrayAdapter<String> cardmm = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, arrayFormm);
        ArrayAdapter<String> cardyy = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, arrayForyy);

        // Drop down layout style - list view with radio button
        cardmm.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        cardyy.setDropDownViewResource(android.R.layout.simple_selectable_list_item);

        // attaching data adapter to spinner
        mCardmm.setAdapter(cardmm);
        mCardyy.setAdapter(cardyy);

        // Spinner click listener
        mCardmm.setOnItemSelectedListener(this);
        mCardyy.setOnItemSelectedListener(this);
        mCardmm.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }) ;

        mCardyy.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }) ;

        //mAddCard.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);


        autocomplete_card = (AutoCompleteTextView) findViewById(R.id.autocomplete_card);

        mFname = (EditText) findViewById(R.id.edt_fname_acard);
        mLName = (EditText) findViewById(R.id.edt_lname_acard);
        country_card = (EditText) findViewById(R.id.txt_country_card);
        state_card = (EditText) findViewById(R.id.txt_state_card);
        city_card = (EditText) findViewById(R.id.txt_city_card);
        zip_card = (EditText) findViewById(R.id.txt_zip_card);
        address1_card = (EditText) findViewById(R.id.txt_address1_card);
        mAddress2 = (EditText) findViewById(R.id.act_Add2_acard);

       // address1_card.setOnClickListener(this);
      //  zip_card.setOnClickListener(this);
      //  city_card.setOnClickListener(this);
      //  state_card.setOnClickListener(this);
      //  country_card.setOnClickListener(this);
        cancelcard.setOnClickListener(this);

        autocomplete_card.setThreshold(1);
        autocomplete_card.setOnItemClickListener(this);


        mNoc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mNoc.setFocusableInTouchMode(true);

                return false;
            }
        });

        mCvv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mCvv.setFocusableInTouchMode(true);

                return false;
            }
        });

        mFname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mFname.setFocusableInTouchMode(true);

                return false;
            }
        });

        mCardNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mCardNumber.setFocusableInTouchMode(true);

                return false;
            }
        });

        mLName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mLName.setFocusableInTouchMode(true);

                return false;
            }
        });

        mAddress2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mAddress2.setFocusableInTouchMode(true);

                return false;
            }
        });


        autocomplete_card.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txt_address = autocomplete_card.getText().toString();
                autocomplete_card.setAdapter(new PlacesAutoCompleteAdapter(PaymentMethod.this, android.R.layout.simple_list_item_1));
                autocomplete_card.requestFocus();
                InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr2.showSoftInput(autocomplete_card, 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

//                txt_address = mAct_country.getText().toString();

                // TODO Auto-generated method stub
            }
        });


        try {

            details = getIntent().getIntExtra("detailschoice", 0);
            int cardid = getIntent().getIntExtra("cardid", 0);


            if (details == 1) {
                load_dialog.show();
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.executeGetRequest(API.carddetails() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + cardid);
                oInsertUpdateApi.setRequestSource(RequestSource.carddetailsshow);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.tool_back_carddetails:

                try {
                    hideSoftKeyboard(PaymentMethod.this);
                } catch (Exception e) {

                }

                finish();
                break;


            case R.id.but_cancel_card:
                try {
                    hideSoftKeyboard(PaymentMethod.this);
                } catch (Exception e) {

                }
                finish();
                break;

            case R.id.txt_address1_card:

                focusshow();
                break;


            case R.id.sp_cardmm:


                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.sp_cardyy:


                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.txt_zip_card:

                focusshow();
                break;

            case R.id.txt_city_card:

                focusshow();


                break;

            case R.id.txt_state_card:

                focusshow();
                break;

            case R.id.txt_country_card:

                focusshow();

                break;

            case R.id.cancel_card:

                LL_autocomplete_card.setVisibility(View.GONE);
                autocomplete_card.setText("");
                autocomplete_card.clearFocus();
                try {
                    hideSoftKeyboard(PaymentMethod.this);
                } catch (Exception e) {

                }
                break;


            case R.id.but_update_card:

                mNoc.setFocusable(true);
                mNoc.setFocusableInTouchMode(true);
                mNoc.setClickable(true);


                mCardNumber.setFocusable(true);
                mCvv.setFocusable(true);
                mFname.setFocusable(true);
                mLName.setFocusable(true);
//                mAct_country.setFocusable(true);
//                mAct_Address1.setFocusable(true);
                mAddress2.setFocusable(true);
//                mAct_city.setFocusable(true);
//                mAct_state.setFocusable(true);
//                mAct_zip.setFocusable(true);

                mCardmm.setEnabled(true);
                mCardyy.setEnabled(true);

                titlepayment.setText("Card Details");

                ll_update.setVisibility(View.GONE);
                ll_save.setVisibility(View.VISIBLE);

                break;

            case R.id.txt_nav_BookingHistory:

                intent = new Intent(this, BookingHistoryActivity.class);
                this.startActivity(intent);

                break;

            case R.id.but_add_card:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mNoc.getText().toString().equals("")) {
                    displayAlert(this, "Name can't be blank");
                } else if (mCardNumber.getText().toString().equals("")) {
                    displayAlert(this, "Card number can't be blank");
                } else if (!mCardNumber.isValid()) {
                    displayAlert(this, "Card number is not valid");
                } else if (!expmm) {
                    displayAlert(this, "Please select card expiration month");
                } else if (!expyy) {
                    displayAlert(this, "Please select card expiration year");
                } else if (mCvv.getText().toString().equals("")) {
                    displayAlert(this, "Please enter card cvv number");
                } else if ((mCvv.getText().length() < 2)) {
                    displayAlert(this, "cvv must be 3 digit");
                } else if (mNoc.getText().toString().equals("")) {
                    displayAlert(this, "Enter card holder name");
                } else if (mFname.getText().toString().equals("")) {
                    displayAlert(this, "First name can't be blank");
                } else if (mLName.getText().toString().equals("")) {
                    displayAlert(this, "Last name can't be blank");
                } else if (address1_card.getText().toString().equals("") || address1_card.getText().toString().equals("Address Line 1")) {
                    displayAlert(this, "Address1 can't be blank");
                } else if (city_card.getText().toString().equals("") || city_card.getText().toString().equals("City")) {
                    displayAlert(this, "City can't be blank");
                } else if (state_card.getText().toString().equals("") || state_card.getText().toString().equals("State")) {
                    displayAlert(this, "State can't be blank");
                } else if (zip_card.getText().toString().equals("") || zip_card.getText().toString().equals("Zip")) {
                    displayAlert(this, "Zip code can't be blank");
                } else if (country_card.getText().toString().equals("") || country_card.getText().toString().equals("Country")) {
                    displayAlert(this, "Country field cant be blank");
                } else {
                    {
                        mProgressDialog.setMessage("Adding your card....");
                        load_dialog.show();
                        mCardNumber.getCardNumber(); // Get the card number
                        mCardNumber.isValid(); // Is the card number valid
                        mCardNumber.getCardType(); // Get the card type

                        cardModel.setName(mNoc.getText().toString());
                        cardModel.setCardNumber(mCardNumber.getText().toString());
                        cardModel.setExpirationMonth("" + mm);
                        cardModel.setExpirationYear("" + yy);
                        cardModel.setCvc(mCvv.getText().toString());
                        cardModel.setFirstName(mFname.getText().toString());
                        cardModel.setLastName(mLName.getText().toString());
                        cardModel.setCountry(country_card.getText().toString());
                        cardModel.setAddressLine1(address1_card.getText().toString());
                        cardModel.setAddressLine2(mAddress2.getText().toString());
                        cardModel.setCity(city_card.getText().toString());
                        cardModel.setState(state_card.getText().toString());
                        cardModel.setZip(zip_card.getText().toString());
                        cardModel.setSessionToken(basicInformation.getSessionToken().toString());
                        cardModel.setCustomerId(basicInformation.getCoustmerid().toString());
                        cardModel.setUserId(basicInformation.getUserId().toString());
                        cardModel.setEmailId(basicInformation.getEmail().toString());
                        cardModel.setCardType(mCardNumber.getCardType());


                        String jsonString = gson.toJson(cardModel, CardModel.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.addcard(), jsonString);
                        oChangePsswordApi.setRequestSource(RequestSource.addcard);


//                        mCardNumber.setText("");
//                        mCvv.setText("");
//                        mNoc.setText("");
//                        mCardmm.setSelection(0);
//                        mCardyy.setSelection(0);
                    }

                    break;


                }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        showDialog(999);

        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.sp_cardmm) {
            if (position > 0) {
                mm = (String) mCardmm.getSelectedItem();
                expmm = true;
            } else {
                // displayAlert(this, "Select card exp month ");
                expmm = false;


            }
        } else if (spinner.getId() == R.id.sp_cardyy) {
            if (position > 0) {
                yy = (String) mCardyy.getSelectedItem();
                expyy = true;
            } else {
                // displayAlert(this, "select card exp year");
                expyy = false;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void dispatchString(RequestSource from, String what) {


        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(PaymentMethod.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

            try {

                Jsonobject = new JSONObject(what);

                if (from.toString().equalsIgnoreCase("Address")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                JSONArray jsonArray = Jsonobject.getJSONArray("results");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject.getJSONArray("address_components");
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONArray ja_types = jsonArray1.getJSONObject(j).getJSONArray("types");
                                        for (int k = 0; k < ja_types.length(); k++) {
                                            if (ja_types.get(0).equals("street_number")) {
                                                StreetNumber = jsonArray1.getJSONObject(j).getString("long_name");
                                                IsStreet = true;

                                            } else if (ja_types.get(0).equals("route")) {
                                                Route = jsonArray1.getJSONObject(j).getString("long_name");
                                                IsRoute = true;
                                            } else if (ja_types.get(0).equals("locality")) {
                                                City = jsonArray1.getJSONObject(j).getString("long_name");
                                                Is_City = true;

                                            } else if (ja_types.get(0).equals("administrative_area_level_1")) {
                                                State = jsonArray1.getJSONObject(j).getString("long_name");
                                                Is_State = true;

                                            } else if (ja_types.get(0).equals("country")) {
                                                Country = jsonArray1.getJSONObject(j).getString("long_name");
                                                Is_Country = true;

                                            } else if (ja_types.get(0).equals("postal_code")) {
                                                ZipCode = jsonArray1.getJSONObject(j).getString("long_name");
                                                IsZipcode = true;
                                            } else if (ja_types.get(0).equals("neighborhood")) {
                                                cty1 = jsonArray1.getJSONObject(j).getString("long_name");
                                                Is_city1 = true;
                                            }
                                        }
                                        if (Is_City) {
                                            city_card.setText(City);
                                        } else {
                                            city_card.setText("");
                                        }
                                        if (Is_State) {
                                            state_card.setText(State);
                                        } else {
                                            state_card.setText("");
                                        }
                                        if (Is_Country) {
                                            country_card.setText(Country);
                                        } else {
                                            country_card.setText("");
                                        }
                                        if (IsZipcode) {
                                            zip_card.setText(ZipCode);
                                        } else {
                                            zip_card.setText("");
                                        }
                                        if (IsRoute && IsStreet) {
                                            address1_card.setText(StreetNumber + "," + Route);
                                        } else {
                                            address1_card.setText("");
                                        }

                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            } catch (JSONException e1) {
                load_dialog.cancel();
                displayAlert(PaymentMethod.this, "connection error");
                e1.printStackTrace();
            }

            if (from.toString().equalsIgnoreCase("addcard")) {

                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    addCardReturn = gson.fromJson(jsonString, AddCardReturn.class);
                    System.out.println(">>>>" + what);
                    String check = addCardReturn.getReturnCode();
                    final String message = addCardReturn.getReturnMessage();


                    if (check.equals("1")) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();

                                try {
                                    hideSoftKeyboard(PaymentMethod.this);
                                } catch (Exception e) {

                                }

                                basicInformation.setCoustmerid(addCardReturn.getCustomerId().toString());
                                mBasicInformation = gson.toJson(basicInformation);
                                mTabel.putString("basicinformation", mBasicInformation);
                                mTabel.commit();

                                submitdiag();
                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(PaymentMethod.this, message);

                            }
                        });
                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(PaymentMethod.this, message);

                            }
                        });
                    }  else if (check.equals("5")) {
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
                           // redirect(PaymentMethod.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();

                        }
                    });
                }

                Log.e("response", "" + what);
            } else if (from.toString().equalsIgnoreCase("carddetailsshow")) {

                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    cardDetailsReturn = gson.fromJson(jsonString, CardDetailsReturn.class);
                    System.out.println(">>>>" + what);
                    String check = cardDetailsReturn.getReturnCode();
                    final String message = cardDetailsReturn.getReturnMessage();


                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mNoc.setText("" + cardDetailsReturn.getCardDetail().getNameonCard());
                                mCardNumber.setText("" + cardDetailsReturn.getCardDetail().getCardNumber());
//                            mCvv.setText(""+cardDetailsReturn.getCardDetail().getCvc());   //address1_card,city_card, state_card, zip_card,country_card
                                mFname.setText("" + cardDetailsReturn.getCardDetail().getFirstName());
                                mLName.setText("" + cardDetailsReturn.getCardDetail().getLastName());
                                country_card.setText("" + cardDetailsReturn.getCardDetail().getCountry());
                                address1_card.setText("" + cardDetailsReturn.getCardDetail().getAddressLine1());
                                mAddress2.setText("" + cardDetailsReturn.getCardDetail().getAddressLine2());
                                city_card.setText("" + cardDetailsReturn.getCardDetail().getCity());
                                state_card.setText("" + cardDetailsReturn.getCardDetail().getState());
                                zip_card.setText("" + cardDetailsReturn.getCardDetail().getZip());

                                mCardmm.setSelection(arrayFormm.indexOf("" + cardDetailsReturn.getCardDetail().getExpirationMonth()));
                                mCardyy.setSelection(arrayForyy.indexOf("" + cardDetailsReturn.getCardDetail().getExpirationYear()));

                                mNoc.setFocusable(false);
                                mCardNumber.setFocusable(false);
                                mCvv.setFocusable(false);
                                mFname.setFocusable(false);
                                mLName.setFocusable(false);
                                mAddress2.setFocusable(false);
                                mCardmm.setEnabled(false);
                                mCardyy.setEnabled(false);

                                titlepayment.setText("Card Details");

                                ll_update.setVisibility(View.VISIBLE);
                                ll_save.setVisibility(View.GONE);

                                load_dialog.cancel();
                                // displayAlert(PaymentMethod.this, message);
                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                load_dialog.cancel();
                                displayAlert(PaymentMethod.this, message);

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                load_dialog.cancel();
                                displayAlert(PaymentMethod.this, message);

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
                           // redirect(PaymentMethod.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("deletecard")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    todayReturn = gson.fromJson(jsonString, TodayReturn.class);
                    System.out.println(">>>>" + what);
                    String check = todayReturn.getReturnCode();
                    final String message = todayReturn.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                finish();

                            }
                        });


                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(PaymentMethod.this, message);
                                load_dialog.cancel();
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(PaymentMethod.this, message);
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
                            redirect(PaymentMethod.this, "No internet access. Please turn on cellular data or use wifi.");
                        }
                    });
                }

                Log.e("response", "" + what);
            }

            Log.e("response", "" + what);

        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
        try {
            hideSoftKeyboard(PaymentMethod.this);
        } catch (Exception e) {

        }

        finish();
    }


    public void deletehit() {

        mProgressDialog.setMessage("Deleting your card....");
        load_dialog.show();

        deletecardhit.setCardId("" + cardDetailsReturn.getCardDetail().getCardId());
        deletecardhit.setStripeCardId("" + cardDetailsReturn.getCardDetail().getStripeCardId());
        deletecardhit.setSessionToken(basicInformation.getSessionToken().toString());
        deletecardhit.setUserId(basicInformation.getUserId().toString());
        deletecardhit.setCustomerId(basicInformation.getCoustmerid().toString());


        String jsonString = gson.toJson(deletecardhit, DeleteCardHit.class).toString();
        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
        oChangePsswordApi.setRequestSource(RequestSource.deletecard);
        oChangePsswordApi.executePostRequest(API.deletecard(), jsonString);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        System.out.println("++++++" + txt_address);
//        String append = txt_address;
//
//        String Url = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=" + "/" + append;
//        System.out.println("*****" + Url);
//
//        //hitting api
//        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
//        oInsertUpdateApi.setRequestSource(RequestSource.Address);
//        oInsertUpdateApi.executeGetRequest(Url);
//
//        try {
//            hideSoftKeyboard(PaymentMethod.this);
//        } catch (Exception e) {
//
//        }


        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void focusshow() {
        LL_autocomplete_card.setVisibility(View.VISIBLE);

        autocomplete_card.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(autocomplete_card, InputMethodManager.SHOW_IMPLICIT);
    }

    public void submitdiag()
    {
        //dialog intialization
        dialog = new Dialog(PaymentMethod.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.card_diag);
        dialog.setCancelable(false);

        Button okplaced = (Button) dialog.findViewById(R.id.ok_card);

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                finish();
            }
        });

        dialog.show();
    }


    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(PaymentMethod.this);
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

                if (mNoc.getText().toString().equals("")) {
                    displayAlert(PaymentMethod.this, "Name can't be blank");
                } else if (mCardNumber.getText().toString().equals("")) {
                    displayAlert(PaymentMethod.this, "Card number can't be blank");
                } else if (!mCardNumber.isValid()) {
                    displayAlert(PaymentMethod.this, "Card number is not valid");
                } else if (!expmm) {
                    displayAlert(PaymentMethod.this, "Please select card expiration month");
                } else if (!expyy) {
                    displayAlert(PaymentMethod.this, "Please select card expiration year");
                } else if (mCvv.getText().toString().equals("")) {
                    displayAlert(PaymentMethod.this, "Please enter card cvv number");
                } else if ((mCvv.getText().length() < 2)) {
                    displayAlert(PaymentMethod.this, "cvv must be 3 digit");
                } else if (mNoc.getText().toString().equals("")) {
                    displayAlert(PaymentMethod.this, "Enter card holder name");
                } else if (mFname.getText().toString().equals("")) {
                    displayAlert(PaymentMethod.this, "First name Can't be blank");
                } else if (mLName.getText().toString().equals("")) {
                    displayAlert(PaymentMethod.this, "Last name Can't be blank");
                } else if (address1_card.getText().toString().equals("") || address1_card.getText().toString().equals("Address Line 1")) {
                    displayAlert(PaymentMethod.this, "Address1 Can't be blank");
                } else if (city_card.getText().toString().equals("") || city_card.getText().toString().equals("City")) {
                    displayAlert(PaymentMethod.this, "City Can't be blank");
                } else if (state_card.getText().toString().equals("") || state_card.getText().toString().equals("State")) {
                    displayAlert(PaymentMethod.this, "State Can't be blank");
                } else if (zip_card.getText().toString().equals("") || zip_card.getText().toString().equals("Zip")) {
                    displayAlert(PaymentMethod.this, "Zip code Can't be blank");
                } else if (country_card.getText().toString().equals("") || country_card.getText().toString().equals("Country")) {
                    displayAlert(PaymentMethod.this, "Country field cant be blank");
                } else {
                    {
                        mProgressDialog.setMessage("Adding your card....");
                        load_dialog.show();
                        mCardNumber.getCardNumber(); // Get the card number
                        mCardNumber.isValid(); // Is the card number valid
                        mCardNumber.getCardType(); // Get the card type

                        cardModel.setName(mNoc.getText().toString());
                        cardModel.setCardNumber(mCardNumber.getText().toString());
                        cardModel.setExpirationMonth("" + mm);
                        cardModel.setExpirationYear("" + yy);
                        cardModel.setCvc(mCvv.getText().toString());
                        cardModel.setFirstName(mFname.getText().toString());
                        cardModel.setLastName(mLName.getText().toString());
                        cardModel.setCountry(country_card.getText().toString());
                        cardModel.setAddressLine1(address1_card.getText().toString());
                        cardModel.setAddressLine2(mAddress2.getText().toString());
                        cardModel.setCity(city_card.getText().toString());
                        cardModel.setState(state_card.getText().toString());
                        cardModel.setZip(zip_card.getText().toString());
                        cardModel.setSessionToken(basicInformation.getSessionToken().toString());
                        cardModel.setCustomerId(basicInformation.getCoustmerid().toString());
                        cardModel.setUserId(basicInformation.getUserId().toString());
                        cardModel.setEmailId(basicInformation.getEmail().toString());
                        cardModel.setCardType(mCardNumber.getCardType());


                        String jsonString = gson.toJson(cardModel, CardModel.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.addcard(), jsonString);
                        oChangePsswordApi.setRequestSource(RequestSource.addcard);


                    }  } }  });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        dialog.show();

    }

}
