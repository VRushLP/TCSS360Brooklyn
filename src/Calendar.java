import java.util.ArrayList;
import java.util.Collection;

public class Calendar
{
    public static final int MAX_JOBS = 30;
    public static final int MAX_WEEKLY_JOBS = 5;
    public static final int MAX_DATE_FROM_TODAY = 90;
    // Current date should not be here. It should be checked every time we
    // instantiate a calendar object, or read one in from a file.

    Collection<Job> masterJobList;

    public Calendar()
    {
        //Example of making new List out of a Collection
        masterJobList = new ArrayList<>();
    }

    public Collection<Job> getJobList()
    {
        return masterJobList;
    }
}
