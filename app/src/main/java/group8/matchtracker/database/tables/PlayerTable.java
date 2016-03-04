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

    public Player createPlayer(int id, String name, String ign){
        ContentValues values = new ContentValues();
        values.put(mDbHelper.PLAYER_ID, id);
        values.put(mDbHelper.PLAYER_NAME, name);
        values.put(mDbHelper.PLAYER_IGN, ign);

        long insertId = mDatabase.insert(mDbHelper.TABLE_PLAYER, null, values);
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_PLAYER, mAllColumns, mDbHelper.PLAYER_ID
                +" = "+insertId, null,null,null,null);
        cursor.moveToFirst();
        Player newPlayer = new Player(cursor);
        cursor.close();

        return newPlayer;
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList listPlayers = new ArrayList<>();
        Cursor cursor = mDatabase.query(mDbHelper.TABLE_PLAYER, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                listPlayers.add(new Player(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listPlayers;
    }
}
