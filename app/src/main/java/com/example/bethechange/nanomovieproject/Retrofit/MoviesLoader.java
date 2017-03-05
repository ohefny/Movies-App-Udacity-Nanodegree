package com.example.bethechange.nanomovieproject.Retrofit;

import android.util.Log;

import com.example.bethechange.nanomovieproject.GridScreenContract;
import com.example.bethechange.nanomovieproject.Models.MoviesList;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class MoviesLoader implements Callback<MoviesList> {

    final static String API_KEY=MovieProjectApplication.getContext().getResources().getString(R.string.API_KEY);
    final static String BaseUrl=MovieProjectApplication.getContext().getResources().getString(R.string.BaseMoviesUrl);
    int pageNo=1;
    String sortCriteria;
    GridScreenContract.LoaderActions mListener;

    public MoviesLoader(GridScreenContract.LoaderActions interactor){
        mListener=interactor;
    }
    public void start(int pageNumber,String sortCriteria) {
        pageNo=pageNumber;
        this.sortCriteria=sortCriteria;
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
            Call<MoviesList> call = tmdbapi.getMovies(pageNumber,API_KEY,sortCriteria);
            call.enqueue(this);
            Log.d(MoviesLoader.class.getSimpleName(),call.request().url().toString());

        }
         catch (Exception ex){
             Log.d(this.getClass().getSimpleName(),ex.toString());
         }
    }
    @Override
    public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {

        Log.d(MoviesLoader.class.getSimpleName(),String.valueOf(response.isSuccessful()));

      mListener.onMovieListLoaded(response.body());
    }

    @Override
    public void onFailure(Call<MoviesList> call, Throwable t) {
       mListener.onMovieListFailure();
       start(pageNo,sortCriteria);
        Log.d(MoviesLoader.class.getSimpleName(),"false");
    }
}
