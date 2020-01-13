
package com.saavor.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saavor.user.Model.DealList;
import com.saavor.user.R;

import java.util.ArrayList;

    public class XYZKitchenAdapter extends RecyclerView.Adapter<com.saavor.user.adapter.XYZKitchenAdapter.MyViewHolder> {

        private ArrayList<DealList> ratingname;
        private Context context;

        public XYZKitchenAdapter(Context contexts, ArrayList<DealList> dishname) {
            this.ratingname = dishname;
            this.context = contexts;
        }

        @Override
        public com.saavor.user.adapter.XYZKitchenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealaround, parent, false);

            return new com.saavor.user.adapter.XYZKitchenAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final com.saavor.user.adapter.XYZKitchenAdapter.MyViewHolder holder, final int position) {

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

            holder.validdate.setText(firstWord+" "+restOfString+", "+third);

        }

        @Override
        public int getItemCount() {
            return ratingname.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView maintitle, validdate;

            public MyViewHolder(View view) {
                super(view);

                maintitle = (TextView) view.findViewById(R.id.txt_main);
                validdate= (TextView) view.findViewById(R.id.title1_date);

            }
        }
    }