package xyz.abhaychauhan.cinebuff.cinebuff.activity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.MovieDetail;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.CommonUtils;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.NetworkController;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.movie_backdrop_iv)
    ImageView backdropImageView;

    @BindView(R.id.movie_poster_iv)
    ImageView posterImageView;

    @BindView(R.id.movie_title_tv)
    TextView movieTitleTv;

    @BindView(R.id.movie_tagline_tv)
    TextView movieTaglineTv;

    @BindView(R.id.movie_votes_tv)
    TextView movieVotesTv;

    @BindView(R.id.movie_time_tv)
    TextView movieTimeTv;

    @BindView(R.id.movie_release_date_tv)
    TextView movieReleaseDateTv;

    @BindView(R.id.movie_language_tv)
    TextView movieLanguageTv;

    @BindView(R.id.movie_genres_tv)
    TextView movieGenresTv;

    @BindView(R.id.movie_rating_tv)
    TextView movieRatingTv;

    @BindView(R.id.movie_synopsis_tv)
    TextView movieSynopsisTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Retrieve movie id from intent
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movieId");

        // Generating movie api url
        String movieUrl = generateUrl(movieId);
        performJsonNetworkRequest(movieUrl);
    }

    private void displayData(MovieDetail movie) {
        Picasso.with(this).load(TmdbUrl.IMAGE_BASE_URL + movie.getBackdropPath())
                .into(backdropImageView);
        Picasso.with(this).load(TmdbUrl.IMAGE_BASE_URL + movie.getPosterPath())
                .into(posterImageView);

        movieTitleTv.setText(movie.getTitle());
        movieTaglineTv.setText(movie.getTagline());
        movieVotesTv.setText(CommonUtils.getFormattedVotes(movie.getVote()));
        movieTimeTv.setText(CommonUtils.getFormattedMovieTime(movie.getRuntime()));
        movieReleaseDateTv.setText(movie.getReleaseDate());
        movieLanguageTv.setText(CommonUtils.getFormattedString(movie.getLanguages()));
        movieGenresTv.setText(CommonUtils.getFormattedString(movie.getGenres()));
        movieRatingTv.setText(movie.getRating().toString());
        movieSynopsisTv.setText(movie.getSynopsis());

    }

    /**
     * This function return the movie url
     *
     * @param movieId
     * @return
     */
    private String generateUrl(String movieId) {
        Uri builtUri = Uri.parse(TmdbUrl.TMDB_BASE_URL + movieId).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .build();
        Log.d("Movie", "generateUrl: " + builtUri.toString());
        return builtUri.toString();
    }

    /**
     * This function retrieve data from JSON Object, then create MovieDetail object from that
     * data and then return that object
     *
     * @param data
     * @return
     */
    private MovieDetail getMovieData(JSONObject data) {
        String backdropPath = data.optString("backdrop_path");
        // Retrieving movie genres
        JSONArray genres = data.optJSONArray("genres");
        ArrayList<String> genresList = new ArrayList<>();
        for (int index = 0; index < genres.length(); index++) {
            JSONObject genre = genres.optJSONObject(index);
            String genreName = genre.optString("name");
            genresList.add(genreName);
        }

        int id = data.optInt("id");

        String title = data.optString("original_title");
        String synopsis = data.optString("overview");

        String posterPath = data.optString("poster_path");

        String releaseDate = data.optString("release_date");

        int runtime = data.optInt("runtime");

        // Retrieving languages spoken in movie
        JSONArray spokenLanguages = data.optJSONArray("spoken_languages");
        ArrayList<String> languages = new ArrayList<>();
        for (int index = 0; index < spokenLanguages.length(); index++) {
            JSONObject language = spokenLanguages.optJSONObject(index);
            String languageName = language.optString("name");
            languages.add(languageName);
        }

        String status = data.optString("status");
        String tagline = data.optString("tagline");

        Double rating = data.optDouble("vote_average");
        int votes = data.optInt("vote_count");

        MovieDetail movieDetail = new MovieDetail(backdropPath, genresList, id,
                title, synopsis, posterPath, releaseDate, runtime, languages,
                status, rating, votes, tagline);

        return movieDetail;
    }

    /**
     * This function performs network request on the api url and
     * pass the response as a parameter to getMovieData() function
     */
    private void performJsonNetworkRequest(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MovieDetail movie = getMovieData(response);
                        displayData(movie);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to fetch the data!!!");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
