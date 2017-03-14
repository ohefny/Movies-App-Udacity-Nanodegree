package com.example.bethechange.nanomovieproject.Presenters;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bethechange.nanomovieproject.Database.MovieContract;
import com.example.bethechange.nanomovieproject.GridScreenContract;
import com.example.bethechange.nanomovieproject.Models.FavoritesList;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Models.MoviesList;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.NetworkMonitor;
import com.example.bethechange.nanomovieproject.R;
import com.example.bethechange.nanomovieproject.Retrofit.MoviesLoader;
import com.example.bethechange.nanomovieproject.Utility;
import com.example.bethechange.nanomovieproject.base.BasePresenter;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;

import java.util.ArrayList;

import static com.example.bethechange.nanomovieproject.Database.MovieContract.Base_Content_URI;
import static com.example.bethechange.nanomovieproject.Database.MovieContract.MOVIES_PATH;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class GridPresenter extends BasePresenter<MoviesList,GridScreenContract.View> implements
        GridScreenContract.LoaderActions,GridScreenContract.ListActions,NetworkMonitor.NetworkStateListener{
    MoviesLoader moviesLoader=new MoviesLoader(this);
    int pageNumber=1;
    boolean connectionError=false;
    NetworkMonitor monitor;
    Thread monitorThread;
    String sortCriteria;
    final String favSortCriteria="favorites";
    private boolean resetSelection=true;

    public GridPresenter(String sortCriteria){
        this.sortCriteria=sortCriteria;
        model=new MoviesList();
       // initNetworkMonitor();
        FavoritesList.getInstance().loadFavs();
        if(!sortCriteria.equals(favSortCriteria))
            loadMovies();
        else{
            model.setMovies(FavoritesList.getInstance().getFavList());
        }
        Uri CONTENT_URI= MovieContract.MovieEntry.CONTENT_URI;
        Log.d("GridPr",CONTENT_URI.toString());
        ContentResolver cr=MovieProjectApplication.getContext().getContentResolver();
        Cursor cursor=cr.query(CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            cursor.getColumnNames().toString();
        }

    }

    private void initNetworkMonitor() {
        monitor=new NetworkMonitor();
        monitor.registerListener(this);
        monitorThread=new Thread(monitor);
        monitorThread.start();
    }

    @Override
    public void unbindView() {
        super.unbindView();
       // monitor.unRegisterListener(this);
    }
    private void loadFavMovies(){


    }
    private void loadMovies() {
       /* if(!monitorThread.isAlive())
            initNetworkMonitor();*/
        checkNetwork();
        if(!connectionError){
            int pageN=model==null?1:model.getLoaded_pages()+1;
            moviesLoader.start(pageN, sortCriteria);
        }
    }

    private void checkNetwork() {
        if(!Utility.isNetworkAvailable()){
            connectionError=true;
            ariseError("Check Your Network Connectivity");
        }
        else {
            connectionError=false;
        }
    }


    @Override
    public void bindView(@NonNull GridScreenContract.View view) {
        super.bindView(view);
        if(model!=null)
            updateView();
    /*    if(connectionError)
            view().showError("Please Check Your Network Connectivity");*/
    }

    @Override
    protected void updateView() {
        if (view()==null)
            return;
        if(resetSelection){
            view().restSelection();
            resetSelection=false;
        }
        view().setMovies(model.getMovies());
    }
    @Override
    public void onItemClicked(int position){
       view().openDetailsScreen(model.getMovies().get(position));
    }

    @Override
    public void onLastItemLoaded() {
            loadMovies();

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

    public void setSortCriteria(String sortCriteria) {
        if(this.sortCriteria.equals(sortCriteria))
            return;
        this.sortCriteria = sortCriteria;
        pageNumber=1;
        model=new MoviesList();
        resetSelection=true;
        if(sortCriteria.equals(favSortCriteria)){
            FavoritesList.getInstance().loadFavs();
            model=new MoviesList();
            model.setMovies(FavoritesList.getInstance().getFavList());
        }
        else
            loadMovies();
        updateView();
    }

    @Override
    public void onMovieListLoaded(MoviesList moviesList) {
        if(moviesList==null){
            Log.d("MoviesLoader","moviesList is null");
            loadMovies();
            return;
        }
        if(model!=null){
            model.getMovies().addAll(moviesList.getMovies());
            model.setLoaded_pages(moviesList.getLoaded_pages());
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
        ariseError("Something Went Wrong .. Reconnecting");
        pageNumber=pageNumber>1 ? pageNumber-1:1;
    }

    private void ariseError(String msg) {
        if (view() != null)
            view().showError(msg);
    }


    @Override
    public void onNetworkStateChanged(boolean isConnected) {
        if(isConnected&&connectionError)
            loadMovies();
        if(!isConnected&&!connectionError){
            connectionError=true;
            ariseError("Check Your Network Connectivity");
        }
    }
}
