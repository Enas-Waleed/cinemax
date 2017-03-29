package xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.abhaychauhan.cinebuff.cinebuff.R;

/**
 * Created by abhay on 28/03/17.
 */

public class LatestShowFragment extends Fragment {

    public LatestShowFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_show_latest,
                container, false);

        return rootView;
    }

}
