package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
    
    public boolean volunteerForJob(Job theJob, WorkLoad theWorkLoad)
            throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobIsFullException,
            JobToThePastException
    {
        checkForConflicts(theJob);

        if (theJob.hasMaxVolunteers(theWorkLoad)) throw new JobIsFullException();

        switch (theWorkLoad)
        {
            case LIGHT:
                theJob.addLightVolunteer(this);
                break;
            case MEDIUM:
                theJob.addMediumVolunteer(this);
                break;
            case DIFFICULT:
                theJob.addDifficultVolunteer(this);
                break;
        }

        return myJobs.add(theJob);
    }

    public boolean removeJob(Job theJob)
    {
        theJob.removeVolunteer(this);
        return myJobs.remove(theJob);
    }

    public Collection<Job> getVolunteeredForJobs()
    {
        return Collections.unmodifiableCollection(myJobs);
    }

    /**
     * Checks if a volunteer has conflicts with a job, and  will throw the appropriate 
     * exception if the job has conflicts.
     * 
     * @param theJob - job to be volunteered for.
     * @throws AlreadyVolunteeredException - thrown if the user has already signed up
     * for this job.
     * @throws ConflictingJobCommitmentException - thrown if the job dates conflict with
     * another job the user already has volunteer for.
     * @throws JobToThePastException - thrown if the jobs start date has already passed.
     */
    private void checkForConflicts(Job theJob)
            throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobToThePastException
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
            // if start / end days overlap with first or last day
            // if start or end days are the same

            if (theJob.shareDates(otherJob) || theJob.endDayOverlaps(otherJob)
                    || theJob.startDayOverlaps(otherJob))
                isFree = false;
            ;
        }

        return isFree;
    }
}
