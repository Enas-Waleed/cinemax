package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.TopRatedTvShowAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.TvShow;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.Constants;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.NetworkController;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

/**
 * Created by abhay on 28/03/17.
 */

public class TopRatedShowFragment extends Fragment implements
        TopRatedTvShowAdapter.OnTopRatedTvShowClickListener {

    @BindView(R.id.top_rated_show_rv)
    RecyclerView topRatedShowRv;

    private GridLayoutManager layoutManager;

    private ArrayList<TvShow> tvShowList;
    private TopRatedTvShowAdapter adapter;

    private int pageCount = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 4;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    public TopRatedShowFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_show_top_rated,
                container, false);
        ButterKnife.bind(this, rootView);

        tvShowList = new ArrayList<>();
        adapter = new TopRatedTvShowAdapter(getContext(), tvShowList, this);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = (int) (width / 120.00);

        layoutManager = new GridLayoutManager(getContext(), spanCount);
        topRatedShowRv.setLayoutManager(layoutManager);
        topRatedShowRv.setHasFixedSize(true);
        setupRecyclerView();

        requestShowList(pageCount);

        return rootView;
    }

    @Override
    public void onTopRatedShowClick(View view, int position) {
        showSnackBarMessage(tvShowList.get(position).getTitle());
    }

    /**
     * This function return the popular show url
     *
     * @param page
     * @return
     */
    private String getTopRatedShowUrl(int page) {
        Uri builtUri = Uri.parse(TmdbUrl.TV_TOP_RATED_URL)
                .buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .appendQueryParameter(TmdbUrl.LANGUAGE_PARAM, TmdbUrl.LANGUAGE)
                .appendQueryParameter(TmdbUrl.PAGE_PARAM, Integer.toString(page))
                .build();
        return builtUri.toString();
    }

    /**
     * This function request tv shows from the API Endpoints and pass the JSONObject result
     * to fetchShow function.
     *
     * @param page
     */
    private void requestShowList(int page) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getTopRatedShowUrl(page), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fetchShow(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        NetworkController.getInstance(getContext()).addToRequestQueue(request);
    }

    /**
     * This function fetch show list from response returned by the API and stores them
     * in showList.
     */
    private void fetchShow(JSONObject response) {
        JSONArray results = response.optJSONArray(Constants.RESULTS);
        for (int index = 0; index < results.length(); index++) {
            JSONObject show = results.optJSONObject(index);
            String posterPath = show.optString(Constants.POSTER_PATH);
            int id = show.optInt(Constants.ID);
            String title = show.optString(Constants.ORIGINAL_NAME);
            double voteAverage = show.optDouble(Constants.VOTE_AVERAGE);
            String firstAirDate = show.optString(Constants.FIRST_AIR_DATE);
            String backdropPath = show.optString(Constants.BACKDROP_PATH);
            String overview = show.optString(Constants.OVERVIEW);
            int voteCount = show.optInt(Constants.VOTE_COUNT);
            TvShow tvShow = new TvShow(id, posterPath, backdropPath, title, voteAverage,
                    firstAirDate, overview, voteCount);
            tvShowList.add(tvShow);
        }
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        topRatedShowRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        pageCount++;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount) <=
                        (firstVisibleItem + visibleThreshold)) {
                    requestShowList(pageCount);
                    showSnackBarMessage("Loading more Tv Shows...");
                    loading = true;
                }
            }
        });
        topRatedShowRv.setAdapter(adapter);
    }

    /**
     * This function display SnackBar with a message when called.
     *
     * @param message
     */
    private void showSnackBarMessage(String message) {
        Snackbar.make(topRatedShowRv, message, Snackbar.LENGTH_SHORT).show();
    }

}
