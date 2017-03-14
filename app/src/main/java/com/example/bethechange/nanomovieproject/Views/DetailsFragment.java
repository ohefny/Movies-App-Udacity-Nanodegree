package com.example.bethechange.nanomovieproject.Views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bethechange.nanomovieproject.Database.MovieContract;
import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.Factories.DetailsPresenterFactory;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Models.Review;
import com.example.bethechange.nanomovieproject.Models.VideoInfo;
import com.example.bethechange.nanomovieproject.Presenters.DetailsPresenter;
import com.example.bethechange.nanomovieproject.R;
import com.example.bethechange.nanomovieproject.base.BasePresenter;
import com.example.bethechange.nanomovieproject.base.BasePresenterFragment;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends BasePresenterFragment<DetailsPresenter,DetailsScreenContract.View> implements DetailsScreenContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOVIE_ARG = "MOVIE-INFO";
    public static String MOVIE_TITLE="About Movie";
    private String movieJson="";
    @BindView(R.id.title) TextView title;
    @BindView(R.id.year) TextView year;
    @BindView(R.id.rate)TextView rate;
    @BindView(R.id.vote_count)TextView vote_count;
    @BindView(R.id.overview)TextView overview;
    @BindView(R.id.poster)ImageView poster;
    @BindView(R.id.fav)CheckBox favCheckBox;
    @BindView(R.id.trailerssList)RecyclerView trailersList;
    @BindView(R.id.reviewsList)RecyclerView reviewsList;
    TitlesAdapter trailersADP;
    TitlesAdapter reviewsADP;
    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String selectedMovie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_ARG, selectedMovie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MOVIE_ARG, movieJson);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(savedInstanceState==null)
        {
         if(getArguments().getString(MOVIE_ARG)==null)
             movieJson="";
         else
             movieJson=getArguments().getString(MOVIE_ARG).toString();
        }
       else
            movieJson=savedInstanceState.getString(MOVIE_ARG).toString();


    }

    @Override
    protected void onPresenterPrepared(@NonNull DetailsPresenter presenter) {
        super.onPresenterPrepared(presenter);
        trailersADP=new TitlesAdapter(getContext(), null,(DetailsScreenContract.TrailersInteractor)getPresenter());
        reviewsADP=new TitlesAdapter(getContext(),null,(DetailsScreenContract.ReviewsInteractor)getPresenter());
        trailersList.setAdapter(trailersADP);
        reviewsList.setAdapter(reviewsADP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this,view);
        favCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onFavoriteClicked(favCheckBox.isChecked());
            }
        });
        trailersList.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @NonNull
    @Override
    protected String tag() {
        return null;
    }

    @NonNull
    @Override
    protected PresenterFactory getPresenterFactory() {
        PresenterFactory p= new DetailsPresenterFactory(movieJson);
        return p;
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void setMovieDetails(MovieClass movieDetails) {
        if(movieDetails==null)
            return;
        MOVIE_TITLE=movieDetails.getTitle();
        ((AppCompatActivity)getActivity())
                .getSupportActionBar().setTitle(movieDetails.getTitle());
        title.setText(movieDetails.getTitle());
        year.setText(movieDetails.getRelease_date().toString());
        rate.setText(String.valueOf(movieDetails.getVote_average()) +" / 10");
        vote_count.setText(String.valueOf(movieDetails.getVote_count()) +" Votes");
        overview.setText(String.valueOf(movieDetails.getOverview()));
        Picasso.with(getActivity()).load(movieDetails.getFullImgPath())
                .placeholder(R.drawable.thumbnail).error(R.drawable.thumbnail).fit()
                .into(poster);
       // poster.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void updateReviews(final ArrayList<Review> reviews) {
      // ViewGroup.LayoutParams params=reviewsList.getLayoutParams();
       // params.height=reviews.size()*50;
      //  reviewsList.setLayoutParams(params);
        reviewsList.setMinimumHeight(reviews.size()*50);
        reviewsADP.setTitles(getReviewsNames(reviews));
        reviewsADP.notifyDataSetChanged();
    }
    @Override
    public void updateTrailers(ArrayList<VideoInfo> videos) {
        trailersList.setMinimumHeight(videos.size()*50);
        trailersADP.setTitles(getTrailersNames(videos));
        trailersADP.notifyDataSetChanged();
    }

    @Override
    public void openReviewFragment(Review review) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_container,new ReviewDetailsFragment(review)).addToBackStack(null).commit();
    }

    @Override
    public void openYoutube(VideoInfo videoInfo) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoInfo.getFullLink())));
    }

    private ArrayList<String> getReviewsNames(ArrayList<Review> reviews) {
        ArrayList<String>ReviewAuthors=new ArrayList<String>();
        for (Review r: reviews) {
            ReviewAuthors.add(r.getAuthor());
        }
        return ReviewAuthors;
    }
    private ArrayList<String> getTrailersNames(ArrayList<VideoInfo> trailers) {
        ArrayList<String>TrailersNames=new ArrayList<String>();
        for (VideoInfo r: trailers) {
            TrailersNames.add(r.getName());
        }
        return TrailersNames;
    }



    @Override
    public void setFavorite(boolean isFav) {
        favCheckBox.setChecked(isFav);
    }
}
