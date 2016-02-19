import java.util.Collections;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class ParkManager extends AbstractUser
{
    private static final long serialVersionUID = 2473533828616953096L;
    private Collection<Park> myParks;

    public ParkManager(String theEmail)
    {
        super(theEmail);
        myParks = new ArrayList<Park>();
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>();
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName,
            Park thePark)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>();
        myParks.add(thePark);
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName,
            Collection<Park> theParks)
    {
        super(theEmail, theFirstName, theLastName);
        myParks = new ArrayList<Park>(theParks);
    }

    public Collection<Park> getParks()
    {
        return Collections.unmodifiableCollection(myParks);
    }

    public boolean addParkToManager(Park thePark)
    {
        return myParks.add(thePark);
    }

    public void deleteJob(UrbanParkCalendar theUPCalendar, Job theJob,
            Park thePark)
    {
        ArrayList<Volunteer> volunteers = new ArrayList<>(
                theJob.getVolunteers());

        // Remove job from all volunteers that were associated with it.
        for (Volunteer volunteer : volunteers)
        {
            volunteer.removeJob(theJob);
        }

        // Update park
        thePark.removeJob(theJob);
        // Update calendar
        theUPCalendar.removeJob(theJob);

    }

    public void editJob(UrbanParkCalendar theUPCalendar, Job theJob,
            Park thePark, int maxVolunteers, String dateStart, String dateEnd,
            String jobTitle, String jobDescription)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        Date endDate = null;

        try
        {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        // Edit information in UrbanParkCalendar first
        theUPCalendar.editJob(theJob, maxVolunteers, startDate,
                endDate, jobTitle, jobDescription);

        // Edit job
        theJob.setStartDate(startDate);
        theJob.setEndDate(endDate);
        theJob.setJobTitle(jobTitle);
        theJob.setJobDescription(jobDescription);
        theJob.setMaxVolunteers(maxVolunteers);
    }
}
