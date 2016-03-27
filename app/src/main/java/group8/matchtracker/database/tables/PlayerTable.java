package group8.matchtracker.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import group8.matchtracker.data.Player;
import group8.matchtracker.database.DatabaseHelper;


public class PlayerTable extends DBTable {

    private String[] mAllColumns = {DatabaseHelper.PLAYER_ID, DatabaseHelper.PLAYER_CHALLONGE_ID, DatabaseHelper.PLAYER_NAME, DatabaseHelper.PLAYER_IGN};

    public PlayerTable(Context context, DatabaseHelper dbHelper){
        super(context, dbHelper);
        clearTable(); /*TODO: Get rid of this line eventually*/
    }

    public Player createPlayer(int challongeId, String name, String ign){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PLAYER_CHALLONGE_ID, challongeId);
        values.put(DatabaseHelper.PLAYER_NAME, name);
        values.put(DatabaseHelper.PLAYER_IGN, ign);

        int insertId = (int)mDatabase.insert(DatabaseHelper.TABLE_PLAYER, null, values);

        return new Player(insertId, challongeId, name, ign);
    }

    public Player getPlayer(long id){
        Player player = null;
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_PLAYER, mAllColumns, DatabaseHelper.PLAYER_ID
                + " = ?", new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();

            int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
            String ign = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_IGN));
            player = new Player(id, challongeId, name, ign);

            cursor.close();
        }
        return player;
    }

    public Player getPlayer(String ign){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_PLAYER, mAllColumns, DatabaseHelper.PLAYER_IGN
                + " = ?", new String[]{ign},null,null,null);
        Player currentPlayer = null;

        if(cursor != null){
            cursor.moveToFirst();

            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.PLAYER_ID));
            int challongeId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PLAYER_CHALLONGE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAYER_NAME));
            currentPlayer = new Player(id, challongeId, name, ign);

            cursor.close();
        }
        return currentPlayer;
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> listPlayers = new ArrayList<>();
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_PLAYER, mAllColumns, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = cursor.getLong(cursor.getColumnIndex(mDbHelper.PLAYER_ID));
                int challongeId = cursor.getInt(cursor.getColumnIndex(mDbHelper.PLAYER_CHALLONGE_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDbHelper.PLAYER_NAME));
                String ign = cursor.getString(cursor.getColumnIndex(mDbHelper.PLAYER_IGN));
                listPlayers.add(new Player(id, challongeId, name, ign));

                cursor.moveToNext();
            }
            cursor.close();
        }
        return listPlayers;
    }

    public boolean isInTable(String ign){
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_PLAYER, mAllColumns, DatabaseHelper.PLAYER_IGN
                + " = ?", new String[]{ign},null,null,null);

        return cursor.getCount() > 0;
    }

    public void clearTable(){
        mDatabase.execSQL("delete from "+DatabaseHelper.TABLE_PLAYER);
    }
}
