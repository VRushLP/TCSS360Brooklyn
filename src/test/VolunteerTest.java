package test;

import static org.junit.Assert.*;

import java.util.Date;

import model.Job;
import model.Park;
import model.ParkManager;
import model.Volunteer;
import model.WorkLoad;

import org.junit.Before;
import org.junit.Test;

import exception.AlreadyVolunteeredException;
import exception.ConflictingJobCommitmentException;
import exception.JobIsFullException;
import exception.JobToThePastException;

/**
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class VolunteerTest
{

    Volunteer joblessVolunteer;
    Volunteer sameVolunteer;
    Volunteer oneJobVolunteer;
    ParkManager tom;
    Park newPark;
    Job newJob;
    Job conflictingJob;
    Job pastJob;
    Job jobToday;
    Job jobConflictsBeforeStart;
    Job jobConflictsAfterStart;

    @Before
    public void setUp()
    {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date sevenDays = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        Date fourDays = new Date(System.currentTimeMillis() + (4 * DAY_IN_MS));
        Date threeDays = new Date(System.currentTimeMillis() + (3 * DAY_IN_MS));
        Date fiveDays = new Date(System.currentTimeMillis() + (5 * DAY_IN_MS));
        Date sixDays = new Date(System.currentTimeMillis() + (6 * DAY_IN_MS));
        Date oneDay = new Date(System.currentTimeMillis() + (DAY_IN_MS));

        joblessVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        sameVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        oneJobVolunteer = new Volunteer("b@uw.edu", "Jane", "Does");
        tom = new ParkManager("tom@uw.edu", "Tom", "Hanks");
        newPark = new Park("Tom's Park", tom);
        newJob = new Job(newPark, 1, 1, 1, fourDays, fiveDays, "Pick up trash",
                "Clean up trash from Park");
        conflictingJob = new Job(newPark, 3 , 3, 3, fourDays, fiveDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        pastJob = new Job(newPark, 3, 3, 3, sevenDays, sevenDays, "Clean bathrroms",
                "Clean the bathrooms in the park");
        jobToday = new Job(newPark, 3, 3, 3, oneDay, oneDay,
                "Clean bathrroms", "Clean the bathrooms in the park");
        jobConflictsBeforeStart = new Job(newPark, 3, 3, 3, threeDays, fiveDays,
                "Clean bathrooms", "Clean the bathrooms in the park");
        jobConflictsAfterStart = new Job(newPark, 3, 3, 3, fourDays, sixDays,
                "Clean bathrooms", "Clean the bathrooms in the park");
    }
    
    /**
     * Test that a volunteer is added to appropriate category when
     * volunteering for a job, for light work.
     */
    @Test
    public void testVolunteerLightWork() {
        assertTrue(newJob.getLightVolunteerCount() == 0);
        try
        {
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
        }
        catch (Exception e)
        {
            fail();
        }
        assertTrue(newJob.getLightVolunteerCount() == 1);
    }
    
    /**
     * Test that a volunteer is added to appropriate category when
     * volunteering for a job, for medium work.
     */
    @Test
    public void testVolunteerMediumWork() {
        assertTrue(newJob.getMediumVolunteerCount() == 0);
        try
        {
            sameVolunteer.volunteerForJob(newJob, WorkLoad.MEDIUM);
        }
        catch (Exception e)
        {
            fail();
        }
        assertTrue(newJob.getMediumVolunteerCount() == 1);
    }
    
    /**
     * Test that a volunteer is added to appropriate category when
     * volunteering for a job, for difficult work.
     */
    @Test
    public void testVolunteerDifficultWork() {
        assertTrue(newJob.getDifficultVolunteerCount() == 0);
        try
        {
            sameVolunteer.volunteerForJob(newJob, WorkLoad.DIFFICULT);
        }
        catch (Exception e)
        {
            fail();
        }
        assertTrue(newJob.getDifficultVolunteerCount() == 1);
    }

    /**
     * Test that the volunteer can sign up for a job.
     */
    @Test
    public void testCanVolunteer()
    {
        try
        {
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            sameVolunteer.volunteerForJob(jobToday, WorkLoad.LIGHT); // job doesn't conflict
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests volunteer can't sign up for past job.
     */
    @Test
    public void testVolunteerPastJobException()
    {
        try
        {
            sameVolunteer.volunteerForJob(pastJob, WorkLoad.LIGHT);
            fail();
        }
        catch (Exception e)
        {
            assertEquals(JobToThePastException.class, e.getClass());
        }
    }

    /**
     * Tests exception is thrown when duplicate job is attempted to volunteer.
     */
    @Test
    public void testVolunteerDuplicateJobException()
    {
        try
        {
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            fail();
        }
        catch (Exception e)
        {
            assertEquals(AlreadyVolunteeredException.class, e.getClass());
        }
    }
    
    /**
     * Test that a job is full.
     */
    @Test
    public void testVolunteerJobIsFullException() {
        try
        {
            joblessVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            fail();
        }
        catch (Exception e)
        {
            assertEquals(JobIsFullException.class, e.getClass());
        }
    }

    /**
     * Tests exception thrown for jobs that interfere.
     */
    @Test
    public void testVolunteerInteferingJobException()
    {
        try
        {
            sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            sameVolunteer.volunteerForJob(conflictingJob, WorkLoad.LIGHT);
            fail();
        }
        catch (Exception e)
        {
            assertEquals(ConflictingJobCommitmentException.class, e.getClass());
        }

        try
        {
            sameVolunteer.volunteerForJob(jobConflictsBeforeStart, WorkLoad.LIGHT);
            fail();
        }
        catch (Exception e)
        {
            assertEquals(ConflictingJobCommitmentException.class, e.getClass());
        }

        try
        {
            sameVolunteer.volunteerForJob(jobConflictsAfterStart, WorkLoad.LIGHT);
            fail();
        }
        catch (Exception e)
        {
            assertEquals(ConflictingJobCommitmentException.class, e.getClass());
        }
    }

    /**
     * Test that equals method works properly
     */
    @Test
    public void testEquals()
    {
        assertEquals("Same object should be the same", joblessVolunteer,
                joblessVolunteer);
        assertEquals("Different objects with same values should be equal",
                joblessVolunteer, sameVolunteer);
        assertNotEquals("Different values shouldn't be equal values",
                joblessVolunteer, new Volunteer("i@uw.edu", "J.", "S."));
    }

    /**
     * Test that user can volunteer for job.
     */
    @Test
    public void testVolunteerForJob()
    {
        assertTrue(!oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
        try
        {
            assertTrue(oneJobVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT));
            assertTrue(oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
        }
        catch (Exception e)
        {
            fail();
        }
    }

    /**
     * Test that job is properly removed from a volunteer.
     */
    @Test
    public void testRemoveJob()
    {
        try
        {
            oneJobVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
        }
        catch (Exception e)
        {
            fail();
        }
        assertTrue(oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
        assertTrue(oneJobVolunteer.removeJob(newJob));
        assertFalse(oneJobVolunteer.getVolunteeredForJobs().contains(newJob));
    }

    /**
     * Tests business rule that a volunteer may not sign up for jobs that occur
     * on the same day.
     */
    @Test
    public void testVolunteerIsFree()
    {
        try
        {
            oneJobVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
            assertFalse(oneJobVolunteer.volunteerIsFree(newJob));
            assertTrue(oneJobVolunteer.volunteerIsFree(pastJob));
        }
        catch (AlreadyVolunteeredException | ConflictingJobCommitmentException
                | JobIsFullException | JobToThePastException e)
        {
            fail();
        }
    }

}