package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
import exception.DuplicateJobExistsException;
import exception.JobToThePastException;
import exception.JobTimeTravelException;
import exception.JobToTheFutureException;
import exception.JobTooLongException;

public class UrbanParkCalendar implements Serializable
{
    private static final long serialVersionUID = -6937747495177492138L;
    public static final int MAX_JOBS = 30;
    public static final int MAX_WEEKLY_JOBS = 5;
    public static final int MAX_DATE_FROM_TODAY = 90;

    private static Collection<Job> upcomingJobCollection;
    private static Collection<Job> pastJobCollection;
    private static Collection<Volunteer> allVolunteers;
    private static GregorianCalendar calendar;

    public UrbanParkCalendar()
    {
        upcomingJobCollection = new ArrayList<Job>();
        pastJobCollection = new ArrayList<Job>();
        allVolunteers = new ArrayList<>();
        calendar = new GregorianCalendar();
    }

    public void updateCalendar()
    {
        calendar.setTime(new Date());
        ArrayList<Job> jobsToCheck = new ArrayList<>(upcomingJobCollection);

        for (Job j : jobsToCheck)
        {
            if (j.getStartDate().before(calendar.getTime()))
            {
                pastJobCollection.add(j);
                upcomingJobCollection.remove(j);
            }
        }
    }

    public Collection<Volunteer> getAllUsers()
    {
        return Collections.unmodifiableCollection(allVolunteers);
    }

    public boolean addVolunteer(Volunteer theVolunteer)
    {
        if (!allVolunteers.contains(theVolunteer))
        {
            return allVolunteers.add(theVolunteer);
        }
        return false;
    }

    public Collection<Job> getJobList()
    {
        updateCalendar();
        return Collections.unmodifiableCollection(upcomingJobCollection);
    }

    public boolean addJob(Job theJob)
            throws CalendarWeekFullException, CalendarFullException,
            JobTooLongException, JobTimeTravelException, JobToThePastException,
            JobToTheFutureException, DuplicateJobExistsException
    {
        checkJobDate(theJob);
        checkForRoomThatWeek(theJob);
        checkJobCapacity();

        return upcomingJobCollection.add(theJob);
    }

    private void checkJobDate(Job theJob) throws DuplicateJobExistsException,
            JobToThePastException, JobToTheFutureException, JobTooLongException
    {
        // Figure out a way to edit a job's start date and not throw 
        // the DuplicateJobExistsException
//        if (upcomingJobCollection.contains(theJob))
//        {
//            throw new DuplicateJobExistsException();
//        }

        if (theJob.getEndDate().getTime()
                - theJob.getStartDate().getTime() > TimeUnit.DAYS
                        .toMillis(Job.MAX_JOB_LENGTH))
        {
            throw new JobTooLongException();
        }

        if (theJob.getStartDate().before(calendar.getTime()))
        {
            throw new JobToThePastException();
        }

        if (theJob.getStartDate().getTime()
                - calendar.getTime().getTime() > TimeUnit.DAYS
                        .toMillis(MAX_DATE_FROM_TODAY))
        {
            throw new JobToTheFutureException();
        }
    }

    private void checkJobCapacity() throws CalendarFullException
    {
        if (upcomingJobCollection.size() >= MAX_JOBS)
            throw new CalendarFullException();
    }

    private void checkForRoomThatWeek(Job theJob)
            throws CalendarWeekFullException
    {
        ArrayList<Job> check = new ArrayList<>(upcomingJobCollection);

        check.add(theJob);

        Date minDate = new Date(theJob.getStartDate().getTime()
                - TimeUnit.DAYS.toMillis(3) - 1);
        Date maxDate = new Date(theJob.getStartDate().getTime()
                + TimeUnit.DAYS.toMillis(3) + 1);
        // Three days on either side.
        int jobsThatWeek = 0;

        for (Job j : check)
        {
            if (j.getStartDate().after(minDate)
                    && j.getStartDate().before(maxDate) && !j.equals(theJob))
            {
                jobsThatWeek++;
            }
        }
        check.remove(check.indexOf(theJob));

        if (jobsThatWeek >= MAX_WEEKLY_JOBS)
        {
            throw new CalendarWeekFullException();
        }
    }

    public boolean removeJob(Job theJob)
    {
        return upcomingJobCollection.remove(theJob);
    }

    public void editJobTitle(Job jobToEdit, String jobTitle)
    {
        Iterator<Job> itr = upcomingJobCollection.iterator();
        while (itr.hasNext())
        {
            Job j = itr.next();
            if (j.getJobTitle() == jobToEdit.getJobTitle())
            {
                j.setJobTitle(jobTitle);
                break;
            }
            
        }
        
    }
    
    public void editJobDesc(Job jobToEdit, String jobDescription)
    {
        Iterator<Job> itr = upcomingJobCollection.iterator();
        while (itr.hasNext())
        {
            Job j = itr.next();
            if (j.getJobTitle() == jobToEdit.getJobTitle())
            {
                j.setJobDescription(jobDescription);
                break;
            }
            
        }
        
    }
    public boolean editJobStartDate(Job jobToEdit, String startDate)
    {
        boolean result = false;
        Iterator<Job> itr = upcomingJobCollection.iterator();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        
        // Get current start date in case editing fails
        Date currentStartDate = jobToEdit.getStartDate();
        
        while (itr.hasNext())
        {
            Job j = itr.next();
            if (j.getJobTitle() == jobToEdit.getJobTitle())
            {
                try
                {
                    j.setStartDate(format.parse(startDate));
                    checkJobDate(j);
                    result = true;
                    
                }
                catch (ParseException | DuplicateJobExistsException | JobToThePastException 
                                      | JobToTheFutureException | JobTooLongException e)
                {
                    // Restore start date
                    j.setStartDate(currentStartDate);
                    e.printStackTrace();
                }
                
                break;
            }
            
        }
        return result;
        
    }

    public boolean editJobEndDate(Job jobToEdit, String endDate)
    {
        Iterator<Job> itr = upcomingJobCollection.iterator();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        boolean result = false;
        
        // Get current end date in case editing fails
        Date currentEndDate = jobToEdit.getEndDate();
        
        while (itr.hasNext())
        {
            Job j = itr.next();
            if (j.getJobTitle() == jobToEdit.getJobTitle())
            {
                try
                {
                    j.setEndDate(format.parse(endDate));
                    // Validate date
                    checkJobDate(j);
                    result = true;
                }
                catch (ParseException | DuplicateJobExistsException | JobToThePastException 
                                      | JobToTheFutureException | JobTooLongException e)
                {
                    // Restore end date
                    j.setEndDate(currentEndDate);
                    e.printStackTrace();
                }
                break;
            }
            
        }
        return result;
    }

    public void editMaxVol(Job jobToEdit, int maxVolunteers)
    {
        Iterator<Job> itr = upcomingJobCollection.iterator();
        while (itr.hasNext())
        {
            Job j = itr.next();
            if (j.getJobTitle() == jobToEdit.getJobTitle())
            {
                j.setMaxVolunteers(maxVolunteers);
                break;
            }
            
        }
        
    }

}