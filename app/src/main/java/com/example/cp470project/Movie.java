package com.example.cp470project;

import java.util.ArrayList;

public class Movie {
    private String name;
    private String ageRating;
    private String movieRating;
    private String releaseDate;
    private ArrayList<String> streams;
    private Integer movieImage;

    public Movie() {
    }

    public Movie(String name, Integer movieImage, String releaseDate, String ageRating, String movieRating, ArrayList<String> streams) {
        this.name = name;
        this.movieImage = movieImage;
        this.releaseDate = releaseDate;
        this.ageRating = ageRating;
        this.movieRating = movieRating;
        this.streams = streams;
    }

    public Movie(String name, Integer movieImage, String releaseDate) {
        this.name = name;
        this.movieImage = movieImage;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<String> getStreams() {
        return streams;
    }

    public void setStreams(ArrayList<String> streams) {
        this.streams = streams;
    }

    public Integer getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(Integer movieImage) {
        this.movieImage = movieImage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", ageRating='" + ageRating + '\'' +
                ", movieRating='" + movieRating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", streams=" + streams +
                '}';
    }

}
