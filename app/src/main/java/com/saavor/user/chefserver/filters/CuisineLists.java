package com.saavor.user.chefserver.filters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saavor.user.Model.ChefCuisineList;
import com.saavor.user.Model.CuisineList;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.chefserver.ChefFiltersActivity;
import com.saavor.user.processor.GetApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class CuisineLists extends BaseActivity implements OnResultReceived {

    OnResultReceived mOnResultReceived;
    ProgressDialog mProgressDialog;
    String ServerResult;
    Context mContext;

    RecyclerView recycle_cusine;
    CuisineListAdapter cuisineListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView tool_back_cuisine;
    private ImageView img_reset;

    ArrayList<Boolean>booleanArrayList;
    ArrayList<ChefCuisineList> cuisineListArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_list);
        InitializeInterface();
    }

    private void InitializeInterface() {
        mOnResultReceived = this;
        mContext = this;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Fetching cusine list");

        recycle_cusine = (RecyclerView) findViewById(R.id.recycle_cusine);
        recycle_cusine.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(CuisineLists.this);
        recycle_cusine.setLayoutManager(mLayoutManager);

        tool_back_cuisine=(ImageView)findViewById(R.id.tool_back_cuisine);
        tool_back_cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        img_reset=(ImageView)findViewById(R.id.img_reset);
        img_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencesCuisinesId =getSharedPreferences("CuisinesId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorCuisinesId = preferencesCuisinesId.edit();
                editorCuisinesId.clear();
                editorCuisinesId.commit();
                for(int m=0;m<booleanArrayList.size();m++){
                    if(booleanArrayList.get(m)==true){
                        booleanArrayList.set(m,false);
                    }
                }
                cuisineListAdapter = new CuisineListAdapter(CuisineLists.this, cuisineListArrayList,booleanArrayList);
                recycle_cusine.setAdapter(cuisineListAdapter);
            }
        });

        load_dialog.show();
        GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
        oInsertUpdateApi.executeGetRequest(API.cusineapi());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent=new Intent(CuisineLists.this, ChefFiltersActivity.class);
        intent.putExtra("Sort", getIntent().getStringExtra("Sort"));
        intent.putExtra("FilterBy", getIntent().getStringExtra("FilterBy"));
        intent.putExtra("MinRadius", getIntent().getStringExtra("MinRadius"));
        intent.putExtra("MaxRadius", getIntent().getStringExtra("MaxRadius"));
        intent.putExtra("BookingDate", getIntent().getStringExtra("BookingDate"));
        intent.putExtra("Cuisine", true);
        startActivity(intent);
    }

    @Override
    public void dispatchString(RequestSource from, final String what) {
        load_dialog.cancel();
        ServerResult = what;
        switch (ServerResult) {
            case "-1":
            case "-2":

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nointernet();
                    }
                });
                break;
            case "-3":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(mContext, "Technical error.Please try after some time.", Toast.LENGTH_SHORT).show();
                        displayAlert(mContext, "Technical error.Please try after some time.");


                    }
                });
                break;
            default:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<String>StoredCuisine=new ArrayList<String>();
                            SharedPreferences shared = getSharedPreferences("CuisinesId", MODE_PRIVATE);
                            Set<String> set = shared.getStringSet("key", null);
                            if(set!=null){
                                StoredCuisine.addAll(set);
                            }

                            JSONObject joResult = new JSONObject(what);
                            booleanArrayList=new ArrayList<Boolean>();
                            cuisineListArrayList = new ArrayList<ChefCuisineList>();
                            JSONArray JaCuisineList = joResult.getJSONArray("CuisineList");
                            for (int m = 0; m < JaCuisineList.length(); m++) {
                                ChefCuisineList cuisineList = new ChefCuisineList();
                                cuisineList.setCuisineId(JaCuisineList.getJSONObject(m).getString("CuisineId"));
                                cuisineList.setCuisineName(JaCuisineList.getJSONObject(m).getString("CuisineName"));
                                cuisineListArrayList.add(cuisineList);
                                booleanArrayList.add(false);

                                for(int j=0;j<StoredCuisine.size();j++){
                                    if(StoredCuisine.get(j).equalsIgnoreCase(JaCuisineList.getJSONObject(m).getString("CuisineName"))){
                                        booleanArrayList.set(m,true);
                                        break;
                                    }
                                }
                            }
                            cuisineListAdapter = new CuisineListAdapter(CuisineLists.this, cuisineListArrayList,booleanArrayList);
                            recycle_cusine.setAdapter(cuisineListAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    public void nointernet()
    {
        //dialog intialization
        final Dialog dialog = new Dialog(CuisineLists.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings=(Button)dialog.findViewById(R.id.not_settings);
        Button retry=(Button)dialog.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        dialog.show();

    }
}
