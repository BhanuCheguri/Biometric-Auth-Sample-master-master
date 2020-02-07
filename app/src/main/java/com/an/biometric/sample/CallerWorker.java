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
       //showNotification("WorkManager", incomingCallNumber != null ? incomingCallNumber : incomingCallNumber);

        showNumberNotification(incomingCallNumber);
        //displayNotification();

      /*  RemoteViews contentView = new RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_layout);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, "Caller Identification");
        contentView.setTextViewText(R.id.text, incomingCallNumber);

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContent(contentView)
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());*/

        /*final LayoutInflater mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView =  mLayoutInflater.inflate(R.layout.popup_layout, null);
        text = (TextView) mView.findViewById(R.id.text);
        text.setText(incomingCallNumber);

        ib_close = (ImageButton) mView.findViewById(R.id.ib_close);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mWindowManager.removeViewImmediate(view);
                return false;
            }
        });
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mView != null) {
                    if(mView.isEnabled()){
                        mWindowManager.removeView(mView);
                        mView = null;
                    }
                }
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT);
        } else {
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT);
        }
        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager)mContext.getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mView, mParams);*/
        return null;
    }


    private void showNumberNotification(String incomingCallNumber) {
        try {
            String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

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
                        .setContentTitle("Caller Identification")
                        .setContentText(incomingCallNumber)
                        .setGroupSummary(true)
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        .setAutoCancel(true);

                //finally displaying the notification
                NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
        }
    }
    /*private void showNumberNotification() {
        try {

            *//*String channelId = "task_channel";
            String channelName = "task_name";

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "1")
                    .setSmallIcon(R.drawable.ic_fingerprint)
                    .setContentTitle("Hey this is Simplified Coding...")
                    .setContentText("Please share your name with us")
                    .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                    .setPriority(Notification.PRIORITY_MAX); //Important for heads-up notification

            Notification buildNotification = mBuilder.build();
            int notifyId = (int) System.currentTimeMillis(); //For each push the older one will not be replaced for this unique id

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
                NotificationChannel channel = new NotificationChannel(channelId,
                        channelName,
                        importance);
                channel.setDescription("Desc");
                channel.setShowBadge(true);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                    notificationManager.notify(notifyId, buildNotification);
                }
            } else {

                NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                if (mNotifyMgr != null) {
                    mNotifyMgr.notify(notifyId, buildNotification);
                }
            }*//*
        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            //channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hey this is Simplified Coding...")
                .setContentText("Please share your name with us")
                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                .setPriority(Notification.PRIORITY_MAX);//Important for heads-up notification
                //.setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager!=null)
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
      *//*  NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Hey this is Simplified Coding...")
                .setContentIntent(notificationPendingIntent)
                .setContentText("Please share your name with us", viewObject.getTitle())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(bigText)
                .setPriority(NotificationCompat.PRIORITY_HIGH);*//*
        }catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
        }
    }*/



    private void showNotification(String task, String desc) {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());

    }

    public void displayNotification() {

      /*  //We need this object for getting direct input from notification
        RemoteInput remoteInput = new RemoteInput.Builder(NOTIFICATION_REPLY)
                .setLabel("Please enter your name")
                .build();


        //For the remote input we need this action object
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_delete,
                        "Reply Now...", helpPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();*/

        //Creating the notifiction builder object
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Hey this is Simplified Coding...")
                .setContentText("Please share your name with us");
                /*.setAutoCancel(true)
                .setContentIntent(helpPendingIntent);*/
                /*.addAction(action)
                .addAction(android.R.drawable.ic_menu_compass, "More", morePendingIntent)
                .addAction(android.R.drawable.ic_menu_directions, "Help", helpPendingIntent);*/


        //finally displaying the notification
        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
