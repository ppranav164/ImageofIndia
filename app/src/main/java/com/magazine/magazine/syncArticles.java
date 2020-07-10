package com.magazine.magazine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class syncArticles extends AsyncTask<String,String,String> {


    ProgressDialog dialog;

    Domain_Configs domain_configs = new Domain_Configs();

    String host = domain_configs.hostname;


    String url = domain_configs.articleUrl;


    Context context;

    ArticlesInterface articlesInterface;


    public syncArticles(Context context, ArticlesInterface articlesInterface) {


      this.context = context;

      this.articlesInterface = articlesInterface;

        Toast.makeText(context,""+context.toString(),Toast.LENGTH_SHORT).show();


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


        articlesInterface.getArticles(data);


    }
}
