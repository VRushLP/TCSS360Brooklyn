import java.io.Serializable;

public class AbstractUser implements User, Serializable
{
    String lastName;
    String firstName;
    String email;

    @Override
    public void viewJob(Job theJob)
    {
        // TODO
    }

    @Override
    public void viewAllJobs(Calendar theCalendar)
    {
        // TODO
    }
}
