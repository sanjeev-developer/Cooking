package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.saavor.user.Model.RefferalModel;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class Referral extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, OnResultReceived {

    private Spinner mRefrence;
    private ArrayList<String> arrayForSpinner = new ArrayList<>();
    Button mContinue,mSkip;
    private Boolean spinner = false;
    private EditText refcode;
    private String reffraltype;
    private ImageView spinneropen, cross;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);


        arrayForSpinner.add("Select Reference");
        arrayForSpinner.add("Friend");
        arrayForSpinner.add("Internet");

        //saving default list
        defaultfilter();

        mOnResultReceived = this;
        basicfetch();

        //saving session
        mTabel.putBoolean("session", true);
        mTabel.commit();

        spinneropen= (ImageView) findViewById(R.id.img_spinneropen_r);
        cross= (ImageView) findViewById(R.id.img_back_ref);
        mSkip= (Button) findViewById(R.id.but_skip_r);
        mContinue = (Button) findViewById(R.id.but_referral_con);
        mContinue.setOnClickListener(this);
        mSkip.setOnClickListener(this);
        spinneropen.setOnClickListener(this);
        cross.setOnClickListener(this);

        refcode = (EditText) findViewById(R.id.edt_ref_code);

        // Spinner element
        mRefrence = (Spinner) findViewById(R.id.sp_refrence);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, arrayForSpinner);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);

        // attaching data adapter to spinner
        mRefrence.setAdapter(dataAdapter);

        // Spinner click listener
        mRefrence.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            spinner = true;
            reffraltype = mRefrence.getSelectedItem().toString();
            System.out.println("" + reffraltype);
        } else {
            spinner = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinner = false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_referral_con:

                if (!spinner) {
                displayAlert(this, "Please select reference");
            } else if (refcode.getText().toString().equals("")) {
                displayAlert(this, "Please enter refrence code");
            } else {

                refferalModel.setReferralType(reffraltype);
                refferalModel.setReferralCode(refcode.getText().toString());
                refferalModel.setUserId(basicInformation.getUserId().toString());

                load_dialog.show();
                String jsonString = gson.toJson(refferalModel, RefferalModel.class).toString();
                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.executePostRequest(API.userrefrerral(), jsonString);
            }
                break;

            case R.id.but_skip_r:

                intent = new Intent(this, DashBoard.class);
                intent.putExtra("onetime",1);
                this.startActivity(intent);
                break;

            case R.id.img_back_ref:

                intent = new Intent(this, DashBoard.class);
                intent.putExtra("onetime",1);
                this.startActivity(intent);
                break;


            case R.id.img_spinneropen_r:

                mRefrence.performClick();

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
                    displayAlert(Referral.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();


                gson = new Gson();
                refferalModel = gson.fromJson(jsonString, RefferalModel.class);
                System.out.println(">>>>" + what);
                String check = refferalModel.getReturnCode();
                final String message = refferalModel.getReturnMessage();


                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();

                            dialog = new Dialog(Referral.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.alert_diag);
                            dialog.setCancelable(false);

                            Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
                            TextView alertext= (TextView) dialog.findViewById(R.id.text_alert);
                            TextView diagtitle= (TextView) dialog.findViewById(R.id.dialog_title);
                            ImageView alertimg =  (ImageView) dialog.findViewById(R.id.alert_img);

                            alertimg.setImageResource(R.drawable.sucess_img);

                            alertext.setText(""+message);
                            diagtitle.setText("Success");

                            okplaced.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    dialog.cancel();
                                    Intent intent = new Intent(Referral.this, DashBoard.class);
                                    intent.putExtra("onetime", 1);
                                    Referral.this.startActivity(intent);

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
                            displayAlert(Referral.this, message);

                        }
                    });

                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();

                            displayAlert(Referral.this, message);

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
                        redirect(Referral.this, "No internet access. Please turn on cellular data or use wifi.");

                        //

                    }
                });
            }

            Log.e("response", "" + what);
        }
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
    }

