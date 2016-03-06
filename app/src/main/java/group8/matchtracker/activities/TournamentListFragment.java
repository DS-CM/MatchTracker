package group8.matchtracker.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.adapters.TournamentListAdapter;
import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class TournamentListFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private TournamentListAdapter mTournamentListAdapter;
    private ArrayList<Tournament> mTournaments;
    private DatabaseHelper mDbHelper;

    public TournamentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tournament_list_fragment, container, false);

        // TODO - Remove
        mDbHelper = new DatabaseHelper(v.getContext());
        mDbHelper.mTournamentTable.createTournament(1, "Shuffle VIII", 03122016, 03132016, "Ohio Union", "eSports Initiative");
        mDbHelper.mTournamentTable.createTournament(2, "Big House", 05032016, 05042016, "U of M", "The school up north");
        mDbHelper.mTournamentTable.createTournament(3, "EVO", 22, 23, "Cali", "EVO LLC");

        mTournaments = mDbHelper.mTournamentTable.getAllTournaments();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.tournamentlist_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mTournamentListAdapter = new TournamentListAdapter(v.getContext(), mTournaments);
        recyclerView.setAdapter(mTournamentListAdapter);

        return v;
    }
}
