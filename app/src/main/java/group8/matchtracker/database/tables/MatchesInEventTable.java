package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class MatchesInEventTable extends DBTable {

    public MatchesInEventTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE matches_in_event (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " mid INTEGER, eid INTEGER, FOREIGN KEY (mid) REFERENCES matches(id), FOREIGN KEY (eid) " +
                "REFERENCES events(id))";
        db.execSQL(CREATE_TABLE);
    }
}
