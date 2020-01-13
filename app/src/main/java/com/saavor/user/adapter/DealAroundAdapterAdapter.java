package com.saavor.user.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.DealList;
import com.saavor.user.R;
import com.saavor.user.activity.XYZKitchen;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by a123456 on 28/09/17.
 */

public class DealAroundAdapterAdapter extends RecyclerView.Adapter<DealAroundAdapterAdapter.MyViewHolder> {

    private ArrayList<DealList> ratingname;
    private Context context;
    int from;
    boolean deal=false;

    public DealAroundAdapterAdapter(Context contexts, ArrayList<DealList> dishname, int fromWhere, boolean deall) {
        this.ratingname = dishname;
        this.context = contexts;
        from = fromWhere;
        deal=deall;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealaround, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.maintitle.setText(ratingname.get(position).getDealTitle());

        String myString = ratingname.get(position).getEndDate().toString();
        String title1date;

        String[] aSplit = myString.split(" ");

        title1date=aSplit[0];

        String firstWord="", restOfString="",third="";
        String[] aSplitt = title1date.split("-");

        firstWord=aSplitt[0];
        restOfString=aSplitt[1];
        third=aSplitt[2];

        System.out.println(firstWord+" "+restOfString+", "+third);

        if (firstWord.equals("1")){
            firstWord = "Jan";
        }else if(firstWord.equals("2")){
            firstWord = "Feb";
        }else if(firstWord.equals("3")){
            firstWord = "Mar";
        }else if(firstWord.equals("4")){
            firstWord = "Apr";
        }else if(firstWord.equals("5")){
            firstWord = "May";
        }else if(firstWord.equals("6")){
            firstWord = "Jun";
        }else if(firstWord.equals("7")){
            firstWord = "Jul";
        }else if(firstWord.equals("8")){
            firstWord = "Aug";
        }else if(firstWord.equals("9")){
            firstWord = "Sep";
        }else if(firstWord.equals("10")){
            firstWord = "Oct";
        }else if(firstWord.equals("11")){
            firstWord = "Nov";
        }else if(firstWord.equals("12")){
            firstWord = "Dec";
        }

        holder.validdate.setText(firstWord+" "+restOfString+", "+third);

        holder.deal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(deal)
                {
                    try {
                        ((XYZKitchen) context).showpop(ratingname.get(position).getDealTitle(), ratingname.get(position).getDescription(), ratingname.get(position).getDealDiscount(),ratingname.get(position).getStartDate(), ratingname.get(position).getEndDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    if (from == 1){
                        //  Toast.makeText(context,"deal " + position,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, XYZKitchen.class);
                        intent.putExtra("profileid", ratingname.get(position).getProfileId());
                        context.startActivity(intent);
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return ratingname.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView maintitle, validdate;
        LinearLayout deal_layout;

        public MyViewHolder(View view) {
            super(view);

            maintitle = (TextView) view.findViewById(R.id.txt_main);
            validdate= (TextView) view.findViewById(R.id.title1_date);
            deal_layout= (LinearLayout) view.findViewById(R.id.deal_layout);

        }
    }
}