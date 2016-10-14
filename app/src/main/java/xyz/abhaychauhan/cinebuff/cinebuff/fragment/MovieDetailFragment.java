package xyz.abhaychauhan.cinebuff.cinebuff.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.abhaychauhan.cinebuff.cinebuff.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {


    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail_fragment,container,false);
        return rootView;
    }

}
