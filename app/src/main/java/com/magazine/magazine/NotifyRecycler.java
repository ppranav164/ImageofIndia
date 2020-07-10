package com.magazine.magazine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class NotifyRecycler extends RecyclerView.Adapter <NotifyRecycler.MyViewHolders> {


    Domain_Configs configs;


    Context context;

    ArrayList<String> arrtitle = new ArrayList<>();
    ArrayList<String> arrcategory = new ArrayList<>();
    ArrayList<String> arrpostat = new ArrayList<>();
    ArrayList<String> arrdescps = new ArrayList<>();
    ArrayList<String> arrcontents = new ArrayList<>();
    ArrayList<String> arrthumbs = new ArrayList<>();
    ArrayList<String> arrpostId = new ArrayList<>();


    String mdata;


    public NotifyRecycler(String data , Context context) {

        configs = new Domain_Configs();

        this.context = context;

        this.mdata = data;


        try {

            JSONArray array = new JSONArray(data);

            for (int i=0; i<data.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                String title = object.getString("post_titile");
                String category = object.getString("category_id");
                String postAt = object.getString("posted_at");

                String content = object.getString("post_content");
                String descps = object.getString("description");
                String postIds = object.getString("id");
                String images = object.getString("image");

                arrtitle.add(title);
                arrcategory.add(category);
                arrpostat.add(postAt);

                arrpostId.add(postIds);
                arrdescps.add(descps);
                arrcontents.add(content);
                arrthumbs.add(configs.storagePath+images);

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public MyViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts,parent,false);

        MyViewHolders holders = new MyViewHolders(view);


        return holders;

    }



    @Override
    public void onBindViewHolder(final MyViewHolders holder, int position) {

       if (arrtitle.size() ==0){

           holder.errTv.setText("Error No Notifications");

       }else {


           holder.txview.setText(arrtitle.get(position));
           holder.catTv.setText(arrcategory.get(position));
           holder.dateTv.setText(arrpostat.get(position));
       }


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(context,ReadArticles.class);
               intent.putExtra("title",arrtitle.get(holder.getLayoutPosition()));
               intent.putExtra("descs",arrdescps.get(holder.getLayoutPosition()));
               intent.putExtra("content",arrcontents.get(holder.getLayoutPosition()));
               intent.putExtra("image",arrthumbs.get(holder.getLayoutPosition()));
               intent.putExtra("id",arrpostId.get(holder.getLayoutPosition()));
               context.startActivity(intent);
           }
       });

    }


    @Override
    public int getItemCount() {

      if (arrtitle.size() == 0)
      {
          return 1;
      }else {

          return arrtitle.size();
      }
    }

    public class MyViewHolders extends RecyclerView.ViewHolder {


        TextView txview,catTv,dateTv;

        TextView errTv;

        public MyViewHolders(View itemView) {

            super(itemView);

            txview =  itemView.findViewById(R.id.demoText);

            catTv = itemView.findViewById(R.id.categoryTV);

            dateTv = itemView.findViewById(R.id.dateTV);

            errTv = itemView.findViewById(R.id.errMessage);
        }
    }


}
