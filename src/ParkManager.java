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
    }

    public ParkManager(String theEmail, String theFirstName, String theLastName)
    {
        super(theEmail, theFirstName, theLastName);
    }

    public ParkManager(String theEmail, String theFirstName,
            String theLastName, Park thePark)
    {
        super(theEmail, theFirstName, theLastName);
        this.myParks = new ArrayList<Park>();
        myParks.add(thePark);
    }

    public ParkManager(String theEmail, String theFirstName,
            String theLastName, Collection<Park> theParks)
    {
        super(theEmail, theFirstName, theLastName);
        this.myParks = new ArrayList<Park>(theParks);
    }

    public Collection<Park> getParks()
    {
        return Collections.unmodifiableCollection(myParks);
    }
    
    public void createJob(UrbanParkCalendar uPCalendar , int parkNum, Park prk, 
                          int maxVolunteers, String dateStart, String dateEnd, 
                          String jobTitle, String jobDescription)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        Date endDate = null;
        Park park = null;
        Job job = new Job(park, maxVolunteers, startDate, endDate, jobTitle, jobDescription);
        
        // TEMPORARY, USE prk parameter instead
        park = new Park("Gasworks park", this);
        
        try {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
//      // Currently generates a null pointer exception
//      // since there are no parks managed by PM
//      Collection<Park> parks = getParks();
//      Iterator itr = parks.iterator();
//        
//        
//        // not a good way of checking if the park number corresponds to current
//        // park
//        int i = 0;
//        
//        while (itr.hasNext())
//        {
//          i++;
//          if (i == parkNum)
//          {
//              park = (Park) itr.next();
//          }
//           
//        }
        
        // Add job to the particular managed park by the current park manager
        park.addJob(job);
        
        // Update calendar
        uPCalendar.addJob(job);
       
    }

    public void deleteJob(UrbanParkCalendar uPCalendar, int selectedJob, Park park)
    {
        Job jobToDel = null;
        ArrayList<Job> jobs = (ArrayList<Job>) park.getJobList();
        Iterator itr = jobs.iterator();
        int count = 0; // silly way of determining what job park manager wants to delete
        while (itr.hasNext())
        {
            if (count == selectedJob)
            {
                jobToDel = (Job) itr.next();
            }
            
        }
        
        // Remove job finally
        park.removeJob(jobToDel);
        
    }
}
