package com.example.bethechange.nanomovieproject.Models;

import com.example.bethechange.nanomovieproject.Database.MovieDBHelper;
import com.example.bethechange.nanomovieproject.GridScreenContract;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/9/2017.
 */

public class FavoritesList {
    ArrayList<MovieClass> favList=new ArrayList<>();
    int numOfMovies=0;
    boolean loaded;
    MovieDBHelper movieDBHelper=new MovieDBHelper(MovieProjectApplication.getContext());

    private FavoritesList(){
    }
    private static FavoritesList ourInstance = new FavoritesList();

    public static FavoritesList getInstance() {
        return ourInstance;
    }

    public void setFavList(ArrayList<MovieClass> favList) {
        this.favList = favList;
    }

    public ArrayList<MovieClass> getFavList() {
        return favList;
    }
    public boolean isLoaded() {
        return loaded;
    }
    public void loadFavs(GridScreenContract.LoaderActions mListener){
        MoviesList moviesList=new MoviesList();
        if(loaded){
            moviesList.setMovies(favList);
            mListener.onMovieListLoaded(moviesList);
            return;
        }
        favList=movieDBHelper.loadFavoritesMovies();
        moviesList.setMovies(favList);
        if(favList!=null){
            loaded=true;
            numOfMovies=favList.size();
        }
        mListener.onMovieListLoaded(moviesList);
    }
    public void loadFavs(){

        if(loaded){
            return;
        }
        favList=movieDBHelper.loadFavoritesMovies();
        if(favList!=null){
            loaded=true;
            numOfMovies=favList.size();
        }

    }
    public void updateFavs(MovieClass updatedMovie){
        if(favList.size()>numOfMovies){
            if(movieDBHelper.InsertMovie(updatedMovie))
                numOfMovies++;
            else // adding to database failed so retrieve the original fav list
                favList.remove(updatedMovie);

        }
        else if (favList.size()<numOfMovies){
            if (movieDBHelper.DeleteMovie(updatedMovie.getId()))
                numOfMovies--;
            else // removing from database failed so retrieve the original fav list
                favList.add(updatedMovie);
        }
    }

}
