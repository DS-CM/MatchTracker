package group8.matchtracker.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Tournament {
    private int id;
    private String name;
    private int startTime;
    private int endTime;
    private String location; // Change to a different data type later
    private String organizer;
    private List<Event> events;

    public Tournament() {
        this.events = new ArrayList<>();
    }

    public Tournament(int id, String name, int startTime, int endTime, String location, String organizer) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.organizer = organizer;
        this.events = new ArrayList<>();
    }

    public Tournament(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.startTime = cursor.getInt(cursor.getColumnIndex("start"));
        this.endTime = cursor.getInt(cursor.getColumnIndex("end"));
        this.location = cursor.getString(cursor.getColumnIndex("location"));
        this.organizer = cursor.getString(cursor.getColumnIndex("organizer"));
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
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

    public List<Event> getEvents() {
        return events;
    }

}
