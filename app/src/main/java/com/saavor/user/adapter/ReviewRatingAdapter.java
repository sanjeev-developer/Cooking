package com.saavor.user.adapter;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.resource.drawable.GlideDrawable;
        import com.bumptech.glide.request.RequestListener;
        import com.bumptech.glide.request.target.Target;
        import com.saavor.user.Model.ReviewList;
        import com.saavor.user.R;
        import java.util.ArrayList;


public class ReviewRatingAdapter extends RecyclerView.Adapter<ReviewRatingAdapter.MyViewHolder> {

    private ArrayList<ReviewList> reviewdata;
    private Context context;
    int count = 0 ;

    public ReviewRatingAdapter(Context contexts, ArrayList<ReviewList> dishname) {
        this.reviewdata = dishname;
        this.context=contexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewratinglayout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.rating_name.setText(reviewdata.get(position).getUserName().toString());

        if(reviewdata.get(position).getDays()>1)
        {
            holder.rating_ago.setText(reviewdata.get(position).getDays()+" Day's Ago");
        }
        else
        {
            holder.rating_ago.setText(reviewdata.get(position).getDays()+" Day Ago");
        }

        holder.rating_desc.setText(reviewdata.get(position).getReviews().toString());

        try {

            String internetUrl = "http://saavorapi.parkeee.net/" +reviewdata.get(position).getProfilePicPath();

            Glide.with(context).load(internetUrl).error(R.drawable.usericonpd).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                    holder.review_image.setImageResource(R.drawable.usericonpd);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                  //  holder.review_image.setImageResource(R.drawable.usericonpd);
                    return false;
                }
            })
                    .into(holder.review_image);


        } catch (Exception e) {
            holder.review_image.setImageResource(R.drawable.usericonpd);
        }

    int star=reviewdata.get(position).getStars();
        switch(star)
        {
            case 0:
            {
                holder.heart1.setBackgroundResource(R.drawable.greyheart);
                holder.heart2.setBackgroundResource(R.drawable.greyheart);
                holder.heart3.setBackgroundResource(R.drawable.greyheart);
                holder.heart4.setBackgroundResource(R.drawable.greyheart);
                holder.heart5.setBackgroundResource(R.drawable.greyheart);
            }
            break;

            case 1:
            {
                holder.heart1.setBackgroundResource(R.drawable.redreview);
                holder.heart2.setBackgroundResource(R.drawable.greyheart);
                holder.heart3.setBackgroundResource(R.drawable.greyheart);
                holder.heart4.setBackgroundResource(R.drawable.greyheart);
                holder.heart5.setBackgroundResource(R.drawable.greyheart);
            }
            break;

            case 2:
            {
                holder.heart1.setBackgroundResource(R.drawable.redreview);
                holder.heart2.setBackgroundResource(R.drawable.redreview);
                holder.heart3.setBackgroundResource(R.drawable.greyheart);
                holder.heart4.setBackgroundResource(R.drawable.greyheart);
                holder.heart5.setBackgroundResource(R.drawable.greyheart);
            }
            break;

            case 3:
            {
                holder.heart1.setBackgroundResource(R.drawable.redreview);
                holder.heart2.setBackgroundResource(R.drawable.redreview);
                holder.heart3.setBackgroundResource(R.drawable.redreview);
                holder.heart4.setBackgroundResource(R.drawable.greyheart);
                holder.heart5.setBackgroundResource(R.drawable.greyheart);
            }
            break;

            case 4:
            {
                holder.heart1.setBackgroundResource(R.drawable.redreview);
                holder.heart2.setBackgroundResource(R.drawable.redreview);
                holder.heart3.setBackgroundResource(R.drawable.redreview);
                holder.heart4.setBackgroundResource(R.drawable.redreview);
                holder.heart5.setBackgroundResource(R.drawable.greyheart);
            }
            break;

            case 5:
            {
                holder.heart1.setBackgroundResource(R.drawable.redreview);
                holder.heart2.setBackgroundResource(R.drawable.redreview);
                holder.heart3.setBackgroundResource(R.drawable.redreview);
                holder.heart4.setBackgroundResource(R.drawable.redreview);
                holder.heart5.setBackgroundResource(R.drawable.redreview);
            }
            break;
        }

    }

    @Override
    public int getItemCount() {
        return reviewdata.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rating_name,rating_ago,rating_desc;
        ImageView heart1,heart2,heart3,heart4,heart5,review_image;

        public MyViewHolder(View view) {
            super(view);

            review_image= (ImageView) view.findViewById(R.id.review_image);
            heart1= (ImageView) view.findViewById(R.id.rr_heart1);
            heart2= (ImageView) view.findViewById(R.id.rr_heart2);
            heart3= (ImageView) view.findViewById(R.id.rr_heart3);
            heart4= (ImageView) view.findViewById(R.id.rr_heart4);
            heart5= (ImageView) view.findViewById(R.id.rr_heart5);
            rating_name=(TextView)view.findViewById(R.id.txt_name_rating);
            rating_ago=(TextView)view.findViewById(R.id.txt_left_rating);
            rating_desc=(TextView)view.findViewById(R.id.txt_description_rating);

        }
    }
}