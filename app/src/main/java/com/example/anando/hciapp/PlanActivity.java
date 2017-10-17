package com.example.anando.hciapp;

import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.anando.hciapp.adapters.activityplanAdapter;
import com.example.anando.hciapp.adapters.planContainer;
import com.example.anando.hciapp.library.SwipeDismissListViewTouchListener;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private ListView activityList;
    private ArrayList<planContainer> list;
    private activityplanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_plan);
        init();


        activityList.setAdapter(adapter);
        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(activityList, new SwipeDismissListViewTouchListener.DismissCallbacks(){

            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                for(int i : reverseSortedPositions){
                    adapter.remove(adapter.getItem(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
        activityList.setOnTouchListener(touchListener);
    }
    private void init(){
        list = new ArrayList<>();
        //for(int i=0;i<15;i++)list.add("x");
        activityList = (ListView)findViewById(R.id.activitylist);
        adapter = new activityplanAdapter(PlanActivity.this,list);

        findViewById(R.id.addplan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager = getPackageManager();
                List<ApplicationInfo> packs = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
                final ArrayList<String>appNames = new ArrayList<String>();
                for(ApplicationInfo appIn : packs){
                    appNames.add(appIn.packageName);
                }
                final CharSequence[] items = new CharSequence[appNames.size()]; for(int i = 0; i<appNames.size(); i++)items[i]=appNames.get(i);

                final ArrayList<String>selectedItems = new ArrayList<>();
                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).setTitle("Select Apps for monitoring").setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selectedItems.add(items[i].toString());
                        }else if(selectedItems.contains(items[i].toString())){
                            selectedItems.remove(items[i].toString());
                        }
                    }
                }).setPositiveButton("Done",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        for(String name : selectedItems){
                            try {
                                Drawable icon = getPackageManager().getApplicationIcon(name);
                                list.add(new planContainer(icon,name));
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).create();
                dialog.show();
            }
        });
    }
}
