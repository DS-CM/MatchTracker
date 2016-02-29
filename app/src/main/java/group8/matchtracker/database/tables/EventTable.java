package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;


public class EventTable extends DBTable {

    public EventTable(SQLiteDatabase database){
        super(database);
        String CREATE_TABLE = "CREATE TABLE events (id INTEGER PRIMARY KEY, name STRING)";
        db.execSQL(CREATE_TABLE);
    }
}
