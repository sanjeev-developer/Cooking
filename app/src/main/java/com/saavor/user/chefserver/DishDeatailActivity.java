package com.saavor.user.chefserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saavor.user.R;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;

public class DishDeatailActivity extends AppCompatActivity implements View.OnClickListener,OnResultReceived{

    ImageView tool_back_dishdetails;
    ImageView img_star_selected;
    ImageView img_star_default;
    TextView txt_username;
    TextView detail_dish_title;
    TextView detail_dish_cusine;
    TextView detail_dish_type;
    TextView detail_dish_price;
    TextView detail_dish_desc;
    TextView detail_dish_ingr;
    TextView detail_dish_calorie;
    TextView service_dish;
    TextView detail_dish_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_deatail);
        InitializeInterface();
    }

    private void InitializeInterface() {
        tool_back_dishdetails=(ImageView)findViewById(R.id.tool_back_dishdetails);
        img_star_selected=(ImageView)findViewById(R.id.img_star_selected);
        img_star_default=(ImageView)findViewById(R.id.img_star_default);

        txt_username=(TextView)findViewById(R.id.txt_username);
        detail_dish_title=(TextView)findViewById(R.id.detail_dish_title);
        detail_dish_cusine=(TextView)findViewById(R.id.detail_dish_cusine);
        detail_dish_type=(TextView)findViewById(R.id.detail_dish_type);
        detail_dish_price=(TextView)findViewById(R.id.detail_dish_price);
        detail_dish_desc=(TextView)findViewById(R.id.detail_dish_desc);
        detail_dish_ingr=(TextView)findViewById(R.id.detail_dish_ingr);
        detail_dish_calorie=(TextView)findViewById(R.id.detail_dish_calorie);
        service_dish=(TextView)findViewById(R.id.service_dish);
        detail_dish_time=(TextView)findViewById(R.id.detail_dish_time);

        tool_back_dishdetails.setOnClickListener(this);
        detail_dish_calorie.setText(getIntent().getStringExtra("Calories"));
        detail_dish_ingr.setText(getIntent().getStringExtra("Ingredeients"));
        detail_dish_desc.setText(getIntent().getStringExtra("Description"));
        detail_dish_title.setText(getIntent().getStringExtra("DishName"));
        detail_dish_cusine.setText(getIntent().getStringExtra("CuisineName"));
        detail_dish_type.setText(getIntent().getStringExtra("Category"));
        txt_username.setText(getIntent().getStringExtra("UserName"));
        detail_dish_time.setText(getIntent().getStringExtra("PreparingTime"));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tool_back_dishdetails:
                onBackPressed();
                break;
        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

    }
}
