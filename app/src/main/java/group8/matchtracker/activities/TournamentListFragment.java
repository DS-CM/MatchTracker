package group8.matchtracker.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.adapters.TournamentAdapter;
import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.tables.TournamentTable;


public class TournamentListFragment extends Fragment {
    private TournamentAdapter mTournamentAdapter;
    private ArrayList<Tournament> mTournaments;

    public TournamentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*mTournaments = new ArrayList<Tournament>();
        mTournaments.add(new Tournament(1, "Shuffle VIII", 03122016, 03132016, "Ohio Union", "eSports Initiative"));
        mTournaments.add(new Tournament(2, "Big House", 05032016, 05042016, "U of M", "The school up north"));
        mTournaments.add(new Tournament(1, "EVO", 22, 23, "Cali", "EVO LLC"));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tournament_list_fragment, container, false);

        TournamentTable tournamentTable = new TournamentTable(v.getContext());
        tournamentTable.createTournament(1, "Shuffle VIII", 03122016, 03132016, "Ohio Union", "eSports Initiative");
        tournamentTable.createTournament(2, "Big House", 05032016, 05042016, "U of M", "The school up north");
        tournamentTable.createTournament(3, "EVO", 22, 23, "Cali", "EVO LLC");

        mTournaments = tournamentTable.getAllTournaments();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.tournamentlist_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mTournamentAdapter = new TournamentAdapter(v.getContext(), mTournaments);
        recyclerView.setAdapter(mTournamentAdapter);

        return v;
    }
}
