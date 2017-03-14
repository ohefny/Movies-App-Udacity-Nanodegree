package com.example.bethechange.nanomovieproject.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bethechange.nanomovieproject.Factories.GridPresenterFactory;
import com.example.bethechange.nanomovieproject.GridScreenContract;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.Presenters.GridPresenter;
import com.example.bethechange.nanomovieproject.R;
import com.example.bethechange.nanomovieproject.Utility;
import com.example.bethechange.nanomovieproject.base.BasePresenterFragment;
import com.example.bethechange.nanomovieproject.base.PresenterFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends BasePresenterFragment<GridPresenter,GridScreenContract.View> implements GridScreenContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnMoviesFragmentInteractionListener mListener;
    @BindView(R.id.moviesGrid) RecyclerView mRecyclerView;
    private ArrayList<MovieClass> mMovies=new ArrayList<>();
    private MoviesAdapter mAdapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {

        String sortStr=MovieProjectApplication.getSharedPrefrences().getString(
                MovieProjectApplication.getContext().getString(R.string.pref_sort),
                MovieProjectApplication.getContext().getString(R.string.sort_via_popularity));
        if(getPresenter()!=null){
            getPresenter().setSortCriteria(sortStr);

        }
        super.onResume();
    }

    @Override
    protected void onPresenterPrepared(@NonNull GridPresenter presenter) {
        super.onPresenterPrepared(presenter);

        if(mAdapter==null)
            mAdapter=new MoviesAdapter(mMovies);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setInterceptor(getPresenter());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this,view);
        mAdapter=new MoviesAdapter(mMovies);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), Utility.calculateNoOfColumns(getActivity()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoviesFragmentInteractionListener) {
            mListener = (OnMoviesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMoviesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    protected String tag() {
        return "GRID_PRESENTER";
    }

    @NonNull
    @Override
    protected PresenterFactory getPresenterFactory() {
        return new GridPresenterFactory();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setMovies(ArrayList<MovieClass> movies) {

        mMovies=movies;
        mAdapter.setMovies(movies);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void openDetailsScreen(MovieClass movie) {
        mListener.onMovieClicked(movie);
    }

    @Override
    public void restSelection() {
        mAdapter.restSelection();
    }


    public interface OnMoviesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMovieClicked(MovieClass movieClass);
    }
}
