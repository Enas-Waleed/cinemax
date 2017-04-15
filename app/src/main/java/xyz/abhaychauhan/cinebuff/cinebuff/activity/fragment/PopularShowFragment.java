package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter.TvShowAdapter;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.TvShow;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.NetworkController;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

/**
 * Created by abhay on 28/03/17.
 */

public class PopularShowFragment extends Fragment implements
        TvShowAdapter.OnTvShowClickListener {

    @BindView(R.id.popular_movie_show_rv)
    RecyclerView recyclerView;

    private GridLayoutManager layoutManager;

    private ArrayList<TvShow> tvShowList;
    private TvShowAdapter adapter;

    public PopularShowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_show_popular,
                container, false);
        ButterKnife.bind(this, rootView);

        tvShowList = new ArrayList<>();
        adapter = new TvShowAdapter(getContext(), tvShowList, this);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount = (int) (width / 250.00);

        layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /**
     * This function return the popular show url
     *
     * @param page
     * @return
     */
    private String getPopularShowUrl(int page) {
        Uri builtUri = Uri.parse(TmdbUrl.TV_POPULAR_URL)
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
                getPopularShowUrl(page), null, new Response.Listener<JSONObject>() {
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
     * This function
     */
    private void fetchShow(JSONObject response) {

    }

    @Override
    public void onShowClick(View view, int position) {

    }
}
