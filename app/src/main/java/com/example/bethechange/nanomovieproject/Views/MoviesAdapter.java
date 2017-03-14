package com.example.bethechange.nanomovieproject.Views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bethechange.nanomovieproject.GridScreenContract;
import com.example.bethechange.nanomovieproject.Models.MovieClass;
import com.example.bethechange.nanomovieproject.MovieProjectApplication;
import com.example.bethechange.nanomovieproject.Presenters.GridPresenter;
import com.example.bethechange.nanomovieproject.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import com.jakewharton.picasso.OkHttp3Downloader;

/**
 * Created by BeTheChange on 3/3/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter {
    Picasso picasso;
    static boolean firstItemSelected=false;
    ArrayList<MovieClass>movies;
    GridScreenContract.ListActions mInteractor;
    okhttp3.OkHttpClient okHttp3Client = new okhttp3.OkHttpClient();
    //OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttp3Client);

    public void setInterceptor(GridScreenContract.ListActions interceptor) {
        this.mInteractor = interceptor;

    }

    public void restSelection() {
        firstItemSelected=false;
    }

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.gridImg) ImageView gridImg;
        GridScreenContract.ListActions clickListener;

        public ImageView getGridImg() {
            return gridImg;
        }

        public ImageHolder(View itemView,GridScreenContract.ListActions clickListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            gridImg.setOnClickListener(this);
            this.clickListener=clickListener;

            if(!firstItemSelected&&MainActivity.TWO_PANEL){
                clickListener.onItemClicked(0);
                firstItemSelected=true;
            }
        }

        @Override
        public void onClick(View v) {

            clickListener.onItemClicked(getAdapterPosition());
        }
    }
    public MoviesAdapter(ArrayList<MovieClass>movies){
        this.movies=movies;
        picasso= new Picasso.Builder(MovieProjectApplication.getContext())
            //    .downloader(new com.jakewharton.picasso.OkHttp3Downloader(okHttp3Client))
                .build();

        // this.mInteractor=interactor;
    }
    public void setMovies(ArrayList<MovieClass> movies) {
        this.movies = movies;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new ImageHolder(itemView,mInteractor);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Bitmap icon = BitmapFactory.decodeResource(MovieProjectApplication.getContext().getResources(),
                R.drawable.thumbnail);

        ((ImageHolder)holder).getGridImg().setImageBitmap(icon);


        String ImgPath=movies.get(position).getFullImgPath().trim();
        try {

            picasso.load((ImgPath!=null)?ImgPath:" ").error(R.drawable.thumbnail)
                    .placeholder(R.drawable.thumbnail).fit()
                    .into(((ImageHolder)holder).getGridImg());

        }
        catch (Exception ex){
            Log.d(MoviesAdapter.class.getSimpleName(),ex.getMessage());
        }
        if(position==movies.size()-10&&mInteractor!=null){
           mInteractor.onLastItemLoaded();
        }
    }


    @Override
    public int getItemCount() {
        return (movies==null) ? 0:movies.size();
    }
}
