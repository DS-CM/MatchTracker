package group8.matchtracker.data;

import android.database.Cursor;

/**
 * Created by dsoll on 2/19/2016.
 */
public class Match {
    private int id;
    private int round;
    private String identifier;
    private String result; // Fix the data type

    public Match(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.round = cursor.getInt(cursor.getColumnIndex("round"));
        this.identifier = cursor.getString(cursor.getColumnIndex("identifier"));
        this.result = cursor.getString(cursor.getColumnIndex("result"));
    }
}
