package com.saavor.user.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.saavor.user.Classes.CountryDetails;
import com.saavor.user.Classes.Preferences;
import com.saavor.user.R;
import com.saavor.user.activity.CountrylistActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by a123456 on 05/09/17.
 */

public class Country_ListAdapter extends BaseAdapter {

    ArrayList<String> codeArrayList = new ArrayList<String>(Arrays.asList(CountryDetails.getCode()));
    ArrayList<String> countryArrayList = new ArrayList<String>(Arrays.asList(CountryDetails.getCountry()));
    Activity context;
    String classNameStr;
    public Country_ListAdapter(CountrylistActivity countrylistActivity, ArrayList<String> codeArrayList, ArrayList<String> countryArrayList, String classNameStr) {

        this.context=countrylistActivity;
        this.codeArrayList=codeArrayList;
        this.countryArrayList=countryArrayList;
        this.classNameStr=classNameStr;

    }

    @Override
    public int getCount() {
        return countryArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        TextView countryname,countrycode;
        view = inflater.inflate(R.layout.countries_list, null);
        countryname=(TextView)view.findViewById(R.id.txt_countryname);
        countrycode=(TextView)view.findViewById(R.id.txt_countrycode);
        countryname.setText(countryArrayList.get(i));
        countrycode.setText("+"+codeArrayList.get(i));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countrycode=codeArrayList.get(i);

                if (classNameStr.equalsIgnoreCase("SignUp"))
                    Preferences.writeString(context,Preferences.SIGN_UP_COUNTRY_CODE,countrycode);
                else if (classNameStr.equalsIgnoreCase("Personal_Details"))
                    Preferences.writeString(context,Preferences.personaldetails_cc,countrycode);
                context.finish();

                Log.e("countryname",countryArrayList.get(i));
                Log.e("countrycode",codeArrayList.get(i));
            }
        });
        return view;
    }
}
