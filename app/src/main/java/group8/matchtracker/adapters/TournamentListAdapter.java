package group8.matchtracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.activities.Login;
import group8.matchtracker.data.Tournament;


public class TournamentListAdapter extends RecyclerView.Adapter<TournamentListAdapter.ViewHolder> {
    private Context mContext;
    private static ArrayList<Tournament> mTournaments;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int mTournamentId;
        public ImageView mImageView;
        public TextView mNameTextView;
        public TextView mLocationTextView;
        public TextView mDateTextView;
        public TextView mOrganizerTextView;

        public ViewHolder(View v) {
            super (v);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //if (mCrime != null) { // TODO - Figure out what shouldn't be null, taken from https://www.bignerdranch.com/blog/recyclerview-part-1-fundamentals-for-listview-experts/
                Context context = itemView.getContext();
                Intent i = new Intent(context, Login.class);
                i.putExtra("TOURNAMENT_ID", mTournaments.get(getLayoutPosition()).getId());
                context.startActivity(i);
            //}
        }
    }

    public TournamentListAdapter(Context context, ArrayList<Tournament> tournaments) {
        mContext = context;
        mTournaments = tournaments;
    }

    @Override
    public TournamentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_list_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mImageView = (ImageView) v.findViewById(R.id.tournament_image_view);
        viewHolder.mNameTextView = (TextView) v.findViewById(R.id.tournament_list_adapter_name_textview);
        viewHolder.mLocationTextView = (TextView) v.findViewById(R.id.tournament_list_adapter_location_textview);
        viewHolder.mDateTextView = (TextView) v.findViewById(R.id.tournament_list_adapter_date_textview);
        viewHolder.mOrganizerTextView = (TextView) v.findViewById(R.id.tournament_list_adapter_organizer_textview);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mTournaments.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tournament currentTournament = mTournaments.get(position);
        String dates = "" + currentTournament.getStartTime() + " to " + currentTournament.getEndTime();

        holder.mTournamentId = currentTournament.getId();

        // TODO - holder.mImageView for logo
        if (false) { // is imageuri is != null enter
            holder.mImageView.setImageURI(Uri.parse("uri link"));
        }

        holder.mNameTextView.setText(currentTournament.getName());
        holder.mLocationTextView.setText(currentTournament.getLocation());
        holder.mDateTextView.setText(dates);
        holder.mOrganizerTextView.setText("By " + currentTournament.getOrganizer());
    }
}
