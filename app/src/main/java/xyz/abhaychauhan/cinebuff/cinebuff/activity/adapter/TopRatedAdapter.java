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

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedMovieHolder> {

    private Context context;
    private ArrayList<Movie> movies;
    private OnItemClickListener listener;

    public TopRatedAdapter(Context context, ArrayList<Movie> movies,
                           OnItemClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class TopRatedMovieHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView moviePosterImageView;
        public TextView movieTitleTextView;

        public TopRatedMovieHolder(View view) {
            super(view);
            moviePosterImageView = (ImageView) view.findViewById(R.id.movie_poster_iv);
            movieTitleTextView = (TextView) view.findViewById(R.id.movie_title_tv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onItemClick(v, clickedPosition);
        }
    }

    @Override
    public TopRatedMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.card_top_rated, parent, false);
        return new TopRatedMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(TopRatedMovieHolder holder, int position) {
        Movie movie = movies.get(position);

        Picasso.with(getContext()).load(TmdbUrl.IMAGE_BASE_URL + movie.getPosterPath()).
                into(holder.moviePosterImageView);
        holder.movieTitleTextView.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private Context getContext() {
        return this.context;
    }
}
