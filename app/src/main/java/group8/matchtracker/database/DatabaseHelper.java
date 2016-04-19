package group8.matchtracker.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import group8.matchtracker.database.tables.MatchesInTournamentTable;
import group8.matchtracker.database.tables.PlayersInMatchTable;
import group8.matchtracker.database.tables.PlayersInTournamentTable;
import group8.matchtracker.database.tables.TournamentInEventTable;
import group8.matchtracker.database.tables.TournamentTable;
import group8.matchtracker.database.tables.EventTable;
import group8.matchtracker.database.tables.MatchTable;
import group8.matchtracker.database.tables.PlayerTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();

    private static final String DATABASE_NAME = "MatchTracker.db";
    private static final int DATABASE_VERSION = 11;
    public static final String TABLE_EVENT = "events";
    public static final String TABLE_TOURNAMENT = "tournaments";
    public static final String TABLE_MATCH = "matches";
    public static final String TABLE_PLAYER = "players";
    public static final String TABLE_PLAYERS_IN_TOURNAMENT = "players_in_tournament";
    public static final String TABLE_PLAYERS_IN_MATCH = "players_in_match";
    public static final String TABLE_MATCHES_IN_TOURNAMENT = "matches_in_tournament";
    public static final String TABLE_TOURNAMENT_IN_EVENT = "tournaments_in_event";
    public final QueryHelper query;

    // Public grabbers
    public final EventTable mEventTable;
    public final PlayerTable mPlayerTable;
    public final TournamentTable mTournamentTable;
    public final MatchTable mMatchTable;
    public final PlayersInTournamentTable mPlayersInTournamentTable;
    public final PlayersInMatchTable mPlayersInMatchTable;
    public final MatchesInTournamentTable mMatchesInTournamentTable;
    public final TournamentInEventTable mTournamentInEventTable;

    // Event
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_START = "start";
    public static final String EVENT_END = "end";
    public static final String EVENT_LOCATION = "location";
    public static final String EVENT_ORGANIZER = "organizer";
    public static final String[] TABLE_EVENT_COLUMNS = {
            EVENT_ID, EVENT_NAME,
            EVENT_START, EVENT_END,
            EVENT_LOCATION, EVENT_ORGANIZER
    };

    // Tournament
    public static final String TOURNAMENT_ID = "id";
    public static final String TOURNAMENT_CHALLONGE_ID = "challongeId";
    public static final String TOURNAMENT_NAME = "name";
    public static final String TOURNAMENT_URL = "url";
    public static final String[] TABLE_TOURNAMENT_COLUMNS = {
            TOURNAMENT_ID, TOURNAMENT_CHALLONGE_ID,
            TOURNAMENT_NAME, TOURNAMENT_URL
    };

    // Match
    public static final String MATCH_ID = "id";
    public static final String MATCH_CHALLONGE_ID = "challongeId";
    public static final String MATCH_ROUND = "round";
    public static final String MATCH_IDENTIFIER = "identifier";
    public static final String MATCH_RESULT_1 = "result1";
    public static final String MATCH_RESULT_2 = "result2";
    public static final String MATCH_TYPE = "type";
    public static final String MATCH_LOCATION = "location";
    public static final String MATCH_TIME = "time";
    public static final String MATCH_PLAYER_1 = "pid_1";
    public static final String MATCH_PLAYER_2 = "pid_2";
    public static final String MATCH_PLAYER_3 = "pid_3";
    public static final String MATCH_PLAYER_4 = "pid_4";
    public static final String[] TABLE_MATCH_COLUMNS = {
            MATCH_ID, MATCH_CHALLONGE_ID,
            MATCH_ROUND, MATCH_IDENTIFIER,
            MATCH_RESULT_1, MATCH_RESULT_2,
            MATCH_TYPE, MATCH_LOCATION,
            MATCH_TIME, MATCH_PLAYER_1, MATCH_PLAYER_2,
            MATCH_PLAYER_3, MATCH_PLAYER_4
    };

    // Player
    public static final String PLAYER_ID = "id";
    public static final String PLAYER_CHALLONGE_ID = "challongeId";
    public static final String PLAYER_NAME = "name";
    public static final String PLAYER_IGN = "ign";
    public static final String[] TABLE_PLAYER_COLUMNS = {
            PLAYER_ID, PLAYER_CHALLONGE_ID,
            PLAYER_NAME, PLAYER_IGN
    };

    // p_i_t
    public static final String PIT_TOURNAMENT_ID = "tid";
    public static final String PIT_PLAYER_ID = "pid";
    public static final String[] TABLE_PLAYERS_IN_TOURNAMENT_COLUMNS = {
            PIT_TOURNAMENT_ID, PIT_PLAYER_ID
    };

    // p_i_m
    public static final String PIM_MATCH_ID = "mid";
    public static final String PIM_PLAYER_ID = "pid";
    public static final String[] TABLE_PLAYERS_IN_MATCH_COLUMNS = {
            PIM_MATCH_ID, PIM_PLAYER_ID
    };

    // m_i_t
    public static final String MIT_TOURNAMENT_ID = "tid";
    public static final String MIT_MATCH_ID = "mid";
    public static final String[] TABLE_MATCHS_IN_TOURNAMENT_COLUMNS = {
            MIT_TOURNAMENT_ID, MIT_MATCH_ID
    };

    // t_i_e
    public static final String TIE_EVENT_ID = "eid";
    public static final String TIE_TOURNAMENT_ID = "tid";
    public static final String[] TABLE_TOURNAMENT_IN_EVENT_COLUMNS = {
            TIE_EVENT_ID, TIE_TOURNAMENT_ID
    };


    // Create Strings
    public static final String SQL_CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENT + " ("
            + EVENT_ID + " INTEGER PRIMARY KEY ASC, "
            + EVENT_NAME + " STRING, "
            + EVENT_START + " STRING, "
            + EVENT_END + " STRING, "
            + EVENT_LOCATION + " STRING, "
            + EVENT_ORGANIZER + " STRING)";

    private static final String SQL_CREATE_TABLE_TOURNAMENTS = "CREATE TABLE " + TABLE_TOURNAMENT + " ("
            + TOURNAMENT_ID + " INTEGER PRIMARY KEY ASC, "
            + TOURNAMENT_CHALLONGE_ID + " INTEGER NOT NULL, "
            + TOURNAMENT_NAME + " STRING, "
            + TOURNAMENT_URL + " STRING)";

    public static final String SQL_CREATE_TABLE_MATCHES = "CREATE TABLE " + TABLE_MATCH + " ("
            + MATCH_ID + " INTEGER PRIMARY KEY ASC, "
            + MATCH_CHALLONGE_ID + " INTEGER NOT NULL, "
            + MATCH_ROUND + " INTEGER, "
            + MATCH_IDENTIFIER + " STRING, "
            + MATCH_RESULT_1 + " INTEGER, "
            + MATCH_RESULT_2 + " INTEGER, "
            + MATCH_TYPE + " STRING, "
            + MATCH_LOCATION + " STRING, "
            + MATCH_TIME + " STRING, "
            + MATCH_PLAYER_1 + " INTEGER, "
            + MATCH_PLAYER_2 + " INTEGER, "
            + MATCH_PLAYER_3 + " INTEGER, "
            + MATCH_PLAYER_4 + " INTEGER)";

    private static final String SQL_CREATE_TABLE_PLAYERS = "CREATE TABLE " + TABLE_PLAYER + " ("
            + PLAYER_ID + " INTEGER PRIMARY KEY ASC NOT NULL, "
            + PLAYER_CHALLONGE_ID + " INTEGER NOT NULL, "
            + PLAYER_NAME + " STRING, "
            + PLAYER_IGN + " STRING)";

    private static final String SQL_CREATE_TABLE_PLAYERS_IN_TOURNAMENT = "CREATE Table " + TABLE_PLAYERS_IN_TOURNAMENT + " ("
            + PIT_TOURNAMENT_ID + " INTEGER NOT NULL, "
            + PIT_PLAYER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + PIT_TOURNAMENT_ID + ") REFERENCES " + TABLE_TOURNAMENT + "(" + TOURNAMENT_ID + "), "
            + "FOREIGN KEY (" + PIT_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + "), "
            + "PRIMARY KEY (" + PIT_TOURNAMENT_ID + ", " + PIT_PLAYER_ID + "))";

    private static final String SQL_CREATE_TABLE_PLAYERS_IN_MATCH = "CREATE Table " + TABLE_PLAYERS_IN_MATCH + " ("
            + PIM_MATCH_ID + " INTEGER NOT NULL, "
            + PIM_PLAYER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + PIM_MATCH_ID + ") REFERENCES " + TABLE_MATCH + "(" + MATCH_ID + "), "
            + "FOREIGN KEY (" + PIM_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER + "(" + PLAYER_ID + "), "
            + "PRIMARY KEY (" + PIM_MATCH_ID + ", " + PIM_PLAYER_ID + "))";

    private static final String SQL_CREATE_TABLE_MATCHS_IN_TOURNAMENT = "CREATE Table " + TABLE_MATCHES_IN_TOURNAMENT + " ("
            + MIT_TOURNAMENT_ID + " INTEGER NOT NULL, "
            + MIT_MATCH_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + MIT_TOURNAMENT_ID + ") REFERENCES " + TABLE_TOURNAMENT + "(" + TOURNAMENT_ID + "), "
            + "FOREIGN KEY (" + MIT_MATCH_ID + ") REFERENCES " + TABLE_MATCH + "(" + MATCH_ID + "), "
            + "PRIMARY KEY (" + MIT_TOURNAMENT_ID + ", " + MIT_MATCH_ID + "))";

    private static final String SQL_CREATE_TABLE_TOURNAMENT_IN_EVENT = "CREATE Table " + TABLE_TOURNAMENT_IN_EVENT + " ("
            + TIE_EVENT_ID + " INTEGER NOT NULL, "
            + TIE_TOURNAMENT_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + TIE_EVENT_ID + ") REFERENCES " + TABLE_EVENT + "(" + EVENT_ID + "), "
            + "FOREIGN KEY (" + TIE_TOURNAMENT_ID + ") REFERENCES " + TABLE_TOURNAMENT + "(" + TOURNAMENT_ID + "), "
            + "PRIMARY KEY (" + TIE_EVENT_ID + ", " + TIE_TOURNAMENT_ID + "))";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase database = null;

        try {
            database = this.getWritableDatabase();
        } catch (SQLException e) {
            Log.e("DBTable", "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
            // TODO - Should probably end the app if this happens...
        }

        mEventTable = new EventTable(context, database, TABLE_EVENT, TABLE_EVENT_COLUMNS, this);
        mPlayerTable = new PlayerTable(context, database, TABLE_PLAYER, TABLE_PLAYER_COLUMNS, this);
        mTournamentTable = new TournamentTable(context, database, TABLE_TOURNAMENT, TABLE_TOURNAMENT_COLUMNS, this);
        mMatchTable = new MatchTable(context, database, TABLE_MATCH, TABLE_MATCH_COLUMNS, this);
        mPlayersInTournamentTable = new PlayersInTournamentTable(context, database, TABLE_PLAYERS_IN_TOURNAMENT, TABLE_PLAYERS_IN_TOURNAMENT_COLUMNS, this);
        mPlayersInMatchTable = new PlayersInMatchTable(context, database, TABLE_PLAYERS_IN_MATCH, TABLE_PLAYERS_IN_MATCH_COLUMNS, this);
        mMatchesInTournamentTable = new MatchesInTournamentTable(context, database, TABLE_MATCHES_IN_TOURNAMENT, TABLE_MATCHS_IN_TOURNAMENT_COLUMNS, this);
        mTournamentInEventTable = new TournamentInEventTable(context, database, TABLE_TOURNAMENT_IN_EVENT, TABLE_TOURNAMENT_IN_EVENT_COLUMNS, this);
        query = new QueryHelper(database, this);
    }

    public void dropAll(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNAMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS_IN_TOURNAMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS_IN_MATCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHES_IN_TOURNAMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNAMENT_IN_EVENT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_EVENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS);
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS_IN_TOURNAMENT);
        db.execSQL(SQL_CREATE_TABLE_PLAYERS_IN_MATCH);
        db.execSQL(SQL_CREATE_TABLE_MATCHS_IN_TOURNAMENT);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENT_IN_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to " + newVersion);

        // TODO (David): Better update method with data retention
        dropAll(db);
        onCreate(db);
    }
}