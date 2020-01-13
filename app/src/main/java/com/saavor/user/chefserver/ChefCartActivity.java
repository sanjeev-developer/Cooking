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
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saavor.user.Model.AddressList;
import com.saavor.user.Model.AddressListt;
import com.saavor.user.Model.PromoCodeValidate;
import com.saavor.user.Model.Slots;
import com.saavor.user.R;
import com.saavor.user.activity.AddAddress;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.CompletePayment;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.activity.SignUp;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.adapter.BookingDetailsAdapter;
import com.saavor.user.chefserver.adapter.ItemsInCartAdapter;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.saavor.user.views.MaterialSpinner;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.saavor.user.activity.DashBoard.finaladdress;
import static com.saavor.user.activity.DashBoard.localityuser;

public class ChefCartActivity extends BaseActivity implements View.OnClickListener, OnResultReceived {


    private EditText et_no_guest,other_event;
    private EditText et_full_name;
    private EditText et_contact_number;
    public static EditText et_Address;
    private EditText etCookingInstructions;
    private EditText et_promo_code;
    private ArrayList<AddressList> addlist = new ArrayList<AddressList>();
    String MenuDishId;
    private ImageView action_back;

    ArrayList<String> aLDishName = new ArrayList<String>();
    ArrayList<String> aLDishId = new ArrayList<String>();

    RecyclerView rv_items_in_cart;
    private RecyclerView.LayoutManager mLayoutManager;

    ItemsInCartAdapter oItemsInCartAdapter;

    private LinearLayout llEmptyCart;

    private Button btnCash;
    private Button btn10Per;
    private Button btn15Per;
    private Button btn20Per;
    private Button btnCustom;

    private RecyclerView rvBookingDetails;

    private BookingDetailsAdapter bookingDetailsAdapter;

    private TextView txtSalesTaxValue;
    private TextView txtSalesTax;
    private TextView txtCookingCharges;
    private TextView txtDiscount;
    private TextView txtTip;
    private TextView txtTotal;
    private TextView txtDiscountText;
    private TextView txtActionBack;


    private LinearLayout ll_other_event;
    private LinearLayout llSalesTax;
    private LinearLayout llDiscount;
    private LinearLayout llPromoCode;
    private LinearLayout llApplyPromoCode;
    private double deductDiscount;

    private Context mContext;
    private OnResultReceived mOnResultReceived;
    private Gson oGson;
    private String ServerResult;

    private String TipAmount = "";

    private EditText input;
    private String m_Text = "";
    private Button btn_payment_method;
    private String BookingSlots = "";
    private String PromoCodeDiscount = "0";

    private ImageView img_change_address;
    private LinearLayout ll_CartItems;
    boolean check_other_event =false;

    MaterialSpinner sp_Events;
    TextView txt_Events;

    private long lastTappedTimeInMillis = 0;

    double CookingCharges = 0;
    double prevousDiscount = 0;
    DecimalFormat df;
    String businesstype = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        businesstype = getIntent().getExtras().getString("BusinessType");
        InitializeInterface();


    }

    private void InitializeInterface() {
        basicfetch();

        mContext = this;
        mOnResultReceived = this;
        oGson = new Gson();

        df = new DecimalFormat("####0.00");

        sp_Events = (MaterialSpinner) findViewById(R.id.sp_Events);
        txt_Events = (TextView) findViewById(R.id.txt_Events);
        txt_Events.setOnClickListener(this);

        ArrayList<String> arrayList = getIntent().getExtras().getStringArrayList("EventsList");
        arrayList.add("Other");

        String[] arCuisines = arrayList.toArray(new String[arrayList.size()]);
      //  arCuisines[arCuisines.length]="other";
        sp_Events.setAdapter(new CustomSpinnerAdapter(mContext, 1, R.layout.list_item_textview, arCuisines));

       /* et_event_name = (EditText) findViewById(R.id.et_event_name);
        et_event_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_event_name.setFocusableInTouchMode(true);
                return false;
            }
        });*/
        ll_other_event= (LinearLayout) findViewById(R.id.ll_other_event);
        other_event= (EditText) findViewById(R.id.other_event);
        et_no_guest = (EditText) findViewById(R.id.et_no_guest);
        et_no_guest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_no_guest.setFocusableInTouchMode(true);
                return false;
            }
        });
        et_full_name = (EditText) findViewById(R.id.et_full_name);
        et_full_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_full_name.setFocusableInTouchMode(true);
                return false;
            }
        });
        et_contact_number = (EditText) findViewById(R.id.et_contact_number);
        et_contact_number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_contact_number.setFocusableInTouchMode(true);
                return false;
            }
        });

        ll_CartItems = (LinearLayout) findViewById(R.id.ll_CartItems);
        if (DashBoard.UserType.equalsIgnoreCase("Chef")) {
            ll_CartItems.setVisibility(View.VISIBLE);
        } else {
            ll_CartItems.setVisibility(View.GONE);
        }

        if (basicInformation.getMobileNumber() == null || basicInformation.getMobileNumber().equals("")|| basicInformation.getMobileNumber().equals("+1")) {
            et_contact_number.setText("");
        } else {
            et_contact_number.setText(basicInformation.getMobileNumber().substring(3));
        }

       /* //et_contact_number.setText(basicInformation.getMobileNumber().replace("+1",""));
        et_contact_number.addTextChangedListener(new TextWatcher() {

            //we need to know if the user is erasing or inputting some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length() - et_contact_number.getSelectionStart();
                //we check if the user is inputting or erasing a character
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                String phone = string.replaceAll("[^\\d]", "");

                //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                if (!editedFlag) {

                    //we start verifying the worst case, many characters mask need to be added
                    //example: 999999999 <- 6+ digits already typed
                    // masked: (999) 999-999
                    if (phone.length() >= 6 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true;
                        //here is the core. we substring the raw digits and add the mask as convenient
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        et_contact_number.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        et_contact_number.setSelection(et_contact_number.getText().length() - cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        et_contact_number.setText(ans);
                        et_contact_number.setSelection(et_contact_number.getText().length() - cursorComplement);
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
            }
        });*/

        et_Address = (EditText) findViewById(R.id.et_Address);
        action_back = (ImageView) findViewById(R.id.action_back);
        action_back.setOnClickListener(this);

        et_promo_code = (EditText) findViewById(R.id.et_promo_code);
        et_promo_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_promo_code.setFocusableInTouchMode(true);
                return false;
            }
        });

        etCookingInstructions = (EditText) findViewById(R.id.etCookingInstructions);
        etCookingInstructions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                etCookingInstructions.setFocusableInTouchMode(true);
                return false;
            }
        });

        txtActionBack = (TextView) findViewById(R.id.txtActionBack);
        txtActionBack.setOnClickListener(this);

        et_promo_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (et_promo_code.getText().length() >= 6) {
                    llApplyPromoCode.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                } else {
                    llApplyPromoCode.setBackgroundColor(ContextCompat.getColor(mContext, R.color.txtcolor));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCash = (Button) findViewById(R.id.btnCash);
        btn10Per = (Button) findViewById(R.id.btn10Per);
        btn15Per = (Button) findViewById(R.id.btn15Per);
        btn20Per = (Button) findViewById(R.id.btn20Per);
        btnCustom = (Button) findViewById(R.id.btnCustom);
        btn_payment_method = (Button) findViewById(R.id.btn_payment_method);

        img_change_address = (ImageView) findViewById(R.id.img_change_address);
        img_change_address.setOnClickListener(this);

        btnCash.setOnClickListener(this);
        btn10Per.setOnClickListener(this);
        btn15Per.setOnClickListener(this);
        btn20Per.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btn_payment_method.setOnClickListener(this);

        rv_items_in_cart = (RecyclerView) findViewById(R.id.rv_items_in_cart);
        rv_items_in_cart.setNestedScrollingEnabled(false);

        llEmptyCart = (LinearLayout) findViewById(R.id.llEmptyCart);
        llEmptyCart.setOnClickListener(this);

        SharedPreferences shared = getSharedPreferences("MenuDishId", MODE_PRIVATE);
        MenuDishId = (shared.getString("Id", ""));
        if (!MenuDishId.equalsIgnoreCase("")) {
            if (MenuDishId.substring(MenuDishId.length() - 1).equalsIgnoreCase("~")) {
                MenuDishId = MenuDishId.substring(0, MenuDishId.length() - 1);

                Set<String> set = shared.getStringSet("DishName", null);
                aLDishName.addAll(set);

                Set<String> setDishId = shared.getStringSet("DishId", null);
                aLDishId.addAll(setDishId);
            }
        }
        SharedPreferences shared2 = getSharedPreferences("Deliverypref", MODE_PRIVATE);

        if (finaladdress == null || finaladdress.equals("")) {
            et_Address.setText(shared2.getString("Deliveryaddress", "").toString());
        } else {
            et_Address.setText(finaladdress);
        }


        mLayoutManager = new LinearLayoutManager(ChefCartActivity.this);
        rv_items_in_cart.setLayoutManager(mLayoutManager);
        rv_items_in_cart.setItemAnimator(new DefaultItemAnimator());

        oItemsInCartAdapter = new ItemsInCartAdapter(this, aLDishName, aLDishId);
        rv_items_in_cart.setAdapter(oItemsInCartAdapter);

        /*LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.bill_summary_view, null);

        TextView txtSummary = (TextView) addView.findViewById(R.id.txtSummary);
        txtSummary.setText("Cooking Charges");

        TextView txtCharges = (TextView) addView.findViewById(R.id.txtCharges);
        txtCharges.setText("$30.00");

        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.llBillSummary);
        insertPoint.addView(addView);*/

        rvBookingDetails = (RecyclerView) findViewById(R.id.rvBookingDetails);
        rvBookingDetails.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(ChefCartActivity.this);
        rvBookingDetails.setLayoutManager(mLayoutManager);
        rvBookingDetails.setItemAnimator(new DefaultItemAnimator());

        txtSalesTaxValue = (TextView) findViewById(R.id.txtSalesTaxValue);
        txtSalesTax = (TextView) findViewById(R.id.txtSalesTax);
        txtCookingCharges = (TextView) findViewById(R.id.txtCookingCharges);
        txtDiscount = (TextView) findViewById(R.id.txtDiscount);
        txtTip = (TextView) findViewById(R.id.txtTip);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtDiscountText = (TextView) findViewById(R.id.txtDiscountText);

        llSalesTax = (LinearLayout) findViewById(R.id.llSalesTax);
        llPromoCode = (LinearLayout) findViewById(R.id.llPromoCode);
        llApplyPromoCode = (LinearLayout) findViewById(R.id.llApplyPromoCode);
        llApplyPromoCode.setOnClickListener(this);


        // Get stored slots arrayList
        SharedPreferences shared22 = getSharedPreferences("SLOTS_LIST", MODE_PRIVATE);
        Type listOfBecons = new TypeToken<List<Slots>>() {
        }.getType();
        ArrayList<Slots> mSavedBeaconList = new Gson().fromJson(shared22.getString("LIST", ""), listOfBecons);

        if (mSavedBeaconList != null) {
            Gson gson = new Gson();
            BookingSlots = gson.toJson(mSavedBeaconList);

            ArrayList<String> arrayListDates = new ArrayList<String>();
            ArrayList<String> arrayListSlots = new ArrayList<String>();

            for (int m = 0; m < mSavedBeaconList.size(); m++) {
                if (arrayListDates.contains(DateToddMMyyyy(mSavedBeaconList.get(m).getStartTime())) == false) {
                    arrayListDates.add(DateToddMMyyyy(mSavedBeaconList.get(m).getStartTime()));
                }
            }

            for (int i = 0; i < arrayListDates.size(); i++) {
                String date = arrayListDates.get(i);
                String slot = "";
                for (int j = 0; j < mSavedBeaconList.size(); j++) {
                    if (date.equalsIgnoreCase(DateToddMMyyyy(mSavedBeaconList.get(j).getStartTime()))) {
                        String StartTime = (DateToddMMyyyy2(mSavedBeaconList.get(j).getStartTime()));
                        String EndTime = (DateToddMMyyyy2(mSavedBeaconList.get(j).getEndTime()));
                        if (slot.equalsIgnoreCase("")) {
                            slot = StartTime + "-" + EndTime;
                        } else {
                            slot = slot + ", " + StartTime + "-" + EndTime;
                        }
                    }
                }
                arrayListSlots.add(slot);
            }
            bookingDetailsAdapter = new BookingDetailsAdapter(this, arrayListDates, arrayListSlots, false);
            rvBookingDetails.setAdapter(bookingDetailsAdapter);


            CookingCharges = mSavedBeaconList.size() * Double.parseDouble(getIntent().getStringExtra("Price").replace("$", ""));
            double roundOf = (float) Math.round(CookingCharges * 10) / 10;

            txtCookingCharges.setText("$" + String.format("%.2f", CookingCharges));

            txtSalesTaxValue.setText("Sales Tax" + " (" + getIntent().getStringExtra("SaleTaxPercentage") + ")");

            if (getIntent().getStringExtra("DealDiscount").equalsIgnoreCase("")) {
                llPromoCode.setVisibility(View.VISIBLE);
                txtDiscountText.setText("Promo Discount");
                txtDiscount.setText("-$" + "0.0");
            } else {
                llPromoCode.setVisibility(View.GONE);
                if (getIntent().getStringExtra("DiscountType").equalsIgnoreCase("$")) {
                    txtDiscount.setText("-$" + String.format("%.2f", Double.parseDouble(getIntent().getStringExtra("DealDiscount"))));
                } else {
                    double discount = (Double.parseDouble(txtCookingCharges.getText().toString().replace("$", "")) * Double.parseDouble(getIntent().getStringExtra("DealDiscount")) / 100);
                    double discountRoundOf = (float) Math.round(discount * 10) / 10;
                    txtDiscount.setText("-$" + String.valueOf(String.format("%.2f", discount)));
                }
            }

            deductDiscount = Double.parseDouble(txtCookingCharges.getText().toString().replace("$", "")) - Double.parseDouble(txtDiscount.getText().toString().replace("-$", ""));


            txtSalesTax.setText("$" + String.format("%.2f", (deductDiscount) * Double.parseDouble(getIntent().getStringExtra("SaleTaxPercentage")) / 100));


            txtTip.setText("$" + 0.0);

            Double Total = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));

            //txtTotal.setText("$" + String.format("%.2f", Total));

            System.out.println("Value: " + df.format(Total));
            txtTotal.setText("$" + df.format(Total));

            if (!getIntent().getStringExtra("MinAmount").equalsIgnoreCase("")) {
                double MinAmount = Double.parseDouble(getIntent().getStringExtra("MinAmount"));
                if (CookingCharges >= MinAmount) {
                } else {
                    double perDis = Double.parseDouble(txtDiscount.getText().toString().replace("-$", ""));
                    prevousDiscount = perDis;
                    // txtTotal.setText("$" + String.format("%.2f", Total+perDis));
                    txtTotal.setText("$" + df.format(Total + perDis));
                    txtDiscount.setText("-$" + "0.0");
                }
            }
        }

        et_full_name.setText(basicInformation.getFirstName() + " " + basicInformation.getLastName());


    }


    public String DateToddMMyyyy2(String time) {
        String inputPattern = "MMM dd, yyyy kk:mm";
        String outputPattern = "kk:mm";
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

    public String DateToddMMyyyy(String time) {
        String inputPattern = "MMM dd, yyyy kk:mm";
        String outputPattern = "MMM dd, yyyy";
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


    public void showCustomDialog() {

        final Dialog dialog = new Dialog(ChefCartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.dialog_popup);

        final EditText percentage = (EditText) dialog.findViewById(R.id.txt_tip_per);

        Button cancel = (Button) dialog.findViewById(R.id.cancel_dialog);
        Button save = (Button) dialog.findViewById(R.id.save_dialog);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

                try {
                    InputMethodManager inputMgr = (InputMethodManager) ChefCartActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMgr.toggleSoftInput(0, 0);
                    inputMgr.showSoftInput(percentage, InputMethodManager.SHOW_IMPLICIT);

                } catch (Exception e) {

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager inputMgr = (InputMethodManager) ChefCartActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMgr.toggleSoftInput(0, 0);
                    inputMgr.showSoftInput(percentage, InputMethodManager.SHOW_IMPLICIT);

                } catch (Exception e) {

                }


                m_Text = percentage.getText().toString();
                if (m_Text.equalsIgnoreCase("")) {
                    displayAlert(mContext, "Please enter the Tip");
                } else {
                    txtTip.setText("$" + (deductDiscount) * Double.parseDouble(m_Text) / 100);

                    TipAmount = m_Text;
                    Double Total3 = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));
                    txtTotal.setText("$" + Total3);
                    txtTotal.setText("$" + df.format(Total3));
                    dialog.dismiss();

                }
            }
        });

        dialog.show();

    }


//    private void AddCuisines() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Custom Tip");
//        builder.setCancelable(false);
//
//// Set up the input
//        input = new EditText(this);
//        input.setFocusable(false);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
//        input.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                input.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
//        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//                    try {
//                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                return false;
//            }
//        });
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//
//        input.setHint("Enter the tip of your choice.");
//        input.setSingleLine(true);
//        builder.setView(input);
//// Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                m_Text = input.getText().toString();
//                if (m_Text.equalsIgnoreCase("")) {
//                   // Toast.makeText(mContext, "Please enter the Tip", Toast.LENGTH_SHORT).show();
//                    displayAlert(mContext, "Please enter the Tip");
//                } else {
//                    txtTip.setText("$" + (deductDiscount) * Double.parseDouble(m_Text) / 100);
//
//                    TipAmount = m_Text;
//                    Double Total3 = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));
////                    txtTotal.setText("$" + Total3);
//                    txtTotal.setText("$" + df.format(Total3));
//                    dialog.dismiss();
//
//                }
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_Events:
                if (System.currentTimeMillis() - lastTappedTimeInMillis > 1000) {

                    lastTappedTimeInMillis = System.currentTimeMillis();
                    try {
                        try {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sp_Events.performClick();
                        }
                    }, 5);
                }
                break;


            case R.id.img_change_address:
//                Intent intent2 =new Intent(this, DashBoard.class);
//                intent2.putExtra("IsChef",true);
//                startActivity(intent2);

                intent = new Intent(ChefCartActivity.this, AddAddress.class);
                intent.putExtra("from_chef", true);
                ChefCartActivity.this.startActivity(intent);

                break;

            case R.id.txtActionBack:
                this.finish();
                break;

            case R.id.btn_payment_method:


                if (txt_Events.getText().toString().trim().equalsIgnoreCase("Events") || txt_Events.getText().toString().trim().equalsIgnoreCase("")) {
                    //  Toast.makeText(getApplicationContext(), "Please enter Event Name", Toast.LENGTH_SHORT).show();
                    displayAlert(mContext, "Please select event.");
                }else if(check_other_event)
                {
                    if(other_event.getText().toString().trim().equals(""))
                    {
                        displayAlert(mContext, "Please enter event");
                    } else if (et_no_guest.getText().toString().trim().equalsIgnoreCase("")) {
                        //   Toast.makeText(getApplicationContext(), "Please enter Number of guests", Toast.LENGTH_SHORT).show();
                        displayAlert(mContext, "Please enter number of guests");
                    } else if (et_full_name.getText().toString().trim().equalsIgnoreCase("")) {
                        //  Toast.makeText(getApplicationContext(), "Please enter Full Name", Toast.LENGTH_SHORT).show();
                        displayAlert(mContext, "Please enter full name");
                    } else if (et_contact_number.getText().toString().trim().equalsIgnoreCase("")) {
                        //   Toast.makeText(getApplicationContext(), "Please enter Contact Number", Toast.LENGTH_SHORT).show();
                        displayAlert(mContext, "Please enter contact number");
                    } else if ((et_contact_number.getText().toString().trim().length() < 14)) {
                        load_dialog.cancel();
                        displayAlert(this, "Mobile number must be 10 digit");
                    } /* else if (etCookingInstructions.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter Cooking Instructions", Toast.LENGTH_SHORT).show();
                }*/ else
                    {
                        load_dialog.show();
                        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                        oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                        oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);
                    }
                }
                else if (et_no_guest.getText().toString().trim().equalsIgnoreCase("")) {
                    //   Toast.makeText(getApplicationContext(), "Please enter Number of guests", Toast.LENGTH_SHORT).show();
                    displayAlert(mContext, "Please enter number of guests");
                } else if (et_full_name.getText().toString().trim().equalsIgnoreCase("")) {
                    //  Toast.makeText(getApplicationContext(), "Please enter Full Name", Toast.LENGTH_SHORT).show();
                    displayAlert(mContext, "Please enter full name");
                } else if (et_contact_number.getText().toString().trim().equalsIgnoreCase("")) {
                    //   Toast.makeText(getApplicationContext(), "Please enter Contact Number", Toast.LENGTH_SHORT).show();
                    displayAlert(mContext, "Please enter contact number");
                } else if ((et_contact_number.getText().toString().trim().length() < 14)) {
                    load_dialog.cancel();
                    displayAlert(this, "Mobile number must be 10 digit");
                } /* else if (etCookingInstructions.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please enter Cooking Instructions", Toast.LENGTH_SHORT).show();
                }*/ else {

                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                    oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);
                }
                break;

            case R.id.llApplyPromoCode:
                if (et_promo_code.getText().toString().trim().equalsIgnoreCase("")) {
                    // Toast.makeText(getApplicationContext(), "Please enter Promo Code", Toast.LENGTH_SHORT).show();
                    displayAlert(mContext, "Please enter promo code");
                } else {
                    PromoCodeValidate promoCodeValidate = new PromoCodeValidate();
                    promoCodeValidate.setCurrentDate(date_format);
                    promoCodeValidate.setSessionToken(basicInformation.getSessionToken());
                    promoCodeValidate.setOrderAmount(txtCookingCharges.getText().toString().replace("$", ""));
                    promoCodeValidate.setPromoCode(et_promo_code.getText().toString());
                    promoCodeValidate.setUserId(basicInformation.getUserId());

                    load_dialog.show();
                    String jsonString = oGson.toJson(promoCodeValidate, PromoCodeValidate.class).toString();
                    PostApiClient oInsertUpdateApii = new PostApiClient(mOnResultReceived);
                    oInsertUpdateApii.setRequestSource(RequestSource.PromoCodeValidate);
                    oInsertUpdateApii.executePostRequest(API.fPromoCodeValidate(), jsonString);
                }

                //txtDiscount.setText("");
                break;

            case R.id.action_back:
                finish();
                break;

            case R.id.llEmptyCart:
                finish();
                break;

            case R.id.btnCash:
                btnCash.setBackgroundResource(R.drawable.filterpressed);
                btnCash.setTextColor(ContextCompat.getColor(this, R.color.white));

                btn10Per.setBackgroundResource(R.drawable.filterback);
                btn15Per.setBackgroundResource(R.drawable.filterback);
                btn20Per.setBackgroundResource(R.drawable.filterback);
                btnCustom.setBackgroundResource(R.drawable.filterback);

                btn10Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn15Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn20Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnCustom.setTextColor(ContextCompat.getColor(this, R.color.black));

                txtTip.setText("$" + 0.0);
                TipAmount = "";

                Double Total = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));

                txtTotal.setText("$" + df.format(Total));
                // txtTotal.setText("$" + Total);

                if (!getIntent().getStringExtra("MinAmount").equalsIgnoreCase("")) {
                    double MinAmount = Double.parseDouble(getIntent().getStringExtra("MinAmount"));
                    if (CookingCharges >= MinAmount) {
                    } else {
                        // txtTotal.setText("$" + String.format("%.2f", Total+prevousDiscount));
                        txtTotal.setText("$" + df.format(Total + prevousDiscount));
                        txtDiscount.setText("-$" + "0.0");
                    }
                }
                break;
            case R.id.btn10Per:

                btnCash.setBackgroundResource(R.drawable.filterback);

                btn10Per.setBackgroundResource(R.drawable.filterpressed);
                btn10Per.setTextColor(ContextCompat.getColor(this, R.color.white));

                btn15Per.setBackgroundResource(R.drawable.filterback);
                btn20Per.setBackgroundResource(R.drawable.filterback);
                btnCustom.setBackgroundResource(R.drawable.filterback);

                btnCash.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn15Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn20Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnCustom.setTextColor(ContextCompat.getColor(this, R.color.black));

                txtTip.setText("$" + (deductDiscount) * 10 / 100);
                TipAmount = "10";

                Double Total2 = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));
                txtTotal.setText("$" + df.format(Total2));
                // txtTotal.setText("$" + Total2);

                if (!getIntent().getStringExtra("MinAmount").equalsIgnoreCase("")) {
                    double MinAmount = Double.parseDouble(getIntent().getStringExtra("MinAmount"));
                    if (CookingCharges >= MinAmount) {
                    } else {
                        txtTotal.setText("$" + df.format(Total2 + prevousDiscount));
                        //txtTotal.setText("$" + String.format("%.2f", Total2+prevousDiscount));
                        txtDiscount.setText("-$" + "0.0");
                    }
                }

                break;
            case R.id.btn15Per:
                btnCash.setBackgroundResource(R.drawable.filterback);

                btn10Per.setBackgroundResource(R.drawable.filterback);

                btn15Per.setBackgroundResource(R.drawable.filterpressed);
                btn15Per.setTextColor(ContextCompat.getColor(this, R.color.white));

                btn20Per.setBackgroundResource(R.drawable.filterback);
                btnCustom.setBackgroundResource(R.drawable.filterback);

                btnCash.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn10Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn20Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnCustom.setTextColor(ContextCompat.getColor(this, R.color.black));

                txtTip.setText("$" + (deductDiscount) * 15 / 100);
                TipAmount = "15";


                Double Total3 = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));

                txtTotal.setText("$" + df.format(Total3));
                // txtTotal.setText("$" + Total3);

                if (!getIntent().getStringExtra("MinAmount").equalsIgnoreCase("")) {
                    double MinAmount = Double.parseDouble(getIntent().getStringExtra("MinAmount"));
                    if (CookingCharges >= MinAmount) {
                    } else {
                        txtTotal.setText("$" + df.format(Total3 + prevousDiscount));
                        //  txtTotal.setText("$" + String.format("%.2f", Total3+prevousDiscount));
                        txtDiscount.setText("-$" + "0.0");
                    }
                }
                break;
            case R.id.btn20Per:
                btnCash.setBackgroundResource(R.drawable.filterback);

                btn10Per.setBackgroundResource(R.drawable.filterback);
                btn15Per.setBackgroundResource(R.drawable.filterback);


                btn20Per.setBackgroundResource(R.drawable.filterpressed);
                btn20Per.setTextColor(ContextCompat.getColor(this, R.color.white));

                btnCustom.setBackgroundResource(R.drawable.filterback);

                btnCash.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn10Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn15Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btnCustom.setTextColor(ContextCompat.getColor(this, R.color.black));

                txtTip.setText("$" + (deductDiscount) * 20 / 100);
                TipAmount = "20";

                Double Total4 = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));
                txtTotal.setText("$" + df.format(Total4));
                // txtTotal.setText("$" + Total4);

                if (!getIntent().getStringExtra("MinAmount").equalsIgnoreCase("")) {
                    double MinAmount = Double.parseDouble(getIntent().getStringExtra("MinAmount"));
                    if (CookingCharges >= MinAmount) {
                    } else {
                        txtTotal.setText("$" + df.format(Total4 + prevousDiscount));
                        // txtTotal.setText("$" + String.format("%.2f", Total4+prevousDiscount));
                        txtDiscount.setText("-$" + "0.0");
                    }
                }
                break;
            case R.id.btnCustom:
                btnCash.setBackgroundResource(R.drawable.filterback);

                btn10Per.setBackgroundResource(R.drawable.filterback);
                btn15Per.setBackgroundResource(R.drawable.filterback);
                btn20Per.setBackgroundResource(R.drawable.filterback);


                btnCustom.setBackgroundResource(R.drawable.filterpressed);
                btnCustom.setTextColor(ContextCompat.getColor(this, R.color.white));

                btnCash.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn10Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn20Per.setTextColor(ContextCompat.getColor(this, R.color.black));
                btn15Per.setTextColor(ContextCompat.getColor(this, R.color.black));

                showCustomDialog();
                break;
        }
    }

    @Override
    public void dispatchString(RequestSource from, final String what) {
        load_dialog.cancel();
        ServerResult = what;
        if (from.toString().equalsIgnoreCase("PromoCodeValidate")) {
            switch (ServerResult) {
                case "-1":
                case "-2":
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
                            //  Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                            displayAlert(mContext, "Technical error.Please try after some time.");
                        }
                    });

                    break;

                case "5":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }
                    });

                    break;

                default:

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final JSONObject result;
                            try {
                                result = new JSONObject(what);
                                if (result.getString("ReturnCode").equals("1")) {
                                    txtDiscount.setText("-$" + result.getString("Discount"));

                                    deductDiscount = Double.parseDouble(txtCookingCharges.getText().toString().replace("$", "")) - Double.parseDouble(txtDiscount.getText().toString().replace("-$", ""));

                                    txtSalesTax.setText("$" + (deductDiscount) * Double.parseDouble(getIntent().getStringExtra("SaleTaxPercentage")) / 100);
                                    if (TipAmount.equalsIgnoreCase("")) {
                                        txtTip.setText("$" + 0.0);
                                    } else if (TipAmount.equalsIgnoreCase("10")) {
                                        txtTip.setText("$" + (deductDiscount) * 10 / 100);
                                    } else if (TipAmount.equalsIgnoreCase("15")) {
                                        txtTip.setText("$" + (deductDiscount) * 15 / 100);
                                    } else if (TipAmount.equalsIgnoreCase("20")) {
                                        txtTip.setText("$" + (deductDiscount) * 20 / 100);
                                    } else {
                                        txtTip.setText("$" + (deductDiscount) * Double.parseDouble(m_Text) / 100);
                                    }

                                    Double Total3 = deductDiscount + (Double.parseDouble(txtSalesTax.getText().toString().replace("$", ""))) + (Double.parseDouble(txtTip.getText().toString().replace("$", "")));
                                    txtTotal.setText("$" + df.format(Total3));
                                    // txtTotal.setText("$" + Total3);
                                } else if (result.getString("ReturnCode").equals("5")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    intent.putExtra("Sessionexp", 1);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    //  Toast.makeText(getApplicationContext(), result.getString("ReturnMessage"), Toast.LENGTH_SHORT).show();
                                    displayAlert(mContext, "" + result.getString("ReturnMessage"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                            boolean check = false;

                            for (int i = 0; i < addlist.size(); i++) {

                                if (localityuser.equals(addlist.get(i).getLocality())) {
                                    finaladdress = addlist.get(i).getAddressLine1() + ", " + addlist.get(i).getAddressLine2() + ", " + addlist.get(i).getCityName() + ", " + addlist.get(i).getStateName() + ", " + addlist.get(i).getCountryName() + ", " + addlist.get(i).getZipCode();
                                    et_Address.setText(finaladdress);
                                    //  Toast.makeText(Chart.this, "found address", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(ChefCartActivity.this, CompletePayment.class);
                                    intent.putExtra("BookingSlots", BookingSlots);
                                    intent.putExtra("BusinessType", businesstype);
                                    intent.putExtra("ContactName", et_full_name.getText().toString());

                                    String phone = et_contact_number.getText().toString().trim();
                                    phone = phone.replace("(", "");
                                    phone = phone.replace(")", "");
                                    phone = phone.replace("-", "");
                                    phone = phone.replace(" ", "");
                                    phone = "+1" + "-" + phone;

                                    intent.putExtra("ContactNumber", phone);
                                    intent.putExtra("CookingInstructions", etCookingInstructions.getText().toString());
                                    intent.putExtra("DealDiscount", txtDiscount.getText().toString().replace("-$", ""));
                                    intent.putExtra("DishList", MenuDishId);
                                    if(check_other_event)
                                    {
                                        intent.putExtra("EventType", other_event.getText().toString());
                                    }
                                    else
                                    {
                                        intent.putExtra("EventType", txt_Events.getText().toString());
                                    }

                                    intent.putExtra("Location", et_Address.getText().toString());
                                    intent.putExtra("NumberOfGuest", et_no_guest.getText().toString());
                                    intent.putExtra("PromoCode", et_promo_code.getText().toString());
                                    intent.putExtra("PromoCodeDiscount", PromoCodeDiscount);
                                    intent.putExtra("SalesTax", txtSalesTax.getText().toString().replace("$", ""));
                                    intent.putExtra("SalesTaxPercentage", getIntent().getStringExtra("SaleTaxPercentage"));
                                    intent.putExtra("TipAmount", txtTip.getText().toString().replace("$", ""));
                                    intent.putExtra("TotalAmount", txtTotal.getText().toString().replace("$", ""));
                                    intent.putExtra("totalamount", txtTotal.getText().toString().replace("$", ""));
                                    intent.putExtra("BookingInsert", true);
                                    intent.putExtra("ChefId", getIntent().getStringExtra("ChefId"));
                                    startActivity(new Intent(intent));

                                    check = true;
                                }
                            }

                            if (!check) {
                                intent = new Intent(ChefCartActivity.this, AddAddress.class);
                                intent.putExtra("from_chef", true);
                                ChefCartActivity.this.startActivity(intent);
                            }


                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            intent = new Intent(ChefCartActivity.this, AddAddress.class);
                            intent.putExtra("from_chef", true);
                            ChefCartActivity.this.startActivity(intent);
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
                            intent = new Intent(ChefCartActivity.this, MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            ChefCartActivity.this.startActivity(intent);
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

    public static void setnewadd(String s, String locality) {
        finaladdress = s;
        localityuser = locality;
        et_Address.setText(s);
    }


    public class CustomSpinnerAdapter extends ArrayAdapter<String> {

        Context _context;
        String[] _objects;
        int _id;

        public CustomSpinnerAdapter(Context context, int id, int textViewResourceId,
                                    String[] objects) {
            super(context, textViewResourceId, objects);
            this._context = context;
            this._objects = objects;
            this._id = id;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(final int position, View convertView, ViewGroup parent) {

            View mContentView = LayoutInflater.from(_context).inflate(R.layout.list_item_textview, parent, false);

            TextView tvLabel = (TextView) mContentView.findViewById(R.id.tv_id);
            tvLabel.setText(_objects[position]);

            mContentView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    switch (_id) {

                        case 1:
                            txt_Events.setText(_objects[position]);

                            if(_objects[position].equals("Other"))
                            {
                                ll_other_event.setVisibility(View.VISIBLE);
                                check_other_event=true;
                            }
                            else
                            {
                                ll_other_event.setVisibility(View.GONE);
                                check_other_event=false;
                            }

                            try {
                                Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                                method.setAccessible(true);
                                method.invoke(sp_Events);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            });
            return mContentView;
        }
    }

    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(ChefCartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings = (Button) dialog.findViewById(R.id.not_settings);
        Button retry = (Button) dialog.findViewById(R.id.not_retry);
        retry.setVisibility(View.GONE);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

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
