package com.saavor.user.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.saavor.user.Classes.TimerView;
import com.saavor.user.Model.OrderStatusList;
import com.saavor.user.Model.OrderStatusResponse;
import com.saavor.user.R;
import com.saavor.user.Utils.Utils;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Order_Status extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private ImageView back;
    ProgressBar mprogressBar;
    LinearLayout textViewTime;
    Button needhelp;
    TextView mReff_code, remaining;
    int shift = 0;
    public TimerView mTimerView;
    Date currentdate = null;
    Date deliverydate = null;
    Boolean submit_review = false;
    @BindView(R.id.img_received)
    ImageView received;
    @BindView(R.id.img_preparing)
    ImageView preparing;
    @BindView(R.id.ll_delivered)
    LinearLayout delivered;
    @BindView(R.id.ll_late)
    LinearLayout ll_late;
    @BindView(R.id.img_ready)
    ImageView ready;
    @BindView(R.id.img_ofd)
    ImageView ofd;
    @BindView(R.id.img_delivered)
    ImageView delivere;
    @BindView(R.id.txt_received_time)
    TextView txt_rec;
    @BindView(R.id.deltime)
    TextView deltime;
    @BindView(R.id.day_dot)
    TextView daydot;
    @BindView(R.id.hour_dot)
    TextView hourdot;
    @BindView(R.id.min_dot)
    TextView mindot;
    @BindView(R.id.txt_prep_time)
    TextView txt_prep;
    @BindView(R.id.txt_ready_time)
    TextView txt_ready;
    @BindView(R.id.txt_ofd_time)
    TextView txt_ofd;
    @BindView(R.id.txt_del_time)
    TextView txt_del;
    Handler handler;
    int isreview = 0;
    int isulyx=0;
    int isulyxreview=0;
    private OrderStatusResponse orderStatusResponse = new OrderStatusResponse();
    ArrayList<OrderStatusList> orderStatusLists = new ArrayList<>();

    String kitchenprofileid, foodOrderId,orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__status);

        ButterKnife.bind(this);
        basicfetch();
        mOnResultReceived = this;
        mReff_code = (TextView) findViewById(R.id.os_refcode);
        mReff_code.setText(basicInformation.getRefferal().toString());

        back = (ImageView) findViewById(R.id.tool_back_orderstatus);
        back.setOnClickListener(this);

        textViewTime = (LinearLayout) findViewById(R.id.reverse_timer);
        remaining = (TextView) findViewById(R.id.txt_delivered);
        needhelp = (Button) findViewById(R.id.but_orders_nh);
        needhelp.setOnClickListener(this);

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mTimerView = (TimerView) findViewById(R.id.timer);
        isreview = getIntent().getIntExtra("isreview", 0);
        isulyx = getIntent().getIntExtra("isulyx", 0);
        isulyxreview = getIntent().getIntExtra("isulyxreview", 0);

        try {
            Intent intent = getIntent();
            foodOrderId = intent.getStringExtra(Utils.ORDER_STATUS_FOOD_ORDER_ID);
            orderId = intent.getStringExtra(Utils.ORDER_STATUS_ORDER_ID);
            kitchenprofileid = intent.getStringExtra("kitchenprofileid");
            mReff_code.setText(orderId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        mProgressDialog.setMessage("Loading Order Status");
        load_dialog.show();
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.executeGetRequest(API.GetOrderStatus() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + foodOrderId);
        oInsertUpdateApi.setRequestSource(RequestSource.timestatus);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tool_back_orderstatus:
                handler.removeCallbacksAndMessages(null);
                finish();
                break;

            case R.id.but_orders_nh:

                if (submit_review) {

                    if(isulyx == 1)
                    {
                        if(isulyxreview == 0 )
                        {
                            intent = new Intent(this, Ulyx_Rating.class);
                            intent.putExtra("kitchenprofileid",kitchenprofileid );
                            intent.putExtra("foodorderid",foodOrderId );
                            intent.putExtra("ulyx",isulyx );
                            intent.putExtra("isreview",isreview );
                            this.startActivity(intent);
                        }
                        else if(isreview == 0)
                        {
                            intent = new Intent(this, SubmitReview.class);
                            intent.putExtra("kitchenprofileid",kitchenprofileid );
                            intent.putExtra("foodorderid",foodOrderId );
                            this.startActivity(intent);
                        }
                        else
                        {
                            intent = new Intent(this, NeedHelp.class);
                            intent.putExtra("foodorderid", foodOrderId);
                            this.startActivity(intent);
                        }
                    }
                    else
                    {
                        if(isreview == 0)
                        {
                            intent = new Intent(this, SubmitReview.class);
                            intent.putExtra("kitchenprofileid",kitchenprofileid );
                            intent.putExtra("foodorderid",foodOrderId );
                            this.startActivity(intent);
                        }
                        else
                        {
                            intent = new Intent(this, NeedHelp.class);
                            intent.putExtra("foodorderid", foodOrderId);
                            this.startActivity(intent);
                        }
                    }



                } else {
                    intent = new Intent(this, NeedHelp.class);
                    intent.putExtra("foodorderid",foodOrderId);
                    this.startActivity(intent);
                }





                break;
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
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.executeGetRequest(API.GetOrderStatus() + basicInformation.getUserId() + "/" + basicInformation.getSessionToken() + "/" + foodOrderId);
                    oInsertUpdateApi.setRequestSource(RequestSource.orderstatus);

                    handler.postDelayed(this, 5000);
                }
            }, 5000);

        }

    @Override
    public void onBackPressed() {
        // do nothing.
        handler.removeCallbacksAndMessages(null);
        finish();
    }

    @Override
    public void dispatchString(final RequestSource from, final String what) {

        if (what.equals("-2")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();

                    AlertDialog.Builder mDialog = new AlertDialog.Builder(Order_Status.this);
                    AlertDialog alertDialog = mDialog.create();

                  if(alertDialog.isShowing())
                  {

                  }
                  else
                  {
                      mDialog.setTitle("Oh dear!");
                      mDialog.setMessage("No internet access. Please turn on cellular data or use wifi.")
                              .setCancelable(false)
                              .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {

                                      startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                                      dialog.dismiss();
                                  }
                              }).show();
                  }


                }
            });
        } else if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Order_Status.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {

            orderStatusLists = new ArrayList<>();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (from.toString().equalsIgnoreCase("orderstatus"))
                    {
                        try {

                            JSONObject Jsonobject = new JSONObject(what);
                            String jsonString = Jsonobject.toString();


                            gson = new Gson();
                            orderStatusResponse = gson.fromJson(jsonString, OrderStatusResponse.class);
                            System.out.println(">>>>" + what);
                            String check = orderStatusResponse.getReturnCode();
                            final String message = orderStatusResponse.getReturnMessage();


                            if (check.equals("1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                        orderStatusLists = orderStatusResponse.getOrderStatuses().getOrderStatusList();

                                        for (int i = 0; i < orderStatusLists.size(); i++) {
                                            String orderStatus = orderStatusLists.get(i).getOrderStatus();
                                            if (orderStatus.equalsIgnoreCase("Accepted")) {

                                                try {
                                                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                                                    submit_review = false;
                                                    txt_rec.setText(datee);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                                received.setImageResource(R.drawable.deliverdtick);
                                                //  txt_rec.setText(orderStatusLists.get(i).getOrderModifiedDate());
                                            } else if (orderStatus.equalsIgnoreCase("Preparing")) {
                                                try {
                                                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                                                    submit_review = false;
                                                    txt_prep.setText(datee);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                preparing.setImageResource(R.drawable.deliverdtick);
                                                //  txt_prep.setText(orderStatusLists.get(i).getOrderModifiedDate());
                                            } else if (orderStatus.equalsIgnoreCase("Ready")) {
                                                try {
                                                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                                                    submit_review = false;
                                                    txt_ready.setText(datee);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                ready.setImageResource(R.drawable.deliverdtick);
                                                //txt_ready.setText(orderStatusLists.get(i).getOrderModifiedDate());
                                            } else if (orderStatus.equalsIgnoreCase("Out for delivery")) {
                                                try {
                                                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                                                    submit_review = false;
                                                    txt_ofd.setText(datee);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                ofd.setImageResource(R.drawable.deliverdtick);
                                                // txt_ofd.setText(orderStatusLists.get(i).getOrderModifiedDate());
                                            } else if (orderStatus.equalsIgnoreCase("Delivered")) {
                                                handler.removeCallbacksAndMessages(null);
                                                try {
                                                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                                                    txt_del.setText(datee);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                ofd.setImageResource(R.drawable.deliverdtick);
                                                txt_ofd.setText(txt_ready.getText().toString());
                                                // txt_del.setText(orderStatusLists.get(i).getOrderModifiedDate());
                                                delivere.setImageResource(R.drawable.deliverdtick);

                                                 submit_review = true;
//                                                needhelp.setText("Submit Review");

                                                mTimerView.setBackgroundResource(R.drawable.circleshape);
                                                textViewTime.setVisibility(View.GONE);
                                                remaining.setVisibility(View.GONE);
                                                delivered.setVisibility(View.VISIBLE);

                                                String myString = orderStatusLists.get(i).getOrderModifiedDate();
                                                String month = "", date = "", year = "";

                                                String[] aSplit = myString.split(" ");

                                                month = aSplit[0];
                                                date = aSplit[1];
                                                year = aSplit[2];

                                                System.out.println(month + " " + date + "," + year);
                                                deltime.setText("at " + date + " " + year);

                                                delivered.setVisibility(View.VISIBLE);
                                                //  deltime.setText();

                                                if(submit_review)
                                                {
                                                    if(isulyx == 1)
                                                    {
                                                        if(isulyxreview == 0 || isreview == 0)
                                                        {
                                                            needhelp.setText("Submit Review");
                                                        }
                                                        else
                                                        {
                                                            needhelp.setText("Need Help ?");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if(isreview == 0)
                                                        {
                                                            needhelp.setText("Submit Review");
                                                        }
                                                        else
                                                        {
                                                            needhelp.setText("Need Help ?");
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    needhelp.setText("Need Help ?");
                                                }
                                            }
                                        }

                                        load_dialog.cancel();

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
                                        displayAlert(Order_Status.this, message);
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

                        } catch (Exception e)

                        {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    load_dialog.cancel();

                                }
                            });
                        }
                    }
                    else if(from.toString().equalsIgnoreCase("timestatus"))
                    {
                        try {

                            JSONObject Jsonobject = new JSONObject(what);
                            String jsonString = Jsonobject.toString();

                            gson = new Gson();
                            orderStatusResponse = gson.fromJson(jsonString, OrderStatusResponse.class);
                            System.out.println(">>>>" + what);
                            String check = orderStatusResponse.getReturnCode();
                            final String message = orderStatusResponse.getReturnMessage();


                            if (check.equals("1")) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        orderStatusLists = orderStatusResponse.getOrderStatuses().getOrderStatusList();
                                        try {
                                            String dateStr = date_format;
                                            DateFormat srcDf = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);
                                            // parse the date string into Date object
                                            Date date = srcDf.parse(dateStr);
                                            DateFormat destDf = new SimpleDateFormat("MM-dd-yyyy hh:mm aa", Locale.ENGLISH);

                                            // format the date into another format
                                            dateStr = destDf.format(date);
                                            currentdate = destDf.parse(dateStr);
                                            deliverydate = destDf.parse(orderStatusResponse.getOrderStatuses().getDeliveryTime());

                                            System.out.println("Converted date is : " + dateStr);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        printDifference(currentdate, deliverydate);

                                        updateview();


                                        load_dialog.cancel();

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
                                        displayAlert(Order_Status.this, message);
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

                        } catch (Exception e)

                        {
                            System.out.println(">>>>" + e);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    load_dialog.cancel();

                                }
                            });
                        }
                    }


                }


            });
        }

    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        String check = "" + different;
        int evaluate = check.indexOf('-');

        if (evaluate == -1) {

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            System.out.printf("%d days, %d hours, %d minutes, %d seconds%n", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

            long day = TimeUnit.DAYS.toMillis(elapsedDays);     // 1 day to milliseconds.
            long min = TimeUnit.MINUTES.toMillis(elapsedMinutes); // 23 minutes to milliseconds.
            long hour = TimeUnit.HOURS.toMillis(elapsedHours);    // 4 hours to milliseconds.
            long sec = TimeUnit.SECONDS.toMillis(elapsedSeconds); // 96 seconds to milliseconds.


            final TextView days = (TextView) findViewById(R.id.days);
            final TextView hours = (TextView) findViewById(R.id.hours);
            final TextView mins = (TextView) findViewById(R.id.minutes);
            final TextView seconds = (TextView) findViewById(R.id.seconds);


            if (day > 0) {
                textViewTime.setVisibility(View.VISIBLE);
                remaining.setVisibility(View.VISIBLE);
                mins.setVisibility(View.GONE);
                seconds.setVisibility(View.GONE);
                hourdot.setVisibility(View.GONE);
                mindot.setVisibility(View.GONE);
                remaining.setText("Days" + '\n' + "remaining");

            } else if (hour > 0) {
                textViewTime.setVisibility(View.VISIBLE);
                remaining.setVisibility(View.VISIBLE);
                days.setVisibility(View.GONE);
                seconds.setVisibility(View.GONE);
                mindot.setVisibility(View.GONE);
                daydot.setVisibility(View.GONE);
                remaining.setText("Hour" + '\n' + "remaining");

            } else if (min > 0) {
                textViewTime.setVisibility(View.VISIBLE);
                remaining.setVisibility(View.VISIBLE);
                days.setVisibility(View.GONE);
                hours.setVisibility(View.GONE);
                hourdot.setVisibility(View.GONE);
                daydot.setVisibility(View.GONE);
                remaining.setText("min" + '\n' + "remaining");

            }


            new CountDownTimer(day + min + hour + sec, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
            /*            converting the milliseconds into days, hours, minutes and seconds and displaying it in textviews             */
                    days.setText(" " + String.format("%02d", TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))));
                    hours.setText(" " + String.format("%02d", (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)))));
                    mins.setText(" " + String.format("%02d", (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)))));
                    seconds.setText(" " + String.format("%02d", (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))));
                }

                @Override
                public void onFinish() {

//                textViewTime.setVisibility(View.GONE);
//                remaining.setVisibility(View.GONE);
                    // delivered.setVisibility(View.VISIBLE);
                    ll_late.setVisibility(View.VISIBLE);

                    mins.setText("00");
                    seconds.setText(":00");
                    // updateview();
                }
            }.start();

            int i = (int) (day + min + hour + sec) / 1000;
            mTimerView.start(i);

        } else {

            updateview();

        }



    }

    public String dateconvert(String orderModifiedDate) throws ParseException {
        String sdate = "" + orderModifiedDate;
        SimpleDateFormat spf = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.ENGLISH);
        Date newDate = spf.parse(sdate);
        spf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa", Locale.ENGLISH);
        sdate = spf.format(newDate);
        System.out.println(sdate);

        return sdate;
    }

    public void updateview() {



        for (int i = 0; i < orderStatusLists.size(); i++) {
            String orderStatus = orderStatusLists.get(i).getOrderStatus();
            if (orderStatus.equalsIgnoreCase("Accepted")) {

                try {
                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                    submit_review = false;
                    txt_rec.setText(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                received.setImageResource(R.drawable.deliverdtick);
                //  txt_rec.setText(orderStatusLists.get(i).getOrderModifiedDate());
            } else if (orderStatus.equalsIgnoreCase("Preparing")) {
                try {
                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                    submit_review = false;
                    txt_prep.setText(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                preparing.setImageResource(R.drawable.deliverdtick);
                //  txt_prep.setText(orderStatusLists.get(i).getOrderModifiedDate());
            } else if (orderStatus.equalsIgnoreCase("Ready")) {
                try {
                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                    submit_review = false;
                    txt_ready.setText(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ready.setImageResource(R.drawable.deliverdtick);
                //txt_ready.setText(orderStatusLists.get(i).getOrderModifiedDate());
            } else if (orderStatus.equalsIgnoreCase("Sent")) {
                try {
                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                    submit_review = false;
                    txt_ofd.setText(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ofd.setImageResource(R.drawable.deliverdtick);
                // txt_ofd.setText(orderStatusLists.get(i).getOrderModifiedDate());
            } else if (orderStatus.equalsIgnoreCase("Delivered")) {
                handler.removeCallbacksAndMessages(null);

                try {
                    String datee = dateconvert(orderStatusLists.get(i).getOrderModifiedDate());
                    txt_del.setText(datee);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ofd.setImageResource(R.drawable.deliverdtick);
                txt_ofd.setText(txt_ready.getText().toString());
                // txt_del.setText(orderStatusLists.get(i).getOrderModifiedDate());
                delivere.setImageResource(R.drawable.deliverdtick);
//
                 submit_review = true;
//                needhelp.setText("Submit Review");

                mTimerView.setBackgroundResource(R.drawable.circleshape);
                textViewTime.setVisibility(View.GONE);
                remaining.setVisibility(View.GONE);
                delivered.setVisibility(View.VISIBLE);

                String myString = orderStatusLists.get(i).getOrderModifiedDate();
                String month = "", date = "", year = "";

                String[] aSplit = myString.split(" ");

                month = aSplit[0];
                date = aSplit[1];
                year = aSplit[2];

                System.out.println(month + " " + date + "," + year);
                deltime.setText("at " + date + " " + year);

                delivered.setVisibility(View.VISIBLE);

                if(submit_review)
                {
                    if(isulyx == 1)
                    {
                        if(isulyxreview == 0 || isreview == 0)
                        {

                            submit_review = true;
                            needhelp.setText("Submit Review");
                        }
                        else
                        {   submit_review = false;
                            needhelp.setText("Need Help ?");
                        }
                    }
                    else
                    {
                        if(isreview == 0)
                        {

                            submit_review = true;
                            needhelp.setText("Submit Review");
                        }
                        else
                        {    submit_review = false;
                            needhelp.setText("Need Help ?");
                        }
                    }
                }
                else
                {
                    needhelp.setText("Need Help ?");
                }


            }
        }
    }
}
