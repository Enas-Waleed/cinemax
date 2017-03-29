package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

/**
 * Created by abhay on 28/03/17.
 */

public class PopularShowFragment extends Fragment {

    @BindView(R.id.popular_movie_show_rv)
    RecyclerView recyclerView;

    public PopularShowFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_show_popular,
                container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

    private String getPopularShowUrl(int page) {
        Uri builtUri = Uri.parse(TmdbUrl.TV_POPULAR_URL)
                .buildUpon()
                .appendQueryParameter(TmdbUrl.API_KEY_PARAM, TmdbUrl.API_KEY)
                .appendQueryParameter(TmdbUrl.LANGUAGE_PARAM, TmdbUrl.LANGUAGE)
                .appendQueryParameter(TmdbUrl.PAGE_PARAM, Integer.toString(page))
                .build();
        return builtUri.toString();
    }

}
