package com.magazine.magazine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class ReadArticles extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {



    TextView titleView,descspView,contentView;
    ImageView thumbsView;

    DatabaseSystem dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_articles);


        String id = getIntent().getStringExtra("id");

        String title = getIntent().getStringExtra("title_head");

        String descs = getIntent().getStringExtra("descps");

        String image = getIntent().getStringExtra("image");

        String contents = getIntent().getStringExtra("content");

        Typeface tf = ResourcesCompat.getFont(getApplicationContext(),R.font.cfontart);


        dbHelper = new DatabaseSystem(this);

        dbHelper.insertContact(id);


        titleView = findViewById(R.id.titleV);
        descspView = findViewById(R.id.descV);
        thumbsView = findViewById(R.id.imageV);
        contentView = findViewById(R.id.contentV);

        titleView.setTypeface(tf);


        titleView.setText(title);
        descspView.setText(descs);
        contentView.setText(contents);

        Picasso.get().load(image).into(thumbsView);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        String hello = getIntent().getStringExtra("title");

        if (hello !=null)
        {
            String titles = getIntent().getStringExtra("title");
            String desps = getIntent().getStringExtra("descs");
            String content = getIntent().getStringExtra("content");
            String images = getIntent().getStringExtra("image");
            String ids = getIntent().getStringExtra("id");

            titleView.setText(titles);
            descspView.setText(desps);
            contentView.setText(content);

            Picasso.get().load(images).into(thumbsView);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.cancel("mine",Integer.parseInt(ids));

            SharedPreferences.Editor editor = getSharedPreferences("notify",MODE_PRIVATE).edit();

            editor.putString("tag","mine");
            editor.putString("id",ids);
            editor.commit();

        }


        SharedPreferences preferences = getSharedPreferences("notify",MODE_PRIVATE);

        String alert = preferences.getString("tag","Nothing Found");
        String alert2 = preferences.getString("id","Nothing Found");

        Toast.makeText(getApplicationContext(),"Memory Tag found was "+alert2,Toast.LENGTH_SHORT).show();




    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.article, menu);

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        int[] inK = {14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};

        int cval =0;
        int diff = 1;

        Boolean stopped = false;

        contentView = findViewById(R.id.contentV);

        SharedPreferences.Editor editor = getSharedPreferences("text_big",MODE_PRIVATE).edit();

        editor.putInt("inc",0);
        editor.putBoolean("stopped",false);
        editor.commit();

        SharedPreferences preferences = getSharedPreferences("text_big",MODE_PRIVATE);



        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {

            Toast.makeText(getApplicationContext(),"BACK SPOTTED",Toast.LENGTH_SHORT).show();

            return true;

        } else  if (id == R.id.text_big) {


             cval = preferences.getInt("text_big",0);

             cval = cval + diff;

             editor.putInt("text_big",cval);
             editor.commit();

             if (cval > 14)
             {
                 editor.putInt("text_big",14);
                 editor.commit();
                 cval = 14;
                 contentView.setTextSize(inK[14]);
             }

             contentView.setTextSize(inK[cval]);

            return true;

        } else  if (id == R.id.text_small) {


            cval = preferences.getInt("text_big",0);

            cval = cval - diff;

            editor.putInt("text_big",cval);
            editor.commit();

            if (cval <= 0)
            {
                editor.putInt("text_big",0);
                editor.commit();
                cval = 0;
                contentView.setTextSize(inK[0]);

            }

            contentView.setTextSize(inK[cval]);


            return true;

        }else if (id == R.id.action_share)
        {
            String idse = getIntent().getStringExtra("id");

            String title = getIntent().getStringExtra("title_head");


            Intent sharing = new Intent(Intent.ACTION_SEND);
            sharing.setType("text/plain");
            sharing.putExtra(Intent.EXTRA_SUBJECT,title);
            sharing.putExtra(Intent.EXTRA_TEXT,"http://192.168.1.102/read/"+idse);
            startActivity(Intent.createChooser(sharing,"Share via"));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
