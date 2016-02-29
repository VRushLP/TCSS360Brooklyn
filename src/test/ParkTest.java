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
    Park prk1;
    Park prk2;
    Park prk1Dupl;
    Park prk3;
    ParkManager pm1;
    ParkManager pm2;
    Date today;
    Date tomorrow;
    Date twoDays;
    Date threeDays;
    Job j1, j2;
    ArrayList<Job> jobs;

    @Before
    public void setUp() throws Exception
    {
        pm1 = new ParkManager("pm@test.org", "John", "Doe");
        pm2 = new ParkManager("pm2@test.org", "Jonny", "Smith");
        prk1 = new Park("Gas works park", pm1);
        prk1Dupl = new Park("Gas works park", pm1);
        prk2 = new Park("Ravenna Park");
        prk3 = new Park("Gas works park", pm2);
        today = new Date(System.currentTimeMillis()
                + TimeUnit.HOURS.toMillis(1));
        tomorrow = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(1));
        twoDays = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(2));
        threeDays = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(3));
        j1 = new Job(prk1, 5, 5, 5, tomorrow, twoDays, "Test Job 1 Title",
                "Test Job 1 Description");
        j2 = new Job(prk2, 5, 5, 5, twoDays, threeDays, "Test Job 2 Title",
                "Test Job 2 Description");
    }

    @Test
    public void testAssertEquals()
    {
        assertTrue(prk1.equals(prk1Dupl));
        assertFalse(prk1.equals(prk2));
        assertFalse(prk3.equals(prk1));
    }

    @Test
    public void testGetJobList1()
    {
        assertTrue(prk1.getJobList() != null);
        assertTrue(prk2.getJobList() != null);
    }

    @Test
    public void testAddJob()
    {
        prk1.addJob(j2);
        prk1.addJob(j1);
        jobs = new ArrayList<Job>(prk1.getJobList());
        assertTrue(jobs.get(0).equals(j2));
        assertTrue(jobs.get(1).equals(j1));
    }

    @Test
    public void testGetJobList2()
    {
        prk1.addJob(j1);
        prk1.addJob(j2);
        assertTrue(prk1.getJobList().size() > 0);
    }

    @Test
    public void testHasJob()
    {
        prk1.addJob(j1);
        assertTrue(prk1.hasJob(j1));
    }

    @Test
    public void testRemoveJob()
    {
        prk1.addJob(j2);
        assertTrue(prk1.removeJob(j2));
    }
}
