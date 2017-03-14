package com.example.bethechange.nanomovieproject.Views;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMoviesFragmentInteractionListener {

    public static boolean TWO_PANEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        MovieProjectApplication.getSharedPrefrences().getString(getResources().getString(R.string.sort_by)
                ,getResources().getString(R.string.sort_via_popularity));
        if (findViewById(R.id.details_container) != null) {
            TWO_PANEL = true;
            if (savedInstanceState == null) {
                DetailsFragment detailsFragment=DetailsFragment.newInstance("");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();
            }

        } else {
            TWO_PANEL = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onMovieClicked(MovieClass movieClass) {
        if(TWO_PANEL){
            DetailsFragment detailsFragment=DetailsFragment.newInstance(new Gson().toJson(movieClass));
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.details_container,detailsFragment).commit();

        }
        else{
            Intent intent=new Intent(this,DetailsActivity.class);
            intent.putExtra(DetailsActivity.Movie_ARG,new Gson().toJson(movieClass));
            startActivity(intent);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
