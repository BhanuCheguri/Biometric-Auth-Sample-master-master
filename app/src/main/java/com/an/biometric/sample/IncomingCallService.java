package com.an.biometric.sample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static android.content.ContentValues.TAG;
import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class IncomingCallService extends Service implements View.OnTouchListener{

    private static View mView;
    private WindowManager.LayoutParams mParams;
    private static WindowManager mWindowManager;
    TextView text;
    ImageButton ib_close;
    @Override
    public void onCreate() {
        super.onCreate();

        //mView = new MyLoadView(this);
        final LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView =  mLayoutInflater.inflate(R.layout.popup_layout, null);
        text = (TextView) mView.findViewById(R.id.text);
        text.setText(InterceptCall.IncomingNumber);

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
        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mView, mParams);
    }

    /*public static void clearView(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        if(mView != null) {
            if(mView.isEnabled()){
                mWindowManager.removeView(mView);
                mView = null;
            }
        }
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).removeView(mView);
        mView = null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(mView != null) {
            if(mView.isEnabled()){
                mWindowManager.removeView(mView);
                mView = null;
            }
        }
        return false;
    }

    public class MyLoadView extends View {

        private Paint mPaint;

        public MyLoadView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setTextSize(50);
            mPaint.setARGB(200, 200, 200, 200);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mPaint.setColor(Color.RED);
            int x = getWidth()/2;
            int y = getWidth()/2;
            int RADIUS = y;
            Rect rectangle = new Rect((int) (x - ((0.8) * RADIUS)), (int) (y - ((0.6) * RADIUS)), (int) (x + ((0.8) * RADIUS)), (int) (y + ((0.6 * RADIUS))));
            canvas.drawRect(rectangle, mPaint);
            canvas.drawText(InterceptCall.IncomingNumber, 0, 100, mPaint);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
