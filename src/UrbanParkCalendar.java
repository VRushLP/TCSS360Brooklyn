import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class UrbanParkCalendar
{
    public static final int MAX_JOBS = 30;
    public static final int MAX_WEEKLY_JOBS = 5;
    public static final int MAX_DATE_FROM_TODAY = 90;

    Collection<Job> masterJobList;
    Date currentDate;

    public UrbanParkCalendar()
    {
        // Example of making new List out of a Collection
        masterJobList = new ArrayList<>();
        currentDate = new Date();
    }

    public Collection<Job> getJobList()
    {
        return masterJobList;
    }

    public void addJob(Job theJob)
    {
        masterJobList.add(theJob);
    }

    public void removeJob(Job theJob)
    {
        masterJobList.remove(theJob);
    }

}