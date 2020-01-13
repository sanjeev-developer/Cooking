package com.saavor.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saavor.user.R;

import java.util.ArrayList;

public class HowAdapter extends RecyclerView.Adapter<HowAdapter.MyViewHolder> {

    private ArrayList<String> listItem;
    private Context context;
    String[]data;
    int count = 0 ;

    public HowAdapter(Context context, String[] list) {
        this.data = list;
        this.context=context;

    }

    @Override
    public HowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_cardview, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        count++;
        holder.textView.setText(String.valueOf(count));
        holder.txt_list.setText(data[position]);
        if(position==3){
            final SpannableString ss = new SpannableString(Html.fromHtml("For more details about Saavor's Kitchen app visit our website www.saavor.io"));
            ClickableSpan span1 = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Selection.setSelection(ss, 0);
                }
            };
            ss.setSpan(span1, 61, 75, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.txt_list.setText(ss);
            holder.txt_list.setMovementMethod(LinkMovementMethod.getInstance());
        }else if(position==4){
            final SpannableString ss = new SpannableString(Html.fromHtml("To read our Terms and Conditions click here"));
            ClickableSpan span1 = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Selection.setSelection(ss, 0);
                }
            };
            ss.setSpan(span1, 33, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.txt_list.setText(ss);
            holder.txt_list.setMovementMethod(LinkMovementMethod.getInstance());
        }


    }


    @Override
    public int getItemCount() {
        return  5;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView,txt_list;

        public MyViewHolder(View view) {
            super(view);

            textView=(TextView)view.findViewById(R.id.txt_count);
            txt_list=(TextView)view.findViewById(R.id.txt_list);

        }


    }
}
