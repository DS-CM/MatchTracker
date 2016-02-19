package group8.matchtracker.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Tournament {
    private int tID;
    private String name;
    private Date start;
    private Date end;
    private String location; // Change to a different data type later
    private String organizer;
    private List<Event> events;

    public Tournament(int tID, String name, Date start, Date end, String location, String organizer) {
        this.tID = tID;
        this.name = name;
        this.start = start;
        this.end = end;
        this.location = location;
        this.organizer = organizer;
        this.events = new ArrayList<Event>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    /* GETTERS */
    public int gettID() {
        return tID;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return start;
    }

    public Date getEndTime() {
        return end;
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
