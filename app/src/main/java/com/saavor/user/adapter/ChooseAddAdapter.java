package com.saavor.user.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.saavor.user.Model.AddressList;
import com.saavor.user.Model.DilalogModel;
import com.saavor.user.R;
import com.saavor.user.activity.AddAddress;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.DashBoard;

import java.util.ArrayList;

import static com.saavor.user.activity.DashBoard.Dialogdata;

/**
 * Created by a123456 on 16/02/18.
 */

public class ChooseAddAdapter  extends RecyclerView.Adapter<ChooseAddAdapter.MyViewHolder> {

    private Context context;
    int count = 0;
    public SharedPreferences mDatabase;
    public SharedPreferences.Editor mTabel;
    public Gson gson;
    ArrayList<AddressList> addlist;

    public ChooseAddAdapter(ArrayList<AddressList> addlist, Context contexts) {

        this.context = contexts;
        this.addlist = addlist;
    }

    @Override
    public ChooseAddAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addlistlayout, parent, false);
        return new ChooseAddAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChooseAddAdapter.MyViewHolder holder, final int position)
    {
        holder.addtitle.setText(addlist.get(position).getAddressType());
        holder.adddesc.setText("  "+addlist.get(position).getLocality());

        holder.LL_diaglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                if(addlist.get(position).getAddressLine2() == null || addlist.get(position).getAddressLine2().equals(""))
                {
                    ((AddAddress) context).sendrecadd(addlist.get(position).getAddressLine1()+ ", " + addlist.get(position).getCityName() + ", " + addlist.get(position).getStateName() + ", " + addlist.get(position).getCountryName() + ", " + addlist.get(position).getZipCode(), addlist.get(position).getLocality(), addlist.get(position).getLatitude(), addlist.get(position).getLongitude());
                }
                else {
                    ((AddAddress) context).sendrecadd(addlist.get(position).getAddressLine1()+ ", " + addlist.get(position).getAddressLine2()+ ", " + addlist.get(position).getCityName() + ", " + addlist.get(position).getStateName() + ", " + addlist.get(position).getCountryName() + ", " + addlist.get(position).getZipCode(), addlist.get(position).getLocality(), addlist.get(position).getLatitude(), addlist.get(position).getLongitude());

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return addlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView addtitle, adddesc;
        LinearLayout LL_diaglist;

        public MyViewHolder(View view) {
            super(view);

            addtitle = (TextView) view.findViewById(R.id.txt_add_tile);
            adddesc = (TextView) view.findViewById(R.id.txt_add_desc);
            LL_diaglist= (LinearLayout) view.findViewById(R.id.ll_diag_list);
        }
    }
}
