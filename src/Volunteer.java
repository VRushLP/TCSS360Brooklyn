import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Volunteer extends AbstractUser
{
    private static final long serialVersionUID = 5475286744817127535L;
    private Collection<Job> myJobs;

    public Volunteer(String theEmail)
    {
        super(theEmail);
        myJobs = new ArrayList<>();
    }

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
