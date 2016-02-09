import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Job implements Serializable
{
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 2; // 2 days

    // These are intentionally left at package visibility
    Collection<Volunteer> volunteerList;
    Park associatedPark;
    int maxVolunteers;
    int volunteerCount;
    Date startDate;
    Date endDate;
    String jobTitle;
    String jobDescription;

    /**
     * Creates a job.
     * 
     * @param thePark
     *            - the name of the Park holding the job.
     * @param theMaxVolunteers
     *            - the maximum volunteers needed.
     * @param theStartDate
     *            - the start date of the job.
     * @param theJobTitle
     *            - the title of the job.
     * @param theJobDescription
     *            - a description of job details.
     */
    Job(Park thePark, int theMaxVolunteers, Date theStartDate, Date theEndDate,
            String theJobTitle, String theJobDescription)
    {
        // volunteer list starts empty
        volunteerList = new ArrayList<Volunteer>();
        associatedPark = thePark;
        // check if max volunteers is 30
        maxVolunteers = theMaxVolunteers;
        volunteerCount = 0;
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;
    }

    /**
     * Returns this instance of a job.
     * 
     * @return this job.
     */
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
        if (!volunteerList.contains(theVolunteer)
                && maxVolunteers > volunteerCount)
        {
            volunteerList.add(theVolunteer);
        }
    }

    /**
     * Lets a user edit the job title.
     * 
     * @param theJobDescription
     *            - text describing the job.
     */
    void setJobTitle(String theJobTitle)
    {
        jobTitle = theJobTitle;
    }

    /**
     * Lets a user edit the job description.
     * 
     * @param theJobDescription
     *            - text describing the job.
     */
    void setJobDescription(String theJobDescription)
    {
        jobDescription = theJobDescription;
    }

    /**
     * Lets a user edit the start of a job.
     * 
     * @param theStartDate
     *            - the start of the job.
     */
    void setStartDate(Date theStartDate)
    {

    }

    /**
     * Lets a user edit the end of a job.
     * 
     * @param theEndDate
     *            - the end of a job.
     */
    void setEndDate(Date theEndDate)
    {

    }

    // Left out workCategory enum. We should talk about how we would use it
    // before we implement it.

    @Override
    public String toString()
    {
        // early version of to string to help in testing
        StringBuilder jobDetails = new StringBuilder();
        jobDetails.append("Job: ");
        jobDetails.append(jobTitle);
        jobDetails.append("\nLocation: ");
        jobDetails.append(associatedPark);
        jobDetails.append("\nVolunteers Needed: ");
        jobDetails.append(maxVolunteers);
        jobDetails.append("\nVolunteers Signed up: ");
        jobDetails.append(volunteerList.size());
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
        return false;
    }
}
