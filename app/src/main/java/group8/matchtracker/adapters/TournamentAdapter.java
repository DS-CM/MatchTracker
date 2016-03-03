package group8.matchtracker.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.data.Tournament;

/**
 * Created by dsoll on 2/29/2016.
 */
public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Tournament> mTournaments;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mDateTextView;
        public ImageView mImageView;
        public ViewHolder(View v) {
            super (v);
        }
    }

    public TournamentAdapter(Context context, ArrayList<Tournament> tournaments) {
        mContext = context;
        mTournaments = tournaments;
    }

    @Override
    public TournamentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.mNameTextView = (TextView) v.findViewById(R.id.tournament_name_textview);
        viewHolder.mDateTextView = (TextView) v.findViewById(R.id.tournament_date_textview);
        viewHolder.mImageView = (ImageView) v.findViewById(R.id.tournament_image_view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return 1; // TODO - Fix
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // stuff

        holder.mNameTextView.setText("stuff"); /*TODO - add*/
        holder.mDateTextView.setText("Date");

        if (false) { // is imageuri is != null enter
            holder.mImageView.setImageURI(Uri.parse("uri link"));
        }
    }
}
