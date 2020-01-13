package com.saavor.user.activity;

import android.content.Intent;
import android.support.multidex.MultiDex;
import android.os.Bundle;

import com.saavor.user.R;

public class SplashActivity extends BaseActivity {

    private boolean session = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MultiDex.install(this);
        //saving default list
        defaultfilter();
        //clear cusine list
        mTabel.remove("statusdata");
        mTabel.apply();

        session = mDatabase.getBoolean("session", false);

        if(session)
        {
            Thread Background = new Thread(){
                public void run(){

                    try {
                        sleep(1*500);

                        intent = new Intent(SplashActivity.this, DashBoard.class);
                        intent.putExtra("onetime",1);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            };

            Background.start();
        }
        else
        {
            Thread Background = new Thread(){
                public void run(){

                    try {
                        sleep(1*500);

                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            };

            Background.start();
        }
    }
}
