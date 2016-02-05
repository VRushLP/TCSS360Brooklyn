import java.util.Collection;
import java.util.Date;

public class Job
{
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 48;

    // These are intentionally left at package visibility
    Collection<Volunteer> volunteerList;
    Park associatedPark;
    Date startDate;
    String jobTitle;
    String jobDescription;

    // Left out workCategory enum. We should talk about how we would use it
    // before we implement it.
}
