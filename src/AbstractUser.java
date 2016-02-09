import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractUser implements User, Serializable
{
    String lastName;
    String firstName;
    String email;

    public AbstractUser(String theEmail)
    {
        this.email = theEmail;
    }

    public AbstractUser(String theEmail, String theFirstName,
            String theLastName)
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

    /**
     * Returns if an object is equal to another object. Should work for all
     * kinds of AbstractUser.
     * 
     * @param o
     *            The object compared.
     * @return True if the objects are equal, false otherwise.
     */
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
}
