package com.saavor.user.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.saavor.user.Model.DishList;
import com.saavor.user.R;
import com.saavor.user.activity.DashBoard;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<DishList>> _listDataChild;
    private List<DishList> childdata = new ArrayList<>();
    private DishList setdata = new DishList();
    int t=0;
    double result =0;
    private int position=0;
    private double updatedprice=0;
    int count = 0;
    Button add;
    Button sub;
    TextView counting;



    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<DishList>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        setdata = (DishList) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.kitchenlayout, null);
        }

        TextView dishname = (TextView) convertView.findViewById(R.id.txt_dish_name);
        TextView calorie = (TextView) convertView.findViewById(R.id.dish_calorie);
        TextView cost = (TextView) convertView.findViewById(R.id.dish_cost);
        TextView type = (TextView) convertView.findViewById(R.id.dish_type);
         counting = (TextView) convertView.findViewById(R.id.txt_count);

       // TextView available = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView left = (TextView) convertView.findViewById(R.id.dish_left);
         add = (Button) convertView.findViewById(R.id.but_add_dish);
       sub = (Button) convertView.findViewById(R.id.but_sub_dish);


        dishname.setText(setdata.getDishName());
        calorie.setText(setdata.getCalories());
        cost.setText("$ "+setdata.getPrice());
        type.setText(setdata.getCategory().toString());
        left.setText(setdata.getAvailableQty().toString()+" left");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (setdata.getCustomizable() == 1) {

//                    System.out.println(">>>>>"+ dishdata.get(position).getDishId());
//                    Intent intent = new Intent(context, Dishcoustmize.class);
//                    intent.putExtra("dishid", dishdata.get(position).getDishId());
//                    intent.putExtra("dishprice",dishdata.get(position).getPrice());
//                    intent.putExtra("position",position);
//                    context.startActivity(intent);

                }
                else
                {
                    count++;
                    DashBoard.TotalItem++;

                    if (count > 0) {
                        sub.setVisibility(View.VISIBLE);
                        counting.setText("" +count);

                        System.out.println("+++++"+childPosition);
                        System.out.println("@@@@@@"+groupPosition);

//                        Kitchen.totalprice= Kitchen.totalprice+dishdata.get(position).getPrice();
//                        ((Kitchen)context).senddata(Kitchen.TotalItem, Kitchen.totalprice);

                    } else {
                        sub.setVisibility(View.GONE);
                        counting.setText("Add");
                        System.out.println("+++++"+childPosition);
                        System.out.println("@@@@@@"+groupPosition);
                    }
                }
            }

        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count--;
                DashBoard.TotalItem--;

                if (count > 0) {
                    sub.setVisibility(View.VISIBLE);
                    counting.setText("" +count);
                    System.out.println("+++++"+childPosition);
                    System.out.println("@@@@@@"+groupPosition);
//                    Kitchen.totalprice= Kitchen.totalprice-dishdata.get(position).getPrice();
//                    ((Kitchen)context).senddata(Kitchen.TotalItem, Kitchen.totalprice);

                } else {
                    sub.setVisibility(View.GONE);
                    counting.setText("Add");
                    System.out.println("+++++"+childPosition);
                    System.out.println("@@@@@@"+groupPosition);
//                    Kitchen.totalprice= Kitchen.totalprice-dishdata.get(position).getPrice();
//                    ((Kitchen)context).senddata(Kitchen.TotalItem, Kitchen.totalprice);
                }

            }
        });

//        holder.LL_dishdetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                System.out.println(">>>>>"+ dishdata.get(position).getDishId());
////                Intent intent = new Intent(context, DishDetails.class);
////                intent.putExtra("dishid", dishdata.get(position).getDishId());
////                intent.putExtra("customize",dishdata.get(position).getCustomizable());
////                context.startActivity(intent);
//
//                System.out.println("***********"+position);
//                System.out.println("###########"+holder);
//                System.out.println("@@@@@@@@@@@"+holder.getAdapterPosition());
//
//
//            }
//        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.txt_parent_title);
        lblListHeader.setText(_listDataHeader.get(groupPosition).toString());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static void setdata(int i,double totalprice) {
//        count++;
//        Kitchen.TotalItem++;
//        Kitchen.totalprice= Kitchen.totalprice+totalprice;
//        ((Kitchen)context).senddata(Kitchen.TotalItem, Kitchen.totalprice);
//        holderr.counting.setText("" + holderr.count);

    }


    public void receivedata(String substring) {

        result = Double.parseDouble(substring);
        System.out.println(result);
    }
}