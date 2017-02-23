package xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ArrayList<Movie> moviesList;
    private Context context;
    OnItemClickListener mItemClickListener;

    public PopularMovieAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView coverPosterImageView;
        public TextView titleTextView;
        public TextView overviewTextView;
        public TextView votesTextView;
        public TextView voteAverageTextView;

        public PopularMovieViewHolder(View itemView) {
            super(itemView);
            coverPosterImageView = (ImageView) itemView.findViewById(R.id.movie_cover_iv);
            titleTextView = (TextView) itemView.findViewById(R.id.title_tv);
            overviewTextView = (TextView) itemView.findViewById(R.id.overview_tv);
            votesTextView = (TextView) itemView.findViewById(R.id.votes_count_tv);
            voteAverageTextView = (TextView) itemView.findViewById(R.id.votes_average_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    @Override
    public PopularMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.card_popular_movie, parent, false);
        return new PopularMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularMovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        Picasso.with(getContext()).load(TmdbUrl.IMAGE_BASE_URL + movie.getPosterPath()).
                into(holder.coverPosterImageView);
        holder.votesTextView.setText(movie.getVotesCount() + " votes");
        holder.voteAverageTextView.setText(movie.getVoteAverage() + "/10");
        holder.titleTextView.setText(movie.getTitle());
        holder.overviewTextView.setText(movie.getOverview());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private Context getContext() {
        return this.context;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
