package com.example.bethechange.nanomovieproject.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/10/2017.
 */

public class Review{
    String id;
    String content  ;
    String author;


    public Review(){

    }

    public Review(String author, String content) {
        this.author=author;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthor() {
        if(author==null||author.isEmpty())
            return "Unknown Author";
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        return  (o instanceof Review) && ((Review)o).id.equals(this.id);

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