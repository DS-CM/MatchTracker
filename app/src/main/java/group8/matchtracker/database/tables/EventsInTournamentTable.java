package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class EventsInTournamentTable extends DBTable {

    public EventsInTournamentTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE events_in_tournament (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " eid INTEGER, tid INTEGER, FOREIGN KEY (eid) REFERENCES events(id), FOREIGN KEY (tid) " +
                "REFERENCES tournament(id))";
        db.execSQL(CREATE_TABLE);
    }
}
