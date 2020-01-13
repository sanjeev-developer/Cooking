package com.saavor.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.AddOn;
import com.saavor.user.Model.CustomDishItem;
import com.saavor.user.R;
import com.saavor.user.activity.Dishcoustmize;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class AddonAdapter extends RecyclerView.Adapter<AddonAdapter.MyViewHolder> {

    private ArrayList<CustomDishItem> ratingname;
    private Context context;
    int count = 0;
    double currentprice=0;
    AddOn addOn= new AddOn();
    ArrayList<AddOn>addOns=new ArrayList<AddOn>();

    public AddonAdapter(Context contexts, ArrayList<CustomDishItem> dishname, float currentprice) {
        this.ratingname = dishname;
        this.context = contexts;
        this.currentprice= currentprice;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishcustumlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        if (ratingname.get(position).getItemName().length()>22){
//            holder.addonname.setText(ratingname.get(position).getItemName().substring(0,20)+"...");
//        }else{
            holder.addonname.setText(ratingname.get(position).getItemName());
      //  }


        holder.price.setText("$"+String.format("%.02f",ratingname.get(position).getCost()));
        holder.LLAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.getLayoutPosition();
                holder.getAdapterPosition();
                holder.getOldPosition();
                holder.getPosition();

                    if (holder.tick.getVisibility() == View.VISIBLE)
                    {
                        //Either gone or invisible
                        holder.tick.setVisibility(View.INVISIBLE);

                        double cost=ratingname.get(position).getCost();
                        String z = (new DecimalFormat("##.##").format(cost));
                        System.out.println(">>>>>>"+z);
                        cost =Double.parseDouble(z);

                        currentprice= currentprice-cost;

                        for(int i=0; i<addOns.size(); i++) {
                            String itemname = addOns.get(i).getAddon_name();
                            String itemcheck = ratingname.get(position).getItemName();

                            if (itemname.equals(itemcheck)) {
                                addOns.remove(addOns.get(i));

                               // currentprice = Float.parseFloat(filename + "." + firstTwo(extension));
                                ((Dishcoustmize)context).senddata(currentprice, addOns);
                            }
                        }

                    } else if(holder.tick.getVisibility() == View.INVISIBLE){

                        //Its visible
                        addOn=new AddOn();
                        holder.tick.setVisibility(View.VISIBLE);
                        currentprice= currentprice+ratingname.get(position).getCost();
                        addOn.setAddon_name(ratingname.get(position).getItemName());
                        addOn.setAddon_price(ratingname.get(position).getCost());
                        addOns.add(addOn);
                        ((Dishcoustmize)context).senddata(currentprice,addOns);
                    }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ratingname.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView addonname,price;
        LinearLayout LLAdd;
        ImageView tick;

        public MyViewHolder(View view) {
            super(view);

            addonname = (TextView) view.findViewById(R.id.txt_add_on);
            price = (TextView) view.findViewById(R.id.add_on_price);
            LLAdd = (LinearLayout) view.findViewById(R.id.ll_add);
            tick = (ImageView) view.findViewById(R.id.img_add_tick);

        }
    }
}