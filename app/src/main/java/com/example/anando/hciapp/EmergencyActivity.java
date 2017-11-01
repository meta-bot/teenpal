package com.example.anando.hciapp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.anando.hciapp.adapters.EmergerncyAdapter;
import com.example.anando.hciapp.adapters.emergencyContainer;
import com.example.anando.hciapp.adapters.planContainer;
import com.example.anando.hciapp.database.planDB;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity {

    private ListView activityList;
    private ArrayList<emergencyContainer> list;
    private EmergerncyAdapter adapter;
    private ArrayList<emergencyContainer> contactFuture;
    private planDB database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_emergency);
        init();
        activityList.setAdapter(adapter);
    }
    private void init(){
        database = new planDB(this);
        new ContactBring().execute();
        list = database.getAllContact();

        //for(int i=0;i<15;i++)list.add("x");
        activityList = (ListView)findViewById(R.id.emergecy_list);
        adapter = new EmergerncyAdapter(this,list);
        ImageButton add = (ImageButton) findViewById(R.id.addEmergency);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<emergencyContainer> myList = contactFuture;
                //getContactList(myList);
                final CharSequence[] items = new CharSequence[myList.size()];
                for(int i=0; i< myList.size(); i++){
                    items[i]= myList.get(i).getName()+"\n"+myList.get(i).getNumber();
                }
                final ArrayList<emergencyContainer>selectedItems = new ArrayList<>();
                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).setTitle("Select Apps for monitoring").setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selectedItems.add(new emergencyContainer(items[i].toString().substring(0,items[i].toString().indexOf("\n")),items[i].toString().substring(items[i].toString().indexOf("\n")+1),null));
                        }else if(selectedItems.contains(new emergencyContainer(items[i].toString().substring(0,items[i].toString().indexOf("\n")),items[i].toString().substring(items[i].toString().indexOf("\n")+1),null))){
                            selectedItems.remove(new emergencyContainer(items[i].toString().substring(0,items[i].toString().indexOf("\n")),items[i].toString().substring(items[i].toString().indexOf("\n")+1),null));
                        }
                    }
                }).setPositiveButton("Done",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        for(emergencyContainer elems : selectedItems){
                            list.add(elems);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).create();
                dialog.show();
            }
        });
    }
    private void getContactList(ArrayList<emergencyContainer> list) {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Log.i(TAG, "Name: " + name);
                        //Log.i(TAG, "Phone Number: " + phoneNo);
                        list.add(new emergencyContainer(name,phoneNo,null));
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new SyncContact().execute();
    }
    private class ContactBring extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            contactFuture = new ArrayList<>();
            getContactList(contactFuture);
            return null;
        }
    }
    private class SyncContact extends AsyncTask<Void , Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = database.getWritableDatabase();
            db.delete(database.table2,null,null);
            for(emergencyContainer ec : list){
                database.pushContact(ec.getName(),ec.getNumber());
            }
            return null;
        }
    }
}
