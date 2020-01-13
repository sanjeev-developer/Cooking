package com.saavor.user.firebaseservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.saavor.user.MyApplication;
import com.saavor.user.R;
import com.saavor.user.activity.BaseActivity;
import com.saavor.user.activity.Chart;
import com.saavor.user.activity.DashBoard;
import com.saavor.user.activity.Notifications;
import com.saavor.user.activity.OrderHistory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.saavor.user.chefserver.BookingHistoryActivity;

import static com.google.android.gms.internal.zzs.TAG;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcaster;
    private boolean IsBookingHistory;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String firerealtime = "" + remoteMessage.getNotification().getBody();
        String check = "Accepted";
        String rejected = "Rejected";
        String delivered = "Delivered";
        String ulyx_delivered = "is Delivered";


        if (firerealtime.toLowerCase().indexOf(check.toLowerCase()) != -1 || firerealtime.toLowerCase().indexOf(rejected.toLowerCase()) != -1 || firerealtime.toLowerCase().indexOf(delivered.toLowerCase()) != -1) {

            System.out.println("found the keyword");
            try {
                // getting the static instance of activity
                OrderHistory.firerealtime();
//
//                if(firerealtime.toLowerCase().indexOf(ulyx_delivered.toLowerCase()) != -1 )
//                {
//                    BaseActivity.show_ulyx_rating(getApplicationContext());
//                }

            } catch (Exception e) {
                System.out.println("error : " + e);
            }


        } else {

            if ((remoteMessage.getNotification().getBody().contains("Booking ID"))) {
                IsBookingHistory = true;
            }
            if (remoteMessage.getNotification().getBody().contains("Clock")) {
                MyApplication.showAlertDialog(getApplicationContext(), remoteMessage.getNotification().getBody());
            }
            System.out.println("not found");

        }


// TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
// Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }
// Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage);
        }

    }

    private void sendNotification(RemoteMessage remoteMessage) {

        int color = ContextCompat.getColor(getApplicationContext(), R.color.accent);

        Intent intent = null;
        String firerealtime = "" + remoteMessage.getNotification().getBody();
        String check = "welcome";
        String paYMENT = "payment";

        if (firerealtime.toLowerCase().indexOf(check.toLowerCase()) != -1) {
            System.out.println("found the keyword");

            intent = new Intent(this, DashBoard.class);
            intent.putExtra("onetime", 1);

        } else if (firerealtime.toLowerCase().indexOf(paYMENT.toLowerCase()) != -1) {
            intent = new Intent(this, Notifications.class);
        }else if(IsBookingHistory){
            intent = new Intent(this, BookingHistoryActivity.class);
        }
        else {

            System.out.println("not found");
            intent = new Intent(this, OrderHistory.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setColor(color)
                .setSmallIcon(R.drawable.not_icon)
                .setContentTitle("Saavor")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}