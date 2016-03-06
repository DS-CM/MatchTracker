package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;


public class PlayerTable extends DBTable {

    private String[] mAllColumns = {mDbHelper.PLAYER_ID, mDbHelper.PLAYER_NAME, mDbHelper.PLAYER_IGN};

    public PlayerTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);

    }

    public Player createPlayer(String name, String ign){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.PLAYER_NAME, name);
        values.put(mDbHelper.PLAYER_IGN, ign);

        long insertId = mDatabase.insert(mDbHelper.TABLE_PLAYER, null, values);

        return new Player(insertId, name, ign);
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> listPlayers = new ArrayList<Player>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_PLAYER, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = cursor.getInt(cursor.getColumnIndex(mDbHelper.PLAYER_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDbHelper.PLAYER_NAME));
                String ign = cursor.getString(cursor.getColumnIndex(mDbHelper.PLAYER_IGN));

                listPlayers.add(new Player(id, name, ign));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listPlayers;
    }
}
