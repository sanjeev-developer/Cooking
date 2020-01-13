package com.saavor.user.Classes;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.saavor.user.R;


public class InputReviewRating extends DialogFragment implements View.OnClickListener {

    Button cancel,post;
    private RatingBar ratingBar;
    View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.reviewratingdialog, container, false);

//        cancel= (Button) layout.findViewById(R.id.but_rr_cancel);
//        post= (Button) layout.findViewById(R.id.but_rr_post);

        cancel.setOnClickListener(this);
        post.setOnClickListener(this);



        return layout;
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId()){

//            case R.id.but_rr_cancel:
//
//                getDialog().dismiss();
//
//                break;
//
//            case R.id.but_rr_post:
//
//                break;

        }
}


}
