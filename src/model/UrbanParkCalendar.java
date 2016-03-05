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
import exception.JobWorksTooHardException;

public class UrbanParkCalendar implements Serializable
{
    private static final long serialVersionUID = 3638477910314787711L;
    public static final int MAX_JOBS = 30;
    public static final int MAX_WEEKLY_JOBS = 5;
    public static final int MAX_DATE_FROM_TODAY = 90;
    public static final int DAYS_ON_EITHER_SIDE = 3;

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

    /**
     * Updates an UrbanParkCalendar so that only jobs that have not yet started
     * are present in the collection of upcoming jobs.<br>
     * Multiple methods call this method to make sure that the UrbanParkCalendar
     * is as up to date as possible.
     */
    public void updateCalendar()
    {
        calendar.setTime(new Date());
        ArrayList<Job> jobsToCheck = new ArrayList<>(upcomingJobCollection);

        // Attempting to remove elements from a collection while also iterating
        // through it with a for-each loop causes an exception.
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

    /**
     * Puts a Volunteer in the Volunteer collection, if they are not already in
     * it.<br>
     * If a Volunteer is already present in the collection, this method does not
     * change the collection.
     * 
     * @param theVolunteer
     *            The volunteer to be placed in the collection.
     * @return True if the volunteer is added, false otherwise.
     */
    public boolean addVolunteer(Volunteer theVolunteer)
    {
        if (!allVolunteers.contains(theVolunteer))
        {
            return allVolunteers.add(theVolunteer);
        }
        return false;
    }

    /**
     * @return The most up-to-date version of the upcoming job collection. This
     *         collection is unmodifiable.
     */
    public Collection<Job> getJobList()
    {
        updateCalendar();
        return Collections.unmodifiableCollection(upcomingJobCollection);
    }

    /**
     * Attempts to add a job to the upcoming Job collection.
     * 
     * @param theJob
     *            The job being added.
     * @return True if the Job is added to the collection successfully, false if
     *         an unknown error occurs. Most errors are covered in the thrown
     *         exceptions.
     * @throws CalendarFullException
     *             If the Job would cause there to be more than 30 total Jobs in
     *             the calendar, this exception is thrown.
     * @throws DuplicateJobExistsException
     *             If a Job already exists in the calendar, this exception is
     *             thrown.
     * @throws JobWorksTooHardException
     *             If a Job has more than 30 open slots for volunteers across
     *             all work categories, this exception is thrown.
     * @throws JobToThePastException
     *             If a Job has a date that already happened, this exception is
     *             thrown.
     * @throws JobToTheFutureException
     *             If a Job is set to occur farther out than the maximum
     *             acceptable date, this exception is thrown.
     * @throws JobTooLongException
     *             If a Job's end date is more than the maximum acceptable
     *             distance from it's starting date, this exception is thrown.
     * @throws JobTimeTravelException
     *             If a Job's start date is set after it's end date, this
     *             exception is thrown.
     * @throws CalendarWeekFullException
     *             If a Job would take place in a 7 day period that is already
     *             considered full, this exception is thrown. A week is
     *             considered full if there is 5 or more jobs that are within 3
     *             days of the job that is trying to be added.
     */
    public boolean addJob(Job theJob) throws CalendarFullException,
            JobWorksTooHardException, DuplicateJobExistsException,
            JobToThePastException, JobToTheFutureException, JobTooLongException,
            JobTimeTravelException, CalendarWeekFullException
    {
        checkJobCapacity();
        checkForDuplicates(theJob);
        checkForJobWorksTooHard(theJob);
        checkJobDate(theJob);
        checkForRoomThatWeek(theJob);
        return upcomingJobCollection.add(theJob);
    }

    /**
     * Checks if a JobWorksTooHardException should be thrown. See the
     * documentation of {@link#addJob} for further details.
     */
    public void checkForJobWorksTooHard(Job theJob)
            throws JobWorksTooHardException
    {
        if (theJob.getMaxLightVolunteers() + theJob.getMaxMediumVolunteers()
                + theJob.getMaxDifficultVolunteers() > Job.MAX_VOLUNTEER_NUM)
            throw new JobWorksTooHardException();
    }

    /**
     * Checks if a DuplicateJobExistsException should be thrown. See the
     * documentation of {@link#addJob} for further details.
     */
    public void checkForDuplicates(Job theJob)
            throws DuplicateJobExistsException
    {
        if (upcomingJobCollection.contains(theJob))
        {
            throw new DuplicateJobExistsException();
        }
    }

    /**
     * Validates the date of a Job.<br>
     * See the documentation of {@link#addJob} for further details.
     */
    public void checkJobDate(Job theJob) throws JobToThePastException,
            JobToTheFutureException, JobTooLongException, JobTimeTravelException
    {
        calendar.setTime(new Date());
        checkForJobTooLong(theJob);
        checkForJobToTheFuture(theJob);
        checkForJobToThePast(theJob);
        checkForJobTimeTravel(theJob);
    }

    /**
     * Checks if a JobTooLongException should be thrown. See the documentation
     * of {@link#addJob} for further details.
     */
    public void checkForJobTooLong(Job theJob) throws JobTooLongException
    {
        if (theJob.getEndDate().getTime()
                - theJob.getStartDate().getTime() > TimeUnit.DAYS
                        .toMillis(Job.MAX_JOB_LENGTH))
        {
            throw new JobTooLongException();
        }
    }

    /**
     * Checks if a JobToThePastException should be thrown. See the documentation
     * of {@link#addJob} for further details.
     */
    public void checkForJobToThePast(Job theJob) throws JobToThePastException
    {
        if (theJob.getStartDate().before(calendar.getTime()))
        {
            throw new JobToThePastException();
        }
    }

    /**
     * Checks if a JobToTheFutureException should be thrown. See the
     * documentation of {@link#addJob} for further details.
     */
    public void checkForJobToTheFuture(Job theJob)
            throws JobToTheFutureException
    {
        if (theJob.getStartDate().getTime()
                - calendar.getTime().getTime() > TimeUnit.DAYS
                        .toMillis(MAX_DATE_FROM_TODAY))
        {
            throw new JobToTheFutureException();
        }
    }

    /**
     * Checks if a JobTimeTravelException should be thrown. See the
     * documentation of {@link#addJob} for further details.
     */
    public void checkForJobTimeTravel(Job theJob) throws JobTimeTravelException
    {
        if (theJob.getEndDate().before(theJob.getStartDate()))
        {
            throw new JobTimeTravelException();
        }
    }

    /**
     * Checks if a CalendarFullException should thrown. See the documentation of
     * {@link#addJob} for further details.
     */
    public void checkJobCapacity() throws CalendarFullException
    {
        if (upcomingJobCollection.size() >= MAX_JOBS)
            throw new CalendarFullException();
    }

    /**
     * Checks if a CalendarWeekFullException should be thrown. See the
     * documentation of {@link#addJob} for further details.
     */
    public void checkForRoomThatWeek(Job theJob)
            throws CalendarWeekFullException
    {
        ArrayList<Job> check = new ArrayList<>(upcomingJobCollection);

        // Three days on either side.
        Date minDate = new Date(theJob.getStartDate().getTime()
                - TimeUnit.DAYS.toMillis(DAYS_ON_EITHER_SIDE) - 1);
        Date maxDate = new Date(theJob.getStartDate().getTime()
                + TimeUnit.DAYS.toMillis(DAYS_ON_EITHER_SIDE) + 1);

        int jobsThatWeek = 0;

        for (Job j : check)
        {
            if (j.getStartDate().after(minDate)
                    && j.getStartDate().before(maxDate))
            {
                if (++jobsThatWeek >= MAX_WEEKLY_JOBS)
                {
                    throw new CalendarWeekFullException();
                }
            }
        }
    }

    /**
     * Removes a Job from the upcoming job collection.
     * 
     * @param theJob
     *            The job to remove.
     * @return True if the job is removed, false if there was an unknown error.
     */
    public boolean removeJob(Job theJob)
    {
        return upcomingJobCollection.remove(theJob);
    }

    /**
     * Edits the title of a job in the passed park.
     * 
     * @return A reference to the editted job.
     * @throws DuplicateJobExistsException
     *             If the edit would cause the job to be a duplicate of a job
     *             that already exists.
     */
    public Job editJobTitle(Park thePark, Job jobToEdit, String jobTitle)
            throws DuplicateJobExistsException
    {
        Job tempJob = new Job(jobToEdit);
        tempJob.setJobTitle(jobTitle);

        checkForDuplicates(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        thePark.removeJob(jobToEdit);
        thePark.addJob(tempJob);

        return tempJob;
    }

    /**
     * Edits the description of a job in the passed park.
     * 
     * @return a reference to the editted job.
     * @throws DuplicateJobExistsException
     *             If the edit would cause the job to be a duplicate of a job
     *             that already exists.
     */
    public Job editJobDesc(Park thePark, Job jobToEdit, String jobDescription)
            throws DuplicateJobExistsException
    {
        Job tempJob = new Job(jobToEdit);
        tempJob.setJobDescription(jobDescription);

        checkForDuplicates(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        thePark.removeJob(jobToEdit);
        thePark.addJob(tempJob);

        return tempJob;
    }

    /**
     * Edits the dates of a job in the passed park.
     * 
     * @return A reference to the editted job.
     * 
     * @throws JobToThePastException
     *             If a Job has a date that already happened, this exception is
     *             thrown.
     * @throws JobToTheFutureException
     *             If a Job is set to occur farther out than the maximum
     *             acceptable date, this exception is thrown.
     * @throws JobTooLongException
     *             If a Job's end date is more than the maximum acceptable
     *             distance from it's starting date, this exception is thrown.
     * @throws JobTimeTravelException
     *             If a Job's start date is set after it's end date, this
     *             exception is thrown.
     */
    public Job editJobDates(Park thePark, Job jobToEdit, Date startDate,
            Date endDate) throws JobToThePastException, JobToTheFutureException,
                    JobTooLongException, CalendarWeekFullException,
                    JobTimeTravelException
    {
        Job tempJob = new Job(jobToEdit);
        tempJob.setStartDate(startDate);
        tempJob.setEndDate(endDate);

        checkJobDate(tempJob);
        checkForRoomThatWeek(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        thePark.removeJob(jobToEdit);
        thePark.addJob(tempJob);

        return tempJob;
    }

    /**
     * Edits a job in the passed park to have new volunteer work load counts.
     * 
     * @return A reference to the editted job.
     * @throws JobWorksTooHardException
     *             If the new counts total over 30.
     */
    public Job editMaxVol(Park park, Job jobToEdit, int newLightWork,
            int newMedWork, int newHardWork) throws JobWorksTooHardException
    {
        Job tempJob = new Job(jobToEdit);
        // set max volunteers needs to be changed for work categories
        tempJob.setMaxLightVolunteers(newLightWork);
        tempJob.setMaxMediumVolunteers(newMedWork);
        tempJob.setMaxDifficultVolunteers(newHardWork);

        checkForJobWorksTooHard(tempJob);

        upcomingJobCollection.remove(jobToEdit);
        upcomingJobCollection.add(tempJob);

        park.removeJob(jobToEdit);
        park.addJob(tempJob);

        return tempJob;
    }

    /**
     * Forces an override of the current job collection, resetting the upcoming
     * job collection.
     */
    public void overrideJobCollection(Collection<Job> theJobs)
    {
        upcomingJobCollection = new ArrayList<>(theJobs);
        pastJobCollection = new ArrayList<>();
        updateCalendar();
    }

    public void updatePark(Park thePark)
    {
        ArrayList<Job> upcoming = new ArrayList<>(upcomingJobCollection);
        for (Job job : upcoming)
        {
            if (job.getParkName().equalsIgnoreCase(thePark.getParkName()))
            {
                thePark.removeJob(job);
                thePark.addJob(job);
            }
        }
    }
}