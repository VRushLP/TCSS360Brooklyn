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
    private static Date currentDate;

    public UrbanParkCalendar()
    {
        masterJobCollection = new ArrayList<>();
        // Throws an exception at this point
//        setDate(new Date());
    }

    public Collection<Job> getJobList()
    {
        return Collections.unmodifiableCollection(masterJobCollection);
    }

    public void addJob(Job theJob)
    {
        masterJobCollection.add(theJob);
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