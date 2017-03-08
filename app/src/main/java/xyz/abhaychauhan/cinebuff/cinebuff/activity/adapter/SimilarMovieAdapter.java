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

/**
 * Created by abhay on 08/03/17.
 */

public class SimilarMovieAdapter extends RecyclerView.Adapter
        <SimilarMovieAdapter.SimilarMovieViewHolder>{

    private ArrayList<Movie> moviesList;
    private Context context;
    private SimilarMovieItemClickListener listener;

    public interface SimilarMovieItemClickListener{
        void onSimilarMovieItemClick(View view, int position);
    }

    public SimilarMovieAdapter(Context context, ArrayList<Movie> moviesList,
                               SimilarMovieItemClickListener listener){
        this.context = context;
        this.moviesList = moviesList;
        this.listener = listener;
    }

    public class SimilarMovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public ImageView moviePosterIv;
        public TextView movieTitleTv;
        public TextView movieRatingTv;

        public SimilarMovieViewHolder(View view){
            super(view);
            moviePosterIv = (ImageView) view.findViewById(R.id.movie_poster_iv);
            movieTitleTv = (TextView) view.findViewById(R.id.movie_title_tv);
            movieRatingTv = (TextView) view.findViewById(R.id.movie_rating_tv);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onSimilarMovieItemClick(v, clickedPosition);
        }
    }

    @Override
    public SimilarMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_movie, parent, false);
        return new SimilarMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarMovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        Picasso.with(getContext()).load(TmdbUrl.IMAGE_BASE_URL + movie.getPosterPath())
                .into(holder.moviePosterIv);

        holder.movieTitleTv.setText(movie.getTitle());
        holder.movieRatingTv.setText(Double.toString(movie.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private Context getContext(){
        return context;
    }
}