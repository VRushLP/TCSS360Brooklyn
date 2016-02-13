import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * 
 * 
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class Job implements Serializable
{
    private static final long serialVersionUID = 8218272151272583884L;
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 2; // 2 days

    // These are intentionally left at package visibility
    private Collection<Volunteer> volunteers;
    private Park associatedPark;
    private int maxVolunteers;

    private Date startDate;
    private Date endDate;

    private String jobTitle;
    private String jobDescription;

    public Job(Park thePark, int theMaxVolunteers, Date theStartDate,
            Date theEndDate, String theJobTitle, String theJobDescription)
    {
        // volunteer list starts empty
        volunteers = new ArrayList<>();
        associatedPark = thePark;
        maxVolunteers = theMaxVolunteers; // check if max volunteers is 30
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;

        associatedPark.addJob(this);
    }

    @Override
    public String toString()
    {
        StringBuilder jobDetails = new StringBuilder();
        jobDetails.append("Job: ");
        jobDetails.append(jobTitle);
        jobDetails.append("\nLocation: ");
        jobDetails.append(associatedPark);
        jobDetails.append("\nVolunteers Needed: ");
        jobDetails.append(maxVolunteers);
        jobDetails.append("\nVolunteers Signed up: ");
        jobDetails.append(volunteers.size());
        jobDetails.append("\nStart Date: ");
        jobDetails.append(startDate);
        jobDetails.append("\nEnd Date: ");
        jobDetails.append(endDate);
        jobDetails.append("\n");
        return jobDetails.toString();
    }

    public boolean addVolunteer(Volunteer theVolunteer)
    {
        if (volunteers.size() < maxVolunteers)
        {
            volunteers.add(theVolunteer);
            return true;
        }
        return false;
    }

    public int getMaxVolunteers()
    {
        return maxVolunteers;
    }

    public void setMaxVolunteers(int maxVolunteers)
    {
        this.maxVolunteers = maxVolunteers;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription()
    {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription)
    {
        this.jobDescription = jobDescription;
    }

    public Collection<Volunteer> getVolunteers()
    {
        return Collections.unmodifiableCollection(volunteers);
    }

    public Park getAssociatedPark()
    {
        return associatedPark;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Job)
        {
            Job compare = (Job) o;
            // Should be the bare minimum to determine if a Job is unique or
            // not.
            return jobTitle.equals(compare.jobTitle)
                    && jobDescription.equals(compare.jobDescription)
                    && associatedPark.equals(compare.associatedPark)
                    && startDate.equals(compare.startDate);
        }

        return false;
    }
}
