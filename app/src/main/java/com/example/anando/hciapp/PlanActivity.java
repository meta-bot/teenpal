package com.example.anando.hciapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.anando.hciapp.adapters.activityplanAdapter;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    private ListView activityList;
    private ArrayList<String> list;
    private activityplanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_plan);
        init();


        activityList.setAdapter(adapter);
    }
    private void init(){
        list = new ArrayList<>();
        for(int i=0;i<15;i++)list.add("x");
        activityList = (ListView)findViewById(R.id.activitylist);
        adapter = new activityplanAdapter(PlanActivity.this,list);

    }
}
