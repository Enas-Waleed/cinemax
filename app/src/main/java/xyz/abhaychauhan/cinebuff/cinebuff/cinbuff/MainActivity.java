package xyz.abhaychauhan.cinebuff.cinebuff.cinbuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.fragment.MoviesFragment;
import xyz.abhaychauhan.cinebuff.cinebuff.utils.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new MoviesFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            default:
                Log.d(LOG_TAG, "onOptionsItemSelected: Error Settings Activity not found!!!");
        }
        return super.onOptionsItemSelected(item);
    }
}
