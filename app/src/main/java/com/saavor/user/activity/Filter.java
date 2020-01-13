package com.saavor.user.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.saavor.user.Model.CusineHit;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.bendik.simplerangeview.SimpleRangeView;

import static com.saavor.user.R.id.txt_sst;
import static com.saavor.user.activity.Cusine.cusinenames;

public class Filter extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Button m20, m50, m100, m150, m200, m500, m1000;
    private ArrayList<String> kitchentypedata = new ArrayList<>();
    private ArrayList<String> servicetypedata = new ArrayList<>();
    private ImageView cross, tick_offer, tick_veg, tick_book;
    private LinearLayout filters_offer, filters_veg, filters_book;
    private ImageView imgreset;
    private RadioGroup sortby;
    TextView Displaycusine;
    private int CalendarHour, CalendarMinute;
    String format;
    TimePickerDialog timepickerdialog;
    private RadioButton rating, distance;
    private DatePicker datePicker;
    private Calendar calendar;
    private int mYear, mMonth, mDay;
    private TextView dateView, selecttimefilter;
    private String monthname;
    public TextView servicet, kitchent, tvMin, tvMax;
    private Boolean offer = true, vege = true, book = true;
    private SimpleRangeView rangeView;
    private String m_DRange_S, m_DRange_F;
    static String cusinehitt = "";
    static String cusinamess = "";
    private CusineHit cusineHit = new CusineHit();
    private LinearLayout LL_servicetype, LL_kitchentype, LL_cusine;
    String min = "";
    String hour = "";
    String date = "";
    String time = "";
    DateFormat datebeforeFormat, converttoset;
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;
    private Date CurrentDate ,deliverydatee, setdate;
    String HitDate;
    boolean cfo10=false, cfo100=false, cfo1000=false;
    boolean mo20=false, mo50=false, mo100=false, mo150=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // get min and max text view
        Displaycusine = (TextView) findViewById(R.id.txt_cusine_type);
        tvMin = (TextView) findViewById(R.id.textMin1);
        tvMax = (TextView) findViewById(R.id.textMax1);
        selecttimefilter = (TextView) findViewById(R.id.txt_selecttimefilter);
        selecttimefilter.setOnClickListener(this);

        //setting delivery pref
        deliverydetailspref=getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();

       // delievryfetch();
        LL_cusine = (LinearLayout) findViewById(R.id.cusine_dash);
        LL_cusine.setOnClickListener(this);

        rangeView = (SimpleRangeView) findViewById(R.id.fixed_rangeview);
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
                tvMin.setText(String.valueOf(start) + " min");
                m_DRange_S = String.valueOf(start);
                kitchenSearch.setDeliveryFrom("" + m_DRange_S);
                codecommit();
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView rangeView, int end) {
                tvMax.setText(String.valueOf(end) + " min");
                m_DRange_F = String.valueOf(end);
                kitchenSearch.setDeliveryTo("" + m_DRange_F);
                codecommit();
            }
        });

        rangeView.setOnChangeRangeListener(new SimpleRangeView.OnChangeRangeListener() {
            @Override
            public void onRangeChanged(@NotNull SimpleRangeView rangeView, int start, int end) {
            }
        });

        //fetching data from filters
        mFilterdefault = mDatabase.getString("defaultfilter", "");
        kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

        filters_offer = (LinearLayout) findViewById(R.id.ll_filters_offer);
        filters_veg = (LinearLayout) findViewById(R.id.ll_filters_veg);
        filters_book = (LinearLayout) findViewById(R.id.ll_filters_book);
        LL_servicetype = (LinearLayout) findViewById(R.id.ll_servicetype);
        LL_kitchentype = (LinearLayout) findViewById(R.id.ll_kitchentype);

        filters_offer.setOnClickListener(this);
        filters_veg.setOnClickListener(this);
        filters_book.setOnClickListener(this);
        LL_servicetype.setOnClickListener(this);
        LL_kitchentype.setOnClickListener(this);

        imgreset = (ImageView) findViewById(R.id.img_reset);
        imgreset.setOnClickListener(this);

        dateView = (TextView) findViewById(R.id.txt_selectdatefilter);
        servicet = (TextView) findViewById(txt_sst);
        kitchent = (TextView) findViewById(R.id.txt_skt);

        dateView.setOnClickListener(this);
        calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (deliverydetailspref.getString("Deliverydate", "").equals("") || deliverydetailspref.getString("Deliverydate", "")== null) {

            showDate(mYear, mMonth, mDay);
            setcurrentime();
        } else {

            deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
            deliveryeditor = deliverydetailspref.edit();

            datebeforeFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);
            converttoset = new SimpleDateFormat("MMM dd, yyyy hh:mm aa",Locale.ENGLISH);

            try {
                CurrentDate=datebeforeFormat.parse(date_format);
              //  CurrentDate=new SimpleDateFormat("MMM dd,yyyy hh:mm aa").parse(date_format);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                deliverydatee = datebeforeFormat.parse(deliverydetailspref.getString("Deliverydate", ""));
              //  deliverydatee=new SimpleDateFormat("MMM dd,yyyy hh:mm aa").parse(deliverydetailspref.getString("Deliverydate", ""));

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (deliverydatee.after(CurrentDate)) {

                HitDate=deliverydetailspref.getString("Deliverydate", "");

            }

            if (deliverydatee.before(CurrentDate)) {
                System.out.println("Date1 is before Date2");

                HitDate=date_format;
            }

            if (deliverydatee.equals(CurrentDate))
            {
                HitDate=date_format;
            }


            try {
                DateFormat srcDf = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);

                // parse the date string into Date object
                Date date = srcDf.parse(HitDate);
                DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                // format the date into another format
                String dateStr = destDf.format(date);
                dateView.setText(dateStr);

                System.out.println("Converted date is : " + dateStr);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            String myString = HitDate;
            String firstWord, restOfString = "", third = "", fourth = "";

            String[] aSplit = myString.split(" ");

            firstWord = aSplit[0];
            restOfString = aSplit[1];
            third = aSplit[2];
            fourth = aSplit[3];

            System.out.println("First word = [" + firstWord + "]");
            System.out.println("Rest of string = [" + restOfString + "]");
            System.out.println(firstWord + " " + restOfString);

            selecttimefilter.setText(third + " " + fourth);

            date = firstWord + " " + restOfString;
            time = third + " " + fourth;
        }

        sortby = (RadioGroup) findViewById(R.id.rg_sortby);
        rating = (RadioButton) findViewById(R.id.rb_rating);
        distance = (RadioButton) findViewById(R.id.rb_distance);

        sortby.setOnCheckedChangeListener(this);

        tick_offer = (ImageView) findViewById(R.id.img_tick_offer);
        tick_veg = (ImageView) findViewById(R.id.img_tick_veg);
        tick_book = (ImageView) findViewById(R.id.img_tick_book);

        m20 = (Button) findViewById(R.id.but_20);
        m50 = (Button) findViewById(R.id.but_50);
        m100 = (Button) findViewById(R.id.but_100);
        m150 = (Button) findViewById(R.id.but_150);

        m200 = (Button) findViewById(R.id.but_200);
        m500 = (Button) findViewById(R.id.but_500);
        m1000 = (Button) findViewById(R.id.but_1000);

        cross = (ImageView) findViewById(R.id.tool_clear_filter);
        cross.setOnClickListener(this);

        m20.setOnClickListener(this);
        m50.setOnClickListener(this);
        m100.setOnClickListener(this);
        m150.setOnClickListener(this);

        m200.setOnClickListener(this);
        m500.setOnClickListener(this);
        m1000.setOnClickListener(this);

        try {
            String s = "";
            s = getIntent().getStringExtra("servicetype");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            String s = "";
            s = getIntent().getStringExtra("Data");

            if (s == null || s.equals(""))
            {
                if(!(kitchenSearch.getKitchenType() == null || kitchenSearch.getKitchenType().equals("")))
                {
                    kitchent.setText(kitchenSearch.getKitchenType());
                }
                else
                {
                    kitchent.setText("Select Kitchen");
                }

            } else {

                    kitchenSearch.setKitchenType(s);
                    codecommit();
                    kitchent.setText(s);

            }
        } catch (Exception e) {

        }

        try {

            String a = kitchenSearch.getIsBookMarked();
            String b = kitchenSearch.getIsVegetarian();
            String c = kitchenSearch.getIsDiscount();

            if (kitchenSearch.getIsBookMarked().equals("1")) {


                tick_book.setVisibility(View.VISIBLE);
                book = false;


            }
            if (kitchenSearch.getIsVegetarian().equals("1")) {


                tick_veg.setVisibility(View.VISIBLE);
                vege = false;

            }
            if (kitchenSearch.getIsDiscount().equals("1")) {


                tick_offer.setVisibility(View.VISIBLE);
                offer = false;
            }

            //setting default kitchen
            String breakfast="Breakfast";
            String lunch = "Lunch";
            String Dinner = "Dinner";
            String Brunch = "Brunch";


            if (kitchenSearch.getServiceType().equals(breakfast)) {
                servicet.setText("Breakfast");

            } else if (kitchenSearch.getServiceType().equals(lunch)) {
                servicet.setText("Lunch");
            } else if (kitchenSearch.getServiceType().equals(Dinner)) {
                servicet.setText("Dinner");
            } else if (kitchenSearch.getServiceType().equals(Brunch)) {
                servicet.setText("Brunch");
            } else {
                servicet.setText("Select Service");
            }

            //clear sort by
            if (kitchenSearch.getSortBy().equals("Rating")) {
                rating.setChecked(true);
                distance.setChecked(false);
            }
            if (kitchenSearch.getSortBy().equals("Distance")) {
                rating.setChecked(false);
                distance.setChecked(true);
            }

            //setting range
            System.out.println(">>>" + kitchenSearch.getDeliveryFrom());
            System.out.println(">>>" + kitchenSearch.getDeliveryTo());

            try {
                rangeView.setStart(Integer.parseInt(kitchenSearch.getDeliveryFrom())-10);
                rangeView.setEnd(Integer.parseInt(kitchenSearch.getDeliveryTo())+10);

                tvMin.setText(kitchenSearch.getDeliveryFrom() + " min");
                tvMax.setText(kitchenSearch.getDeliveryTo() + " min");

            } catch (Exception e) {
                System.out.println("+++++" + e);
            }

            Integer result = Integer.valueOf(kitchenSearch.getCostForOne());
            System.out.println(">>>>>" + kitchenSearch.getCostForOne());

            switch (result) {

                case 10:

                    m200.setBackgroundResource(R.drawable.filterpressed);
                    m500.setBackgroundResource(R.drawable.filterback);
                    m1000.setBackgroundResource(R.drawable.filterback);

                    m200.setTextColor(getResources().getColor(R.color.white));
                    m500.setTextColor(getResources().getColor(R.color.darkgrey));
                    m1000.setTextColor(getResources().getColor(R.color.darkgrey));

                    cfo10=true;
                    cfo100=false;
                    cfo1000=false;

                    break;

                case 100:

                    m200.setBackgroundResource(R.drawable.filterback);
                    m500.setBackgroundResource(R.drawable.filterpressed);
                    m1000.setBackgroundResource(R.drawable.filterback);

                    m200.setTextColor(getResources().getColor(R.color.darkgrey));
                    m500.setTextColor(getResources().getColor(R.color.white));
                    m1000.setTextColor(getResources().getColor(R.color.darkgrey));

                    cfo10=false;
                    cfo100=true;
                    cfo1000=false;

                    break;

                case 1000:

                    m200.setBackgroundResource(R.drawable.filterback);
                    m500.setBackgroundResource(R.drawable.filterback);
                    m1000.setBackgroundResource(R.drawable.filterpressed);

                    m200.setTextColor(getResources().getColor(R.color.darkgrey));
                    m500.setTextColor(getResources().getColor(R.color.darkgrey));
                    m1000.setTextColor(getResources().getColor(R.color.white));

                    cfo10=false;
                    cfo100=false;
                    cfo1000=true;

                    break;

            }


            int mo = Integer.parseInt(kitchenSearch.getMinimumOrder());

            switch (mo) {

                case 20:

                    m20.setBackgroundResource(R.drawable.filterpressed);
                    m50.setBackgroundResource(R.drawable.filterback);
                    m100.setBackgroundResource(R.drawable.filterback);
                    m150.setBackgroundResource(R.drawable.filterback);

                    m20.setTextColor(getResources().getColor(R.color.white));
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    mo20=true;
                    mo50=false;
                    mo100=false;
                    mo150=false;

                    break;

                case 50:

                    m20.setBackgroundResource(R.drawable.filterback);
                    m50.setBackgroundResource(R.drawable.filterpressed);
                    m100.setBackgroundResource(R.drawable.filterback);
                    m150.setBackgroundResource(R.drawable.filterback);

                    m20.setTextColor(getResources().getColor(R.color.darkgrey));
                    m50.setTextColor(getResources().getColor(R.color.white));
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    mo20=false;
                    mo50=true;
                    mo100=false;
                    mo150=false;

                    break;

                case 100:

                    m20.setBackgroundResource(R.drawable.filterback);
                    m50.setBackgroundResource(R.drawable.filterback);
                    m100.setBackgroundResource(R.drawable.filterpressed);
                    m150.setBackgroundResource(R.drawable.filterback);

                    m20.setTextColor(getResources().getColor(R.color.darkgrey));
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));
                    m100.setTextColor(getResources().getColor(R.color.white));
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    mo20=false;
                    mo50=false;
                    mo100=true;
                    mo150=false;


                    break;

                case 150:

                    m20.setBackgroundResource(R.drawable.filterback);
                    m50.setBackgroundResource(R.drawable.filterback);
                    m100.setBackgroundResource(R.drawable.filterback);
                    m150.setBackgroundResource(R.drawable.filterpressed);

                    m20.setTextColor(getResources().getColor(R.color.darkgrey));
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));
                    m150.setTextColor(getResources().getColor(R.color.white));

                    mo20=false;
                    mo50=false;
                    mo100=false;
                    mo150=true;

                    break;
            }

        } catch (Exception e) {

            System.out.println(">>>" + e);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(cusinenames == null || cusinenames.equals(""))
        {
            Displaycusine.setText("Select Cuisine");
        }
        else
        {
            Displaycusine.setText(""+cusinenames);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_20:

                if(mo20)
                {
                    m20.setBackgroundResource(R.drawable.filterback);
                    m20.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("0");
                    codecommit();
                    mo20=false;

                }
                else
                {
                    m20.setBackgroundResource(R.drawable.filterpressed);
                    m50.setBackgroundResource(R.drawable.filterback);
                    m100.setBackgroundResource(R.drawable.filterback);
                    m150.setBackgroundResource(R.drawable.filterback);

                    m20.setTextColor(getResources().getColor(R.color.white));
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("" + 20);
                    codecommit();
                    mo20=true;
                    mo50=false;
                    mo100=false;
                    mo150=false;
                }

                break;

            case R.id.but_50:

                if(mo50)
                {
                    m50.setBackgroundResource(R.drawable.filterback);
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("0");
                    codecommit();
                    mo50=false;
                }
                else
                {
                    m20.setBackgroundResource(R.drawable.filterback);
                    m50.setBackgroundResource(R.drawable.filterpressed);
                    m100.setBackgroundResource(R.drawable.filterback);
                    m150.setBackgroundResource(R.drawable.filterback);

                    m20.setTextColor(getResources().getColor(R.color.darkgrey));
                    m50.setTextColor(getResources().getColor(R.color.white));
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("" + 50);
                    codecommit();
                    mo50=true;
                    mo20=false;
                    mo100=false;
                    mo150=false;
                }

                break;

            case R.id.but_100:


                if(mo100)
                {
                    m100.setBackgroundResource(R.drawable.filterback);
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("0");
                    codecommit();
                    mo100=false;
                }
                else
                {
                    m20.setBackgroundResource(R.drawable.filterback);
                    m50.setBackgroundResource(R.drawable.filterback);
                    m100.setBackgroundResource(R.drawable.filterpressed);
                    m150.setBackgroundResource(R.drawable.filterback);

                    m20.setTextColor(getResources().getColor(R.color.darkgrey));
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));
                    m100.setTextColor(getResources().getColor(R.color.white));
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("" + 100);
                    codecommit();
                    mo100=true;
                    mo50=false;
                    mo20=false;
                    mo150=false;
                }

                break;

            case R.id.but_150:


                if(mo150)
                {
                    m150.setBackgroundResource(R.drawable.filterback);
                    m150.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setMinimumOrder("0");
                    codecommit();
                    mo150=false;
                }
                else
                {
                    m20.setBackgroundResource(R.drawable.filterback);
                    m50.setBackgroundResource(R.drawable.filterback);
                    m100.setBackgroundResource(R.drawable.filterback);
                    m150.setBackgroundResource(R.drawable.filterpressed);

                    m20.setTextColor(getResources().getColor(R.color.darkgrey));
                    m50.setTextColor(getResources().getColor(R.color.darkgrey));
                    m100.setTextColor(getResources().getColor(R.color.darkgrey));
                    m150.setTextColor(getResources().getColor(R.color.white));

                    kitchenSearch.setMinimumOrder("" + 150);
                    codecommit();
                    mo150=true;
                    mo50=false;
                    mo20=false;
                    mo100=false;

                }


                break;

            case R.id.but_200:

                if(cfo10)
                {
                    m200.setBackgroundResource(R.drawable.filterback);
                    m200.setTextColor(getResources().getColor(R.color.darkgrey));
                    cfo10=false;
                    kitchenSearch.setCostForOne("0");
                    codecommit();
                }
                else {
                    m200.setBackgroundResource(R.drawable.filterpressed);
                    m500.setBackgroundResource(R.drawable.filterback);
                    m1000.setBackgroundResource(R.drawable.filterback);

                    m200.setTextColor(getResources().getColor(R.color.white));
                    m500.setTextColor(getResources().getColor(R.color.darkgrey));
                    m1000.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setCostForOne("" + 10);
                    codecommit();
                    cfo10=true;
                    cfo100=false;
                    cfo1000=false;
                }



                break;

            case R.id.but_500:

                if(cfo100)
                {
                    m500.setBackgroundResource(R.drawable.filterback);
                    m500.setTextColor(getResources().getColor(R.color.darkgrey));
                    kitchenSearch.setCostForOne("0");
                    codecommit();
                    cfo100=false;
                }
                else
                {
                    m200.setBackgroundResource(R.drawable.filterback);
                    m500.setBackgroundResource(R.drawable.filterpressed);
                    m1000.setBackgroundResource(R.drawable.filterback);


                    m200.setTextColor(getResources().getColor(R.color.darkgrey));
                    m500.setTextColor(getResources().getColor(R.color.white));
                    m1000.setTextColor(getResources().getColor(R.color.darkgrey));

                    kitchenSearch.setCostForOne("" + 100);
                    codecommit();
                    cfo100=true;
                    cfo10=false;
                    cfo1000=false;
                }

                break;

            case R.id.but_1000:

                if(cfo1000)
                {
                    m1000.setBackgroundResource(R.drawable.filterback);
                    m1000.setTextColor(getResources().getColor(R.color.darkgrey));
                    kitchenSearch.setCostForOne("0");
                    codecommit();
                    cfo1000=false;
                }
                else
                {

                    m200.setBackgroundResource(R.drawable.filterback);
                    m500.setBackgroundResource(R.drawable.filterback);
                    m1000.setBackgroundResource(R.drawable.filterpressed);

                    m200.setTextColor(getResources().getColor(R.color.darkgrey));
                    m500.setTextColor(getResources().getColor(R.color.darkgrey));
                    m1000.setTextColor(getResources().getColor(R.color.white));

                    kitchenSearch.setCostForOne("" + 1000);
                    codecommit();
                    cfo1000=true;
                    cfo100=false;
                    cfo10=false;
                }


                break;

            case R.id.tool_clear_filter:

                String k = cusinehitt;

                kitchenSearch.setCuisineList(k);
                kitchenSearch.setDeliveryDate(date + " " + time);
                codecommit();

                deliveryeditor.putString("Deliverydate", date + " " + time);
                deliveryeditor.commit();

                intent = new Intent(this, DashBoard.class);
                this.startActivity(intent);


                break;

            case R.id.ll_filters_offer:

                if (offer) {

                    tick_offer.setVisibility(View.VISIBLE);
                    kitchenSearch.setIsDiscount("" + 1);
                    codecommit();
                    offer = false;

                } else {

                    tick_offer.setVisibility(View.INVISIBLE);
                    kitchenSearch.setIsDiscount("" + 0);
                    codecommit();
                    offer = true;
                }


                break;

            case R.id.ll_filters_veg:

                if (vege) {
                    tick_veg.setVisibility(View.VISIBLE);
                    kitchenSearch.setIsVegetarian("" + 1);
                    codecommit();
                    vege = false;

                } else {


                    tick_veg.setVisibility(View.INVISIBLE);
                    kitchenSearch.setIsVegetarian("" + 0);
                    codecommit();
                    vege = true;
                }


                break;

            case R.id.ll_filters_book:

                if (book) {
                    tick_book.setVisibility(View.VISIBLE);
                    kitchenSearch.setIsBookMarked("" + 1);
                    codecommit();
                    book = false;

                } else {

                    tick_book.setVisibility(View.INVISIBLE);
                    kitchenSearch.setIsBookMarked("" + 0);
                    codecommit();
                    book = true;
                }


                break;

            case R.id.ll_servicetype:
                intent = new Intent(this, Service.class);
                this.startActivity(intent);

                break;

            case R.id.txt_selecttimefilter:

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(Filter.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (minute < 9) {
                            min = "0" + minute;
                        } else {
                            min = "" + minute;
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

                        selecttimefilter.setText(hourOfDay + ":" + min + " " + format);
                        time = hourOfDay + ":" + min + " " + format;
                    }
                }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

                break;

            case R.id.ll_kitchentype:

                intent = new Intent(this, KitchenType.class);
                this.startActivity(intent);
                break;

            case R.id.cusine_dash:


                intent = new Intent(this, Cusine.class);
                this.startActivity(intent);

                break;

            case R.id.img_reset:

                //clear quick filter
                tick_offer.setVisibility(View.GONE);
                tick_veg.setVisibility(View.GONE);
                tick_book.setVisibility(View.GONE);

                //clear sort by
                rating.setChecked(false);
                distance.setChecked(true);

                //clear delivery range time
                rangeView.setStart(0);
                rangeView.setEnd(60);

                tvMin.setText("0 min");
                tvMax.setText("60 min");


                //minimum order
                m20.setBackgroundResource(R.drawable.filterback);
                m50.setBackgroundResource(R.drawable.filterback);
                m100.setBackgroundResource(R.drawable.filterback);
                m150.setBackgroundResource(R.drawable.filterback);

                m20.setTextColor(getResources().getColor(R.color.darkgrey));
                m50.setTextColor(getResources().getColor(R.color.darkgrey));
                m100.setTextColor(getResources().getColor(R.color.darkgrey));
                m150.setTextColor(getResources().getColor(R.color.darkgrey));

                //clear cost for one
                m200.setBackgroundResource(R.drawable.filterback);
                m500.setBackgroundResource(R.drawable.filterback);
                m1000.setBackgroundResource(R.drawable.filterback);

                m200.setTextColor(getResources().getColor(R.color.darkgrey));
                m500.setTextColor(getResources().getColor(R.color.darkgrey));
                m1000.setTextColor(getResources().getColor(R.color.darkgrey));

                //setting service type
                servicet.setText("Select Service");

                //setting Kitchen type
                kitchent.setText("Select Kitchen");

                cusinenames="";

                //settingcusinename
                Displaycusine.setText("Select Cuisine");

                //clear date
                showDate(mYear, mMonth, mDay);
                //resetting cusines

                try {
                    String json = mDatabase.getString("statusdata", null);
                    cusineHit = gson.fromJson(json, CusineHit.class);

                    for (int i = 0; i < cusineHit.getCuisineList().size(); i++) {

                        cusineHit.getCuisineList().get(i).setStatus(0);
                    }

                    json = gson.toJson(cusineHit);
                    mTabel.putString("statusdata", json);
                    mTabel.commit();

                    cusinehitt = "";
                } catch (Exception e) {
                    cusinehitt = "";
                }

                kitchenSearch.setCostForOne("" + 0);
                kitchenSearch.setDeliveryFrom("" + 0);
                kitchenSearch.setDeliveryTo("" + 60);
                kitchenSearch.setDistance("" + 5);
                kitchenSearch.setStartIndex("" + 0);
                kitchenSearch.setEndIndex("" + 20);
                kitchenSearch.setIsBookMarked("" + 0);
                kitchenSearch.setIsDiscount("" + 0);
                kitchenSearch.setIsVegetarian("" + 0);
                kitchenSearch.setMinimumOrder("" + 0);
                kitchenSearch.setServiceType("");
                kitchenSearch.setKitchenType("");
                kitchenSearch.setCusinename("");
                kitchenSearch.setSortBy("Distance");
                kitchenSearch.setCuisineList("");

                codecommit();

                setcurrentime();
                setCurrentDateOnView();
                deliveryeditor.putString("Deliverydate", date_format);
                deliveryeditor.commit();

              //  Toast.makeText(Filter.this, "Filter Reset", Toast.LENGTH_LONG).show();

                load_dialog.show();
                mProgressDialog.setMessage("Filter Reset...");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        load_dialog.cancel();
                    }
                }, 1000);

                break;

            case R.id.txt_selectdatefilter:
                showDialog(999);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            //  return new DatePickerDialog(this, myDateListener, year, month, day);
            setCurrentDateOnView();
            DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay) {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 7);
                    System.out.println("Date = " + cal.getTime());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    java.util.Date date1 = null;

                    try {
                        date1 = sdf.parse("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (year < mYear) {
                        view.updateDate(mYear, mMonth, mDay);
                        Toast.makeText(getApplicationContext(), "Choose within 7 days", Toast.LENGTH_LONG).show();
                    }

                    if (monthOfYear < mMonth && year == mYear) {
                        view.updateDate(mYear, mMonth, mDay);
                        Toast.makeText(getApplicationContext(), "Choose within 7 days", Toast.LENGTH_LONG).show();
                    }

                    if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth) {
                        view.updateDate(mYear, mMonth, mDay);
                        Toast.makeText(getApplicationContext(), "Choose within 7 days", Toast.LENGTH_LONG).show();
                    }

                    if (date1.after(cal.getTime())) {
                        System.out.println("Date1 is after Date2");
                        view.updateDate(mYear, mMonth, mDay);
                        Toast.makeText(getApplicationContext(), "Choose within 7 days", Toast.LENGTH_LONG).show();
                    }

                }
            };

            _date.show();

        }
        return null;
    }

    private void setCurrentDateOnView() {

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        Date clickset=calendar.getTime();


        try {

            DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

            // format the date into another format
            String dateStr = destDf.format(clickset);
            dateView.setText(dateStr);

            System.out.println("Converted date is : " + dateStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            showDate(selectedYear, selectedMonth, selectedDay);
        }
    };

    private void showDate(int selectedYear, int selectedMonth, int selectedDay) {

        mYear = selectedYear;
        mMonth = selectedMonth;
        mDay = selectedDay;

        switch (mMonth + 1) {
            case 1:
                monthname = "Jan";

                break;

            case 2:
                monthname = "Feb";

                break;

            case 3:
                monthname = "Mar";

                break;

            case 4:
                monthname = "Apr";

                break;

            case 5:
                monthname = "May";

                break;

            case 6:
                monthname = "Jun";

                break;

            case 7:
                monthname = "Jul";

                break;

            case 8:
                monthname = "Aug";

                break;

            case 9:
                monthname = "Sep";
                break;

            case 10:
                monthname = "Oct";
                break;

            case 11:
                monthname = "Nov";
                break;

            case 12:
                monthname = "Dec";

                break;
        }

        dateView.setText(monthname + " " + mDay + ", " + mYear);
        date = monthname + " " + mDay + "," + mYear;
    }


    @Override
    public void onBackPressed() {
        // do nothing.

        String k = cusinehitt;

        kitchenSearch.setCuisineList(k);
        kitchenSearch.setDeliveryDate(date + " " + time);
        codecommit();

        if (m_DRange_F == null || m_DRange_S == null) {

        } else {
            kitchenSearch.setDeliveryFrom("" + m_DRange_S);
            kitchenSearch.setDeliveryTo("" + m_DRange_F);
        }
        codecommit();

        intent = new Intent(this, DashBoard.class);
        this.startActivity(intent);


    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        java.util.Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            // LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        int c = checkedId;

        System.out.println("::::::" + group.getCheckedRadioButtonId());

        int radioBtnID = group.getCheckedRadioButtonId();

        View radioB = group.findViewById(radioBtnID);

        int position = group.indexOfChild(radioB);

        if (position == 0) {
            kitchenSearch.setSortBy("Rating");
            codecommit();
        }

        if (position == 1) {
            kitchenSearch.setSortBy("Distance");
            codecommit();
        }
    }

    public void codecommit() {
        mFilterdefault = gson.toJson(kitchenSearch);
        mTabel.putString("defaultfilter", mFilterdefault);
        mTabel.commit();
    }

    public static void getcusinetype(String type) {
        cusinehitt = type;
    }

    public void setcurrentime() {
        calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);


        if (CalendarMinute < 9) {
            min = "0" + CalendarMinute;
        } else {
            min = "" + CalendarMinute;
        }


        if (CalendarHour == 0) {

            CalendarHour += 12;

            format = "AM";
        } else if (CalendarHour == 12) {

            format = "PM";

        } else if (CalendarHour > 12) {

            CalendarHour -= 12;

            format = "PM";
        } else {

            format = "AM";
        }

       // DecimalFormat df = new DecimalFormat("####0.00");
        selecttimefilter.setText( CalendarHour + ":" + min + " " + format);
        time = CalendarHour + ":" + min + " " + format;
    }
}

