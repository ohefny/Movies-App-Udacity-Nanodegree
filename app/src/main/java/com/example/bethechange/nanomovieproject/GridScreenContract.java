package com.example.bethechange.nanomovieproject;

import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Models.MoviesList;

import java.util.ArrayList;

/**
 * Created by Hefny on 2/21/2017.
 */

public interface GridScreenContract {
     interface View{
         void showError(String msg);
         void setMovies(ArrayList<MovieClass>movies);
         void openDetailsScreen(MovieClass movie);
         void restSelection();
     }
    interface LoaderActions{
          void onMovieListLoaded(MoviesList moviesList);
          void onMovieListFailure();

    }
    interface ListActions{
        void onItemClicked(int pos);
        void onLastItemLoaded();

    }
}
