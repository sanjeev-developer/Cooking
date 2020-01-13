package com.saavor.user.activity;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by a123456 on 09/05/17.
 */

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> resultList;
    Context context;
    private static final String LOG_TAG = "ExampleApp";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static String API_KEY = "";

    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        if (resultList == null) {
            return 0;
        }
        return resultList.size();
    }

    public String getItem(int index) {

        return resultList.get(index);
    }

    public Filter getFilter() {
        System.out.println("?AAAAAAAAAAAAAAAaa");
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();



                synchronized (filterResults) {
                    if (constraint != null) {
                        // Clear and Retrieve the autocomplete results.
                        try {
                            resultList = autocomplete(constraint.toString());
                        }catch (Exception e){

                        }
                        System.out.println(resultList);

                        Log.e("Error in Typing","---result list----"+resultList);

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {

                if (filterResults != null && filterResults.count > 0) {

                    notifyDataSetChanged();
                } else {

                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }


    private ArrayList<String> autocomplete(String input) {
        System.out.println("sssssssssssssssssssssssssssssssssssssss");

        Log.e("autocomplete","---result autocomplete----"+input);

        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            API_KEY = "AIzaSyCT8tJaaVl6dkEwWx7-28vcoCZls1aV5KY";

            StringBuilder sb = new StringBuilder(PLACES_API_BASE
                    + TYPE_AUTOCOMPLETE + OUT_JSON);

            sb.append("?sensor=false&key=" + API_KEY);

            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {

            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            System.out
                    .println("Crash_\n\n"+predsJsonArray.length());
            System.out.println(jsonObj);

            Log.e("final Output","---result autocomplete----"+predsJsonArray.length());

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            if (input.length()==0){
                resultList.clear();
            }
            else{
                try {
                    for (int i = 0; i < predsJsonArray.length(); i++) {
                        resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }
        return resultList;
    }
}