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
import com.example.bethechange.nanomovieproject.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMoviesFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onMovieClicked(MovieClass movieClass) {
        Intent intent=new Intent(this,DetailsActivity.class);
        intent.putExtra(DetailsActivity.Movie_ARG,new Gson().toJson(movieClass));
        startActivity(intent);
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
