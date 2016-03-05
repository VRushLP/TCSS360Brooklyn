package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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

public class ParkManagerTest
{
    ParkManager p1;
    ParkManager p2;
    ParkManager p3;
    ParkManager p0;

    Volunteer joblessVolunteer;
    Volunteer sameVolunteer;
    Volunteer oneJobVolunteer;

    Job job;
    Park park;
    Collection<Park> parks;
    Job newJob;
    Job conflictingJob;
    Job pastJob;
    Job jobToday;
    Job jobConflictsBeforeStart;
    Job jobConflictsAfterStart;

    @Before
    public void setUp() throws Exception
    {
        long DAY_IN_MS = TimeUnit.DAYS.toMillis(1);

        Date sevenDays = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        Date fourDays = new Date(System.currentTimeMillis() + (4 * DAY_IN_MS));
        Date threeDays = new Date(System.currentTimeMillis() + (3 * DAY_IN_MS));
        Date fiveDays = new Date(System.currentTimeMillis() + (5 * DAY_IN_MS));
        Date sixDays = new Date(System.currentTimeMillis() + (6 * DAY_IN_MS));

        park = new Park("testPark", p2);
        job = new Job(park, 3, 0, 0, new Date(), new Date(), "Pick up trash",
                "Clean up trash from Park");
        newJob = new Job(park, 1, 0, 0, fourDays, fiveDays, "Pick up trash",
                "Clean up trash from Park");
        conflictingJob = new Job(park, 3, 0, 0, fourDays, fiveDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        pastJob = new Job(park, 3, 0, 0, sevenDays, sevenDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        jobToday = new Job(park, 3, 0, 0, new Date(), new Date(),
                "Clean bathrroms", "Clean the bathrooms in the park");
        jobConflictsBeforeStart = new Job(park, 3, 0, 0, threeDays, fiveDays,
                "Clean bathrroms", "Clean the bathrooms in the park");
        jobConflictsAfterStart = new Job(park, 3, 0, 0, fourDays, sixDays,
                "Clean bathrroms", "Clean the bathrooms in the park");

        joblessVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        sameVolunteer = new Volunteer("jobless@gmail.com", "John", "Smith");
        oneJobVolunteer = new Volunteer("b@uw.edu", "Jane", "Does");

        parks = new ArrayList<Park>();
        parks.add(park);
        p1 = new ParkManager("Email", "FirstName", "LastName");
        p0 = new ParkManager("Email", "FirstName", "LastName");
        p2 = new ParkManager("Email", "FirstName", "LastName", park);
        p3 = new ParkManager("Email", "FirstName", "LastName", parks);
    }

    @Test
    public void testParkManagerStringStringString()
    {
        assertTrue(p1.getEmail().equals("Email"));
        assertTrue(p1.getLastName().equals("LastName"));
        assertTrue(p1.getFirstName().equals("FirstName"));
    }

    @Test
    public void testParkManagerStringStringStringPark()
    {
        assertTrue(p2.getEmail().equals("Email"));
        assertTrue(p2.getLastName().equals("LastName"));
        assertTrue(p2.getFirstName().equals("FirstName"));
        assertTrue(p2.getParks().size() == 1);
    }

    @Test
    public void testParkManagerStringStringStringCollectionOfPark()
    {
        assertTrue(p3.getEmail().equals("Email"));
        assertTrue(p3.getLastName().equals("LastName"));
        assertTrue(p3.getFirstName().equals("FirstName"));
    }

    @Test
    public void testAddParkToManager()
    {
        assertTrue(p1.addParkToManager(park));
    }

    @Test
    public void testGetParks()
    {
        p1.addParkToManager(park);
        assertTrue(1 == p1.getParks().size());
    }

    @Test
    public void testSubmitNewJob()
    {
        park.addJob(job);
        p1.addParkToManager(park);
        assertTrue(p1.getParks().iterator().next().getJobs().contains(job));
    }

    @Test
    public void testDeleteJob()
    {
        park.addJob(job);
        p1.addParkToManager(park);
        p1.getParks().iterator().next().removeJob(job);
        assertTrue(!p1.getParks().iterator().next().getJobs().contains(job));
    }

    @Test
    public void testEditJob()
    {
        park.addJob(job);
        p1.addParkToManager(park);
        p1.getParks().iterator().next().getJobs().iterator().next()
                .setJobTitle("Clean bathrroms");
        ;
        assertTrue(p1.getParks().iterator().next().getJobs().iterator()
                .next().getJobTitle().equals("Clean bathrroms"));
    }

    @Test
    public void testViewUpcomingJob()
    {
        park.addJob(pastJob);
        park.addJob(newJob);
        park.addJob(jobToday);
        park.addJob(conflictingJob);
        park.addJob(jobConflictsAfterStart);
        park.addJob(jobConflictsBeforeStart);
        p1.addParkToManager(park);
        Park tmp = p1.getParks().iterator().next();
        String result = "";
        Iterator<Job> jobs = tmp.getJobs().iterator();
        while (jobs.hasNext())
        {
            Job jobTmp = jobs.next();
            if (jobTmp.getStartDate().getTime() > System.currentTimeMillis())
                result += jobTmp.toString();
        }
        String resultTrue = newJob.toString() + conflictingJob.toString()
                + jobConflictsAfterStart.toString()
                + jobConflictsBeforeStart.toString();
        assertTrue(resultTrue.equals(result));
    }

    @Test
    public void testVolunteerJob() throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobIsFullException,
            JobToThePastException
    {
        // sameVolunteer.volunteerForJob(pastJob);
        sameVolunteer.volunteerForJob(newJob, WorkLoad.LIGHT);
        oneJobVolunteer.volunteerForJob(conflictingJob, WorkLoad.LIGHT);
        park.addJob(pastJob);
        park.addJob(newJob);
        park.addJob(jobToday);
        park.addJob(conflictingJob);
        park.addJob(jobConflictsAfterStart);
        park.addJob(jobConflictsBeforeStart);
        p1.addParkToManager(park);
        Park tmp = p1.getParks().iterator().next();
        String result = "";
        Iterator<Job> jobs = tmp.getJobs().iterator();
        Collection<Volunteer> lstVolunteers = new ArrayList<Volunteer>();
        while (jobs.hasNext())
        {
            Job jobTmp = jobs.next();
            Iterator<Volunteer> volunteers = jobTmp.getVolunteers().iterator();
            int count = 0;
            while (volunteers.hasNext())
            {
                Volunteer volunteer = volunteers.next();
                lstVolunteers.add(volunteer);
            }
        }

        Volunteer volunteer = lstVolunteers.iterator().next();
        assertTrue(volunteer.getEmail().equals(sameVolunteer.getEmail()));
        assertTrue(volunteer.getFirstName()
                .equals(sameVolunteer.getFirstName()));
        assertTrue(volunteer.getLastName().equals(sameVolunteer.getLastName()));
        lstVolunteers.remove(volunteer);

        volunteer = lstVolunteers.iterator().next();
        assertTrue(volunteer.getEmail().equals(oneJobVolunteer.getEmail()));
        assertTrue(volunteer.getFirstName().equals(
                oneJobVolunteer.getFirstName()));
        assertTrue(volunteer.getLastName()
                .equals(oneJobVolunteer.getLastName()));

    }
}
