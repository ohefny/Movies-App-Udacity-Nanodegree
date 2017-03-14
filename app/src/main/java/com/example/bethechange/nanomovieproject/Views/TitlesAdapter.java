package com.example.bethechange.nanomovieproject.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bethechange.nanomovieproject.DetailsScreenContract;
import com.example.bethechange.nanomovieproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BeTheChange on 3/14/2017.
 */

public class TitlesAdapter extends RecyclerView.Adapter {
    public static final int REVIEWS_LIST=1;
    public static final int TRAILERS_LIST=2;
    private LayoutInflater inflater;
    private int listType=REVIEWS_LIST;
    private Context mContext;
    DetailsScreenContract.ReviewsInteractor mRevInteractor;
    DetailsScreenContract.TrailersInteractor mTrailersInteractor;
    private ArrayList<String>titles=new ArrayList<>();

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public TitlesAdapter(Context context, ArrayList<String>titles,DetailsScreenContract.ReviewsInteractor mRevInteractor){
        this.mContext=context;
        this.mRevInteractor=mRevInteractor;
        inflater=LayoutInflater.from(mContext);
        if(titles!=null)
            this.titles=titles;
    }

    public TitlesAdapter(Context context, ArrayList<String>titles,DetailsScreenContract.TrailersInteractor mTrailersInteractor){
        this.mContext=context;
        this.mTrailersInteractor=mTrailersInteractor;
        inflater=LayoutInflater.from(mContext);
        if(titles!=null)
            this.titles=titles;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)(holder)).titleTV.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public @BindView(R.id.item_title) TextView titleTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            titleTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      if(mRevInteractor!=null)
                          mRevInteractor.onReviewClicked(getAdapterPosition());
                      else if(mTrailersInteractor!=null)
                          mTrailersInteractor.onTrailersClicked(getAdapterPosition());
                }
            });
        }

    }

}
