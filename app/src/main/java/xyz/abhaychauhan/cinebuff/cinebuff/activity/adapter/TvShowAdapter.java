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
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.TvShow;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

/**
 * Created by abhay on 28/03/17.
 */

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private Context context;
    private List<TvShow> tvShowList;
    private OnTvShowClickListener listener;

    public interface OnTvShowClickListener {
        void onShowClick(View view, int position);
    }

    public TvShowAdapter(Context context, List<TvShow> tvShowList, OnTvShowClickListener listener) {
        this.context = context;
        this.tvShowList = tvShowList;
        this.listener = listener;
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public ImageView showPosterIv;
        public TextView showTitleTv;

        public TvShowViewHolder(View itemView) {
            super(itemView);
            showPosterIv = (ImageView) itemView.findViewById(R.id.tv_show_poster);
            showTitleTv = (TextView) itemView.findViewById(R.id.tv_show_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onShowClick(v, position);
        }
    }

    @Override
    public TvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_show,
                parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvShowViewHolder holder, int position) {
        TvShow tvShow = tvShowList.get(position);
        Picasso.with(getContext()).load(TmdbUrl.IMAGE_BASE_URL + tvShow.getPosterPath())
                .into(holder.showPosterIv);
        holder.showTitleTv.setText(tvShow.getTitle());
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    private Context getContext() {
        return this.context;
    }


}
