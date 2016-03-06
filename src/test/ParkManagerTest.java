package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Park;
import model.ParkManager;
import model.UrbanParkCalendar;

/**
 * JUnit tests to test basics about park managers. Tests about Job submission
 * can be found in the UrbanParkCalendarTest.java file, since that's where the
 * logic for adding jobs can be found.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class ParkManagerTest
{
    ParkManager testParkManager;
    Park testPark;
    UrbanParkCalendar testCalendar;

    @Before
    public void setUp() throws Exception
    {
        testParkManager = new ParkManager("Email", "FirstName", "LastName");
        testPark = new Park("Testing", testParkManager);
        testCalendar = new UrbanParkCalendar();
    }

    @Test
    public void testAddParkToManager()
    {
        Park park = new Park("Test park", testParkManager);
        assertTrue("The tested Park Manager does not manage the test park!",
                testParkManager.getParks().contains(park));
    }

    @Test
    public void testAddParksToManager()
    {
        Park parkOne = new Park("Test park", testParkManager);
        Park parkTwo = new Park("Test park", testParkManager);
        assertTrue(
                "The tested Park Manager does not manage both the test parks!",
                testParkManager.getParks().contains(parkOne)
                        && testParkManager.getParks().contains(parkTwo));

    }
}
