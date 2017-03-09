package xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Review;

/**
 * Created by abhay on 08/03/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private ArrayList<Review> reviewList;
    private ReviewClickListener listener;
    private Context context;

    public ReviewAdapter(Context context, ArrayList<Review> reviewList, ReviewClickListener listener){
        this.context = context;
        this.reviewList = reviewList;
        this.listener = listener;
    }

    public interface ReviewClickListener{
        void onReviewClickListener(View view, int position);
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView reviewerName;
        public TextView reviewerReview;

        public ReviewViewHolder(View view){
            super(view);
            reviewerName = (TextView) view.findViewById(R.id.reviewer_name);
            reviewerReview = (TextView) view.findViewById(R.id.reviewer_review);

            // TODO : Set clickListener to this
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onReviewClickListener(v, clickedPosition);
        }
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewerName.setText(review.getAuthor());
        holder.reviewerReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public Context getContext() {
        return context;
    }
}
