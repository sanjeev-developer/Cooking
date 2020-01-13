package com.saavor.user.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saavor.user.Model.ChangePasswordModel;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private Button mSave, mCancel;
    private EditText mOldPassword, mNewPassword, mConfirmPassword;
    ImageView cp_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //intializing all elements in ui

        mSave = (Button) findViewById(R.id.but_save_cp);
        mCancel = (Button) findViewById(R.id.but_cancel_cp);

        mOldPassword = (EditText) findViewById(R.id.edt_oldpassword);
        mNewPassword = (EditText) findViewById(R.id.edt_newpassword);
        mConfirmPassword = (EditText) findViewById(R.id.edt_confirmpassword);
        cp_back = (ImageView) findViewById(R.id.img_back_cp);

        mOldPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mOldPassword.setFocusableInTouchMode(true);
                return false;
            }
        });

        mNewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mNewPassword.setFocusableInTouchMode(true);
                return false;
            }
        });


        mConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mConfirmPassword.setFocusableInTouchMode(true);
                return false;
            }
        });



        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        cp_back.setOnClickListener(this);
        basicfetch();
        mOnResultReceived = this;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_save_cp:

                String oldpassdatabase = basicInformation.getPassword().toString();
                String oldpasscurrent = mOldPassword.getText().toString();
                String newPassword = mNewPassword.getText().toString();
                String newConfirm = mConfirmPassword.getText().toString();

//                if (oldpassdatabase.isEmpty() || oldpassdatabase.equals("") || oldpassdatabase == null) {
//                    displayAlert(this, "You Logged in through Social Account");
//                } else

                    if (mOldPassword.getText().toString().equals("")) {
                    displayAlert(this, "Current password Can't be blank");
                } else if (!oldpassdatabase.equals(oldpasscurrent)) {
                    displayAlert(this, "Current password is not correct");
                } else if (newPassword.equals(oldpasscurrent)) {
                    displayAlert(this, "Current password and new password cannot be same");
                } else if (mNewPassword.getText().toString().equals("")) {
                    displayAlert(this, "Password Can't be blank");
                } else if (mNewPassword.getText().toString().length() < 7) {
                    displayAlert(this, "Password must be between 8-16 Character");
                } else if (!match()) {
                    displayAlert(this, "Password must contain at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character");
                } else if (mConfirmPassword.getText().toString().equals("")) {
                    displayAlert(this, "Confirm Password Can't be blank");
                } else if (!mNewPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                    displayAlert(this, "Confirm Password and Password not matched");
                } else {

                    changePasswordModel.setPassword(mNewPassword.getText().toString());
                    changePasswordModel.setSessionToken(basicInformation.getSessionToken().toString());
                    changePasswordModel.setUserId(basicInformation.getUserId().toString());

                    String jsonString = gson.toJson(changePasswordModel, ChangePasswordModel.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.changepassword(), jsonString);
                }

                break;

            case R.id.but_cancel_cp:

                try {
                    hideSoftKeyboard(ChangePassword.this);
                } catch (Exception e) {

                }

                finish();
                break;


            case R.id.img_back_cp:
                try {
                    hideSoftKeyboard(ChangePassword.this);
                } catch (Exception e) {

                }
                finish();

                break;


        }
    }

    //pattern matcher according to regex for new password
    public boolean match()
    {
        String password = mNewPassword.getText().toString();

        //Minimum 8 and Maximum 16 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character:

        Boolean retun = false;
        String myPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!#%*?&])[A-Za-z\\d$@$#!%*?&]{8,16}";
        Pattern p = Pattern.compile(myPattern, Pattern.DOTALL);
        Matcher m = p.matcher(password);

        if (m.matches()) {
            Log.d("Matcher", "PATTERN MATCHES!");
            retun = true;
        } else {
            Log.d("MATCHER", "PATTERN DOES NOT MATCH!");
            retun = false;
        }

        return retun;
    }


    @Override
    public void dispatchString(RequestSource from, String what) {

      if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(ChangePassword.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {

            try {

                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();
                System.out.println(">>>>" + jsonString);
                gson = new Gson();
                changePasswordModel = gson.fromJson(jsonString, ChangePasswordModel.class);
                String check = changePasswordModel.getReturnCode();
                System.out.println(">>>>" + check);

                load_dialog.cancel();

                if (check.equals("1")) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            displayAlert(ChangePassword.this, "" + changePasswordModel.getReturnMessage().toString());

                            basicInformation.setPassword(mNewPassword.getText().toString());
                            mBasicInformation = gson.toJson(basicInformation);
                            mTabel.putString("basicinformation", mBasicInformation);
                            mTabel.commit();

                            mOldPassword.setText("");
                            mNewPassword.setText("");
                            mConfirmPassword.setText("");

                            String nmae = basicInformation.getSessionToken();

                            showdiag();
                            System.out.println(">>" + nmae);

                        }
                    });
                } else if (check.equals("0")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            displayAlert(ChangePassword.this, "" + loginDataReturn.getReturnMessage());
                        }
                    });

                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            // Toast.makeText(XYZKitchen.this, "Server not responding", Toast.LENGTH_LONG).show();
                            displayAlert(ChangePassword.this, "Slow InternetConnection");
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

            } catch (JSONException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        redirect(ChangePassword.this, "No internet access. Please turn on cellular data or use wifi.");
                    }
                });

            }
        }
    }
    @Override
    public void onBackPressed() {
        // do nothing.
        try {
            hideSoftKeyboard(ChangePassword.this);
        } catch (Exception e) {

        }
        finish();
    }

    //successfull dialog of reset password
    public void showdiag()
    {
        dialog = new Dialog(ChangePassword.this);
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

        alertext.setText("Password reset successfully.");
        diagtitle.setText("Success");

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                finish();

            }
        });

        dialog.show();
    }

}