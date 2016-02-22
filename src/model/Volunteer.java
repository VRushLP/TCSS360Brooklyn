package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import exception.AlreadyVolunteeredException;
import exception.ConflictingJobCommitmentException;
import exception.JobIsFullException;
import exception.JobToThePastException;

/**
 * @author Bethany Eastman
 * @version 02/21/2016
 */
public class Volunteer extends AbstractUser
{
    private static final long serialVersionUID = -3492237092177579789L;
    private Collection<Job> myJobs;

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myJobs = new ArrayList<>();
    }

    public boolean volunteerForJob(Job theJob)
            throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobIsFullException,
            JobToThePastException
    {
        checkForConflicts(theJob);
        return myJobs.add(theJob);
    }

    public boolean removeJob(Job theJob)
    {
        return myJobs.remove(theJob);
    }

    public Collection<Job> getVolunteeredForJobs()
    {
        return Collections.unmodifiableCollection(myJobs);
    }

    private void checkForConflicts(Job theJob)
            throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobIsFullException,
            JobToThePastException
    {
        if (isPastJob(theJob))
        {
            throw new JobToThePastException();
        }

        // make sure user hasn't signed up for job already
        if (myJobs.contains(theJob))
        {
            throw new AlreadyVolunteeredException();
        }

        // check if user has signed up for job on same day
        if (!volunteerIsFree(theJob))
        {
            throw new ConflictingJobCommitmentException();
        }

        // make sure job is not full already
        // TODO Change this so it works for multiple work categories.
        if (theJob.getVolunteers().size() == theJob.getMaxVolunteers())
        {
            throw new JobIsFullException();
        }
    }

    /**
     * Checks that there are no days overlapping with a potential job in the
     * Volunteers current list of jobs.
     */
    public boolean volunteerIsFree(Job theJob)
    {
        boolean isFree = true;

        for (Job otherJob : myJobs)
        {
            // if start / end days overlap with first day
            if (startDayOverlaps(theJob, otherJob))
                isFree = false;
            // if start / end days overlap with last day
            if (endDayOverlaps(theJob, otherJob))
                isFree = false;
            // if start or end days are the same
            if (shareDates(theJob, otherJob))
                isFree = false;
        }

        return isFree;
    }

    /**
     * Return true if the job already happened.
     */
    public boolean isPastJob(Job theJob)
    {
        return theJob.getStartDate().before(new Date());
    }

    /**
     * Return true if a job overlaps with another jobs start date.
     */
    public boolean startDayOverlaps(Job theJob, Job theOtherJob)
    {
        return theJob.getStartDate().before(theOtherJob.getStartDate())
                && theJob.getEndDate().after(theOtherJob.getStartDate());
    }

    /**
     * Return true if a job overlaps with another jobs end date.
     */
    public boolean endDayOverlaps(Job theJob, Job theOtherJob)
    {
        return theJob.getStartDate().before(theOtherJob.getEndDate())
                && theJob.getEndDate().after(theOtherJob.getEndDate());
    }

    /**
     * Return true if two jobs share any start or end dates.
     */
    public boolean shareDates(Job theJob, Job theOtherJob)
    {
        return theJob.getStartDate().equals(theOtherJob.getStartDate())
                || theJob.getStartDate().equals(theOtherJob.getEndDate())
                || theJob.getEndDate().equals(theOtherJob.getStartDate())
                || theJob.getEndDate().equals(theOtherJob.getEndDate());
    }
}
