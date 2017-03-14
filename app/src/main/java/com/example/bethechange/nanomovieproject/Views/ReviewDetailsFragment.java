package com.example.bethechange.nanomovieproject.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bethechange.nanomovieproject.Models.Review;
import com.example.bethechange.nanomovieproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewDetailsFragment extends Fragment {
    Review mReview;
    @BindView(R.id.reviewContetnTV)TextView revContent;
    public ReviewDetailsFragment(Review mReview) {
        this.mReview=mReview;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review_details, container, false);
        ButterKnife.bind(this,view);
        revContent.setText(mReview.getContent());
        return view;
    }


}
