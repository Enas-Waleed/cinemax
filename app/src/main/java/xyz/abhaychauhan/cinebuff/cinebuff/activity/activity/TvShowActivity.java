package xyz.abhaychauhan.cinebuff.cinebuff.activity.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment.AirShowFragment;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment.LatestShowFragment;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment.PopularShowFragment;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.fragment.TopRatedShowFragment;

public class TvShowActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.frame_container)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tv Shows");

        changeFragment(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_popular_shows:
                changeFragment(0);
                return true;
            case R.id.action_latest:
                changeFragment(1);
                return true;
            case R.id.action_airing_today:
                changeFragment(2);
                return true;
            case R.id.action_top_rated:
                changeFragment(3);
                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Function change the fragment when an item in Bottom Navigation is clicked
     *
     * @param position item clicked position
     */
    private void changeFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new PopularShowFragment();
                break;
            case 1:
                fragment = new LatestShowFragment();
                break;
            case 2:
                fragment = new AirShowFragment();
                break;
            case 3:
                fragment = new TopRatedShowFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                fragment).commit();
    }

}
