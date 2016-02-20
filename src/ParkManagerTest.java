import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
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
        myCalendar = new UrbanParkCalendar();
        joblessParkManager = new ParkManager("john@uw.edu", "John", "Smith",
                myPark);
        myPark = new Park("Seattle Park", joblessParkManager);

        String dateEnd = "02/24/2016";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        endDate = format.parse(dateEnd);

        myJob = new Job(myPark, 5, endDate, endDate, "Pick up Trash",
                "Pick up trash at park");
        
        joblessParkManager.addParkToManager(myPark);
    }
    
    /**
     * Test Park Manager & Park assignments work properly
     */
    // @Test
    // public void testAddParkToManager()
    // {
    // assertTrue(joblessParkManager.getParks().contains(myPark));
    // assertTrue(myPark.getParkManager().equals(joblessParkManager));
    // }
   

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
//    @Test
//    public void testEditJob()
//    {
//        assertTrue(myJob.getMaxVolunteers() == 5); // edit volunteer number
//        assertTrue(myJob.getStartDate().equals(endDate));
//        assertTrue(myJob.getEndDate().equals(endDate));
//        assertTrue(myJob.getJobTitle().equals("Pick up Trash"));
//        assertTrue(myJob.getJobDescription().equals("Pick up trash at park"));
//        joblessParkManager.editJob(myCalendar, myJob,
//                myPark, 20, "02/25/2016", "02/25/2016", 
//                "Trees", "Plant trees");
//        assertTrue(myJob.getMaxVolunteers() == 20);
//        assertTrue(!myJob.getStartDate().equals(endDate));
//        assertTrue(!myJob.getEndDate().equals(endDate));
//        assertTrue(myJob.getJobTitle().equals("Trees"));
//        assertTrue(myJob.getJobDescription().equals("Plant trees"));
//    }

    /**
     * Make sure that when a Park Manager removes a job that it is 
     * removed from the calendar.
     */
//    @Test
//    public void testRemoveJob()
//    {
//        myCalendar.addJob(myJob);
//        assertTrue(myCalendar.getJobList().contains(myJob));
//        joblessParkManager.deleteJob(myCalendar, myJob, myPark);
//        assertTrue(!myCalendar.getJobList().contains(myJob));
//    }

}
