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
//    private int myWorkCategory;

    public Volunteer(String theEmail)
    {
        super(theEmail);
        myJobs = new ArrayList<>();
    }

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myJobs = new ArrayList<>();
    }

    public boolean volunteerForJob(Job theJob)
    {
        return myJobs.add(theJob);
    }

    /**
     * Remove a job from the volunteer's list of jobs.
     * @return true if removed successfully. 
     */
    public boolean removeJob(Job theJob)
    {
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
    public void canVolunteer(Job theJob) throws Exception { // throw exception if can't volunteer
        // check that job doesn't already exist in user list of jobs
        if (!newJob(theJob)) {
            throw new Exception("Already signed up.");
        }
 
        // check that job is not past job
        if (!notPastJob(theJob)) { 
            throw new Exception("Job is in the past.");
        }
     
        // check that job is not full
        if (!jobNotFull(theJob)) {
            throw new Exception("Job is full.");
        }
        
        // check that user has not signed up for job on same day.
        
        
        
        // no exceptions thrown, allow to volunteer
        volunteerForJob(theJob);
    }
    
    /**
     * Return true if the job has not been assigned to the volunteer already.
     */
    private boolean newJob(Job theJob) {
        return !myJobs.contains(theJob);
    }
    
    /**
     * Return true if the job is not full of volunteers for the work category associated
     * with the volunteer.
     */
     private boolean jobNotFull(Job theJob) {
         return false;
     }
    
    // move methods below into the job class
    
    /**
     * Return true if the job is in the future.
     */
    private boolean notPastJob(Job theJob) {
        // new date returns the current time ? 
        return theJob.getStartDate().after(new Date());
    }

}
