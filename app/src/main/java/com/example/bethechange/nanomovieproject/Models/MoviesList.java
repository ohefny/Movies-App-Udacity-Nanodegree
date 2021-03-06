package com.example.bethechange.nanomovieproject.Models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class MoviesList {
    @SerializedName("results")
    ArrayList<MovieClass> movies=new ArrayList<>();
    int total_pages=15054;
    int total_results=301061;


    @SerializedName("page")
    int loaded_pages=0;

    public ArrayList<MovieClass> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieClass> movies) {
        this.movies = movies;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
    public int getLoaded_pages() {
        return loaded_pages;
    }

    public void setLoaded_pages(int loaded_pages) {
        this.loaded_pages = loaded_pages;
    }

}
