package com.magazine.magazine;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class syncCategories extends AsyncTask<String,String,String> {


    String host = "http://192.168.1.108/";

    String url = host+"api/home/";


    Context context;

    getCategories categories;


    public syncCategories(Context context,getCategories categories) {

      this.context = context;
      this.categories = categories;

    }

    @Override
    protected String doInBackground(String... strings) {


        StringBuilder builder = new StringBuilder();


        try {

            URL link = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) link.openConnection();

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
    protected void onPostExecute(String data) {


        categories.category(data);

    }
}
