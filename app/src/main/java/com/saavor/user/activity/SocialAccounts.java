package com.saavor.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.saavor.user.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class SocialAccounts extends BaseActivity {

    ImageView check_facebook,check_googleplus,tool_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(SocialAccounts.this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_social_accounts);


        basicfetch();

        mSocialAccStatus=mDatabase.getInt("socialaccstatus", 0);
        check_facebook= (ImageView) findViewById(R.id.img_check_facebook);
        check_googleplus=(ImageView) findViewById(R.id.img_check_google);
        tool_back=(ImageView) findViewById(R.id.img_back_social);

        tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!(basicInformation.getFacebookid() == null || basicInformation.getFacebookid().toString().equals("")))
        {

            check_facebook.setVisibility(View.VISIBLE);;
        }
        else if(!(basicInformation.getGoogleid() == null || basicInformation.getGoogleid().toString().equals(""))){
            check_googleplus.setVisibility(View.VISIBLE);
        }
        else
        {

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
