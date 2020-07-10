package com.magazine.magazine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class customAdapter  extends RecyclerView.Adapter <customAdapter.MyViewHolder>  {


    Context ctx;



    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> descripts = new ArrayList<>();
    ArrayList<String> thumbnails = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> postsContent = new ArrayList<>();
    ArrayList<String> postId = new ArrayList<>();


    Domain_Configs domain_configs = new Domain_Configs();

    String host = domain_configs.hostname;

    String storage = domain_configs.ImgURL;


    public  customAdapter(Context context,String ApiData)
    {

        this.ctx = context;



        try{

            JSONArray array = new JSONArray(ApiData);

            for (int i=0; i < ApiData.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);

                String name = object.getString("post_titile");

                String descs = object.getString("description");

                String ImageUrl = object.getString("image");

                String datePost = object.getString("posted_at");

                String postContent = object.getString("post_content");

                String pid = object.getString("id");

                titles.add(name);
                descripts.add(descs);
                thumbnails.add(storage+ImageUrl);
                dates.add(datePost);
                postsContent.add(postContent);
                postId.add(pid);

            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);

        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int pos) {



        holder.tv.setText(titles.get(pos));
        holder.descV.setText(descripts.get(pos));
        holder.dateV.setText(dates.get(pos));
        Picasso.get().load(thumbnails.get(pos)).transform(new RoundedCornersTransformation(20,40)).resize(300,300).into(holder.thumbsV);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String getTitle = titles.get(holder.getPosition());
                String descR = descripts.get(holder.getPosition());
                String image = thumbnails.get(holder.getPosition());
                String contents = postsContent.get(holder.getPosition());

                String id = postId.get(holder.getPosition());

                Intent intent  = new Intent(ctx,ReadArticles.class);

                intent.putExtra("title_head",getTitle);
                intent.putExtra("descps",descR);
                intent.putExtra("image",image);
                intent.putExtra("content",contents);
                intent.putExtra("id",id);

                ctx.startActivity(intent);
            }
        });

    }




    @Override
    public int getItemCount() {

        return titles.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder

    {

        TextView tv , descV,dateV;
        ImageView thumbsV;

        public MyViewHolder( View itemView) {

            super(itemView);


            tv = itemView.findViewById(R.id.sampletext);

            descV = itemView.findViewById(R.id.descsP);

            thumbsV = itemView.findViewById(R.id.thumbs);

            dateV = itemView.findViewById(R.id.dates);


        }

    }


}
