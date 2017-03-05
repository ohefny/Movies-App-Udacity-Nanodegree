package com.example.bethechange.nanomovieproject;

import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Models.MoviesList;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/4/2017.
 */

public interface DetailsScreenContract {
    interface View{
        void showError(String msg);
        void setMovieDetails(MovieClass movieDetails);
    }
    interface LoaderActions{
        void onReviewsListLoaded(ArrayList<MovieClass.Review> reviews);
        void onReviewsListFailure();

    }

}
