package com.saavor.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.CustomDishItem;
import com.saavor.user.Model.CustomizeHit;
import com.saavor.user.Model.CustomizeReturn;
import com.saavor.user.Model.FavDishInsert;
import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.Model.LoginDataReturn;
import com.saavor.user.Model.SignupReturn;
import com.saavor.user.R;
import com.saavor.user.adapter.DishDetailAdapter;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishDetails extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private ImageView defaultstar,selectedstar, back;

    String jsonString;
    private RecyclerView recyclerView_cus;
    ArrayList<CustomDishItem>customdetail = new ArrayList<>();
    PostApiClient oChangePsswordApi;
    public DishDetailAdapter dishDetailAdapter ;

    int s,c;
    // Automatically finds each field by the specified ID.
    @BindView(R.id.detail_Kitchen)
    TextView mDish_Kname;

    @BindView(R.id.detail_dish_title)
    TextView mDish_name;

    @BindView(R.id.detail_dish_price)
    TextView mDish_price;

    @BindView(R.id.detail_dish_cusine)
    TextView mDish_cusine;

    @BindView(R.id.detail_dish_type)
    TextView mDish_type;

    @BindView(R.id.detail_dish_desc)
    TextView mDish_desc;

    @BindView(R.id.detail_dish_ingr)
    TextView mDish_ing;

    @BindView(R.id.detail_dish_calorie)
    TextView mDish_calorie;

    @BindView(R.id.detail_dish_time)
    TextView mDish_time;

    @BindView(R.id.detail_dish_quantity)
    TextView mDish_quantity;

    @BindView(R.id.service_dish)
    TextView servicedish;

    @BindView(R.id.detail_dish_custom)
    TextView mDish_custom;

    @BindView(R.id.ll_custom)
    LinearLayout LL_custom;

    @BindView(R.id.chilli_1)
    ImageView chilli_1;

    @BindView(R.id.chilli_2)
    ImageView chilli_2;

    @BindView(R.id.chilli_3)
    ImageView chilli_3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);

        ButterKnife.bind(this);

        //fetching user basic infromation
        basicfetch();

        mOnResultReceived = this;
        s = getIntent().getIntExtra("dishid",0);
        c = getIntent().getIntExtra("customize",0);

        if(c==1)
        {
            mDish_custom.setText("CUSTOMIZATIONS AVAILABLE");
            LL_custom.setVisibility(View.VISIBLE);
        }
        else
        {
            mDish_custom.setText("CUSTOMIZATIONS NOT AVAILABLE");
            LL_custom.setVisibility(View.INVISIBLE);
        }

        back = (ImageView) findViewById(R.id.tool_back_dishdetails);
        defaultstar = (ImageView) findViewById(R.id.img_star_default);
        selectedstar = (ImageView) findViewById(R.id.img_star_selected);
        defaultstar.setOnClickListener(this);
        selectedstar.setOnClickListener(this);
        back.setOnClickListener(this);

        recyclerView_cus =(RecyclerView) findViewById(R.id.recycle_dish_custom);

        try {
            //fetching default
            String dataa = mDatabase.getString("defaultfilter", "");
            kitchenSearch = gson.fromJson(dataa, KitchenSearch.class);

            if(!(kitchenSearch == null))
            {
                try
                {
                    customizeHit.setCurrentDate("" + kitchenSearch.getDeliveryDate());
                }
                catch (Exception e)
                {
                    customizeHit.setCurrentDate(date_format);
                }

            }
            else
            {
                customizeHit.setCurrentDate(date_format);
            }
            customizeHit.setUserId(basicInformation.getUserId());
            customizeHit.setSessionToken(basicInformation.getSessionToken());
            customizeHit.setDishId(""+s);

            mProgressDialog.setMessage("Fetching Dish Details....");
            load_dialog.show();

            String jsonString = gson.toJson(customizeHit, CustomizeHit.class).toString();
            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
            oChangePsswordApi.setRequestSource(RequestSource.dishdetails);
            oChangePsswordApi.executePostRequest(API.customizeapi(), jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tool_back_dishdetails:

                finish();

                break;

            case R.id.img_star_default:

                favDishInsert.setDishId(""+s);
                favDishInsert.setSessionToken(basicInformation.getSessionToken().toString());
                favDishInsert.setUserId(basicInformation.getUserId().toString());

                mProgressDialog.setMessage("Add to Favorites");
                load_dialog.show();

                jsonString = gson.toJson(favDishInsert, FavDishInsert.class).toString();
                oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.favdishinsert);
                oChangePsswordApi.executePostRequest(API.insertfavdish(), jsonString);

                break;

            case R.id.img_star_selected:

                favDishInsert.setDishId(""+s);
                favDishInsert.setSessionToken(basicInformation.getSessionToken().toString());
                favDishInsert.setUserId(basicInformation.getUserId().toString());

                mProgressDialog.setMessage("Remove from Favorites");
                load_dialog.show();

                jsonString = gson.toJson(favDishInsert, FavDishInsert.class).toString();
                oChangePsswordApi = new PostApiClient(mOnResultReceived);
                oChangePsswordApi.setRequestSource(RequestSource.favdishdelete);
                oChangePsswordApi.executePostRequest(API.deletefavdish(), jsonString);

                break;
        }
    }


    @Override
    public void dispatchString(RequestSource from, String what) {
 if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(DishDetails.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
            String t = what;

            if (from.toString().equalsIgnoreCase("favdishinsert")) {

                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();


                    gson = new Gson();
                    signupReturn = gson.fromJson(jsonString, SignupReturn.class);
                    System.out.println(">>>>" + what);
                    String check = signupReturn.getReturnCode();
                    final String message = signupReturn.getReturnMessage();


                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                defaultstar.setVisibility(View.GONE);
                                selectedstar.setVisibility(View.VISIBLE);
                                load_dialog.cancel();

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(DishDetails.this, message);

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                            }
                        });
                        displayAlert(DishDetails.this, message);
                    }
                    else if (check.equals("5")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("Sessionexp", 1);
                                startActivity(intent);
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
                            redirect(DishDetails.this, "No internet access. Please turn on cellular data or use wifi.");
                        }
                    });
                }

                Log.e("response", "" + what);
            } else if (from.toString().equalsIgnoreCase("favdishdelete")) {
                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();
                    //System.out.println(">>>>"+jsonString);

                    gson = new Gson();
                    loginDataReturn = gson.fromJson(jsonString, LoginDataReturn.class);
                    String check = loginDataReturn.getReturnCode();
                    final String message = signupReturn.getReturnMessage();


                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                defaultstar.setVisibility(View.VISIBLE);
                                selectedstar.setVisibility(View.GONE);
                                load_dialog.cancel();

                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                load_dialog.cancel();
                                displayAlert(DishDetails.this, message);
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
                            redirect(DishDetails.this, "No internet access. Please turn on cellular data or use wifi.");
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("dishdetails")) {
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

                                mDish_Kname.setText(customizeReturn.getKitchenTodayDish().getKitchenName());
                                mDish_name.setText(customizeReturn.getKitchenTodayDish().getDishName());

                                DecimalFormat df = new DecimalFormat("####0.00");
                            //    mDish_price.setText("$" + df.format(""+customizeReturn.getKitchenTodayDish().getPrice()));

                                mDish_price.setText("$" + String.format("%.02f", customizeReturn.getKitchenTodayDish().getPrice()));

                               // mDish_price.setText("$" + customizeReturn.getKitchenTodayDish().getPrice());
                                mDish_cusine.setText(customizeReturn.getKitchenTodayDish().getCuisineName());
                                mDish_type.setText(customizeReturn.getKitchenTodayDish().getCategory());
                                mDish_desc.setText(customizeReturn.getKitchenTodayDish().getDishDesc());
                                mDish_ing.setText(customizeReturn.getKitchenTodayDish().getIngredeients());
                                mDish_calorie.setText(customizeReturn.getKitchenTodayDish().getCalories());

                                if(customizeReturn.getKitchenTodayDish().getSpiceLevel().equals(""))
                                {
                                    chilli_1.setVisibility(View.GONE);
                                    chilli_2.setVisibility(View.GONE);
                                    chilli_3.setVisibility(View.GONE);
                                }
                                else if(customizeReturn.getKitchenTodayDish().getSpiceLevel().equals("Mild"))
                                {
                                    chilli_1.setVisibility(View.VISIBLE);
                                    chilli_2.setVisibility(View.GONE);
                                    chilli_3.setVisibility(View.GONE);
                                }
                                else if(customizeReturn.getKitchenTodayDish().getSpiceLevel().equals("Medium"))
                                {
                                    chilli_1.setVisibility(View.VISIBLE);
                                    chilli_2.setVisibility(View.VISIBLE);
                                    chilli_3.setVisibility(View.GONE);
                                }
                                else if (customizeReturn.getKitchenTodayDish().getSpiceLevel().equals("Hot"))
                                {
                                    chilli_1.setVisibility(View.VISIBLE);
                                    chilli_2.setVisibility(View.VISIBLE);
                                    chilli_3.setVisibility(View.VISIBLE);
                                }

                                if(new String("Vegetarian").equals(customizeReturn.getKitchenTodayDish().getCategory()) )
                                {
                                    mDish_type.setText("Vegetarian");
                                }
                                else
                                {
                                    mDish_type.setText("Non-Vegetarian");
                                }

                                if(Integer.parseInt(customizeReturn.getKitchenTodayDish().getPreparingTime())>60)
                                {
                                    int t=Integer.parseInt(customizeReturn.getKitchenTodayDish().getPreparingTime());

                                    int hours = t / 60; //since both are ints, you get an int
                                    int minutes = t % 60;

//                                    if(minutes == 0)
//                                    {
//                                        mDish_time.setText( hours+"hour ");
//                                    }
//                                    else//2hr(s) 0min(s)
//                                    {
                                        mDish_time.setText( hours+" hr "+minutes+" min");
                                   // }
                                }
                                else
                                {
                                    mDish_time.setText(customizeReturn.getKitchenTodayDish().getPreparingTime() + " mins");
                                }

                                mDish_quantity.setText(customizeReturn.getKitchenTodayDish().getAvailableQty().toString());
                                servicedish.setText(customizeReturn.getKitchenTodayDish().getServiceList().toString());

                                int s1 = customizeReturn.getKitchenTodayDish().getIsFavDish();

                                if (s1 == 1) {
                                    defaultstar.setVisibility(View.GONE);
                                    selectedstar.setVisibility(View.VISIBLE);
                                } else {
                                    defaultstar.setVisibility(View.VISIBLE);
                                    selectedstar.setVisibility(View.GONE);
                                }

                                customdetail = customizeReturn.getKitchenTodayDish().getCustomDishItems();
                                dishDetailAdapter = new DishDetailAdapter(DishDetails.this, customdetail);
                                recyclerView_cus.setLayoutManager(new LinearLayoutManager(DishDetails.this, LinearLayoutManager.VERTICAL, true));
                                recyclerView_cus.setAdapter(dishDetailAdapter);


                                load_dialog.cancel();
                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(DishDetails.this, message);
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
                        displayAlert(DishDetails.this, message);
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
                            redirect(DishDetails.this, "No internet access. Please turn on cellular data or use wifi.");
                        }
                    });
                }

                Log.e("response", "" + what);

            }
        }
    }

    @Override
    public void onBackPressed() {
       finish();
    }
}