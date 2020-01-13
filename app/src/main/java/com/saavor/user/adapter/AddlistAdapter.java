package com.saavor.user.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.DilalogModel;
import com.saavor.user.R;
import com.saavor.user.activity.DashBoard;
import com.google.gson.Gson;

import static com.saavor.user.activity.DashBoard.Dialogdata;

public class AddlistAdapter extends RecyclerView.Adapter<AddlistAdapter.MyViewHolder> {


    private Context context;
    int count = 0;
    public SharedPreferences mDatabase;
    public SharedPreferences.Editor mTabel;
    public Gson gson;


    public AddlistAdapter(Context contexts) {

        this.context = contexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addlistlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.addtitle.setText(Dialogdata.get(position).getType());
        holder.adddesc.setText(Dialogdata.get(position).getDeladdress());

        holder.LL_diaglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mDatabase = context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                mTabel = mDatabase.edit();
                gson = new Gson();

                DilalogModel dilalogModel = new DilalogModel();
                dilalogModel.setAddress(Dialogdata.get(position).getLocality());
                dilalogModel.setDeladdress(Dialogdata.get(position).getDeladdress());
                dilalogModel.setLat(Dialogdata.get(position).getLat());
                dilalogModel.setLongi(Dialogdata.get(position).getLongi());
                dilalogModel.setCity(Dialogdata.get(position).getCity());

                String dialogdata = gson.toJson(dilalogModel);
                mTabel.putString("dialogdata", dialogdata);
                mTabel.commit();

                ((DashBoard) context).commonhit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return Dialogdata.size();
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