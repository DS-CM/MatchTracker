package group8.matchtracker.services;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import group8.matchtracker.activities.TabMatchFeedFragment;
import group8.matchtracker.data.Match;
import group8.matchtracker.data.Event;
import group8.matchtracker.database.DatabaseHelper;

public class EventUpdateService extends IntentService {

    DatabaseHelper dbHelper;
    String tournamentName;

    public EventUpdateService(){
        super("EventUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String API_KEY = "JSDvdSusuXhmamjxPcukkXOhw8fnDeTAyMYroYIV";//intent.getStringExtra("API_KEY");
        int tournamentId = intent.getIntExtra("TOURNAMENT_ID",0);
        dbHelper = new DatabaseHelper(this);

        Log.d("INFO",""+tournamentId);

        Event t = dbHelper.mTournamentTable.getTournament(tournamentId);
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

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(TabMatchFeedFragment.ResponseReceiver.PROCESS_RESPONSE);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                broadcastIntent.putExtra("RESPONSE_MESSAGE", "Done");
                sendBroadcast(broadcastIntent);
            }finally {
                urlConnection.disconnect();
            }
        }catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    private void parseJSON(JSONArray jArray){
        JSONObject match = null;
        try {
            for (int i = 0; i < jArray.length(); i++) {
                match = jArray.getJSONObject(i).getJSONObject("match");
                Match m = dbHelper.mMatchTable.createMatch(match.getInt("round"),
                        match.getString("identifier"),
                        new int[]{0,0}, //replace with real data
                        "1 v 1",    //replace with real data
                        match.getString("location"),
                        match.getString("scheduled_time"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }catch (Exception e){}
    }
}
