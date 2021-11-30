package com.example.cp470project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class MovieActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MovieActivity";
    private ListView listView;
    boolean fragmentLoaded;
    Movie movie = new Movie();
    String movieName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        listView = findViewById(R.id.list_movies);

        if (findViewById(R.id.frame_layout) != null) {
            Log.i(ACTIVITY_NAME, "FrameLayout exists on the screen");
            fragmentLoaded = true;
        } else {
            Log.i(ACTIVITY_NAME, "FrameLayout does not exist on the screen");
            fragmentLoaded = false;
        }


        //Query movie names, set ListView with names
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_movie_item, list);
        listView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Movies");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    list.add(snapshot1.getKey());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        reference.addListenerForSingleValueEvent(valueEventListener);

        //Query movie details then pass values to createFragment() method
        ArrayList<String> movieDetailList = new ArrayList<>();
        ArrayList<String> streamsList = new ArrayList<>();
        ValueEventListener movieEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movieDetailList.clear();
                streamsList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (snapshot.getKey().equals("Streams")){
                        for (DataSnapshot child : snapshot.getChildren()) {
                            streamsList.add(child.getValue().toString());
                        }
                    } else{
                        movieDetailList.add(snapshot.getValue().toString());
                    }
                }
                movie.setAgeRating(movieDetailList.get(0));
                movie.setMovieRating(movieDetailList.get(1));
                movie.setReleaseDate(movieDetailList.get(2));
                movie.setStreams(streamsList);
                Log.i(ACTIVITY_NAME, "movieDetailList: " + movieDetailList + ". streamsList: " + streamsList);
                createFragment(movie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MovieActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                movieName = (String) adapterView.getItemAtPosition(i);
                Query query = FirebaseDatabase.getInstance().getReference().child("Movies").child(movieName);
                query.addListenerForSingleValueEvent(movieEventListener);
                movie.setName(movieName);

            }
        });
    }


    public void createFragment(Movie movie){
        Bundle bundle = new Bundle();
        bundle.putString("Movie", movie.getName());
        bundle.putString("AgeRating", movie.getAgeRating());
        bundle.putString("MovieRating", movie.getMovieRating());
        bundle.putString("ReleaseDate", movie.getReleaseDate());
        bundle.putStringArrayList("Streams", movie.getStreams());
        if (fragmentLoaded) {
            MovieFragment fragment = new MovieFragment();
            fragment.setArguments(bundle);
            FragmentTransaction ft =
                    getSupportFragmentManager().beginTransaction();
            if (ft != null){
                FrameLayout tempFrame = (FrameLayout)findViewById(R.id.frame_layout);
                tempFrame.removeAllViews();
            }
            ft.add(R.id.frame_layout, fragment);
            ft.commit();
        } else {
            Intent intent = new Intent(MovieActivity.this, MovieDetails.class
            );
            intent.putExtras(bundle);
            startActivityForResult(intent, 10);
        }
    }


}