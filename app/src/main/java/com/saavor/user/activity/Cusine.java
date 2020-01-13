package com.saavor.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.saavor.user.Model.CuisineList;
import com.saavor.user.Model.CusineHit;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.R;
import com.saavor.user.adapter.CuisineAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.ChefFiltersActivity;
import com.saavor.user.processor.GetApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class Cusine extends BaseActivity implements View.OnClickListener, OnResultReceived {
    private RecyclerView recyclerView_cusine;
    private CuisineAdapter cuisineAdapter;
    private ImageView tool_backcuisine, reset;
    String json;
    ArrayList<String> save = new ArrayList<>();
    ArrayList<String> savename = new ArrayList<>();
    StringBuilder stringBuilder;
    StringBuilder cusinenamebuilder;
    String sandy;
    private CusineHit cusineHit = new CusineHit();
    LinearLayoutManager linearLayoutManager;
    public static String cusinenames="";
    private ArrayList<CuisineList> cusineHits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusine);

        mOnResultReceived = this;

        try {
            json = mDatabase.getString("statusdata", null);
            if (json == null) {
                try {
                    mProgressDialog.setMessage("Fetching cuisine list");
                    load_dialog.show();
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.executeGetRequest(API.cusineapi());

                } catch (Exception e) {

                }
            } else {
                cusineHit = gson.fromJson(json, CusineHit.class);
                cuisineAdapter = new CuisineAdapter(Cusine.this);
                recyclerView_cusine = (RecyclerView) findViewById(R.id.recycle_cusine);
                linearLayoutManager = new LinearLayoutManager(Cusine.this);
                recyclerView_cusine.setLayoutManager(linearLayoutManager);
                recyclerView_cusine.setAdapter(cuisineAdapter);

            }


        } catch (Exception e) {

        }

        tool_backcuisine = (ImageView) findViewById(R.id.tool_back_cuisine);
        reset = (ImageView) findViewById(R.id.img_reset);
        tool_backcuisine.setOnClickListener(this);
        reset.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tool_back_cuisine:
                
                json = mDatabase.getString("statusdata", null);
                cusineHit = gson.fromJson(json, CusineHit.class);
                save = new ArrayList<String>();
                savename = new ArrayList<String>();
                stringBuilder = new StringBuilder();
                cusinenamebuilder = new StringBuilder();
                String chefcusine = null;

                if (json == null) {

                } else {

                    int cal = 0;

                    for (int i = 0; i < cusineHit.getCuisineList().size(); i++) {

                        if (cusineHit.getCuisineList().get(i).getStatus() == 1) {
                            save.add(cusineHit.getCuisineList().get(i).getCuisineId().toString());
                            savename.add(cusineHit.getCuisineList().get(i).getCuisineName().toString());
                            cal++;
                        }
                    }

                    if (cal > 0) {
                        for (int i = 0; i < save.size(); i++) {
                            stringBuilder.append(save.get(i) + "~");
                            cusinenamebuilder.append(savename.get(i) + ", ");

                        }

                        String finalString = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
                        
                         if(getIntent().getBooleanExtra("IsChef",false)){
                             chefcusine = cusinenamebuilder.toString().substring(0, cusinenamebuilder.toString().length() - 2);
                        }
                        else
                         {
                             cusinenames = cusinenamebuilder.toString().substring(0, cusinenamebuilder.toString().length() - 2);
                         }
                        

                        //fetching data from filters
                        mFilterdefault = mDatabase.getString("defaultfilter", "");
                        kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

                        kitchenSearch.setCusinename(cusinenames);
                        kitchenSearch.setCuisineList(finalString);

                        mFilterdefault = gson.toJson(kitchenSearch);
                        mTabel.putString("defaultfilter", mFilterdefault);
                        mTabel.commit();


                        Filter.getcusinetype(finalString);
                    } else {
                        Filter.getcusinetype("");
                        cusinenames="";
                    }


                    Intent intent = new Intent(getBaseContext(), Filter.class);
                    intent.putExtra("cusinenames", cusinenames);
                    startActivity(intent);
                }

                break;

            case R.id.img_reset:

                //  Collections.reverse(cusineHit.getCuisineList());

                for (int i = 0; i < cusineHit.getCuisineList().size(); i++) {

                    cusineHit.getCuisineList().get(i).setStatus(0);
                }

                String json = gson.toJson(cusineHit);
                mTabel.putString("statusdata", json);
                mTabel.commit();

                //fetching data from filters
                mFilterdefault = mDatabase.getString("defaultfilter", "");
                kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

                kitchenSearch.setCusinename("");
                kitchenSearch.setCuisineList("");

                mFilterdefault = gson.toJson(kitchenSearch);
                mTabel.putString("defaultfilter", mFilterdefault);
                mTabel.commit();

                cuisineAdapter = new CuisineAdapter(Cusine.this);
                recyclerView_cusine = (RecyclerView) findViewById(R.id.recycle_cusine);
                linearLayoutManager = new LinearLayoutManager(Cusine.this);
                recyclerView_cusine.setLayoutManager(linearLayoutManager);
                recyclerView_cusine.setAdapter(cuisineAdapter);

                cusinenames="";
                break;

        }
    }

    @Override
    public void onBackPressed() {
        json = mDatabase.getString("statusdata", null);
        cusineHit = gson.fromJson(json, CusineHit.class);
        save = new ArrayList<String>();
        savename = new ArrayList<String>();
        stringBuilder = new StringBuilder();
        cusinenamebuilder = new StringBuilder();
        String chefcusine = null;

        if (json == null) {

        } else {

            int cal = 0;

            for (int i = 0; i < cusineHit.getCuisineList().size(); i++) {

                if (cusineHit.getCuisineList().get(i).getStatus() == 1) {
                    save.add(cusineHit.getCuisineList().get(i).getCuisineId().toString());
                    savename.add(cusineHit.getCuisineList().get(i).getCuisineName().toString());
                    cal++;
                }
            }

            if (cal > 0) {
                for (int i = 0; i < save.size(); i++) {
                    stringBuilder.append(save.get(i) + "~");
                    cusinenamebuilder.append(savename.get(i) + ", ");

                }

                String finalString = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);

                if(getIntent().getBooleanExtra("IsChef",false)){
                    chefcusine = cusinenamebuilder.toString().substring(0, cusinenamebuilder.toString().length() - 2);
                }
                else
                {
                    cusinenames = cusinenamebuilder.toString().substring(0, cusinenamebuilder.toString().length() - 2);
                }


                //fetching data from filters
                mFilterdefault = mDatabase.getString("defaultfilter", "");
                kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

                kitchenSearch.setCusinename(cusinenames);
                kitchenSearch.setCuisineList(finalString);

                mFilterdefault = gson.toJson(kitchenSearch);
                mTabel.putString("defaultfilter", mFilterdefault);
                mTabel.commit();


                Filter.getcusinetype(finalString);
            } else {
                Filter.getcusinetype("");
                cusinenames="";
            }


            Intent intent = new Intent(getBaseContext(), Filter.class);
            intent.putExtra("cusinenames", cusinenames);
            startActivity(intent);
    }}

    @Override
    public void dispatchString(RequestSource from, String what) {
 if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Cusine.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
            try {

                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();
                //System.out.println(">>>>"+jsonString);
                cusineHit = new CusineHit();
                gson = new Gson();
                cusineHit = gson.fromJson(jsonString, CusineHit.class);
                String check = cusineHit.getReturnCode();

                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            // Collections.reverse(cusineHit.getCuisineList());

                            String json = gson.toJson(cusineHit); // myObject - instance of MyObject
                            mTabel.putString("statusdata", json);
                            mTabel.commit();

                            cuisineAdapter = new CuisineAdapter(Cusine.this);
                            recyclerView_cusine = (RecyclerView) findViewById(R.id.recycle_cusine);
//                        linearLayoutManager = new LinearLayoutManager(Cusine.this);
//                        recyclerView_cusine.setLayoutManager(linearLayoutManager);
                            recyclerView_cusine.setLayoutManager(new LinearLayoutManager(Cusine.this, LinearLayoutManager.VERTICAL, false));
                            recyclerView_cusine.setAdapter(cuisineAdapter);
                            load_dialog.cancel();
                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                        }
                    });
                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            displayAlert(Cusine.this, "" + cusineHit.getReturnMessage());
                        }
                    });

                } else if (check.equals("5")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }
                    });
                }

            } catch (Exception e) {
                System.out.println(">>>>" + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        redirect(Cusine.this, "No internet access. Please turn on cellular data or use wifi.");
                    }
                });
            }
        }
    }
}