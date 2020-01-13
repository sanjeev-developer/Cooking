package com.saavor.user.chefserver.needhelp;

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

import com.google.gson.Gson;
import com.saavor.user.Model.NeedHelpHit;
import com.saavor.user.Model.TodayReturn;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.MainActivity;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralQueriesActivity extends BaseActivity implements View.OnClickListener,OnResultReceived {

    private ImageView back;
    private TextView title;
    private Button submit;

    @BindView(R.id.gq_desc)
    EditText description;
    @BindView(R.id.gq_title)
    EditText titlee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_queries2);
        InitializeInterface();
    }

    private void InitializeInterface() {
        basicfetch();
        ButterKnife.bind(this);

        mOnResultReceived = this;

        back = (ImageView) findViewById(R.id.tool_back_needf);
        title = (TextView) findViewById(R.id.title_gq);
        submit = (Button) findViewById(R.id.gq_submit);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
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
                        needHelpHit.setFoodOrderId(getIntent().getStringExtra("BookingId"));
                        needHelpHit.setSessionToken(basicInformation.getSessionToken().toString());
                        needHelpHit.setUserId(basicInformation.getUserId().toString());
                        needHelpHit.setDescription(description.getText().toString());
                        needHelpHit.setTopicName(getIntent().getStringExtra("data"));
                        needHelpHit.setTopicTitle(titlee.getText().toString());
                        needHelpHit.setCreateDate(date_format);

                        String jsonString = gson.toJson(needHelpHit, NeedHelpHit.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.setRequestSource(RequestSource.NeedHelpHit);
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
    public void dispatchString(RequestSource from, String what) {
            load_dialog.cancel();
            switch (what) {
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
                            displayAlert(GeneralQueriesActivity.this, "Sorry! The process failed due to some technical error. Please try after some time.");

                        }
                    });

                    break;
                case "5":
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
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
                            submitdiag();
                        }
                    });

                    break;
            }
    }


    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(GeneralQueriesActivity.this);
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
                        displayAlert(GeneralQueriesActivity.this, "Title Can't be blank");
                    } else if (titlee.getText().toString().equals("")) {
                        displayAlert(GeneralQueriesActivity.this, "Description Can't be blank");
                    } else {


                        try {
                            mProgressDialog.setMessage("submitting...");
                            load_dialog.show();

                            NeedHelpHit needHelpHit = new NeedHelpHit();
                            needHelpHit.setFoodOrderId(getIntent().getStringExtra("BookingId"));
                            needHelpHit.setSessionToken(basicInformation.getSessionToken().toString());
                            needHelpHit.setUserId(basicInformation.getUserId().toString());
                            needHelpHit.setDescription(description.getText().toString());
                            needHelpHit.setTopicName(getIntent().getStringExtra("data"));
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
        dialog = new Dialog(GeneralQueriesActivity.this);
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
