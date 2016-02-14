import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

/**
 * This JUnit class tests the Job class.
 * 
 * @author TCSS 360 Brooklyn (Group 1)
 *
 */
public class JobTest
{
    // Fields
    ParkManager pm;
    Park prk;
    Job jb;

    @Before
    public void setUp() throws Exception
    {
        String parkName = "Woodland Park";

        
        prk = new Park(parkName, pm);
        String jobTitle = "Pick up trash";
        String jobDescription = "Volunteers would be picking up the trash";

        String dateStart = "02/22/2016 15:00:00";
        String dateEnd = "02/24/2016 15:00:00";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date startDate = format.parse(dateStart);
        Date endDate = format.parse(dateEnd);

        pm = new ParkManager("testPM@doesntexist.net", "dr", "test", prk);
        jb = new Job(prk, 5, startDate, endDate, jobTitle, jobDescription);
    }

    /**
     * This test asserts that the returned job is not empty.
     */
    @Test
    public void testGetJob()
    {
        assertFalse(jb.getJobTitle().isEmpty());
        assertTrue(jb.getAssociatedPark() != null);
        assertTrue(jb.getStartDate() != null);
        assertTrue(jb.getEndDate() != null);
    }

    /**
     * This test asserts that the maximum number of volunteers is greater than
     * 0.
     */
    @Test
    public void testMaxVol()
    {
        assertTrue(jb.getMaxVolunteers() > 0);
    }

    /**
     * This test asserts that the toString method does not return an empty
     * string.
     */
    @Test
    public void testToString()
    {
        assertFalse(jb.toString().isEmpty());
    }

    // Note: The test methods below could be more relevant when
    // we are testing the park manager class and more specifically
    // when a park manager is creating a job.

    /**
     * This test asserts that the startDate is not before the current date
     */
    @Test
    public void testStartDate()
    {
        // Get system's current date
        Calendar calobj = Calendar.getInstance();
        Date currentDate = calobj.getTime();

        // Get the difference between start date and current date in
        // milliseconds
        float diff = jb.getStartDate().getTime() - currentDate.getTime();

        // Difference in days
        float diffDays = diff / (24 * 60 * 60 * 1000);
        assertFalse(diffDays <= 0);

    }

    /**
     * This test asserts that the startDate is not more than 90 days from
     * today's date. This satisfies business rule #5 (see official
     * requirements).
     */
    @Test
    public void testStartDate2()
    {
        // Get system's current date
        Calendar calobj = Calendar.getInstance();
        Date currentDate = calobj.getTime();

        // Get the difference between start date and current date in
        // milliseconds
        float diff = jb.getStartDate().getTime() - currentDate.getTime();

        // Difference in days
        float diffDays = diff / (24 * 60 * 60 * 1000);
        assertTrue(diffDays <= 90);

    }

    /**
     * This test asserts that the endDate is not more than 90 days from today's
     * date. This satisfies business rule #5 (see official requirements).
     */
    @Test
    public void testEndDate()
    {
        // Get system's current date
        Calendar calobj = Calendar.getInstance();
        Date currentDate = calobj.getTime();

        // Get the difference between start date and current date in
        // milliseconds
        float diff = jb.getEndDate().getTime() - currentDate.getTime();

        // Difference in days
        float diffDays = diff / (24 * 60 * 60 * 1000);
        System.out.println("Diff in days: " + diffDays);
        assertTrue(diffDays <= 90);

    }

    /**
     * This test asserts that the startDate is before the end date
     */
    @Test
    public void testStartEndDate()
    {
        assertTrue(jb.getStartDate().compareTo(jb.getEndDate()) < 0);
    }

    /**
     * This test asserts that the end date is no more than two days after the
     * start date. This satisfies business rule #4 (see official requirements).
     */
    @Test
    public void testStartEndDate2()
    {
        // Get the difference in milliseconds
        float diff = jb.getEndDate().getTime() - jb.getStartDate().getTime();

        // Difference in days
        float diffDays = diff / (24 * 60 * 60 * 1000);
        assertTrue(diffDays <= 2);
    }
}
