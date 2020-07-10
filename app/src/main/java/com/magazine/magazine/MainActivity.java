package com.magazine.magazine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , PopupMenu.OnMenuItemClickListener {


     private static ArrayList<String> titleArrays = new ArrayList<>();

     TextView badgeText;

     RelativeLayout badgeLay;


     Domain_Configs domains = new Domain_Configs();

     DatabaseSystem databaseSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.cancel("mine",32);



        startService(new Intent(getApplicationContext(),BackGroundService.class));


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        Fragment fragment = new MainFragement();

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.framelayout,fragment,"home");
        transaction.addToBackStack("home");
        transaction.commit();



        loadAdapters();


        new alert_updates(getApplicationContext()).execute();


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setIcon(R.drawable.action_logo);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);




    }






    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.shares, popup.getMenu());
        popup.show();
    }



    public void loadAdapters()
    {



        new syncCategories(getApplicationContext(), new getCategories() {
            @Override
            public void category(String data) {

                //Toast.makeText(getApplicationContext(),""+data,Toast.LENGTH_SHORT).show();

                ViewPager pager = findViewById(R.id.viewPager);

                categoryPager cpager = new categoryPager(getApplicationContext(),data);

                pager.setAdapter(cpager);

                if (data.isEmpty()){


                    Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();

                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.framelayout,new errors(),"error");
                    transaction.addToBackStack("error");
                    transaction.commit();


                }else
                {
                    Toast.makeText(getApplicationContext(),"Not Empty",Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();


        new syncRecentPosts(getApplicationContext(), new RecentCategories() {
            @Override
            public void getRecentsPosts(String data) {

                ViewPager mpager = findViewById(R.id.togglePager);

                ToggleRecentPager toggleRecentPager = new ToggleRecentPager(getApplicationContext(),data);

                mpager.setAdapter(toggleRecentPager);


            }
        }).execute();


        new syncArticles(getApplicationContext(), new ArticlesInterface() {
            @Override
            public void getArticles(String data) {

                RecyclerView recyclerView = findViewById(R.id.recylers);


                customAdapter adapter  = new customAdapter(getApplicationContext(),data);

                LinearLayoutManager managers = new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(managers);

                recyclerView.setAdapter(adapter);

                recyclerView.setNestedScrollingEnabled(false);



            }
        }).execute();




    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {


        switch (item.getItemId()){

            case R.id.whatsapp:
                Toast.makeText(getApplicationContext(),"Facebook sharing turned on",Toast.LENGTH_SHORT).show();
                return true;

        }

        return false;
    }




    @Override
    public void onBackPressed() {

        loadAdapters();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_notify);

        final View mview = MenuItemCompat.getActionView(menuItem);

        badgeText = mview.findViewById(R.id.notify_badge);

        SharedPreferences pref = getSharedPreferences("counts",MODE_PRIVATE);

        String vals = pref.getString("countValue","NULL");

        badgeText.setText(vals);

        if (vals ==null || vals.contains("0"))
        {
            badgeText.setVisibility(View.GONE);
        }

        mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notify) {


            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.framelayout,new notificationsFragment(),"alert");
            transaction.addToBackStack("alert");
            transaction.commit();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.framelayout,new MainFragement(),"home");
            transaction.addToBackStack("home");
            transaction.commit();
            loadAdapters();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.action_share)
        {
            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
