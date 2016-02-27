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

import exception.JobIsFullException;

/**
 * This JUnit class tests the Job class.
 * 
 * @author Bethany Eastman
 * @version 02/26/2016
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
     * Test a job can be edited with no volunteers.
     */
    @Test
    public void testCanEditNoVolunteers() {
        assertTrue(newJob.canEdit());
    }
    
    /**
     * Test a job should not be edited with a volunteer.
     */
    @Test
    public void testCanEditWithVolunteerLight() {
        newJob.addLightVolunteer(joblessVolunteer);
        assertFalse(newJob.canEdit());
    }
    
    /**
     * Test a job should not be edited with a volunteer.
     */
    @Test
    public void testCanEditWithVolunteerMedium() {
        newJob.addMediumVolunteer(joblessVolunteer);
        assertFalse(newJob.canEdit());
    }
    
    /**
     * Test a job should not be edited with a volunteer.
     */
    @Test
    public void testCanEditWithVolunteerDifficult() {
        newJob.addDifficultVolunteer(joblessVolunteer);
        assertFalse(newJob.canEdit());
    }
    
    /**
     * Test a job returns appropriate value for max volunteers of a work category.
     */
    @Test
    public void testHasMaxVolunteersNoVolunteers() {
        assertFalse(newJob.hasMaxVolunteers(WorkLoad.LIGHT));
        assertFalse(newJob.hasMaxVolunteers(WorkLoad.MEDIUM));
        assertFalse(newJob.hasMaxVolunteers(WorkLoad.DIFFICULT));
    }
    
    /**
     * Test a job returns appropriate value for max volunteers of a work category,
     * tests that hasMaxVolunteers will return full when a work category is full.
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
     * Test that nothing happens when trying to remove a volunteer that does not exist 
     * in list of volunteers.
     */
    @Test
    public void testRemoveVolunteerNoVolunteer() {
        assertFalse(newJob.removeVolunteer(joblessVolunteer));
    }
    
    /**
     * Test that a volunteer is removed from light work.
     */
    @Test
    public void testRemoveVolunteerLight() {
        newJob.addLightVolunteer(joblessVolunteer);
        assertTrue(newJob.removeVolunteer(joblessVolunteer));
    }
    
    /**
     * Test that a volunteer is removed from medium work.
     */
    @Test
    public void testRemoveVolunteerMedium() {
        newJob.addMediumVolunteer(joblessVolunteer);
        assertTrue(newJob.removeVolunteer(joblessVolunteer));
    }
    
    /**
     * Test that a volunteer is removed from difficult work.
     */
    @Test
    public void testRemoveVolunteerDifficult() {
        newJob.addDifficultVolunteer(joblessVolunteer);
        assertTrue(newJob.removeVolunteer(joblessVolunteer));
    }
    
    /**
     * Test the job is full exception is thrown when someone tries to 
     * sign up for a full work category.
     */
    @Test
    public void testVolunteerJobIsFullException() {
        try {
            joblessVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            fail();
        } catch (Exception e) {
            assertEquals(JobIsFullException.class, e.getClass());
        }
    }
    
    /**
     * Test that a volunteer is added into light work properly.
     */
    @Test
    public void testAddVolunteerLightWork() {
        assertTrue(newJob.getLightVolunteerCount() == 0);
        assertTrue(newJob.addLightVolunteer(joblessVolunteer));
        assertTrue(newJob.getLightVolunteerCount() == 1);
    }
    
    /**
     * Test that a volunteer is added into medium work properly.
     */
    @Test
    public void testAddVolunteerMediumWork() {
        assertTrue(newJob.getMediumVolunteerCount() == 0);
        assertTrue(newJob.addMediumVolunteer(joblessVolunteer));
        assertTrue(newJob.getMediumVolunteerCount() == 1);
    }
    
    /**
     * Test that a volunteer is added into difficult work properly.
     */
    @Test
    public void testAddVolunteerDifficultWork() {
        assertTrue(newJob.getDifficultVolunteerCount() == 0);
        assertTrue(newJob.addDifficultVolunteer(joblessVolunteer));
        assertTrue(newJob.getDifficultVolunteerCount() == 1);
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
