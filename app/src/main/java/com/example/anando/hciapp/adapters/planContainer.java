package com.example.anando.hciapp.adapters;

import android.graphics.drawable.Drawable;

/**
 * Created by anando on 17-Oct-17.
 */
public class planContainer {
    private Drawable icon;
    private String name;
    public planContainer(Drawable icon , String name){
        this.icon = icon;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
