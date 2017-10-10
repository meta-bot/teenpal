package com.example.anando.hciapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.anando.hciapp.adapters.EmergerncyAdapter;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity {

    private ListView activityList;
    private ArrayList<String> list;
    private EmergerncyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_emergency);
        init();
        activityList.setAdapter(adapter);
    }
    private void init(){
        list = new ArrayList<>();
        for(int i=0;i<15;i++)list.add("x");
        activityList = (ListView)findViewById(R.id.emergecy_list);
        adapter = new EmergerncyAdapter(this,list);
    }
}
