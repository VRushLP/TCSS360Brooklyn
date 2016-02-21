package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.UrbanParkCalendar;
import model.Volunteer;

public class UrbanParkCalendarTest
{
    UrbanParkCalendar calendar;

    @Before
    public void setUp() throws Exception
    {
        calendar = new UrbanParkCalendar();
    }

    @Test
    public void testGetAllUsers()
    {
        assertNotNull(calendar.getAllUsers());
        assertEquals(0, calendar.getAllUsers().size());
        calendar.addVolunteer(new Volunteer(null, null, null));
        assertEquals(1, calendar.getAllUsers().size());
    }

    @Test
    public void testAddVolunteer()
    {
        Volunteer local = new Volunteer("localmail", "first name", "last name");
        assertTrue(calendar.addVolunteer(local));
        assertFalse(calendar.addVolunteer(local)); // Calendars do not allow
                                                   // duplicate Volunteers
        assertTrue(calendar.getAllUsers().contains(local));
        assertEquals(1, calendar.getAllUsers().size());
    }

    @Test
    public void testGetJobList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAddJob()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveJob()
    {
        fail("Not yet implemented");
    }

}
