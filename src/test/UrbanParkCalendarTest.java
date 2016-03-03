package test;

import static org.junit.Assert.*;

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
import exception.JobWorksTooHardException;
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
        today = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
        tomorrow = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        twoDays = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2));
        tooLong = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3));
    }

    @Test
    public void testUpdateCalendar() throws CalendarFullException,
            JobWorksTooHardException, DuplicateJobExistsException,
            JobToThePastException, JobToTheFutureException, JobTooLongException,
            JobTimeTravelException, CalendarWeekFullException, InterruptedException
    {
        calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0, new Date(
                System.currentTimeMillis() + 1), new Date(
                System.currentTimeMillis() + 1), "Just subtlely in the past",
                "Really does not matter"));

        TimeUnit.MILLISECONDS.sleep(2);

        calendar.updateCalendar();
        assertEquals(0, calendar.getJobList().size());
    }

    @Test
    public void testAddVolunteer()
    {
        Volunteer local = new Volunteer("localmail", "first name", "last name");
        assertTrue(calendar.addVolunteer(local));
        assertFalse(calendar.addVolunteer(local));
        // Calendars should not allow duplicate Volunteers
        assertTrue(calendar.getAllVolunteers().contains(local));
        assertEquals(1, calendar.getAllVolunteers().size());
    }

    @Test
    public void testAddJob()
    {
        assertNotNull(calendar.getJobList());
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0, today,
                    tomorrow, "Test Calendar methods",
                    "This Job for testing purposes only"));
        }
        catch (CalendarWeekFullException | CalendarFullException
                | JobTooLongException | JobTimeTravelException
                | JobToThePastException | JobToTheFutureException
                | DuplicateJobExistsException | JobWorksTooHardException e)
        {
            fail(e.getClass().getSimpleName());
        }
        assertEquals(1, calendar.getJobList().size());
    }

    @Test(expected = JobWorksTooHardException.class)
    public void testCheckForJobWorksTooHard() throws JobWorksTooHardException
    {
        calendar.checkForJobWorksTooHard(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 1,
                1, today, tomorrow, "Uh oh", "This job should trigger an exception"));
    }

    @Test(expected = DuplicateJobExistsException.class)
    public void testCheckForDuplicates() throws DuplicateJobExistsException
    {
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0, today,
                    tomorrow, "Uh oh", "This job should trigger an exception"));
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0, today,
                    tomorrow, "Uh oh", "This job should trigger an exception"));
        }
        catch (CalendarWeekFullException | JobToTheFutureException
                | JobTimeTravelException | CalendarFullException
                | JobToThePastException | JobTooLongException
                | JobWorksTooHardException e)
        {
            fail(e.getClass().getName());
        }
    }

    @Test
    public void testCheckJobDate()
    {
        assertTrue(
                "Testing for checkJobDate(Job theJob) is done in the tests below.",
                true);
        // To be more specific, checkJobDate calls checkForJobTooLong,
        // checkForJobToThePast, checkForJobToTheFuture and
        // checkForJobTimeTravel and those are tested individually.
    }

    @Test(expected = JobTooLongException.class)
    public void testCheckForJobTooLong() throws JobTooLongException
    {
        try
        {
            calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0, today,
                    tooLong, "Uh oh", "This job should trigger an exception"));
        }
        catch (CalendarWeekFullException | JobToTheFutureException
                | JobTimeTravelException | CalendarFullException
                | JobToThePastException | DuplicateJobExistsException
                | JobWorksTooHardException e)
        {
            fail(e.getClass().getName());
        }
    }

    @Test(expected = JobToThePastException.class)
    public void testCheckForJobToThePast() throws JobToThePastException
    {
        calendar.checkForJobToThePast(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0,
                new Date(System.currentTimeMillis() - 1), new Date(System
                        .currentTimeMillis() - 1), "Just subtlely in the past",
                "Really does not matter"));
    }

    @Test(expected = JobToTheFutureException.class)
    public void testCheckForJobToTheFuture() throws JobToTheFutureException
    {
        calendar.checkForJobToTheFuture(new Job(
                testPark,
                Job.MAX_VOLUNTEER_NUM,
                0,
                0,
                new Date(System.currentTimeMillis()
                        + TimeUnit.DAYS
                                .toMillis(UrbanParkCalendar.MAX_DATE_FROM_TODAY + 1)),
                new Date(System.currentTimeMillis()
                        + TimeUnit.DAYS
                                .toMillis(UrbanParkCalendar.MAX_DATE_FROM_TODAY + 2)),
                "Uh oh", "This job should trigger an exception"));

    }

    @Test(expected = JobTimeTravelException.class)
    public void testCheckForJobTimeTravel() throws JobTimeTravelException
    {
        calendar.checkForJobTimeTravel(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0,
                0, tomorrow, today, "Uh oh", "This job should trigger an exception"));
    }

    @Test(expected = CalendarFullException.class)
    public void testCheckJobCapacity() throws CalendarFullException
    {
        for (int i = 1; i < UrbanParkCalendar.MAX_JOBS + 2; i++)
        {
            try
            {
                calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0,
                        new Date(System.currentTimeMillis()
                                + TimeUnit.DAYS.toMillis(2 * (i))), new Date(System
                                .currentTimeMillis()
                                + TimeUnit.DAYS.toMillis((2 * (i)) + 1)),
                        "" + i * i, "This Job for testing purposes only"));
            }
            catch (JobWorksTooHardException | DuplicateJobExistsException
                    | JobToThePastException | JobToTheFutureException
                    | JobTooLongException | JobTimeTravelException
                    | CalendarWeekFullException e)
            {
                fail(e.getClass().getSimpleName());
            }
        }
    }

    @Test(expected = CalendarWeekFullException.class)
    public void testCheckForRoomThatWeek() throws CalendarWeekFullException
    {
        for (int i = 0; i < UrbanParkCalendar.MAX_WEEKLY_JOBS + 1; i++)
        {
            try
            {
                calendar.addJob(new Job(testPark, Job.MAX_VOLUNTEER_NUM, 0, 0,
                        today, tomorrow, "" + i,
                        "This Job for testing purposes only"));
            }
            catch (CalendarFullException | JobWorksTooHardException
                    | DuplicateJobExistsException | JobToThePastException
                    | JobToTheFutureException | JobTooLongException
                    | JobTimeTravelException e)
            {
                fail(e.getClass().getSimpleName());
            }
        }
    }

    @Test
    public void testRemoveJob()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEditJobTitle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEditJobDesc()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEditJobDates()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEditMaxVol()
    {
        fail("Not yet implemented");
    }
}