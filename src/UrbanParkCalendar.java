import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class UrbanParkCalendar implements Serializable
{
    private static final long serialVersionUID = -6937747495177492138L;
    public static final int MAX_JOBS = 30;
    public static final int MAX_WEEKLY_JOBS = 5;
    public static final int MAX_DATE_FROM_TODAY = 90;

    private static Collection<Job> masterJobCollection;
    private static Collection<Volunteer> allVolunteers;
    private static Date currentDate;

    public UrbanParkCalendar()
    {
        masterJobCollection = new ArrayList<Job>();
        allVolunteers = new ArrayList<>();
        // Throws an exception at this point
        // setDate(new Date());
    }

    public Collection<Volunteer> getAllUsers()
    {
        return Collections.unmodifiableCollection(allVolunteers);
    }

    public boolean addVolunteer(Volunteer theVolunteer)
    {
        return allVolunteers.add(theVolunteer);
    }

    public Collection<Job> getJobList()
    {
        return Collections.unmodifiableCollection(masterJobCollection);
    }

    public void addJob(Job theJob)
    {
        masterJobCollection.add(theJob);
    }
    
    // Added edit job method
    public void editJob(Job theJob, Park park, int maxVolunteers, Date startDate, 
                        Date endDate, String jobTitle, String jobDescription)
    {

        // Iterate over masterJobCollection
        // When a job matching the parameter job is found,
        // then edit its attributes
        for (Job j : masterJobCollection)
        {
            if (j.equals(theJob))
            {
                j.setStartDate(startDate);
                j.setEndDate(endDate);
                j.setJobTitle(jobTitle);
                j.setJobDescription(jobDescription);
                j.setMaxVolunteers(maxVolunteers);
            }
        }
        
    }

    public boolean removeJob(Job theJob)
    {
        return masterJobCollection.remove(theJob);
    }

    public void setDate(Date theDate)
    {
        setDate(theDate);
    }

    public static Date getCurrentDate()
    {
        return currentDate;
    }
}