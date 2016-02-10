import java.util.ArrayList;
import java.util.Collection;

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

    public Collection<Volunteer> searchVolunteer(UrbanParkCalendar theCalendar,
            String theLastName)
    {
        ArrayList<Job> masterJobList = (ArrayList<Job>) theCalendar
                .getJobList();
        ArrayList<Volunteer> returnable = new ArrayList<>();

        for (Job job : masterJobList)
        {
            ArrayList<Volunteer> jobVolunteers = (ArrayList<Volunteer>) job
                    .getVolunteers();

            for (Volunteer v : jobVolunteers)
            {
                if (v.getLastName().equals(theLastName))
                {
                    returnable.add(v);
                }
            }
        }
        return returnable;
    }
}
