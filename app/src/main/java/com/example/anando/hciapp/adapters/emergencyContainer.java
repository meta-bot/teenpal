package com.example.anando.hciapp.adapters;

import android.graphics.drawable.Drawable;

/**
 * Created by anando on 17-Oct-17.
 */
public class emergencyContainer {
    private String name;
    private String number;
    private Drawable pic;
    public emergencyContainer(String name, String number , Drawable pic){
        this.name = name;
        this.number= number;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public Drawable getPic() {
        return pic;
    }

    public String getNumber() {
        return number;
    }
}
