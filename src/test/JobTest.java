package test;

import static org.junit.Assert.*;

import java.util.Date;

import model.Job;
import model.Park;
import model.ParkManager;
import model.Volunteer;
import model.WorkLoad;

import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit class tests the Job class.
 * 
 * @author Bethany Eastman
 * @version 02/22/2016
 */
public class JobTest
{   
    
    // Fields
    Volunteer joblessVolunteer;
    Volunteer sameVolunteer;
    Volunteer oneJobVolunteer;
    ParkManager tom;
    Park newPark;
    Job newJob;
    Job conflictingJob;
    Job pastJob;
    Job jobToday;
    Job jobConflictsBeforeStart;
    Job jobConflictsAfterStart;
    
    
    @Before
    public void setUp() throws Exception
    {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date sevenDays = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        Date fourDays = new Date(System.currentTimeMillis() + (4 * DAY_IN_MS));
        Date threeDays = new Date(System.currentTimeMillis() + (3 * DAY_IN_MS));
        Date fiveDays = new Date(System.currentTimeMillis() + (5 * DAY_IN_MS));
        Date sixDays = new Date(System.currentTimeMillis() + (6 * DAY_IN_MS));

        joblessVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        sameVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        oneJobVolunteer = new Volunteer("b@uw.edu", "Jane", "Does");
        tom = new ParkManager("tom@uw.edu", "Tom", "Hanks");
        newPark = new Park("Tom's Park", tom);
        newJob = new Job(newPark, 1, 1, 1, fourDays, fiveDays, "Pick up trash",
                "Clean up trash from Park");
        conflictingJob = new Job(newPark, 3, 3, 3, fourDays, fiveDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        pastJob = new Job(newPark, 3, 3, 3, sevenDays, sevenDays, "Clean bathrroms",
                "Clean the bathrooms in the park");
        jobToday = new Job(newPark, 3, 3, 3,  new Date(), new Date(),
                "Clean bathrroms", "Clean the bathrooms in the park");
        jobConflictsBeforeStart = new Job(newPark, 3, 3, 3, threeDays, fiveDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        jobConflictsAfterStart = new Job(newPark, 3, 3, 3, fourDays, sixDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        
    }
    
    /**
     * Test a job returns appropraite value for max volunteers of a work category.
     */
    @Test
    public void testHasMaxVolunteersNoVolunteers() {
        assertFalse(newJob.hasMaxVolunteers(WorkLoad.LIGHT));
        assertFalse(newJob.hasMaxVolunteers(WorkLoad.MEDIUM));
        assertFalse(newJob.hasMaxVolunteers(WorkLoad.DIFFICULT));
    }
    
    /**
     * Test a job returns appropraite value for max volunteers of a work category.
     */
    @Test
    public void testHasMaxVolunteersFullVolunteers() {
        try {
            joblessVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            sameVolunteer.volunteerForJob(newJob, WorkLoad.MEDIUM);
            oneJobVolunteer.volunteerForJob(newJob, WorkLoad.DIFFICULT);
        } catch (Exception e) {
            System.out.println(e.getClass());
            fail();
        }
        assertTrue(newJob.hasMaxVolunteers(WorkLoad.LIGHT));
        assertTrue(newJob.hasMaxVolunteers(WorkLoad.MEDIUM));
        assertTrue(newJob.hasMaxVolunteers(WorkLoad.DIFFICULT));
    }

    /**
     * Test that two jobs have share a start or end date.
     */
    @Test
    public void testShareDates()
    {
        assertTrue(newJob.shareDates(conflictingJob));
        assertFalse(newJob.shareDates(pastJob));
    }

    /**
     * Tests that a job conflicts with another job, if job occurs during the
     * other job.
     */
    @Test
    public void testStartDayOverlaps()
    {
        assertTrue(jobConflictsBeforeStart.startDayOverlaps(newJob));
        assertFalse(jobConflictsAfterStart.startDayOverlaps(newJob));
    }

    /**
     * Tests that a job conflicts with another job, if job starts during job.
     */
    @Test
    public void testEndDayOverlaps()
    {
        assertTrue(jobConflictsAfterStart.endDayOverlaps(newJob));
        assertFalse(jobConflictsBeforeStart.endDayOverlaps(newJob));
    }

    /**
     * Tests that a job is in the past.
     */
    @Test
    public void testIsPastJob()
    {
        assertTrue(pastJob.isPastJob());
        assertFalse(newJob.isPastJob());
    }
}
