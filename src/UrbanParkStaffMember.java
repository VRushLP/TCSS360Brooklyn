
public class UrbanParkStaffMember extends AbstractUser
{
    private static final long serialVersionUID = -5534126442323868632L;

    public UrbanParkStaffMember(String theEmail)
    {
        super(theEmail);
    }

    public UrbanParkStaffMember(String theEmail, String theFirstName,
            String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }
}
