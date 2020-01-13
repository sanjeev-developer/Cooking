package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.AddressList;
import com.saavor.user.Model.AddressListt;
import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.CartItem;
import com.saavor.user.Model.CartPromoHit;
import com.saavor.user.Model.CartPromoReturn;
import com.saavor.user.Model.CartdetailsHit;
import com.saavor.user.Model.Emptycart;
import com.saavor.user.Model.OrderDetailsHit;
import com.saavor.user.Model.OrderDetailsReceive;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.adapter.ChartAdapter;
import com.saavor.user.adapter.DeleteCartMenu;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.finaladdress;
import static com.saavor.user.activity.DashBoard.localityuser;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;
import static com.saavor.user.activity.DashBoard.totalprice;

public class Chart extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_chart;
    private ChartAdapter chartAdapter;
    private LinearLayout LLAddress;
    private Button switchbut, AmCash, Am10, Am15, Am20, AmCustom, butSelectP, but_empty_cart;
    private TextView switchtxt;
    Boolean switchlayout = true;
    private ImageView add_cart, back, switchimage;
    double kitchen_min_amount;
    EditText percentage;
    double totalbill = 0;
    double tip = 0;
    double deliveryfee = 0;
    double salestax = 0;
    public static Context chartcontext ;
    double minorderamount = 0;
    CartPromoReturn cartPromoReturn;
    Boolean infotocart = false;
    boolean minordergo = false;
    boolean reorder = false;
    boolean directcart = false;
    int tippercentage = 0;
    double Salestax = 0;
    public  static String total_address = "";
    String promotxt = "";
    static TextView cartaddress;
    private ArrayList<AddressList> addlist = new ArrayList<AddressList>();
    boolean disavail = false, cash = false, tenper = false, twentyper = false, fifteenper = false, customper = false;
    public static double sub = 0;
    @BindView(R.id.txt_orderid)
    TextView orderid;
    @BindView(R.id.img_edit_add)
    ImageView img_edit_add;
    @BindView(R.id.txt_cart_date)
    TextView cartdate;
    @BindView(R.id.txt_cart_del_fee)
    TextView cartdelfee;
    @BindView(R.id.ll_pickup)
    LinearLayout LL_pickup;
    @BindView(R.id.txt_salestax)
    TextView txtsalestax;
    @BindView(R.id.txt_cart_sales)
    TextView cartsale;
    @BindView(R.id.txt_cart_dis)
    TextView cartdis;
    @BindView(R.id.txt_tip)
    TextView carttip;
    @BindView(R.id.remaing_dollar)
    TextView remain_dollar;
    @BindView(R.id.txt_subtotal)
    TextView subtotal;
    @BindView(R.id.empty_text)
    TextView emptytext;
    @BindView(R.id.grand_total)
    TextView grandtotal;
    @BindView(R.id.edt_cooking_instruc)
    EditText cartcooking;
    @BindView(R.id.edt_delivery_instruc)
    EditText cartdelivery;
    @BindView(R.id.ll_remain_money)
    LinearLayout LL_remain_money;
    @BindView(R.id.but_cart_promo)
    Button button_promo;
    @BindView(R.id.edt_get_promo)
    EditText get_promo;
    @BindView(R.id.cart_phoneno)
    EditText cartphone;
    @BindView(R.id.cart_username)
    EditText cartusername;
    @BindView(R.id.ll_delfee)
    LinearLayout LL_delfee;
    double FreeDeliveryLimitAmount = 0;
    double deliverycharge = 0;
    @BindView(R.id.txt_promo_dis)
    TextView promocode_txt;
    int neww = 0;
    int old = 0;
    int z = 0;
    String k, rdeladd = "", risdel = "", rprofileid = "", rdelfee = "", oredrdatemove;
    double promocode = 0, dislimit = 0;
    double discount = 0;
    String TipType = "";
    CartdetailsHit cartdetailsHit = new CartdetailsHit();
    private OrderDetailsReceive orderDetailsReceive;
    LinearLayout LL_discount_avail, LL_promohide;
    public int tempisdel = 0;
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    String movedate;
    int choose;

    public static ArrayList<DeleteCartMenu> deletelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //intializing all elements in ui
        basicfetch();
        chartcontext = Chart.this;
        cartaddress = (TextView) findViewById(R.id.txt_cart_address);
        cartaddress.setOnClickListener(this);
        cartPromoReturn = new CartPromoReturn();
        mOnResultReceived = this;
        ButterKnife.bind(this);
        infotocart = getIntent().getBooleanExtra("infotocart", false);
        reorder = getIntent().getBooleanExtra("reorder", false);
        movedate = getIntent().getStringExtra("movdate");

        img_edit_add.setOnClickListener(this);



        //fetching delivery data from shared
        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

        //if reorder food then all pervious reorder data fetch
        if (reorder) {
            rdeladd = getIntent().getStringExtra("rdeladdress");
            risdel = getIntent().getStringExtra("risdel");
            rprofileid = getIntent().getStringExtra("rprofileid");
            rdelfee = getIntent().getStringExtra("rkitchendelcharge");
        }
        directcart = getIntent().getBooleanExtra("directcart", false);
        LL_promohide = (LinearLayout) findViewById(R.id.ll_promohide);
        LL_discount_avail = (LinearLayout) findViewById(R.id.ll_discount_avail);
        LL_remain_money = (LinearLayout) findViewById(R.id.ll_remain_money);
        LLAddress = (LinearLayout) findViewById(R.id.ll_cart_address);
        switchtxt = (TextView) findViewById(R.id.txt_switch_cart);
        switchbut = (Button) findViewById(R.id.btn_switch_cart);
        AmCash = (Button) findViewById(R.id.but_cart_cash);
        Am10 = (Button) findViewById(R.id.but_cart_10);
        Am15 = (Button) findViewById(R.id.but_cart_15);
        Am20 = (Button) findViewById(R.id.but_cart_20);
        but_empty_cart = (Button) findViewById(R.id.btn_empty_cart);
        AmCustom = (Button) findViewById(R.id.but_cart_custom);
        add_cart = (ImageView) findViewById(R.id.img_add_cart);
        switchimage = (ImageView) findViewById(R.id.img_switch);
        switchimage.setBackgroundResource(R.drawable.ic_deliveryk_icon);
        back = (ImageView) findViewById(R.id.tool_back_cart);
        butSelectP = (Button) findViewById(R.id.but_select_cart);
        cartcooking = (EditText) findViewById(R.id.edt_cooking_instruc);
        cartdelivery = (EditText) findViewById(R.id.edt_delivery_instruc);
        back.setOnClickListener(this);

        switchbut.setOnClickListener(this);
        AmCash.setOnClickListener(this);
        Am10.setOnClickListener(this);
        Am15.setOnClickListener(this);
        Am20.setOnClickListener(this);
        AmCustom.setOnClickListener(this);
        add_cart.setOnClickListener(this);
        butSelectP.setOnClickListener(this);
        but_empty_cart.setOnClickListener(this);
        button_promo.setOnClickListener(this);


        choose = getIntent().getIntExtra("hitreorder", 0);
        k = getIntent().getStringExtra("kitchentitle");

        if (reorder) {

            add_cart.setVisibility(View.INVISIBLE);
            but_empty_cart.setVisibility(View.GONE);
        }

        if (choose == 0) {
            //cartdetails
            try {
                load_dialog.show();
                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.cartdetails);
                oInsertUpdateApi.executeGetRequest(API.cardetailshit() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());

                String fullapi = API.cardetailshit() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
                String fullapii = API.cardetailshit() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
            } catch (Exception e) {

            }
        } else {
            String orderid = getIntent().getStringExtra("foodorderid");
            orderDetailsReceive = new OrderDetailsReceive();

            try {
                mProgressDialog.setMessage("Re-ordering your food");
                load_dialog.show();
                OrderDetailsHit orderDetailsHit = new OrderDetailsHit();
                orderDetailsHit.setCurrentDate(date_format);
                orderDetailsHit.setSessionToken(basicInformation.getSessionToken().toString());
                orderDetailsHit.setUserId(basicInformation.getUserId().toString());
                orderDetailsHit.setOrderId(orderid);

                String jsonString = gson.toJson(orderDetailsHit, OrderDetailsHit.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.executePostRequest(API.orderhistorydetails(), jsonString);
                oChangePsswordApi.setRequestSource(RequestSource.reorder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        get_promo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (get_promo.getText().length() == 6) {
                    button_promo.setBackgroundColor(getResources().getColor(R.color.accent));
                } else {
                    button_promo.setBackgroundColor(getResources().getColor(R.color.buttonback));
                }

            }
        });

        get_promo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                get_promo.setFocusableInTouchMode(true);

                return false;
            }
        });

        cartcooking.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                cartcooking.setFocusableInTouchMode(true);

                return false;
            }
        });

        cartdelivery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                cartdelivery.setFocusableInTouchMode(true);

                return false;
            }
        });

        cartphone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                cartphone.setFocusableInTouchMode(true);

                return false;
            }
        });

        cartusername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                cartusername.setFocusableInTouchMode(true);

                return false;
            }
        });


        int deliveryy = 0;
//        if (reorder) {
//            deliveryy = Integer.parseInt(risdel);
//            //BasicKitchenInfo basicKitInfo = new BasicKitchenInfo();
//            String data = mDatabase.getString("basickitcheninfo", "");
//            basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);
//            deliveryy = basicKitInfo.getIsdelivery();
//        } else {
//            // make switch button visiblity gone in case kitchen doesnot provide delivery
//           // BasicKitchenInfo basicKitInfo = new BasicKitchenInfo();
//            String data = mDatabase.getString("basickitcheninfo", "");
//            basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);
//            deliveryy = basicKitInfo.getIsdelivery();
//
//        }

        String data = mDatabase.getString("basickitcheninfo", "");
        basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);
        deliveryy = basicKitInfo.getIsdelivery();
        int kitchendel = basicKitInfo.getIskitchendelivery();


        FreeDeliveryLimitAmount = basicKitInfo.getFreeDeliveryLimitAmount();


        if (deliveryy == 1 || kitchendel == 1) {
            switchtxt.setText("Delivery");
            switchbut.setText("Switch to Pickup");
            switchimage.setBackgroundResource(R.drawable.ic_deliveryk_icon);
            switchlayout = true;
            LLAddress.setVisibility(View.VISIBLE);
            LL_pickup.setVisibility(View.GONE);
            LL_delfee.setVisibility(View.VISIBLE);

            tempisdel = 1;
        } else {

            LLAddress.setVisibility(View.GONE);
            switchbut.setVisibility(View.GONE);
            LL_pickup.setVisibility(View.VISIBLE);
            LL_delfee.setVisibility(View.GONE);
            switchlayout = false;

            tempisdel = 0;
        }

        try {
            cartusername.setText(basicInformation.getFirstName() + " " + basicInformation.getLastName());

        } catch (Exception e) {

        }


        cartphone.addTextChangedListener(new TextWatcher() {
            //we need to know if the user is erasing or inputting some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length() - cartphone.getSelectionStart();
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
                        cartphone.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        cartphone.setSelection(cartphone.getText().length() - cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        cartphone.setText(ans);
                        cartphone.setSelection(cartphone.getText().length() - cursorComplement);
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
            }
        });


        try {
            if (basicInformation.getMobileNumber().equals("") || basicInformation.getMobileNumber().equals(null) || basicInformation.getMobileNumber().equals("null")) {

                cartphone.setText("");

            } else {
                String value = "" + basicInformation.getMobileNumber();
                String[] aSplit = value.split(" ");
                cartphone.setText(aSplit[1] + " " + aSplit[2]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


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
    }

    @Override
    public void onClick(View v) {

        String tipamount;
        int first;
        DecimalFormat df = new DecimalFormat("####0.00");

        switch (v.getId()) {


            case R.id.but_cart_promo:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (get_promo.getText().toString().equals("")) {
                    displayAlert(Chart.this, "please enter promo code first");
                } else
                    {
                    load_dialog.show();
                    mProgressDialog.setMessage("Please Wait...");

                    CartPromoHit cartPromoHit = new CartPromoHit();
                    cartPromoHit.setCurrentDate(date_format);
                    cartPromoHit.setOrderAmount("" + sub);
                    cartPromoHit.setSessionToken(basicInformation.getSessionToken());
                    cartPromoHit.setPromoCode(get_promo.getText().toString());
                    cartPromoHit.setUserId("" + basicInformation.getUserId());

                    String jsonString = gson.toJson(cartPromoHit, CartPromoHit.class).toString();
                    System.out.println("" + jsonString);
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.setRequestSource(RequestSource.cartpromo);
                    oChangePsswordApi.executePostRequest(API.cartpromo(), jsonString);
                }

                break;

            case R.id.but_cart_cash:

                if (cash) {
                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    tip = 0;
                    carttip.setText("$" + "0.00");
                    TipType = "";
                    cash = false;
                    tippercentage = 0;
                    Totalamount();
                } else {
                    AmCash.setBackgroundResource(R.drawable.filterpressed);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.white));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    tip = 0;
                    carttip.setText("$" + "0.00");
                    TipType = "cash";
                    cash = true;
                    tenper = false;
                    tippercentage = 0;
                    fifteenper = false;
                    twentyper = false;
                    customper = false;
                    Totalamount();
                }


                break;

            case R.id.but_cart_10:

                if (tenper) {
                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    // System.out.println("Value: " + sub * 10 / 100);
                    tip = 0;
                    carttip.setText("$" + "0.00");
                    TipType = "";
                    tippercentage = 0;
                    tenper = false;
                    Totalamount();
                } else {
                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterpressed);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.white));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));


                    System.out.println("Value: " + sub * 10 / 100);
//                    tip = Double.parseDouble(df.format(sub * 10 / 100));
//                    carttip.setText("$" + tip);
                    cash = false;
                    tenper = true;
                    twentyper = false;
                    fifteenper = false;
                    tippercentage = 10;
                    customper = false;
                    TipType = "10%";
                    Totalamount();
                }

                break;

            case R.id.but_cart_15:

                if (fifteenper) {
                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    // System.out.println("Value: " + sub * 10 / 100);
                    tip = 0;
                    carttip.setText("$" + "0.00");
                    TipType = "";
                    tippercentage = 0;
                    fifteenper = false;
                    Totalamount();
                } else {

                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterpressed);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.white));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));


                    System.out.println("Value: " + sub * 15 / 100);
//                    tip = Double.parseDouble(df.format(sub * 15 / 100));
//                    carttip.setText("$" + tip);
                    cash = false;
                    fifteenper = true;
                    tenper = false;
                    twentyper = false;
                    tippercentage = 15;
                    customper = false;
                    TipType = "15%";
                    Totalamount();
                }
                break;

            case R.id.but_cart_20:


                if (twentyper) {
                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    // System.out.println("Value: " + sub * 10 / 100);
                    tip = 0;
                    carttip.setText("$" + "0.00");
                    TipType = "";
                    tippercentage = 0;
                    twentyper = false;
                    Totalamount();
                } else {

                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterpressed);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.white));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    System.out.println("Value: " + sub * 20 / 100);
//                    tip = Double.parseDouble(df.format(sub * 20 / 100));
//                    carttip.setText("$" + tip);
                    TipType = "20%";
                    twentyper = true;
                    tippercentage = 20;
                    cash = false;
                    fifteenper = false;
                    tenper = false;
                    customper = false;
                    Totalamount();
                }
                break;

            case R.id.but_cart_custom:

                if (customper) {
                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterback);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                    // System.out.println("Value: " + sub * 10 / 100);
                    tip = 0;
                    carttip.setText("$" + "0.00");
                    TipType = "";
                    customper = false;
                    tippercentage = 0;
                    Totalamount();
                } else {

                    AmCash.setBackgroundResource(R.drawable.filterback);
                    Am10.setBackgroundResource(R.drawable.filterback);
                    Am15.setBackgroundResource(R.drawable.filterback);
                    Am20.setBackgroundResource(R.drawable.filterback);
                    AmCustom.setBackgroundResource(R.drawable.filterpressed);

                    AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                    Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                    AmCustom.setTextColor(getResources().getColor(R.color.white));

                    get_promo.setFocusable(false);

                    showCustomDialog();
                    closeKB();

                }

                break;

            case R.id.btn_empty_cart:

            {
                //dialog intialization
                dialog = new Dialog(Chart.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.empty_cart_layout);
                dialog.setCancelable(true);

                Button no = (Button) dialog.findViewById(R.id.not_empty);
                Button yes = (Button) dialog.findViewById(R.id.yes_empty);
                ImageView cnacel = (ImageView) dialog.findViewById(R.id.empty_clear);
                TextView text = (TextView) dialog.findViewById(R.id.text_empty);
                text.setText("Are you sure you want to empty cart?");

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        emptycart();
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

            case R.id.btn_switch_cart:

                if (!switchlayout) {
                    switchtxt.setText("Delivery");
                    switchbut.setText("Switch to Pickup");
                    switchimage.setBackgroundResource(R.drawable.ic_deliveryk_icon);
                    switchlayout = true;
                    LLAddress.setVisibility(View.VISIBLE);
                    LL_delfee.setVisibility(View.VISIBLE);

                    deliveryfee = deliverycharge;
                    tempisdel = 1;
                    Totalamount();

                } else {

                    switchtxt.setText("Pickup");
                    switchbut.setText("Switch to Delivery");
                    switchimage.setBackgroundResource(R.drawable.ic_pickupk_icon);
                    LLAddress.setVisibility(View.GONE);
                    switchlayout = false;
                    LL_delfee.setVisibility(View.GONE);
                    tempisdel = 0;
                    deliveryfee = 0;
                    cartdelfee.setText("$0.00");
                    Totalamount();
                }

                break;

            case R.id.img_add_cart:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (directcart) {
                    intent = new Intent(this, Kitchen.class);
                    intent.putExtra("kitchentitle", cartdetailsHit.getCartInfo().getKitchenName().toString());
                    this.startActivity(intent);
                } else {
//                    updatelist();
//                    finish();
                    intent = new Intent(this, Kitchen.class);
                    intent.putExtra("kitchentitle", cartdetailsHit.getCartInfo().getKitchenName().toString());
                    this.startActivity(intent);
                }
                break;

            case R.id.tool_back_cart:

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if (reorder) {
                    finish();
                } else {
//                    updatelist();
//                    finish();
                    intent = new Intent(this, Kitchen.class);
                    intent.putExtra("kitchentitle", cartdetailsHit.getCartInfo().getKitchenName().toString());
                    this.startActivity(intent);
                }
                break;

            case R.id.but_select_cart:

                if (minordergo) {
                    if (cartusername.getText().toString().trim().equals("")) {
                        displayAlert(this, "Username Can't be blank");
                    } else if (cartphone.getText().toString().equals("")) {
                        displayAlert(this, "Mobile Number Can't be blank");
                    } else if ((cartphone.getText().length() < 14)) {
                        displayAlert(this, "Mobile Number must be 10 digit");
                    } else
                        {
                            if(tempisdel == 1)
                            {
                                load_dialog.show();
                                GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                                oInsertUpdateApi.executeGetRequest(API.addresslist() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());
                                oInsertUpdateApi.setRequestSource(RequestSource.FetchAdd);
                            }
                            else
                            {
                                intent = new Intent(Chart.this, CompletePayment.class);
                                intent.putExtra("totalamount", grandtotal.getText().toString());
                                intent.putExtra("orderID", orderid.getText().toString());
                                intent.putExtra("cooking_instructions", "" + cartcooking.getText().toString());
                                intent.putExtra("delivery_instructions", "" + cartdelivery.getText().toString());
                                intent.putExtra("servicecharge", "" + 0);
                                intent.putExtra("salestax", "" + Salestax);
                                intent.putExtra("salestaxpercentage", "" + cartdetailsHit.getCartInfo().getSalesPercentage());
                                intent.putExtra("tip", "" + tip);
                                intent.putExtra("PromoCodetxt", "" + promotxt);
                                intent.putExtra("PromoCodeDiscount", promocode);
                                intent.putExtra("dealdiscount", discount);
                                intent.putExtra("tiptype", TipType);
                                intent.putExtra("username", cartusername.getText().toString());
                                intent.putExtra("contact", cartphone.getText().toString());
                                intent.putExtra("isdelivery", tempisdel);
                                intent.putExtra("orderdatemove", movedate);
                                intent.putExtra("deliveryaddress", "");
                                if (reorder) {
                                    intent.putExtra("reorder", reorder);
                                    intent.putExtra("rdeladdresss", rdeladd);
                                    intent.putExtra("rprofileid", rprofileid);
                                    intent.putExtra("rdelfee", rdelfee);
                                }
                                Chart.this.startActivity(intent);
                            }

                    }
                }
                break;

            case R.id.img_edit_add:

                intent = new Intent(Chart.this, AddAddress.class);
                intent.putExtra("fromcart", true);
                Chart.this.startActivity(intent);

                break;

        }
    }

    @Override
    public void onBackPressed() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (reorder) {
            finish();
        } else {
//            updatelist();
//            finish();
            intent = new Intent(this, Kitchen.class);
            intent.putExtra("kitchentitle", cartdetailsHit.getCartInfo().getKitchenName().toString());
            this.startActivity(intent);
        }
    }

    //custom tip dialog
    public void showCustomDialog() {

        final Dialog dialog = new Dialog(Chart.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.dialog_popup);

        percentage = (EditText) dialog.findViewById(R.id.txt_tip_per);

        Button cancel = (Button) dialog.findViewById(R.id.cancel_dialog);
        Button save = (Button) dialog.findViewById(R.id.save_dialog);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

                cartcooking.clearFocus();
                cartdelivery.clearFocus();

                AmCash.setBackgroundResource(R.drawable.filterback);
                Am10.setBackgroundResource(R.drawable.filterback);
                Am15.setBackgroundResource(R.drawable.filterback);
                Am20.setBackgroundResource(R.drawable.filterback);
                AmCustom.setBackgroundResource(R.drawable.filterback);

                AmCash.setTextColor(getResources().getColor(R.color.darkgrey));
                Am10.setTextColor(getResources().getColor(R.color.darkgrey));
                Am15.setTextColor(getResources().getColor(R.color.darkgrey));
                Am20.setTextColor(getResources().getColor(R.color.darkgrey));
                AmCustom.setTextColor(getResources().getColor(R.color.darkgrey));

                // System.out.println("Value: " + sub * 10 / 100);
                tip = 0;
                carttip.setText("$" + "0.00");
                TipType = "";
                customper = false;
                tippercentage = 0;
                Totalamount();

                closeKB();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartcooking.clearFocus();
                cartdelivery.clearFocus();


                if (percentage.getText().toString().equals("")) {
                    dialog.dismiss();

                } else {

                    String number = percentage.getText().toString();
                    int result = Integer.parseInt(number);
                    TipType = number + "%";

                    DecimalFormat df = new DecimalFormat("####0.00");

                    System.out.println("Value: " + df.format(sub * result / 100));
                    //  tip = Double.parseDouble(df.format(sub * result / 100));
                    //  carttip.setText("$" + df.format(sub * result / 100));
                    twentyper = false;
                    cash = false;
                    fifteenper = false;
                    tippercentage = result;
                    tenper = false;
                    customper = true;
                    Totalamount();
                    dialog.dismiss();
                    closeKB();
                }

            }
        });

        dialog.show();

    }

    @Override
    public void dispatchString(RequestSource from, String what) {
        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Chart.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {

            String data = what;
            if (from.toString().equalsIgnoreCase("cartdetails")) {

                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();

                    cartdetailsHit = gson.fromJson(jsonString, CartdetailsHit.class);
                    System.out.println(">>>>" + what);
                    String check = cartdetailsHit.getReturnCode();
                    final String message = cartdetailsHit.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<CartItem> cartItems = new ArrayList<>();
                                emptytext.setVisibility(View.GONE);


                                if (reorder) {
                                    but_empty_cart.setVisibility(View.GONE);
                                } else {
                                    but_empty_cart.setVisibility(View.VISIBLE);
                                }

                                cartItems = cartdetailsHit.getCartInfo().getCartItems();


                                chartAdapter = new ChartAdapter(Chart.this, cartItems, reorder);
                                recyclerView_chart = (RecyclerView) findViewById(R.id.recycle_chart);
                                recyclerView_chart.setNestedScrollingEnabled(false);
                                recyclerView_chart.setFocusable(false);
                                recyclerView_chart.setLayoutManager(new LinearLayoutManager(Chart.this, LinearLayoutManager.VERTICAL, true));
                                recyclerView_chart.setAdapter(chartAdapter);

                                orderid.setText(cartdetailsHit.getCartInfo().getOrderID().toString());

                                String myString = cartdetailsHit.getCartInfo().getOrderDate().toString();
                                String month = "", date = "", year = "";

                                String[] aSplit = myString.split("-");

                                month = aSplit[0];
                                date = aSplit[1];
                                year = aSplit[2];


                                DecimalFormat df = new DecimalFormat("####0.00");

                                System.out.println("Value: " + df.format(DashBoard.totalprice));
                                sub = Double.parseDouble(df.format(DashBoard.totalprice));
                                subtotal.setText("$" + String.format("%.02f", sub));

                                if(mDatabase.getString("DealDiscount", "").equals(""))
                                {
                                    cartdetailsHit.getCartInfo().setDealDiscountValue(0.00);
                                }
                                else
                                {
                                    cartdetailsHit.getCartInfo().setDealDiscountValue(Double.parseDouble(mDatabase.getString("DealDiscount", "")));
                                }

                                if (mDatabase.getString("discount_type", "").equals("%")) {
                                    double newdis = sub * Double.parseDouble(mDatabase.getString("DealDiscount", "")) / 100;
                                    try {
                                        // discount = Double.parseDouble(df.format(newdis));
                                        cartdetailsHit.getCartInfo().setDiscount(Double.parseDouble(df.format(newdis)));

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if(mDatabase.getString("discount_type", "").equals("$")) {
                                    cartdetailsHit.getCartInfo().setDiscount(Double.parseDouble(mDatabase.getString("DealDiscount", "")));
                                }
                                else
                                {
                                    cartdetailsHit.getCartInfo().setDiscount(0.00);
                                }

                                if(mDatabase.getString("dis_MinAmount", "").equals(""))
                                {
                                    cartdetailsHit.getCartInfo().setDealOrderMinAmount(0.00);
                                }
                                else
                                {
                                    cartdetailsHit.getCartInfo().setDealOrderMinAmount(Double.parseDouble(mDatabase.getString("dis_MinAmount", "")));
                                }

                                cartdetailsHit.getCartInfo().setDealDiscountType(mDatabase.getString("discount_type", ""));
                                cartdate.setText(month + " " + date + "," + year);

                                if (cartdetailsHit.getCartInfo().getDiscount() == null || cartdetailsHit.getCartInfo().getDiscount().equals("") || cartdetailsHit.getCartInfo().getDiscount() == 0) {
                                    discount = 0;

                                    LL_discount_avail.setVisibility(View.VISIBLE);
                                    LL_promohide.setVisibility(View.VISIBLE);
                                    disavail = false;
                                    cartdis.setText("$0.00");

                                } else {
                                    dislimit = cartdetailsHit.getCartInfo().getDealOrderMinAmount();
                                    discount = cartdetailsHit.getCartInfo().getDiscount();
                                    disavail = true;

                                    if (sub > cartdetailsHit.getCartInfo().getDealOrderMinAmount())
                                    {
                                        LL_discount_avail.setVisibility(View.GONE);
                                        LL_promohide.setVisibility(View.GONE);
                                        cartdis.setText("- $" + df.format(discount));

                                    } else {
                                        LL_discount_avail.setVisibility(View.VISIBLE);
                                        LL_promohide.setVisibility(View.VISIBLE);
                                        cartdis.setText("$0.00");

                                    }
                                }

                                if(finaladdress==null || finaladdress.equals(""))
                                {

                                  //  total_address = cartdetailsHit.getCartInfo().getCustomerAddress().toString();

                                    String data =""+cartdetailsHit.getCartInfo().getCustomerAddress().toString();
                                    String add1="", add2="",city="";
                                    String state="", country="",zip="";

                                    String[] Split = data.split(",");

                                    add1=Split[0];
                                    add2=Split[1];
                                    city=Split[2];
                                    state=Split[3];
                                    country=Split[4];

                                    try {
                                           zip=Split[5];

                                        if(add2 == null || add2.equals(" ") || add2.equals(""))
                                        {
                                            total_address = add1+","+city+","+state+","+country+","+zip;
                                        }
                                        else
                                        {
                                            total_address = add1+","+add2+","+city+","+state+","+country+","+zip;
                                        }

                                    }
                                    catch (Exception e)
                                    {
                                            total_address = add1+","+add2+","+city+","+state+","+country;
                                    }

//                                    if(Split[5] == null || Split[5].equals("") || Split[5].equals(" "))
//                                    {
//                                        total_address = add1+","+add2+","+city+","+state+","+country;
//                                    }
//                                    else
//                                    {
//                                        zip=Split[5];
//                                        total_address = add1+","+add2+","+city+","+state+","+country+","+zip;
//                                    }


//                                    if(zip == null || zip.equals("") || zip.equals(" "))
//                                    {
//                                        total_address = add1+","+city+","+state+","+country+","+zip;
//                                    }else
//                                    {
//                                        total_address = add1+","+add2+","+city+","+state+","+country+","+zip;
//                                    }
                                    cartaddress.setText(total_address);
                                }
                                else
                                {
                                    cartaddress.setText(finaladdress);
                                }


                                Salestax = sub * cartdetailsHit.getCartInfo().getSalesPercentage() / 100;
                                txtsalestax.setText("Sales Tax(" + df.format(cartdetailsHit.getCartInfo().getSalesPercentage()) + "%)");

                                deliverycharge = cartdetailsHit.getCartInfo().getDeliveryFee();
                                deliveryfee = deliverycharge;

                                cartdelfee.setText(String.format("%.02f", deliveryfee));

                                if (cartdetailsHit.getCartInfo().getIsDelivery() == 1) {
                                    // fetching default
                                    String data = mDatabase.getString("basickitcheninfo", "");
                                    basicKitInfo = gson.fromJson(data, BasicKitchenInfo.class);
                                    minorderamount = basicKitInfo.getKitchenminamount();
                                } else {
                                    minorderamount = 0;
                                }

                                deletelist.clear();

                                tip = 0;
                                carttip.setText("$" + String.format("%.02f", 0.0f));

                                Totalamount();
                                load_dialog.cancel();
                            }
                        });


                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                finish();

                                load_dialog.cancel();
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(Chart.this, "" + message);
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
                            //  redirect(Chart.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);

            } else if (from.toString().equalsIgnoreCase("emptycart")) {


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

                                intent = new Intent(Chart.this, Kitchen.class);
                                intent.putExtra("kitchentitle", k);
                                Chart.this.startActivity(intent);

                                storenotcustomizable.clear();
                                totalprice = 0;
                                TotalItem = 0;
                            }
                        });


                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(Chart.this, message);
                                load_dialog.cancel();
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(Chart.this, "" + message);
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
                            //  redirect(Chart.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);

            } else if (from.toString().equalsIgnoreCase("cartpromo")) {
                try {
                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    cartPromoReturn = gson.fromJson(jsonString, CartPromoReturn.class);
                    System.out.println(">>>>" + what);
                    String check = cartPromoReturn.getReturnCode();
                    final String message = cartPromoReturn.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                //dialog intialization
                                dialog = new Dialog(Chart.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setContentView(R.layout.alert_diag);
                                dialog.setCancelable(false);

                                Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
                                TextView alertext = (TextView) dialog.findViewById(R.id.text_alert);
                                TextView diagtitle = (TextView) dialog.findViewById(R.id.dialog_title);
                                ImageView alertimg = (ImageView) dialog.findViewById(R.id.alert_img);
                                alertimg.setVisibility(View.VISIBLE);
                                alertimg.setImageResource(R.drawable.sucess_img);

                                alertext.setText("Promo code applied");
                                diagtitle.setText("Success");

                                okplaced.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.cancel();

                                    }
                                });

                                dialog.show();

                                DecimalFormat df = new DecimalFormat("####0.00");
                                System.out.println("Value: " + df.format(cartPromoReturn.getDiscount()));
                                promocode = cartPromoReturn.getDiscount();
                                promocode_txt.setText("- $" + promocode);
                                promotxt = get_promo.getText().toString();
                                get_promo.setText("");
                                Totalamount();

                            }
                        });


                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(Chart.this, message);
                                load_dialog.cancel();
                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(Chart.this, "" + message);
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
                            //redirect(Chart.this, "No internet access. Please turn on cellular data or use wifi.");
                            nointernet();
                        }
                    });
                }

                Log.e("response", "" + what);
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
                                String tempcheck="";

                                for (int i = 0; i < addlist.size(); i++) {

                                    if (localityuser.equals(addlist.get(i).getLocality())) {

                                        if(addlist.get(i).getAddressLine2() == null || addlist.get(i).getAddressLine2().equals(""))
                                        {
                                            tempcheck = addlist.get(i).getAddressLine1() + ", " + addlist.get(i).getCityName() + ", " + addlist.get(i).getStateName() + ", " + addlist.get(i).getCountryName() + ", " + addlist.get(i).getZipCode();
                                        }
                                        else
                                        {
                                            tempcheck = addlist.get(i).getAddressLine1() + ", " + addlist.get(i).getAddressLine2() + ", " + addlist.get(i).getCityName() + ", " + addlist.get(i).getStateName() + ", " + addlist.get(i).getCountryName() + ", " + addlist.get(i).getZipCode();

                                        }

                                        //  Toast.makeText(Chart.this, "found address", Toast.LENGTH_LONG).show();

                                        if (tempcheck.equals(total_address)) {

                                            cartaddress.setText(total_address);
                                            check = true;

                                            intent = new Intent(Chart.this, CompletePayment.class);
                                            intent.putExtra("totalamount", grandtotal.getText().toString());
                                            intent.putExtra("orderID", orderid.getText().toString());
                                            intent.putExtra("cooking_instructions", "" + cartcooking.getText().toString());
                                            intent.putExtra("delivery_instructions", "" + cartdelivery.getText().toString());
                                            intent.putExtra("servicecharge", "" + 0);
                                            intent.putExtra("salestax", "" + Salestax);
                                            intent.putExtra("salestaxpercentage", "" + cartdetailsHit.getCartInfo().getSalesPercentage());
                                            intent.putExtra("tip", "" + tip);
                                            intent.putExtra("PromoCodetxt", "" + promotxt);
                                            intent.putExtra("PromoCodeDiscount", promocode);
                                            intent.putExtra("dealdiscount", discount);
                                            intent.putExtra("tiptype", TipType);
                                            intent.putExtra("username", cartusername.getText().toString());
                                            intent.putExtra("contact", cartphone.getText().toString());
                                            intent.putExtra("isdelivery", tempisdel);
                                            intent.putExtra("orderdatemove", movedate);
                                            intent.putExtra("deliveryaddress", finaladdress);
                                            if (reorder) {
                                                intent.putExtra("reorder", reorder);
                                                intent.putExtra("rdeladdresss", rdeladd);
                                                intent.putExtra("rprofileid", rprofileid);
                                                intent.putExtra("rdelfee", rdelfee);
                                            }
                                            Chart.this.startActivity(intent);
                                            break;
                                        }
                                    }
                                }

                                if (!check) {
                                    intent = new Intent(Chart.this, AddAddress.class);
                                    intent.putExtra("fromcart", true);
                                    Chart.this.startActivity(intent);
                                }


                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                intent = new Intent(Chart.this, AddAddress.class);
                                intent.putExtra("fromcart", true);
                                Chart.this.startActivity(intent);
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


                                intent = new Intent(Chart.this, MainActivity.class);
                                intent.putExtra("Sessionexp", 1);
                                Chart.this.startActivity(intent);
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

    //empty cart
    public void emptycart() {
        load_dialog.show();
        Emptycart emptycart = new Emptycart();
        emptycart.setCurrentDate(date_format);
        emptycart.setSessionToken(basicInformation.getSessionToken());
        emptycart.setUserId(basicInformation.getUserId());

        String jsonString = gson.toJson(emptycart, Emptycart.class).toString();
        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
        oChangePsswordApi.setRequestSource(RequestSource.emptycart);
        oChangePsswordApi.executePostRequest(API.emptycart(), jsonString);

    }


    //chart adapter function
    public void setvalue(double price) {
        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Value: " + df.format(price));
        sub = Double.parseDouble(df.format(price));
        subtotal.setText("$" + String.format("%.02f", sub));

        Salestax = price * cartdetailsHit.getCartInfo().getSalesPercentage() / 100;

        promocode_txt.setText("$0.00");
        promocode = 0.00;
        promotxt = "";

        Totalamount();
    }

    //calculating total bill amount
    public void Totalamount() {
        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Value: " + df.format(Salestax));
        Salestax = Double.parseDouble(df.format(Salestax));
        cartsale.setText("$" + df.format(Salestax));


        if (disavail) {

            if (sub >= dislimit) {

                LL_discount_avail.setVisibility(View.GONE);
                LL_promohide.setVisibility(View.GONE);

                discount = cartdetailsHit.getCartInfo().getDiscount();
                cartdis.setText("- $" + df.format(discount));

                if (cartdetailsHit.getCartInfo().getDealDiscountType().equals("%")) {
                    double discountper = cartdetailsHit.getCartInfo().getDealDiscountValue();
                    double newdis = sub * discountper / 100;
                    try {
                        discount = Double.parseDouble(df.format(newdis));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cartdis.setText("- $" + df.format(discount));
                }

            } else {
                discount = 0;
                cartdis.setText("$0.00");
                LL_discount_avail.setVisibility(View.VISIBLE);
                LL_promohide.setVisibility(View.VISIBLE);
            }
        }


        if (sub < minorderamount) {
            if (tempisdel == 0) {
                LL_remain_money.setVisibility(View.GONE);
                minordergo = true;
            } else {
                LL_remain_money.setVisibility(View.VISIBLE);
                remain_dollar.setText("$" + df.format((minorderamount - sub)));
                minordergo = false;
            }

        } else {
            LL_remain_money.setVisibility(View.GONE);
            minordergo = true;
        }

        tip = Double.parseDouble(df.format(sub * tippercentage / 100));
        carttip.setText("$" + df.format(sub * tippercentage / 100));


        if (sub < FreeDeliveryLimitAmount) {

            basicKitInfo.setKitchenDelCharge("" + deliveryfee);


            deliveryfee = deliverycharge;
            cartdelfee.setText("$" + String.format("%.02f", deliveryfee));

            if (basicKitInfo.getIskitchendelivery() == 1) {
                deliveryfee = cartdetailsHit.getCartInfo().getDeliveryFee();
                cartdelfee.setText("$" + df.format(deliveryfee));
                basicKitInfo.setKitchenDelCharge("" + deliveryfee);
            }

            if (tempisdel == 0) {
                deliveryfee = 0;
                basicKitInfo.setKitchenDelCharge("0");
            }

            String data = gson.toJson(basicKitInfo);
            mTabel.putString("basickitcheninfo", data);
            mTabel.commit();

            totalbill = sub + tip + deliveryfee + Salestax - promocode - discount;

            System.out.println("Value: " + df.format(totalbill));
            grandtotal.setText("$" + df.format(totalbill));
            totalbill = Double.parseDouble(df.format(totalbill));

        } else
            {
            deliveryfee = 0;
            basicKitInfo.setKitchenDelCharge("" + deliveryfee);

            cartdelfee.setText("$0.00");

            if (basicKitInfo.getIskitchendelivery() == 1) {
                deliveryfee = cartdetailsHit.getCartInfo().getDeliveryFee();
                cartdelfee.setText("$" + df.format(deliveryfee));
                basicKitInfo.setKitchenDelCharge("" + deliveryfee);
            }

            if (tempisdel == 0) {
                deliveryfee = 0;
                basicKitInfo.setKitchenDelCharge("" + deliveryfee);
            }

            if(FreeDeliveryLimitAmount == 0)
            {
                deliveryfee = deliverycharge;
                basicKitInfo.setKitchenDelCharge("" + deliveryfee);
                cartdelfee.setText("$" + String.format("%.02f", deliveryfee));
            }

                String data = gson.toJson(basicKitInfo);
                mTabel.putString("basickitcheninfo", data);
                mTabel.commit();


                totalbill = (sub + tip + deliveryfee + Salestax) - promocode - discount;

            System.out.println("Value: " + df.format(totalbill));
            grandtotal.setText("$" + df.format(totalbill));
            totalbill = Double.parseDouble(df.format(totalbill));

        }
    }

    //upadting list items in cart
    public void updaterec() {

        if (storenotcustomizable.isEmpty()) {


            if (reorder) {
                finish();
            } else {

                intent = new Intent(Chart.this, Kitchen.class);
                intent.putExtra("kitchentitle", k);
                Chart.this.startActivity(intent);
            }
        } else {
            ArrayList<CartItem> newarray = new ArrayList<>();
            for (int i = 0; i < storenotcustomizable.size(); i++) {
                CartItem cartItem = new CartItem();
                cartItem.setDishId(storenotcustomizable.get(i).getDishId());
                cartItem.setQuantity(storenotcustomizable.get(i).getQuantity());
                cartItem.setDishName(storenotcustomizable.get(i).getDishname());
                cartItem.setPrice(storenotcustomizable.get(i).getPrice());
                cartItem.setCustomItemsName(storenotcustomizable.get(i).getItemsName());
                cartItem.setCustomItemsCost(storenotcustomizable.get(i).getItemsCost());
                cartItem.setIsCustomizable(storenotcustomizable.get(i).getIsCustomizable());
                newarray.add(cartItem);
            }

            chartAdapter = new ChartAdapter(Chart.this, newarray, reorder);
            recyclerView_chart = (RecyclerView) findViewById(R.id.recycle_chart);
            recyclerView_chart.setNestedScrollingEnabled(false);
            recyclerView_chart.setFocusable(false);
            recyclerView_chart.setLayoutManager(new LinearLayoutManager(Chart.this, LinearLayoutManager.VERTICAL, true));
            recyclerView_chart.setAdapter(chartAdapter);
        }
    }

    // closing keyboard
    private void closeKB() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        try {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //dialog of no intrernet
    public void nointernet() {
        //dialog intialization
        dialog = new Dialog(Chart.this);
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
                if (choose == 0) {
                    //cartdetails
                    try {
                        load_dialog.show();
                        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                        oInsertUpdateApi.setRequestSource(RequestSource.cartdetails);
                        oInsertUpdateApi.executeGetRequest(API.cardetailshit() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken());

                        String fullapi = API.cardetailshit() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
                        String fullapii = API.cardetailshit() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken();
                    } catch (Exception e) {

                    }
                } else {
                    String orderid = getIntent().getStringExtra("foodorderid");
                    orderDetailsReceive = new OrderDetailsReceive();

                    try {
                        mProgressDialog.setMessage("Re-ordering your food");
                        load_dialog.show();
                        OrderDetailsHit orderDetailsHit = new OrderDetailsHit();
                        orderDetailsHit.setCurrentDate(date_format);
                        orderDetailsHit.setSessionToken(basicInformation.getSessionToken().toString());
                        orderDetailsHit.setUserId(basicInformation.getUserId().toString());
                        orderDetailsHit.setOrderId(orderid);

                        String jsonString = gson.toJson(orderDetailsHit, OrderDetailsHit.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.orderhistorydetails(), jsonString);
                        oChangePsswordApi.setRequestSource(RequestSource.reorder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

//    public void newaddress() {
//        //dialog intialization
//        dialog = new Dialog(Chart.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.choose_cart);
//        dialog.setCancelable(true);
//
//        LinearLayout addressbook = (LinearLayout) dialog.findViewById(R.id.ll_choose_book);
//        LinearLayout addnew = (LinearLayout) dialog.findViewById(R.id.ll_add_new);
//
//        addressbook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.cancel();
//                intent = new Intent(Chart.this, AddressBook.class);
//                intent.putExtra("fromcartclick", true);
//                Chart.this.startActivity(intent);
//            }
//        });
//
//        addnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.cancel();
//                intent = new Intent(Chart.this, AddAddress.class);
//                intent.putExtra("fromcartclick", true);
//                Chart.this.startActivity(intent);
//            }
//        });
//
//        dialog.show();
//    }


    //
    public static void setnewadd(String s, String locality) {
        finaladdress = s;
        localityuser=locality;
        cartaddress.setText(finaladdress);
        total_address=finaladdress;
    }
}