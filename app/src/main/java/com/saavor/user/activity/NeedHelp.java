package com.saavor.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saavor.user.Model.NeedHelpHit;
import com.saavor.user.R;

public class NeedHelp extends BaseActivity implements View.OnClickListener {

    private LinearLayout  incomplete, order, billing, refund, general;
    ImageView need_back;
    Intent intent;
    String orderid;
    NeedHelpHit needHelpHit = new NeedHelpHit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help);

        orderid = getIntent().getStringExtra("foodorderid");
        need_back = (ImageView) findViewById(R.id.tool_need_back);
        incomplete = (LinearLayout) findViewById(R.id.img_incomplete_order);
        order = (LinearLayout) findViewById(R.id.img_order_status);
        billing = (LinearLayout) findViewById(R.id.img_billing_error);
        refund = (LinearLayout) findViewById(R.id.img_refund_status);
        general = (LinearLayout) findViewById(R.id.img_general_queries);

        need_back.setOnClickListener(this);
        incomplete.setOnClickListener(this);
        order.setOnClickListener(this);
        billing.setOnClickListener(this);
        refund.setOnClickListener(this);
        general.setOnClickListener(this);

        intent = new Intent(getBaseContext(), GeneralQueries.class);
        intent.putExtra("foodorderid",orderid);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tool_need_back:

                finish();
                break;

            case R.id.img_incomplete_order:

                intent.putExtra("data", "Incomplete Order");
                startActivity(intent);
                break;

            case R.id.img_order_status:

                intent.putExtra("data", "Order Status");
                startActivity(intent);

                break;

            case R.id.img_billing_error:

                intent.putExtra("data", "Billing Error");
                startActivity(intent);

                break;

            case R.id.img_refund_status:

                intent.putExtra("data", "Refund Status");
                startActivity(intent);
                break;

            case R.id.img_general_queries:

                intent.putExtra("data", "General Queries");
                startActivity(intent);
                break;

        }
    }
}