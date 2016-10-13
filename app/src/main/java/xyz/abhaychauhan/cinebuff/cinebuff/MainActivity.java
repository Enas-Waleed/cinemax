package xyz.abhaychauhan.cinebuff.cinebuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.abhaychauhan.cinebuff.cinebuff.fragment.MoviesFragment;

public class MainActivity extends AppCompatActivity {

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
}
