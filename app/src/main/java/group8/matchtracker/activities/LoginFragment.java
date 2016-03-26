package group8.matchtracker.activities;


import android.app.Fragment;

import android.app.SearchManager;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group8.matchtracker.R;
import group8.matchtracker.adapters.PlayerListAdapter;
import group8.matchtracker.async.RetrievePlayersTask;
import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;

public class LoginFragment extends Fragment implements SearchView.OnQueryTextListener {
    private final String TAG = getClass().getSimpleName();
    private PlayerListAdapter mPlayerListAdapter;
    private ArrayList<Player> mPlayers = new ArrayList<>();
    private DatabaseHelper mDbHelper;
    private RecyclerView recyclerView;
    private View v;
    private long tid;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v = inflater.inflate(R.layout.player_login_fragment, container, false);
        tid = getActivity().getIntent().getIntExtra(DatabaseHelper.EVENT_ID, 0);

        recyclerView = (RecyclerView) v.findViewById(R.id.player_login_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        // TODO - Change away from this
        mDbHelper = new DatabaseHelper(v.getContext());
        executeRetrievePlayerTask();

//        mDbHelper.mPlayerTable.createPlayer("Name1", "MrToast");
//        mDbHelper.mPlayerTable.createPlayer("Name2", "MsButter");
//        mDbHelper.mPlayerTable.createPlayer("Name3", "3ygun");
//        mDbHelper.mPlayerTable.createPlayer("Name4", "J3rn");

        mPlayers = mDbHelper.mPlayerTable.getAllPlayers();
        populateList(v);


        Log.d(TAG, "onCreateView");
        return v;
    }

    public void populateList(View v) {
        mPlayerListAdapter = new PlayerListAdapter(v.getContext(), mPlayers);
        recyclerView.setAdapter(mPlayerListAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.search_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mPlayerListAdapter.setFilter(mPlayers);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                executeRetrievePlayerTask();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Player> filteredPlayerList = filter(mPlayers,newText);
        mPlayerListAdapter.setFilter(filteredPlayerList);
        return true;
    }

    private  List<Player> filter(List<Player> players, String query){
        query = query.toLowerCase();

        final List<Player> filteredPlayerList = new ArrayList<>();
        for(Player player:players){
            final String ign = player.getIgn();
            if(ign.contains(query)){
                filteredPlayerList.add(player);
            }
        }
        return filteredPlayerList;
    }

    private void executeRetrievePlayerTask(){
        RetrievePlayersTask rp = new RetrievePlayersTask();
        rp.setJsonDownloadListener(new RetrievePlayersTask.JsonDownloadListener() {
            @Override
            public void jsonDownloadedSuccessfully(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        object = object.getJSONObject("participant");
                        mPlayers.add(mDbHelper.mPlayerTable.createPlayer(object.getString("username"), object.getString("name")));
                        populateList(v);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rp.execute(mDbHelper.mTournamentTable.getTournament(tid).getUrl());
    }
}
