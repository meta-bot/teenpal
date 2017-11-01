package com.example.anando.hciapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.anando.hciapp.adapters.FriendAdapter;
import com.example.anando.hciapp.adapters.FriendContainer;

import java.util.ArrayList;

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
    }
}
