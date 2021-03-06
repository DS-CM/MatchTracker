package group8.matchtracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import group8.matchtracker.R;
import group8.matchtracker.activities.TabbedActivity;
import group8.matchtracker.data.Player;


public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private static ArrayList<Player> mPlayers;
    private long tid;
    private long eid;

    public PlayerListAdapter(Context context, ArrayList<Player> players, long eid, long tid) {
        mContext = context;
        mPlayers = players;
        this.eid = eid;
        this.tid = tid;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public long mPlayerId;
        public TextView mNameTextView;
        public TextView mIgnTextView;
        public long tid;
        public long eid;

        public ViewHolder(View v) {
            super (v);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //if (mCrime != null) { // TODO - Figure out what shouldn't be null, taken from https://www.bignerdranch.com/blog/recyclerview-part-1-fundamentals-for-listview-experts/
                Context context = itemView.getContext();
                Intent i = new Intent(context, TabbedActivity.class);
                Player selectedPlayer = mPlayers.get(getLayoutPosition());
                i.putExtra("EID", eid);
                i.putExtra("TID", tid);
                i.putExtra("PID", selectedPlayer.getId());
                context.startActivity(i);
            //}
        }
    }

    @Override
    public PlayerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_login_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mNameTextView = (TextView) v.findViewById(R.id.player_login_adapter_name_textview);
        viewHolder.mIgnTextView = (TextView) v.findViewById(R.id.player_login_adapter_ign_textview);

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
        holder.eid = eid;
        holder.tid = tid;
        //holder.mNameTextView.setText(currentPlayer.getName());
        holder.mNameTextView.setText(currentPlayer.getIgn());
        holder.mIgnTextView.setText(currentPlayer.getIgn());
    }

    public void setFilter(List<Player> players){
        mPlayers = new ArrayList<>();
        mPlayers.addAll(players);
        notifyDataSetChanged();
    }
}
