package xyz.abhaychauhan.cinebuff.cinebuff.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.models.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.utils.tmDbUrl;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {


    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        Movie movie = bundle.getParcelable("movie");

        //Poster
        ImageView moviePosterView = (ImageView) rootView.findViewById(R.id.movie_poster_view);
        Picasso.with(getContext()).load(tmDbUrl.MOVIE_POSTER_VERTICAL_BASE_URL + movie.getPosterPath())
                .into(moviePosterView);

        //Title
        TextView movieTitleView = (TextView) rootView.findViewById(R.id.movie_title_view);
        movieTitleView.setText(movie.getTitle());

        //Rating
        TextView movieRatingView = (TextView) rootView.findViewById(R.id.movie_rating_view);
        movieRatingView.setText(movie.getVoteAverage().toString());

        //Popularity
        TextView moviePopularityView = (TextView) rootView.findViewById(R.id.movie_popularity_view);
        moviePopularityView.setText(movie.getPopularity().toString());

        //Language
        TextView movieLanguageView = (TextView) rootView.findViewById(R.id.movie_language_view);
        movieLanguageView.setText(movie.getLanguage());

        //Overview
        TextView movieOverview = (TextView) rootView.findViewById(R.id.movie_overview_view);
        movieOverview.setText(movie.getOverView());

        return rootView;
    }

}
