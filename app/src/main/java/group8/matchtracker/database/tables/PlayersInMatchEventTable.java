package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class PlayersInMatchEventTable extends DBTable {

    public PlayersInMatchEventTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE players_in_match (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " pid INTEGER, mid INTEGER, FOREIGN KEY (pid) REFERENCES players(id), FOREIGN KEY (mid) " +
                "REFERENCES matches(id))";
        db.execSQL(CREATE_TABLE);
    }
}
