package com.saavor.user.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.DishList;
import com.saavor.user.Model.MenuCartModel;
import com.saavor.user.Model.NonCustomizableList;
import com.saavor.user.R;
import com.saavor.user.activity.CompletePayment;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.activity.DishDetails;
import com.saavor.user.activity.Dishcoustmize;
import com.saavor.user.activity.Kitchen;
import com.google.gson.Gson;
import com.saavor.user.activity.OrderHistory;

import java.util.ArrayList;

import static com.saavor.user.activity.Chart.chartcontext;
import static com.saavor.user.activity.Chart.deletelist;
import static com.saavor.user.activity.DashBoard.storenotcustomizable;

/**
 * Created by a123456 on 21/04/17.
 */

public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.MyViewHolder> {

    private ArrayList<DishList> dishdata;
    private static Activity context;
    int t = 0;
    double result = 0;
    private int position = 0;
    private double updatedprice = 0;
    double addonprice = 0;
    MenuCartModel menuCartModel = new MenuCartModel();
    NonCustomizableList nonCustomizableList = new NonCustomizableList();

    public SharedPreferences mDatabase;
    public SharedPreferences.Editor mTabel;
    public SharedPreferences sharedPrefs;
    public SharedPreferences.Editor editor;
    public Gson gson;
    public Boolean multiadd = false;
    public boolean ordernow;
    public Integer orderdishid;
    boolean cartitems =false;

    public KitchenAdapter(Activity contexts, ArrayList<DishList> dishname, boolean ordernow, Integer orderdishid, boolean cartitems) {
        this.dishdata = dishname;
        this.context = contexts;
        mDatabase = contexts.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        mTabel = mDatabase.edit();
        gson = new Gson();
        this.ordernow = ordernow;
        this.orderdishid = orderdishid;
        this.cartitems=cartitems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchenlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.m_dish_name.setText(dishdata.get(position).getDishName());
        holder.m_dish_cost.setText("$" + String.format("%.02f", dishdata.get(position).getPrice()));
        holder.m_dish_calorie.setText(dishdata.get(position).getCalories().toString());

        if(new String("Vegetarian").equals(dishdata.get(position).getCategory().toString()) )
        {
            holder.m_dish_type.setText("Vegetarian");
        }
        else
        {
            holder.m_dish_type.setText("Non-Vegetarian");
        }

        if(dishdata.get(position).getAvailableQty() <= 0)
        {
            holder.LL_soldout.setVisibility(View.GONE);
            holder.m_dish_left.setText("Sold Out");
        }
        else
        {
            holder.m_dish_left.setText(dishdata.get(position).getAvailableQty().toString() + " left");
        }

        if (dishdata.get(position).getCustomizable() == 1) {
            holder.m_dish_coustmizable.setVisibility(View.VISIBLE);
        } else {
            holder.m_dish_coustmizable.setVisibility(View.GONE);
        }

        if(ordernow)
        {
            int iD1 = Integer.valueOf(dishdata.get(position).getDishId());
            int iD2 = Integer.valueOf(orderdishid);
            if(iD1 == iD2)
            {
                if(dishdata.get(position).getCustomizable() == 0)
                {

                    holder.count++;
                    DashBoard.TotalItem++;

                    if (holder.count > 0) {
                        holder.sub.setVisibility(View.VISIBLE);

                        if (holder.count <= dishdata.get(position).getAvailableQty()) {
                            holder.counting.setText("" + holder.count);

                            if (holder.count == dishdata.get(position).getAvailableQty()) {
                                holder.add.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < storenotcustomizable.size(); i++) {
                                if (storenotcustomizable.get(i).getDishId() == dishdata.get(position).getDishId()) {

                                    storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity() + 1);
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - holder.count);
                                    DashBoard.totalprice = DashBoard.totalprice + dishdata.get(position).getPrice();
                                    ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

                                    //setting item left after quantity
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - storenotcustomizable.get(i).getQuantity());
                                    holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");
                                    multiadd = true;

                                    break;
                                }
                            }

                            if (!multiadd)
                            {
                                NonCustomizableList nonCustomizableList = new NonCustomizableList();
                                nonCustomizableList.setQuantity(1);
                                nonCustomizableList.setDishId(dishdata.get(position).getDishId());
                                nonCustomizableList.setDishname(dishdata.get(position).getDishName());
                                nonCustomizableList.setPrice(dishdata.get(position).getPrice());
                                nonCustomizableList.setItemleft(dishdata.get(position).getAvailableQty() - holder.count);
                                nonCustomizableList.setHolder(holder);
                                nonCustomizableList.setAvilquantity(dishdata.get(position).getAvailableQty());
                                nonCustomizableList.setPosition(position);
                                nonCustomizableList.setCalories(dishdata.get(position).getCalories());
                                nonCustomizableList.setIsCustomizable(dishdata.get(position).getCustomizable());
                                nonCustomizableList.setPreprationtime(dishdata.get(position).getPreparingTime());
                                nonCustomizableList.setItemsName("");
                                nonCustomizableList.setItemsCost("");

                                //saving object only for first time
                                storenotcustomizable.add(nonCustomizableList);

                                //setting dish left
                                holder.m_dish_left.setText(dishdata.get(position).getAvailableQty() - holder.count + " left");

                                //setting total price of selected dish on button
                                DashBoard.totalprice = DashBoard.totalprice + dishdata.get(position).getPrice();
                                ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);
                            }
                        }
                        else
                        {

                        }

                        multiadd = false;
                    }
                }
            }
        }


        //updating cart items if exist
        if(cartitems)
        {

            if(dishdata.get(position).getCustomizable() == 0)
            {
                for (int i = 0; i < storenotcustomizable.size(); i++) {

                    int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                    int iD2 = Integer.valueOf(dishdata.get(position).getDishId());
                    if(iD1 == iD2)
                    {
                        //setting quantity on card
                        if(dishdata.get(position).getAvailableQty() <= 0)
                        {
                            holder.LL_soldout.setVisibility(View.GONE);
                            holder.m_dish_left.setText("Sold Out");
                            storenotcustomizable.remove(i);
                        }
                        else if(dishdata.get(position).getAvailableQty() == storenotcustomizable.get(i).getQuantity())
                        {
                            holder.add.setVisibility(View.GONE);
                            holder.sub.setVisibility(View.VISIBLE);
                            holder.counting.setText(""+storenotcustomizable.get(i).getQuantity());
                            holder.m_dish_left.setText("0 left");

                            int z = (Integer) storenotcustomizable.get(i).getQuantity();
                            holder.count = z;
                        }
                        else if(dishdata.get(position).getAvailableQty() > storenotcustomizable.get(i).getQuantity())
                        {
                            holder.sub.setVisibility(View.VISIBLE);
                            holder.counting.setText(""+storenotcustomizable.get(i).getQuantity());
                            holder.m_dish_left.setText((dishdata.get(position).getAvailableQty() - storenotcustomizable.get(i).getQuantity())+"  left");

                            int z = (Integer) storenotcustomizable.get(i).getQuantity();
                            holder.count = z;
                        }

                        else if(dishdata.get(position).getAvailableQty() < storenotcustomizable.get(i).getQuantity())
                        {
                            holder.sub.setVisibility(View.VISIBLE);
                            holder.m_dish_left.setText(dishdata.get(position).getAvailableQty()+"  left");
                            storenotcustomizable.get(i).setQuantity(dishdata.get(position).getAvailableQty());
                            holder.counting.setText(""+storenotcustomizable.get(i).getQuantity());

                            int z = (Integer) storenotcustomizable.get(i).getQuantity();
                            holder.count = z;
                        }
                    }
                }
            }

            else if(dishdata.get(position).getCustomizable() == 1)
            {
                for (int i = 0; i < storenotcustomizable.size(); i++)
                {
                    int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                    int iD2 = Integer.valueOf(dishdata.get(position).getDishId());
                    int order_quantity = 0;

                    if(iD1 == iD2)
                    {
                        for (int f = 0; f < storenotcustomizable.size(); f++)
                        {
                            int test = Integer.valueOf(storenotcustomizable.get(f).getDishId());
                            if (iD2 == test)
                            {
                                order_quantity = order_quantity + storenotcustomizable.get(f).getQuantity();
                            }
                        }

                        //setting quantity on card
                        if(dishdata.get(position).getAvailableQty() <= 0)
                        {
                            holder.LL_soldout.setVisibility(View.GONE);
                            holder.m_dish_left.setText("Sold Out");

                            for (int f = 0; f < storenotcustomizable.size(); f++)
                            {
                                int test = Integer.valueOf(storenotcustomizable.get(f).getDishId());
                                if (iD2 == test)
                                {
                                    storenotcustomizable.remove(f);
                                }
                            }
                        }
                        else if(dishdata.get(position).getAvailableQty() == order_quantity)
                        {
                            holder.add.setVisibility(View.GONE);
                            holder.sub.setVisibility(View.VISIBLE);
                            holder.counting.setText(""+order_quantity);
                            holder.m_dish_left.setText("0 left");

                            //int z = (Integer) order_quantity;
                            holder.count = order_quantity;
                        }
                        else if(dishdata.get(position).getAvailableQty() > order_quantity)
                        {
                            holder.sub.setVisibility(View.VISIBLE);
                            holder.counting.setText(""+order_quantity);
                            holder.m_dish_left.setText((dishdata.get(position).getAvailableQty() - order_quantity)+"  left");

                          //  int z = (Integer) order_quantity;
                            holder.count = order_quantity;
                        }

                        else if(dishdata.get(position).getAvailableQty() < order_quantity)
                        {
//                            holder.sub.setVisibility(View.VISIBLE);
//                            holder.m_dish_left.setText(dishdata.get(position).getAvailableQty()+"  left");
//                            storenotcustomizable.get(i).setQuantity(dishdata.get(position).getAvailableQty());
//                            holder.counting.setText(""+storenotcustomizable.get(i).getQuantity());
//
//                            int z = (Integer) storenotcustomizable.get(i).getQuantity();
//                            holder.count = z;
                        }
                    }
                }
            }
        }


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dishdata.get(position).getCustomizable() == 1) {

                    System.out.println(">>>>>" + dishdata.get(position).getDishId());
                    Intent intent = new Intent(context, Dishcoustmize.class);
                    intent.putExtra("dishid", dishdata.get(position).getDishId());
                    intent.putExtra("dishprice", dishdata.get(position).getPrice());
                    intent.putExtra("position", position);
                    intent.putExtra("count", holder.count);
                    int aq = Integer.valueOf(dishdata.get(position).getAvailableQty());
                    intent.putExtra("totalavailable", aq);
                    intent.putExtra("itemleft", dishdata.get(position).getAvailableQty() - holder.count);
                    intent.putExtra("preprationtime", dishdata.get(position).getPreparingTime());

                    Dishcoustmize.receiveholder(holder);//,Kitchen.storenotcustomizable,Kitchen.storeCustomizableLists

                    context.startActivity(intent);

                } else {
                    holder.count++;
                    DashBoard.TotalItem++;

                    if (holder.count > 0) {
                        holder.sub.setVisibility(View.VISIBLE);

                        if (holder.count <= dishdata.get(position).getAvailableQty()) {
                            holder.counting.setText("" + holder.count);

                            if (holder.count == dishdata.get(position).getAvailableQty()) {
                                holder.add.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < storenotcustomizable.size(); i++) {

                                int database_dish = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                                int selected_dish = Integer.valueOf(dishdata.get(position).getDishId());

                                if ( database_dish == selected_dish) {

                                    storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity() + 1);
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - holder.count);
                                    DashBoard.totalprice = DashBoard.totalprice + dishdata.get(position).getPrice();
                                    ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

                                    //setting item left after quantity
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - storenotcustomizable.get(i).getQuantity());
                                    holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");
                                    multiadd = true;

                                    break;
                                }
                            }

                            if (!multiadd)
                            {
                                NonCustomizableList nonCustomizableList = new NonCustomizableList();
                                nonCustomizableList.setQuantity(1);
                                nonCustomizableList.setDishId(dishdata.get(position).getDishId());
                                nonCustomizableList.setDishname(dishdata.get(position).getDishName());
                                nonCustomizableList.setPrice(dishdata.get(position).getPrice());
                                nonCustomizableList.setItemleft(dishdata.get(position).getAvailableQty() - holder.count);
                                nonCustomizableList.setHolder(holder);
                                nonCustomizableList.setAvilquantity(dishdata.get(position).getAvailableQty());
                                nonCustomizableList.setPosition(position);
                                nonCustomizableList.setCalories(dishdata.get(position).getCalories());
                                nonCustomizableList.setIsCustomizable(dishdata.get(position).getCustomizable());
                                nonCustomizableList.setPreprationtime(dishdata.get(position).getPreparingTime());
                                nonCustomizableList.setItemsName("");
                                nonCustomizableList.setItemsCost("");

                                //saving object only for first time
                                storenotcustomizable.add(nonCustomizableList);

                                //setting dish left
                                holder.m_dish_left.setText(dishdata.get(position).getAvailableQty() - holder.count + " left");

                                //setting total price of selected dish on button
                                DashBoard.totalprice = DashBoard.totalprice + dishdata.get(position).getPrice();
                                ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

                            }
                        }

                        multiadd = false;

                    }

                }
            }

        });

        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int decision = 0;

                if (dishdata.get(position).getCustomizable() == 1) {

                    int check = dishdata.get(position).getDishId();

                    for (int k = 0; k < storenotcustomizable.size(); k++) {
                        if (check == storenotcustomizable.get(k).getDishId()) {
                            decision++;
                        }
                    }

                    if (decision > 1) {
                        displayAlert(context, "You have added different variations of this item. Please visit the cart to remove them separately.");
                       // Toast.makeText(context, "you can only delete from cart when multiple coustum item is selected", Toast.LENGTH_LONG).show();
                    } else {
                        holder.count--;
                        DashBoard.TotalItem--;
                        holder.add.setVisibility(View.VISIBLE);
                        if (holder.count > 0) {
                            holder.sub.setVisibility(View.VISIBLE);

                            for (int i = 0; i < storenotcustomizable.size(); i++) {
                                Log.i("Saavor", "dish id: " + storenotcustomizable.get(i).getDishId() + "dish id2 " + dishdata.get(position).getDishId());

                                int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                                int iD2 = Integer.valueOf(dishdata.get(position).getDishId());

                                if (iD1 == iD2) {

                                    int quantitycheck = storenotcustomizable.get(i).getQuantity();

                                    if (quantitycheck > 1) {

                                        storenotcustomizable.get(i).setQuantity(storenotcustomizable.get(i).getQuantity() - 1);
                                        storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - storenotcustomizable.get(i).getQuantity());
                                        DashBoard.totalprice = DashBoard.totalprice - storenotcustomizable.get(i).getUpdatedprice();
                                        ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);
                                        holder.counting.setText("" + holder.count);
                                        holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");

                                        break;
                                    } else {
                                        DashBoard.totalprice = DashBoard.totalprice - storenotcustomizable.get(i).getUpdatedprice();
                                        ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

                                        holder.count = 0;

                                        //setting item left after quantity
                                        storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity());
                                        holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");


                                        storenotcustomizable.remove(i);
                                        holder.sub.setVisibility(View.GONE);
                                        holder.counting.setText("Add");

                                        break;
                                    }
                                }
                            }


                        } else {
                            holder.sub.setVisibility(View.GONE);
                            holder.counting.setText("Add");
                            holder.add.setVisibility(View.VISIBLE);
                            for (int i = 0; i < storenotcustomizable.size(); i++) {
                                int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                                int iD2 = Integer.valueOf(dishdata.get(position).getDishId());

                                if (iD1 == iD2) {

                                    DashBoard.totalprice = DashBoard.totalprice - storenotcustomizable.get(i).getUpdatedprice();
                                    ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

                                    //setting item left after quantity
                                    storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity());
                                    holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");


                                    storenotcustomizable.remove(i);
                                    //saving database
                                }
                            }
                        }


                    }
                } else {

                    holder.count--;
                    DashBoard.TotalItem--;
                    holder.add.setVisibility(View.VISIBLE);
                    if (holder.count > 0) {
                        holder.sub.setVisibility(View.VISIBLE);

                        for (int i = 0; i < storenotcustomizable.size(); i++) {
                            Log.i("Saavor", "dish id: " + storenotcustomizable.get(i).getDishId() + "dish id2 " + dishdata.get(position).getDishId());

                            int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                            int iD2 = Integer.valueOf(dishdata.get(position).getDishId());
                            if (iD1 == iD2) {
                                //setting order quantity
                                storenotcustomizable.get(i).setQuantity(holder.count);
                                holder.counting.setText("" + holder.count);

                                //setting item left after quantity
                                storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity() - storenotcustomizable.get(i).getQuantity());
                                holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");

                                //setting total price of selected dish on button
                                DashBoard.totalprice = DashBoard.totalprice - dishdata.get(position).getPrice();
                                ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);
                            }
                        }


                    } else {
                        holder.sub.setVisibility(View.GONE);
                        holder.counting.setText("Add");

                        for (int i = 0; i < storenotcustomizable.size(); i++) {
                            int iD1 = Integer.valueOf(storenotcustomizable.get(i).getDishId());
                            int iD2 = Integer.valueOf(dishdata.get(position).getDishId());

                            if (iD1 == iD2) {

                                //setting item left after quantity
                                storenotcustomizable.get(i).setItemleft(storenotcustomizable.get(i).getAvilquantity());
                                holder.m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");

                                //removing from database
                                storenotcustomizable.remove(i);

                                //setting total price of selected dish on button
                                DashBoard.totalprice = DashBoard.totalprice - dishdata.get(position).getPrice();
                                ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

                            }
                        }
                    }
                }


            }
        });

        holder.LL_dishdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(">>>>>" + dishdata.get(position).getDishId());
                Intent intent = new Intent(context, DishDetails.class);
                intent.putExtra("dishid", dishdata.get(position).getDishId());
                intent.putExtra("customize", dishdata.get(position).getCustomizable());
                context.startActivity(intent);

                System.out.println("***********" + position);
                System.out.println("###########" + holder);
                System.out.println("@@@@@@@@@@@" + holder.getAdapterPosition());


            }
        });
    }

    @Override
    public int getItemCount() {
        return dishdata.size();
    }

    public void receivedata(String substring) {

        result = Double.parseDouble(substring);
        System.out.println(result);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView m_dish_name, m_dish_left, m_dish_coustmizable, counting, m_dish_calorie, m_dish_cost, m_dish_type;
        LinearLayout LL_dishdetails, LL_soldout;
        Button add, sub;
        int count = 0;

        public MyViewHolder(View view) {
            super(view);

            LL_soldout= (LinearLayout) view.findViewById(R.id.ll_soldout);
            LL_dishdetails = (LinearLayout) view.findViewById(R.id.ll_dishdetail);
            m_dish_name = (TextView) view.findViewById(R.id.txt_dish_name);
            m_dish_cost = (TextView) view.findViewById(R.id.dish_cost);
            m_dish_calorie = (TextView) view.findViewById(R.id.dish_calorie);
            m_dish_type = (TextView) view.findViewById(R.id.dish_type);
            m_dish_left = (TextView) view.findViewById(R.id.dish_left);
            counting = (TextView) view.findViewById(R.id.txt_count);
            m_dish_coustmizable = (TextView) view.findViewById(R.id.txt_custom_avail);

            add = (Button) view.findViewById(R.id.but_add_dish);
            sub = (Button) view.findViewById(R.id.but_sub_dish);
        }
    }

    public static void setdata(double totalprice, MyViewHolder holder, int position, int totalqantity, int dishid)
    {
        holder.count++;

        //setting total price of selected dish on button
        DashBoard.totalprice = DashBoard.totalprice + totalprice;
        ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);

        //setting order quantity
        holder.counting.setText("" + holder.count);

        //setting item left after quantity
        int cal = 0;
        for (int i = 0; i < storenotcustomizable.size(); i++) {
            if (storenotcustomizable.get(i).getDishId() == dishid) {
                cal = cal + storenotcustomizable.get(i).getQuantity();
            }
        }
        int t = totalqantity - cal;
        if (t == 0) {
            holder.add.setVisibility(View.GONE);
        }

        holder.m_dish_left.setText(totalqantity - cal + " left");
        holder.sub.setVisibility(View.VISIBLE);
    }


    public void commit(ArrayList<NonCustomizableList> storenotcustomizable) {
        String mnoncustomize = gson.toJson(storenotcustomizable);
        mTabel.putString("noncustom", mnoncustomize);
        mTabel.commit();
    }

    public static void updatelist() {

        for (int i = 0; i < storenotcustomizable.size(); i++) {

            if (storenotcustomizable.get(i).getIsCustomizable() == 1) {
                int dishid = Integer.valueOf(storenotcustomizable.get(i).getDishId());

                int order_quantity = 0;

                for (int f = 0; f < storenotcustomizable.size(); f++)
                {
                    if (dishid == storenotcustomizable.get(f).getDishId()) {
                        order_quantity = order_quantity + storenotcustomizable.get(f).getQuantity();
                    }
                }

                storenotcustomizable.get(i).getHolder().m_dish_left.setText(storenotcustomizable.get(i).getAvilquantity() - order_quantity + " left");
                storenotcustomizable.get(i).getHolder().count = order_quantity;
                storenotcustomizable.get(i).getHolder().counting.setText("" + order_quantity);

                if(order_quantity < storenotcustomizable.get(i).getAvilquantity())
                {
                    storenotcustomizable.get(i).getHolder().add.setVisibility(View.VISIBLE);
                }
                else
                {
                    storenotcustomizable.get(i).getHolder().add.setVisibility(View.GONE);
                }

            } else
                {
                storenotcustomizable.get(i).getHolder().m_dish_left.setText(storenotcustomizable.get(i).getItemleft() + " left");
                storenotcustomizable.get(i).getHolder().count = storenotcustomizable.get(i).getQuantity();
                storenotcustomizable.get(i).getHolder().counting.setText("" + storenotcustomizable.get(i).getQuantity());

                if(storenotcustomizable.get(i).getHolder().count  < storenotcustomizable.get(i).getAvilquantity())
                {
                    storenotcustomizable.get(i).getHolder().add.setVisibility(View.VISIBLE);
                }
                else
                {
                    storenotcustomizable.get(i).getHolder().add.setVisibility(View.GONE);
                }
            }
        }


        int customcount=0;

        for (int j = 0; j < deletelist.size(); j++) {

            if (deletelist.get(j).getIsCustomizable() == 1) {
                for (int k = 0; k < storenotcustomizable.size(); k++) {
                    int iD1 = Integer.valueOf(storenotcustomizable.get(k).getDishId());
                    int iD2 = Integer.valueOf(deletelist.get(j).getDishId());

                    if (iD1 == iD2) {
                        customcount++;
                    }
                }
            } else if (customcount > 0) {
                //changing total count to add initial

                try {
                    deletelist.get(j).getHolder().counting.setText("" + customcount);
                }
                catch(Exception e)
                    {
                        Intent intent = new Intent(context, OrderHistory.class);
                        context.startActivity(intent);
                    }

            } else if (customcount == 0) {

                try {

                    //changing total count to add initial
                    deletelist.get(j).getHolder().counting.setText("ADD");

                    //setting counter 0
                    deletelist.get(j).getHolder().count = 0;

                    //making invisible to sub sign if no more item left to subtract
                    deletelist.get(j).getHolder().sub.setVisibility(View.GONE);

                    //make visible add button
                    deletelist.get(j).getHolder().add.setVisibility(View.VISIBLE);

                    //setting quantity left
                    deletelist.get(j).getHolder().m_dish_left.setText(deletelist.get(j).getItemleft() + " left");
                }
                catch(Exception e)
                {
//                    Intent intent = new Intent(chartcontext, OrderHistory.class);
//                    chartcontext.startActivity(intent);
                }

            }
        }

        try{
            ((Kitchen) context).senddata(DashBoard.TotalItem, DashBoard.totalprice);
        }catch (Exception e){
            e.printStackTrace();
        }

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