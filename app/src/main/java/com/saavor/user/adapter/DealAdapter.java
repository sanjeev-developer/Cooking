
package com.saavor.user.adapter;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import com.saavor.user.R;
        import java.util.ArrayList;


public class DealAdapter extends RecyclerView.Adapter<DealAdapter.MyViewHolder> {

    private ArrayList<String> near_title;
    private ArrayList<String> near_dishtype;
    private ArrayList<String> near_dishprice;
    private ArrayList<String> near_dishtime;
    private ArrayList<String> deal_discount;
    private Context context;
    int count = 0 ;

    public DealAdapter(Context contexts, ArrayList<String> near_title, ArrayList<String> near_dishtype, ArrayList<String> near_dishprice, ArrayList<String> near_dishtime,ArrayList<String> deal_discount)
    {
        this.near_dishtype = near_dishtype;
        this.near_title = near_title;
        this.near_dishprice = near_dishprice;
        this.near_dishtime = near_dishtime;
        this.deal_discount = deal_discount;
        this.context=contexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.title.setText(near_title.get(position));
        holder.dishtype.setText(near_dishtype.get(position));
        holder.dishprice.setText(near_dishprice.get(position));
        holder.dishttime.setText(near_dishtime.get(position));
        holder.deal_discount.setText(deal_discount.get(position));
    }

    @Override
    public int getItemCount()
    {
        return near_dishprice.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,dishtype,dishprice,dishttime,deal_discount;

        public MyViewHolder(View view) {
            super(view);

            title=(TextView)view.findViewById(R.id.txt_deal_title);
            dishtype=(TextView)view.findViewById(R.id.txt_deal_dishtype);
            dishprice=(TextView)view.findViewById(R.id.txt_deal_dishamount);
            dishttime=(TextView)view.findViewById(R.id.txt_deal_dishtime);
            deal_discount=(TextView)view.findViewById(R.id.txt_deal_discount);
        }
    }
}