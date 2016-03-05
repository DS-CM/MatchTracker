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

    public Match() {
        // Empty :)
    }

    public Match(int id, int round, String result, String identifier) {
        this.id = id;
        this.round = round;
        this.result = result;
        this.identifier = identifier;
    }

    public Match(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.round = cursor.getInt(cursor.getColumnIndex("round"));
        this.identifier = cursor.getString(cursor.getColumnIndex("identifier"));
        this.result = cursor.getString(cursor.getColumnIndex("result"));
    }

    /* SETTERS */
    public void setId(int id) {
        this.id = id;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /* GETTERS */
    public int getId() {
        return id;
    }

    public int getRound() {
        return round;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getResult() {
        return result;
    }
}
