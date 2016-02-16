import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class UrbanParkCalendarTest
{
    private static UrbanParkCalendar testUPCalendar;
    private static Job testJob;
    private static Park testPark;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        testUPCalendar = new UrbanParkCalendar();
        testPark = new Park("Park (for testing)",
                new ParkManager("testing@gmail.com"));
        testJob = new Job(testPark, 10, new Date(), new Date(), "test", "test");
    }

    @Test
    public void testListCurrentJobs()
    {
        testUPCalendar.addJob(testJob);
        assertEquals("The size of the list should be one", 1,
                testUPCalendar.getJobList().size());
    }

    @Test
    public void testAddJobWhenMax30Jobs()
    {
        Date start = new Date();
        Date end = new Date();
        for (int i = 0; i < 100; i++)
        {
            testUPCalendar.addJob(
                    new Job(testPark, 30, start, end, "" + i, "" + i * i));
        }
        assertEquals(30, testUPCalendar.getJobList().size());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testMaxDateFromToday()
    {
        testJob.setEndDate(new Date(2020, 11, 1));
        testUPCalendar.addJob(testJob);
        assertEquals(0, testUPCalendar.getJobList().size());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSetDate()
    {
        Date testDate = new Date();
        testUPCalendar.setDate(testDate);
        assertEquals("Date is set correctly", testDate,
                testUPCalendar.getCurrentDate());
    }
}