import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Park implements Serializable
{
    private static final long serialVersionUID = -6204556836361813053L;
    private String parkName;
    private ParkManager parkManager;
    private Collection<Job> parksJobList;

    public Park(String theParkName, ParkManager theParkManager)
    {
        parkName = theParkName;
        parkManager = theParkManager;
        parksJobList = new ArrayList<Job>();
    }

    public String getParkName()
    {
        return parkName;
    }

    public ParkManager getParkManager()
    {
        return parkManager;
    }

    public Collection<Job> getJobList()
    {
        return Collections.unmodifiableCollection(parksJobList);
    }

    public void addJob(Job theJob)
    {
        parksJobList.add(theJob);
    }

    public boolean checkForJob(Job theJob)
    {
        return parksJobList.contains(theJob);
    }

    /**
     * Removes the job from the parks collection of jobs.
     * 
     * @return true if the Job was removed successfully, false otherwise.
     */
    public boolean removeJob(Job theJob)
    {
        return parksJobList.remove(theJob);
    }

    @Override
    public String toString()
    {
        // TODO This probably needs to be redone.
        StringBuilder parkDetails = new StringBuilder();
        parkDetails.append("ParkName: ");
        parkDetails.append(getParkName());
        parkDetails.append("\nParkManager: ");
        parkDetails.append(getParkManager());

        return parkDetails.toString();
    }
}
