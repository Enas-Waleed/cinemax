package xyz.abhaychauhan.cinebuff.cinebuff.fragment;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
import xyz.abhaychauhan.cinebuff.cinebuff.models.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.utils.tmDbUrl;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    public final static String LOG_TAG = MoviesFragment.class.getName();

    public MovieAdapter adapter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void updateMovieScreen(){
        Uri builtUri = Uri.parse(tmDbUrl.BASE_URL + "popular?").buildUpon()
                .appendQueryParameter("&api_key",tmDbUrl.API_KEY).build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            Log.d(LOG_TAG, "updateMovieScreen: Not able to create Url");
        }

        MovieAsyncTask movieTask = new MovieAsyncTask();
        movieTask.execute(url.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new MovieAdapter(getContext(),new ArrayList<Movie>());
        View rootView = inflater.inflate(R.layout.moviefragment,container,false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_box);
        gridView.setAdapter(adapter);
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
            if(movies != null){
                adapter.clear();
                adapter.addAll(movies);
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
            if (jsonStringData != null) {
                try {
                    JSONObject baseObject = new JSONObject(jsonStringData);
                    JSONArray resultArray = baseObject.getJSONArray("result");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject movieObject = resultArray.getJSONObject(i);
                        String moviePosterImage = movieObject.getString("poster_path");
                        int movieId = movieObject.getInt("id");
                        String movieTitle = movieObject.getString("title");
                        movieList.add(new Movie(movieId, movieTitle, moviePosterImage));
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "extractDataFromJson: Not able to create JSONObject !!!");
                }
            }
            return movieList;
        }
    }

}
