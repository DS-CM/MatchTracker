package group8.matchtracker.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.adapters.MatchAdapter;
import group8.matchtracker.data.Match;
import group8.matchtracker.database.DatabaseHelper;

public class TabMatchFeedFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private MatchAdapter mMatchAdapter;
    private ArrayList<Match> mMatches = new ArrayList<>();
    private DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;

    public TabMatchFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.feed_of_matches_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.feed_of_matches_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // TODO - Change away from this
        mDbHelper = new DatabaseHelper(v.getContext());
        mDbHelper.mMatchTable.createMatch(0, "A", new int[]{0, 0}, "BO5", "Seat 33", "12:00pm");

        mMatches = mDbHelper.mMatchTable.getAllMatches();
        populateList(v);

        Log.d(TAG, "onCreateView");
        return v;
    }

    public void populateList(View v){
        mMatchAdapter = new MatchAdapter(v.getContext(), mMatches);
        mRecyclerView.setAdapter(mMatchAdapter);
    }
}
