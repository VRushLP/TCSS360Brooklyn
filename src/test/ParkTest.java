package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Park;
import model.ParkManager;

public class ParkTest
{
    Park park1Duplicate; // duplicate park with park2Duplicate
    Park park2;
    Park parkDuplicate;
    Park parkWithNoJobs;
    Park parkWithOneJob;
    Park parkWithSomeJobs;
    ParkManager pm1, pm2, pm3, pm4, pm5;
    Date today;
    Date tomorrow;
    Date twoDays;
    Date threeDays, fourDays, fiveDays, sixDays;
    Job j1, j2, j3, j4, j5;
    ArrayList<Job> jobs;

    @Before
    public void setUp() throws Exception
    {
        pm1 = new ParkManager("pm@test.org", "John", "Doe");
        pm2 = new ParkManager("pm2@test.org", "Jonny", "Smith");
        pm3 = new ParkManager("pm3@test.org", "Steven", "Broyle");
        pm4 = new ParkManager("pm4@test.org", "Steven", "Broyle");
        pm5 = new ParkManager("pm5@test.org", "Hannah", "Stevens");
        park1Duplicate = new Park("Gas works park", pm1);
        parkDuplicate = new Park("Gas works park", pm1);
        park2 = new Park("Ravenna Park", pm3);
        parkWithNoJobs = new Park("Ravenna Park2", pm5);
        parkWithOneJob = new Park("Fremont Park", pm2);
        parkWithSomeJobs = new Park("North Passage Point Park", pm4);

        today = new Date(System.currentTimeMillis()
                + TimeUnit.HOURS.toMillis(1));
        tomorrow = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(1));
        twoDays = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(2));
        threeDays = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(3));
        fourDays = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(4));
        fiveDays = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(5));
        j1 = new Job(park1Duplicate, 5, 0, 0, tomorrow, twoDays,
                "Test Job 1 Title", "Test Job 1 Description");
        j2 = new Job(park2, 5, 0, 0, twoDays, threeDays, "Test Job 2 Title",
                "Test Job 2 Description");
        j3 = new Job(park2, 6, 0, 0, threeDays, fourDays, "Test Job 3 Title",
                "Test Job 3 Description");
        j4 = new Job(park2, 7, 0, 0, fourDays, fiveDays, "Test Job 4 Title",
                "Test Job 4 Description");
        j5 = new Job(park2, 8, 0, 0, fiveDays, sixDays, "Test Job 5 Title",
                "Test Job 5 Description");
        parkWithOneJob.addJob(j1);
        parkWithSomeJobs.addJob(j1);
        parkWithSomeJobs.addJob(j2);
        parkWithSomeJobs.addJob(j3);
        parkWithSomeJobs.addJob(j4);
    }

    @Test
    public void testEqualsOnDuplicateParks()
    {
        assertTrue(park1Duplicate.equals(parkDuplicate));
    }

    /**
     * Test when two parks are unique from one another.
     */
    @Test
    public void testEqualsOnUniqueParks()
    {
        assertFalse(park2.equals(parkWithNoJobs));
    }

    @Test
    public void testAddJobOnEmptyJobList()
    {
        // park1Duplicate.addJob(j2);
        park1Duplicate.addJob(j1);
        jobs = new ArrayList<Job>(park1Duplicate.getJobList());
        // assertTrue(jobs.get(0).equals(j2));
        assertTrue(jobs.get(0).equals(j1));
        assertEquals(jobs.size(), 1);
    }

    @Test
    public void testAddJobOnJobListWithOneJob()
    {
        parkWithOneJob.addJob(j2);
        jobs = new ArrayList<Job>(parkWithOneJob.getJobList());
        assertFalse(jobs.get(0).equals(j2));
        assertTrue(jobs.get(jobs.size() - 1).equals(j2));
        assertEquals(jobs.size(), 2);
    }

    @Test
    public void testAddJobOnJobListWithNJobs()
    {
        parkWithSomeJobs.addJob(j5);
        jobs = new ArrayList<Job>(parkWithSomeJobs.getJobList());
        assertFalse(jobs.get(0).equals(j2));
        assertFalse(jobs.get(jobs.size() - 2).equals(j2));
        assertTrue(jobs.get(jobs.size() - 1).equals(j5));
        assertTrue(jobs.size() > 1);
    }

    @Test
    public void testGetJobListOnNoJobs()
    {
        assertTrue(parkWithNoJobs.getJobList().isEmpty());
    }

    @Test
    public void testGetJobListOnOneJob()
    {
        assertTrue(parkWithOneJob.getJobList().size() == 1);
    }

    @Test
    public void testGetJobListOnNJobs()
    {
        park2.addJob(j2);
        park2.addJob(j3);
        park2.addJob(j4);
        park2.addJob(j5);
        assertTrue(park2.getJobList().size() > 1);
    }

    @Test
    public void testCheckForJobOnParkWithNoJobs()
    {
        assertFalse(parkWithNoJobs.hasJob(j1));
        assertTrue(parkWithNoJobs.getJobList().isEmpty());
    }

    @Test
    public void testCheckForJobOnParkWithOneJob()
    {
        assertTrue(parkWithOneJob.hasJob(j1));
    }

    @Test
    public void testCheckForJobOnParkWithNJobs()
    {
        assertTrue(parkWithSomeJobs.hasJob(j1));
        assertTrue(parkWithSomeJobs.hasJob(j3));
        assertTrue(parkWithSomeJobs.hasJob(j4));
    }

    @Test
    public void testRemoveJobOnParkWithNoJobs()
    {
        assertFalse(parkWithNoJobs.removeJob(j2));
    }

    @Test
    public void testRemoveJobOnParkWithOneJob()
    {
        assertTrue(parkWithOneJob.removeJob(j1));
    }

    @Test
    public void testRemoveJobOnParkWithNJobs()
    {
        assertTrue(parkWithSomeJobs.removeJob(j4));
    }
}
