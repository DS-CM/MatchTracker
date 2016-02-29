package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class PlayerTable extends DBTable {

    public PlayerTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE players (id INTEGER PRIMARY KEY, name STRING, ign STRING)";
        db.execSQL(CREATE_TABLE);
    }
}
