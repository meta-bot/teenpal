package com.example.anando.hciapp.adapters;

import android.graphics.drawable.Drawable;

/**
 * Created by anando on 17-Oct-17.
 */
public class planContainer {
    private Drawable icon;
    private String name;
    private int id;
    private int timer;
    public planContainer(Drawable icon , String name){
        this.icon = icon;
        this.name = name;
        //timer = 0;
    }
    public planContainer(String name ,int id , int timer){
        this(null,name);
        this.id = id;
        this.timer = timer;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getId() {
        return id;
    }

    public int getTimer() {
        return timer;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
