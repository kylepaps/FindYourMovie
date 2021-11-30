package com.example.cp470project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected static final String ACTIVITY_NAME = "MainActivity";
    private static Context context;
    //Spinner spinner;
    //String[] streaming_platforms;
    //ArrayAdapter adapter;
    Movie movie = new Movie();
    EditText dialogInput;
    ValueEventListener movieEventListener;
    ArrayList<String> movieDetailList;
    ArrayList<String> streamsList;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
//        spinner = (Spinner) findViewById(R.id.dropdown_menu);
//        streaming_platforms = getResources().getStringArray(R.array.streaming_platforms);
//        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, streaming_platforms);

        //set recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //movies list for PopularMoviesAdapter
        Movie[] movies = new Movie[]{
                new Movie("The Wolf of Wall Street", R.drawable.thewolfofwallstreet, "2013", "R", "8.2", createStreamList("Netflix", null, null, null)),
                new Movie("Avengers: Infinity War", R.drawable.avengers, "2018", "PG", "8.4", createStreamList(null, null, "Disney+", null)),
                new Movie("Goodfellas", R.drawable.goodfellas, "1990", "14A", "8.7", createStreamList("Netflix", null, null, "Crave")),
                new Movie("Inception", R.drawable.inception, "2010", "14A", "8.8", createStreamList("Netflix", null, null, "Crave")),
                new Movie("Jurrasic Park", R.drawable.jurassicpark, "1993", "PG", "8.1", createStreamList("Netflix", "Prime Video", null, null)),
                new Movie("The Shawshank Redemption", R.drawable.shawshankredemption, "1994", "14A", "9.3", createStreamList("Netflix", null, null, "Crave")),
                new Movie("Joker", R.drawable.joker, "2019", "14A", "8.4", createStreamList("Netflix", null, null, null)),
                new Movie("The Matrix", R.drawable.thematrix, "1999", "14A", "8.7", createStreamList(null, null, null, "Crave")),
                new Movie("Back to the Future", R.drawable.backtothefuture, "1985", "PG", "8.5", createStreamList(null, "Prime Video", null, null)),
                new Movie("Forrest Gump", R.drawable.forrestgump, "1994", "PG", "8.8", createStreamList("Netflix", "Prime Video", null, "Crave")),
                new Movie("The Hurt Locker", R.drawable.thehurtlocker, "2008", "14A", "7.5", createStreamList("Netflix", null, null, "Crave")),
                new Movie("Slumdog Millionaire", R.drawable.slumdogmillionaire, "2008", "14A", "8.0", createStreamList(null, null, null, "Crave"))
        };

        PopularMoviesAdapter moviesAdapter = new PopularMoviesAdapter(movies, MainActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(moviesAdapter);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        //Query movie details then pass values to createFragment() method
        movieDetailList = new ArrayList<>();
        streamsList = new ArrayList<>();
        movieEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    Toast.makeText(MainActivity.this,"Sorry we don't have the movie that you're looking for!",Toast.LENGTH_LONG).show();
                } else {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
    }


    //referenced from PopularMoviesAdapter. Static version of createFragment
    public static void onPosterClick(String name, String age, String rating, String release, ArrayList<String> streams) {
        Bundle bundle = new Bundle();
        bundle.putString("Movie", name);
        bundle.putString("AgeRating", age);
        bundle.putString("MovieRating", rating);
        bundle.putString("ReleaseDate", release);
        bundle.putStringArrayList("Streams", streams);
        Intent intent = new Intent(context, MovieDetails.class
        );
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    //create ArrayList for streaming platforms to be passed as arguments ex. Movie("Back to the Future", R.drawable.backtothefuture, "1985", "PG", "8.5", createStreamList(null, "Prime Video", null, null))
    public ArrayList<String> createStreamList(String netflix, String prime, String disney, String crave){
        ArrayList<String> streams = new ArrayList<String>();
        if (netflix != null) {
            streams.add(netflix);
        }
        if (prime != null) {
            streams.add(prime);
        }
        if (disney != null) {
            streams.add(disney);
        }
        if (crave != null) {
            streams.add(crave);
        }
        return streams;
    }


    public void createFragment(Movie movie){
        Bundle bundle = new Bundle();
        bundle.putString("Movie", movie.getName());
        bundle.putString("AgeRating", movie.getAgeRating());
        bundle.putString("MovieRating", movie.getMovieRating());
        bundle.putString("ReleaseDate", movie.getReleaseDate());
        bundle.putStringArrayList("Streams", movie.getStreams());
        Intent intent = new Intent(MainActivity.this, MovieDetails.class
            );
        intent.putExtras(bundle);
        startActivityForResult(intent, 10);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // invoked automatically by activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //toolbar options
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // invoked when an Item selected.
        int id = menuItem.getItemId();
        switch(id) {
            case R.id.watchlist_action:
                Log.d("Toolbar", "Option 1 selected");
                Intent intentWL = new Intent(MainActivity.this, WatchList.class);
                startActivity(intentWL);
                break;

            case R.id.about_action:
                Log.d("Toolbar", "Option 2 selected");
                Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent1);
                break;

            case R.id.movies_action:
                Log.d("Toolbar", "Option 3 selected");
                Intent intent2 = new Intent(MainActivity.this, MovieActivity.class);
                startActivity(intent2);
                break;

            case R.id.search_action:
                Log.d("Toolbar", "Option 4 selected");
                AlertDialog.Builder customDialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_search, null);
                dialogInput = (EditText) view.findViewById(R.id.dialog_search_box);

                customDialog.setView(view)
                        .setPositiveButton(R.string.search_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String input = dialogInput.getText().toString();
                                movie.setName(input);
                                Log.i(ACTIVITY_NAME, "Search Input: " + input);
                                Query query = FirebaseDatabase.getInstance().getReference().child("Movies").child(input);
                                query.addListenerForSingleValueEvent(movieEventListener);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog c_dialog = customDialog.create();
                c_dialog.show();
                break;


        }
        return true;
    }

}