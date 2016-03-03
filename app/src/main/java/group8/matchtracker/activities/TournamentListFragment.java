package group8.matchtracker.activities;

import android.app.Activity;
import android.app.Fragment;
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


public class TournamentListFragment extends Fragment {
    private TournamentAdapter mTournamentAdapter;
    private ArrayList<Tournament> mTournaments;

    public TournamentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tournament_list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.tournamentlist_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mTournamentAdapter = new TournamentAdapter(v.getContext(), mTournaments);
        recyclerView.setAdapter(mTournamentAdapter);

        return v;
    }
}
