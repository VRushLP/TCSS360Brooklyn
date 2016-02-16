import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public boolean addJob(Job theJob)
    {
        boolean theresRoom = checkForRoomThatWeek(theJob);

        if (theresRoom && masterJobCollection.size() < 30)
        {
            return masterJobCollection.add(theJob);
        }
        else
            return false;
    }

    private boolean checkForRoomThatWeek(Job theJob)
    {
        ArrayList<Job> check = new ArrayList<>(masterJobCollection);

        check.add(theJob);
        int checkAround = check.indexOf(theJob);
        Date minDate = new Date(theJob.getStartDate().getTime()
                - TimeUnit.DAYS.toMillis(3) - 1);
        Date maxDate = new Date(theJob.getStartDate().getTime()
                + TimeUnit.DAYS.toMillis(3) + 1);
        int jobsThatWeek = 0;

        System.out.println(minDate);
        System.out.println(maxDate);

        for (Job j : check)
        {
            if (j.getStartDate().after(minDate)
                    && j.getStartDate().before(maxDate) && !j.equals(theJob))
            {
                jobsThatWeek++;
            }
        }

        check.remove(checkAround);

        if (jobsThatWeek > MAX_WEEKLY_JOBS)
        {
            return false;
        }
        return true;
    }

    // Added edit job method
    public void editJob(Job theJob, Park park, int maxVolunteers,
            Date startDate, Date endDate, String jobTitle,
            String jobDescription)
    {

        // Iterate over masterJobCollection
        // When a job matching the parameter job is found,
        // then edit its attributes
        for (Job curJob : masterJobCollection)
        {
            if (curJob.equals(theJob))
            {
                curJob.setStartDate(startDate);
                curJob.setEndDate(endDate);
                curJob.setJobTitle(jobTitle);
                curJob.setJobDescription(jobDescription);
                curJob.setMaxVolunteers(maxVolunteers);
            }
        }

    }

    public boolean removeJob(Job theJob)
    {
        return masterJobCollection.remove(theJob);
    }

    public void setDate(Date theDate)
    {
        currentDate = theDate;
    }

    public static Date getCurrentDate()
    {
        return currentDate;
    }

    public String toString()
    {
        return masterJobCollection.toString();
    }
}