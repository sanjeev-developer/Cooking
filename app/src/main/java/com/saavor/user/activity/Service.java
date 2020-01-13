package com.saavor.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.R;
import com.saavor.user.chefserver.ChefFiltersActivity;

import java.util.ArrayList;

public class Service extends BaseActivity implements View.OnClickListener {

    //near array
    String Addon;
    private ImageView back, img_break, img_lunch, img_brunch, img_dinner;
    private LinearLayout LL_service_break, LL_service_lunch, LL_service_dinner, ll_service_brunch;
    private Boolean b_break = false, b_lunch = false, b_brunch = false, b_dinner = false;
    ArrayList<String>ServiceType=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        img_break = (ImageView) findViewById(R.id.img_tick_breakfast);
        img_lunch = (ImageView) findViewById(R.id.img_tick_lunch);
        img_brunch = (ImageView) findViewById(R.id.img_tick_brunch);
        img_dinner = (ImageView) findViewById(R.id.img_tick_dinner);

        try
        {
            //fetching default
            String data = mDatabase.getString("defaultfilter", "");
            kitchenSearch = gson.fromJson(data, KitchenSearch.class);

            String breakfast ="Breakfast";
            String lunch ="Lunch";
            String Dinner="Dinner";
            String Brunch="Brunch";


            if (getIntent().getBooleanExtra("IsChef", false)) {
                SharedPreferences shared2 = getSharedPreferences("Services", MODE_PRIVATE);
                String CuisineId = (shared2.getString("Value", ""));
                if (!CuisineId.equalsIgnoreCase("")) {
                    if(CuisineId.contains("Breakfast")){
                        img_break.setVisibility(View.VISIBLE);
                        ServiceType.add("Breakfast");
                        img_break.setTag("1");
                    }if(CuisineId.contains("Lunch")){
                        img_lunch.setVisibility(View.VISIBLE);
                        ServiceType.add("Lunch");
                        img_lunch.setTag("1");
                    }if(CuisineId.contains("Brunch")){
                        img_brunch.setVisibility(View.VISIBLE);
                        ServiceType.add("Brunch");
                        img_brunch.setTag("1");
                    }if(CuisineId.contains("Dinner")){
                        img_dinner.setVisibility(View.VISIBLE);
                        ServiceType.add("Dinner");
                        img_dinner.setTag("1");
                    }
                }
            }else{


            if(kitchenSearch.getServiceType().equals(breakfast))
            {
                img_break.setVisibility(View.VISIBLE);
                img_lunch.setVisibility(View.INVISIBLE);
                img_dinner.setVisibility(View.INVISIBLE);
                img_brunch.setVisibility(View.INVISIBLE);

            }
            else if(kitchenSearch.getServiceType().equals(lunch))
            {
                img_break.setVisibility(View.INVISIBLE);
                img_lunch.setVisibility(View.VISIBLE);
                img_dinner.setVisibility(View.INVISIBLE);
                img_brunch.setVisibility(View.INVISIBLE);
            }
            else if(kitchenSearch.getServiceType().equals(Dinner))
            {
                img_break.setVisibility(View.INVISIBLE);
                img_lunch.setVisibility(View.INVISIBLE);
                img_dinner.setVisibility(View.VISIBLE);
                img_brunch.setVisibility(View.INVISIBLE);
            }
            else if(kitchenSearch.getServiceType().equals(Brunch))
            {
                img_break.setVisibility(View.INVISIBLE);
                img_lunch.setVisibility(View.INVISIBLE);
                img_dinner.setVisibility(View.INVISIBLE);
                img_brunch.setVisibility(View.VISIBLE);
            }
        }}
        catch (Exception e)
        {

        }

        LL_service_break = (LinearLayout) findViewById(R.id.ll_service_break);
        LL_service_lunch = (LinearLayout) findViewById(R.id.ll_service_lunch);
        LL_service_dinner = (LinearLayout) findViewById(R.id.ll_service_dinner);
        ll_service_brunch = (LinearLayout) findViewById(R.id.ll_service_brunch);

        LL_service_break.setOnClickListener(this);
        LL_service_lunch.setOnClickListener(this);
        LL_service_dinner.setOnClickListener(this);
        ll_service_brunch.setOnClickListener(this);


        back = (ImageView) findViewById(R.id.img_service_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentt = new Intent(getBaseContext(), Filter.class);
        intentt.putExtra("servicetype", Addon);
        startActivity(intentt);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_service_break:


                    if (b_break) {
                        img_break.setVisibility(View.INVISIBLE);
                        kitchenSearch.setServiceType("");
                        Addon = "";
                        codecommit();
                        b_break = false;

                    } else {
                        img_break.setVisibility(View.VISIBLE);
                        img_lunch.setVisibility(View.INVISIBLE);
                        img_dinner.setVisibility(View.INVISIBLE);
                        img_brunch.setVisibility(View.INVISIBLE);

                        kitchenSearch.setServiceType("Breakfast");
                        Addon = "Breakfast";
                        codecommit();
                        b_break = true;
                        b_lunch = false;
                        b_dinner = false;
                        b_brunch = false;

                }
                break;

            case R.id.ll_service_lunch:


                    if (b_lunch) {
                        img_lunch.setVisibility(View.INVISIBLE);
                        kitchenSearch.setServiceType("");
                        Addon = "";
                        codecommit();

                        b_lunch = false;

                    } else {

                        img_break.setVisibility(View.INVISIBLE);
                        img_lunch.setVisibility(View.VISIBLE);
                        img_dinner.setVisibility(View.INVISIBLE);
                        img_brunch.setVisibility(View.INVISIBLE);
                        kitchenSearch.setServiceType("Lunch");
                        Addon = "Lunch";
                        codecommit();
                        b_lunch = true;
                        b_dinner = false;
                        b_brunch = false;
                        b_break = false;
                    }

                break;

            case R.id.ll_service_dinner:



                if(b_dinner)
                {
                    img_dinner.setVisibility(View.INVISIBLE);
                    kitchenSearch.setServiceType("");
                    Addon="";
                    codecommit();

                    b_dinner = false;

                }
                else {

                    img_break.setVisibility(View.INVISIBLE);
                    img_lunch.setVisibility(View.INVISIBLE);
                    img_dinner.setVisibility(View.VISIBLE);
                    img_brunch.setVisibility(View.INVISIBLE);
                    kitchenSearch.setServiceType("Dinner");
                    Addon = "Dinner";
                    codecommit();
                    b_dinner = true;
                    b_break = false;
                    b_lunch = false;
                    b_brunch = false;

                }

                break;

            case R.id.ll_service_brunch:


                    if (b_brunch) {
                        img_brunch.setVisibility(View.INVISIBLE);
                        kitchenSearch.setServiceType("");
                        Addon = "";
                        codecommit();

                        b_brunch = false;
                    } else {
                        img_break.setVisibility(View.INVISIBLE);
                        img_lunch.setVisibility(View.INVISIBLE);
                        img_dinner.setVisibility(View.INVISIBLE);
                        img_brunch.setVisibility(View.VISIBLE);
                        kitchenSearch.setServiceType("Brunch");
                        Addon = "Brunch";
                        codecommit();
                        b_brunch = true;
                        b_break = false;
                        b_lunch = false;
                        b_dinner = false;
                    }

                break;


            case R.id.img_service_back:

                    Intent intentt = new Intent(getBaseContext(), Filter.class);
                    intentt.putExtra("servicetype", Addon);
                    startActivity(intentt);

                break;

        }
    }

    public void codecommit()
    {
        mFilterdefault = gson.toJson(kitchenSearch);
        mTabel.putString("defaultfilter", mFilterdefault);
        mTabel.commit();
    }
}