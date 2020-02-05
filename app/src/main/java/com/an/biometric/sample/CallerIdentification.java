package com.an.biometric.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CallerIdentification extends AppCompatActivity {

    private static CallerIdentification mainActivityRunningInstance;
    public static CallerIdentification  getInstace(){
        return mainActivityRunningInstance;
    }
    int SYSTEM_ALERT_WINDOW_PERMISSION = 0;
    boolean askedForOverlayPermission = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivityRunningInstance =this;

        setContentView(R.layout.activity_caller_identification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        }

        if (ContextCompat.checkSelfPermission(CallerIdentification.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(CallerIdentification.this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED
         || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallerIdentification.this,
                    new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.READ_PHONE_STATE},
                    1);
        }
    }

    @TargetApi(23)
    public void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    private void showDialog(String number) {
        final Dialog dialog = new Dialog(CallerIdentification.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_layout);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(number);

        ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    } } else {
                        Toast.makeText(getApplicationContext(), "No Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                   return;
        }
    }

    @TargetApi(23)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SYSTEM_ALERT_WINDOW_PERMISSION) {
            askedForOverlayPermission = false;
            if (Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                //Toast.makeText(MyProtector.getContext(), "ACTION_MANAGE_OVERLAY_PERMISSION Permission Granted", Toast.LENGTH_SHORT).show();
               /* InterceptCall myReceiver= new InterceptCall();
                IntentFilter filter = new IntentFilter();
                filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);

                registerReceiver(myReceiver, filter);*/
            } else {
                Toast.makeText(CallerIdentification.this, "ACTION_MANAGE_OVERLAY_PERMISSION Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
