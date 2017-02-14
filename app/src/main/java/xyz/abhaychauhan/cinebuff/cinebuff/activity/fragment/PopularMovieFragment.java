package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.PopularMovieAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.NetworkController;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;


public class PopularMovieFragment extends Fragment {

    private static final String TAG = PopularMovieFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PopularMovieAdapter adapter;

    private ArrayList<Movie> movieList;

    public PopularMovieFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_popular, container, false);

        movieList = new ArrayList<>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.popular_movie_rv);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PopularMovieAdapter(getActivity(), movieList);

        recyclerView.setAdapter(adapter);

        getMovieList();
        return rootView;
    }

    private void getMovieList() {
        //RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest popularMovieJsonRequest = new JsonObjectRequest(Request.Method.GET,
                generateUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updateMoviesList(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        NetworkController.getInstance(this.getContext()).addToRequestQueue(popularMovieJsonRequest);
    }

    private void updateMoviesList(JSONObject response) {
        JSONArray results = response.optJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.optJSONObject(i);
            String posterPath = movieObject.optString("poster_path");
            String overview = movieObject.optString("overview");
            String title = movieObject.optString("original_title");
            int voteCount = movieObject.optInt("vote_count");
            double voteAverage = movieObject.optDouble("vote_average");
            Log.d(TAG, "Object : " + movieObject.toString());

            Movie movie = new Movie(title, overview, voteCount, voteAverage, posterPath);
            movieList.add(movie);
        }
        adapter.notifyDataSetChanged();
    }

    private String generateUrl() {
        Uri builtUri = Uri.parse(TmdbUrl.POPULAR_MOVIES_URL).buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .appendQueryParameter(TmdbUrl.LANGUAGE_PARAM, TmdbUrl.LANGUAGE)
                .build();
        return builtUri.toString();
    }

}
