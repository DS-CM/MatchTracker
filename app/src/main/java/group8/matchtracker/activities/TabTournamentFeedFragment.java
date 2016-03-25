package group8.matchtracker.activities;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group8.matchtracker.R;

public class TabTournamentFeedFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    public TabTournamentFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.tab_fragment_tournament_feed, container, false);

        Log.d(TAG, "onCreateView");
        return v;
    }
}
