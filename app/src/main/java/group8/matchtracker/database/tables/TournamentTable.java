package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class TournamentTable extends DBTable {

    public TournamentTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE tournaments (id INTEGER PRIMARY KEY, name STRING, " +
                "start INTEGER, end INTEGER, location STRING, organizer STRING)";
        db.execSQL(CREATE_TABLE);
    }



}
