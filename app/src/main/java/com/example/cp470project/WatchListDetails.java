package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WatchListDetails extends AppCompatActivity {
    private Button deleteMovieB;
    WatchListDBHelper newDB;
    private String selectedMovie;
    private int selectedID;
    private TextView movieNameDisplayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list_details);
        movieNameDisplayed = (TextView) findViewById(R.id.movieDetailTV);
        deleteMovieB = (Button)findViewById(R.id.deleteMovieButton);
        newDB = new WatchListDBHelper(this);
        TextView movieNameDisplayed = (TextView)findViewById(R.id.movieDetailTV);
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedMovie = receivedIntent.getStringExtra("name");
        movieNameDisplayed.setText(selectedMovie);

        deleteMovieB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDB.deleteMovie(selectedID,selectedMovie);
                Intent intent = new Intent(WatchListDetails.this, WatchList.class);
                startActivity(intent);
            }
        });


    }
}