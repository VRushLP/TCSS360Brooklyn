package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
import exception.DuplicateJobExistsException;
import exception.JobToThePastException;
import exception.JobTimeTravelException;
import exception.JobToTheFutureException;
import exception.JobTooLongException;

public class UrbanParkCalendar implements Serializable
{
    private static final long serialVersionUID = 3638477910314787711L;
    public static final int MAX_JOBS = 30;
    public static final int MAX_WEEKLY_JOBS = 5;
    public static final int MAX_DATE_FROM_TODAY = 90;

    private static Collection<Job> upcomingJobCollection;
    private static Collection<Job> pastJobCollection;
    private static Collection<Volunteer> allVolunteers;
    private static GregorianCalendar calendar;

    public UrbanParkCalendar()
    {
        upcomingJobCollection = new ArrayList<Job>();
        pastJobCollection = new ArrayList<Job>();
        allVolunteers = new ArrayList<>();
        calendar = new GregorianCalendar();
    }

    public void updateCalendar()
    {
        calendar.setTime(new Date());
        ArrayList<Job> jobsToCheck = new ArrayList<>(upcomingJobCollection);

        // Attempting to update upcomingJobCollection while using a for each
        // loop causes an exception.
        for (Job j : jobsToCheck)
        {
            if (j.getStartDate().before(calendar.getTime()))
            {
                pastJobCollection.add(j);
                upcomingJobCollection.remove(j);
            }
        }
        Collections.sort((List<Job>) upcomingJobCollection);
    }

    public Collection<Volunteer> getAllVolunteers()
    {
        return Collections.unmodifiableCollection(allVolunteers);
    }

    public boolean addVolunteer(Volunteer theVolunteer)
    {
        if (!allVolunteers.contains(theVolunteer))
        {
            return allVolunteers.add(theVolunteer);
        }
        return false;
    }

    public Collection<Job> getJobList()
    {
        updateCalendar();
        return Collections.unmodifiableCollection(upcomingJobCollection);
    }

    public boolean addJob(Job theJob) throws CalendarWeekFullException,
            CalendarFullException, JobTooLongException, JobTimeTravelException,
            JobToThePastException, JobToTheFutureException,
            DuplicateJobExistsException
    {
        checkForDuplicates(theJob);
        checkJobDate(theJob);
        checkForRoomThatWeek(theJob);
        checkJobCapacity();
        return upcomingJobCollection.add(theJob);
    }

    private void checkForDuplicates(Job theJob)
            throws DuplicateJobExistsException
    {
        if (upcomingJobCollection.contains(theJob))
        {
            throw new DuplicateJobExistsException();
        }
    }

    private void checkJobDate(Job theJob) throws JobToThePastException,
            JobToTheFutureException, JobTooLongException,
            JobTimeTravelException
    {
        if (theJob.getEndDate().getTime() - theJob.getStartDate().getTime() > TimeUnit.DAYS
                .toMillis(Job.MAX_JOB_LENGTH))
        {
            throw new JobTooLongException();
        }

        if (theJob.getStartDate().before(calendar.getTime()))
        {
            throw new JobToThePastException();
        }

        if (theJob.getStartDate().getTime() - calendar.getTime().getTime() > TimeUnit.DAYS
                .toMillis(MAX_DATE_FROM_TODAY))
        {
            throw new JobToTheFutureException();
        }

        if (theJob.getEndDate().before(theJob.getStartDate()))
        {
            throw new JobTimeTravelException();
        }
    }

    private void checkJobCapacity() throws CalendarFullException
    {
        if (upcomingJobCollection.size() >= MAX_JOBS)
            throw new CalendarFullException();
    }

    private void checkForRoomThatWeek(Job theJob)
            throws CalendarWeekFullException
    {
        ArrayList<Job> check = new ArrayList<>(upcomingJobCollection);

        // Three days on either side.
        Date minDate = new Date(theJob.getStartDate().getTime()
                - TimeUnit.DAYS.toMillis(3) - 1);
        Date maxDate = new Date(theJob.getStartDate().getTime()
                + TimeUnit.DAYS.toMillis(3) + 1);

        int jobsThatWeek = 0;

        for (Job j : check)
        {
            if (j.getStartDate().after(minDate)
                    && j.getStartDate().before(maxDate))
            {
                jobsThatWeek++;
            }
        }

        if (jobsThatWeek >= MAX_WEEKLY_JOBS)
        {
            throw new CalendarWeekFullException();
        }
    }

    public boolean removeJob(Job theJob)
    {
        return upcomingJobCollection.remove(theJob);
    }

    public Job editJobTitle(Park park, Job jobToEdit, String jobTitle)
            throws DuplicateJobExistsException
    {
        Job tempJob = new Job(jobToEdit);
        tempJob.setJobTitle(jobTitle);

        checkForDuplicates(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        park.removeJob(jobToEdit);
        park.addJob(tempJob);

        return tempJob;
    }

    public Job editJobDesc(Park park, Job jobToEdit, String jobDescription)
            throws DuplicateJobExistsException
    {
        Job tempJob = new Job(jobToEdit);
        tempJob.setJobDescription(jobDescription);

        checkForDuplicates(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        park.removeJob(jobToEdit);
        park.addJob(tempJob);

        return tempJob;
    }

    public Job editJobDates(Park park, Job jobToEdit, Date startDate,
            Date endDate) throws JobToThePastException,
            JobToTheFutureException, JobTooLongException,
            CalendarWeekFullException, JobTimeTravelException
    {
        Job tempJob = new Job(jobToEdit);
        tempJob.setStartDate(startDate);
        tempJob.setEndDate(endDate);

        checkJobDate(tempJob);
        checkForRoomThatWeek(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        park.removeJob(jobToEdit);
        park.addJob(tempJob);

        return tempJob;
    }

    //TODO Change this to accept 3 numbers and edit all job categories
    public Job editMaxVol(Park park, Job jobToEdit, int maxVolunteers)
    {
        if (maxVolunteers > Job.MAX_VOLUNTEER_NUM)
            maxVolunteers = Job.MAX_VOLUNTEER_NUM;

        Job tempJob = new Job(jobToEdit);
        // set max volunteers needs to be changed for work categories
//        tempJob.setMaxVolunteers(maxVolunteers);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        park.removeJob(jobToEdit);
        park.addJob(tempJob);

        return tempJob;
    }

    public void overrideJobCollection(Collection<Job> theJobs)
    {
        upcomingJobCollection = new ArrayList<>(theJobs);
        updateCalendar();
    }
}