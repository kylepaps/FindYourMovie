package com.example.cp470project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MovieDetails";
    String movieName;
    String ageRating;
    String movieRating;
    String releaseDate;
    ArrayList<String> streams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieName = getIntent().getExtras().getString("Movie");
        ageRating = getIntent().getExtras().getString("AgeRating");
        movieRating = getIntent().getExtras().getString("MovieRating");
        releaseDate = getIntent().getExtras().getString("ReleaseDate");
        streams = getIntent().getExtras().getStringArrayList("Streams");

        Bundle bundle = new Bundle();
        bundle.putString("Movie", movieName);
        bundle.putString("AgeRating", ageRating);
        bundle.putString("MovieRating", movieRating);
        bundle.putString("ReleaseDate", releaseDate);
        bundle.putStringArrayList("Streams", streams);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        ft.add(R.id.activity_frame, fragment);
        ft.commit();
    }
}