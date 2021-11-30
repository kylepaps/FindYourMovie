package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WatchList extends AppCompatActivity {
    ListView watchList;
    private ArrayList<String> newMoviesArray;
    WatchListDBHelper newDB;
    Cursor dbData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        newMoviesArray = new ArrayList<>();
        watchList = (ListView) findViewById(R.id.movieListView);
        newDB = new WatchListDBHelper(this);
        Intent intent = getIntent();
        String tempVal = intent.getStringExtra("key");

        if (tempVal != null) {
            if (tempVal.length() != 0) {
                AddData(tempVal);
            }
        }

        dbData = newDB.getListContents();


        if (dbData.getCount() == 0){
            Toast.makeText(WatchList.this,"Database is empty", Toast.LENGTH_LONG).show();
        }
        else{
            while(dbData.moveToNext()){
                if (newMoviesArray.contains(dbData.getString(1))){
                    Toast.makeText(WatchList.this,"This movie is already in Watchlist", Toast.LENGTH_LONG).show();
                }
                else {
                    newMoviesArray.add(dbData.getString(1));
                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newMoviesArray);
                    watchList.setAdapter(listAdapter);
                }
            }
        }

        watchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mName = adapterView.getItemAtPosition(i).toString();
                Cursor tempData = newDB.getID(mName);

                int tempID = -1;
                while(tempData.moveToNext()){
                    tempID = tempData.getInt(0);
                }
                if (tempID > -1){
                    Intent wlDetailsIntent = new Intent(WatchList.this, WatchListDetails.class);
                    wlDetailsIntent.putExtra("id",tempID);
                    wlDetailsIntent.putExtra("name",mName);
                    startActivity(wlDetailsIntent);
                }

            }
        });
    }

    public void AddData(String newMovie){
        boolean insertData = newDB.addData(newMovie);

        if(insertData == true){
            Toast.makeText(WatchList.this,"Movie added to Watchlist", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(WatchList.this,"Movie NOT added to Watchlist(Check)", Toast.LENGTH_LONG).show();
        }
    }
}