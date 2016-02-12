import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 
 * 
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class Job implements Serializable
{
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 2;     // 2 days

    
    Collection<Volunteer> volunteers;        // set of volunteers
    Park associatedPark;
    int maxVolunteers;
    int volunteerCount;
    Date startDate;
    Date endDate;
    String jobTitle;
    String jobDescription;
    
    
    Job(Park thePark, int theMaxVolunteers, Date theStartDate, Date theEndDate,
            String theJobTitle, String theJobDescription)
    {
        volunteers = new ArrayList<Volunteer>();
        associatedPark = thePark;
        maxVolunteers = theMaxVolunteers;   // check if max volunteers is 30
        volunteerCount = 0;
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;
        
        associatedPark.parksJobList.add(this);
    }

    Job getJob()
    {
        return this; // are we just returning this instance?
    }

    /**
     * Add a volunteer to the job. A volunteer will only be added if they have
     * not signed up for the job already and the max volunteer count has not
     * been reached.
     * 
     * @param theVolunteer
     */
    void addVolunteer(Volunteer theVolunteer)
    {
        // add if the volunteer has not signed up already & max volunteers not
        // reached
        if (!volunteers.contains(theVolunteer) && maxVolunteers > volunteerCount)
        {
            volunteers.add(theVolunteer);
        }
    }


    void setJobTitle(String theJobTitle)
    {
        jobTitle = theJobTitle;
    }


    void setJobDescription(String theJobDescription)
    {
        jobDescription = theJobDescription;
    }

    void setStartDate(Date theStartDate)
    {
        startDate = theStartDate;

    }

    void setEndDate(Date theEndDate)
    {
        endDate = theEndDate;
    }

    // Left out workCategory enum. We should talk about how we would use it
    // before we implement it.

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
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Job)
        {
            Job comparison = (Job) o;

            return volunteers.equals(comparison.volunteers)
             && associatedPark.equals(comparison.associatedPark)
             && maxVolunteers == comparison.maxVolunteers
             && volunteerCount == comparison.volunteerCount
             && startDate.equals(comparison.startDate)
             && endDate.equals(comparison.endDate)
             && jobTitle.equals(comparison.jobTitle)
             && jobDescription.equals(comparison.jobDescription);
        }

        return false;
    }
}
