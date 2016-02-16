import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ParkTest
{
    // Fields
    ParkManager theodoreRoosevelt;
    Park prk;
    Job job1;
    Job job2;
    String prkName;
    ArrayList<Job> jobList;
    
    @Before
    public void setUp() throws Exception
    {
        theodoreRoosevelt = new ParkManager(
                "presidenttheo@whitehouse.gov", "Alexander", "The Great");
        prkName = "Wild Waves Theme Park";
        prk = new Park(prkName, theodoreRoosevelt);
        
        job1 = new Job(prk, 5, new Date(), new Date(),
                "Sweeping up the beach.",
                "Getting rid of the sand. It gets /everywhere/");
        job2 = new Job(prk, 4, new Date(), new Date(),
                "Drain the Lake",
                "We're going to need 4 able bodied men and women to drink all that water.");
        prk.addJob(job1);
        prk.addJob(job2);
        
        jobList = new ArrayList<Job>(prk.getJobList());
    }

    @Test
    public void testCheckForJob()
    {
        assertTrue(prk.checkForJob(job1));
        assertTrue(prk.checkForJob(job2));
    }

    @Test
    public void testGetParkName()
    {
        assertEquals(prkName, prk.getParkName());
    }

    @Test
    public void testGetParkManager()
    {
        assertEquals(theodoreRoosevelt, prk.getParkManager());
    }

    @Test
    public void testGetJobList()
    {
        assertTrue(prk.getJobList().size() > 0);
        assertEquals(job1, jobList.get(0));
        assertEquals(job2, jobList.get(1));
    }

}
