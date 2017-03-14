package com.example.bethechange.nanomovieproject.Presenters;

import android.support.annotation.NonNull;

import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.Models.FavoritesList;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Models.Review;
import com.example.bethechange.nanomovieproject.Models.VideoInfo;
import com.example.bethechange.nanomovieproject.Retrofit.ReviewsLoader;
import com.example.bethechange.nanomovieproject.Retrofit.VideosLoader;
import com.example.bethechange.nanomovieproject.Utility;
import com.example.bethechange.nanomovieproject.base.BasePresenter;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class DetailsPresenter extends BasePresenter<MovieClass,DetailsScreenContract.View> implements
        DetailsScreenContract.ReviewsLoaderActions,DetailsScreenContract.VideosLoaderActions,DetailsScreenContract.ReviewsInteractor
        ,DetailsScreenContract.TrailersInteractor{
    boolean isFav;
    ReviewsLoader reviewsLoader;
    VideosLoader videosLoader;
    private boolean connectionError;

    public DetailsPresenter(MovieClass movieClass, ReviewsLoader reviewsLoader, VideosLoader videosLoader) {
        super();
        model=movieClass;
        this.reviewsLoader=reviewsLoader;
        this.videosLoader=videosLoader;
        reviewsLoader.setmListener(this);
        videosLoader.setListener(this);
        if(FavoritesList.getInstance().getFavList().indexOf(model)>=0){
            isFav=true;
        }
        checkNetwork();
        if(!connectionError)
        {
            videosLoader.start(movieClass.getId());
            reviewsLoader.start(movieClass.getId());
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

    private void ariseError(String s) {
        if(view()!=null)
            view().showError(s);
    }

    @Override
    protected void updateView() {
        if(model==null)
            return;
        view().setMovieDetails(model);
        view().setFavorite(isFav);
        view().updateReviews(model.getReviews());
        view().updateTrailers(model.getVideosInfo());

    }

    @Override
    public void bindView(@NonNull DetailsScreenContract.View view) {
        super.bindView(view);
        if(model!=null)
            updateView();

    }
    public void onFavoriteClicked(boolean isFav){
        if(isFav){
            FavoritesList.getInstance().getFavList().add(model);
        }
        else {
            FavoritesList.getInstance().getFavList().remove(model);
        }
        this.isFav=isFav;
    }

    @Override
    public void unbindView() {
        super.unbindView();
        FavoritesList.getInstance().updateFavs(model);
    }

    private void updateFavoritesList() {
    }

    @Override
    public void onReviewsListLoaded(Review.ReviewList list) {
          model.setReviews(list.getReviews());
        if(view()!=null)
            view().updateReviews(list.getReviews());
    }

    @Override
    public void onReviewsListFailure() {
           ariseError("Unable To Fetch Movie Reviews");
    }

    @Override
    public void onVideosLoaded(VideoInfo.VideosInfoList list) {
         model.setVideosInfo(list.getVideoInfoArrayList());
        if(view()!=null)
            view().updateTrailers(list.getVideoInfoArrayList());

    }

    @Override
    public void OnVideosLoadFailure() {
         ariseError("Unable To Fetch Movie Trailers");
    }

    @Override
    public void onReviewClicked(int pos) {
             if (view()!=null)
                 view().openReviewFragment(model.getReviews().get(pos));
    }

    @Override
    public void onTrailersClicked(int pos) {
        if (view()!=null)
            view().openYoutube(model.getVideosInfo().get(pos));

    }
}
