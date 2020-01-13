package com.saavor.user.adapter;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.saavor.user.Model.CustomDishItem;
        import com.saavor.user.R;

        import java.text.DecimalFormat;
        import java.util.ArrayList;


    public class DishDetailAdapter extends RecyclerView.Adapter<DishDetailAdapter.MyViewHolder> {

        private ArrayList<CustomDishItem> dishdetail;
        private Context context;
        int count = 0 ;


        public DishDetailAdapter(Context contexts, ArrayList<CustomDishItem> dishdetail)
        {
            this.dishdetail = dishdetail;
            this.context=contexts;
        }

        @Override
        public com.saavor.user.adapter.DishDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_layout, parent, false);
            return new com.saavor.user.adapter.DishDetailAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final com.saavor.user.adapter.DishDetailAdapter.MyViewHolder holder, int position)
        {
            holder.dishname.setText(dishdetail.get(position).getItemName());

            DecimalFormat df = new DecimalFormat("####0.00");
            System.out.println("Value: " + dishdetail.get(position).getCost());
            double cost = Double.parseDouble(df.format(dishdetail.get(position).getCost()));

            holder.dishprice.setText("$"+cost);

        }

        @Override
        public int getItemCount()
        {
            return dishdetail.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView dishname,dishprice;

            public MyViewHolder(View view) {
                super(view);

                dishname=(TextView)view.findViewById(R.id.txt_custom_title);
                dishprice=(TextView)view.findViewById(R.id.txt_custom_price);

            }
        }

    }
