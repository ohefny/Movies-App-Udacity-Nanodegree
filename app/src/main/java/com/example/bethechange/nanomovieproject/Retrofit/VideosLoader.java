package com.example.bethechange.nanomovieproject.Retrofit;

import android.util.Log;

import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.Models.Review;
import com.example.bethechange.nanomovieproject.Models.VideoInfo;
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

public class VideosLoader implements Callback<VideoInfo.VideosInfoList> {
    final static String API_KEY= MovieProjectApplication.getContext().getResources().getString(R.string.API_KEY);
    final static String BaseUrl=MovieProjectApplication.getContext().getResources().getString(R.string.BaseMoviesUrl);
    DetailsScreenContract.VideosLoaderActions mListener;
    int movieID;
    public void setListener(DetailsScreenContract.VideosLoaderActions mLoaderActions) {
        this.mListener = mLoaderActions;
    }
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
            Call<VideoInfo.VideosInfoList> call = tmdbapi.getVideos(movieID,API_KEY);
            call.enqueue(this);
            Log.d(VideosLoader.class.getSimpleName(),call.request().url().toString());

        }
        catch (Exception ex){
            Log.d(this.getClass().getSimpleName(),ex.toString());
        }
    }
    @Override
    public void onResponse(Call<VideoInfo.VideosInfoList> call, Response<VideoInfo.VideosInfoList> response) {
        mListener.onVideosLoaded(response.body());
    }

    @Override
    public void onFailure(Call<VideoInfo.VideosInfoList> call, Throwable t) {
       mListener.OnVideosLoadFailure();
    }
}
