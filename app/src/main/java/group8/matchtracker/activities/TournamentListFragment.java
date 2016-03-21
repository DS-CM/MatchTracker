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
import group8.matchtracker.adapters.TournamentListAdapter;
import group8.matchtracker.async.RetrieveTournamentsTask;
import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;


public class TournamentListFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private TournamentListAdapter mTournamentListAdapter;
    protected ArrayList<Tournament> mTournaments = new ArrayList<>();
    private DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;

    public TournamentListFragment() {
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
        final View v =  inflater.inflate(R.layout.tournament_list_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.tournament_list_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        RetrieveTournamentsTask rt = new RetrieveTournamentsTask();
        rt.setJsonDownloadListener(new RetrieveTournamentsTask.JsonDownloadListener() {
            @Override
            public void jsonDownloadedSuccessfully(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        mTournaments.add(mDbHelper.mTournamentTable.createTournament(object.getJSONObject("tournament").getString("name"), 0, 0, "Ohio Union", "esi"));
                        populateList(v);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rt.execute();

        // TODO - Remove
        mDbHelper = new DatabaseHelper(v.getContext());
/*        mDbHelper.mTournamentTable.createTournament("Shuffle VIII", 03122016, 03132016, "Ohio Union", "eSports Initiative");
        mDbHelper.mTournamentTable.createTournament("Big House", 05032016, 05042016, "U of M", "The school up north");
        mDbHelper.mTournamentTable.createTournament("EVO", 22, 23, "Cali", "EVO LLC");*/

        mTournaments = mDbHelper.mTournamentTable.getAllTournaments();
        populateList(v);


        return v;
    }

    public void populateList(View v){
        mTournamentListAdapter = new TournamentListAdapter(v.getContext(), mTournaments);
        mRecyclerView.setAdapter(mTournamentListAdapter);
    }
}
