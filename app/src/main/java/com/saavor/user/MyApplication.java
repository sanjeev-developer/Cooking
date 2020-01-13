package com.saavor.user;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.saavor.user.chefserver.BookingHistoryActivity;

public class MyApplication extends Application {

    /** Instance of the current application. */
    private static MyApplication instance;
    /**
     * Constructor.
     */
    public MyApplication() {
        instance = this;
    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public static Context getContext() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void showAlertDialog(final Context context,final String Message) {
        /** define onClickListener for dialog */
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                DialogInterface.OnClickListener listener
                        = new   DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            //((Activity) context).finishAffinity();
                            Intent intent =new Intent(context, BookingHistoryActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                /*android.os.Process.killProcess(android.os.Process.myPid());*/
                        }catch (NullPointerException exp){

                        }
                    }
                };

                /** create builder for dialog */
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage(Message)
                        .setTitle("Alert!")
                        .setPositiveButton("OK", listener);
                /** create dialog & set builder on it */
                Dialog dialog = builder.create();
                /** this required special permission but u can use aplication context */
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                /** show dialog */
                dialog.show();
            }
        });

    }
}
