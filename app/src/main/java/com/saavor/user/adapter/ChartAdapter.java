
package com.saavor.user.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.CartItem;
import com.saavor.user.R;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.DashBoard;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.saavor.user.activity.Chart.deletelist;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;
import static com.saavor.user.adapter.KitchenAdapter.updatelist;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.MyViewHolder> {

    private Context context;
    int count = 0;
    ArrayList<CartItem> cartItems;
    boolean reorder = false;

    public ChartAdapter(Context contexts, ArrayList<CartItem> near_title, boolean reorder) {
        this.cartItems = near_title;
        this.context = contexts;
        this.reorder = reorder;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        if (cartItems.get(position).getCustomItemsName().toString().equals("")) {

            holder.LL_addon.setVisibility(View.GONE);
            holder.title.setText("" + cartItems.get(position).getDishName().toString());

            String costamount = "" + cartItems.get(position).getPrice();
            int firstIndex = costamount.indexOf('.');


            if (firstIndex == -1) {
                // holder.cost.setText("$ " + t);
                holder.itemprice.setText("$" + String.format("%.02f", cartItems.get(position).getPrice()));
            } else {
                try {
                    String textfile = "" + costamount;
                    String filename = textfile.split("\\.")[0];
                    String extension = textfile.split("\\.")[1];

                    holder.itemprice.setText("$" + filename + "." + firstTwo(extension));
                } catch (Exception e)

                {
                    //holder.cost.setText("$ " + t);
                    holder.itemprice.setText("$ " + String.format("%.02f", cartItems.get(position).getPrice()));
                }
            }

            holder.count = cartItems.get(position).getQuantity();
            holder.cost.setText("$" +  String.format("%.02f", cartItems.get(position).getPrice() * holder.count));
            holder.counting.setText("" + cartItems.get(position).getQuantity());
        }
        else
            {
            holder.LL_addon.setVisibility(View.VISIBLE);
            holder.title.setText("" + cartItems.get(position).getDishName().toString());
            holder.count = cartItems.get(position).getQuantity();

            for (int i = 0; i < storenotcustomizable.size(); i++) {
                int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                int iD2 = Integer.valueOf(cartItems.get(position).getDishId());

                if (iD1 == iD2) {
                    String itemname = cartItems.get(position).getCustomItemsName();
                    String itemcheck = storenotcustomizable.get(i).getItemsName();

                    if (itemname.equals(itemcheck)) {

                        String costamount = "" + storenotcustomizable.get(i).getUpdatedprice();
                        int firstIndex = costamount.indexOf('.');

                        if (firstIndex == -1) {
                            // holder.cost.setText("$ " + t);
                            holder.itemprice.setText("$" + String.format("%.02f", storenotcustomizable.get(i).getUpdatedprice()));
                        } else
                            {
                            try
                            {
                                String textfile = "" + costamount;
                                String filename = textfile.split("\\.")[0];
                                String extension = textfile.split("\\.")[1];

                                holder.itemprice.setText("$" + filename + "." + firstTwo(extension));
                            } catch (Exception e)

                            {
                                //holder.cost.setText("$ " + t);
                                holder.itemprice.setText("$ " + String.format("%.02f",storenotcustomizable.get(i).getUpdatedprice() ));
                            }
                        }
                        holder.cost.setText("$" + String.format("%.02f", storenotcustomizable.get(i).getUpdatedprice() * holder.count));
                    }
                }
            }
            holder.counting.setText("" + cartItems.get(position).getQuantity());
            String result = cartItems.get(position).getCustomItemsName().replaceAll("~", ", ");
            holder.addon.setText("" + result);
            }

        holder.add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (cartItems.get(position).getIsCustomizable() == 1)
                {
                    for (int i = 0; i < storenotcustomizable.size(); i++) {
                        //matching dish id
                        int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                        int iD2 = Integer.valueOf(cartItems.get(position).getDishId());

                        if (iD1 == iD2)
                        {
                            //matching add on item name
                            String itemname = cartItems.get(position).getCustomItemsName();
                            String itemcheck = storenotcustomizable.get(i).getItemsName();

                            if (itemname.equals(itemcheck)) {

                                //fetching the total quantity ordered
                                int ordered_quantity = 0;
                                for (int k = 0; k < storenotcustomizable.size(); k++) {

                                    int arrayid = Integer.valueOf(storenotcustomizable.get(k).getDishId());
                                    int currentid = Integer.valueOf(cartItems.get(position).getDishId());

                                    if (arrayid == currentid) {

                                        ordered_quantity = ordered_quantity + storenotcustomizable.get(k).getQuantity();
                                    }
                                }

                                if (ordered_quantity < storenotcustomizable.get(i).getAvilquantity()) {

                                    holder.count++;
                                    DashBoard.TotalItem++;
                                    //setting the quantity on specific item
                                    storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity() + 1);
                                    holder.counting.setText("" + storenotcustomizable.get(i).getQuantity());

                                    //setting the cost on card
                                    double t = (storenotcustomizable.get(i).getUpdatedprice() * (storenotcustomizable.get(i).getQuantity()));
                                    DecimalFormat df = new DecimalFormat("####0.00");

                                    holder.cost.setText("$" + df.format(t));

                                    //setting subtotal value
                                    DashBoard.totalprice = DashBoard.totalprice + storenotcustomizable.get(i).getUpdatedprice();
                                    ((Chart) context).setvalue(DashBoard.totalprice);

                                    //fetching the total quantity ordered
                                    int cal = 0;
                                    for (int k = 0; k < storenotcustomizable.size(); k++) {
                                        if (storenotcustomizable.get(k).getDishId() == cartItems.get(position).getDishId()) {
                                            cal = cal + storenotcustomizable.get(k).getQuantity();
                                        }
                                    }
                                    //setting item left after quantity
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - cal);
                                } else {
                                    displayAlert(context, "No more item available");
                                    //Toast.makeText(context, "no more item available", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                } else {

                    if (holder.count > 0) {


                        for (int i = 0; i < storenotcustomizable.size(); i++) {

                            int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                            int iD2 = Integer.valueOf(cartItems.get(position).getDishId());

                            if (iD1 == iD2) {
                                if (holder.count < storenotcustomizable.get(i).getAvilquantity()) {


                                    holder.count++;
                                    DashBoard.TotalItem++;

                                    holder.counting.setText("" + holder.count);
                                    System.out.println(">>>>>" + cartItems.get(position).getPrice() * holder.count);

                                    //set quantity order
                                    storenotcustomizable.get(i).setQuantity(holder.count);

                                    //setting item left after quantity
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - storenotcustomizable.get(i).getQuantity());

                                    DecimalFormat df = new DecimalFormat("####0.00");
                                    holder.cost.setText("$" + df.format(cartItems.get(position).getPrice() * holder.count));

                                    // holder.sub.setVisibility(View.VISIBLE);
                                    holder.counting.setText("" + holder.count);

                                    DashBoard.totalprice = DashBoard.totalprice + cartItems.get(position).getPrice();
                                    ((Chart) context).setvalue(DashBoard.totalprice);
                                    break;
                                } else {
                                    // holder.add.setVisibility(View.GONE);
                                    displayAlert(context, "No more item available");
                                    // Toast.makeText(context, "no more item available", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }

        });


        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cartItems.get(position).getIsCustomizable() == 1) {

                    holder.count--;
                    DashBoard.TotalItem--;

                    for (int i = 0; i < storenotcustomizable.size(); i++) {
                        int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                        int iD2 = Integer.valueOf(cartItems.get(position).getDishId());

                        if (iD1 == iD2) {
                            String itemname = cartItems.get(position).getCustomItemsName();
                            String itemcheck = storenotcustomizable.get(i).getItemsName();

                            if (itemname.equals(itemcheck)) {

                                if (storenotcustomizable.get(i).getQuantity() > 1) {
                                    storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity() - 1);

                                    //setting the cost on card
                                    double t = (storenotcustomizable.get(i).getUpdatedprice() * (storenotcustomizable.get(i).getQuantity()));
                                    DecimalFormat df = new DecimalFormat("####0.00");
                                    holder.cost.setText("$" + df.format(t));

                                    //setting order quantity
                                    holder.counting.setText("" + storenotcustomizable.get(i).getQuantity());

                                    //fetching the total quantity ordered
                                    int cal = 0;
                                    for (int k = 0; k < storenotcustomizable.size(); k++) {
                                        if (storenotcustomizable.get(k).getDishId() == cartItems.get(position).getDishId()) {
                                            cal = cal + storenotcustomizable.get(k).getQuantity();
                                        }
                                    }

                                    //setting item left after quantity
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - cal);

                                    //setting subtotal price
                                    DashBoard.totalprice = DashBoard.totalprice - storenotcustomizable.get(i).getUpdatedprice();
                                    ((Chart) context).setvalue(DashBoard.totalprice);

                                } else

                                {
                                    int order_quantity = 0;

                                    for (int m = 0; m < storenotcustomizable.size(); m++) {
                                        int arrayid = Integer.valueOf(storenotcustomizable.get(m).getDishId());
                                        int currentid = Integer.valueOf(cartItems.get(position).getDishId());

                                        for (int f = 0; f < storenotcustomizable.size(); f++) {
                                            if (arrayid == currentid) {

                                                order_quantity = order_quantity + storenotcustomizable.get(f).getQuantity();
                                            }
                                        }
                                        if (order_quantity == 1) {
                                            DeleteCartMenu deleteCartMenu = new DeleteCartMenu();
                                            deleteCartMenu.setDishId(cartItems.get(position).getDishId());
                                            deleteCartMenu.setHolder(storenotcustomizable.get(i).getHolder());
                                            deleteCartMenu.setDeleteprice(storenotcustomizable.get(i).getPrice());
                                            deleteCartMenu.setItemname(storenotcustomizable.get(i).getItemsName());
                                            deleteCartMenu.setIsCustomizable(storenotcustomizable.get(i).getIsCustomizable());
                                            deleteCartMenu.setItemleft(storenotcustomizable.get(i).getAvilquantity());

                                            //checking no of same dishes
                                            int cal = 0;
                                            for (int k = 0; k < storenotcustomizable.size(); k++) {
                                                if (storenotcustomizable.get(k).getDishId() == cartItems.get(position).getDishId()) {
                                                    cal = cal + storenotcustomizable.get(k).getQuantity();
                                                }
                                            }

                                            //setting item left after quantity
                                            deleteCartMenu.setItemleft(storenotcustomizable.get(i).getAvilquantity() - cal);

                                            deletelist.add(deleteCartMenu);
                                            System.out.println(">>>" + deletelist.size());

                                            //setting subtotal value
                                            DashBoard.totalprice = DashBoard.totalprice - storenotcustomizable.get(i).getUpdatedprice();
                                            ((Chart) context).setvalue(DashBoard.totalprice);

                                            // delete from list.
                                            storenotcustomizable.remove(i);
                                            System.out.println(">>>" + storenotcustomizable.size());

                                            //updating recycler view
                                            ((Chart) context).updaterec();

                                            break;
                                        } else {
                                            //setting subtotal value
                                            DashBoard.totalprice = DashBoard.totalprice - storenotcustomizable.get(i).getUpdatedprice();
                                            ((Chart) context).setvalue(DashBoard.totalprice);

                                            //add in delete array
                                            DeleteCartMenu deleteCartMenu = new DeleteCartMenu();
                                            deleteCartMenu.setDishId(cartItems.get(position).getDishId());
                                            deleteCartMenu.setHolder(storenotcustomizable.get(i).getHolder());
                                            deleteCartMenu.setDeleteprice(storenotcustomizable.get(i).getPrice());
                                            deleteCartMenu.setIsCustomizable(storenotcustomizable.get(i).getIsCustomizable());
                                            deleteCartMenu.setItemleft(storenotcustomizable.get(i).getItemleft());
                                            deletelist.add(deleteCartMenu);

                                            // delete from list.
                                            storenotcustomizable.remove(i);
                                            System.out.println(">>>" + storenotcustomizable.size());

                                            //updating recycler view
                                            ((Chart) context).updaterec();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }


                    if (storenotcustomizable.isEmpty()) {

                        if(reorder)
                        {
                            ((Chart) context).finish();
                        }
                        else
                        {
                            updatelist();
                            ((Chart) context).finish();
                        }


                    }


                } else {
                    holder.count--;
                    DashBoard.TotalItem--;
                    System.out.println(">>>>>" + cartItems.get(position).getPrice() * holder.count);
                    if (holder.count > 0) {
                        //  holder.add.setVisibility(View.VISIBLE);

                        for (int i = 0; i < storenotcustomizable.size(); i++) {
                            Log.i("Saavor", "dish id: " + storenotcustomizable.get(i).getDishId() + "dish id2 " + cartItems.get(position).getDishId());

                            int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                            int iD2 = Integer.valueOf(cartItems.get(position).getDishId());

                            if (iD1 == iD2) {

                                //setting the ordered quantity
                                storenotcustomizable.get(i).setQuantity(holder.count);
                                holder.counting.setText("" + holder.count);

                                //setting the cost on card
                                double t = (cartItems.get(position).getPrice() * storenotcustomizable.get(i).getQuantity());

                                DecimalFormat df = new DecimalFormat("####0.00");
                                holder.cost.setText("$" + df.format(t));


                                //setting the subtotal
                                DashBoard.totalprice = DashBoard.totalprice - cartItems.get(position).getPrice();
                                ((Chart) context).setvalue(DashBoard.totalprice);

                                //setting the item left for order
                                storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - storenotcustomizable.get(i).getQuantity());
                            }
                        }

                    } else {

                        for (int i = 0; i < storenotcustomizable.size(); i++) {
                            int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                            int iD2 = Integer.valueOf(cartItems.get(position).getDishId());

                            if (iD1 == iD2) {

                                //setting the cost on card
                                double t = (cartItems.get(position).getPrice() - storenotcustomizable.get(i).getQuantity());

                                DecimalFormat df = new DecimalFormat("####0.00");
                                holder.cost.setText("$" + df.format(t));


                                //setting the subtotal
                                DashBoard.totalprice = DashBoard.totalprice - cartItems.get(position).getPrice();
                                ((Chart) context).setvalue(DashBoard.totalprice);

                                //setting the item left for order
                                storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity());

                                //making a new delete items array
                                DeleteCartMenu deleteCartMenu = new DeleteCartMenu();
                                deleteCartMenu.setDishId(cartItems.get(position).getDishId());
                                deleteCartMenu.setHolder(storenotcustomizable.get(i).getHolder());
                                deleteCartMenu.setDeleteprice(storenotcustomizable.get(i).getPrice());
                                deleteCartMenu.setIsCustomizable(storenotcustomizable.get(i).getIsCustomizable());
                                deleteCartMenu.setItemleft(storenotcustomizable.get(i).getItemleft());
                                deletelist.add(deleteCartMenu);

                                // deleteling item from cart and array as well.
                                storenotcustomizable.remove(i);

                                //updating cart recyclerview
                                ((Chart) context).updaterec();

                                break;
                            }
                        }
                    }

                    if (storenotcustomizable.isEmpty()) {
                        ((Chart) context).finish();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, extra, cost, counting, itemprice, addon;
        Button add, sub;
        int count = 0;
        LinearLayout LL_addon;

        public MyViewHolder(View view) {
            super(view);

            addon = (TextView) view.findViewById(R.id.txt_addon);
            LL_addon = (LinearLayout) view.findViewById(R.id.ll_addon);
            cost = (TextView) view.findViewById(R.id.txt_tad);
            itemprice = (TextView) view.findViewById(R.id.txt_item_price);
            title = (TextView) view.findViewById(R.id.txt_cart_title);
            add = (Button) view.findViewById(R.id.but_cart_add);
            sub = (Button) view.findViewById(R.id.but_cart_sub);
            counting = (TextView) view.findViewById(R.id.txt_cart_count);
        }
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }

    public static void displayAlert(Context mContext, String strMessage) {

        AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setTitle("Alert");
        mDialog.setMessage(strMessage)
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
    }
}