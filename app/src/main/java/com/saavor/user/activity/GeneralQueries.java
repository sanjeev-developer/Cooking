package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saavor.user.Model.BasicKitchenInfo;
import com.saavor.user.Model.NeedHelpHit;
import com.saavor.user.Model.ProfileUpdateModel;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;

public class GeneralQueries extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private ImageView back;
    private TextView title;
    private Button submit;
    String orderid;

    @BindView(R.id.gq_desc)
    EditText description;
    @BindView(R.id.gq_title)
    EditText titlee;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_queries);

        mOnResultReceived = this;
        basicfetch();

        orderid = getIntent().getStringExtra("foodorderid");
        description = (EditText) findViewById(R.id.gq_desc);
        titlee = (EditText) findViewById(R.id.gq_title);
        back = (ImageView) findViewById(R.id.tool_back_needf);
        title = (TextView) findViewById(R.id.title_gq);
        submit = (Button) findViewById(R.id.gq_submit);

        s = getIntent().getStringExtra("data");
        title.setText(s);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.gq_submit: /** Start a new Activity signup.java */

                if (titlee.getText().toString().equals("")) {
                    displayAlert(this, "Title Can't be blank");
                } else if (titlee.getText().toString().equals("")) {
                    displayAlert(this, "Description Can't be blank");
                } else {


                    try {
                        mProgressDialog.setMessage("submitting...");
                        load_dialog.show();

                        NeedHelpHit needHelpHit = new NeedHelpHit();
                        needHelpHit.setFoodOrderId(orderid);
                        needHelpHit.setSessionToken(basicInformation.getSessionToken().toString());
                        needHelpHit.setUserId(basicInformation.getUserId().toString());
                        needHelpHit.setDescription(description.getText().toString());
                        needHelpHit.setTopicName(s);
                        needHelpHit.setTopicTitle(titlee.getText().toString());
                        needHelpHit.setCreateDate(date_format);

                        String jsonString = gson.toJson(needHelpHit, NeedHelpHit.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.submitenquiry(), jsonString);
                    } catch (Exception e) {

                    }

                }
                break;

            case R.id.tool_back_needf: /** Start a new Activity signup.java */
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(GeneralQueries.this, "Sorry! The process failed due to some technical error. Please try after some time.");
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
                todayReturn = gson.fromJson(jsonString, TodayReturn.class);
                System.out.println(">>>>" + what);
                String check = todayReturn.getReturnCode();
                final String message = todayReturn.getReturnMessage();


                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                          //  Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                            load_dialog.cancel();

                            submitdiag();

                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(GeneralQueries.this, message);

                        }
                    });
                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(GeneralQueries.this, message);

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
                        //redirect(GeneralQueries.this, "No internet access. Please turn on cellular data or use wifi.");
                        nointernet();
                    }
                });
            }


        }

    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(GeneralQueries.this);
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
                    if (titlee.getText().toString().equals("")) {
                        displayAlert(GeneralQueries.this, "Title Can't be blank");
                    } else if (titlee.getText().toString().equals("")) {
                        displayAlert(GeneralQueries.this, "Description Can't be blank");
                    } else {


                        try {
                            mProgressDialog.setMessage("submitting...");
                            load_dialog.show();

                            NeedHelpHit needHelpHit = new NeedHelpHit();
                            needHelpHit.setFoodOrderId(orderid);
                            needHelpHit.setSessionToken(basicInformation.getSessionToken().toString());
                            needHelpHit.setUserId(basicInformation.getUserId().toString());
                            needHelpHit.setDescription(description.getText().toString());
                            needHelpHit.setTopicName(s);
                            needHelpHit.setTopicTitle(titlee.getText().toString());
                            needHelpHit.setCreateDate(date_format);

                            String jsonString = gson.toJson(needHelpHit, NeedHelpHit.class).toString();
                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                            oChangePsswordApi.executePostRequest(API.submitenquiry(), jsonString);
                        } catch (Exception e) {

                        }

                    }
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

    public void submitdiag()
    {
        //dialog intialization
        dialog = new Dialog(GeneralQueries.this);
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

                finish();

            }
        });

        dialog.show();
    }

}