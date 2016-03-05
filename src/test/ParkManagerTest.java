package test;

import model.ParkManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
        fail("Not yet implemented");
    }

    @Test
    public void testGetParks()
    {
        fail("Not yet implemented.");
    }
}
