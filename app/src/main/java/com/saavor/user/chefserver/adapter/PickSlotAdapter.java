package com.saavor.user.chefserver.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saavor.user.Model.Slots;
import com.saavor.user.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.chefserver.AvailabilityandBookActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PickSlotAdapter extends RecyclerView.Adapter<PickSlotAdapter.MyViewHolder> {
    Activity context;
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    ArrayList<String> aLSots;
    ArrayList<Slots> arrayListSlots;
    int Count;
    String Date;
    ArrayList<String> aLDates = new ArrayList<String>();
    ArrayList<Slots> aLAddedSlots = new ArrayList<Slots>();
    ArrayList<Boolean> aLBoolean;
    SharedPreferences.Editor editor1;
    ArrayList<String> arrayListBookedSlots;
    int count = 0;
    int position_a = 0;
    int position_b = 0;
    boolean swap = true;
    int locate = 0;
    boolean firsttime = false;

    boolean SaveInstance;


    public PickSlotAdapter(Activity context, ArrayList<String> aLSots, ArrayList<Slots> arrayListSlots, String Date, ArrayList<Boolean> aLBoolean, ArrayList<String> arrayListBookedSlots, boolean SaveInstance) {
        this.context = context;
        this.aLSots = aLSots;
        Count = aLBoolean.size();
        this.arrayListSlots = arrayListSlots;
        this.Date = Date;
        this.aLBoolean = aLBoolean;
        this.arrayListBookedSlots = arrayListBookedSlots;
        this.SaveInstance = SaveInstance;

        editor1 = context.getSharedPreferences("SLOTS_LIST", MODE_PRIVATE).edit();

        // Get stored slots arrayList
        SharedPreferences shared = context.getSharedPreferences("SLOTS_LIST", MODE_PRIVATE);
        Type listOfBecons = new TypeToken<List<Slots>>() {
        }.getType();
        ArrayList<Slots> mSavedBeaconList = new Gson().fromJson(shared.getString("LIST", ""), listOfBecons);
        if (mSavedBeaconList != null) {

            for (int m = 0; m < mSavedBeaconList.size(); m++) {
                Slots obj = new Slots();
                obj.setStartTime(mSavedBeaconList.get(m).getStartTime());
                obj.setEndTime(mSavedBeaconList.get(m).getEndTime());
                aLAddedSlots.add(obj);
            }

            Type listOfBeconss = new TypeToken<List<Slots>>() {
            }.getType();
            String strBecons = new Gson().toJson(aLAddedSlots, listOfBeconss);
            editor1.putString("LIST", strBecons).apply();
            editor1.commit();
        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_slot_listitems, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtTimings.setText(aLSots.get(position).toString());
        holder.ll_parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveInstance = false;
                locate = position;
                boolean IsMatched = false;
                firsttime = true;
                for (int m = 0; m < arrayListBookedSlots.size(); m++) {
                    if (arrayListBookedSlots.get(m).toString().equalsIgnoreCase(aLSots.get(position).toString())) {
                        IsMatched = true;
                        break;
                    }
                }
                if (!IsMatched) {
                    AvailabilityandBookActivity.llBookForFullDay.setTag("0");
                    AvailabilityandBookActivity.imgBookForDay.setImageResource(R.drawable.dselected);

                    if (aLBoolean.get(position) == false) {
                        aLBoolean.set(position, true);
                    } else {
                        aLBoolean.set(position, false);
                    }
                    notifyDataSetChanged();
                }
            }
        });


        if (!SaveInstance) {
            count = 0;

            for (int i = 0; i < aLBoolean.size(); i++) {
                if (aLBoolean.get(i) == true) {
                    count++;
                }
            }

            if (count >= 2) {
                for (int y = 0; y < aLBoolean.size(); y++) {
                    if (aLBoolean.get(y) == true) {
                        position_a = y;
                        break;
                    }
                }

                if (count > 2) {
                    position_b = locate;
                } else {
                    for (int z = aLBoolean.size() - 1; z >= 0; z--) {
                        if (aLBoolean.get(z) == true) {
                            position_b = z;
                            break;
                        }
                    }
                }

                boolean decision = false;

                if (arrayListBookedSlots.isEmpty()) {
                    for (int v = 0; v < aLBoolean.size(); v++) {
                        aLBoolean.set(v, false);
                    }

                    for (int m = position_a; m <= position_b; m++) {
                        aLBoolean.set(m, true);
                    }
                } else {

                    for (int u = 0; u < arrayListBookedSlots.size(); u++) {

                        //   aLSots.indexOf(arrayListBookedSlots.get(u));
                        if (aLSots.indexOf(arrayListBookedSlots.get(u)) < position_b && aLSots.indexOf(arrayListBookedSlots.get(u)) > position_a) {
                            decision = true;

                            if (swap) {
                                for (int j = 0; j < aLBoolean.size(); j++) {
                                    if (j == position_b) {
                                        aLBoolean.set(j, true);
                                    } else {

                                        aLBoolean.set(j, false);
                                    }
                                }
                                // aLBoolean.set(position_a, false);

                                swap = false;
                            } else {
                                for (int j = 0; j < aLBoolean.size(); j++) {
                                    if (j == position_a) {
                                        aLBoolean.set(j, true);
                                    } else {

                                        aLBoolean.set(j, false);
                                    }
                                }

                                // aLBoolean.set(position_b, false);
                                swap = true;
                            }

                            break;
                        }
                    }

                    if (!decision) {

                        for (int v = 0; v < aLBoolean.size(); v++) {
                            aLBoolean.set(v, false);
                        }
                        for (int m = position_a; m <= position_b; m++) {
                            aLBoolean.set(m, true);
                        }
                    }
                }
            }
        }


        if (position == aLBoolean.size() - 1) {
            if (firsttime) {
                update();
            }

        }

        if (aLBoolean.get(position) == true) {
            holder.ll_parentView.setBackgroundResource(R.drawable.slot_selected);
            holder.txtTimings.setTextColor(Color.parseColor("#ffffff"));


        } else {


            boolean IsMatched = false;
            for (int m = 0; m < arrayListBookedSlots.size(); m++) {
                if (arrayListBookedSlots.get(m).toString().equalsIgnoreCase(aLSots.get(position).toString())) {
                    IsMatched = true;
                    break;
                }

            }
            if (IsMatched) {
                holder.ll_parentView.setBackgroundResource(R.drawable.slot_grey_curve);
                holder.txtTimings.setTextColor(Color.parseColor("#d7d7d7"));
            } else {
                holder.ll_parentView.setBackgroundResource(R.drawable.slot_grey_curve);
                holder.txtTimings.setTextColor(Color.parseColor("#b9000000"));
            }

        }

    }

    @Override
    public int getItemCount() {
        return Count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTimings;
        LinearLayout ll_parentView;

        public MyViewHolder(View view) {
            super(view);

            txtTimings = (TextView) view.findViewById(R.id.txtTimings);
            ll_parentView = (LinearLayout) view.findViewById(R.id.ll_parentView);
        }
    }


    public void update() {

        Type listOfBecons = new TypeToken<List<Slots>>() {
        }.getType();
        aLAddedSlots.clear();
        for (int w = 0; w < aLBoolean.size(); w++) {
            if (aLBoolean.get(w) == true) {
                aLAddedSlots.add(arrayListSlots.get(w));

            } else {

                for (int j = 0; j < aLAddedSlots.size(); j++) {
                    Slots obj = aLAddedSlots.get(j);
                    if (obj.getStartTime().equals(arrayListSlots.get(w).getStartTime())) {
                        aLAddedSlots.remove(j);
                        break;
                    }
                }
            }
        }

        String strBecons = new Gson().toJson(aLAddedSlots, listOfBecons);
        editor1.putString("LIST", strBecons).apply();
        editor1.apply();
    }

    public void bookall(boolean b) {
        SaveInstance = true;
        Type listOfBecons = new TypeToken<List<Slots>>() {
        }.getType();
        if (b) {
            for (int c = 0; c < aLBoolean.size(); c++) {
                aLBoolean.set(c, true);
                aLAddedSlots.add(arrayListSlots.get(c));
            }
        } else {

            for (int c = 0; c < aLBoolean.size(); c++) {
                aLBoolean.set(c, false);
                for (int j = 0; j < aLAddedSlots.size(); j++) {
                    Slots obj = aLAddedSlots.get(j);
                    if (obj.getStartTime().equals(arrayListSlots.get(c).getStartTime())) {
                        aLAddedSlots.remove(j);
                        break;
                    }
                }
            }
        }
        String strBecons = new Gson().toJson(aLAddedSlots, listOfBecons);
        editor1.putString("LIST", strBecons).apply();
        editor1.apply();
        notifyDataSetChanged();
    }

    public void checkvalidate()
    {
        boolean next = false;
        for(int x=0;x< aLBoolean.size(); x++)
        {
            if(aLBoolean.get(x) == true)
            {
                next=true;
                break;
            }
            else
            {
                next=false;
            }

        }

        if(next)
        {
            ((AvailabilityandBookActivity) context).nextbutton(true);
        }
        else
        {
            ((AvailabilityandBookActivity) context).nextbutton(false);
        }

    }
}



