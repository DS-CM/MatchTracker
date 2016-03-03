package group8.matchtracker.activities;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group8.matchtracker.R;

public class TournamentFeed extends Fragment {
    public TournamentFeed(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);

        return v;
    }
}
