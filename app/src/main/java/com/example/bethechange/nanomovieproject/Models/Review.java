package com.example.bethechange.nanomovieproject.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/10/2017.
 */

public class Review{
    String content;
    String name;


    public Review(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return  (o instanceof VideoInfo) && ((VideoInfo)o).name.equals(this.name);

    }
    public static class ReviewList{
        int total_pages=1;
        public ArrayList<Review> getReviews() {
            return reviews;
        }

        public void setReviews(ArrayList<Review> reviews) {
            this.reviews = reviews;
        }
        @SerializedName("results")
        ArrayList<Review>reviews=new ArrayList<>();

    }

}