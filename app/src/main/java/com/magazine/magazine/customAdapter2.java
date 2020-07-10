package com.magazine.magazine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class customAdapter2 extends RecyclerView.Adapter <customAdapter2.MyViewHolder>  {


    Context ctx;



    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts,parent,false);

        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int pos) {


        holder.tv.setText("hoi");


    }




    @Override
    public int getItemCount() {

        return 1;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder

    {

        TextView tv;


        public MyViewHolder( View itemView) {

            super(itemView);


            tv = itemView.findViewById(R.id.demoText);

        }

    }


}
