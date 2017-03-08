package xyz.abhaychauhan.cinebuff.cinebuff.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import xyz.abhaychauhan.cinebuff.cinebuff.R;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.model.LeadCast;
import xyz.abhaychauhan.cinebuff.cinebuff.activity.utils.TmdbUrl;

/**
 * Created by abhay on 08/03/17.
 */

public class LeadCastAdapter extends RecyclerView.Adapter<LeadCastAdapter.LeadCastViewHolder> {

    private ArrayList<LeadCast> leadCastList;
    private LeadCastClickListener listener;
    private Context context;

    public interface LeadCastClickListener{
        void onLeadCastClick(View view, int position);
    }

    public LeadCastAdapter(Context context, ArrayList<LeadCast> leadCastList,
                           LeadCastClickListener listener){
        this.context = context;
        this.leadCastList = leadCastList;
        this.listener = listener;
    }

    public class LeadCastViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public ImageView actorIv;
        public TextView actorName;

        public LeadCastViewHolder(View view){
            super(view);
            actorIv = (ImageView) view.findViewById(R.id.actor_iv);
            actorName = (TextView) view.findViewById(R.id.actor_name);

            // TODO : Set Click listener for lead cast
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listener.onLeadCastClick(v, clickedPosition);
        }
    }

    @Override
    public LeadCastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_lead_cast,
                parent, false);
        return new LeadCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeadCastViewHolder holder, int position) {
        LeadCast leadCast = leadCastList.get(position);
        Picasso.with(getContext()).load(TmdbUrl.IMAGE_BASE_URL + leadCast.getProfilePath())
                .into(holder.actorIv);
        holder.actorName.setText(leadCast.getName());
    }

    @Override
    public int getItemCount() {
        return leadCastList.size();
    }

    private Context getContext(){
        return context;
    }

}
