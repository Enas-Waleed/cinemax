package xyz.abhaychauhan.cinebuff.cinebuff.cinbuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.fragment.MovieDetailFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, new MovieDetailFragment())
                    .commit();
        }
    }
}
