package com.example.cp470project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder>{
    Movie[] movies;
    Context context;

    public PopularMoviesAdapter(Movie[] movies, MainActivity activity) {
        this.movies = movies;
        context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.popular_movies_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movieList = movies[position];
        holder.movieName.setText(movieList.getName());
        //holder.releaseDate.setText(movieList.getReleaseDate());
        holder.moviePoster.setImageResource(movieList.getMovieImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.onPosterClick(movieList.getName(), movieList.getAgeRating(), movieList.getMovieRating(), movieList.getReleaseDate(), movieList.getStreams());

            }
        });
    }



    @Override
    public int getItemCount() {
        return movies.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieName;
        //TextView releaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            movieName = itemView.findViewById(R.id.movie_card_name);
            //releaseDate = itemView.findViewById(R.id.movie_card_release_date);
        }
    }
}
