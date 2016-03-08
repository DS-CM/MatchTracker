package group8.matchtracker.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.adapters.PlayerListAdapter;
import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;

public class LoginFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private PlayerListAdapter mPlayerListAdapter;
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private DatabaseHelper mDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.player_login_fragment, container, false);

        // TODO - Change away from this
        mDbHelper = new DatabaseHelper(v.getContext());
        mDbHelper.mPlayerTable.createPlayer("Name1", "MrToast");
        mDbHelper.mPlayerTable.createPlayer("Name2", "MsButter");
        mDbHelper.mPlayerTable.createPlayer("Name3", "3ygun");
        mDbHelper.mPlayerTable.createPlayer("Name4", "J3rn");

        mPlayers = mDbHelper.mPlayerTable.readAllPlayers();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.player_login_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mPlayerListAdapter = new PlayerListAdapter(v.getContext(), mPlayers);
        recyclerView.setAdapter(mPlayerListAdapter);

        Log.d(TAG, "onCreateView");
        return v;
    }
}
