import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD
/**
 * 
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class VolunteerTest {
    
    Volunteer joblessVolunteer;
    Volunteer sameVolunteer;
//    Volunteer maxVolunteer;

	@Before
	public void setUp() {
		joblessVolunteer = new Volunteer("jobless@gmail.com", 
		        "John", "Smith");
		sameVolunteer = new Volunteer("jobless@gmail.com", 
                "John", "Smith");
	}
	
	@Test
	public void testEquals() {
	    assertEquals("Same object should be the same", 
	            joblessVolunteer, joblessVolunteer);
	    assertEquals("Different objects with same values should be equal", 
	            joblessVolunteer, sameVolunteer);
	    assertNotEquals("Different values shouldn't be equal values",
	            joblessVolunteer, new Volunteer("i@uw.edu", "J.", "S."));
	}

	@Test
	public void testVolunteerForJob() {
		// test that volunteer is added to job
		// test volunteer can't sign up for full job
		// test volunteer can't sign up for two jobs on same day
		// test volunteer can't sign up for old job
		fail("Not yet implemented");
	}

	@Test
	public void testViewJob() {
		// make sure job view is same as expected
		// for specific job
		fail("Not yet implemented");
	}

	@Test
	public void testViewAllJobs() {
		// test view with no job
		// test view with one job
		// test view with some jobs
		fail("Not yet implemented");
	}
	
	@Test
	public void testMyJobs() {
		// test view for a volunteers jobs
		// test view for job less volunteer
		// test view for volunteer with one job
		fail("Not yet implemented");
	}
=======
public class VolunteerTest
{

    @Before
    public void setUp()
    {
        // Volunteer joblessVolunteer = new Volunteer();
    }

    @Test
    public void testVolunteerForJob()
    {
        // test that volunteer is added to job
        // test volunteer can't sign up for full job
        // test volunteer can't sign up for two jobs on same day
        // test volunteer can't sign up for old job
        fail("Not yet implemented");
    }

    @Test
    public void testViewJob()
    {
        // make sure job view is same as expected
        // for specific job
        fail("Not yet implemented");
    }

    @Test
    public void testViewAllJobs()
    {
        // test view with no job
        // test view with one job
        // test view with some jobs
        fail("Not yet implemented");
    }

    @Test
    public void testMyJobs()
    {
        // test view for a volunteers jobs
        // test view for job less volunteer
        // test view for volunteer with one job
        fail("Not yet implemented");
    }
>>>>>>> master

}
