package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class PlayersInEventTable extends DBTable {

    public PlayersInEventTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE players_in_event (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " pid INTEGER, eid INTEGER, FOREIGN KEY (pid) REFERENCES players(id), FOREIGN KEY (eid) " +
                "REFERENCES events(id))";
        db.execSQL(CREATE_TABLE);
    }
}
