package com.magazine.magazine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ToggleRecentPager extends PagerAdapter {


    Context context;

    String host = "http://192.168.1.108/";


    String storage = host+"storage/";


    ArrayList<String> images = new ArrayList<>();



    public ToggleRecentPager(Context context,String data) {

        this.context = context;

       try {
           JSONArray array = new JSONArray(data);

           for (int i=0; i<data.length(); i++)
           {
               JSONObject object = array.getJSONObject(i);
               String ImageUrl = object.getString("image");
               images.add(ImageUrl);
           }

       }catch (Exception e)
       {
           e.printStackTrace();
       }

    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View page = inflater.inflate(R.layout.activity_main,null);


        final ImageView imageView  = new ImageView(context);

        final int radius = 5;
        final int margin = 5;

        Picasso.get().load(storage+images.get(position)).transform(new RoundedCornersTransformation(20,40)).resize(250,300).into(imageView);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        ((ViewPager) container).addView(imageView,0);

        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == ((ImageView) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public float getPageWidth(int position) {
        return(0.3f);

    }


}
