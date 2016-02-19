package group8.matchtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.List;

public class DatabaseHelper {
    private static final String DATABASE_NAME = "MatchTracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TOURNAMENT = "Tournaments";
    private static final String TABLE_EVENT = "Events";
    private static final String TABLE_MATCH = "Matches";
    private static final String TABLE_PLAYER = "Players";
    private static final String TABLE_PLAYERS_IN_EVENT = "players_in_event";
    private static final String TABLE_PLAYERS_IN_MATCH = "players_in_match";
    private static final String TABLE_MATCHES_IN_EVENT = "matches_in_event";
    private static final String TABLE_EVENTS_IN_TOURNAMENT = "events_in_tournaments";
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt; /*TODO: create insert statements for each table*/
    private static final String INSERT = "" ; /*TODO: write insert strings for each table*/

    public DatabaseHelper(Context context) {
        this.context = context;
        MatchTrackerOpenHelper openHelper = new MatchTrackerOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public void insert(){
        /*TODO: Implement insert function*/
    }

    public void deleteAll(){
        this.db.delete(TABLE_TOURNAMENT, null, null);
        this.db.delete(TABLE_EVENT, null, null);
        this.db.delete(TABLE_MATCH, null, null);
        this.db.delete(TABLE_PLAYER, null, null);
        this.db.delete(TABLE_PLAYERS_IN_EVENT, null, null);
        this.db.delete(TABLE_PLAYERS_IN_MATCH, null, null);
        this.db.delete(TABLE_MATCHES_IN_EVENT, null, null);
        this.db.delete(TABLE_EVENTS_IN_TOURNAMENT, null, null);
    }

    private static class MatchTrackerOpenHelper extends SQLiteOpenHelper{
        MatchTrackerOpenHelper(Context context){
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE "+TABLE_TOURNAMENT+"(tid INTEGER PRIMARY KEY, name TEXT, start TEXT, end TEXT, location TEXT, organizer TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_EVENT+"(eid INTEGER PRIMARY KEY, name TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_MATCH+"(mid INTEGER PRIMARY KEY, round TEXT, identifier TEXT, result TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_PLAYER+"(pid INTEGER PRIMARY KEY, name TEXT, ign TEXT)");
            db.execSQL("CREATE TABLE "+TABLE_PLAYERS_IN_EVENT+"(pid INTEGER FOREIGN KEY, eid INTEGER FOREIGN KEY)");
            db.execSQL("CREATE TABLE "+TABLE_PLAYERS_IN_MATCH+"(pid INTEGER FOREIGN KEY, mid INTEGER FOREIGN KEY)");
            db.execSQL("CREATE TABLE "+TABLE_EVENTS_IN_TOURNAMENT+"(eid INTEGER FOREIGN KEY, tid INTEGER FOREIGN KEY)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w("Example", "Upgrading database; this drops & recreates tables.");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOURNAMENT);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENT);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_MATCH);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYER);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYERS_IN_EVENT);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYERS_IN_MATCH);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS_IN_TOURNAMENT);
        }
    }
}