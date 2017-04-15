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
 * Created by abhay on 15/04/17.
 */

public class TopRatedTvShowAdapter extends RecyclerView.Adapter<TopRatedTvShowAdapter.TopRatedShowViewHolder> {

    private List<TvShow> tvShowList;
    private Context context;
    private OnTopRatedTvShowClickListener listener;

    public interface OnTopRatedTvShowClickListener {
        void onTopRatedShowClick(View view, int position);
    }

    public TopRatedTvShowAdapter(Context context, List<TvShow> tvShowList,
                                 OnTopRatedTvShowClickListener listener) {
        this.context = context;
        this.tvShowList = tvShowList;
        this.listener = listener;
    }

    public class TopRatedShowViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public ImageView tvShowPosterIv;
        public TextView tvShowTitleTv;

        public TopRatedShowViewHolder(View itemView) {
            super(itemView);
            tvShowPosterIv = (ImageView) itemView.findViewById(R.id.tv_show_poster);
            tvShowTitleTv = (TextView) itemView.findViewById(R.id.tv_show_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onTopRatedShowClick(v, position);
        }
    }

    @Override
    public TopRatedShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_show_sm, parent, false);
        return new TopRatedShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopRatedShowViewHolder holder, int position) {
        TvShow tvShow = tvShowList.get(position);
        Picasso.with(getContext()).load(TmdbUrl.IMAGE_BASE_URL + tvShow.getPosterPath())
                .into(holder.tvShowPosterIv);
        holder.tvShowTitleTv.setText(tvShow.getTitle());
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    private Context getContext() {
        return context;
    }
}
