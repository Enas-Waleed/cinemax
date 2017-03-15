package xyz.abhaychauhan.cinebuff.cinebuff.activity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.LeadCastAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.ReviewAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.SimilarMovieAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.TrailerAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.LeadCast;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.MovieDetail;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Review;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Trailer;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.CommonUtils;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.NetworkController;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

public class MovieDetailActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerClickListener, SimilarMovieAdapter.SimilarMovieItemClickListener,
        LeadCastAdapter.LeadCastClickListener, ReviewAdapter.ReviewClickListener {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

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

    @BindView(R.id.trailer)
    RecyclerView trailerRv;

    @BindView(R.id.similar_movie_rv)
    RecyclerView similarMovieRv;

    @BindView(R.id.lead_cast_rv)
    RecyclerView leadCastRv;

    @BindView(R.id.movie_reviews_rv)
    RecyclerView reviewRv;

    @BindView(R.id.layout_movie_trailer)
    LinearLayout movieTrailerLayout;

    @BindView(R.id.layout_movie_review)
    LinearLayout movieReviewLayout;

    @BindView(R.id.layout_lead_cast)
    LinearLayout movieCastLayout;

    @BindView(R.id.layout_movie_similar)
    LinearLayout movieSimilarLayout;

    @BindView(R.id.share_layout)
    LinearLayout shareLayout;

    private LinearLayoutManager trailerLayoutManager;
    private LinearLayoutManager similarMovieLayoutManager;
    private LinearLayoutManager leadCastLayoutManager;
    private LinearLayoutManager reviewLayoutManager;

    private TrailerAdapter trailerAdapter;
    private SimilarMovieAdapter similarMovieAdapter;
    private LeadCastAdapter leadCastAdapter;
    private ReviewAdapter reviewAdapter;

    private List<Trailer> trailerList;
    private ArrayList<Movie> similarMovieList;
    private ArrayList<LeadCast> leadCastLists;
    private ArrayList<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        // Retrieve movie id from intent
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movieId");
        String movieTitle = intent.getStringExtra("movieTitle");

        // Setting up toolbar
        setupToolbar(movieTitle);

        // Generating movie api url
        String movieUrl = generateUrl(movieId);
        String movieTrailerUrl = generateMovieTrailerUrl(movieId);
        String similarMoviesUrl = generateSimilarMoviesUrl(movieId);
        String leadCastUrl = generateMovieCastUrl(movieId);
        String reviewUrl = generateReviewUrl(movieId);

        performJsonNetworkRequest(movieUrl, movieTrailerUrl, similarMoviesUrl,
                leadCastUrl, reviewUrl);

        setupTrailerRecyclerView();
        setupSimilarMovieRecyclerView();
        setupLeadCastRecyclerView();
        setupReviewRecyclerView();

    }

    /**
     * This function display the data about movie in their respective views by using
     * the information stored in the MovieDetail object passed as an argument to the
     * function
     *
     * @param movie MovieDetail Object containing the information about the movie
     */
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
     * @param movieId String containing the unique movie id
     * @return Api url for the movie with movieId equal to @param movieId
     */
    private String generateUrl(String movieId) {
        Uri builtUri = Uri.parse(TmdbUrl.TMDB_BASE_URL + movieId).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .build();
        return builtUri.toString();
    }

    /**
     * This function return the movie cast url
     *
     * @param movieId String containing the unique movie id
     * @return Api url for the movie cast
     */
    private String generateMovieCastUrl(String movieId) {
        Uri builtUri = Uri.parse(String.format(TmdbUrl.MOVIE_CAST_URL, movieId)).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .build();
        return builtUri.toString();
    }

    /**
     * This function return the movie trailers url
     *
     * @param movieId String containing the unique movie id
     * @return Api url for the movie trailer
     */
    private String generateMovieTrailerUrl(String movieId) {
        Uri builtUri = Uri.parse(String.format(TmdbUrl.MOVIE_TRAILER_URL, movieId)).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .build();
        return builtUri.toString();
    }

    /**
     * This function return the movie review url
     *
     * @param movieId String containing the unique movie id
     * @return Api url for the movie review
     */
    private String generateReviewUrl(String movieId){
        Uri builtUri = Uri.parse(String.format(TmdbUrl.MOVIE_REVIEW_URL, movieId)).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .build();
        return builtUri.toString();
    }

    /**
     * This function return the similar movie url of the movie referenced by
     * movieId.
     *
     * @param movieId String containing the unique movie id
     * @return Api url for the similar movie
     */
    private String generateSimilarMoviesUrl(String movieId) {
        Uri builtUri = Uri.parse(String.format(TmdbUrl.MOVIE_SIMILAR_URL, movieId)).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .build();
        return builtUri.toString();
    }

    /**
     * This function retrieve data from JSON Object, then create MovieDetail object from that
     * data and then return that object
     *
     * @param data JsonObject retrieved from the movie api endpoint
     * @return movieDetail An object containing the information of the movie whose
     * id is sent as a extra string to the activity via intent
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
     * This function return the lead cast list
     *
     * @param data Contains the json data of the lead cast
     * @return list of lead cast
     */
    private ArrayList<LeadCast> getLeadCastList(JSONObject data) {
        ArrayList<LeadCast> leadCastList = new ArrayList<>();
        JSONArray cast = data.optJSONArray("cast");
        for (int index = 0; index < cast.length(); index++) {
            JSONObject object = cast.optJSONObject(index);
            String characterName = object.optString("character");
            String creditId = object.optString("credit_id");
            int id = object.optInt("id");
            String name = object.optString("name");
            String profilePath = object.optString("profile_path");

            LeadCast leadCast = new LeadCast(characterName, creditId, id, name, profilePath);
            leadCastList.add(leadCast);
        }
        return leadCastList;
    }

    /**
     * This function return the movie review list
     *
     * @param data Contains the json data of the review
     * @return list of movie review
     */
    private ArrayList<Review> getReviewList(JSONObject data){
        ArrayList<Review> reviewLists = new ArrayList<>();
        JSONArray results = data.optJSONArray("results");
        for(int index=0; index<results.length(); index++){
            JSONObject object = results.optJSONObject(index);
            String id = object.optString("id");
            String author = object.optString("author");
            String content = object.optString("content");
            String url = object.optString("url");
            Review review = new Review(id, author, content, url);
            reviewLists.add(review);
        }
        return reviewLists;
    }

    /**
     * This function return the similar movie list
     *
     * @param data Contains the json data of the similar movies
     * @return list of similar movies
     */
    private ArrayList<Movie> getSimilarMovieList(JSONObject data) {
        ArrayList<Movie> similarMovieList = new ArrayList<>();
        JSONArray results = data.optJSONArray("results");
        for (int index = 0; index < results.length(); index++) {
            JSONObject object = results.optJSONObject(index);
            int id = object.optInt("id");
            String title = object.optString("original_title");
            String overview = object.optString("overview");
            String posterPath = object.optString("poster_path");
            double voteAverage = object.optDouble("vote_average");
            int voteCount = object.optInt("vote_count");
            Movie movie = new Movie(id, title, overview, voteCount, voteAverage, posterPath);
            similarMovieList.add(movie);
        }
        return similarMovieList;
    }

    /**
     * This function return the Trailer object lists
     *
     * @param data Contains the json data of movie trailer
     * @return list of Trailers object
     */
    private ArrayList<Trailer> getTrailerList(JSONObject data) {
        ArrayList<Trailer> trailers = new ArrayList<>();
        JSONArray results = data.optJSONArray("results");
        for (int index = 0; index < results.length(); index++) {
            JSONObject object = results.optJSONObject(index);
            String id = object.optString("id");
            String key = object.optString("key");
            String name = object.optString("name");
            String site = object.optString("site");
            String size = object.optString("size");
            Trailer trailer = new Trailer(id, name, key, site, size);
            trailers.add(trailer);
        }
        return trailers;
    }

    @Override
    public void onLeadCastClick(View view, int position) {
        showSnackbarMessage(leadCastLists.get(position).getName());
    }

    @Override
    public void onReviewClickListener(View view, int position) {
        showSnackbarMessage(reviewList.get(position).getAuthor());
        // TODO : Change body
    }

    @Override
    public void onSimilarMovieItemClick(View view, int position) {
        Movie movie = similarMovieList.get(position);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieId", Integer.toString(movie.getId()));
        intent.putExtra("movieTitle", movie.getTitle());
        startActivity(intent);

//        View sharedView = findViewById(R.id.movie_poster_iv);
//        String transitionName = getString(R.string.shared_poster);
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
//                sharedView, transitionName);
//        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onTrailerClick(View view, int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.format(TmdbUrl.YOUTUBE_VIDEO_URL,
                trailerList.get(position).getKey())));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This function performs network request on the api url and
     * pass the response as a parameter to getMovieData() function
     *
     * @param movieUrl        movie url
     * @param movieTrailerUrl movie trailer url
     */
    private void performJsonNetworkRequest(String movieUrl, String movieTrailerUrl,
                                           String similarMoviesUrl, String movieCastUrl,
                                           String reviewUrl) {
        // Request for movie data
        JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.GET, movieUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MovieDetail movie = getMovieData(response);
                displayData(movie);
                setupShare(movie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to fetch the data!!!");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(movieRequest);

        // Request for movie trailer data
        JsonObjectRequest movieTrailerRequest = new JsonObjectRequest(Request.Method.GET,
                movieTrailerUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                trailerList.clear();
                trailerList.addAll(getTrailerList(response));
                if(trailerList.size() > 0){
                    movieTrailerLayout.setVisibility(View.VISIBLE);
                }
                trailerAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to load Trailers");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(movieTrailerRequest);

        // Request for similar movie data
        JsonObjectRequest similarMovieRequest = new JsonObjectRequest(Request.Method.GET,
                similarMoviesUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                similarMovieList.clear();
                similarMovieList.addAll(getSimilarMovieList(response));
                if(similarMovieList.size() > 0){
                    movieSimilarLayout.setVisibility(View.VISIBLE);
                }
                similarMovieAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to load similar movies !!");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(similarMovieRequest);

        // Request for movie cast data
        JsonObjectRequest leadCastRequest = new JsonObjectRequest(Request.Method.GET,
                movieCastUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                leadCastLists.clear();
                leadCastLists.addAll(getLeadCastList(response));
                if(leadCastLists.size() > 0){
                    movieCastLayout.setVisibility(View.VISIBLE);
                }
                leadCastAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to lead movie cast data !!");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(leadCastRequest);

        // Request for movie review data
        JsonObjectRequest reviewRequest = new JsonObjectRequest(Request.Method.GET,
                reviewUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                reviewList.clear();
                reviewList.addAll(getReviewList(response));
                if(reviewList.size() > 0){
                    movieReviewLayout.setVisibility(View.VISIBLE);
                }
                reviewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to load review data !!");
            }
        });
        NetworkController.getInstance(this).addToRequestQueue(reviewRequest);
    }

    /**
     * This function setup share action button
     *
     * @param movie
     */
    private void setupShare(final MovieDetail movie){
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.setType("text/plain");
                String message = movie.getTitle() + "\n" + movie.getSynopsis();
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * This function setups the toolbar, and set it title to
     * the movie title
     *
     * @param title Title of the movie
     */
    private void setupToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    /**
     * This function setup the lead cast recycler view, initialize lead cast list,
     * set layout manager for lead cast recycler view
     */
    private void setupLeadCastRecyclerView(){
        leadCastLists = new ArrayList<>();
        leadCastLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        leadCastAdapter = new LeadCastAdapter(this, leadCastLists, this);
        leadCastRv.setLayoutManager(leadCastLayoutManager);
        leadCastRv.setAdapter(leadCastAdapter);
    }

    /**
     * This function setup the trailer recycler view, initialize trailers array list,
     * set layout manager for trailer recycler view
     */
    private void setupTrailerRecyclerView() {
        trailerList = new ArrayList<>();

        trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        trailerAdapter = new TrailerAdapter(this, trailerList, this);

        trailerRv.setLayoutManager(trailerLayoutManager);
        trailerRv.setAdapter(trailerAdapter);
    }

    /**
     * This function setup the review recycler view, initialize review array lis,
     * set layout manager for review recycler view
     */
    private void setupReviewRecyclerView(){
        reviewList = new ArrayList<>();

        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        reviewAdapter = new ReviewAdapter(this, reviewList, this);

        reviewRv.setLayoutManager(reviewLayoutManager);
        reviewRv.setAdapter(reviewAdapter);
    }

    /**
     * This function setup the similar movie recycler view, initialize similar movie list,
     * set layout manager for similar movie recycler view
     */
    private void setupSimilarMovieRecyclerView() {
        similarMovieList = new ArrayList<>();

        similarMovieLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);

        similarMovieAdapter = new SimilarMovieAdapter(this, similarMovieList, this);

        similarMovieRv.setLayoutManager(similarMovieLayoutManager);
        similarMovieRv.setAdapter(similarMovieAdapter);
    }

    /**
     * This function displays Snackbar with a particular message
     * that is sent as a parameter to the function
     *
     * @param message Message to display in the snackbar
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
