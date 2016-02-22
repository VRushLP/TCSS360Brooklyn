package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Park implements Serializable
{
    private String parkName;
    private String parkManagerName;
    private String parkManagerEmail;
    private Collection<Job> parksJobList;

    public Park(String theParkName, ParkManager theParkManager)
    {
        parkName = theParkName;
        parkManagerName = theParkManager.getFirstName() + " "
                + theParkManager.getLastName();
        parkManagerEmail = theParkManager.getEmail();
        parksJobList = new ArrayList<Job>();
    }

    public String getParkName()
    {
        return parkName;
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
     * @return True if the Job was removed successfully, false otherwise.
     */
    public boolean removeJob(Job theJob)
    {
        return parksJobList.remove(theJob);
    }

    @Override
    public String toString()
    {
        StringBuilder parkDetails = new StringBuilder();
        parkDetails.append("Park: ");
        parkDetails.append(getParkName());

        return parkDetails.toString();
    }

    public String getParkManagerEmail()
    {
        return parkManagerEmail;
    }

    public void setParkManagerEmail(String parkManagerEmail)
    {
        this.parkManagerEmail = parkManagerEmail;
    }

    public String getParkManagerName()
    {
        return parkManagerName;
    }

    public void setParkManagerName(String parkManagerName)
    {
        this.parkManagerName = parkManagerName;
    }

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
}
