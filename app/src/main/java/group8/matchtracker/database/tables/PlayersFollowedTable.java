package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class PlayersFollowedTable extends DBTable {

    public PlayersFollowedTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE players_followed (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " pid INTEGER, fpid INTEGER, FOREIGN KEY (pid) REFERENCES players(id), FOREIGN KEY (fpid) " +
                "REFERENCES players(id))";
        db.execSQL(CREATE_TABLE);
    }
}
