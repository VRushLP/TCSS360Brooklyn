import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Volunteer extends AbstractUser
{
    private Collection<Job> myJobs;

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myJobs = new ArrayList<>();
    }

    public boolean volunteerForJob(Job theJob)
    {
        return myJobs.add(theJob);
    }

    public boolean removeJob(Job theJob)
    {
        return myJobs.remove(theJob);
    }

    public Collection<Job> getVolunteeredForJobs()
    {
        return Collections.unmodifiableCollection(myJobs);
    }

}
