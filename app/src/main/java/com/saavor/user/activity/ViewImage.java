package com.saavor.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.saavor.user.R;

public class ViewImage extends BaseActivity implements View.OnClickListener {

    ImageView image,cancel,cross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        basicfetch();

        image= (ImageView) findViewById(R.id.view_image);
        cross= (ImageView) findViewById(R.id.cancel_img_up);
        cross.setOnClickListener(this);

        try {

            String internetUrl = "http://saavorapi.parkeee.net/"+basicInformation.getUserprofile().toString();


            load_dialog.show();
            mProgressDialog.setMessage("Loading Image");
            Glide.with(this).load(internetUrl).listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            load_dialog.cancel();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            load_dialog.cancel();
                            return false;
                        }
                    })
                    .into(image);


        } catch (Exception e) {
            //navpic.setImageResource(R.drawable.usericonpd);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
