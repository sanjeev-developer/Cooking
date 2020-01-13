package com.saavor.user.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.AddressList;
import com.saavor.user.R;
import com.saavor.user.activity.AddAddress;
import com.saavor.user.activity.AddressBook;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.DashBoard;

import java.util.ArrayList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.MyViewHolder> {

    private ArrayList<AddressList> addlist;
    private Context context;
    private int linearS=0;
    public AddressBookAdapter(Context contexts, ArrayList<AddressList> addlist,int linearS)
    {

        this.addlist = addlist;
        this.context=contexts;
        this.linearS=linearS;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressbook_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.addbook_add1.setText(addlist.get(position).getAddressLine1().toString());
        if(addlist.get(position).getAddressLine2().toString()=="" ||addlist.get(position).getAddressLine2().toString().isEmpty() ||addlist.get(position).getAddressLine2().toString()==null)
        {
            holder.addbook_add2.setVisibility(View.GONE);
        }
        else
        {
            holder.addbook_add2.setText(addlist.get(position).getAddressLine2().toString());
        }

        if(addlist.get(position).getInstructions().toString()=="" ||addlist.get(position).getInstructions().toString().isEmpty() ||addlist.get(position).getInstructions().toString()==null)
        {
            holder.LL_inst.setVisibility(View.GONE);
        }
        else
        {
            holder.instructions.setText(addlist.get(position).getInstructions().toString());
        }

        holder.addbook_city.setText(addlist.get(position).getCityName().toString());
        holder.addbook_state.setText(addlist.get(position).getStateName().toString() + " " + addlist.get(position).getZipCode());
        holder.addbook_country.setText(addlist.get(position).getCountryName().toString());
        holder.addtype.setText(addlist.get(position).getAddressType().toString());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                                        ((AddressBook) context).deletediag(addlist.get(position).getAddressId().toString(), addlist.get(position).getAddressLine1().toString() + ", " + addlist.get(position).getCityName().toString() + ", " + addlist.get(position).getStateName().toString() + ", " + addlist.get(position).getCountryName().toString());

            }});

        holder.edit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddAddress.class);
                intent.putExtra("addline1", addlist.get(position).getAddressLine1());
                intent.putExtra("addline2", addlist.get(position).getAddressLine2());
                intent.putExtra("city", "" +  addlist.get(position).getCityName());
                intent.putExtra("state", "" + addlist.get(position).getStateName());
                intent.putExtra("zip", "" + addlist.get(position).getZipCode());
                intent.putExtra("country", "" + addlist.get(position).getCountryName());
                intent.putExtra("Instructions", "" + addlist.get(position).getInstructions());
                intent.putExtra("type", "" + addlist.get(position).getAddressType());
                intent.putExtra("locality", "" + addlist.get(position).getLocality());
                intent.putExtra("addid", addlist.get(position).getAddressId().toString());
                intent.putExtra("lat", addlist.get(position).getLatitude().toString());
                intent.putExtra("long", addlist.get(position).getLongitude().toString());

                intent.putExtra("isedit", true);

                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount()
    {
        return addlist.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView addbook_add1,addbook_add2,addbook_city,addbook_state,addbook_country,instructions,addtype;
        LinearLayout LL_inst,LL_selectable;
        ImageView delete,edit_add;

        public MyViewHolder(View view) {
            super(view);

            addtype =(TextView)view.findViewById(R.id.add_type);
            delete =(ImageView)view.findViewById(R.id.txt_delete_add);
            addbook_add1 =(TextView)view.findViewById(R.id.txt_addbook_add1);
            addbook_add2 =(TextView)view.findViewById(R.id.txt_addbook_add2);
            addbook_city =(TextView)view.findViewById(R.id.txt_addbook_city);
            addbook_state =(TextView)view.findViewById(R.id.txt_addbook_state);
            addbook_country =(TextView)view.findViewById(R.id.txt_addbook_country);
            instructions =(TextView)view.findViewById(R.id.txt_addbook_inst);
            LL_inst= (LinearLayout) view.findViewById(R.id.ll_instructions);
            edit_add=(ImageView)view.findViewById(R.id.img_edit_add);

            if(linearS == 1)
            {
                LL_selectable= (LinearLayout) view.findViewById(R.id.ll_selectable);
                delete.setVisibility(View.GONE);
            }
        }
    }
}