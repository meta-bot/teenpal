package com.example.anando.hciapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anando.hciapp.adapters.FriendAdapter;
import com.example.anando.hciapp.adapters.FriendContainer;
import com.example.anando.hciapp.adapters.planContainer;
import com.example.anando.hciapp.database.planDB;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

public class ShareActivity extends AppCompatActivity {

    ArrayList<FriendContainer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init(){
        ListView listView = (ListView)findViewById(R.id.friend_container);
        list = new ArrayList<>();
        list.add(new FriendContainer("Fahim Arefin", R.mipmap.fahim));
        list.add(new FriendContainer("Meha Masum",R.mipmap.meha));
        FriendAdapter friendAdapter = new FriendAdapter(this,list);
        listView.setAdapter(friendAdapter);
        final ArrayList<planContainer> planContainers = new planDB(ShareActivity.this).getAllPlans();
        final long total = new UTILL(this).Aggregate(planContainers);
        final ArrayList<planContainer> temps =  new UTILL(this).detail(planContainers);
        Button details = (Button)findViewById(R.id.details_button);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ShareActivity.this);
                dialog.setContentView(R.layout.dialog_usage);
                de.hdodenhof.circleimageview.CircleImageView dialogPP = (de.hdodenhof.circleimageview.CircleImageView)dialog.findViewById(R.id.dialog_friend_pp);
                dialogPP.setImageResource(R.mipmap.ic_profile);
                TextView dialogName = (TextView)dialog.findViewById(R.id.dialog_name);
                dialogName.setText("Muhaimin Anando");
                PieChart pie = (PieChart)dialog.findViewById(R.id.pie_chart);
                ArrayList<Entry> entries = new ArrayList<Entry>();

                for(int x = 0 ; x< temps.size();x++){
                    entries.add(new Entry((int)((int)temps.get(x).getTimer()/(int)total)*100,x));
                }
                PieDataSet dataSet = new PieDataSet(entries,"Apps");
                ArrayList<String> apps = new ArrayList<>();
                for(planContainer pc : planContainers)apps.add(pc.getName());
                PieData data = new PieData(apps,dataSet);

                pie.setDescription("Apps Usage Time");
                pie.setData(data);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pie.animateY(1000);
                Button done = (Button)dialog.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
