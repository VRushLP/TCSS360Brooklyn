package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import exception.AlreadyVolunteeredException;
import exception.ConflictingJobCommitmentException;
import exception.JobIsFullException;

public class Volunteer extends AbstractUser
{
    private Collection<Job> myJobs;

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myJobs = new ArrayList<>();
    }

    public boolean volunteerForJob(Job theJob)
            throws AlreadyVolunteeredException,
            ConflictingJobCommitmentException, JobIsFullException
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
            ConflictingJobCommitmentException, JobIsFullException
    {
        // make sure user hasn't signed up for job already
        if (myJobs.contains(theJob))
        {
            throw new AlreadyVolunteeredException();
        }

        // check if user has signed up for job on same day
        for (Job job : myJobs)
        {
            if (job.getStartDate().equals(theJob.getStartDate())
                    || job.getStartDate().equals(theJob.getEndDate())
                    || job.getEndDate().equals(theJob.getStartDate())
                    || job.getEndDate().equals(theJob.getEndDate()))
            {
                throw new ConflictingJobCommitmentException();
            }
        }

        // make sure job is not full already
        // TODO Change this so it works for multiple work categories.
        if (theJob.getVolunteers().size() == theJob.getMaxVolunteers())
        {
            throw new JobIsFullException();
        }
    }
}
