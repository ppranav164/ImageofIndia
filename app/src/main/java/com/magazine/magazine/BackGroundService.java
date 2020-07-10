package com.magazine.magazine;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class BackGroundService extends Service {


    public Context context = this;
    public Handler handler = null;
    public  static  Runnable runnable = null;


    @Override
    public void onCreate() {

//        Toast.makeText(this, "Service created!", Toast.LENGTH_SHORT).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

//                Toast.makeText(context, "Service is still running", Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable, 10000);
                new alert_updates(context).execute();
            }
        };

        handler.postDelayed(runnable, 15000);

    }

    @Override
    public void onStart(Intent intent, int startId) {

//        Toast.makeText(this, "Service started by user.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {

//        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
