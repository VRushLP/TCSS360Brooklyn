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

    /**
     * Checks if a volunteer has conflicts with a job, and will throw the
     * appropriate exception if the job has conflicts.
     * 
     * @param theJob
     *            - job to be volunteered for.
     * @throws AlreadyVolunteeredException
     *             - thrown if the user has already signed up for this job.
     * @throws ConflictingJobCommitmentException
     *             - thrown if the job dates conflict with another job the user
     *             already has volunteer for.
     * @throws JobToThePastException
     *             - thrown if the jobs start date has already passed.
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
     * Get the Volunteer's collection of jobs, will return an empty collection
     * if the volunteer has not volunteered for any jobs.
     * 
     * @return a collection of the volunteers jobs.
     */
    public Collection<Job> getVolunteeredForJobs()
    {
        return Collections.unmodifiableCollection(myJobs);
    }

    /**
     * Removes a job from the volunteer's collection of jobs, if the collection
     * contains the job. Will also remove the volunteer from the Job's
     * collection of Volunteers for that job.
     * 
     * @param theJob
     *            - the job to remove from the volunteers collection of jobs.
     * @return true - if the job was removed successfully from the collection of
     *         a Volunteer's jobs.
     */
    public boolean removeJob(Job theJob)
    {
        theJob.removeVolunteer(this);
        return myJobs.remove(theJob);
    }

    /**
     * A Volunteer will sign up for a Job if the workload for a job do not have
     * any conflicts when added into the Volunteer's collection of Jobs. If
     * there are any conflicts, an exception will be thrown.
     * 
     * @param theJob
     *            - the Job to be added into the Volunteer's collection of Jobs.
     * @param theWorkLoad
     *            - the WorkLoad type of the job to volunteer for.
     * @return true if the job was successfully added into the collection of
     *         Jobs for the volunteer, false otherwise.
     * @throws AlreadyVolunteeredException
     *             - thrown if Volunteer already has the job contained in it's
     *             collection.
     * @throws ConflictingJobCommitmentException
     *             - thrown if the dates of a Job conflict with any days in the
     *             Volunteer's collection of Jobs.
     * @throws JobIsFullException
     *             - thrown if the Job has the maximum amount of Volunteers for
     *             the corresponding WorkLoad.
     * @throws JobToThePastException
     *             - thrown if the Job to volunteer started in the past.
     */
    public boolean volunteerForJob(Job theJob, WorkLoad theWorkLoad)
            throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobIsFullException,
            JobToThePastException
    {
        checkForConflicts(theJob);

        if (theJob.hasMaxVolunteers(theWorkLoad))
            throw new JobIsFullException();

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

    /**
     * Checks that there are no days overlapping with a potential job in the
     * Volunteers current list of jobs.
     * 
     * @return true - if the volunteer is free, false otherwise.
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
        }

        return isFree;
    }
}
