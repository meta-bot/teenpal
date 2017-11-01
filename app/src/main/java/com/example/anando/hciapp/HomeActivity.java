package com.example.anando.hciapp;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anando.hciapp.adapters.emergencyContainer;
import com.example.anando.hciapp.adapters.newsAdapter;
import com.example.anando.hciapp.adapters.newsContainer;
import com.example.anando.hciapp.adapters.planContainer;
import com.example.anando.hciapp.database.planDB;
import com.github.lzyzsd.circleprogress.ArcProgress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<newsContainer> list;
    newsAdapter news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        init();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void pushNews(String result) throws JSONException {
        JSONObject jobj = new JSONObject(result);
        JSONArray jsonArray = jobj.getJSONArray("articles");
        for(int i=0;i<jsonArray.length();i++){
            jobj = jsonArray.getJSONObject(i);
            String headline = jobj.getString("title");
            String image = jobj.getString("urlToImage");
            String news = jobj.getString("url");
            list.add(new newsContainer(headline,image,news));
        }
    }
    private class BringNews extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String address = "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=latest&apiKey=f5bd7dbf76814bd4b8a5826298b07827";
            try {
                URL url = new URL(address);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String result = bufferedReader.readLine();
                if(result!=null) {
                    Log.i("NEWS", "report fetch reply: " + result);
                    pushNews(result);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            news.notifyDataSetChanged();
        }
    }
    private void init(){

        list = new ArrayList<>();
        new BringNews().execute();
        //for(int i=0;i<15;i++)list.add("");
        news = new newsAdapter(this, list);
        RecyclerView lv =(RecyclerView) findViewById(R.id.newslist);
        lv.setAdapter(news);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lv.setLayoutManager(layoutManager);

        ImageButton sms = (ImageButton)findViewById(R.id.send_sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planDB database = new planDB(HomeActivity.this);
                ArrayList<emergencyContainer> ec = database.getAllContact();
                for(emergencyContainer x : ec){
                    sendSMS(x.getNumber(),"MSG:: FROM TEENPAL.");
                }
            }
        });

        ArcProgress progress = (ArcProgress)findViewById(R.id.home_progress);
        ArrayList<planContainer> containers = new planDB(HomeActivity.this).getAllPlans();
        long totalTime = 0;
        for(planContainer pc : containers)totalTime+=(pc.getTimer()*1000);
        long aggrigate = new UTILL(HomeActivity.this).Aggregate(containers);
        Log.d("TOTAL##",totalTime+" "+aggrigate);
        if(totalTime>0) {
            double setTime = (double) ((double)(aggrigate / (double)totalTime) * 100.0);
            if (setTime > 100.0) setTime = 100.0;
            if(setTime>=100)new UTILL(HomeActivity.this).vibrate("YOU EXCEED YOUR LIMIT");
            progress.setProgress((int)Math.ceil(setTime));
        }else progress.setProgress(100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //finish();
        //startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_plan) {
            startActivity(new Intent(HomeActivity.this,PlanActivity.class));
        } else if (id == R.id.nav_emergency) {
            startActivity(new Intent(HomeActivity.this,EmergencyActivity.class));
        } else if (id == R.id.nav_fav) {
            FragmentManager fm = getSupportFragmentManager();
            FavouriteList alertdFragment = new FavouriteList();
            alertdFragment.show(fm, "FavouriteList");
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(HomeActivity.this,ShareActivity.class));
        } else if (id == R.id.nav_send) {
            //Usage();
            new UTILL(HomeActivity.this).Usage(new planDB(HomeActivity.this).getAllPlans());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
