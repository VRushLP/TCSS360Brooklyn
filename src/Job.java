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
    public static final int MAX_JOB_LENGTH = 2;     // 2 days

<<<<<<< HEAD
    
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
=======
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
>>>>>>> master
        associatedPark = thePark;
        maxVolunteers = theMaxVolunteers;   // check if max volunteers is 30
        volunteerCount = 0;
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;
        
        associatedPark.parksJobList.add(this);
    }

<<<<<<< HEAD
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
=======
    @Override
    public String toString()
    {
        // TODO change this for more long term use.
>>>>>>> master
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
<<<<<<< HEAD
    
=======

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

>>>>>>> master
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Job)
        {
<<<<<<< HEAD
            Job comparison = (Job) o;

            return volunteers.equals(comparison.volunteers)
             && associatedPark.equals(comparison.associatedPark)
             && maxVolunteers == comparison.maxVolunteers
             && volunteerCount == comparison.volunteerCount
             && startDate.equals(comparison.startDate)
             && endDate.equals(comparison.endDate)
             && jobTitle.equals(comparison.jobTitle)
             && jobDescription.equals(comparison.jobDescription);
=======
            Job compare = (Job) o;
            // Should be the bare minimum to determine if a Job is unique or
            // not.
            return jobTitle.equals(compare.jobTitle)
                    && jobDescription.equals(compare.jobDescription)
                    && associatedPark.equals(compare.associatedPark)
                    && startDate.equals(compare.startDate);
>>>>>>> master
        }

        return false;
    }
}
