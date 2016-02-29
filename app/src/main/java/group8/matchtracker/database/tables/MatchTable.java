package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class MatchTable  extends DBTable {

    public MatchTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE match (id INTEGER PRIMARY KEY, round INTEGER, " +
                "identifier STRING, result STRING)";
        db.execSQL(CREATE_TABLE);
    }
}
