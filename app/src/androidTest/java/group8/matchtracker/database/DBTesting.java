package group8.matchtracker.database;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import java.util.List;

import group8.matchtracker.data.Event;
import group8.matchtracker.data.Player;
import group8.matchtracker.data.Tournament;

public class DBTesting extends AndroidTestCase {

    private static final String TEST_FILE_PREFIX = "test_";
    private DatabaseHelper dbHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        MockContentResolver resolver = new MockContentResolver();
        RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(new MockContext(), getContext(), TEST_FILE_PREFIX);
        Context context = new IsolatedContext(resolver, targetContextWrapper);
        setContext(context);

        dbHelper = new DatabaseHelper(context);
        dbHelper.getWritableDatabase();
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
        dbHelper.close();
        dbHelper = null;
    }

    public void testEventCRUD() {
        // Setup
        long e1_ID = 1;
        String e1_Name = "foo";
        String e1_Start = "1pm";
        String e1_End = "2pm";
        String e1_Location = "Union";
        String e1_Organizer = "Me";
        Event e1 = new Event(e1_ID, e1_Name, e1_Start, e1_End, e1_Location, e1_Organizer);

        long e2_ID = 2;
        String e2_Name = "bar";
        String e2_Start = "";
        String e2_End = "Noon";
        String e2_Location = "House";
        String e2_Organizer = "You";
        Event e2 = new Event(e2_ID, e2_Name, e2_Start, e2_End, e2_Location, e2_Organizer);

        // Start
        assertFalse(dbHelper.mEventTable.hasData());

        // Create
        Event e1_Created = dbHelper.mEventTable.create(e1_Name, e1_Start, e1_End, e1_Location, e1_Organizer);
        assertEquals(e1, e1_Created);
        Event e2_Created = dbHelper.mEventTable.create(e2_Name, e2_Start, e2_End, e2_Location, e2_Organizer);
        assertEquals(e2, e2_Created);
        assertTrue(dbHelper.mEventTable.hasData());

        // Read
        Event e1_ReadID = dbHelper.mEventTable.read(e1_ID);
        assertEquals(e1, e1_ReadID);
        Event e2_ReadName = dbHelper.mEventTable.read(e2_Name);
        assertEquals(e2, e2_ReadName);
        List<Event> eventList = dbHelper.mEventTable.readAll();
        assertEquals(2, eventList.size());
        assertEquals(e1, eventList.get(0)); // Does list guarantee order?
        assertEquals(e2, eventList.get(1));

        // Update

        // Delete
        dbHelper.mEventTable.deleteAll();
        assertFalse(dbHelper.mEventTable.hasData());
    }

    public void testTournamentCRUD() {
        // Setup
        long t1_ID = 1;
        int t1_CID = 5678;
        String t1_Name = "t1";
        String t1_url = "t1.com";
        Tournament t1 = new Tournament(t1_ID, t1_CID, t1_Name, t1_url);

        long t2_ID = 2;
        int t2_CID = -8765;
        String t2_Name = "t2";
        String t2_url = "t2.com";
        Tournament t2 = new Tournament(t2_ID, t2_CID, t2_Name, t2_url);

        // Start
        assertFalse(dbHelper.mTournamentTable.hasData());

        // Create
        Tournament t1_Created = dbHelper.mTournamentTable.create(t1_CID, t1_Name, t1_url);
        assertEquals(t1, t1_Created);
        Tournament t2_Created = dbHelper.mTournamentTable.create(t2_CID, t2_Name, t2_url);
        assertEquals(t2, t2_Created);
        assertTrue(dbHelper.mTournamentTable.hasData());

        // Read
        Tournament t1_ReadID = dbHelper.mTournamentTable.read(t1_ID);
        assertEquals(t1, t1_ReadID);
        Tournament t2_ReadID = dbHelper.mTournamentTable.read(t2_ID);
        assertEquals(t2, t2_ReadID);
        List<Tournament> tournamentList = dbHelper.mTournamentTable.readAll();
        assertEquals(2, tournamentList.size());
        assertEquals(t1, tournamentList.get(0)); // Does list guarantee order?
        assertEquals(t2, tournamentList.get(1));

        // Update

        // Delete
        dbHelper.mTournamentTable.deleteAll();
        assertFalse(dbHelper.mTournamentTable.hasData());
    }

    public void testMatchCRUD() {
        // Setup
        // TODO :D

        // Start
        assertFalse(dbHelper.mMatchTable.hasData());

        // Create

        // Read

        // Update

        // Delete
        dbHelper.mMatchTable.deleteAll();
        assertFalse(dbHelper.mMatchTable.hasData());
    }

    public void testPlayerCRUD() {
        // Setup
        long p1_ID = 1;
        int p1_CID = 1234;
        String p1_name = "player1";
        String p1_ign = "p1";
        Player p1 = new Player(p1_ID, p1_CID, p1_name, p1_ign);

        long p2_ID = 2;
        int p2_CID = -4321;
        String p2_name = "player2";
        String p2_ign = "p2";
        Player p2 = new Player(p2_ID, p2_CID, p2_name, p2_ign);

        // Start
        assertFalse(dbHelper.mPlayerTable.hasData());

        // Create
        Player p1_Created = dbHelper.mPlayerTable.create(p1_CID, p1_name, p1_ign);
        assertEquals(p1, p1_Created);
        Player p2_Created = dbHelper.mPlayerTable.create(p2_CID, p2_name, p2_ign);
        assertEquals(p2, p2_Created);
        assertTrue(dbHelper.mPlayerTable.hasData());

        // Read
        Player p1_ReadID = dbHelper.mPlayerTable.read(p1_ID);
        assertEquals(p1, p1_ReadID);
        Player p2_ReadIGN = dbHelper.mPlayerTable.read(p2_ign);
        assertEquals(p2, p2_ReadIGN);
        List<Player> playersList = dbHelper.mPlayerTable.readAll();
        assertEquals(2, playersList.size());
        assertEquals(p1, playersList.get(0));
        assertEquals(p2, playersList.get(1));

        // Update

        // Delete
        dbHelper.mPlayerTable.deleteAll();
        assertFalse(dbHelper.mPlayerTable.hasData());
    }
}
