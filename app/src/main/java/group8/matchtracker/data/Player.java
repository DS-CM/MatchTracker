package group8.matchtracker.data;

import android.database.Cursor;


public class Player {
    private final String TAG = getClass().toString();

    private long id;
    private int challongeId;
    private String name;
    private String ign; // In Game Name

    public Player() {
        // Empty :)
    }

    public Player(long id, int challongeId, String name, String ign) {
        this.id = id;
        this.challongeId = challongeId;
        this.name = name;
        this.ign = ign;
    }

    /* SETTERS */
    public void setId(long id) {
        this.id = id;
    }

    public void setChallongeId(int challongeId) {
        this.challongeId = challongeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    /* GETTERS */
    public long getId() {
        return id;
    }

    public int getChallongeId() {
        return challongeId;
    }

    public String getName() {
        return name;
    }

    public String getIgn() {
        return ign;
    }
}
