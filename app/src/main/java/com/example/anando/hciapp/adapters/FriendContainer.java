package com.example.anando.hciapp.adapters;

import android.widget.Filterable;

/**
 * Created by anando on 30-Oct-17.
 */
public class FriendContainer {
    private String name;
    private int url;
    public FriendContainer(String name,int url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getUrl() {
        return url;
    }
}
