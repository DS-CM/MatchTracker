package group8.matchtracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.activities.TabbedActivity;
import group8.matchtracker.data.Player;

/**
 * Created by dsoll on 2/29/2016.
 */
public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Player> mPlayers;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int mPlayerId;
        public TextView mNameTextView;
        public TextView mIgnTextView;

        public ViewHolder(View v) {
            super (v);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //if (mCrime != null) { // TODO - Figure out what shouldn't be null, taken from https://www.bignerdranch.com/blog/recyclerview-part-1-fundamentals-for-listview-experts/
                Context context = itemView.getContext();
                Intent i = new Intent(context, TabbedActivity.class);
                context.startActivity(i);
            //}
        }
    }

    public LoginAdapter(Context context, ArrayList<Player> players) {
        mContext = context;
        mPlayers = players;
    }

    @Override
    public LoginAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mNameTextView = (TextView) v.findViewById(R.id.login_name_textview);
        viewHolder.mIgnTextView = (TextView) v.findViewById(R.id.login_ign_textview);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player currentPlayer = mPlayers.get(position);

        holder.mPlayerId = currentPlayer.getId();
        holder.mNameTextView.setText(currentPlayer.getName());
        holder.mIgnTextView.setText(currentPlayer.getIgn());
    }
}
