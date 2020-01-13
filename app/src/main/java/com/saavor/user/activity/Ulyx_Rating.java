package com.saavor.user.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.saavor.user.Model.Driverdetail_return;
import com.saavor.user.Model.InsertReview;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;

import org.json.JSONObject;

public class Ulyx_Rating extends BaseActivity implements View.OnClickListener, OnResultReceived {

    String kitchenid, foodid;
    private ImageView heart1, heart2, heart3, heart4, heart5, back ,driver_img;
    private Button submit, skip;
    InsertReview insertReview;
    int ulyx;
    Driverdetail_return driverdetail_return;
    TextView drivername;
    ProgressBar progressBar;
    int isreview = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ulyx_rating_diag);

        mOnResultReceived = this;
        basicfetch();
        kitchenid = getIntent().getStringExtra("kitchenprofileid");
        foodid = getIntent().getStringExtra("foodorderid");
        ulyx= getIntent().getIntExtra("ulyx",0);

        insertReview = new InsertReview();
        progressBar= (ProgressBar) findViewById(R.id.pd_driver);
        heart1 = (ImageView) findViewById(R.id.img_heart1_d);
        heart2 = (ImageView) findViewById(R.id.img_heart2_d);
        heart3 = (ImageView) findViewById(R.id.img_heart3_d);
        heart4 = (ImageView) findViewById(R.id.img_heart4_d);
        heart5 = (ImageView) findViewById(R.id.img_heart5_d);
        back = (ImageView) findViewById(R.id.img_back_dr);
        submit = (Button) findViewById(R.id.btn_sub_driver);
        skip = (Button) findViewById(R.id.btn_skip_driver);
        drivername= (TextView) findViewById(R.id.txt_driver_name);
        driver_img= (ImageView) findViewById(R.id.img_driver);

        heart1.setOnClickListener(this);
        heart2.setOnClickListener(this);
        heart3.setOnClickListener(this);
        heart4.setOnClickListener(this);
        heart5.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        skip.setOnClickListener(this);
        insertReview.setStars("0");

        isreview = getIntent().getIntExtra("isreview", 0);

        load_dialog.show();
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.executeGetRequest(API.driverdetail()+basicInformation.getUserId()+"/"+basicInformation.getSessionToken()+"/"+foodid);
        oInsertUpdateApi.setRequestSource(RequestSource.driverdetail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_heart1_d:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.greyheart);
                heart3.setImageResource(R.drawable.greyheart);
                heart4.setImageResource(R.drawable.greyheart);
                heart5.setImageResource(R.drawable.greyheart);
                insertReview.setStars("1");

                break;

            case R.id.img_back_dr:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent = new Intent(getApplicationContext(), OrderHistory.class);
                startActivity(intent);

                break;

            case R.id.btn_sub_driver:

                try {
                    load_dialog.show();

                    insertReview.setReviewDate(date_format);
                    insertReview.setSessionToken(basicInformation.getSessionToken().toString());
                    insertReview.setReviews("");
                    insertReview.setProfileId(kitchenid);
                    insertReview.setUserId(basicInformation.getUserId());
                    insertReview.setFoodOrderId(foodid);

                    String jsonString = gson.toJson(insertReview, InsertReview.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.setRequestSource(RequestSource.submit_rating_d);
                    oChangePsswordApi.executePostRequest(API.ulyxrating(), jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.img_heart2_d:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.greyheart);
                heart4.setImageResource(R.drawable.greyheart);
                heart5.setImageResource(R.drawable.greyheart);

                insertReview.setStars("2");

                break;

            case R.id.img_heart3_d:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.redreview);
                heart4.setImageResource(R.drawable.greyheart);
                heart5.setImageResource(R.drawable.greyheart);

                insertReview.setStars("3");

                break;

            case R.id.img_heart4_d:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.redreview);
                heart4.setImageResource(R.drawable.redreview);
                heart5.setImageResource(R.drawable.greyheart);

                insertReview.setStars("4");

                break;

            case R.id.img_heart5_d:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.redreview);
                heart4.setImageResource(R.drawable.redreview);
                heart5.setImageResource(R.drawable.redreview);

                insertReview.setStars("5");

                break;

            case R.id.btn_skip_driver:


                if(isreview == 1)
                {
                    intent = new Intent(getApplicationContext(), SubmitReview.class);
                    intent.putExtra("kitchenprofileid",kitchenid );
                    intent.putExtra("foodorderid",foodid );
                    intent.putExtra("isulyx",ulyx );
                    Ulyx_Rating.this.startActivity(intent);
                }
                else
                {
                    intent = new Intent(getApplicationContext(), OrderHistory.class);
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

        String t = what;
        load_dialog.cancel();

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Ulyx_Rating.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {

            if (from.toString().equalsIgnoreCase("driverdetail")) {

                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    driverdetail_return = new Driverdetail_return();

                    gson = new Gson();
                    driverdetail_return = gson.fromJson(jsonString, Driverdetail_return.class);
                    System.out.println(">>>>" + what);
                    String check = driverdetail_return.getReturnCode();
                    final String message = driverdetail_return.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                drivername.setText(""+driverdetail_return.getDriverName());

                                String internetUrl = driverdetail_return.getProfilePicPath();
                                Glide.with(Ulyx_Rating.this).load(internetUrl).listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        progressBar.setVisibility(View.GONE);
                                        driver_img.setImageResource(R.drawable.usericonpd);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        progressBar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                        .into(driver_img);

                            }});}
                    else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                               // load_dialog.cancel();
                                // displayAlert(SubmitReview.this, message);
                                //  submitdiag();
                            }
                        });
                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                             //   load_dialog.cancel();
                                displayAlert(Ulyx_Rating.this, message);
                            }
                        });
                    } else if (check.equals("5")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              //  load_dialog.cancel();
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
                         //   load_dialog.cancel();
                            // redirect(SubmitReview.this, "No internet access. Please turn on cellular data or use wifi.");
                            //nointernet();
                        }
                    });
                }

            }
            else if(from.toString().equalsIgnoreCase("submit_rating_d"))
            {
                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    TodayReturn todayReturn = new TodayReturn();

                    gson = new Gson();
                    todayReturn = gson.fromJson(jsonString, TodayReturn.class);
                    System.out.println(">>>>" + what);
                    String check = todayReturn.getReturnCode();
                    final String message = todayReturn.getReturnMessage();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                               // Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                                displayAlert(Ulyx_Rating.this, "" + message);

                                load_dialog.cancel();

                                if(isreview == 1)
                                {
                                    intent = new Intent(getApplicationContext(), SubmitReview.class);
                                    intent.putExtra("kitchenprofileid",kitchenid );
                                    intent.putExtra("foodorderid",foodid );
                                    intent.putExtra("isulyx",ulyx );
                                    Ulyx_Rating.this.startActivity(intent);
                                }
                                else
                                {
                                    intent = new Intent(getApplicationContext(), OrderHistory.class);
                                    startActivity(intent);
                                }


                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                // displayAlert(SubmitReview.this, message);
                                //  submitdiag();
                            }
                        });
                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(Ulyx_Rating.this, message);
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
                            // redirect(SubmitReview.this, "No internet access. Please turn on cellular data or use wifi.");
                            //nointernet();
                        }
                    });
                }
            }


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent = new Intent(getApplicationContext(), OrderHistory.class);
        startActivity(intent);
    }
}
