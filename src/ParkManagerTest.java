import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class ParkManagerTest
{

    ParkManager joblessParkManager;
    Park myPark;
    Job myJob;
    Date endDate;

    @Before
    public void setUp() throws Exception
    {
        // Park manager with no job
        joblessParkManager = new ParkManager("john@uw.edu", "John", "Smith",
                myPark);
        myPark = new Park("Seattle Park", joblessParkManager);

        String dateStart = "02/22/2016 15:00:00";
        String dateEnd = "02/24/2016 15:00:00";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date startDate = format.parse(dateStart);
        endDate = format.parse(dateEnd);

        myJob = new Job(myPark, 5, startDate, endDate, "Pick up Trash",
                "Pick up trash at park");
    }

    /**
     * Create new job and make sure it is contained in that park.
     */
    @Test
    public void testCreateJob()
    {
        assertTrue(myPark.getJobList().contains(myJob));
    }

    /**
     * Edit details of job and make sure updated job is contained in jobs list
     * for that park.
     */
    @Test
    public void testEditJob()
    {
        myJob.setEndDate(endDate);
        myJob.setJobDescription("Plant trees in park");
        myJob.setJobTitle("Plant trees");
        myJob.setStartDate(endDate);
        assertTrue(myPark.getJobList().contains(myJob));
        assertEquals(myJob, new Job(myPark, 5, endDate, endDate, "Plant trees",
                "Plant trees in park"));

    }

    /**
     * Remove a job and make sure it is no longer in list of jobs for a park
     * managers park.
     */
    @Test
    public void testRemoveJob()
    {
        myPark.addJob(myJob);
        assertTrue(myPark.removeJob(myJob));
        assertEquals(myPark.getJobList(), new ArrayList<Job>());
    }

}
