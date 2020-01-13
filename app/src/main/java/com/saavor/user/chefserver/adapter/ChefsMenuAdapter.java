package com.saavor.user.chefserver.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.ChefDishList;
import com.saavor.user.R;
import com.saavor.user.chefserver.ChefsMenuActivity;
import com.saavor.user.chefserver.DishDeatailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 12-12-2017.
 */

public class ChefsMenuAdapter extends BaseExpandableListAdapter {

    Activity context;
    ArrayList<String>arMenuHeading=new ArrayList<String>();
    ArrayList<String>arMenus=new ArrayList<String>();

    private List<String> parentDataSource;

    private HashMap<String, List<ChefDishList>> childDataSource;

    private List<String>aLDishId=new ArrayList<String>();
    private List<String>aLDishName=new ArrayList<String>();

    SharedPreferences.Editor editor1;
    String ChefName="";


    public ChefsMenuAdapter(Activity context, List<String> childParent, HashMap<String, List<ChefDishList>> child,String ChefName){
        this.context=context;
        this.parentDataSource = childParent;
        this.childDataSource = child;
        editor1 =context.getSharedPreferences(
                "MenuDishId", MODE_PRIVATE).edit();
        this.ChefName=ChefName;

    }

    @Override
    public int getGroupCount() {
        return this.parentDataSource.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childDataSource.get(this.parentDataSource.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentDataSource.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishName();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.chef_menu_heading_list_items, null);
        }
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        TextView txtHeading;
        txtHeading=view.findViewById(R.id.txtHeading);
        String parentHeader = (String)getGroup(groupPosition);
        txtHeading.setText(parentHeader);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean b, View view, ViewGroup viewGroup) {

        TextView txtMenuItems;
        final LinearLayout ll_parentView;
        final Button btn_Add;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.chef_menu_list_items, null);
        }
        txtMenuItems=(TextView)view.findViewById(R.id.txtMenuItems);
        btn_Add=(Button)view.findViewById(R.id.btn_Add);
        ll_parentView=(LinearLayout)view.findViewById(R.id.ll_parentView);
        ll_parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChefsMenuActivity.IsItemClick=true;
                Intent intent =new Intent(context, DishDeatailActivity.class);
                intent.putExtra("Calories",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getCalories());
                intent.putExtra("Category",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getCategory());
                intent.putExtra("CuisineName",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getCuisineName());
                intent.putExtra("Description",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDescription());
                intent.putExtra("DishName",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishName());
                intent.putExtra("Ingredeients",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getIngredeients());
                intent.putExtra("PreparingTime",childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getPreparingTime());
                intent.putExtra("UserName",ChefName);
                context.startActivity(intent);
            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aLDishId.size()>0){
                    if(aLDishId.contains(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishId())){
                        aLDishId.remove(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishId());
                        aLDishName.remove(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishName());

                        btn_Add.setBackgroundResource(R.drawable.btn_add_transparent);
                        btn_Add.setTextColor(ContextCompat.getColor(context,R.color.green));
                        btn_Add.setText("ADD");

                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < aLDishId.size(); i++) {
                            str.append(aLDishId.get(i)).append("~");
                        }

                        editor1.putString("Id", str.toString());
                        Set<String> setDishName = new HashSet<String>();
                        setDishName.addAll(aLDishName);
                        editor1.putStringSet("DishName", setDishName);
                        Set<String> setDishId = new HashSet<String>();
                        setDishId.addAll(aLDishId);
                        editor1.putStringSet("DishId", setDishId);
                        editor1.commit();

                        if(aLDishId.size()<2){
                            ChefsMenuActivity.total_items.setText(String.valueOf(aLDishId.size())+" item");
                        }else{
                            ChefsMenuActivity.total_items.setText(String.valueOf(aLDishId.size())+" items");
                        }


                        if(aLDishId.size()<1){
                            ChefsMenuActivity.llViewCart.setVisibility(View.GONE);
                        }


                    }else{
                        aLDishId.add(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishId());
                        aLDishName.add(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishName());

                        btn_Add.setBackgroundResource(R.drawable.btn_remove_transparent);
                        btn_Add.setTextColor(ContextCompat.getColor(context,R.color.black));
                        btn_Add.setText("REMOVE");

                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < aLDishId.size(); i++) {
                            str.append(aLDishId.get(i)).append("~");
                        }

                        editor1.putString("Id", str.toString());
                        Set<String> setDishName = new HashSet<String>();
                        setDishName.addAll(aLDishName);
                        editor1.putStringSet("DishName", setDishName);
                        Set<String> setDishId = new HashSet<String>();
                        setDishId.addAll(aLDishId);
                        editor1.putStringSet("DishId", setDishId);
                        editor1.commit();

                        if(aLDishId.size()<2){
                            ChefsMenuActivity.total_items.setText(String.valueOf(aLDishId.size())+" item");
                        }else{
                            ChefsMenuActivity.total_items.setText(String.valueOf(aLDishId.size())+" items");
                        }
                    }
                }else{
                    aLDishId.add(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishId());
                    aLDishName.add(childDataSource.get(parentDataSource.get(groupPosition)).get(childPosition).getDishName());

                    btn_Add.setBackgroundResource(R.drawable.btn_remove_transparent);
                    btn_Add.setTextColor(ContextCompat.getColor(context,R.color.black));
                    btn_Add.setText("REMOVE");

                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < aLDishId.size(); i++) {
                        str.append(aLDishId.get(i)).append("~");
                    }

                    editor1.putString("Id", str.toString());
                    Set<String> setDishName = new HashSet<String>();
                    setDishName.addAll(aLDishName);
                    editor1.putStringSet("DishName", setDishName);
                    Set<String> setDishId = new HashSet<String>();
                    setDishId.addAll(aLDishId);
                    editor1.putStringSet("DishId", setDishId);
                    editor1.commit();

                    ChefsMenuActivity.llViewCart.setVisibility(View.VISIBLE);
                    ChefsMenuActivity.total_items.setText("1 item");
                }
            }
        });
        String childName = (String)getChild(groupPosition, childPosition);
        txtMenuItems.setText(childName);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
