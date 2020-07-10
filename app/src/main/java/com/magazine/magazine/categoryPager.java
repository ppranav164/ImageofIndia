package com.magazine.magazine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class categoryPager extends PagerAdapter {


    Context context;

    ArrayList<String> arrayList = new ArrayList<>();

    public categoryPager(Context context,String data) {

        this.context = context;


       try {
           JSONArray array = new JSONArray(data);
           for (int i=0;i<data.length();i++)
           {
               JSONObject object = array.getJSONObject(i);
               String category = object.getString("category_name");
               arrayList.add(category);
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


        View page = inflater.inflate(R.layout.layout_main_fragement,null);


        final TextView textView  = new TextView(context);

        textView.setText(arrayList.get(position));

        ((ViewPager) container).addView(textView,0);


        return textView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == ((TextView) object);
    }

    @Override
    public int getCount() {

        return arrayList.size();
    }


    @Override
    public float getPageWidth(int position) {
        return(0.3f);

    }

}
