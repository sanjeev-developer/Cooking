package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saavor.user.Model.BookmarkViewModel;
import com.saavor.user.Model.InsertReview;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.Utils.Utils;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;
import static com.saavor.user.activity.DashBoard.totalprice;

public class SubmitReview extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private ImageView heart1, heart2, heart3, heart4, heart5, back;
    private Button submit;
    private InsertReview insertReview= new InsertReview();
    private EditText edt_review;
    String kitchenid,foodid;
    int ulyx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_review);

        mOnResultReceived = this;
        basicfetch();
        kitchenid=getIntent().getStringExtra("kitchenprofileid");
        foodid=getIntent().getStringExtra("foodorderid");
        ulyx =getIntent().getIntExtra("isulyx",0);
        heart1 = (ImageView) findViewById(R.id.img_heart1_r);
        heart2 = (ImageView) findViewById(R.id.img_heart2_r);
        heart3 = (ImageView) findViewById(R.id.img_heart3_r);
        heart4 = (ImageView) findViewById(R.id.img_heart4_r);
        heart5 = (ImageView) findViewById(R.id.img_heart5_r);
        back = (ImageView) findViewById(R.id.tool_back_sr);
        submit = (Button) findViewById(R.id.btn_submit_rr);

        edt_review= (EditText) findViewById(R.id.et_review);

        heart1.setOnClickListener(this);
        heart2.setOnClickListener(this);
        heart3.setOnClickListener(this);
        heart4.setOnClickListener(this);
        heart5.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);

        insertReview.setStars("0");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_heart1_r:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.greyheart);
                heart3.setImageResource(R.drawable.greyheart);
                heart4.setImageResource(R.drawable.greyheart);
                heart5.setImageResource(R.drawable.greyheart);
                insertReview.setStars("1");

                break;

            case R.id.tool_back_sr:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                if(ulyx == 1)
//                {
//                    intent = new Intent(getApplicationContext(), OrderHistory.class);
//                    startActivity(intent);
//                }
//                else
//                {
//                    finish();
//                }
                intent = new Intent(getApplicationContext(), OrderHistory.class);
                startActivity(intent);


                break;

            case R.id.btn_submit_rr:

                if (edt_review.getText().toString().equals("")) {
                    displayAlert(this, "Review Can't be blank");
                }
                else
                {
                    try {
                        load_dialog.show();

                        insertReview.setReviewDate(date_format);
                        insertReview.setSessionToken(basicInformation.getSessionToken().toString());
                        insertReview.setReviews(edt_review.getText().toString());
                        insertReview.setProfileId(kitchenid);
                        insertReview.setUserId(basicInformation.getUserId());
                        insertReview.setFoodOrderId(foodid);

                        String jsonString = gson.toJson(insertReview, InsertReview.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.submitreviews(), jsonString);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            case R.id.img_heart2_r:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.greyheart);
                heart4.setImageResource(R.drawable.greyheart);
                heart5.setImageResource(R.drawable.greyheart);

                insertReview.setStars("2");

                break;

            case R.id.img_heart3_r:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.redreview);
                heart4.setImageResource(R.drawable.greyheart);
                heart5.setImageResource(R.drawable.greyheart);

                insertReview.setStars("3");

                break;

            case R.id.img_heart4_r:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.redreview);
                heart4.setImageResource(R.drawable.redreview);
                heart5.setImageResource(R.drawable.greyheart);

                insertReview.setStars("4");

                break;

            case R.id.img_heart5_r:

                heart1.setImageResource(R.drawable.redreview);
                heart2.setImageResource(R.drawable.redreview);
                heart3.setImageResource(R.drawable.redreview);
                heart4.setImageResource(R.drawable.redreview);
                heart5.setImageResource(R.drawable.redreview);

                insertReview.setStars("5");

                break;
        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {
 if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(SubmitReview.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

        String t = what;

        try {

            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();


            TodayReturn todayReturn = new TodayReturn();

            gson = new Gson();
            todayReturn= gson.fromJson(jsonString, TodayReturn.class);
            System.out.println(">>>>" + what);
            String check = todayReturn.getReturnCode();
            final String message = todayReturn.getReturnMessage();


            if (check.equals("1")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();

                        //dialog intialization
                        dialog = new Dialog(SubmitReview.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.alert_diag);

                        Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
                        TextView alertext= (TextView) dialog.findViewById(R.id.text_alert);

                        alertext.setText(message);

                        okplaced.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.cancel();


                                intent = new Intent(getApplicationContext(), OrderHistory.class);
                                startActivity(intent);

                            }
                        });

                        dialog.show();

                    }
                });
            } else if (check.equals("0")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                       // displayAlert(SubmitReview.this, message);
                        submitdiag();

                    }
                });
            }

            else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(SubmitReview.this, message);
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
                   // redirect(SubmitReview.this, "No internet access. Please turn on cellular data or use wifi.");
                    nointernet();
                }
            });
        }
    }
    }


    @Override
    public void onBackPressed() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if(ulyx == 1)
//        {
//            intent = new Intent(getApplicationContext(), OrderHistory.class);
//            startActivity(intent);
//        }
//        else
//        {
//            finish();
//        }

        intent = new Intent(getApplicationContext(), OrderHistory.class);
        startActivity(intent);
    }

    public void submitdiag()
    {
        //dialog intialization
        dialog = new Dialog(SubmitReview.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.submit_diag);
        dialog.setCancelable(false);

        Button okplaced = (Button) dialog.findViewById(R.id.ok_submit);

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();

            }
        });

        dialog.show();
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(SubmitReview.this);
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

                if (edt_review.getText().toString().equals("")) {
                    displayAlert(SubmitReview.this, "Review Can't be blank");
                }
                else
                {
                    try {
                        load_dialog.show();

                        insertReview.setReviewDate(date_format);
                        insertReview.setSessionToken(basicInformation.getSessionToken().toString());
                        insertReview.setReviews(edt_review.getText().toString());
                        insertReview.setProfileId(kitchenid);
                        insertReview.setUserId(basicInformation.getUserId());
                        insertReview.setFoodOrderId(foodid);

                        String jsonString = gson.toJson(insertReview, InsertReview.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.submitreviews(), jsonString);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
