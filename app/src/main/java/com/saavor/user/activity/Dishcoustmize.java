package com.saavor.user.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.AddOn;
import com.saavor.user.Model.CustomDishHit;
import com.saavor.user.Model.CustomDishItem;
import com.saavor.user.Model.CustomizableList;
import com.saavor.user.Model.CustomizeHit;
import com.saavor.user.Model.CustomizeReturn;
import com.saavor.user.Model.NonCustomizableList;
import com.saavor.user.R;
import com.saavor.user.adapter.AddonAdapter;
import com.saavor.user.adapter.KitchenAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.saavor.user.activity.DashBoard.TotalItem;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;

public class Dishcoustmize extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private RecyclerView recyclerView_add;
    private AddonAdapter addonAdapter;
    ImageView half,full,back;
    private Button addtocart,cancel;
    private TextView dishname, dishprice,mUpdated_price;
    public double updatedprice=0;
    int position =0;
    int dishid;
    String dishnamee;
    int itemleft;
    int count;
    int totalqantity;
    Boolean multiadd=false;
    StringBuilder itemnamebuilder;
    StringBuilder itemcostbulider;
    TextView kitchentitle;
    String preparingtime;
    SharedPreferences deliverydetailspref;
    SharedPreferences.Editor deliveryeditor;

    public static ArrayList<CustomDishHit>store_addon_Lists=new ArrayList<CustomDishHit>();
    ArrayList<AddOn>addOnarray=new ArrayList<AddOn>();


    public static KitchenAdapter.MyViewHolder holderr;


    //near array
    ArrayList<CustomDishItem> Addon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishcoustmize);

        //fetching user basic infromation
        basicfetch();

        deliverydetailspref = getSharedPreferences("Deliverypref", Context.MODE_PRIVATE);
        deliveryeditor = deliverydetailspref.edit();


        itemnamebuilder = new StringBuilder();
        itemcostbulider = new StringBuilder();

        dishid = getIntent().getIntExtra("dishid", 0);
        position = getIntent().getIntExtra("position", 0);
        itemleft = getIntent().getIntExtra("itemleft", 0);
        count = getIntent().getIntExtra("count", 0);
        preparingtime = getIntent().getStringExtra("preprationtime");
        totalqantity = getIntent().getIntExtra("totalavailable", 0);

        recyclerView_add = (RecyclerView) findViewById(R.id.recycle_add_coustum);
        mOnResultReceived = this;

        dishname = (TextView) findViewById(R.id.dish_title);
        kitchentitle = (TextView) findViewById(R.id.kitchen_title_addon);
        dishprice = (TextView) findViewById(R.id.dish_price);
        mUpdated_price = (TextView) findViewById(R.id.updated_price);


        back = (ImageView) findViewById(R.id.img_back_coustmize);
//        half= (ImageView) findViewById(R.id.img_custum_half);
//        full= (ImageView) findViewById(R.id.img_custum_full);
        addtocart = (Button) findViewById(R.id.btn_addtocart);
        cancel = (Button) findViewById(R.id.btn_cancel_dc);

//        checkhalf.setOnClickListener(this);
//        checkfull.setOnClickListener(this);
        addtocart.setOnClickListener(this);
        cancel.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            customizeHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
            customizeHit.setUserId(basicInformation.getUserId());
            customizeHit.setSessionToken(basicInformation.getSessionToken());
            customizeHit.setDishId(""+dishid);

            mProgressDialog.setMessage("Loading....");
            load_dialog.show();

            String jsonString = gson.toJson(customizeHit, CustomizeHit.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.executePostRequest(API.customizeapi(), jsonString);

        } catch (Exception e) {

        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back_coustmize:
              finish();
                break;

            case R.id.btn_addtocart:

                DashBoard.TotalItem= DashBoard.TotalItem+1;

                if(addOnarray.isEmpty())
                {
                    for(int i=0; i<storenotcustomizable.size(); i++)
                    {
                        if(storenotcustomizable.get(i).getDishId() == dishid)
                        {
                            String match = storenotcustomizable.get(i).getItemsName();

                            if(match.equals(""))
                            {
                                //setting the ordered quantity of specific item
                                storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity()+1);

                                //fetching the total quantity ordered
                                int cal = 0;
                                for (int k = 0; k < storenotcustomizable.size(); k++)
                                {
                                    if (storenotcustomizable.get(k).getDishId() == dishid) {
                                        cal = cal + storenotcustomizable.get(k).getQuantity();
                                    }
                                }

                                //setting item left after quantity
                                storenotcustomizable.get(i).setItemleft(totalqantity - cal );

                                multiadd=true;
                                break;
                            }

                        }
                    }

                    if(!multiadd)
                    {
                        NonCustomizableList nonCustomizableList= new NonCustomizableList();
                        nonCustomizableList.setQuantity(1);
                        nonCustomizableList.setDishId(position);
                        nonCustomizableList.setDishname(customizeReturn.getKitchenTodayDish().getDishName());
                        nonCustomizableList.setPrice(customizeReturn.getKitchenTodayDish().getPrice());

                        //fetching the total quantity ordered
                        int cal = 0;
                        for (int k = 0; k < storenotcustomizable.size(); k++)
                        {
                            if (storenotcustomizable.get(k).getDishId() == dishid) {
                                cal = cal + storenotcustomizable.get(k).getQuantity();
                            }
                        }

                        //setting item left after quantity
                        nonCustomizableList.setItemleft(totalqantity - cal );
                        nonCustomizableList.setHolder(holderr);
                        nonCustomizableList.setDishId(customizeReturn.getKitchenTodayDish().getDishId());
                        nonCustomizableList.setPosition(position);
                        nonCustomizableList.setCalories(customizeReturn.getKitchenTodayDish().getCalories());
                        nonCustomizableList.setIsCustomizable(1);
                        nonCustomizableList.setItemsName("");
                        nonCustomizableList.setItemsCost("");
                        nonCustomizableList.setPreprationtime(preparingtime);
                        nonCustomizableList.setAvilquantity(totalqantity);
                        nonCustomizableList.setUpdatedprice(updatedprice);

                        storenotcustomizable.add(nonCustomizableList);

                    }

                    multiadd=false;
                }

              else {
                    for(int j =0; j<addOnarray.size();j++)
                    {
                        itemnamebuilder.append(addOnarray.get(j).getAddon_name() + "~");
                        itemcostbulider.append(addOnarray.get(j).getAddon_price() + "~");
                    }


                    String itemname = itemnamebuilder.toString().substring(0, itemnamebuilder.toString().length() - 1);
                    String itemcost = itemcostbulider.toString().substring(0, itemcostbulider.toString().length() - 1);


                    for(int i=0; i<storenotcustomizable.size(); i++)
                    {
                        if(storenotcustomizable.get(i).getDishId() == dishid)
                        {
                            String compare = itemname;
                            String match = storenotcustomizable.get(i).getItemsName();

                            if(compare.equals(match))
                            {
                                //setting the quantity ordered
                                storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity()+1);

                                //fetching the total quantity ordered
                                int cal = 0;
                                for (int k = 0; k < storenotcustomizable.size(); k++)
                                {
                                    if (storenotcustomizable.get(k).getDishId() == dishid) {
                                        cal = cal + storenotcustomizable.get(k).getQuantity();
                                    }
                                }

                                //setting item left after quantity
                                storenotcustomizable.get(i).setItemleft(totalqantity - cal );

                                multiadd=true;
                                break;
                            }
                        }
                    }


                    if(!multiadd)
                    {
                        NonCustomizableList nonCustomizableList= new NonCustomizableList();
                        nonCustomizableList.setQuantity(1);
                        nonCustomizableList.setDishname(customizeReturn.getKitchenTodayDish().getDishName());
                        nonCustomizableList.setPrice(customizeReturn.getKitchenTodayDish().getPrice());

                        //fetching the total quantity ordered
                        int cal = 0;
                        for (int k = 0; k < storenotcustomizable.size(); k++)
                        {
                            if (storenotcustomizable.get(k).getDishId() == dishid) {
                                cal = cal + storenotcustomizable.get(k).getQuantity();
                            }
                        }

                        //setting item left after quantity
                        nonCustomizableList.setItemleft(totalqantity - cal );
                        nonCustomizableList.setHolder(holderr);
                        nonCustomizableList.setDishId(customizeReturn.getKitchenTodayDish().getDishId());
                        nonCustomizableList.setPosition(position);
                        nonCustomizableList.setCalories(customizeReturn.getKitchenTodayDish().getCalories());
                        nonCustomizableList.setIsCustomizable(1);
                        nonCustomizableList.setItemsName(itemname);
                        nonCustomizableList.setItemsCost(itemcost);
                        nonCustomizableList.setPreprationtime(preparingtime);
                        nonCustomizableList.setAvilquantity(totalqantity);
                        nonCustomizableList.setUpdatedprice(updatedprice);

                        storenotcustomizable.add(nonCustomizableList);
                    }

                    multiadd=false;

                }
                int dishid = Integer.valueOf(customizeReturn.getKitchenTodayDish().getDishId());
                KitchenAdapter.setdata(updatedprice, holderr, position, totalqantity, dishid);//, storenotcustomizable, storeCustomizableLists
               finish();
                break;

            case R.id.btn_cancel_dc:
              finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
        finish();
    }

    @Override
    public void dispatchString(RequestSource from, String what) {
 if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Dishcoustmize.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
        try {
            JSONObject Jsonobject = new JSONObject(what);
            String jsonString = Jsonobject.toString();

            gson = new Gson();
            customizeReturn = gson.fromJson(jsonString, CustomizeReturn.class);
            System.out.println(">>>>" + what);
            String check = customizeReturn.getReturnCode();
            final String message = customizeReturn.getReturnMessage();

            if (check.equals("1")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Addon =customizeReturn.getKitchenTodayDish().getCustomDishItems();

                        addonAdapter = new AddonAdapter(Dishcoustmize.this, Addon, customizeReturn.getKitchenTodayDish().getPrice());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView_add.setLayoutManager(mLayoutManager);
                       // recyclerView_add.setLayoutManager(new LinearLayoutManager(Dishcoustmize.this, LinearLayoutManager.VERTICAL, true));
                        recyclerView_add.setAdapter(addonAdapter);

                        updatedprice=customizeReturn.getKitchenTodayDish().getPrice();

//                        String data = "" + updatedprice;
//                        int firstIndex = data.indexOf('.');
//
//                        if (firstIndex == -1)
//                        {
//                            mUpdated_price.setText("$" + String.format("%.02f",updatedprice));
//                        }
//                        else
//                        {
//                            try
//                            {
//                                String textfile = "" + updatedprice;
//                                String filename = textfile.split("\\.")[0];
//                                String extension = textfile.split("\\.")[1];
//
//                                mUpdated_price.setText("$ " + filename + "." + firstTwo(extension));
//                            }catch(Exception e)
//
//                            {
                                mUpdated_price.setText("$" + String.format("%.02f",updatedprice));
                        //    }

                        //}

                        dishname.setText(customizeReturn.getKitchenTodayDish().getDishName());
                        dishprice.setText("$"+String.format("%.02f",customizeReturn.getKitchenTodayDish().getPrice()));
                       // mUpdated_price.setText("$"+customizeReturn.getKitchenTodayDish().getPrice().toString());

                        dishnamee=customizeReturn.getKitchenTodayDish().getDishName();
                        dishid=customizeReturn.getKitchenTodayDish().getDishId();

                        kitchentitle.setText(customizeReturn.getKitchenTodayDish().getKitchenName().toString());

                        load_dialog.cancel();
                    }
                });
            }
            else if (check.equals("0")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayAlert(Dishcoustmize.this, message);

                        load_dialog.cancel();

                    }
                });

            } else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                    }
                });
                displayAlert(Dishcoustmize.this, message);
            }

            else if (check.equals("5")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Sessionexp",1);
                        startActivity(intent);
                    }
                });}


        } catch (Exception e) {
            System.out.println(">>>>" + e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    nointernet();
                }
            });
        }

        Log.e("response", "" + what);

    }}

    public void senddata(double totalprice, ArrayList<AddOn> addOns) {

      //  String data = "" + totalprice;
        //int firstIndex = data.indexOf('.');

        mUpdated_price.setText("$"+String.format("%.02f",totalprice));

//        if (firstIndex == -1)
//        {
//            mUpdated_price.setText("$"+String.format("%.02f",totalprice));
//        }
//        else
//        {
//            try
//            {
//                String textfile = "" + totalprice;
//                String filename = textfile.split("\\.")[0];
//                String extension = textfile.split("\\.")[1];
//
//               // mUpdated_price.setText("$ " + filename + "." + firstTwo(extension));
//                mUpdated_price.setText("$"+filename + "." + firstTwo(extension));
//            }catch(Exception e)
//
//            {
//                mUpdated_price.setText("$"+String.format("%.02f",totalprice));
//            }
//
//        }
//
//
//        //mUpdated_price.setText("$"+totalprice);
        updatedprice=totalprice;
        addOnarray =addOns;
    }

    public static void receiveholder(KitchenAdapter.MyViewHolder holder)//, ArrayList<NonCustomizableList> storenotcustomizable, ArrayList<CustomizableList> storeCustomizableLists
    {
        holderr=holder;
//        storenotcustomizablee=storenotcustomizable;
//        storeCustomizableListss =storeCustomizableLists;
    }

    public void commit(ArrayList<NonCustomizableList> storenotcustomizable)
    {
        String mnoncustomize = gson.toJson(storenotcustomizable);
        mTabel.putString("noncustom", mnoncustomize);
        mTabel.commit();
    }

    public void commitaddon(ArrayList<CustomizableList> storeCustomizableLists)
    {
        maddondata = gson.toJson(storeCustomizableLists);
        mTabel.putString("addondata", maddondata);
        mTabel.commit();
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Dishcoustmize.this);
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


                try {
                    customizeHit.setCurrentDate(deliverydetailspref.getString("Deliverydate", ""));
                    customizeHit.setUserId(basicInformation.getUserId());
                    customizeHit.setSessionToken(basicInformation.getSessionToken());
                    customizeHit.setDishId(""+dishid);

                    mProgressDialog.setMessage("Loading....");
                    load_dialog.show();

                    String jsonString = gson.toJson(customizeHit, CustomizeHit.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.customizeapi(), jsonString);

                } catch (Exception e) {

                }

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
