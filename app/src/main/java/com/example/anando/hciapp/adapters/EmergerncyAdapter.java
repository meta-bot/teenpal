package com.example.anando.hciapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anando.hciapp.R;

import java.util.ArrayList;

/**
 * Created by anando on 10-Oct-17.
 */
public class EmergerncyAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<emergencyContainer>list;

    public EmergerncyAdapter(Activity activity, ArrayList<emergencyContainer>list){
        this.activity = activity;
        this.list = list;
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
    public void add(emergencyContainer e){
        list.add(e);
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emergencycard , viewGroup , false);
        }
        if(list != null){
            ImageView propic = (ImageView)view.findViewById(R.id.emergency_pic);
            TextView name = (TextView)view.findViewById(R.id.emergency_name);
            TextView cont = (TextView)view.findViewById(R.id.emergency_contact);
            ImageButton delete = (ImageButton)view.findViewById(R.id.emergency_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(i);
                    notifyDataSetChanged();
                }
            });
            if(list.get(i).getPic() != null)
                propic.setImageDrawable(list.get(i).getPic());
            name.setText(list.get(i).getName());
            cont.setText(list.get(i).getNumber());
        }
        return view;
    }
}
