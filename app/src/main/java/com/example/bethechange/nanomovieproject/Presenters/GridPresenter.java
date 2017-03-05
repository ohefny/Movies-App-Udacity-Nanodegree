package com.example.bethechange.nanomovieproject.Presenters;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bethechange.nanomovieproject.GridScreenContract;
import com.example.bethechange.nanomovieproject.Models.MoviesList;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.R;
import com.example.bethechange.nanomovieproject.Retrofit.MoviesLoader;
import com.example.bethechange.nanomovieproject.base.BasePresenter;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class GridPresenter extends BasePresenter<MoviesList,GridScreenContract.View> implements GridScreenContract.LoaderActions,GridScreenContract.ListActions{
    MoviesLoader moviesLoader=new MoviesLoader(this);
    int pageNumber=1;


    String sortCriteria;
    public GridPresenter(String sortCriteria){
        this.sortCriteria=sortCriteria+".desc";
        loadMovies(pageNumber, this.sortCriteria);

    }

    private void loadMovies(int pageNumber, String sortCriteria) {
        moviesLoader.start(pageNumber, sortCriteria);
    }

    @Override
    public void bindView(@NonNull GridScreenContract.View view) {
        super.bindView(view);
        if(model!=null)
            updateView();
    }

    @Override
    protected void updateView() {
             view().setMovies(model.getMovies());
    }
    @Override
    public void onItemClicked(int position){
       view().openDetailsScreen(model.getMovies().get(position));
    }

    @Override
    public void onLastItemLoaded() {
       if(pageNumber<model.getTotal_pages()){
            pageNumber++;
            loadMovies(pageNumber,sortCriteria);
        }
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void onSortCriteriaChanged(String sortCriteria) {
        this.sortCriteria = sortCriteria;
        loadMovies(pageNumber, sortCriteria);
    }

    @Override
    public void onMovieListLoaded(MoviesList moviesList) {
        if(moviesList==null){
            Log.d("MoviesLoader","moviesList is null");
            loadMovies(pageNumber,sortCriteria);
            return;
        }
        if(model!=null){
            model.getMovies().addAll(moviesList.getMovies());
            Log.d("MoviesLoader","Model isn't null");
        }
        else{
            setModel(moviesList);
            Log.d("MoviesLoader","Model is null");
        }
        if(view()!=null) {
            updateView();
            Log.d("MoviesLoader","View isn't null");
        }
    }

    @Override
    public void onMovieListFailure() {
        if(view()!=null)
            view().showError("Please Check Your Network Connectivity");
    }


}
