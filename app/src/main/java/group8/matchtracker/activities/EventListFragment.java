package group8.matchtracker.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.event_list_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.event_list_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mDbHelper = new DatabaseHelper(v.getContext());

        //mDbHelper.mTournamentInEventTable.deleteAll(); // TODO - remove
        mDbHelper.mEventTable.removeAllEvents(); // TODO - remove
        //mDbHelper.mTournamentTable.deleteAll(); // TODO - remove

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

                        Event event = mDbHelper.mEventTable.createEvent(tName, 0, 0, "Ohio Union", "esi");
                        Tournament tournament = mDbHelper.mTournamentTable.createTournament(tChallongeId, tName, tUrl);
                        mDbHelper.mTournamentInEventTable.createTIE(event.getId(), tournament.getId());

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
/*        mDbHelper.mEventTable.createEvent("Shuffle VIII", 03122016, 03132016, "Ohio Union", "eSports Initiative");
        mDbHelper.mEventTable.createEvent("Big House", 05032016, 05042016, "U of M", "The school up north");
        mDbHelper.mEventTable.createEvent("EVO", 22, 23, "Cali", "EVO LLC");*/

        mEvents = mDbHelper.mEventTable.getAllEvents();
        populateList(v);


        return v;
    }

    public void populateList(View v) {
        mEventListAdapter = new EventListAdapter(v.getContext(), mEvents);
        mRecyclerView.setAdapter(mEventListAdapter);
    }
}
