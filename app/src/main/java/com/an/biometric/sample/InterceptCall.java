package com.an.biometric.sample;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class InterceptCall extends BroadcastReceiver {
    public static String IncomingNumber;

    /*private ReceiverCallback callback;

    public InterceptCall(ReceiverCallback callback){

        this.callback = callback;        //<--Initialize this
    }*/

    @Override
    public void onReceive(final Context context, final Intent intent) {
        /*String newPhoneState = intent.hasExtra(TelephonyManager.EXTRA_STATE) ? intent.getStringExtra(TelephonyManager.EXTRA_STATE) : null;
        Bundle bundle = intent.getExtras();

        if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            //read the incoming call number
            String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context, "Telephone is now ringing", Toast.LENGTH_SHORT).show();

        } else if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            //Once the call ends, phone will become idle
            Toast.makeText(context, "Telephone is now idle", Toast.LENGTH_SHORT).show();

        } else if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            //Once you receive call, phone is busy
            Toast.makeText(context, "Telephone is now busy", Toast.LENGTH_SHORT).show();

        }*/

        TelephonyManager teleMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener psl = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        IncomingNumber = incomingNumber;
                        Toast.makeText(context, "Telephone is now ringing :: " + incomingNumber , Toast.LENGTH_SHORT).show();

                        Log.i("CallReceiverBroadcast", "Incoming call caught. Caller's number is " + incomingNumber + ".");
                        Intent i = new Intent(context, IncomingCallService.class);
                        context.startService(i);

                        /*if(CallerIdentification.getInstace()!=null)
                            CallerIdentification.getInstace().updateUI(incomingNumber);*/

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
}
