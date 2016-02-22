package group8.matchtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import group8.matchtracker.data.Tournament;

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

    // Tournament
    private static final String TOURNAMENT_ID = "tid";
    private static final String TOURNAMENT_NAME = "name";
    private static final String TOURNAMENT_START = "start";
    private static final String TOURNAMENT_END = "end";
    private static final String TOURNAMENT_LOCATION = "location";
    private static final String TOURNAMENT_ORGANIZER = "organizer";


    public DatabaseHelper(Context context) {
        this.context = context;
        MatchTrackerOpenHelper openHelper = new MatchTrackerOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public void insert(){
        /*TODO: Implement insert function*/
    }

    // Tournament
    public long createTournament(Tournament tournament) {
        ContentValues values = new ContentValues();
        values.put(TOURNAMENT_NAME, tournament.getName());
        values.put(TOURNAMENT_START, tournament.getStartTime());
        values.put(TOURNAMENT_END, tournament.getEndTime());
        values.put(TOURNAMENT_LOCATION, tournament.getLocation());
        values.put(TOURNAMENT_ORGANIZER, tournament.getOrganizer());

        // insert row
        long tournament_ID = this.db.insert(TABLE_TOURNAMENT, null, values);

        return tournament_ID;
    }

    public Tournament getTournament(long tournament_ID) {
        String selectQuery = "SELECT  * FROM " + TABLE_TOURNAMENT + " WHERE "
                + TOURNAMENT_ID + " = " + tournament_ID;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Tournament tournament = new Tournament();
        tournament.setId(c.getInt(c.getColumnIndex(TOURNAMENT_ID)));
        tournament.setName((c.getString(c.getColumnIndex(TOURNAMENT_NAME))));
        tournament.setStartTime(c.getInt(c.getColumnIndex(TOURNAMENT_START)));
        tournament.setEndTime(c.getInt(c.getColumnIndex(TOURNAMENT_END)));
        tournament.setLocation((c.getString(c.getColumnIndex(TOURNAMENT_LOCATION))));
        tournament.setOrganizer((c.getString(c.getColumnIndex(TOURNAMENT_ORGANIZER))));

        return tournament;
    }

    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<Tournament>();
        String selectQuery = "SELECT * FROM " + TABLE_TOURNAMENT;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Tournament tournament = new Tournament();
                tournament.setId(c.getInt(c.getColumnIndex(TOURNAMENT_ID)));
                tournament.setName((c.getString(c.getColumnIndex(TOURNAMENT_NAME))));
                tournament.setStartTime(c.getInt(c.getColumnIndex(TOURNAMENT_START)));
                tournament.setEndTime(c.getInt(c.getColumnIndex(TOURNAMENT_END)));
                tournament.setLocation((c.getString(c.getColumnIndex(TOURNAMENT_LOCATION))));
                tournament.setOrganizer((c.getString(c.getColumnIndex(TOURNAMENT_ORGANIZER))));

                // adding to Tournament List
                tournaments.add(tournament);
            } while (c.moveToNext());
        }

        return tournaments;
    }

    public int updateTournament(Tournament tournament) {
        ContentValues values = new ContentValues();
        values.put(TOURNAMENT_NAME, tournament.getName());
        values.put(TOURNAMENT_START, tournament.getStartTime());
        values.put(TOURNAMENT_END, tournament.getEndTime());
        values.put(TOURNAMENT_LOCATION, tournament.getLocation());
        values.put(TOURNAMENT_ORGANIZER, tournament.getOrganizer());

        // updating row
        return db.update(TABLE_TOURNAMENT, values, TOURNAMENT_ID + " = ?",
                new String[] { String.valueOf(tournament.getId()) });
    }

    public void deleteTournament(long tournament_ID) {
        db.delete(TABLE_TOURNAMENT, TOURNAMENT_ID + " = ?",
                new String[] { String.valueOf(tournament_ID) });
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
            db.execSQL("CREATE TABLE "+TABLE_TOURNAMENT+"("+TOURNAMENT_ID+" INTEGER PRIMARY KEY, "+TOURNAMENT_NAME+" TEXT, "+TOURNAMENT_START+" LONG, "+TOURNAMENT_END+" LONG, "+TOURNAMENT_LOCATION+" TEXT, "+TOURNAMENT_ORGANIZER+" TEXT)");
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