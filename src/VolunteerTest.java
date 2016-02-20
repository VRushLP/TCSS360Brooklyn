import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class VolunteerTest
{
    // job's equals not working properly, does not check if names are the same.

    Volunteer joblessVolunteer;
    Volunteer sameVolunteer;
    Volunteer oneJobVolunteer;
    ParkManager tom;
    Park newPark;
    Job newJob;
    Job conflictingJob;
    Job pastJob;

    @Before
    public void setUp()
    {
        joblessVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        sameVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        oneJobVolunteer = new Volunteer("b@uw.edu");
        tom = new ParkManager("tom@uw.edu");
        newPark = new Park("Tom's Park", tom);
        newJob = new Job(newPark, 1, new Date(),
                new Date(), "Pick up trash", "Clean up trash from Park");
        conflictingJob = new Job(newPark, 3, new Date(),
                new Date(), "Clean bathrroms", "Clean the bathrooms in the park");
        pastJob = new Job(newPark, 3, new Date(1994, 2, 1),
                new Date(1994, 2, 2), "Clean bathrroms", "Clean the bathrooms in the park");
    }
    
    /**
     * Test that equals method works properly
     */
    @Test
    public void testEquals()
    {
        assertEquals("Same object should be the same", joblessVolunteer,
                joblessVolunteer);
        assertEquals("Different objects with same values should be equal",
                joblessVolunteer, sameVolunteer);
        assertNotEquals("Different values shouldn't be equal values",
                joblessVolunteer, new Volunteer("i@uw.edu", "J.", "S."));
    }
    
    /**
     * Test that user can volunteer for job.
     */
    @Test
    public void testVolunteerForJob()
    {
        assertTrue(!oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
        assertTrue(oneJobVolunteer.volunteerForJob(newJob));
        assertTrue(oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
    }
    
    /**
     * Test that job is properly removed from a volunteer.
     */
    @Test
    public void testRemoveJob()
    {
        oneJobVolunteer.volunteerForJob(newJob);
        assertTrue(oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
        assertTrue(oneJobVolunteer.removeJob(newJob));
        assertTrue(!oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
    }
    
    /**
     * Test that the volunteer can sign up for a job.
     */
    @Test
    public void testCanVolunteer() {
        fail("Not yet implemented");
    }
    
    /**
     * Tests business rule that a volunteer may not sign up 
     * for jobs that occur on the same day.
     */
    @Test
    public void testVolunteerIsFree() {
        oneJobVolunteer.volunteerForJob(newJob);
        assertFalse(oneJobVolunteer.volunteerIsFree(newJob));
        assertTrue(oneJobVolunteer.volunteerIsFree(pastJob));
    }
    
    /**
     * Tests that a volunteer may not sign up for
     * the same job twice.
     */
    @Test
    public void testIsDuplicate() {
        oneJobVolunteer.volunteerForJob(newJob);
        assertTrue(oneJobVolunteer.isDuplicate(newJob));
        assertFalse(oneJobVolunteer.isDuplicate(conflictingJob));
    }
    
    // go in job tests? 
    
    @Test
    public void testStartDayOverlaps() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testEndDayOverlaps() {
        fail("Not yet implemented");

    }
    
    @Test
    public void testShareDates() {
        fail("Not yet implemented");

    }
    
    @Test
    public void testIsPastJob() {
        fail("Not yet implemented");
    }
    

}