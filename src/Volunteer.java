public class Volunteer extends AbstractUser
{
    public Volunteer(String theEmail)
    {
        super(theEmail);
    }

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }
}
