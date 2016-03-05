package test;

import model.Park;
import model.ParkManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests to test basics about park managers.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class ParkManagerTest
{
    ParkManager testParkmanager;

    @Before
    public void setUp() throws Exception
    {
        testParkmanager = new ParkManager("Email", "FirstName", "LastName");
    }

    @Test
    public void testAddParkToManager()
    {
        Park park = new Park("Test park", testParkmanager);
        assertTrue("The tested Park Manager does not manage the test park!",
                testParkmanager.getParks().contains(park));
    }

    @Test
    public void testAddParksToManager()
    {
        Park parkOne = new Park("Test park", testParkmanager);
        Park parkTwo = new Park("Test park", testParkmanager);
        assertTrue(
                "The tested Park Manager does not manage both the test parks!",
                testParkmanager.getParks().contains(parkOne)
                        && testParkmanager.getParks().contains(parkTwo));

    }
}
