package com.saavor.user.chefserver.filters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.*;
import com.saavor.user.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 17-01-2018.
 */

public class CuisineListAdapter extends RecyclerView.Adapter<CuisineListAdapter.MyViewHolder> {

     ArrayList<ChefCuisineList>CuisinearrayList;
     ArrayList<Boolean>booleanArrayList;
     Context context;

    ArrayList<String> SelectedId=new ArrayList<String>();
    ArrayList<String> SelectedCuisine=new ArrayList<String>();
    SharedPreferences.Editor editor1;

    public CuisineListAdapter(Context context,ArrayList<ChefCuisineList>CuisinearrayList,ArrayList<Boolean>booleanArrayList) {
        this.CuisinearrayList=CuisinearrayList;
        this.context=context;
        this.booleanArrayList=booleanArrayList;
        editor1 =context.getSharedPreferences(
                "CuisinesId", MODE_PRIVATE).edit();


        SharedPreferences shared = context.getSharedPreferences("CuisinesId", MODE_PRIVATE);
        Set<String> set = shared.getStringSet("key", null);
        Set<String> set2 = shared.getStringSet("IDkey", null);
        if(set!=null){
            SelectedCuisine.addAll(set);
        }
        if(set2!=null){
            SelectedId.addAll(set2);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cusine_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.title.setText(CuisinearrayList.get(position).getCuisineName());
        holder.cusineselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(booleanArrayList.get(position)==false){
                    booleanArrayList.set(position,true);

                    SelectedCuisine.add(CuisinearrayList.get(position).getCuisineName());

                    SelectedId.add(CuisinearrayList.get(position).getCuisineId());

                    Set<String> set = new HashSet<String>();
                    set.addAll(SelectedCuisine);

                    Set<String> Idset = new HashSet<String>();
                    Idset.addAll(SelectedId);

                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < SelectedId.size(); i++) {
                        str.append(SelectedId.get(i)).append("~");
                    }
                    editor1.putString("Id", str.toString());
                    editor1.putString("Cuisine", SelectedCuisine.toString());
                    editor1.putStringSet("key", set);
                    editor1.putStringSet("IDkey", Idset);
                    editor1.commit();
                }else{
                    booleanArrayList.set(position,false);

                    SelectedCuisine.remove(CuisinearrayList.get(position).getCuisineName());

                    SelectedId.remove(CuisinearrayList.get(position).getCuisineId());

                    Set<String> set = new HashSet<String>();
                    set.addAll(SelectedCuisine);

                    Set<String> Idset = new HashSet<String>();
                    Idset.addAll(SelectedId);

                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < SelectedId.size(); i++) {
                        str.append(SelectedId.get(i)).append("~");
                    }
                    editor1.putString("Id", str.toString());
                    editor1.putString("Cuisine", SelectedCuisine.toString());
                    editor1.putStringSet("key", set);
                    editor1.putStringSet("IDkey", Idset);
                    editor1.commit();
                }
                notifyDataSetChanged();
            }
        });

        if(booleanArrayList.get(position)==true){
            holder.tick.setVisibility(View.VISIBLE);
        }else{
            holder.tick.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return CuisinearrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView tick;
        LinearLayout cusineselect;
        public MyViewHolder(View view) {
            super(view);
            cusineselect = (LinearLayout) view.findViewById(R.id.ll_cuisine_tick);
            title = (TextView) view.findViewById(R.id.txt_cusine_title);
            tick = (ImageView) view.findViewById(R.id.cusine_tick);
        }
    }
}
