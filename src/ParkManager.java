import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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

    public boolean addParkToJurisdiction(Park thePark)
    {
        return myParks.add(thePark);
    }

    public void createJob(UrbanParkCalendar uPCalendar, Park prk, 
            int maxVolunteers, String dateStart, String dateEnd, 
            String jobTitle, String jobDescription)
        {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        Date endDate = null;
        //Park park = null;
        
        try 
        {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
        } 
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        // TEMPORARY, USE prk parameter instead
        //park = new Park("Gasworks park", this);
        
        // Add job to the particular managed park by the current park manager
        Job job = new Job(prk, maxVolunteers, startDate, endDate, jobTitle, jobDescription);
//        prk.addJob(new Job(prk, maxVolunteers, startDate, endDate, jobTitle, jobDescription));
        
        // Update calendar
        uPCalendar.addJob(job);  
        }

    public void deleteJob(UrbanParkCalendar uPCalendar, Job j,
            Park park)
    {
        park.removeJob(j);
        // Update calendar
        uPCalendar.removeJob(j);

    }
    
    
    public void editJob(UrbanParkCalendar uPCalendar, Job j,
            Park park, int maxVolunteers, String dateStart, String dateEnd, 
            String jobTitle, String jobDescription)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        Date endDate = null;
        //Park park = null;
        
        try 
        {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
        } 
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Edit information in UrbanParkCalendar first
        uPCalendar.editJob(j, park, maxVolunteers, startDate,
                           endDate, jobTitle, jobDescription);
        
        // Edit job
        j.setStartDate(startDate);
        j.setEndDate(endDate);
        j.setJobTitle(jobTitle);
        j.setJobDescription(jobDescription);
        j.setMaxVolunteers(maxVolunteers);
        
        ArrayList<Job> jobs = new ArrayList<Job>(uPCalendar.getJobList());
//        for (int i = 0; i < jobs)

    }
}
