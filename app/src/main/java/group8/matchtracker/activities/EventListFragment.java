package group8.matchtracker.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

import group8.matchtracker.R;
import group8.matchtracker.adapters.EventListAdapter;
import group8.matchtracker.async.RetrieveTournamentsTask;
import group8.matchtracker.data.Event;
import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class EventListFragment extends Fragment {
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

/*        String API_URL = "https://api.challonge.com/v1/tournaments.json?api_key="+API_KEY;

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

        mEvents = mDbHelper.mEventTable.readAll();
        populateList(v);


        return v;
    }

    public void populateList(View v) {
        mEventListAdapter = new EventListAdapter(v.getContext(), mEvents);
        mRecyclerView.setAdapter(mEventListAdapter);
    }
}
