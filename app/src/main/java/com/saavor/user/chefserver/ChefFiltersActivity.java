package com.saavor.user.chefserver;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.vision.text.Line;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.R;
import com.saavor.user.Utils.Utils;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Cusine;
import com.saavor.user.activity.Service;
import com.saavor.user.chefserver.filters.CuisineLists;
import com.saavor.user.chefserver.filters.ServiceFilter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.bendik.simplerangeview.SimpleRangeView;

public class ChefFiltersActivity extends BaseActivity implements View.OnClickListener {

    private SimpleRangeView rangeView;
    private TextView textMin1, textMax1;
    private LinearLayout ll_select_cuisines;
    private RadioButton rb_rating;
    private RadioButton rb_distance;
    private TextView txt_cuisine_type;
    private LinearLayout ll_any_gender;
    private ImageView img_tick_any_gender;
    private LinearLayout ll_male;
    private ImageView img_tick_male;
    private LinearLayout ll_female;
    private ImageView img_tick_female;
    private LinearLayout ll_service_type;
    private TextView txt_sst;
    private LinearLayout ll_date_time;
    private TextView txt_selectedDate;
    private ImageView img_reset;
    private ImageView img_clear_filter;
    private String QuickFilters = "Any Gender";

    boolean IsCuisineValueGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_filters);
        InitializeInterface();
    }

    private void InitializeInterface() {
        textMin1 = (TextView) findViewById(R.id.textMin1);
        textMax1 = (TextView) findViewById(R.id.textMax1);

        ll_any_gender = (LinearLayout) findViewById(R.id.ll_any_gender);
        ll_any_gender.setOnClickListener(this);

        img_tick_any_gender = (ImageView) findViewById(R.id.img_tick_any_gender);

        ll_male = (LinearLayout) findViewById(R.id.ll_male);
        ll_male.setOnClickListener(this);

        img_tick_male = (ImageView) findViewById(R.id.img_tick_male);

        ll_female = (LinearLayout) findViewById(R.id.ll_female);
        ll_female.setOnClickListener(this);

        img_tick_female = (ImageView) findViewById(R.id.img_tick_female);

        ll_service_type = (LinearLayout) findViewById(R.id.ll_service_type);
        ll_service_type.setOnClickListener(this);

        txt_sst = (TextView) findViewById(R.id.txt_sst);

        ll_select_cuisines = (LinearLayout) findViewById(R.id.ll_select_cuisines);
        ll_select_cuisines.setOnClickListener(this);

        txt_cuisine_type = (TextView) findViewById(R.id.txt_cuisine_type);

        ll_date_time = (LinearLayout) findViewById(R.id.ll_date_time);
        ll_date_time.setOnClickListener(this);

        txt_selectedDate = (TextView) findViewById(R.id.txt_selectedDate);

        rb_rating = (RadioButton) findViewById(R.id.rb_rating);
        rb_rating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        rb_distance = (RadioButton) findViewById(R.id.rb_distance);
        rb_distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        rangeView = (SimpleRangeView) findViewById(R.id.rangeView);
        rangeView.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView rangeView, int pos, @NotNull SimpleRangeView.State state) {
                return String.valueOf("");
            }
        });

        rangeView.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView rangeView, int start) {
                textMin1.setText(String.valueOf(start) + " miles");

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView rangeView, int end) {
                textMax1.setText(String.valueOf(end) + " miles");
            }
        });

        rangeView.setOnChangeRangeListener(new SimpleRangeView.OnChangeRangeListener() {
            @Override
            public void onRangeChanged(@NotNull SimpleRangeView rangeView, int start, int end) {
            }
        });

        img_reset = (ImageView) findViewById(R.id.img_reset);
        img_reset.setOnClickListener(this);


        //fetching data from filters
        mFilterdefault = mDatabase.getString("defaultfilter", "");
        kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

        img_clear_filter = (ImageView) findViewById(R.id.img_clear_filter);
        img_clear_filter.setOnClickListener(this);


        txt_selectedDate.setText(date_format);


        LinearLayout llCuisines=(LinearLayout)findViewById(R.id.llCuisines);
        LinearLayout llServiceType=(LinearLayout)findViewById(R.id.llServiceType);
        try {
            if(!getIntent().getStringExtra("UserType").equalsIgnoreCase("Chef")){
                llCuisines.setVisibility(View.GONE);
                llServiceType.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }

    }



    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "mm-dd-yyyy hh:mm a";
        String outputPattern = "MMM dd,yyyy hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    @Override
    protected void onResume() {
        super.onResume();

        // set previous stored Services
        SharedPreferences shared2 = getSharedPreferences("ChefServices", MODE_PRIVATE);
        String Services = (shared2.getString("Services", ""));
        Services=Services.replace("[","");
        Services=Services.replace("]","");

        if (!Services.equalsIgnoreCase("")||Services.equalsIgnoreCase("[]")) {
            txt_sst.setText(Services);
        }else{
            txt_sst.setText("Select Service");
        }


        // set previous stored Cuisines
        SharedPreferences sharedCuisines = getSharedPreferences("CuisinesId", MODE_PRIVATE);
        String Cuisine = (sharedCuisines.getString("Cuisine", ""));
        if(Cuisine.equalsIgnoreCase("")||Cuisine.equalsIgnoreCase("[]")){
            txt_cuisine_type.setText("Select Cuisine");
        }else {
            Cuisine=Cuisine.replace("[","");
            Cuisine=Cuisine.replace("]","");
            txt_cuisine_type.setText(Cuisine);
        }

        // set previous stored filter data
        SharedPreferences shared = getSharedPreferences("ChefFilters", MODE_PRIVATE);
        if (!shared.getString("SortBy", "").equalsIgnoreCase("")) {
            if (shared.getString("SortBy", "").equalsIgnoreCase("Distance")) {
                rb_distance.setChecked(true);
            } else {
                rb_rating.setChecked(true);
            }
            if (shared.getString("QuickFilter", "").equalsIgnoreCase("Any Gender")) {
                img_tick_any_gender.setVisibility(View.VISIBLE);
                QuickFilters = "Any Gender";
            } else if (shared.getString("QuickFilter", "").equalsIgnoreCase("Male")) {
                img_tick_male.setVisibility(View.VISIBLE);
                QuickFilters = "Male";
            } else if (shared.getString("QuickFilter", "").equalsIgnoreCase("FeMale")){
                img_tick_female.setVisibility(View.VISIBLE);
                QuickFilters = "Female";
            }

            textMax1.setText(shared.getString("MaxRadius", "")+" miles");

            String StartRange=shared.getString("MinRadius", "").trim();
            if(StartRange.contains(" miles")){
                StartRange=  StartRange.replace(" miles","");
            }else{
                StartRange= StartRange.replace(" mile","");
            }
            if(StartRange.equalsIgnoreCase("0")||StartRange.equalsIgnoreCase("1")){
                textMin1.setText(StartRange+" mile");
            }else{
                textMin1.setText(StartRange+" miles");
            }

            rangeView.setStart(Integer.parseInt(StartRange));
            rangeView.setEnd(Integer.parseInt(shared.getString("MaxRadius", "").trim().replace(" miles", "")));
            if(shared.getString("BookingDateTime", "").equalsIgnoreCase("Select Date & Time")){
                txt_selectedDate.setText(date_format);
            }else{
                txt_selectedDate.setText(shared.getString("BookingDateTime", ""));
            }
        }

        // set previous filtered data
        if(getIntent().getBooleanExtra("Service",false)||getIntent().getBooleanExtra("Cuisine",false)){
            //textMin1.setText(getIntent().getStringExtra("MinRadius"));
            textMax1.setText(getIntent().getStringExtra("MaxRadius"));


            String StartRange=getIntent().getStringExtra("MinRadius").trim();
            if(StartRange.contains(" miles")){
                StartRange=  StartRange.replace(" miles","");
            }else{
                StartRange= StartRange.replace(" mile","");
            }
            if(StartRange.equalsIgnoreCase("0")||StartRange.equalsIgnoreCase("1")){
                textMin1.setText(StartRange+" mile");
            }else{
                textMin1.setText(StartRange+" miles");
            }

            rangeView.setStart(Integer.parseInt(StartRange));
            //rangeView.setStart(Integer.parseInt(getIntent().getStringExtra("MinRadius").trim().replace(" miles", "")));
            rangeView.setEnd(Integer.parseInt(getIntent().getStringExtra("MaxRadius").trim().replace(" miles", "")));
            if(getIntent().getStringExtra("BookingDate").equalsIgnoreCase("Select Date & Time")){
                txt_selectedDate.setText(date_format);
            }else{
                txt_selectedDate.setText(getIntent().getStringExtra("BookingDate"));
            }

            QuickFilters=getIntent().getStringExtra("FilterBy");
            if (getIntent().getStringExtra("FilterBy").equalsIgnoreCase("Any Gender")) {
                img_tick_any_gender.setVisibility(View.VISIBLE);
                img_tick_male.setVisibility(View.GONE);
                img_tick_female.setVisibility(View.GONE);
            } else if (getIntent().getStringExtra("FilterBy").equalsIgnoreCase("Male")) {
                img_tick_male.setVisibility(View.VISIBLE);
                img_tick_female.setVisibility(View.GONE);
                img_tick_any_gender.setVisibility(View.GONE);
            } else if (getIntent().getStringExtra("FilterBy").equalsIgnoreCase("FeMale")){
                img_tick_female.setVisibility(View.VISIBLE);
                img_tick_any_gender.setVisibility(View.GONE);
                img_tick_male.setVisibility(View.GONE);
            }

            if (getIntent().getStringExtra("Sort").equalsIgnoreCase("Distance")) {
                rb_distance.setChecked(true);
            } else {
                rb_rating.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_clear_filter:
                SharedPreferences shared = getSharedPreferences("CuisinesId", MODE_PRIVATE);
                String CuisineId = (shared.getString("Id", ""));

                if (!CuisineId.equalsIgnoreCase("")) {
                    if (CuisineId.substring(CuisineId.length() - 1).equalsIgnoreCase("~")) {
                        CuisineId = CuisineId.substring(0, CuisineId.length() - 1);
                    }
                }

                SharedPreferences.Editor editor1 = getSharedPreferences(
                        "ChefFilters", MODE_PRIVATE).edit();
                if (rb_distance.isChecked()) {
                    editor1.putString("SortBy", "Distance");
                } else {
                    editor1.putString("SortBy", "Rating");
                }
                editor1.putString("Cuisine", CuisineId);
                if(txt_sst.getText().toString().equalsIgnoreCase("Select Service")){
                    editor1.putString("Service", "");
                }else{
                    editor1.putString("Service", txt_sst.getText().toString());
                }

                String StartRange=textMin1.getText().toString().trim();
                if(StartRange.contains(" miles")){
                    StartRange=  StartRange.replace(" miles","");
                }else{
                    StartRange= StartRange.replace(" mile","");
                }

                editor1.putString("QuickFilter", QuickFilters);
                editor1.putString("ServiceType", txt_sst.getText().toString().trim());
                editor1.putString("MinRadius",StartRange);
                editor1.putString("MaxRadius", textMax1.getText().toString().trim().replace(" miles", ""));
                if(txt_selectedDate.getText().toString().trim().equalsIgnoreCase("Select Date & Time")){
                    editor1.putString("BookingDateTime", date_format);
                }else{
                    editor1.putString("BookingDateTime",txt_selectedDate.getText().toString().trim());
                }

                editor1.commit();
                finish();
                break;

            case R.id.img_reset:
                rb_distance.setChecked(true);
                txt_cuisine_type.setText("Select Cuisines");
                img_tick_any_gender.setVisibility(View.GONE);
                img_tick_female.setVisibility(View.GONE);
                img_tick_male.setVisibility(View.GONE);
                txt_sst.setText("Select Service");
                //txt_selectedDate.setText("Select Date & Time");
                txt_selectedDate.setText(date_format);
                textMin1.setText("0 miles");
                textMax1.setText("60 miles");
                rangeView.setStart(0);
                rangeView.setEnd(100);
                QuickFilters="Any Gender";

                SharedPreferences preferencesService =getSharedPreferences("ChefServices", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorService = preferencesService.edit();
                editorService.clear();
                editorService.commit();

                SharedPreferences preferencesIsAppSubmit =getSharedPreferences("ChefFilters", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorIsAppSubmit = preferencesIsAppSubmit.edit();
                editorIsAppSubmit.clear();
                editorIsAppSubmit.commit();

                SharedPreferences preferencesCuisinesId =getSharedPreferences("CuisinesId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorCuisinesId = preferencesCuisinesId.edit();
                editorCuisinesId.clear();
                editorCuisinesId.commit();
                break;

            case R.id.ll_select_cuisines:
                IsCuisineValueGet=false;
                Intent intent = new Intent(this, CuisineLists.class);
                intent.putExtra("IsChef", true);

                if (rb_rating.isChecked()) {
                    intent.putExtra("Sort", "Rating");
                } else {
                    intent.putExtra("Sort", "Distance");
                }

                intent.putExtra("FilterBy", QuickFilters);
                intent.putExtra("MinRadius", textMin1.getText().toString());
                intent.putExtra("MaxRadius", textMax1.getText().toString());
                intent.putExtra("BookingDate", txt_selectedDate.getText().toString());
                this.startActivity(intent);
                finish();
                break;
            case R.id.ll_any_gender:
                img_tick_any_gender.setVisibility(View.VISIBLE);
                img_tick_female.setVisibility(View.GONE);
                img_tick_male.setVisibility(View.GONE);

                QuickFilters = "Any Gender";
                break;
            case R.id.ll_male:
                img_tick_any_gender.setVisibility(View.GONE);
                img_tick_female.setVisibility(View.GONE);
                img_tick_male.setVisibility(View.VISIBLE);
                QuickFilters = "Male";
                break;
            case R.id.ll_female:
                img_tick_any_gender.setVisibility(View.GONE);
                img_tick_female.setVisibility(View.VISIBLE);
                img_tick_male.setVisibility(View.GONE);
                QuickFilters = "FeMale";
                break;
            case R.id.ll_service_type:
                Intent intent2 = new Intent(this, ServiceFilter.class);
                intent2.putExtra("IsChef", true);
                if (rb_rating.isChecked()) {
                    intent2.putExtra("Sort", "Rating");
                } else {
                    intent2.putExtra("Sort", "Distance");
                }

                intent2.putExtra("FilterBy", QuickFilters);
                intent2.putExtra("MinRadius", textMin1.getText().toString());
                intent2.putExtra("MaxRadius", textMax1.getText().toString());
                intent2.putExtra("BookingDate", txt_selectedDate.getText().toString());
                this.startActivity(intent2);
                finish();
                break;
            case R.id.ll_date_time:
                datePicker();
                break;

        }
    }


    @Override
    public void onBackPressed() {
    }

    private void datePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                         String day="";
                         String mnth="";
                        if(dayOfMonth<10){
                            day="0";
                        }
                        if(monthOfYear<10){
                            mnth="0";
                        }

                        String date_timea =day+dayOfMonth + "-" + mnth+(monthOfYear + 1) + "-" + year;
                        String date_time1 = mnth+(monthOfYear + 1) + "-" + day+dayOfMonth + "-" + year;
                      //  txt_selectedDate.setText(date_time1);

                          String textfile =""+date_time1;
//                        String month = textfile.split("-")[0];
//                        String date = textfile.split("-")[1];
//                        String yearr =textfile.split("-")[2];

                            Date apidate=null;
                            DateFormat datebeforeFormat = new SimpleDateFormat("MM-dd-yyyy");

                            try {
                                apidate = datebeforeFormat.parse(textfile);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy");

                            // format the date into another format
                            String dateStr = destDf.format(apidate);

                            System.out.println("Converted date is : " + dateStr);

                            txt_selectedDate.setText(dateStr);

                        //*************Call Time Picker Here ********************

                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour="";
                        String minutes="";
                        String format;

                        if(hourOfDay<10){
                            hour="0";
                        }
                        if(minute<10){
                            minutes="0";
                        }


                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "AM";
                        } else if (hourOfDay == 12) {
                            format = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }

                      //  String formattedDate=parseDateToddMMyyyy(txt_selectedDate.getText().toString() + " " +hour+ hourOfDay + ":" + minutes+minute+" "+format);

                        txt_selectedDate.setText(txt_selectedDate.getText().toString() + " " +hour+ hourOfDay + ":" + minutes+minute+" "+format);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }



}
