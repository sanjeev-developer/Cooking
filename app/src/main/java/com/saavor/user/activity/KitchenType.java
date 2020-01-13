package com.saavor.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saavor.user.Model.KitchenSearch;
import com.saavor.user.R;

public class KitchenType extends BaseActivity implements View.OnClickListener {

    private ImageView back,img_res,img_hc;
    private LinearLayout LL_kitchen_hc, LL_Kitchen_res;
    String type="";
    boolean ktres=false, kthome=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_type);

        mFilterdefault = mDatabase.getString("defaultfilter", "");
        kitchenSearch = gson.fromJson(mFilterdefault, KitchenSearch.class);

        LL_kitchen_hc = (LinearLayout) findViewById(R.id.ll_kitchen_hc);
        LL_Kitchen_res = (LinearLayout) findViewById(R.id.ll_Kitchen_res);
        back= (ImageView) findViewById(R.id.img_kitchen_back);
        img_res= (ImageView) findViewById(R.id.img_tick_res);
        img_hc= (ImageView) findViewById(R.id.img_tick_hc);
        LL_kitchen_hc.setOnClickListener(this);
        LL_Kitchen_res.setOnClickListener(this);
        back.setOnClickListener(this);

        try
        {
          if(kitchenSearch.getKitchenType().equals("Home Kitchen"))
          {
              img_hc.setVisibility(View.VISIBLE);
              img_res.setVisibility(View.INVISIBLE);

          }
          else if(kitchenSearch.getKitchenType().equals("Restaurant"))
          {
              img_hc.setVisibility(View.INVISIBLE);
              img_res.setVisibility(View.VISIBLE);
          }
          else
          {
              img_hc.setVisibility(View.INVISIBLE);
              img_res.setVisibility(View.INVISIBLE);
          }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.ll_kitchen_hc:

                if(kthome)
                {
                    img_hc.setVisibility(View.INVISIBLE);
                    type="";
                    kitchenSearch.setKitchenType("");
                    kthome=false;
                }
                else
                {
                    img_hc.setVisibility(View.VISIBLE);
                    img_res.setVisibility(View.INVISIBLE);
                    type="Home Kitchen";
                    kthome=true;
                    ktres=false;
                    kitchenSearch.setKitchenType(type);
                }
                break;

            case R.id.ll_Kitchen_res:

                if(ktres)
                {
                    img_res.setVisibility(View.INVISIBLE);
                    type="";
                    ktres=false;
                    kitchenSearch.setKitchenType("");
                }
                else {
                    img_hc.setVisibility(View.INVISIBLE);
                    img_res.setVisibility(View.VISIBLE);
                    type = "Restaurant";
                    ktres = true;
                    kthome=false;

                    kitchenSearch.setKitchenType(type);

                }
                break;

            case R.id.img_kitchen_back:

                codecommit();

                if(type == "")
                {

                    if(kitchenSearch.getKitchenType().equals(""))
                    {
                        Intent intent = new Intent(getBaseContext(), Filter.class);
                        intent.putExtra("Data", type);
                        startActivity(intent);
                    }

                    else
                    {
                        Intent intent = new Intent(getBaseContext(), Filter.class);
                        intent.putExtra("Data", kitchenSearch.getKitchenType());
                        startActivity(intent);
                    }

                }
                else
                {
                    Intent intent = new Intent(getBaseContext(), Filter.class);
                    intent.putExtra("Data", type);
                    startActivity(intent);
                }


                break;

        }
    }

    public void codecommit() {
        mFilterdefault = gson.toJson(kitchenSearch);
        mTabel.putString("defaultfilter", mFilterdefault);
        mTabel.commit();
    }
    }
