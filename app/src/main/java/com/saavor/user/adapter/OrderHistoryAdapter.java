package com.saavor.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.OrderList;
import com.saavor.user.R;
import com.saavor.user.activity.OrderHistory;
import com.saavor.user.activity.Order_Status;
import com.saavor.user.activity.SubmitReview;
import com.saavor.user.Utils.Utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private ArrayList<OrderList>orderLists;
    private Context context;
    int count = 0 ;
    String datedata;


    public OrderHistoryAdapter(Context contexts, ArrayList<OrderList> dishprice, String datedata)
    {
        this.orderLists=dishprice;
        this.context=contexts;
        this.datedata=datedata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentorderlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.dish_price.setText("$"+String.format("%.02f",orderLists.get(position).getTotalAmount()));
        holder.kitaddress.setText(""+orderLists.get(position).getCustomerAddress().toString());
        if(orderLists.get(position).getCustomerAddress().toString().equals(""))
        {
            holder.kitaddress.setVisibility(View.GONE);
        }
        holder.orderid.setText(""+orderLists.get(position).getOrderId().toString());

        String textfile = "" + ""+orderLists.get(position).getDeliveryDate().toString();
        String month = textfile.split("-")[0];
        String date = textfile.split("-")[1];
        String year =textfile.split("-")[2];

        String mix=month+" "+date+", "+year;
        if(datedata.equals(mix))
        {
            holder.date.setText("Today");
        }
        else
        {
            Date apidate=null;
            DateFormat datebeforeFormat = new SimpleDateFormat("MMM-dd-yyyy hh:mm aa", Locale.ENGLISH);

            try {
                apidate = datebeforeFormat.parse(orderLists.get(position).getDeliveryDate().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy",Locale.ENGLISH);

            // format the date into another format
            String dateStr = destDf.format(apidate);

            System.out.println("Converted date is : " + dateStr);
//            date_title.setText(""+dateStr);

            holder.date.setText(dateStr);
        }

        holder.kitname.setText(""+orderLists.get(position).getKitchenName().toString());



        //after delievring item showing reorder if available and showing heart
        String accepted= "Accepted";
        String sent= "Sent";
        String preparing= "Preparing";
        String readytodeliver= "Out for delivery";
        String ready= "Ready";

        String compare3 =orderLists.get(position).getOrderStatus();

        if(accepted.equals(compare3) || sent.equals(compare3) || preparing.equals(compare3) || readytodeliver.equals(compare3) || ready.equals(compare3))
        {
            holder.LL_reason.setVisibility(View.GONE);
            holder.LL_od_status.setVisibility(View.GONE);
            holder.reorder.setVisibility(View.GONE);
            holder.ordercancel.setVisibility(View.GONE);
            holder.orderstatus.setVisibility(View.VISIBLE);

        }

        //showing cancel button if order status is pending
        String match ="Pending";
        String compare =orderLists.get(position).getOrderStatus();

        if (match.equals(compare))
        {
            holder.LL_reason.setVisibility(View.GONE);
            holder.orderstatus.setVisibility(View.VISIBLE);
            holder.LL_od_status.setVisibility(View.GONE);
            holder.ordercancel.setVisibility(View.VISIBLE);
            holder.reorder.setVisibility(View.GONE);
        }


        String cancel ="Cancelled";
        String rejected ="Rejected";
        String delivered ="Delivered";

        String compare1 =orderLists.get(position).getOrderStatus();

        if (cancel.equals(compare1) || rejected.equals(compare1) || delivered.equals(compare1)) {
            holder.LL_od_status.setVisibility(View.VISIBLE);
            holder.orderstatus.setVisibility(View.GONE);
            holder.ordercancel.setVisibility(View.GONE);

            if(cancel.equals(compare1))
            {
                holder.od_status.setText("The Order is Cancelled by you ");
                holder.orderstatus.setVisibility(View.GONE);
                holder.LL_reason.setVisibility(View.GONE);
            }
            else if(rejected.equals(compare1))
            {
                holder.od_status.setText("The Order is rejected by kitchen ");
                holder.orderstatus.setVisibility(View.GONE);
                holder.LL_reason.setVisibility(View.VISIBLE);
                holder.reason.setText(""+orderLists.get(position).getRejectReason());

            }
            else if(delivered.equals(compare1))
            {
                holder.LL_reason.setVisibility(View.GONE);
                holder.od_status.setText("Delivered");

                if(orderLists.get(position).getIsReview() == 0 || orderLists.get(position).getIsUlyxReview()==0)
                {
                    holder.orderstatus.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.orderstatus.setVisibility(View.GONE);
                }

            }

            //showing reorder button if reorder is available
            if (orderLists.get(position).getIsReOrder() == 1)
            {
                holder.LL_reason.setVisibility(View.GONE);
                holder.reorder.setVisibility(View.VISIBLE);
            } else {
                holder.reorder.setVisibility(View.GONE);
            }
        }


        holder.mOHForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OrderHistory) context).moveorderdetails(orderLists.get(position).getFoodOrderId().toString(), orderLists.get(position).getIsKitchenDelivery());


            }
        });

        holder.reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OrderHistory) context).showCustomDialog(orderLists.get(position).getFoodOrderId().toString(), orderLists.get(position).getProfileId(), orderLists.get(position).getAvgDeliveryTime(), orderLists.get(position).getIsKitchenDelivery(),orderLists.get(position).getCustomerAddress());
            }
        });

        holder.orderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intentt = new Intent(context, Order_Status.class);
                intentt.putExtra(Utils.ORDER_STATUS_CUSTOMER_ADDRESS,""+orderLists.get(position).getCustomerAddress());
                intentt.putExtra(Utils.ORDER_STATUS_FOOD_ORDER_ID,""+orderLists.get(position).getFoodOrderId());
                intentt.putExtra(Utils.ORDER_STATUS_IS_REORDER,""+orderLists.get(position).getIsReOrder());
                intentt.putExtra(Utils.ORDER_STATUS_KITCHEN_NAME,""+orderLists.get(position).getKitchenName());
                intentt.putExtra(Utils.ORDER_STATUS_ORDER_DATE,""+orderLists.get(position).getOrderDate());
                intentt.putExtra(Utils.ORDER_STATUS_ORDER_ID,""+orderLists.get(position).getOrderId());
                intentt.putExtra(Utils.ORDER_STATUS_ORDER_NUMBER,""+orderLists.get(position).getOrderNumber());
                intentt.putExtra(Utils.ORDER_STATUS_ORDER_STATUS,""+orderLists.get(position).getOrderStatus());
                intentt.putExtra(Utils.ORDER_STATUS_TOTAL_AMOUNT,""+orderLists.get(position).getTotalAmount());
                intentt.putExtra("isulyx",orderLists.get(position).getIsKitchenDelivery());
                intentt.putExtra("isreview",orderLists.get(position).getIsReview());
                intentt.putExtra("isulyxreview",orderLists.get(position).getIsUlyxReview());
                intentt.putExtra("kitchenprofileid",orderLists.get(position).getProfileId().toString());
                context.startActivity(intentt);

               // ((OrderHistory) context).finish();
            }
        });


        holder.heartrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent rating = new Intent(context, SubmitReview.class);
                context.startActivity(rating);
            }
        });


        holder.ordercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderHistory) context).cancelorder(orderLists.get(position).getFoodOrderId());
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return orderLists.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dish_price, kitaddress, kitname, orderid, date, od_status, reason;
        Button orderstatus,reorder, ordercancel;
        LinearLayout heartrating , LL_buttonGroup, LL_od_status, mOHForward, LL_reason;

        public MyViewHolder(View view) {
            super(view);
            dish_price=(TextView)view.findViewById(R.id.txt_recent_dishprice);
            kitaddress=(TextView)view.findViewById(R.id.txt_kitadd);
            kitname=(TextView)view.findViewById(R.id.txt_oh_kitname);
            orderid=(TextView)view.findViewById(R.id.txt_oh_orderid);
            date=(TextView)view.findViewById(R.id.txt_oh_orderdate);
            od_status=(TextView)view.findViewById(R.id.txt_od_status);
            reason=(TextView)view.findViewById(R.id.txt_reason_txt);

            mOHForward=(LinearLayout)view.findViewById(R.id.img_forward_oh);
            LL_reason=(LinearLayout)view.findViewById(R.id.ll_reason);
            reorder=(Button) view.findViewById(R.id.oh_reorder);
            orderstatus=(Button) view.findViewById(R.id.but_order_status_history);
            ordercancel=(Button) view.findViewById(R.id.but_order_cancel);
            heartrating = (LinearLayout)view.findViewById(R.id.ll_heart_rating);
            LL_buttonGroup= (LinearLayout)view.findViewById(R.id.ll_buttongroup);
            LL_od_status= (LinearLayout)view.findViewById(R.id.ll_statustxt);

        }
    }
}