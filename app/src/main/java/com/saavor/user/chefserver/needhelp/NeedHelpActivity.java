package com.saavor.user.chefserver.needhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saavor.user.R;

public class NeedHelpActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout incomplete, order, billing, refund, general;
    ImageView need_back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_help2);
        InitializeInterface();
    }

    private void InitializeInterface() {
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

        intent = new Intent(getBaseContext(), GeneralQueriesActivity.class);
        intent.putExtra("BookingId",getIntent().getStringExtra("BookingId"));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

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
