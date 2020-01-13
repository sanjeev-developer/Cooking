package com.saavor.user.chefserver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saavor.user.R;

import java.util.ArrayList;

/**
 * Created by user on 21-12-2017.
 */

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.MyViewHolder> {

    ArrayList<String> aLDates;
    ArrayList<String> aLSlots;
    Context context;
    boolean IsDetail;

    public BookingDetailsAdapter(Context context, ArrayList<String> aLDates, ArrayList<String> aLSlots,boolean IsDetail) {
        this.context = context;
        this.aLDates=aLDates;
        this.aLSlots=aLSlots;
        this.IsDetail=IsDetail;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null ;
        if(IsDetail){
             itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_booking_details2, parent, false);
        }else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_booking_details, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtDate.setText(aLDates.get(position));

        String sentence = ""+aLSlots.get(position);
        String search  = ",";

        if (sentence.toLowerCase().indexOf(search.toLowerCase()) != -1 )
        {
            String end = sentence.substring(Math.max(sentence.length() - 5, 0));
            String start = sentence.substring(0, 5);

            if(start.equals("24:00"))
            {
                holder.txtSlotsTime.setText("00:00-"+end);
            }
            else
            {
                System.out.println("I found the keyword");
                holder.txtSlotsTime.setText(start+"-"+end);
            }


        } else {

            System.out.println("not found");
            holder.txtSlotsTime.setText(aLSlots.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return aLDates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        TextView txtSlotsTime;

        public MyViewHolder(View view) {
            super(view);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtSlotsTime = (TextView) view.findViewById(R.id.txtSlotsTime);
        }
    }
}
