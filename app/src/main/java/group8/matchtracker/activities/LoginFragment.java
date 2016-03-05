package group8.matchtracker.activities;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;

import group8.matchtracker.R;
import group8.matchtracker.adapters.LoginAdapter;
import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;

public class LoginFragment extends ListFragment {
    private final String TAG = getClass().getSimpleName();
    private LoginAdapter mPlayerAdapter;
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private DatabaseHelper mDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        mDbHelper = new DatabaseHelper(v.getContext());

        // TODO - Change away from this
        mDbHelper.mPlayerTable.deletePlayers();
        mDbHelper.mPlayerTable.createPlayer(1, "Name1", "MrToast");
        mDbHelper.mPlayerTable.createPlayer(2, "Name2", "MsButter");
        mDbHelper.mPlayerTable.createPlayer(3, "Name3", "3ygun");
        mDbHelper.mPlayerTable.createPlayer(4, "Name4", "J3rn");

        mPlayers = mDbHelper.mPlayerTable.getAllPlayers();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.login_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mPlayerAdapter  = new LoginAdapter(v.getContext(), mPlayers);
        recyclerView.setAdapter(mPlayerAdapter);

        Log.d(TAG, "onCreateView");
        return v;
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id){
        super.onListItemClick(lv, v, position, id);

        //String value = lv.getItemAtPosition(position).toString();
        Context context = v.getContext();
        Intent i = new Intent(context, TabbedActivity.class);
        context.startActivity(i);

        Log.d(TAG, "onListItemClick");
    }
}
