import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractUser implements User, Serializable
{
    private static final long serialVersionUID = -483510211432682135L;
    private String lastName;
    private String firstName;
    private String email;

    public AbstractUser(String theEmail)
    {
        this.email = theEmail;
    }

    public AbstractUser(String theEmail, String theFirstName, String theLastName)
    {
        this(theEmail);
        this.firstName = theFirstName;
        this.lastName = theLastName;
    }

    @Override
    public void viewJob(Job theJob)
    {
        System.out.println(theJob.toString());
    }

    public void viewAllJobs(UrbanParkCalendar theCalendar)
    {
        ArrayList<Job> allJobs = (ArrayList<Job>) theCalendar.getJobList();

        for (int i = 0; i < allJobs.size(); i++)
        {
            System.out.println(i + " " + allJobs.get(i));
        }
    }

    @Override
    public String toString()
    {
        StringBuilder abstractUserString = new StringBuilder();
        abstractUserString.append("E-mail: ");
        abstractUserString.append(email);
        abstractUserString.append(" Name: ");
        abstractUserString.append(lastName);
        abstractUserString.append(", ");
        abstractUserString.append(firstName);

        return abstractUserString.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof AbstractUser)
        {
            AbstractUser comparison = (AbstractUser) o;

            return email.equals(comparison.email)
                    && firstName.equals(comparison.firstName)
                    && lastName.equals(comparison.lastName);
        }
        return false;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String theLastName)
    {
        this.lastName = theLastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String theFirstName)
    {
        this.firstName = theFirstName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String theEmail)
    {
        this.email = theEmail;
    }
}
