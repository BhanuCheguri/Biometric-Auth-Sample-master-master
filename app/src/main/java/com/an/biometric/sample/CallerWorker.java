package com.an.biometric.sample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.app.NotificationCompat.DEFAULT_SOUND;
import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;

public class CallerWorker extends Worker {

    public static final String TASK_DESC = "task_desc";
    private static View mView;
    private WindowManager.LayoutParams mParams;
    private static WindowManager mWindowManager;
    TextView text;
    ImageButton ib_close;
    Context mContext;

    public static final String NOTIFICATION_REPLY = "NotificationReply";
    public static final String CHANNNEL_ID = "SimplifiedCodingChannel";
    public static final String CHANNEL_NAME = "SimplifiedCodingChannel";
    public static final String CHANNEL_DESC = "This is a channel for Simplified Coding Notifications";

    public static final String KEY_INTENT_MORE = "keyintentmore";
    public static final String KEY_INTENT_HELP = "keyintenthelp";

    public static final int REQUEST_CODE_MORE = 100;
    public static final int REQUEST_CODE_HELP = 101;
    public static final int NOTIFICATION_ID = 200;

    public CallerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        String incomingCallNumber = getInputData().getString(TASK_DESC);

        showNumberNotification(incomingCallNumber);
        //displayNotification();

        return null;
    }


    private void showNumberNotification(String incomingCallNumber) {
        try {

            RemoteViews contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.customnotification_layout);
            contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            contentView.setTextViewText(R.id.title, "ProducerMAX");
            contentView.setTextViewText(R.id.text, incomingCallNumber);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager mNotificationManager =
                        (NotificationManager)getApplicationContext(). getSystemService(Context.NOTIFICATION_SERVICE);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNNEL_ID, CHANNEL_NAME, importance);
                mChannel.setDescription(CHANNEL_DESC);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);

                //Creating the notifiction builder object
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        //.setContentTitle("Caller Identification")
                        .setContentText(incomingCallNumber)
                        .setContent(contentView)
                        .setAutoCancel(true);

                //finally displaying the notification
                NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }else
            {
                NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new
                            NotificationChannel(CHANNNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNNEL_ID)
                        //.setContentTitle("Caller Identification")
                        //.setContentText(incomingCallNumber)
                        .setContentText(incomingCallNumber)
                        .setContent(contentView)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.mipmap.ic_launcher);
                manager.notify(1, builder.build());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
        }
    }
}
