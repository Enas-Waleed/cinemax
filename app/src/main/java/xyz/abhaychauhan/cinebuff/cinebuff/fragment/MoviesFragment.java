package xyz.abhaychauhan.cinebuff.cinebuff.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.adapters.MovieAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.cinbuff.MovieDetailActivity;
import xyz.abhaychauhan.cinebuff.cinebuff.models.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.utils.tmDbUrl;


public class MoviesFragment extends Fragment {

    private final static String LOG_TAG = MoviesFragment.class.getName();

    LinearLayout noDataFoundView, noNetworkFoundView;
    ProgressBar progressBar;
    GridView gridView;

    public MovieAdapter adapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieScreen();
    }

    private void updateMovieScreen() {
        final String SORT_BY = "sort_by";
        final String API_KEY = "api_key";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_by = prefs.getString(getString(R.string.pref_sortby_key)
                , getString(R.string.pref_sortby_popular));

        Uri builtUri = Uri.parse(tmDbUrl.BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY, sort_by)
                .appendQueryParameter(API_KEY, tmDbUrl.API_KEY).build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(LOG_TAG, "updateMovieScreen: Not able to create Url");
        }

        MovieAsyncTask movieTask = new MovieAsyncTask();
        movieTask.execute(url.toString());
        Log.v(LOG_TAG, "updateMovieScreen: " + url.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialising adapter
        adapter = new MovieAdapter(getContext(), new ArrayList<Movie>());
        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);

        //Initialising Views
        LinearLayout noDataFoundView = (LinearLayout) rootView.findViewById(R.id.no_data_found_view);
        LinearLayout noNetworkFoundView = (LinearLayout) rootView.findViewById(R.id.no_network_found_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_view);

        //Intialising GridView
        gridView = (GridView) rootView.findViewById(R.id.movie_grid_box);
        gridView.setAdapter(adapter);

        //Check to see if Device is connected to network or not
        ConnectivityManager connMgr = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            noDataFoundView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            //gridView.setVisibility(View.GONE);
            noNetworkFoundView.setVisibility(View.GONE);
        } else {
            //noDataFoundView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            //gridView.setVisibility(View.GONE);
            noNetworkFoundView.setVisibility(View.VISIBLE);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie currentMovie = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movie", currentMovie);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        public final String LOG_TAG = MovieAsyncTask.class.getName();

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            //Check to see if no parameters were set
            if (params.length == 0) {
                return null;
            }
            //Creating URL object
            URL url = createUrl(params[0]);
            //Log.v(LOG_TAG,"URL " + url.toString());
            String jsonResonse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            if (url == null) {
                return null;
            }

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResonse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "doInBackground: Error response code " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "doInBackground: Error Not able to create HttpURLConnection object !!!");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "doInBackground: Error closing inputstream !!!");
                    }
                }
            }

            try {
                return extractDataFromJson(jsonResonse);
            } catch (Exception e) {
                Log.e(LOG_TAG, "doInBackground: Error extracting movie list from json response!!!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies != null) {
                progressBar.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                adapter.clear();
                adapter.addAll(movies);
            } else {
                progressBar.setVisibility(View.GONE);
                gridView.setVisibility(View.GONE);
                noNetworkFoundView.setVisibility(View.GONE);
                noDataFoundView.setVisibility(View.VISIBLE);
            }
        }

        /**
         * Function convert string url passed in parameter to the URL object
         *
         * @param createUrl
         * @return
         */
        private URL createUrl(String createUrl) {
            URL url = null;
            try {
                url = new URL(createUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "createUrl: Error creating URL");
            }
            return url;
        }

        /**
         * Function read data from inputstream and return the data in String format
         *
         * @param inputStream
         * @return
         * @throws IOException
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line + "\n");
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private ArrayList<Movie> extractDataFromJson(String jsonStringData) {
            ArrayList<Movie> movieList = new ArrayList<Movie>();

            try {
                JSONObject baseObject = new JSONObject(jsonStringData);
                JSONArray resultArray = baseObject.getJSONArray("results");
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject movieObject = resultArray.getJSONObject(i);

                    String moviePosterImage = movieObject.getString("poster_path");
                    String movieOverView = movieObject.getString("overview");
                    String movieReleaseDate = movieObject.getString("release_date");
                    int movieId = movieObject.getInt("id");
                    String movieTitle = movieObject.getString("original_title");
                    String moviePosterPath = movieObject.getString("backdrop_path");
                    Double moviePopularity = movieObject.getDouble("popularity");
                    int movieVoteCount = movieObject.getInt("vote_count");
                    Double movieVoteAverage = movieObject.getDouble("vote_average");
                    String movieLanguage = movieObject.getString("original_language");

                    movieList.add(new Movie(movieId, movieTitle, moviePosterImage, movieOverView,
                            movieReleaseDate, moviePopularity, movieVoteCount, movieVoteAverage
                            , moviePosterPath, movieLanguage));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "extractDataFromJson: Not able to create JSONObject !!!");
            }

            return movieList;
        }
    }

}
