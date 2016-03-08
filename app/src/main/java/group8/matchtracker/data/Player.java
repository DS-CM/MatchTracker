package group8.matchtracker.data;

import android.database.Cursor;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Player {
    private final String TAG = getClass().toString();

    private long id;
    private String name;
    private String ign; // In Game Name

    public Player() {
        // Empty :)
    }

    public Player(long id, String name, String ign) {
        this.id = id;
        this.name = name;
        this.ign = ign;
    }

    public Player(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.ign = cursor.getString(cursor.getColumnIndex("ign"));
    }

    /* SETTERS */
    public void setId(long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public String getIgn() {
        return ign;
    }
}
