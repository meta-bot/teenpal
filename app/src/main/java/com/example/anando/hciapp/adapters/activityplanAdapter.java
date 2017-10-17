package com.example.anando.hciapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.anando.hciapp.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by anando on 10-Oct-17.
 */
public class activityplanAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<planContainer> list;
    public activityplanAdapter(Activity activity , ArrayList<planContainer> list){
        this.list = list;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void remove(Object o){
        list.remove(o);
        return;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plancard , viewGroup , false);
        }
        if(list != null){
            TextView name = (TextView)view.findViewById(R.id.planname);
            ImageView logo = (ImageView)view.findViewById(R.id.planlogo);
            logo.setImageDrawable(list.get(i).getIcon());
            name.setText(list.get(i).getName().substring(list.get(i).getName().lastIndexOf(".")+1));
            CrystalSeekbar seekbar = (CrystalSeekbar) view.findViewById(R.id.planseek);
            final TextView timer = (TextView)view.findViewById(R.id.planTime);
            seekbar.setMaxValue(24*4);
            seekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
                @Override
                public void valueChanged(Number value) {
                    long hours = (long)value/4;
                    long min = (long)value%4;
                    timer.setText(""+hours+"hour");
                }
            });
        }
        return view;
    }
    public void addElem(planContainer p){
        list.add(p);
    }
}
