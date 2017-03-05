package com.example.bethechange.nanomovieproject.Factories;

import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.Presenters.GridPresenter;
import com.example.bethechange.nanomovieproject.R;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;

/**
 * Created by BeTheChange on 3/2/2017.
 */
public class GridPresenterFactory implements PresenterFactory<GridPresenter> {
    @Override
    public GridPresenter create() {
        return new GridPresenter(MovieProjectApplication.getSharedPrefrences().getString(
                MovieProjectApplication.getContext().getString(R.string.pref_sort),
                MovieProjectApplication.getContext().getString(R.string.sort_via_popularity)));
    }

}
