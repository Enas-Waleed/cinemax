package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.abhaychauhan.cinebuff.cinebuff.R;


public class TopRatedMovieFragment extends Fragment {

    public TopRatedMovieFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_top_rated, container, false);
        return rootView;
    }
}
