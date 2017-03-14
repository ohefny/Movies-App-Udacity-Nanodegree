package com.example.bethechange.nanomovieproject;

import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Models.MoviesList;
import com.example.bethechange.nanomovieproject.Models.Review;
import com.example.bethechange.nanomovieproject.Models.VideoInfo;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/4/2017.
 */

public interface DetailsScreenContract {
    interface View{
        void showError(String msg);
        void setMovieDetails(MovieClass movieDetails);
        void updateReviews(ArrayList<Review>reviews);
        void updateTrailers(ArrayList<VideoInfo>videos);
        void openReviewFragment(Review review);
        void openYoutube(VideoInfo videoInfo);
        void setFavorite(boolean isFav);
    }
    interface ReviewsLoaderActions{
        void onReviewsListLoaded(Review.ReviewList list);
        void onReviewsListFailure();

    }
    interface VideosLoaderActions{
        void onVideosLoaded(VideoInfo.VideosInfoList list);
        void OnVideosLoadFailure();

    }
    interface ReviewsInteractor{
        void onReviewClicked(int pos);
    }
    interface TrailersInteractor{
        void onTrailersClicked(int pos);
    }


}
