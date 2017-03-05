package com.example.bethechange.nanomovieproject.Views;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMoviesFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onMovieClicked(MovieClass movieClass) {
        Intent intent=new Intent(this,DetailsActivity.class);
        intent.putExtra(DetailsActivity.Movie_ARG,new Gson().toJson(movieClass));
        startActivity(intent);
    }
}
