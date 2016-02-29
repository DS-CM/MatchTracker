package group8.matchtracker.database.tables;

import android.database.sqlite.SQLiteDatabase;

public abstract class DBTable {

    private int id;
    SQLiteDatabase db;

    public DBTable(SQLiteDatabase db){
        this.db = db;
    }

    public int getId(){
        return id;
    }
    protected void setId(int newId){
        id = newId;
    }
}
