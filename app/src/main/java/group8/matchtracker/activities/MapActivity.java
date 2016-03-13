package group8.matchtracker.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import group8.matchtracker.DirectionsJSONParser;
import group8.matchtracker.R;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ArrayList<LatLng> markerPoints;
    private String KEY = "AIzaSyAKChdKLPDuzi1EiDXNmdCTtkQm9us-iQA";
    private LatLng origin = new LatLng(40.048445, -83.016127);
    private LatLng destination = new LatLng(39.999469, -83.012756);
    private LatLng camera = new LatLng((origin.latitude+destination.latitude)/2, (origin.longitude+destination.longitude)/2);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        markerPoints = new ArrayList<>();

        ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 13));
        showDirections(parseDirections(downloadUrl(getDirectionUrl(origin, destination))));
    }

    private String getDirectionUrl(LatLng origin, LatLng destination){
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+destination.latitude+","+destination.longitude;
        String parameters = str_origin+"&"+str_dest+"&key=AIzaSyAKChdKLPDuzi1EiDXNmdCTtkQm9us-iQA";
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String urlStr){
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(urlStr);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            iStream.close();
            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Url exception", e.toString());
        }finally{
            urlConnection.disconnect();
        }
        return data;
    }

    private PolylineOptions parseDirections(String json){
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>();
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        MarkerOptions markerOptions = new MarkerOptions();

        try{
            jObject = new JSONObject(json);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        }catch(Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<routes.size();i++){
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = routes.get(i);

            // Fetching all the points in i-th route
            for(int j=0;j<path.size();j++){
                HashMap<String,String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(2);
            lineOptions.color(Color.RED);
        }
        return lineOptions;
    }

    public void showDirections(PolylineOptions pOptions){
        googleMap.addMarker(new MarkerOptions().position(origin));
        googleMap.addMarker(new MarkerOptions().position(destination));
        if(pOptions != null) {
            Polyline line = googleMap.addPolyline(pOptions);
        }
    }
}
