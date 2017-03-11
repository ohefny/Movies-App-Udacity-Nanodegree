package com.example.bethechange.nanomovieproject.Models;

import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 3/10/2017.
 */
public class VideoInfo{
    String key;
    String name;
    String fullLink;


    String id;
    final static String BASE_YOUTUBE_LINK= MovieProjectApplication.getContext().getResources().getString(R.string.BASE_YOUTUBE_LINK);
    public VideoInfo(){
    }



    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
        this.fullLink= BASE_YOUTUBE_LINK+key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof VideoInfo){
            return  ((VideoInfo)o).id.equals(this.id);
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getFullLink(){
        return fullLink;


    }
    public static class VideosInfoList{

       @SerializedName("results")
        ArrayList<VideoInfo>videoInfoArrayList=new ArrayList<>();

        public ArrayList<VideoInfo> getVideoInfoArrayList() {
            return videoInfoArrayList;
        }

        public void setVideoInfoArrayList(ArrayList<VideoInfo> videoInfoArrayList) {
            this.videoInfoArrayList = videoInfoArrayList;
        }
    }

}