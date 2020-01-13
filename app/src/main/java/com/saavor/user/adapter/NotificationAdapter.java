package com.saavor.user.adapter;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.saavor.user.Model.NotificationList;
        import com.saavor.user.R;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.Locale;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private ArrayList<NotificationList> near_title;
    private Context context;
    int count = 0 ;

    public NotificationAdapter(Context contexts, ArrayList<NotificationList> near_title)
    {
        this.near_title = near_title;
        this.context=contexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationlayout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.title.setText(near_title.get(position).getTitle());
        holder.sub.setText(near_title.get(position).getMessage());


        String sdate = "" +near_title.get(position).getCreateDate();
        SimpleDateFormat spf = new SimpleDateFormat("MM-dd-yyyy hh:mm aaa", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = spf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("MMM dd, yyyy hh:mm aaa",Locale.ENGLISH);
        sdate = spf.format(newDate);
        System.out.println(sdate);

  holder.time.setText(sdate);

        String payment="Payment";
        String order="Order Status";
        String complaint="Complaint";
        String registration="Registration";
        String refund="Refund ";
        String match=near_title.get(position).getNotificationType();

        if(payment.equals(match) || refund.equals(match))
        {
            holder.notimage.setBackgroundResource(R.drawable.payment);
        }
        if(order.equals(match))
        {
            holder.notimage.setBackgroundResource(R.drawable.dispatched);
        }
        if(complaint.equals(match))
        {
            holder.notimage.setBackgroundResource(R.drawable.complaint);
        }
        if(registration.equals(match))
        {
            holder.notimage.setBackgroundResource(R.drawable.reg_icon);
        }

    }

    @Override
    public int getItemCount()
    {
        return near_title.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,sub,time;
        ImageView notimage;

        public MyViewHolder(View view) {
            super(view);

            title=(TextView)view.findViewById(R.id.noti_title);
            sub=(TextView)view.findViewById(R.id.noti_subtitle);
            time=(TextView)view.findViewById(R.id.noti_time);
            notimage=(ImageView) view.findViewById(R.id.not_image);
        }
    }
}