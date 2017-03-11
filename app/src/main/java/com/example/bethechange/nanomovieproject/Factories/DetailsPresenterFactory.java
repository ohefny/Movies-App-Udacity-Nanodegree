package com.example.bethechange.nanomovieproject.Factories;

import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Presenters.DetailsPresenter;
import com.example.bethechange.nanomovieproject.Retrofit.ReviewsLoader;
import com.example.bethechange.nanomovieproject.Retrofit.VideosLoader;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;
import com.google.gson.Gson;

/**
 * Created by BeTheChange on 3/4/2017.
 */

public class DetailsPresenterFactory implements PresenterFactory<DetailsPresenter>{
    MovieClass movieClass;
    public DetailsPresenterFactory(String movieJson){
        Gson gson=new Gson();
        movieClass=gson.fromJson(movieJson,MovieClass.class);
    }
    @Override
    public DetailsPresenter create() {
        return new DetailsPresenter(movieClass,new ReviewsLoader(),new VideosLoader());
    }
}
