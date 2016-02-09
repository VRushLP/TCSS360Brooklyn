import java.util.ArrayList;
import java.util.Collection;

public class UrbanParkStaffMember extends AbstractUser
{
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
            ArrayList<Volunteer> jobVolunteers = (ArrayList<Volunteer>) job.volunteerList;

            for (Volunteer v : jobVolunteers)
            {
                if (v.lastName.equals(theLastName))
                {
                    returnable.add(v);
                }
            }
        }
        return returnable;
    }
}
