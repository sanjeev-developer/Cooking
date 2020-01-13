package com.saavor.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.saavor.user.Classes.CountryDetails;
import com.saavor.user.R;
import com.saavor.user.adapter.Country_ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class CountrylistActivity extends AppCompatActivity {

    ListView lv_countries;
    ArrayList<String> codeArrayList = new ArrayList<String>(Arrays.asList(CountryDetails.getCode()));
    ArrayList<String> countryArrayList = new ArrayList<String>(Arrays.asList(CountryDetails.getCountry()));
    Country_ListAdapter country_listAdapter;
    TextView txt_done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrylist);
        txt_done=(TextView)findViewById(R.id.txt_done);

        lv_countries=(ListView)findViewById(R.id.lv_countries);
        country_listAdapter=new Country_ListAdapter(this,codeArrayList,countryArrayList,getIntent().getStringExtra("class_name"));
        lv_countries.setAdapter(country_listAdapter);
    }
}
