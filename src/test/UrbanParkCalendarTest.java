package test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
import exception.DuplicateJobExistsException;
import exception.JobTimeTravelException;
import exception.JobToTheFutureException;
import exception.JobToThePastException;
import exception.JobTooLongException;

import model.Job;
import model.Park;
import model.ParkManager;
import model.UrbanParkCalendar;
import model.Volunteer;

public class UrbanParkCalendarTest
{
    UrbanParkCalendar calendar;
    Park testPark;
    ParkManager testManager;
    Date today;
    Date tomorrow;
    Date twoDays;
    Date tooLong;

    @Before
    public void setUp() throws Exception
    {
        calendar = new UrbanParkCalendar();
        testManager = new ParkManager(null, null, null);
        testPark = new Park("Test park", testManager);
        today = new Date(
                System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
        tomorrow = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        twoDays = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2));
        tooLong = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3));
    }

    @Test
    public void testAddVolunteer()
    {
        Volunteer local = new Volunteer("localmail", "first name", "last name");
        assertTrue(calendar.addVolunteer(local));
        assertFalse(calendar.addVolunteer(local)); // Calendars do not allow
                                                   // duplicate Volunteers
        assertTrue(calendar.getAllUsers().contains(local));
        assertEquals(1, calendar.getAllUsers().size());
    }

    @Test
    public void testGetAllUsers()
    {
        assertNotNull(calendar.getAllUsers());
        assertEquals(0, calendar.getAllUsers().size());
        calendar.addVolunteer(new Volunteer(null, null, null));
        assertEquals(1, calendar.getAllUsers().size());
    }

    @Test
    public void testUpcomingValidation() throws CalendarWeekFullException,
            CalendarFullException, JobTooLongException, JobTimeTravelException,
            JobToThePastException, JobToTheFutureException,
            DuplicateJobExistsException, InterruptedException
    {
        calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM,
                new Date(System.currentTimeMillis() + 1),
                new Date(System.currentTimeMillis() + 1),
                "Just subtlely in the past", "Really does not matter"));

        TimeUnit.MILLISECONDS.sleep(2);

        calendar.updateCalendar();
        assertEquals(0, calendar.getJobList().size());
    }

    @Test
    public void testAddSingleJob()
    {
        assertNotNull(calendar.getJobList());
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tomorrow, "Test Calendar methods",
                    "This Job for testing purposes only"));
        }
        catch (CalendarWeekFullException | CalendarFullException
                | JobTooLongException | JobTimeTravelException
                | JobToThePastException | JobToTheFutureException
                | DuplicateJobExistsException e)
        {
            e.printStackTrace(); // There is no reason this should trigger from
                                 // the above add
        }
        assertEquals(1, calendar.getJobList().size());
    }

    @Test
    public void testOverfullWeek()
            throws CalendarWeekFullException, CalendarFullException,
            JobTooLongException, JobTimeTravelException, JobToThePastException,
            JobToTheFutureException, DuplicateJobExistsException
    {
        for (int i = 0; i < 4; i++)
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tomorrow, "" + i, "This Job for testing purposes only"));
        }

        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tomorrow, "Uh oh", "This Job should trigger an exception"));
        }
        catch (CalendarWeekFullException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof CalendarWeekFullException);
        }
        catch (CalendarFullException | JobTooLongException
                | JobTimeTravelException | JobToThePastException
                | JobToTheFutureException | DuplicateJobExistsException e)
        {
            fail(e.getClass().getName());
        }

    }

    @Test
    public void testCalendarFullException()
            throws CalendarWeekFullException, CalendarFullException,
            JobTooLongException, JobTimeTravelException, JobToThePastException,
            JobToTheFutureException, DuplicateJobExistsException
    {
        for (int i = 0; i < 30; i++)
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM,
                    new Date(System.currentTimeMillis()
                            + TimeUnit.DAYS.toMillis(i)),
                    new Date(System.currentTimeMillis()
                            + TimeUnit.DAYS.toMillis(i + 1)),
                    "" + i * i, "This Job for testing purposes only"));
        }

        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tomorrow, "Uh oh", "This Job should trigger an exception"));
        }
        catch (CalendarFullException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof CalendarFullException);
        }
        catch (CalendarWeekFullException | JobTooLongException
                | JobTimeTravelException | JobToThePastException
                | JobToTheFutureException | DuplicateJobExistsException e)
        {
            fail(e.getClass().getName());
        }

    }

    @Test
    public void testJobTimeTravelingException()
    {
        try
        {

            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, tomorrow,
                    today, "Uh oh", "This job should trigger an exception"));
        }
        catch (CalendarFullException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof CalendarFullException);
        }
        catch (CalendarWeekFullException | JobTooLongException
                | JobTimeTravelException | JobToThePastException
                | JobToTheFutureException | DuplicateJobExistsException e)
        {
            fail(e.getClass().getName());
        }
    }

    public void testJobToThePastException()
    {
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, new Date(),
                    today, "Uh oh", "This job should trigger an exception"));
        }
        catch (JobToThePastException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof JobToThePastException);
        }
        catch (CalendarWeekFullException | JobTooLongException
                | JobTimeTravelException | CalendarFullException
                | JobToTheFutureException | DuplicateJobExistsException e)
        {
            fail(e.getClass().getName());
        }
    }

    @Test
    public void testJobToTheFutureException()
    {
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM,
                    new Date(
                            System.currentTimeMillis() + TimeUnit.DAYS.toMillis(
                                    UrbanParkCalendar.MAX_DATE_FROM_TODAY + 1)),
                    new Date(
                            System.currentTimeMillis() + TimeUnit.DAYS.toMillis(
                                    UrbanParkCalendar.MAX_DATE_FROM_TODAY + 2)),
                    "Uh oh", "This job should trigger an exception"));
        }
        catch (JobToTheFutureException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof JobToTheFutureException);
        }
        catch (CalendarWeekFullException | JobTooLongException
                | JobTimeTravelException | CalendarFullException
                | JobToThePastException | DuplicateJobExistsException e)
        {
            fail(e.getClass().getName());
        }
    }

    @Test
    public void testJobTooLongException()
    {
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tooLong, "Uh oh", "This job should trigger an exception"));
        }
        catch (JobTooLongException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof JobTooLongException);
        }
        catch (CalendarWeekFullException | JobToTheFutureException
                | JobTimeTravelException | CalendarFullException
                | JobToThePastException | DuplicateJobExistsException e)
        {
            fail(e.getClass().getName());
        }
    }

    @Test
    public void testDuplicateJobExistsException()
    {
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tomorrow, "Uh oh", "This job should trigger an exception"));
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, today,
                    tomorrow, "Uh oh", "This job should trigger an exception"));
        }
        catch (DuplicateJobExistsException e)
        {
            assertTrue(e.getClass().getSimpleName(),
                    e instanceof DuplicateJobExistsException);
        }
        catch (CalendarWeekFullException | JobToTheFutureException
                | JobTimeTravelException | CalendarFullException
                | JobToThePastException | JobTooLongException e)
        {
            fail(e.getClass().getName());
        }
    }

}
