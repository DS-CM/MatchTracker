package group8.matchtracker.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import group8.matchtracker.data.Match;
import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;

public class MatchUpdateService extends IntentService {

    DatabaseHelper dbHelper;
    String tournamentName;
    Tournament mTournament;

    public MatchUpdateService(){
        super("MatchUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String API_KEY = "JSDvdSusuXhmamjxPcukkXOhw8fnDeTAyMYroYIV";//intent.getStringExtra("API_KEY");
        long tid = intent.getLongExtra(DatabaseHelper.TOURNAMENT_ID, 0);
        dbHelper = new DatabaseHelper(this);

        Log.d("INFO",""+tid);

        mTournament = dbHelper.mTournamentTable.getTournament(tid);
        tournamentName = mTournament.getUrl();

        try{
            String API_URL = "https://api.challonge.com/v1/tournaments/"+tournamentName+"/matches.json?api_key="+API_KEY;

            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine())!= null){
                    stringBuilder.append(line).append("\n");
                }
                in.close();
                parseJSON(new JSONArray(stringBuilder.toString()));
            }finally {
                urlConnection.disconnect();
            }
        }catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    private void parseJSON(JSONArray jArray){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // TODO (David): \/ replace with real data \/
        int[] result = new int[]{0,0};
        String type = "1 v 1";
        String location = "setup #13";
        String time = "12:00pm";

        dbHelper.mMatchesInTournamentTable.deleteAll(); // TODO - remove
        dbHelper.mMatchTable.clearTable(); // TODO - remove

        try {
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonMatch = jArray.getJSONObject(i).getJSONObject("match");

                int challongeId = jsonMatch.getInt("id");
                int round = jsonMatch.getInt("round");
                String identifier = jsonMatch.getString("identifier");

                Match match  = dbHelper.mMatchTable.createMatch(challongeId, round, identifier, result, type, location, time);
                dbHelper.mMatchesInTournamentTable.createMIT(mTournament.getId(), match.getId());
            }
        }catch(JSONException e){
            e.printStackTrace();
        }catch (Exception e){}

        this.sendBroadcast(new Intent().setAction("bcReceiver"));
    }
}
