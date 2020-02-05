package com.an.biometric.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

public class MyAlertDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide activity title
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Test")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
