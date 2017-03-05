package com.example.bethechange.nanomovieproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BeTheChange on 3/2/2017.
 */

public class MovieProjectApplication extends Application {
    private static MovieProjectApplication mInstance;
    static SharedPreferences mPref;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        mInstance.initializeInstance();
    }

    private void initializeInstance() {
        mPref = this.getApplicationContext().getSharedPreferences("pref_key", MODE_PRIVATE);
    }
    public static SharedPreferences getSharedPrefrences(){
        return mPref;
    }
    public static Context getContext(){
        return mInstance;
    }
}
