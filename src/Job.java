import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Job
{
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 2; // 2 days

    // These are intentionally left at package visibility
    Collection<Volunteer> volunteerList;
    Park associatedPark;
    int maxVolunteers;
    Date startDate;
    Date endDate;
    String jobTitle;
    String jobDescription;
    
    /**
     * Creates a job.
     * 
     * @param thePark - the name of the Park holding the job.
     * @param theMaxVolunteers - the maximum volunteers needed.
     * @param theStartDate - the start date of the job.
     * @param theJobTitle - the title of the job.
     * @param theJobDescription - a description of job details.
     */
    Job(Park thePark, int theMaxVolunteers, Date theStartDate, Date theEndDate, 
    		String theJobTitle, String theJobDescription) {
    	// volunteer list starts empty
    	volunteerList = new ArrayList<Volunteer>(); 
        associatedPark = thePark;
        maxVolunteers = theMaxVolunteers;
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;
    }
    
    
    Job getJob() {
    	return this; // are we just returning this instance?
    }

    // Left out workCategory enum. We should talk about how we would use it
    // before we implement it.
    
    @Override
    public String toString() {
    	// early version of to string to help in testing
    	StringBuilder jobDetails = new StringBuilder();
    	jobDetails.append("Job: ");
    	jobDetails.append(jobTitle);
    	jobDetails.append("\nLocation: ");
    	jobDetails.append(associatedPark);
    	jobDetails.append("\nVolunteers Needed: " );
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
}

