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

    UrbanParkCalendar myCalendar;
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

        String dateStart = "02/22/2016";
        String dateEnd = "02/24/2016";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        Date startDate = format.parse(dateStart);
        endDate = format.parse(dateEnd);

        myJob = new Job(myPark, 5, startDate, endDate, "Pick up Trash",
                "Pick up trash at park");
    }
    
    /**
     * Test Park Manager & Park assignments work properly
     */
    @Test
    public void testAddParkToManager()
    {
        assertFalse(joblessParkManager.getParks().contains(myPark));
        joblessParkManager.addParkToManager(myPark);
        assertTrue(joblessParkManager.getParks().contains(myPark));
        assertTrue(myPark.getParkManager().equals(joblessParkManager));
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
//        joblessParkManager.addParkToManager(myPark);
        assertTrue(myJob.getMaxVolunteers() == 5); // edit volunteer number
        assertTrue(!myJob.getStartDate().equals(endDate));
        assertTrue(myJob.getJobTitle().equals("Pick up Trash"));
        joblessParkManager.editJob(myCalendar, myJob,
                myPark, 20, "02/24/2016", "02/24/2016", 
                "Pick up new Trash", "Pick up trash at park");
        assertTrue(myJob.getMaxVolunteers() == 20);
        assertTrue(myJob.getStartDate().equals(endDate));
        assertTrue(myJob.getJobTitle().equals("Pick up new Trash"));
    }

    /**
     * Remove a job and make sure it is no longer in list of jobs for a park
     * managers park.
     */
    @Test
    public void testRemoveJob()
    {
//        joblessParkManager.addParkToManager(myPark);
        myCalendar.addJob(myJob);
        assertTrue(myCalendar.getJobList().contains(myJob));
        joblessParkManager.deleteJob(myCalendar, myJob, myPark);
        assertTrue(!myCalendar.getJobList().contains(myJob));
    }

}
