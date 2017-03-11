package com.example.bethechange.nanomovieproject.Retrofit;

import android.util.Log;

import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.Models.MoviesList;
import com.example.bethechange.nanomovieproject.Models.Review;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BeTheChange on 3/10/2017.
 */

public class ReviewsLoader implements Callback<Review.ReviewList> {
    final static String API_KEY= MovieProjectApplication.getContext().getResources().getString(R.string.API_KEY);
    final static String BaseUrl=MovieProjectApplication.getContext().getResources().getString(R.string.BaseMoviesUrl);
    int movieID =0;
    int page=1;
    DetailsScreenContract.ReviewsLoaderActions mListener;
    public void start(int movieID) {
        this.movieID=movieID;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        TMDBAPI tmdbapi = retrofit.create(TMDBAPI.class);
        try {
            Call<Review.ReviewList> call = tmdbapi.getReviews(movieID,API_KEY,page);
            call.enqueue(this);
            Log.d(ReviewsLoader.class.getSimpleName(),call.request().url().toString());

        }
        catch (Exception ex){
            Log.d(this.getClass().getSimpleName(),ex.toString());
        }
    }

    public void setmListener(DetailsScreenContract.ReviewsLoaderActions mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResponse(Call<Review.ReviewList> call, Response<Review.ReviewList> response) {
        mListener.onReviewsListLoaded(response.body());
    }

    @Override
    public void onFailure(Call<Review.ReviewList> call, Throwable t) {
       mListener.onReviewsListFailure();
    }
}
