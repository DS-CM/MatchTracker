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

import group8.matchtracker.data.Tournament;
import group8.matchtracker.database.DatabaseHelper;

public class MatchUpdateService extends IntentService {

    DatabaseHelper dbHelper;
    String tournamentName;

    public MatchUpdateService(){
        super("MatchUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String API_KEY = "JSDvdSusuXhmamjxPcukkXOhw8fnDeTAyMYroYIV";//intent.getStringExtra("API_KEY");
        long tid = intent.getIntExtra(DatabaseHelper.TOURNAMENT_ID, 0);
        dbHelper = new DatabaseHelper(this);

        Log.d("INFO",""+tid);

        Tournament t = dbHelper.mTournamentTable.getTournament(tid);
        tournamentName = t.getUrl();

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
        JSONObject match = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int round;
        String identifier;
        // TODO (David): \/ replace with real data \/
        int[] result = new int[]{0,0};
        String type = "1 v 1";
        String location = "setup #13";
        String time = "12:00pm";

        //database.execSQL("delete from " + dbHelper.TABLE_MATCH); /*TODO: Get rid of this line eventually*/

        //long[] matchsOfEvent = dbHelper.

        try {
            for (int i = 0; i < jArray.length(); i++) {
                match = jArray.getJSONObject(i).getJSONObject("match");

                round = match.getInt("round");
                identifier = match.getString("identifier");

                dbHelper.mMatchTable.createMatch(round, identifier, result, type, location, time);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }catch (Exception e){}

        this.sendBroadcast(new Intent().setAction("bcReceiver"));
    }
}
