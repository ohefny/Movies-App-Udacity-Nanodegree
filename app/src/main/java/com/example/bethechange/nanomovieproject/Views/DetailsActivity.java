package com.example.bethechange.nanomovieproject.Views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.bethechange.nanomovieproject.R;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
    public final static String Movie_ARG="MOVIE_INFO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if(savedInstanceState==null){
            DetailsFragment detailsFragment=DetailsFragment.newInstance(getIntent().getStringExtra(Movie_ARG));
            getSupportFragmentManager().beginTransaction().add(R.id.details_container,detailsFragment).commit();
        }
    }

}
