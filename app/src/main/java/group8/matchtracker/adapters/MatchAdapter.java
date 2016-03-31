package group8.matchtracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.data.Match;

/**
 * Created by dsoll on 3/14/2016.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private static ArrayList<Match> mMatches;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public long mMatchId;
        public ImageView mGameImageView;
        public TextView mMatchTypeTextView;
        public TextView mMatchPlayersTextView;
        public TextView mMatchInformationTextView;
        public TextView mMatchLocationTextView;

        public ViewHolder(View v) {
            super (v);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            // TODO - Something to submit scores or get more information
        }
    }

    public MatchAdapter(Context context, ArrayList<Match> matches) {
        mContext = context;
        mMatches = matches;
    }

    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mGameImageView = (ImageView) v.findViewById(R.id.match_imageview);
        viewHolder.mMatchTypeTextView = (TextView) v.findViewById(R.id.match_type);
        viewHolder.mMatchPlayersTextView = (TextView) v.findViewById(R.id.match_player_names);
        viewHolder.mMatchInformationTextView = (TextView) v.findViewById(R.id.match_information);
        viewHolder.mMatchLocationTextView = (TextView) v.findViewById(R.id.match_location);


        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mMatches.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match currentMatch = mMatches.get(position);
        int[] result = currentMatch.getResult();

        // TODO - David - Fix so don't have to have check
        if (result == null) result = new int[]{0,0};

        holder.mMatchId = currentMatch.getId();
        //holder.mGameImageView
        holder.mMatchTypeTextView.setText(currentMatch.getType());
        holder.mMatchPlayersTextView.setText(currentMatch.getVersingPlayers());
        holder.mMatchInformationTextView.setText("Score: " + result[0] + " - " + result[1]);
        holder.mMatchLocationTextView.setText(currentMatch.getLocation() + " at " + currentMatch.getTime());
    }
}
