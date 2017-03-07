package com.example.bethechange.nanomovieproject.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.R;

import java.util.Set;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
   ListPreference mListPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        Preference pref=findPreference(getResources().getString(R.string.pref_sort));
        if(pref instanceof ListPreference) {
            mListPreference = ((ListPreference) pref);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListPreference.setOnPreferenceChangeListener(this);
              //  .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mListPreference.setOnPreferenceChangeListener(null);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        //mListPreference.set
       // preference.setDefaultValue(newValue.toString());
      /*  */

        Log.d("Entryss",((ListPreference)preference).getValue().toString());
       // Log.d("Entry",mListPreference.getEntry().toString());
        ((ListPreference)preference).setValue(newValue.toString());
        MovieProjectApplication.getSharedPrefrences().edit().putString(
                MovieProjectApplication.getContext().getString(R.string.pref_sort),newValue.toString()
        ).commit();
        Log.d(SettingsFragment.class.getSimpleName(),newValue.toString());
        return false;
    }
}



