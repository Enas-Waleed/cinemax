package xyz.abhaychauhan.cinebuff.cinebuff.activity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.MovieDetail;
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
        // Retrieve movie id from intent
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movie_id");

        // Generating movie api url
        String movieUrl = generateUrl(movieId);
        performJsonNetworkRequest(movieUrl);
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
        return builtUri.toString();
    }

    private MovieDetail getMovieData(JSONObject data){
        return null;
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
                        getMovieData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to fetch the data!!!");
            }
        });
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
