package xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.Trailer;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

/**
 * Created by abhay on 06/03/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailerList;
    private Context context;
    private TrailerClickListener listener;

    public interface TrailerClickListener{
        void onTrailerClick(View view, int position);
    }

    public TrailerAdapter(Context context, List<Trailer> trailerList,
                          TrailerClickListener listener) {
        this.context = context;
        this.trailerList = trailerList;
        this.listener = listener;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView trailerIv;
        public TextView nameTv;

        public TrailerViewHolder (View view){
            super(view);
            trailerIv = (ImageView) view.findViewById(R.id.trailer_iv);
            nameTv = (TextView) view.findViewById(R.id.trailer_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onTrailerClick(v, clickedPosition);
        }
    }

    @Override
    public TrailerViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie_trailer,
                parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder (TrailerViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        Picasso.with(getContext()).load(String.format(TmdbUrl.YOUTUBE_THUMB_URL,
                trailer.getKey())).into(holder.trailerIv);
        holder.nameTv.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public Context getContext() {
        return context;
    }
}
