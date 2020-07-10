package com.magazine.magazine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SyncNotify extends AsyncTask<String,String,String> {


    Domain_Configs configs = new Domain_Configs();

    Context context;

    String link = configs.getNotifData;

    getNotification getNotification;

    ProgressBar bar;


    public SyncNotify(Context context , getNotification notification) {

        this.context = context;

        this.getNotification = notification;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        getNotification.getAll(s);



    }

    @Override
    protected String doInBackground(String... strings) {

        StringBuilder builder = new StringBuilder();

        try {

            URL API = new URL(link);

            HttpURLConnection connection = (HttpURLConnection) API.openConnection();

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
}
