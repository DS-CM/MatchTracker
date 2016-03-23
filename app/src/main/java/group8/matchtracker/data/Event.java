package group8.matchtracker.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Event {
    private final String TAG = getClass().toString();

    private int id;
    private String name;
    private int startTime;
    private int endTime;
    private String location; // Change to a different data type later
    private String organizer;
    private String url;
    private List<Tournament> mTournaments;

    public Event() {
        this.mTournaments = new ArrayList<>();
    }

    public Event(int id, String name, int startTime, int endTime, String location, String organizer, String url) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.organizer = organizer;
        this.url = url;
        this.mTournaments = new ArrayList<>();
    }

    public Event(Cursor cursor, String url){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.startTime = cursor.getInt(cursor.getColumnIndex("start"));
        this.endTime = cursor.getInt(cursor.getColumnIndex("end"));
        this.location = cursor.getString(cursor.getColumnIndex("location"));
        this.organizer = cursor.getString(cursor.getColumnIndex("organizer"));
        this.url = url;
        this.mTournaments = new ArrayList<>();
    }

    public void addEvent(Tournament tournament) {
        this.mTournaments.add(tournament);
    }

    /* SETTERS */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /* GETTERS */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getUrl() { return url; }

    public List<Tournament> getTournaments() {
        return mTournaments;
    }

}
