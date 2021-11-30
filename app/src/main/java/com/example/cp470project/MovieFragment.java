package com.example.cp470project;

import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MovieFragment extends Fragment {
    TextView ageRating;
    TextView movieRating;
    TextView releaseDate;
    TextView movieName;
    ListView streamsList;
    String name;
    String age;
    String rating;
    String release;
    ArrayList<String> streams;
    Button WLButton;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("Movie");
        age = getArguments().getString("AgeRating");
        rating = getArguments().getString("MovieRating");
        release = getArguments().getString("ReleaseDate");
        streams = getArguments().getStringArrayList("Streams");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_fragment, container, false);
        WLButton = (Button) view.findViewById(R.id.addWatchlistButton);
        WLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WatchList.class);
                intent.putExtra("key",name);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        movieName = view.findViewById(R.id.movie_name);
        movieName.setText(name);
        ageRating = view.findViewById(R.id.age_rating);
        ageRating.setText("Age Rating: " + age);
        movieRating = view.findViewById(R.id.movie_rating);
        movieRating.setText("IMDb Rating: " + rating);
        releaseDate = view.findViewById(R.id.release_date);
        releaseDate.setText(release);
        streamsList = view.findViewById(R.id.streams_list);
        ArrayAdapter adapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_stream_item, streams);
        streamsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}