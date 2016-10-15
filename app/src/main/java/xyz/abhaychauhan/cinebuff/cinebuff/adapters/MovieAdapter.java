package xyz.abhaychauhan.cinebuff.cinebuff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.models.Movie;
import xyz.abhaychauhan.cinebuff.cinebuff.utils.tmDbUrl;

/**
 * Created by abhay on 15/10/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context,ArrayList<Movie> movieList){
        super(context,0,movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.movie_grid_box_view_item,parent,false);
        }

        Movie movie = getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_image);
        TextView textView = (TextView) convertView.findViewById(R.id.movie_title);
        Picasso.with(getContext()).load(tmDbUrl.MOVIE_POSTER_VERTICAL_BASE_URL + movie.getImage())
                .into(imageView);
        textView.setText(movie.getTitle());
        return convertView;
    }
}
