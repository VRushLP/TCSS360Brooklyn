package model;

/**
 * A class which defines an Urban Park Staff Member as a type of user. While
 * this class contains no methods, it is useful for the class definition, which
 * allows the Primary Driver to display the appropriate menu options to the
 * user.
 * 
 * @author Robert, Bethany, Lachezar, Duong
 * @version Release
 */
public class UrbanParkStaffMember extends AbstractUser
{
    private static final long serialVersionUID = -8561228001048029689L;

    public UrbanParkStaffMember(String theEmail, String theFirstName,
            String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }
}
