package com.magazine.magazine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class alert_updates extends AsyncTask<String,String,String> {

    String host = "http://192.168.1.108/";

    String url = host+"api/home/alerts";

    String storage = host+"storage/";

    private static final String TAG = "YourNotification";

    private static final int NOTIFICATION_ID = 101;

    DatabaseSystem db;

    ArrayList<String> alert_title = new ArrayList<>();
    ArrayList<String> descsarray = new ArrayList<>();
    ArrayList<String> contentsarry = new ArrayList<>();
    ArrayList<String> imgsarray = new ArrayList<>();
    ArrayList<String> postAtarray = new ArrayList<>();
    ArrayList<String> idArray = new ArrayList<>();
    ArrayList<String> total = new ArrayList<>();


    Context context;

    public alert_updates(Context context) {

        this.context = context;

        db = new DatabaseSystem(context);
    }

    @Override
    protected String doInBackground(String... strings) {

        StringBuilder builder = new StringBuilder();

        try {

            URL API_LINK = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) API_LINK.openConnection();

            connection.setRequestMethod("GET");

            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            while (true) {

                String readLine = reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }

                builder.append(line);
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return builder.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        try {

            JSONArray array = new JSONArray(s);

            for (int i=0;i<s.length();i++)
            {


                JSONObject object = array.getJSONObject(i);
                String title = object.getString("post_titile");
                String desps = object.getString("description");
                String content = object.getString("post_content");
                String imgURL = object.getString("image");
                String postedAT = object.getString("posted_at");
                String id = object.getString("id");
                String tot = object.getString("counts");

                alert_title.add(title);
                descsarray.add(desps);
                contentsarry.add(content);
                imgsarray.add(storage+imgURL);
                postAtarray.add(postedAT);
                idArray.add(id);

//                SharedPreferences pref = getSharedPreferences("counts",MODE_PRIVATE);
//
//                String vals = pref.getString("countValue","NULL");

                SharedPreferences.Editor editor = context.getSharedPreferences("counts",Context.MODE_PRIVATE).edit();

                editor.putString("countValue",tot);
                editor.commit();

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Notification.Builder notification = new Notification.Builder(context)
                        .setContentTitle("Breaking News")
                        .setContentText(title)
                        .setSmallIcon(R.drawable.ic_share)
                        .setAutoCancel(true);

                notification.build().flags |= Notification.FLAG_AUTO_CANCEL;

                Uri sounds = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//                notification.setSound(sounds);
//                notification.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
//                notification.setLights(Color.RED, 3000, 3000);

                Intent resultIntent = new Intent(context, ReadArticles.class); //to open an activity on touch notification

                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                resultIntent.putExtra("title",alert_title.get(i));
                resultIntent.putExtra("descs",descsarray.get(i));
                resultIntent.putExtra("content",contentsarry.get(i));
                resultIntent.putExtra("image",imgsarray.get(i));
                resultIntent.putExtra("id",idArray.get(i));

                int requestID = (int) System.currentTimeMillis();

                PendingIntent resultPendingIntent = PendingIntent
                        .getActivity(context,requestID,resultIntent,0);

                notification.setContentIntent(resultPendingIntent);

                int idv = Integer.parseInt(idArray.get(i));

                //notificationManager.notify(String.valueOf(idv),i,notification.build());

                notificationManager.notify("mine",idv,notification.build());


            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
