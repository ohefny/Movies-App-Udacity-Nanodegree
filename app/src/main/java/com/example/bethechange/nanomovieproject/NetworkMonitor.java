package com.example.bethechange.nanomovieproject;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/7/2017.
 */
public class NetworkMonitor implements Runnable{
    public static interface NetworkStateListener{
        void onNetworkStateChanged(boolean isConnected);
    }
    boolean isConnected=false;
    ArrayList<NetworkStateListener> listeners=new ArrayList<>();
    public void registerListener(NetworkStateListener listener){
        listener.onNetworkStateChanged(isConnected);
        listeners.add(listener);
    }
    public void unRegisterListener(NetworkStateListener listener){
        listeners.remove(listener);
    }
    public void tellListener(){
        for (NetworkStateListener listener:listeners) {
            listener.onNetworkStateChanged(isConnected);}
    }
    @Override
    public void run() {
        while (listeners.size()>0) {
            boolean connState = Utility.isNetworkAvailable();
            Log.d(NetworkMonitor.class.getSimpleName().toString(),String.valueOf(connState));
            if (connState != isConnected){
                isConnected=connState;
                tellListener();

            }
            if(isConnected)
                break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

