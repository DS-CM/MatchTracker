package group8.matchtracker.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import group8.matchtracker.adapters.EventListAdapter;
import group8.matchtracker.async.RetrieveTournamentsTask;
import group8.matchtracker.data.Event;
import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class EventListFragment extends Fragment implements SearchView.OnQueryTextListener  {
    private final String TAG = getClass().getSimpleName();
    private EventListAdapter mEventListAdapter;
    protected ArrayList<Event> mEvents = new ArrayList<>();
    protected DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;
    private String API_KEY = "JSDvdSusuXhmamjxPcukkXOhw8fnDeTAyMYroYIV";

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.event_list_fragment, container, false);
        mDbHelper = new DatabaseHelper(v.getContext());

        mRecyclerView = (RecyclerView) v.findViewById(R.id.event_list_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mDbHelper.mTournamentInEventTable.deleteAll(); // TODO - remove
        mDbHelper.mEventTable.deleteAll(); // TODO - remove
        mDbHelper.mTournamentTable.deleteAll(); // TODO - remove

        String API_URL = "https://api.challonge.com/v1/tournaments.json?api_key="+API_KEY;
        /*
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonTournament = jsonArray.getJSONObject(i).getJSONObject("tournament");

                        int tChallongeId = jsonTournament.getInt("id");
                        String tName = jsonTournament.getString("name");
                        String tUrl = jsonTournament.getString("url");

                        Event event = mDbHelper.mEventTable.create(tName, 0, 0, "Ohio Union", "esi");
                        Tournament tournament = mDbHelper.mTournamentTable.create(tChallongeId, tName, tUrl);
                        mDbHelper.mTournamentInEventTable.create(event.getId(), tournament.getId());

                        event.addTournament(tournament);
                        mEvents.add(event);
                        populateList(v);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "ERROR: When retrieving tournaments");
                    Log.d(TAG, "Report: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonRequest);*/
        RetrieveTournamentsTask rt = new RetrieveTournamentsTask();
        rt.setJsonDownloadListener(new RetrieveTournamentsTask.JsonDownloadListener() {
            @Override
            public void jsonDownloadedSuccessfully(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonTournament = jsonArray.getJSONObject(i).getJSONObject("tournament");

                        int tChallongeId = jsonTournament.getInt("id");
                        String tName = jsonTournament.getString("name");
                        String tUrl = jsonTournament.getString("url");

                        Event event = mDbHelper.mEventTable.create(tName, 0, 0, "Ohio Union", "esi");
                        Tournament tournament = mDbHelper.mTournamentTable.create(tChallongeId, tName, tUrl);
                        mDbHelper.mTournamentInEventTable.create(event.getId(), tournament.getId());

                        event.addTournament(tournament);
                        mEvents.add(event);
                        populateList(v);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "ERROR: When retrieving tournaments");
                    Log.d(TAG, "Report: " + e.toString());
                }
            }
        });
        rt.execute();

        // TODO - Remove
/*        mDbHelper.mEventTable.create("Shuffle VIII", 03122016, 03132016, "Ohio Union", "eSports Initiative");
        mDbHelper.mEventTable.create("Big House", 05032016, 05042016, "U of M", "The school up north");
        mDbHelper.mEventTable.create("EVO", 22, 23, "Cali", "EVO LLC");*/

        //mEvents = mDbHelper.mEventTable.readAll();
        populateList(v);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.search_menu, menu);

        //final MenuItem item = menu.findItem(R.id.search_menu_action);
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
                mEventListAdapter.setFilter(mEvents);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_refresh:
                //executeRetrievePlayerTask();
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
        final List<Event> filteredPlayerList = filter(mEvents, newText);
        mEventListAdapter.setFilter(filteredPlayerList);
        return true;
    }

    public void populateList(View v) {
        mEvents = mDbHelper.mEventTable.readAll();
        mEventListAdapter = new EventListAdapter(v.getContext(), mEvents);
        mRecyclerView.setAdapter(mEventListAdapter);
    }

    private List<Event> filter(List<Event> events, String query){
        query = query.toLowerCase();

        final List<Event> filteredEventList = new ArrayList<>();
        for(Event event : events){
            final String eventName = event.getName().toLowerCase();
            if(eventName.contains(query)){
                filteredEventList.add(event);
            }
        }
        return filteredEventList;
    }
}
