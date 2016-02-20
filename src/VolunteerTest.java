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

    Volunteer joblessVolunteer;
    Volunteer sameVolunteer;
    Volunteer oneJobVolunteer;
    ParkManager tom;
    Park newPark;
    Job newJob;

//    @Before
//    public void setUp()
//    {
//        joblessVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
//        sameVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
//        oneJobVolunteer = new Volunteer("b@uw.edu");
//        tom = new ParkManager("tom@uw.edu");
//        newPark = new Park("Tom's Park", tom);
//        newJob = new Job(newPark, 1, new Date(),
//                new Date(), "Pick up trash", "Clean up trash from Park");
//    }
    
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
    

}