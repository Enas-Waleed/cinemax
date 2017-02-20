package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.TopRatedAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.NetworkController;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;


public class TopRatedMovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private TopRatedAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Movie> moviesList;

    private int pageCount = 1;
    private int previousTotal = 0;
    private Boolean loading = true;
    private int visibleThreshold = 6;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    public TopRatedMovieFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_top_rated, container, false);

        moviesList = new ArrayList<>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.top_rated_movie_rv);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new TopRatedAdapter(getContext(), moviesList);

        getTopRatedMovieList(pageCount);
        setupRecyclerViewOnScroll();

        return rootView;
    }

    private void getTopRatedMovieList(int page) {
        JsonObjectRequest topRatedMovieRequest = new JsonObjectRequest(Request.Method.GET,
                getTopRatedUrl(page), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateTopRatedMovieList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackbarMessage("Not able to load data!!!");
            }
        });
        NetworkController.getInstance(this.getContext()).addToRequestQueue(topRatedMovieRequest);
    }

    private String getTopRatedUrl(int page) {
        Uri builtUri = Uri.parse(TmdbUrl.TOP_RATED_URL)
                .buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .appendQueryParameter(TmdbUrl.LANGUAGE_PARAM, TmdbUrl.LANGUAGE)
                .appendQueryParameter(TmdbUrl.PAGE_PARAM, Integer.toString(page))
                .build();
        return builtUri.toString();
    }

    private void updateTopRatedMovieList(JSONObject response) {
        JSONArray results = response.optJSONArray("results");
        for (int index = 0; index < results.length(); index++) {
            JSONObject movieObject = results.optJSONObject(index);
            String poster_path = movieObject.optString("poster_path");
            String overview = movieObject.optString("overview");
            String title = movieObject.optString("title");
            int vote_count = movieObject.optInt("vote_count");
            Double vote_average = movieObject.optDouble("vote_average");
            Movie movie = new Movie(title, overview, vote_count, vote_average, poster_path);
            moviesList.add(movie);
        }
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerViewOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        pageCount++;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    getTopRatedMovieList(pageCount);
                    showSnackbarMessage("loading...");
                    loading = true;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
