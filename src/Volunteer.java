public class Volunteer extends AbstractUser
{
    private static final long serialVersionUID = 1426923419814220179L;

    public Volunteer(String theEmail)
    {
        super(theEmail);
    }

    public Volunteer(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }
}
