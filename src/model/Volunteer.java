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
        
        if (theJob.isPastJob())
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
            if (theJob.startDayOverlaps(otherJob))
                isFree = false;
            // if start / end days overlap with last day
            if (theJob.endDayOverlaps(otherJob))
                isFree = false;
            // if start or end days are the same
            if (theJob.shareDates(otherJob))
                isFree = false;
        }

        return isFree;
    }
}
