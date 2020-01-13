package com.saavor.user.chefserver.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.saavor.user.Model.BookingList;
import com.saavor.user.Model.BookingStatusUpdate;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.chefserver.BookingDetails;
import com.saavor.user.chefserver.BookingHistoryActivity;
import com.saavor.user.processor.PostApiClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.MyViewHolder> implements OnResultReceived {

    ArrayList<BookingList> AlBookingList;
    Activity activity;
    String Confirm = "";
    String SessionToken = "";
    String UserId = "";
    String date_format = "";
    OnResultReceived mOnResultReceived;
    Gson oGson;
    String ServerResult;
    String datedata;
    ProgressDialog mProgressDialog;
    public Dialog load_dialog;

    public BookingHistoryAdapter(Activity context, ArrayList<BookingList> AlBookingList, String Confirm, String SessionToken, String UserId, String date_format,String datedata) {
        this.AlBookingList = AlBookingList;
        activity = context;
        this.Confirm = Confirm;
        this.SessionToken = SessionToken;
        this.UserId = UserId;
        this.date_format = date_format;
        mOnResultReceived = this;
        oGson = new Gson();
       // mProgressDialog = new ProgressDialog(context);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Please wait ...");
        this.datedata=datedata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtChefName.setText(AlBookingList.get(position).getChefName());
        holder.txt_add.setText(AlBookingList.get(position).getCustomerAddress());
        holder.txt_amount.setText("$" + AlBookingList.get(position).getTotalAmount());
        holder.txt_booking_id.setText(AlBookingList.get(position).getBookingID());
        holder.img_forward_oh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(activity, BookingDetails.class));
                intent.putExtra("BookingsId", AlBookingList.get(position).getBookingsId());
                activity.startActivity(intent);
            }
        });
        holder.btn_order_status_history.setVisibility(View.GONE);

        //holder.txtEventsName.setTextColor(ContextCompat.getColor(activity, R.color.black));
        //holder.txtEvents.setTextColor(ContextCompat.getColor(activity, R.color.black));
        holder.txtEvents.setText("Events: " + AlBookingList.get(position).getEventTitle());

        holder.ll_statustxt.setVisibility(View.VISIBLE);
        holder.txt_od_status.setText(AlBookingList.get(position).getBookingStatus());

        if(!AlBookingList.get(position).getBookingStatus().equalsIgnoreCase("Completed")){
            if (AlBookingList.get(position).getIsClockIn().equalsIgnoreCase("1") && AlBookingList.get(position).getIsClockOut().equalsIgnoreCase("0")&&AlBookingList.get(position).getIsClockInConfirm().equalsIgnoreCase("0")) {
                holder.txtChefStatus.setText("Confirm Clock In");
                holder.ll_chefStatus.setVisibility(View.VISIBLE);
                holder.btn_order_status_history.setVisibility(View.VISIBLE);
                holder.btn_order_status_history.setText("Confirm");
            } else if (AlBookingList.get(position).getIsClockIn().equalsIgnoreCase("1") && AlBookingList.get(position).getIsClockOut().equalsIgnoreCase("1")&&AlBookingList.get(position).getIsClockOutConfirm().equalsIgnoreCase("0")) {
                holder.txtChefStatus.setText("Confirm Clock Out");
                holder.ll_chefStatus.setVisibility(View.VISIBLE);
                holder.btn_order_status_history.setVisibility(View.VISIBLE);
                holder.btn_order_status_history.setText("Confirm");
            }
        }

        String textfile =""+AlBookingList.get(position).getSlotDates().toString();
        String month = textfile.split("-")[0];
        String date = textfile.split("-")[1];
        String year =textfile.split("-")[2];

        String mix=month+" "+date+", "+year;
        if(datedata.equals(mix))
        {
            holder.date_booking.setText("Today");
        }
        else
        {
            Date apidate=null;
            DateFormat datebeforeFormat = new SimpleDateFormat("MM-dd-yyyy");

            try {
                apidate = datebeforeFormat.parse(textfile);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat destDf = new SimpleDateFormat("MMM dd, yyyy");

            // format the date into another format
            String dateStr = destDf.format(apidate);

            System.out.println("Converted date is : " + dateStr);
//            date_title.setText(""+dateStr);

            holder.date_booking.setText(dateStr);
        }

        holder.btn_order_status_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookingStatusUpdate bookingStatusUpdate = new BookingStatusUpdate();
                bookingStatusUpdate.setBookingsId(AlBookingList.get(position).getBookingsId());
                bookingStatusUpdate.setCurrentDate(date_format);
                if (holder.txtChefStatus.getText().toString().equalsIgnoreCase("Confirm Clock In")) {
                    bookingStatusUpdate.setIsClockInConfirm("1");
                    bookingStatusUpdate.setIsClockOutConfirm("0");
                } else if (holder.txtChefStatus.getText().toString().equalsIgnoreCase("Confirm Clock Out")) {
                    bookingStatusUpdate.setIsClockInConfirm("0");
                    bookingStatusUpdate.setIsClockOutConfirm("1");
                    holder.txt_od_status.setText("Completed");
                }
                bookingStatusUpdate.setSessionToken(SessionToken);
                bookingStatusUpdate.setUserId(UserId);
              //  mProgressDialog.show();
                String jsonString = oGson.toJson(bookingStatusUpdate, BookingStatusUpdate.class).toString();
                PostApiClient oInsertUpdateApi = new PostApiClient(mOnResultReceived);
                oInsertUpdateApi.setRequestSource(RequestSource.BookingStatusUpdate);
                oInsertUpdateApi.executePostRequest(API.fBookingStatusUpdate(), jsonString);
                holder.ll_chefStatus.setVisibility(View.GONE);
                holder.btn_order_status_history.setVisibility(View.GONE);
               // ((BookingHistoryActivity)activity ).hitapi();
            }
        });
    }

//    private void showdialog(Context activity) {
//
//        //dialog intialization
//        load_dialog = new Dialog(activity);
//        load_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        load_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        load_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        load_dialog.setContentView(R.layout.loading_dialog);
//        load_dialog.show();
//    }

    @Override
    public int getItemCount() {
        return AlBookingList.size();
    }

    @Override
    public void dispatchString(RequestSource from, String what) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtChefName;
        TextView txt_add;
        TextView txt_booking_id;
        TextView txt_amount;
        TextView txtEvents;
        TextView txtEventsName , date_booking;
        TextView txt_od_status;
        LinearLayout img_forward_oh;
        LinearLayout ll_statustxt;
        LinearLayout ll_chefStatus;
        TextView txtChefStatus;
        Button btn_order_status_history;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtChefName = (TextView) itemView.findViewById(R.id.txt_oh_kitname);
            img_forward_oh = (LinearLayout) itemView.findViewById(R.id.img_forward_oh);
            txt_add = (TextView) itemView.findViewById(R.id.txt_kitadd);
            txt_booking_id = (TextView) itemView.findViewById(R.id.txt_oh_orderid);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_recent_dishprice);
            btn_order_status_history = (Button) itemView.findViewById(R.id.oh_reorder);
            txtEventsName = (TextView) itemView.findViewById(R.id.txt_oh_orderdate);
            txtEvents = (TextView) itemView.findViewById(R.id.txtEvents);
            ll_statustxt = (LinearLayout) itemView.findViewById(R.id.ll_statustxt);
            txt_od_status = (TextView) itemView.findViewById(R.id.txt_od_status);
            date_booking= (TextView) itemView.findViewById(R.id.date_booking);
            ll_chefStatus = (LinearLayout) itemView.findViewById(R.id.ll_chefStatus);
            txtChefStatus = (TextView) itemView.findViewById(R.id.txtChefStatus);
        }
    }
}
