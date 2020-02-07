package com.an.biometric.sample;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class InterceptCall extends BroadcastReceiver {
    public static String IncomingNumber;
   // private final Handler handler; // Handler used to execute code on the UI thread
    //Context mContext ;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //mContext = context;
        TelephonyManager teleMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener psl = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        IncomingNumber = incomingNumber;
                        Toast.makeText(context, "Telephone is now ringing :: " + incomingNumber , Toast.LENGTH_SHORT).show();

                        Log.i("CallReceiverBroadcast", "Incoming call caught. Caller's number is " + incomingNumber + ".");
                        //Intent i = new Intent(context, IncomingCallService.class);
                       // context.startService(i);
                        final Data data = new Data.Builder()
                                .putString(CallerWorker.TASK_DESC, InterceptCall.IncomingNumber)
                                .build();
                        Log.d("UI thread", "I am the UI thread");
                        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(CallerWorker.class, 10, TimeUnit.MILLISECONDS).setInputData(data).build();
                        //OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(CallerWorker.class).setInputData(data).build();
                        WorkManager.getInstance().enqueue(periodicWorkRequest);

                        /*handler.post(new Runnable() {
                            @Override
                            public void run() {
                                                        }
                        });*/

                        //CallWorkerTask asyncTask=new CallWorkerTask();
                        //asyncTask.execute(InterceptCall.IncomingNumber);

                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.i("CallReceiverBroadcast", "CALL_STATE_IDLE");
                        //IncomingCallService.clearView(context);
                        // Call Disconnected
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.i("CallReceiverBroadcast", "CALL_STATE_OFFHOOK");
                        //IncomingCallService.clearView(context);
                        // Call Answer Mode
                        break;
                }
            }
        };
        teleMgr.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
        teleMgr.listen(psl, PhoneStateListener.LISTEN_NONE);

    }

    /*private class CallWorkerTask extends AsyncTask<String, Void, Void> {
        ProgressDialog p;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                p = new ProgressDialog(mContext);
                p.setMessage("Please wait...");
                p.setIndeterminate(false);
                p.setCancelable(false);
                p.show();
            }
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    final Data data = new Data.Builder()
                            .putString(CallerWorker.TASK_DESC, InterceptCall.IncomingNumber)
                            .build();
                    Log.d("UI thread", "I am the UI thread");
                    OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(CallerWorker.class).setInputData(data).build();
                    WorkManager.getInstance().enqueue(workRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute() {
                p.dismiss();
            }

        }*/
}
