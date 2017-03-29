package xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.abhaychauhan.cinebuff.cinebuff.R;

/**
 * Created by abhay on 28/03/17.
 */

public class TvShowAdapter {

    private Context context;

    public class TvShowViewHolder extends RecyclerView.ViewHolder{

        public ImageView showPosterIv;
        public TextView showTitleTv;

        public TvShowViewHolder(View itemView) {
            super(itemView);
            showPosterIv = (ImageView) itemView.findViewById(R.id.tv_show_poster);
            showTitleTv = (TextView) itemView.findViewById(R.id.tv_show_title);
        }
    }

}
