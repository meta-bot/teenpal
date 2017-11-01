package com.example.anando.hciapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.anando.hciapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by anando on 30-Oct-17.
 */
public class FriendAdapter extends BaseAdapter {
    ArrayList<FriendContainer> list;
    Activity activity;
    public FriendAdapter(Activity activity , ArrayList<FriendContainer> list){
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
    class ImageFetch extends AsyncTask<Void, Void ,Bitmap>{
        private de.hdodenhof.circleimageview.CircleImageView pp;
        private URL url;
        ImageFetch(de.hdodenhof.circleimageview.CircleImageView pp , URL url){
            this.pp = pp;
            this.url = url;
        }
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //pp.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            pp.setImageBitmap(bitmap);
        }
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friendcard , viewGroup , false);
        }
        if(list!=null){
            de.hdodenhof.circleimageview.CircleImageView pp = (de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.friend_image);
            TextView name = (TextView)view.findViewById(R.id.friend_name);
            final TextView active_time = (TextView)view.findViewById(R.id.friend_active_time);
            Button details = (Button)view.findViewById(R.id.friend_details);

            pp.setImageResource(list.get(i).getUrl());
            name.setText(list.get(i).getName());
            active_time.setText("Usage Time: "+new Random().nextInt(8)+" hour(s)");

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.dialog_usage);
                    de.hdodenhof.circleimageview.CircleImageView dialogPP = (de.hdodenhof.circleimageview.CircleImageView)dialog.findViewById(R.id.dialog_friend_pp);
                    dialogPP.setImageResource(list.get(i).getUrl());
                    TextView dialogName = (TextView)dialog.findViewById(R.id.dialog_name);
                    dialogName.setText(list.get(i).getName());
                    PieChart pie = (PieChart)dialog.findViewById(R.id.pie_chart);
                    ArrayList<Entry> entries = new ArrayList<Entry>();
                    for(int x = 0; x<5; x++){
                        entries.add(new Entry(new Random().nextInt(100),x));
                    }
                    PieDataSet dataSet = new PieDataSet(entries,"Apps");
                    ArrayList<String> apps = new ArrayList<>();
                    apps.add("Facebook"); apps.add("Whats App"); apps.add("Viber"); apps.add("Emo"); apps.add("Pathao");
                    PieData data = new PieData(apps,dataSet);

                    pie.setDescription("Apps Usage Time");
                    pie.setData(data);
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pie.animateY(1000);
                    Button done = (Button)dialog.findViewById(R.id.done);
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
        return view;
    }
}
