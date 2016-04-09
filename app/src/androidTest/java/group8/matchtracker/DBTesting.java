package group8.matchtracker;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;
import android.util.Log;

import group8.matchtracker.data.Event;
import group8.matchtracker.database.DatabaseHelper;

public class DBTesting extends AndroidTestCase {

    private static final String TEST_FILE_PREFIX = "test_";
    private DatabaseHelper dbHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        MockContentResolver resolver = new MockContentResolver();
        RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(new MockContext(),getContext(),TEST_FILE_PREFIX);
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

    public void testCreate(){
        long eId = 1;
        String eName = "foo";
        int eStart = 1;
        int eEnd = 2;
        String eLocation = "Union";
        String eOrganizer = "Me";
        Event eExpected = new Event(eId,eName,eStart,eEnd,eLocation,eOrganizer);
        Event eResult = dbHelper.mEventTable.create(eName, eStart, eEnd, eLocation, eOrganizer);

        assertTrue(dbHelper.mEventTable.hasData());
        assertEquals(eExpected, eResult);
    }
}
