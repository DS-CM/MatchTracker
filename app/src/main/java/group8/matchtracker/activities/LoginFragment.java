package group8.matchtracker.activities;


import android.app.Fragment;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

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
    private long eid;
    private String API_KEY = "JSDvdSusuXhmamjxPcukkXOhw8fnDeTAyMYroYIV";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v = inflater.inflate(R.layout.player_login_fragment, container, false);
        eid = getActivity().getIntent().getLongExtra("EID", 0);

        recyclerView = (RecyclerView) v.findViewById(R.id.player_login_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        // TODO - Change away from this
        mDbHelper = new DatabaseHelper(v.getContext());

        tid = mDbHelper.mTournamentInEventTable.readTIDs(eid).get(0);

        Log.d(TAG, "No tournaments: "+mDbHelper.mTournamentTable.hasData());
        executeRetrievePlayerTask();

//        mDbHelper.mPlayerTable.create("Name1", "MrToast");
//        mDbHelper.mPlayerTable.create("Name2", "MsButter");
//        mDbHelper.mPlayerTable.create("Name3", "3ygun");
//        mDbHelper.mPlayerTable.create("Name4", "J3rn");

        //mPlayers = mDbHelper.mPlayerTable.readAll();
        populateList(v);


        Log.d(TAG, "onCreateView");
        return v;
    }

    public void populateList(View v) {
        mPlayers = mDbHelper.mPlayerTable.readAll();
        mPlayerListAdapter = new PlayerListAdapter(v.getContext(), mPlayers, eid, tid);
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
        /*switch (item.getItemId()){
            case R.id.action_refresh:
                executeRetrievePlayerTask();
                return true;
        }*/

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
        for(Player player : players){
            final String ign = player.getIgn().toLowerCase();
            if(ign.contains(query)){
                filteredPlayerList.add(player);
            }
        }
        return filteredPlayerList;
    }

    private void executeRetrievePlayerTask(){
        String API_URL = "https://api.challonge.com/v1/tournaments/"+mDbHelper.mTournamentTable.read(tid).getUrl()+"/participants.json?api_key="+API_KEY;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                mDbHelper.mPlayersInTournamentTable.deleteAll();
                mDbHelper.mPlayerTable.deleteAll();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonPlayer = jsonArray.getJSONObject(i).getJSONObject("participant");

                        if(!(mDbHelper.mPlayerTable.isInTable(jsonPlayer.getString("name")))) {
                            int challongeId = jsonPlayer.getInt("id");
                            String name = jsonPlayer.getString("username");
                            String ign = jsonPlayer.getString("name");
                            Player player = mDbHelper.mPlayerTable.create(challongeId, name, ign);

                            mPlayers.add(player);
                            mDbHelper.mPlayersInTournamentTable.create(tid, player.getId());
                        }
                    }
                    populateList(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonRequest);

        /*
        RetrievePlayersTask rp = new RetrievePlayersTask();
        rp.setJsonDownloadListener(new RetrievePlayersTask.JsonDownloadListener() {
            @Override
            public void jsonDownloadedSuccessfully(JSONArray jsonArray) {
                mDbHelper.mPlayersInTournamentTable.deleteAll();
                mDbHelper.mPlayerTable.deleteAll();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonPlayer = jsonArray.getJSONObject(i).getJSONObject("participant");

                        if(!(mDbHelper.mPlayerTable.isInTable(jsonPlayer.getString("name")))) {
                            int challongeId = jsonPlayer.getInt("id");
                            String name = jsonPlayer.getString("username");
                            String ign = jsonPlayer.getString("name");
                            Player player = mDbHelper.mPlayerTable.create(challongeId, name, ign);

                            mPlayers.add(player);
                            mDbHelper.mPlayersInTournamentTable.create(tid, player.getId());
                        }
                    }
                    populateList(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rp.execute(mDbHelper.mTournamentTable.read(tid).getUrl());
        */
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
        Log.d(TAG, "OnPause does it gots data: " +mDbHelper.mPlayerTable.hasData());
    }
}
