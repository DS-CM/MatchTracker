package group8.matchtracker.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
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
import group8.matchtracker.adapters.MatchAdapter;
import group8.matchtracker.async.RetrieveMatchesForPlayerTask;
import group8.matchtracker.async.RetrievePlayersTask;
import group8.matchtracker.data.Match;
import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;

public class TabMatchFeedFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private MatchAdapter mMatchAdapter;
    private ArrayList<Match> mMatches = new ArrayList<>();
    private DatabaseHelper mDbHelper;
    private RecyclerView mRecyclerView;
    protected View v;
    private long tid;
    protected long pid;
    protected Player player;

    public TabMatchFeedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.matchBroadcastReceiver, new IntentFilter("bcReceiver"));
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            Log.d(TAG, "Event ID: "+extras.getLong("EID"));
            Log.d(TAG, "Tournament ID: "+extras.getLong("TID"));
            Log.d(TAG, "Player ID: "+extras.getLong("PID"));
        }
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v = inflater.inflate(R.layout.feed_of_matches_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.feed_of_matches_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //TODO - Change away from this
        mDbHelper = new DatabaseHelper(v.getContext());

        tid = getActivity().getIntent().getLongExtra("TID", 0);
        pid = getActivity().getIntent().getLongExtra("PID", 0);
        Log.d(TAG, "Tournament ID 3: "+tid);
        Log.d(TAG, "Player ID 3: "+pid);
        Log.d(TAG, "Has any players: " + mDbHelper.mPlayerTable.hasData());
        player = mDbHelper.mPlayerTable.read(pid);
        executeRetrieveMatchTask();
        //mDbHelper.mMatchTable.createMatch(0, "A", new int[]{0, 0}, "BO5", "Seat 33", "12:00pm");

        //Intent updateEvent = new Intent(this.getContext(),MatchUpdateService.class);
        //updateEvent.putExtra(DatabaseHelper.TOURNAMENT_ID, ((TabbedActivity) getActivity()).getTid());
        //getContext().startService(updateEvent);

        //mMatches = mDbHelper.query.readMatchesOfPlayer(pid);
        populateList(v);

        Log.d(TAG, "onCreateView");
        return v;
    }

    public void populateList(View v){
        mMatches = mDbHelper.mMatchTable.readAll();
        mMatchAdapter = new MatchAdapter(v.getContext(), mMatches);
        mRecyclerView.setAdapter(mMatchAdapter);
    }

    BroadcastReceiver matchBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mMatches = mDbHelper.query.readMatchesOfPlayer(pid);
            //mMatches = mDbHelper.mMatchTable.readAll();
            populateList(v);
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        getActivity().unregisterReceiver(matchBroadcastReceiver);
    }

    private void executeRetrieveMatchTask(){
        RetrieveMatchesForPlayerTask rp = new RetrieveMatchesForPlayerTask();
        rp.setJsonDownloadListener(new RetrieveMatchesForPlayerTask.JsonDownloadListener() {
            @Override
            public void jsonDownloadedSuccessfully(JSONArray jsonArray) {
                // TODO (David): \/ replace with real data \/
                //int[] result = new int[]{2, 3};
                String type = "1 v 1";
                String location = "setup #25";
                String time = "12:01pm";

                mDbHelper.mPlayersInMatchTable.deleteAll(); // TODO - remove
                mDbHelper.mMatchesInTournamentTable.deleteAll(); // TODO - remove
                mDbHelper.mMatchTable.deleteAll(); // TODO - remove

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonMatch = jsonArray.getJSONObject(i).getJSONObject("match");
                        Log.d(TAG, "Match: " + jsonMatch.toString());

                        int challongeId = jsonMatch.getInt("id");
                        int round = jsonMatch.getInt("round");
                        String identifier = jsonMatch.getString("identifier");
                        int p1ChallongeId = jsonMatch.getInt("player1_id");
                        int p2ChallongeId = jsonMatch.getInt("player2_id");
                        //String type = jsonMatch.getString("scores_csv");
                        String sResults = jsonMatch.getString("scores_csv");

                        Log.d(TAG, "Got here " + type);

                        Match match = mDbHelper.mMatchTable.create(challongeId, round, identifier, getResults(sResults), type, location, time, new int[]{p1ChallongeId, p2ChallongeId});
                        mDbHelper.mMatchesInTournamentTable.create(tid, match.getId());

                        //Player p1 = mDbHelper.mPlayerTable.readPlayerByChallongeId(p1ChallongeId);
                        //Player p2 = mDbHelper.mPlayerTable.readPlayerByChallongeId(p2ChallongeId);
                        Player n = new Player(0, 123, "Joe", "joe");
                        //match.addPlayer(n);
                        //match.addPlayer(n);

                        Log.d(TAG, "Match: " + match.getId() + ", " + match.getType());

                        //mDbHelper.mPlayersInMatchTable.create(match.getId(), p1.getId());
                        //mDbHelper.mPlayersInMatchTable.create(match.getId(), p2.getId());
                    }
                    populateList(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rp.execute(mDbHelper.mTournamentTable.read(tid).getUrl(), String.valueOf(player.getChallongeId()));
    }

    private int[] getResults(String sResults){
        if(sResults.equals("")){
            return new int[]{0,0};
        }
        int s1 = Integer.parseInt(sResults.substring(0,1));
        int s2 = Integer.parseInt(sResults.substring(2));
        return new int[]{s1,s2};
    }
}
