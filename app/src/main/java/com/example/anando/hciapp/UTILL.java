package com.example.anando.hciapp;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.anando.hciapp.adapters.planContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anando on 01-Nov-17.
 */
public class UTILL {
    private Context context;
    public UTILL(Context context){
        this.context = context;
    }
    public  void Usage(ArrayList<planContainer> plans){
        final UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);

        Calendar cal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,-1);
            final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(), System.currentTimeMillis());
            //System.out.println("results for " + beginCal.getTime().toGMTString() + " - " + endCal.getTime().toGMTString());
            for (UsageStats app : queryUsageStats) {
                planContainer pc = has(plans,app.getPackageName());
                if(pc !=null){
                    System.out.println(app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000));
                    if((app.getTotalTimeInForeground()/1000)>pc.getTimer()){
                        Toast.makeText(context,pc.getName()+" is out of boundary", Toast.LENGTH_LONG).show();
                        vibrate(pc.getName());
                        //break;
                    }
                }
            }
        }

    }
    public  long Aggregate(ArrayList<planContainer> plans){
        final UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
        long ret = 0;
        Calendar cal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,-1);
            final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(), System.currentTimeMillis());
            //System.out.println("results for " + beginCal.getTime().toGMTString() + " - " + endCal.getTime().toGMTString());
            for (UsageStats app : queryUsageStats) {
                Log.d("UTILLAgrigate##: ",app.getTotalTimeInForeground()+"");
                planContainer pc = has(plans,app.getPackageName());
                if(pc !=null){
                    System.out.println(app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000));
                    ret += (app.getTotalTimeInForeground()/1000);
                }
            }
        }
        return ret;
    }

    public  ArrayList<planContainer> detail(ArrayList<planContainer> plans){
        ArrayList<planContainer> ret = new ArrayList<>();
        final UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
        //long ret = 0;
        Calendar cal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,-1);
            final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(), System.currentTimeMillis());
            //System.out.println("results for " + beginCal.getTime().toGMTString() + " - " + endCal.getTime().toGMTString());
            for (UsageStats app : queryUsageStats) {
                planContainer pc = has(plans,app.getPackageName());
                if(pc !=null){
                    planContainer temp = new planContainer(pc.getIcon(),pc.getName());

                    System.out.println(app.getPackageName() + " | " + (float) (app.getTotalTimeInForeground() / 1000));
                    temp.setTimer((int)(app.getTotalTimeInForeground()/1000));
                    ret.add(temp);
                }
            }
        }
        return ret;
    }

    public void vibrate(String str){
        final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000};
        v.vibrate(pattern, 0);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final AlertDialog dialog = dialogBuilder.create();

        dialogBuilder.setTitle("Time Usage Exceed");
        dialogBuilder.setIcon(R.drawable.rect_error);
        dialogBuilder.setMessage("You've used "+str+" more than your limit");
        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                v.cancel();
            }
        });
        dialogBuilder.show();
        //dialog.show();
    }
    private planContainer has(ArrayList<planContainer> plans , String str){
        planContainer ret = null;
        for(planContainer pc : plans){
            if(pc.getName().matches(str))return pc;
        }
        return ret;
    }
}
