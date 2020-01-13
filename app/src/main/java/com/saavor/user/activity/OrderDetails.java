package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.OrderDetailsHit;
import com.saavor.user.Model.OrderDetailsReceive;
import com.saavor.user.Model.OrderDish;
import com.saavor.user.R;
import com.saavor.user.Utils.Utils;
import com.saavor.user.adapter.OrderDetailsAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetails extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_orderdetails;
    private OrderDetailsAdapter orderDetailsAdapter;
    private Button order_need;
    private OrderDetailsReceive orderDetailsReceive;
    private ArrayList<OrderDish> orderDishes = new ArrayList<>();

    String orderid;

    @BindView(R.id.odid_Kit_name)
    TextView od_name;
    @BindView(R.id.img_deltype)
    ImageView imgdeltype;
    @BindView(R.id.txt_deltype)
    TextView txtdeltype;
    @BindView(R.id.odid_address)
    TextView od_kit_add;
    @BindView(R.id.odid_time)
    TextView od_time;
    @BindView(R.id.odid_id)
    TextView od_id;
    @BindView(R.id.odid_date)
    TextView od_date;
    @BindView(R.id.odid_slots)
    TextView od_slots;
    @BindView(R.id.odid_add_cus)
    TextView od_cus_add;
    @BindView(R.id.odid_sub_totall)
    TextView od_sub_total;
    @BindView(R.id.odio_fee)
    TextView od_delfee;
    @BindView(R.id.odio_dis)
    TextView od_dis;
    @BindView(R.id.odid_cooking)
    TextView od_cook;
    @BindView(R.id.odid_delivery)
    TextView od_del;
    @BindView(R.id.orderIDTv)
    TextView orderIDTv;
    @BindView(R.id.odid_grandtotal)
    TextView grandtotal;
    @BindView(R.id.txt_tip)
    TextView od_tip;
    @BindView(R.id.odid_taxes)
    TextView od_salestax;
    @BindView(R.id.kitchen_Status)
    TextView kitchen_Status;
    @BindView(R.id.txt_cardty_od)
    TextView cardtype;
    @BindView(R.id.txt_cardnum)
    TextView cardnum;
    @BindView(R.id.od_promo_dis)
    TextView od_promo;
    @BindView(R.id.od_salestax)
    TextView odsalestax;
    @BindView(R.id.ll_cooking_inst)
    LinearLayout LL_cookinst;
    @BindView(R.id.ll_deliveryinst)
    LinearLayout LL_delinst;
    @BindView(R.id.ll_cart_address)
    LinearLayout LL_cart_address;

    int iskitchendelivery=0;

    double z = 0;

    //near array
    ArrayList<String> near_title = new ArrayList<>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        basicfetch();

        mOnResultReceived = this;

        ButterKnife.bind(this);

        orderid = getIntent().getStringExtra("foodorderid");
        iskitchendelivery= getIntent().getIntExtra("iskitchend",0);
        orderDetailsReceive = new OrderDetailsReceive();

        back = (ImageView) findViewById(R.id.img_back_od);
        back.setOnClickListener(this);

        near_title.add("$50");
        near_title.add("60$");
        near_title.add("70$");
        near_title.add("10$");
        near_title.add("20$");

        order_need = (Button) findViewById(R.id.btn_order_need);
        order_need.setOnClickListener(this);

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mProgressDialog.setMessage("fetching order details");
            load_dialog.show();
            OrderDetailsHit orderDetailsHit = new OrderDetailsHit();
            orderDetailsHit.setCurrentDate(date_format);
            orderDetailsHit.setSessionToken(basicInformation.getSessionToken().toString());
            orderDetailsHit.setUserId(basicInformation.getUserId().toString());
            orderDetailsHit.setOrderId(orderid);

            String jsonString = gson.toJson(orderDetailsHit, OrderDetailsHit.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.executePostRequest(API.orderhistorydetails(), jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back_od:

                finish();
                break;

            case R.id.btn_order_need:

                intent = new Intent(this, NeedHelp.class);
                intent.putExtra("foodorderid", orderid);
                this.startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
        finish();
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

       if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(OrderDetails.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
        String t = what;

        try {

            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();

            gson = new Gson();
            orderDetailsReceive = gson.fromJson(jsonString, OrderDetailsReceive.class);
            System.out.println(">>>>" + what);
            String check = orderDetailsReceive.getReturnCode();
            final String message = orderDetailsReceive.getReturnMessage();


            if (check.equals("1")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //change date format
                        Utils utils = new Utils();
                        DecimalFormat df = new DecimalFormat("####0.00");
                        od_name.setText(orderDetailsReceive.getOrderDetails().getKitchenName().toString());
                        od_kit_add.setText(orderDetailsReceive.getOrderDetails().getKitchenAddress().toString());
                        od_time.setText(utils.changeDateTimeFormat(orderDetailsReceive.getOrderDetails().getOrderDate()));
                        od_slots.setText(orderDetailsReceive.getOrderDetails().getDeliveryFrom().toString() + " - " + orderDetailsReceive.getOrderDetails().getDeliveryTo().toString());
                        od_cus_add.setText(orderDetailsReceive.getOrderDetails().getCustomerAddress().toString());
                        od_cook.setText(orderDetailsReceive.getOrderDetails().getCookingInstruction().toString());
                        od_del.setText(orderDetailsReceive.getOrderDetails().getDeliveryInstruction().toString());
                        od_sub_total.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getTotalAmount()));
                        od_delfee.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getDeliveryFee()));

                        if(orderDetailsReceive.getOrderDetails().getPromoCodeDiscount()>0)
                        {
                            od_promo.setText("-$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getPromoCodeDiscount()));
                        }
                        else
                        {
                            od_promo.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getPromoCodeDiscount()));
                        }

                        if(orderDetailsReceive.getOrderDetails().getDealDiscount()>0)
                        {
                            od_dis.setText("-$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getDealDiscount()));
                        }
                        else
                        {
                            od_dis.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getDealDiscount()));
                        }

                        od_id.setText(orderDetailsReceive.getOrderDetails().getOrderId().toString());
                        grandtotal.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getTotalAmount()));
                        od_tip.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getTipAmount()));
                        od_salestax.setText("$" + String.format("%.02f", orderDetailsReceive.getOrderDetails().getSalesTax()));
                        cardnum.setText(orderDetailsReceive.getOrderDetails().getPaymentCardNumber());
                        cardtype.setText(orderDetailsReceive.getOrderDetails().getPaymentCardType().toString());


                        od_date.setText(utils.changeDateFormat(orderDetailsReceive.getOrderDetails().getDeliveryDate().toString()));

                        orderIDTv.setText("Order ID: " + orderDetailsReceive.getOrderDetails().getOrderNumber().toString());


                        if (orderDetailsReceive.getOrderDetails().getIsOpen() == 1) {
                            kitchen_Status.setText("Now Open");

                        } else {
                            kitchen_Status.setText("Close Now");
                        }

                        odsalestax.setText("Sales Tax("+df.format(orderDetailsReceive.getOrderDetails().getSalesPercentage())+"%)");
                        orderDishes = orderDetailsReceive.getOrderDetails().getOrderDishes();
                        orderDetailsAdapter = new OrderDetailsAdapter(OrderDetails.this, orderDishes);
                        recyclerView_orderdetails = (RecyclerView) findViewById(R.id.recycle_orderdetails);
                        recyclerView_orderdetails.setNestedScrollingEnabled(false);
                        recyclerView_orderdetails.setLayoutManager(new LinearLayoutManager(OrderDetails.this, LinearLayoutManager.VERTICAL, true));
                        recyclerView_orderdetails.setAdapter(orderDetailsAdapter);
                        recyclerView_orderdetails.setFocusable(false);
                        load_dialog.cancel();


                        if (orderDetailsReceive.getOrderDetails().getDeliveryInstruction().toString().equals("")) {

                            LL_delinst.setVisibility(View.GONE);
                        }
                        else
                        {
                            LL_delinst.setVisibility(View.VISIBLE);
                        }



                        if (od_cook.getText().toString().equals("")) {
                            LL_cookinst.setVisibility(View.GONE);
                        }
                        else
                        {
                            LL_cookinst.setVisibility(View.VISIBLE);
                        }

                        if(orderDetailsReceive.getOrderDetails().getIsDelivery() == 1)
                        {
                            imgdeltype.setBackgroundResource(R.drawable.ic_deliveryk_icon);
                            LL_cart_address.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            imgdeltype.setBackgroundResource(R.drawable.ic_pickupk_icon);
                            txtdeltype.setText("Pick up");
                            LL_cart_address.setVisibility(View.GONE);
                        }

                    }
                });
            } else if (check.equals("0")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(OrderDetails.this, message);

                    }
                });
            } else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        displayAlert(OrderDetails.this, message);
                    }
                });
            }

            else if (check.equals("5")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Sessionexp",1);
                        startActivity(intent);
                    }
                });}


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
    }}

    public void setsub(double i) {
        z = z + i;
        od_sub_total.setText("$" + String.format("%.02f", z));
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


    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(OrderDetails.this);
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
                    mProgressDialog.setMessage("fetching order details");
                    load_dialog.show();
                    OrderDetailsHit orderDetailsHit = new OrderDetailsHit();
                    orderDetailsHit.setCurrentDate(date_format);
                    orderDetailsHit.setSessionToken(basicInformation.getSessionToken().toString());
                    orderDetailsHit.setUserId(basicInformation.getUserId().toString());
                    orderDetailsHit.setOrderId(orderid);

                    String jsonString = gson.toJson(orderDetailsHit, OrderDetailsHit.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.orderhistorydetails(), jsonString);

                } catch (Exception e) {
                    e.printStackTrace();
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

}
