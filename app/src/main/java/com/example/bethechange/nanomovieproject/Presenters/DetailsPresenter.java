package com.example.bethechange.nanomovieproject.Presenters;

import android.support.annotation.NonNull;

import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.base.BasePresenter;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class DetailsPresenter extends BasePresenter<MovieClass,DetailsScreenContract.View> {

    public DetailsPresenter(MovieClass movieClass) {
        super();
        model=movieClass;
    }

    @Override
    protected void updateView() {
         view().setMovieDetails(model);
    }

    @Override
    public void bindView(@NonNull DetailsScreenContract.View view) {
        super.bindView(view);
        if(model!=null)
            updateView();
    }
}
