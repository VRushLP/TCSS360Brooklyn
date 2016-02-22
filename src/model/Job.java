package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author Bethany Eastman
 * @version 02/09/2016
 */
public class Job implements Serializable, Comparable<Job>
{
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 2; // 2 days

    private Collection<Volunteer> volunteers;
    private String parkName;
    private int maxVolunteers;

    private Date startDate;
    private Date endDate;

    private String jobTitle;
    private String jobDescription;

    public Job(Park thePark, int theMaxVolunteers, Date theStartDate,
            Date theEndDate, String theJobTitle, String theJobDescription)
    {
        // volunteer list starts empty
        volunteers = new ArrayList<>();
        parkName = thePark.getParkName();
        maxVolunteers = theMaxVolunteers; // check if max volunteers is 30
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;
    }

    /**
     * Copies a Job's information so that it can be edited safely. This
     * constructor <i>does not</i> copy Volunteers, since jobs should not be
     * edited if they already have someone Volunteered for them.
     * 
     * @param toCopy
     */
    public Job(Job toCopy)
    {
        volunteers = new ArrayList<>();
        parkName = toCopy.parkName;
        maxVolunteers = toCopy.maxVolunteers; // check if max volunteers is 30
        startDate = toCopy.startDate;
        endDate = toCopy.endDate;
        jobTitle = toCopy.jobTitle;
        jobDescription = toCopy.jobDescription;
    }

    @Override
    public String toString()
    {
        StringBuilder jobDetails = new StringBuilder();
        jobDetails.append("Job: ");
        jobDetails.append(jobTitle);
        jobDetails.append("\nDescription: ");
        jobDetails.append(jobDescription);
        jobDetails.append("\nLocation: ");
        jobDetails.append(parkName);
        jobDetails.append("\nVolunteers: ");
        jobDetails.append(volunteers.size());
        jobDetails.append("/");
        jobDetails.append(maxVolunteers);
        jobDetails.append("\nStart Date: ");
        jobDetails.append(startDate);
        jobDetails.append(" End Date: ");
        jobDetails.append(endDate);
        jobDetails.append("\n");
        return jobDetails.toString();
    }

    public boolean addVolunteer(Volunteer theVolunteer)
    {
        if (volunteers.size() < maxVolunteers)
        {
            return volunteers.add(theVolunteer);
        }
        return false;
    }

    public int getMaxVolunteers()
    {
        return maxVolunteers;
    }

    public void setMaxVolunteers(int maxVolunteers)
    {
        this.maxVolunteers = maxVolunteers;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription()
    {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription)
    {
        this.jobDescription = jobDescription;
    }

    public Collection<Volunteer> getVolunteers()
    {
        return Collections.unmodifiableCollection(volunteers);
    }

    public String getAssociatedPark()
    {
        return parkName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Job)
        {
            Job compare = (Job) o;
            // Should be the bare minimum to determine if a Job is unique or
            // not.
            return jobTitle.equalsIgnoreCase(compare.jobTitle)
                    && jobDescription.equalsIgnoreCase(compare.jobDescription)
                    && parkName.equals(compare.parkName)
                    && startDate.equals(compare.startDate);
        }
        return false;
    }

    @Override
    public int compareTo(Job o)
    {
        return (int) (startDate.getTime() - ((Job) o).startDate.getTime());
    }

}
