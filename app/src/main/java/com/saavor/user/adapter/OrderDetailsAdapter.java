package com.saavor.user.adapter;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.saavor.user.Model.OrderDish;
        import com.saavor.user.R;
        import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    private ArrayList<OrderDish> orderDishes;
    private ArrayList<Integer> subtotal = new ArrayList<>();
    private Context context;
    int count = 0 ;

    public OrderDetailsAdapter(Context contexts, ArrayList<OrderDish> near_title)
    {
        this.orderDishes = near_title;
        this.context=contexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetails_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {

        holder.cost.setText("$" + String.format("%.02f",orderDishes.get(position).getPrice()));
        holder.title.setText(orderDishes.get(position).getDishName().toString());
        holder.quantity.setText(orderDishes.get(position).getQuantity().toString() + " ");

        if (orderDishes.get(position).getIsCustomizable() == 1)
        {
            if (orderDishes.get(position).getCustomItemsCost().toString().equals(""))
            {
                double sendtotal=orderDishes.get(position).getQuantity() * orderDishes.get(position).getPrice();
                ((com.saavor.user.activity.OrderDetails) context).setsub(sendtotal);
                holder.cdprice.setText("$" + String.format("%.02f",(orderDishes.get(position).getQuantity() * orderDishes.get(position).getPrice())));
                holder.addonview.setVisibility(View.GONE);
            }
            else {
                String check = "" + orderDishes.get(position).getCustomItemsCost();
                int evaluate = check.indexOf('~');

                if (evaluate == -1) {
                    float t = orderDishes.get(position).getQuantity() * (orderDishes.get(position).getPrice() + Float.parseFloat(orderDishes.get(position).getCustomItemsCost()));
                    float sendtotal=t;
                    ((com.saavor.user.activity.OrderDetails) context).setsub(sendtotal);
                    holder.cdprice.setText("$" + String.format("%.02f",t));
                    holder.orderextra.setText(orderDishes.get(position).getCustomItemsName());
                } else {
                    try {
                        float z = 0;
                        String[] aSplit = check.split("~");

                        for (int i = 0; i < aSplit.length; i++) {
                            float d = Float.parseFloat(aSplit[i]);
                            z = z + d;
                        }
                        double t = orderDishes.get(position).getQuantity() * (orderDishes.get(position).getPrice() + z);
                        double sendtotal=t;
                        ((com.saavor.user.activity.OrderDetails) context).setsub(sendtotal);
                        holder.cdprice.setText("$" + String.format("%.02f",t));

                        String my_new_str = orderDishes.get(position).getCustomItemsName().replaceAll("~", ", ");
                        holder.orderextra.setText(my_new_str);
                        System.out.println(my_new_str);


                    } catch (Exception e)

                    {
                        System.out.println("final" + e);
                    }
                }
            }
        }
        else
        {
            //double sendtotal=orderDishes.get(position).getQuantity() * orderDishes.get(position).getPrice();
            ((com.saavor.user.activity.OrderDetails) context).setsub(orderDishes.get(position).getQuantity() * orderDishes.get(position).getPrice());
            holder.cdprice.setText("$" + String.format("%.02f",(orderDishes.get(position).getQuantity() * orderDishes.get(position).getPrice())));
            holder.addonview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return orderDishes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cost,title,quantity,extra, cdprice, orderextra;
        LinearLayout addonview;

        public MyViewHolder(View view)
        {
            super(view);
            cdprice = (TextView) view.findViewById(R.id.txt_cdp);
            title=(TextView)view.findViewById(R.id.txt_order_title);
            quantity=(TextView)view.findViewById(R.id.txt_order_quantity);
            extra=(TextView)view.findViewById(R.id.txt_order_extra);
            cost=(TextView)view.findViewById(R.id.txt_order_cost);
            orderextra= (TextView) view.findViewById(R.id.txt_order_extra);
            addonview= (LinearLayout) view.findViewById(R.id.ll_addonViewgone);
        }
    }
}