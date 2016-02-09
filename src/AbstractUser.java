import java.io.Serializable;

public abstract class AbstractUser implements User, Serializable
{
    String lastName;
    String firstName;
    String email;

    public AbstractUser(String myEmail, String myFirstName, String myLastName)
    {

    }

    public AbstractUser(String email)
    {

    }

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

    @Override
    public String toString()
    {
        return email;
    }
}
