package com.an.biometric.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {
// TODO Auto-generated method stub
        try {
            if (intent != null && intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
//Toast.makeText(context, "Outgoign call", 1000).show();
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            } else {
//get the phone state
                String newPhoneState = intent.hasExtra(TelephonyManager.EXTRA_STATE) ? intent.getStringExtra(TelephonyManager.EXTRA_STATE) : null;
                Bundle bundle = intent.getExtras();

                if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//read the incoming call number
                    String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    Log.i("PHONE RECEIVER", "Telephone is now ringing " + phoneNumber);

                } else if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//Once the call ends, phone will become idle
                    Log.i("PHONE RECEIVER", "Telephone is now idle");
                } else if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
//Once you receive call, phone is busy
                    Log.i("PHONE RECEIVER", "Telephone is now busy");
                }

            }

        } catch (Exception ee) {
            Log.i("Telephony receiver", ee.getMessage());
        }
    }
}