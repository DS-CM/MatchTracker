package group8.matchtracker.data;

import android.database.Cursor;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Player {
    private String name;
    private String ign; // In Game Name

    public Player(String name, String ign) {
        this.name = name;
        this.ign = ign;
    }

    public Player(Cursor cursor){
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.ign = cursor.getString(cursor.getColumnIndex("ign"));
    }

    /* GETTERS */
    public String getName() {
        return name;
    }

    public String getIgn() {
        return ign;
    }
}
