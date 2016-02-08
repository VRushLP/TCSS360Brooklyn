import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Park implements Serializable
{

    String parkName;
    ParkManager parkManager;
    Collection<Job> parksJobList;
    
    /**
     * Constructor for creating a park.
     * @param theParkName
     * @param theParkManager
     */
    public Park(String theParkName,ParkManager theParkManager) {
        parkName = theParkName;
        parkManager = theParkManager;
        parksJobList = new ArrayList<Job>();
    }
    /**
     * A getter method for the park name.
     * 
     */
    public String getParkName()
    {
        return this.parkName;
    }
    
    /**
     * A getter method for the park manager field.
     * 
     */
    public ParkManager getParkManager()
    {
        return this.parkManager;
    }
    
    /**
     * A getter method for the job list field.
     * 
     * @param theParkName
     */
    public Collection<Job> getJobList() {
        return parksJobList;
        
    }
    
//    /**
//     * A setter method for the park name field.
//     * 
//     * @param theParkName
//     */
//    public void setParkName(String theParkName)
//    {
//        parkName = theParkName;
//    }
//    
//    /**
//     * A setter method for the park manager field.
//     * 
//     * @param theParkManager
//     */
//    public void setParkManager(ParkManager theParkManager)
//    {
//        parkManager = theParkManager;
//    }
    
    @Override
    public String toString() {
        StringBuilder parkDetails = new StringBuilder();
        parkDetails.append("ParkName: ");
        parkDetails.append(getParkName());
        parkDetails.append("\nParkManager: ");
        parkDetails.append(getParkManager());
        parkDetails.append("\nList of Jobs:");
        
        // Iterate through the job list and append each job to StringBuilder
        for (Job j : getJobList()) {
            parkDetails.append(j.toString());
            parkDetails.append("\n");
        }        
        return parkDetails.toString();
    }
}
