import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author Bethany Eastman
 * @version 02/18/2016
 */
public class Volunteer extends AbstractUser
{
    private static final long serialVersionUID = 5475286744817127535L;
    private Collection<Job> myJobs;
    // private int myWorkCategory;

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myJobs = new ArrayList<>();
    }

    public boolean volunteerForJob(Job theJob)
    {
        // add volunteer to list of volunteers in job ?
        theJob.addVolunteer(this);
        return myJobs.add(theJob);
    }

    /**
     * Remove a job from the volunteer's list of jobs.
     * 
     * @return true if removed successfully.
     */
    public boolean removeJob(Job theJob)
    {
        // remove volunteer for job's list of volunteers ?
        // theJob.removeVolunteer(this);
        return myJobs.remove(theJob);
    }

    /**
     * Return a list of the Volunteer's jobs.
     */
    public Collection<Job> getVolunteeredForJobs()
    {
        return Collections.unmodifiableCollection(myJobs);
    }

    /**
     * Will throw an exception if the user cannot volunteer for a job.
     */
    public void canVolunteer(Job theJob) throws Exception
    { // throw exception if can't volunteer
        // check that job doesn't already exist in user list of jobs
        if (isDuplicate(theJob))
        {
            throw new Exception("You have already signed up for this job.");
        }

        // check that job is not past job
        if (isPastJob(theJob))
        {
            throw new Exception("This job has already happended");
        }

        // check that job is not full
        if (isFullJob(theJob))
        {
            throw new Exception("This job has the max amount of volunteers.");
        }

        // check that user has not signed up for job on same day.
        if (!volunteerIsFree(theJob))
        {
            throw new Exception("This job intereferes with another job.");
        }

        // no exceptions thrown, allow to volunteer
//        theJob.addVolunteer(this); 
        volunteerForJob(theJob);
    }

    /**
     * Returns true if there are no days shared between jobs that a volunteer
     * has signed up for.
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
     * Return true if the job is already assigned to the volunteer.
     */
    public boolean isDuplicate(Job theJob)
    {
        return myJobs.contains(theJob);
    }

    /**
     * Return true if the job is full of volunteers for the work category
     * associated with the volunteer.
     */
    public boolean isFullJob(Job theJob)
    {
        return theJob.getVolunteers().size() == theJob.getMaxVolunteers();
        // needs to be fixed for work categories
    }

    /**
     * Return true if a job overlaps with another jobs start date.
     */
    private boolean startDayOverlaps(Job theJob, Job theOtherJob)
    {
        return theJob.getStartDate().before(theOtherJob.getStartDate())
                && theJob.getEndDate().after(theOtherJob.getStartDate());
    }

    /**
     * Return true if a job overlaps with another jobs end date.
     */
    private boolean endDayOverlaps(Job theJob, Job theOtherJob)
    {
        return theJob.getStartDate().before(theOtherJob.getEndDate())
                && theJob.getEndDate().after(theOtherJob.getEndDate());
    }

    /**
     * Return true if two jobs share any start or end dates.
     */
    private boolean shareDates(Job theJob, Job theOtherJob)
    {
        return theJob.getStartDate().equals(theOtherJob.getStartDate())
                || theJob.getStartDate().equals(theOtherJob.getEndDate())
                || theJob.getEndDate().equals(theOtherJob.getStartDate())
                || theJob.getEndDate().equals(theOtherJob.getEndDate());
    }

    /**
     * Return true if the job already happened.
     */
    public boolean isPastJob(Job theJob)
    {
        return theJob.getStartDate().before(new Date());
    }

}
