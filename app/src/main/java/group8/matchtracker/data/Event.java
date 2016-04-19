package group8.matchtracker.data;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Event {
    private final String TAG = getClass().toString();

    private long id;
    private String name;
    private String startTime;
    private String endTime;
    private String location; // Change to a different data type later
    private String organizer;
    private List<Tournament> mTournaments;

    public Event() {
        this.mTournaments = new ArrayList<>();
    }

    public Event(long id, String name, String startTime, String endTime, String location, String organizer) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.organizer = organizer;
        this.mTournaments = new ArrayList<>();
    }

    public Event(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.startTime = cursor.getString(cursor.getColumnIndex("start"));
        this.endTime = cursor.getString(cursor.getColumnIndex("end"));
        this.location = cursor.getString(cursor.getColumnIndex("location"));
        this.organizer = cursor.getString(cursor.getColumnIndex("organizer"));
        this.mTournaments = new ArrayList<>();
    }

    public void addTournament(Tournament tournament) {
        this.mTournaments.add(tournament);
    }

    /* SETTERS */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /* GETTERS */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String  getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }

    public List<Tournament> getTournaments() {
        return mTournaments;
    }

    @Override
    public boolean equals(Object event){
        Event e = (Event) event;
        return (id == e.getId() && name.equals(e.getName()) && startTime.equals(e.getStartTime())
                && endTime.equals(e.getEndTime()) && location.equals(e.getLocation())
                && organizer.equals(e.getOrganizer()));
    }
}
