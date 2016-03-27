package group8.matchtracker.database.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DBTable {
    protected SQLiteDatabase mDatabase;
    protected Context mContext;

    public DBTable(Context context, SQLiteDatabase database) {
        this.mDatabase = database;
        this.mContext = context;
    }
}