package model;

public class UrbanParkStaffMember extends AbstractUser
{
    private static final long serialVersionUID = -8561228001048029689L;

    public UrbanParkStaffMember(String theEmail, String theFirstName,
            String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }
}
