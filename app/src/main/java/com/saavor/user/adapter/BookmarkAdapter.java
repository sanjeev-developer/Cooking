package com.saavor.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saavor.user.Model.BookmarkList;
import com.saavor.user.Model.KitchenBookmarkList;
import com.saavor.user.R;
import com.saavor.user.activity.XYZKitchen;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {

    private ArrayList<KitchenBookmarkList> booklist ;
    private Context context;
    int count = 0;

    public BookmarkAdapter(Context contexts, ArrayList<KitchenBookmarkList> booklist) {
        this.booklist = booklist;
        this.context = contexts;

        System.out.println(">>>>" + booklist);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


        holder.bookmark_title.setText(booklist.get(position).getKitchenName().toString()+" (Kitchen)");
        holder.bookmark_address.setText(booklist.get(position).getAddress().toString());

        if (booklist.get(position).getIsOpen() == 0){
            holder.bookmark_status.setTextColor(context.getResources().getColor(R.color.tooltitle));
            holder.bookmark_status.setText("Closed");
        }else{
            holder.bookmark_status.setTextColor(context.getResources().getColor(R.color.green));
            holder.bookmark_status.setText("Now Open");
        }



        try {
            String internetUrl = "http://saavorapi.parkeee.net/" + booklist.get(position).getProfileImagePath();
            Glide.with(getApplicationContext()).load(internetUrl).error(R.drawable.usericonpd).dontAnimate().into(holder.bookimage);
            System.out.println(">>>>>>>>>");

        } catch (Exception e) {
            holder.bookimage.setImageResource(R.drawable.usericonpd);
        }

        holder.LL_booktouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, XYZKitchen.class);
                intent.putExtra("profileid", booklist.get(position).getProfileId());
                intent.putExtra("decision",1 );
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (booklist == null) ? 0 : booklist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bookmark_title, bookmark_address, bookmark_status;
        ImageView bookimage;
        LinearLayout LL_booktouch;

        public MyViewHolder(View view) {
            super(view);

            bookmark_title = (TextView) view.findViewById(R.id.txt_bookmark_title);
            bookmark_address = (TextView) view.findViewById(R.id.txt_bookmark_address);
            bookmark_status = (TextView) view.findViewById(R.id.txt_bookmark_status);
            bookimage = (ImageView) view.findViewById(R.id.img_bookimage);
            LL_booktouch= (LinearLayout) view.findViewById(R.id.ll_booktouch);

        }
    }
}