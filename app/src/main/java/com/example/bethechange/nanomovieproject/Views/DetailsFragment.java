package com.example.bethechange.nanomovieproject.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.Factories.DetailsPresenterFactory;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.Presenters.DetailsPresenter;
import com.example.bethechange.nanomovieproject.R;
import com.example.bethechange.nanomovieproject.base.BasePresenter;
import com.example.bethechange.nanomovieproject.base.BasePresenterFragment;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends BasePresenterFragment<DetailsPresenter,DetailsScreenContract.View> implements DetailsScreenContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOVIE_ARG = "MOVIE-INFO";
    private String movieJson="";
    @BindView(R.id.title) TextView title;
    @BindView(R.id.year) TextView year;
    @BindView(R.id.rate)TextView rate;
    @BindView(R.id.vote_count)TextView vote_count;
    @BindView(R.id.overview)TextView overview;
    @BindView(R.id.poster)ImageView poster;


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
            movieJson=getArguments().getString(MOVIE_ARG).toString() ==null ? "" : getArguments().getString(MOVIE_ARG).toString();
       else
            movieJson=savedInstanceState.getString(MOVIE_ARG).toString();
        movieJson=movieJson;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this,view);
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
        return new DetailsPresenterFactory(movieJson);
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void setMovieDetails(MovieClass movieDetails) {
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
}
