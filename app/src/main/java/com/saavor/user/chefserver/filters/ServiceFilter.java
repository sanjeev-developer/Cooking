package com.saavor.user.chefserver.filters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saavor.user.R;
import com.saavor.user.chefserver.ChefFiltersActivity;

import java.util.ArrayList;

public class ServiceFilter extends AppCompatActivity implements View.OnClickListener {

    ImageView img_service_back;
    LinearLayout ll_service_breakfast;
    LinearLayout ll_service_lunch;
    LinearLayout ll_service_brunch;
    LinearLayout ll_service_dinner;

    ImageView img_tick_breakfast;
    ImageView img_tick_lunch;
    ImageView img_tick_brunch;
    ImageView img_tick_dinner;

    ArrayList<String>SelectedServices=new ArrayList<>();
    SharedPreferences.Editor editor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_filter);
        InitializeInterface();
    }

    private void InitializeInterface() {
        ll_service_breakfast = (LinearLayout) findViewById(R.id.ll_service_breakfast);
        ll_service_lunch = (LinearLayout) findViewById(R.id.ll_service_lunch);
        ll_service_brunch = (LinearLayout) findViewById(R.id.ll_service_brunch);
        ll_service_dinner = (LinearLayout) findViewById(R.id.ll_service_dinner);

        ll_service_breakfast.setOnClickListener(this);
        ll_service_lunch.setOnClickListener(this);
        ll_service_brunch.setOnClickListener(this);
        ll_service_dinner.setOnClickListener(this);

        img_service_back = (ImageView) findViewById(R.id.img_service_back);
        img_service_back.setOnClickListener(this);

        img_tick_breakfast = (ImageView) findViewById(R.id.img_tick_breakfast);
        img_tick_lunch = (ImageView) findViewById(R.id.img_tick_lunch);
        img_tick_brunch = (ImageView) findViewById(R.id.img_tick_brunch);
        img_tick_dinner = (ImageView) findViewById(R.id.img_tick_dinner);


        SharedPreferences shared2 = getSharedPreferences("ChefServices", MODE_PRIVATE);
        String Services = (shared2.getString("Services", ""));
        Services=Services.replace("[","");
        Services=Services.replace("]","");

        if(Services.contains("Breakfast")){
            ll_service_breakfast.setTag("1");
            img_tick_breakfast.setVisibility(View.VISIBLE);
            SelectedServices.add("Breakfast");
        }
        if(Services.contains("Lunch")){
            img_tick_lunch.setVisibility(View.VISIBLE);
            ll_service_lunch.setTag("1");
            SelectedServices.add("Lunch");
        }
        if(Services.contains("Brunch")){
            img_tick_brunch.setVisibility(View.VISIBLE);
            ll_service_brunch.setTag("1");
            SelectedServices.add("Brunch");
        }
        if(Services.contains("Dinner")){
            img_tick_dinner.setVisibility(View.VISIBLE);
            ll_service_dinner.setTag("1");
            SelectedServices.add("Dinner");
        }


        editor1 =getSharedPreferences(
                "ChefServices", MODE_PRIVATE).edit();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_service_back:
               onBackPressed();
                break;

            case R.id.ll_service_breakfast:
                if (ll_service_breakfast.getTag().equals("0")) {
                    ll_service_breakfast.setTag("1");
                    img_tick_breakfast.setVisibility(View.VISIBLE);
                    SelectedServices.add("Breakfast");
                } else {
                    ll_service_breakfast.setTag("0");
                    img_tick_breakfast.setVisibility(View.GONE);
                    SelectedServices.remove("Breakfast");
                }
                break;
            case R.id.ll_service_lunch:
                if (ll_service_lunch.getTag().equals("0")) {
                    img_tick_lunch.setVisibility(View.VISIBLE);
                    ll_service_lunch.setTag("1");
                    SelectedServices.add("Lunch");
                } else {
                    img_tick_lunch.setVisibility(View.GONE);
                    ll_service_lunch.setTag("0");
                    SelectedServices.remove("Lunch");
                }
                break;
            case R.id.ll_service_brunch:
                if (ll_service_brunch.getTag().equals("0")) {
                    img_tick_brunch.setVisibility(View.VISIBLE);
                    ll_service_brunch.setTag("1");
                    SelectedServices.add("Brunch");
                } else {
                    img_tick_brunch.setVisibility(View.GONE);
                    ll_service_brunch.setTag("0");
                    SelectedServices.remove("Brunch");
                }
                break;
            case R.id.ll_service_dinner:
                if (ll_service_dinner.getTag().equals("0")) {
                    img_tick_dinner.setVisibility(View.VISIBLE);
                    ll_service_dinner.setTag("1");
                    SelectedServices.add("Dinner");
                } else {
                    img_tick_dinner.setVisibility(View.GONE);
                    ll_service_dinner.setTag("0");
                    SelectedServices.remove("Dinner");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editor1.putString("Services", SelectedServices.toString());
        editor1.commit();
        finish();
        Intent intent=new Intent(ServiceFilter.this, ChefFiltersActivity.class);
        intent.putExtra("Sort", getIntent().getStringExtra("Sort"));
        intent.putExtra("FilterBy", getIntent().getStringExtra("FilterBy"));
        intent.putExtra("MinRadius", getIntent().getStringExtra("MinRadius"));
        intent.putExtra("MaxRadius", getIntent().getStringExtra("MaxRadius"));
        intent.putExtra("BookingDate", getIntent().getStringExtra("BookingDate"));
        intent.putExtra("Service", true);
        startActivity(intent);

    }
}
