package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Park implements Serializable
{
    private static final long serialVersionUID = -4808769468460385164L;
    
    private String parkName;
    private String parkManagerName;
    private String parkManagerEmail;
    private Collection<Job> parksJobList;

    /**
     * Creates a new park with the given park name, managed by the given Park
     * Manager.
     */
    public Park(String theParkName, ParkManager theParkManager)
    {
        parkName = theParkName;
        parkManagerName = theParkManager.getFirstName() + " "
                + theParkManager.getLastName();
        parkManagerEmail = theParkManager.getEmail();
        parksJobList = new ArrayList<Job>();
        theParkManager.addParkToManager(this);
    }

    /**
     * Adds a job to the park. Error checking is handled through the Calendar.
     * 
     * @param theJob
     */
    public void addJob(Job theJob)
    {
        if (!parksJobList.contains(theJob))
        {
            parksJobList.add(theJob);
        }
    }

    /**
     * Parks are equal to each other if they have the same name and are managed
     * by the same Park Manager.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Park)
        {
            Park compare = (Park) o;
            // Should be the bare minimum to determine if a Park is equal to
            // another
            return parkName.equals(compare.parkName)
                    && this.parkManagerName
                            .equalsIgnoreCase(compare.parkManagerName)
                    && this.parkManagerEmail
                            .equalsIgnoreCase(compare.parkManagerEmail);
        }
        return false;
    }

    /**
     * @return An unmodifiable version of the parks job Collection
     */
    public Collection<Job> getJobs()
    {
        return Collections.unmodifiableCollection(parksJobList);
    }

    public String getParkManagerEmail()
    {
        return parkManagerEmail;
    }

    public String getParkManagerName()
    {
        return parkManagerName;
    }

    public String getParkName()
    {
        return parkName;
    }

    /**
     * @return True if the park has the passed job in it already, false
     *         otherwise.
     */
    public boolean hasJob(Job theJob)
    {
        return parksJobList.contains(theJob);
    }

    /**
     * Removes the job from the parks collection of jobs.
     * 
     * @return True if the Job was removed successfully, false otherwise.
     */
    public boolean removeJob(Job theJob)
    {
        return parksJobList.remove(theJob);
    }

    public void setParkManagerEmail(String parkManagerEmail)
    {
        this.parkManagerEmail = parkManagerEmail;
    }

    public void setParkManagerName(String parkManagerName)
    {
        this.parkManagerName = parkManagerName;
    }

    @Override
    public String toString()
    {
        StringBuilder parkDetails = new StringBuilder();
        parkDetails.append("Park: ");
        parkDetails.append(getParkName());

        return parkDetails.toString();
    }
}
