package com.saavor.user.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.CusineHit;
import com.saavor.user.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.MyViewHolder> {

    private Context context;
    public View t;
    public SharedPreferences mDatabase;
    public SharedPreferences.Editor mTabel;
    public Gson gson;
    String json;
    private CusineHit cusinehit;
    SharedPreferences.Editor editor1;


    ArrayList<Integer> SelectedId=new ArrayList<Integer>();


    public CuisineAdapter(Context contexts) {
        this.context = contexts;


        mDatabase = contexts.getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        mTabel = mDatabase.edit();
        gson = new Gson();

        json = mDatabase.getString("statusdata", null);
        cusinehit = gson.fromJson(json, CusineHit.class);

        editor1 =contexts.getSharedPreferences(
                "CuisinesId", MODE_PRIVATE).edit();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cusine_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(cusinehit.getCuisineList().get(position).getCuisineName());
        if (cusinehit.getCuisineList().get(position).getStatus() == 1) {
            holder.tick.setVisibility(View.VISIBLE);

        } else {
            holder.tick.setVisibility(View.INVISIBLE);

        }


        holder.cusineselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cusinehit.getCuisineList().get(position).getStatus() == 0) {
                    cusinehit.getCuisineList().get(position).setStatus(1);

                    holder.tick.setVisibility(View.VISIBLE);

                    json = gson.toJson(cusinehit);
                    mTabel.putString("statusdata", json);
                    mTabel.commit();


                    SelectedId.add(cusinehit.getCuisineList().get(position).getCuisineId());

                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < SelectedId.size(); i++) {
                        str.append(SelectedId.get(i)).append("~");
                    }

                    editor1.putString("Id", str.toString());
                    editor1.commit();

                } else if (cusinehit.getCuisineList().get(position).getStatus() == 1) {
                    cusinehit.getCuisineList().get(position).setStatus(0);
                    holder.tick.setVisibility(View.INVISIBLE);

                    json = gson.toJson(cusinehit);
                    mTabel.putString("statusdata", json);
                    mTabel.commit();

                    SelectedId.remove(cusinehit.getCuisineList().get(position).getCuisineId());

                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < SelectedId.size(); i++) {
                        str.append(SelectedId.get(i)).append("~");
                    }

                    editor1.putString("Id", str.toString());
                    editor1.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cusinehit.getCuisineList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView tick;
        LinearLayout cusineselect;
        View v;

        public MyViewHolder(View view) {
            super(view);

            cusineselect = (LinearLayout) view.findViewById(R.id.ll_cuisine_tick);
            title = (TextView) view.findViewById(R.id.txt_cusine_title);
            tick = (ImageView) view.findViewById(R.id.cusine_tick);
            v = view;
        }
    }
}