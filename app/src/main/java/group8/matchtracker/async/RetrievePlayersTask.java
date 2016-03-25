package group8.matchtracker.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrievePlayersTask extends AsyncTask<String, Void, String> {

    private String API_KEY = "JSDvdSusuXhmamjxPcukkXOhw8fnDeTAyMYroYIV";
    private String tournament_url;
    JsonDownloadListener jsonDownloadListener;

    public void setJsonDownloadListener(JsonDownloadListener jsonDownloadListener){
        this.jsonDownloadListener = jsonDownloadListener;
    }

    @Override
    protected String doInBackground(String... params) {
        tournament_url = params[0];
        try{
            String API_URL = "https://api.challonge.com/v1/tournaments/"+tournament_url+"/participants.json?api_key="+API_KEY;

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
                return stringBuilder.toString();
            }finally {
                urlConnection.disconnect();
            }
        }catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response){
        if(response == null){
            Log.i("INFO","THERE WAS AN ERROR");
        }else{
            try{
                jsonDownloadListener.jsonDownloadedSuccessfully(new JSONArray(response));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

    }
    public interface JsonDownloadListener {
        void jsonDownloadedSuccessfully(JSONArray json);
    }
}