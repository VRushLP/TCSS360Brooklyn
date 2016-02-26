package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author Robert, Bethany
 * @version 02/09/2016
 */
public class Job implements Serializable, Comparable<Job>
{
    private static final long serialVersionUID = 9061001735234100580L;
    public static final int MAX_VOLUNTEER_NUM = 30;
    public static final int MAX_JOB_LENGTH = 2; // 2 days

    private String parkName;

    private Date startDate;
    private Date endDate;

    private String jobTitle;
    private String jobDescription;

    // work categories

    private Collection<Volunteer> lightVolunteers;
    private Collection<Volunteer> mediumVolunteers;
    private Collection<Volunteer> difficultVolunteers;

    private int maxLightVolunteers;
    private int maxDifficultVolunteers;
    private int maxMediumVolunteers;

    /**
     * Copies a Job's information so that it can be edited safely. This
     * constructor <i>does not</i> copy Volunteers, since jobs should not be
     * edited if they already have someone Volunteered for them.
     * 
     * @param toCopy
     */
    public Job(Job toCopy)
    {
        lightVolunteers = new ArrayList<Volunteer>();
        mediumVolunteers = new ArrayList<Volunteer>();
        difficultVolunteers = new ArrayList<Volunteer>();
        maxLightVolunteers = toCopy.maxLightVolunteers;
        maxMediumVolunteers = toCopy.maxMediumVolunteers;
        maxDifficultVolunteers = toCopy.maxDifficultVolunteers;

        parkName = toCopy.parkName;
        startDate = toCopy.startDate;
        endDate = toCopy.endDate;
        jobTitle = toCopy.jobTitle;
        jobDescription = toCopy.jobDescription;

    }

    public Job(Park thePark, int theMaxLight, int theMaxMed,
            int theMaxDifficult, Date theStartDate, Date theEndDate,
            String theJobTitle, String theJobDescription)
    {
        lightVolunteers = new ArrayList<Volunteer>();
        mediumVolunteers = new ArrayList<Volunteer>();
        difficultVolunteers = new ArrayList<Volunteer>();
        maxLightVolunteers = theMaxLight;
        maxMediumVolunteers = theMaxMed;
        maxDifficultVolunteers = theMaxDifficult;

        parkName = thePark.getParkName();
        startDate = theStartDate;
        endDate = theEndDate;
        jobTitle = theJobTitle;
        jobDescription = theJobDescription;
    }

    // work categories

    /**
     * Returns a list of all volunteers from all work categories.
     */
    public Collection<Volunteer> getVolunteers()
    {
        Collection<Volunteer> allVolunteers = new ArrayList<Volunteer>(
                lightVolunteers);
        allVolunteers.addAll(mediumVolunteers);
        allVolunteers.addAll(difficultVolunteers);
        return Collections.unmodifiableCollection(allVolunteers);
    }

    public boolean addLightVolunteer(Volunteer theVolunteer)
    {
        if (lightVolunteers.size() < maxLightVolunteers)
        {
            return lightVolunteers.add(theVolunteer);
        }
        return false;
    }

    public boolean addDifficultVolunteer(Volunteer theVolunteer)
    {
        if (difficultVolunteers.size() < maxDifficultVolunteers)
        {
            return difficultVolunteers.add(theVolunteer);
        }
        return false;
    }

    public boolean addMediumVolunteer(Volunteer theVolunteer)
    {
        if (mediumVolunteers.size() < maxMediumVolunteers)
        {
            return mediumVolunteers.add(theVolunteer);
        }
        return false;
    }
    
    public boolean hasMaxVolunteers(WorkLoad theWorkLoad) {
        switch (theWorkLoad) {
            case LIGHT:
                return maxLightVolunteers == lightVolunteers.size();
            case MEDIUM:
                return maxMediumVolunteers == mediumVolunteers.size();
            case DIFFICULT:
                return maxDifficultVolunteers == difficultVolunteers.size();
            default:
                return false;
        }
    }

    public int getMaxLightVolunteers()
    {
        return maxLightVolunteers;
    }

    public int getMaxMediumVolunteers()
    {
        return maxMediumVolunteers;
    }

    public int getMaxDifficultVolunteers()
    {
        return maxDifficultVolunteers;
    }

    public void setMaxLightVolunteers(int theMax)
    {
        maxLightVolunteers = theMax;
    }

    public void setMaxMediumVolunteers(int theMax)
    {
        maxMediumVolunteers = theMax;
    }

    public void setMaxDifficultVolunteers(int theMax)
    {
        maxDifficultVolunteers = theMax;
    }
    
    public boolean removeVolunteer(Volunteer theVolunteer) {
        if (lightVolunteers.contains(theVolunteer)) {
            return lightVolunteers.remove(theVolunteer);
        } else if (mediumVolunteers.contains(theVolunteer)) {
            return mediumVolunteers.remove(theVolunteer);
        } else if (difficultVolunteers.contains(theVolunteer)) {
            return difficultVolunteers.remove(theVolunteer);
        }
        return false;
    }

    // end work categories

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

        jobDetails.append("\nLight Volunteers Needed: ");
        jobDetails.append(lightVolunteers.size());
        jobDetails.append("/");
        jobDetails.append(maxLightVolunteers);

        jobDetails.append("\nMedium Volunteers Needed: ");
        jobDetails.append(mediumVolunteers.size());
        jobDetails.append("/");
        jobDetails.append(maxMediumVolunteers);

        jobDetails.append("\nDifficult Volunteers Needed: ");
        jobDetails.append(difficultVolunteers.size());
        jobDetails.append("/");
        jobDetails.append(maxDifficultVolunteers);

        jobDetails.append("\nStart Date: ");
        jobDetails.append(startDate);
        jobDetails.append(" End Date: ");
        jobDetails.append(endDate);
        jobDetails.append("\n");
        return jobDetails.toString();
    }
<<<<<<< HEAD
   
=======

    public boolean addVolunteer(Volunteer theVolunteer)
    {
        if (volunteers.size() < maxVolunteers)
        {
            return volunteers.add(theVolunteer);
        }
        return false;
    }

    public boolean removeVolunteer(Volunteer theVolunteer)
    {
        return volunteers.remove(theVolunteer);
    }

    public int getMaxVolunteers()
    {
        return maxVolunteers;
    }

    public void setMaxVolunteers(int maxVolunteers)
    {
        this.maxVolunteers = maxVolunteers;
    }

>>>>>>> master
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

    public String getParkName()
    {
        return parkName;
    }

    // TODO Change this when WorkCategories are in
    public boolean canEdit()
    {
        return true;
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

    /**
     * Return true if the job already happened.
     */
    public boolean isPastJob()
    {
        return startDate.before(new Date());
    }

    /**
     * Return true if a job overlaps with another jobs start date.
     */
    public boolean startDayOverlaps(Job theOtherJob)
    {
        return startDate.before(theOtherJob.getStartDate())
                && endDate.after(theOtherJob.getStartDate());
    }

    /**
     * Return true if a job overlaps with another jobs end date.
     */
    public boolean endDayOverlaps(Job theOtherJob)
    {
        return startDate.before(theOtherJob.getEndDate())
                && endDate.after(theOtherJob.getEndDate());
    }

    /**
     * Return true if two jobs share any start or end dates.
     */
    public boolean shareDates(Job theOtherJob)
    {
        return startDate.equals(theOtherJob.getStartDate())
                || startDate.equals(theOtherJob.getEndDate())
                || endDate.equals(theOtherJob.getStartDate())
                || endDate.equals(theOtherJob.getEndDate());
    }
}
