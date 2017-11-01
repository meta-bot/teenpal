package com.example.anando.hciapp.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anando.hciapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by anando on 11-Oct-17.
 */
public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {
    Activity activity;
    ArrayList<newsContainer> list;
    public newsAdapter(Activity activity , ArrayList<newsContainer>list){
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.favrthomecard,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView iv = (ImageView)cardView.findViewById(R.id.fav_icon);
        TextView headline = (TextView)cardView.findViewById(R.id.fav_headLine);
        Picasso.with(activity).load(list.get(position).getImage()).into(iv);
        headline.setText(list.get(position).getHeadLine());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }

}
