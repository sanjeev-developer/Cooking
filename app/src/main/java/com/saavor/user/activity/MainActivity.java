package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.saavor.user.Model.Cancelorder;
import com.saavor.user.R;
import com.saavor.user.adapter.ViewPagerAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    // Declare Variables
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Button mSignup,mLogin;
    int[] flag;
    private Boolean exit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.activity_main);

        MultiDex.install(this);

        //saving default list
        defaultfilter();

        try
        {
            int fire = getIntent().getIntExtra("Sessionexp", 0);

            if(fire == 1)
            {

                //dialog intialization
                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.sessionexpired_diag);
                dialog.setCancelable(false);

                Button yes=(Button)dialog.findViewById(R.id.ok_session);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { dialog.cancel();
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        }catch (Exception e)
        {

        }

        mSignup= (Button) findViewById(R.id.btn_signup);
        mLogin= (Button) findViewById(R.id.btn_login);

        mSignup.setOnClickListener(this);
        mLogin.setOnClickListener(this);

        flag = new int[] {R.drawable.twopic,R.drawable.threepic,R.drawable.fourpic,R.drawable.fivepic};

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabdot);

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(MainActivity.this, flag);
        // Binds the Adapter to the ViewPager

        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {

            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.btn_signup: /** Start a new Activity signup.java */
                Intent intent = new Intent(this, SignUp.class);
                this.startActivity(intent);
                break;

            case R.id.btn_login:
                Intent intentt = new Intent(this, Login.class);
                this.startActivity(intentt);
                break;

        }

    }
}
