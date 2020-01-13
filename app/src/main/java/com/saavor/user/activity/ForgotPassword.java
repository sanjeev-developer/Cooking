package com.saavor.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.saavor.user.Model.SignupReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ForgotPassword extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private EditText mEmail;
    private Button mSubmit;
    private ImageView mClear;
    private OnResultReceived mOnResultReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmail= (EditText) findViewById(R.id.et_email_forgot);
        mSubmit= (Button) findViewById(R.id.but_submit_forgot);
        mClear= (ImageView) findViewById(R.id.tool_clear_forgot);
        mClear.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        mOnResultReceived=this;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.tool_clear_forgot:

                  finish();

                break;

            case R.id.but_submit_forgot:

                if (mEmail.getText().toString().equals("")) {
                    displayAlert(this, "Email Can't be blank");
                } else if (!isValidEmail(mEmail.getText().toString())) {
                    displayAlert(this, "Please enter the valid email");
                }else
                {
                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.executeGetRequest(API.forgot()+mEmail.getText().toString());
                }

                break;

    }}

    public static boolean isValidEmail(String st_email) {
        if (st_email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(st_email).matches();
        }

    }

    @Override
    public void dispatchString(RequestSource from, String what) {

        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(ForgotPassword.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
        System.out.println(">>>>"+what);

        try {

            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();
            //System.out.println(">>>>"+jsonString);

            gson = new Gson();
            signupReturn= gson.fromJson(jsonString, SignupReturn.class);
            String check = signupReturn.getReturnCode();

            if(check.equals("1"))
            {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(ForgotPassword.this, ""+signupReturn.getReturnMessage());
                        mEmail.setText("");

                    }
                });
            }

            else if(check.equals("0"))
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(ForgotPassword.this, ""+signupReturn.getReturnMessage());


                    }
                });

            }
            else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(ForgotPassword.this, signupReturn.getReturnMessage());
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

        }
        catch (Exception e)
        {
            System.out.println(">>>>"+e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    redirect(ForgotPassword.this, "No internet access. Please turn on cellular data or use wifi.");
                }
            });
        }

    }}
    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
