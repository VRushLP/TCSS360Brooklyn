package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
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
        for (Job j : upcomingJobCollection)
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
        return allVolunteers.add(theVolunteer);
    }

    public Collection<Job> getJobList()
    {
        return Collections.unmodifiableCollection(upcomingJobCollection);
    }

    public boolean addJob(Job theJob) throws CalendarWeekFullException,
            CalendarFullException, JobTooLongException, JobTimeTravelException,
            JobToThePastException, JobToTheFutureException
    {
        checkJobDate(theJob);
        checkForRoomThatWeek(theJob);
        checkJobCapacity();

        return upcomingJobCollection.add(theJob);
    }

    private void checkJobDate(Job theJob) throws JobToThePastException,
            JobToTheFutureException
    {
        if (theJob.getStartDate().before(calendar.getTime()))
            throw new JobToThePastException();
        if (theJob.getStartDate().getTime() - calendar.getTime().getTime() > TimeUnit.DAYS
                .toMillis(MAX_DATE_FROM_TODAY))
            throw new JobToTheFutureException();
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
        int checkAround = check.indexOf(theJob);
        Date minDate = new Date(theJob.getStartDate().getTime()
                - TimeUnit.DAYS.toMillis(2) - 1);
        Date maxDate = new Date(theJob.getStartDate().getTime()
                + TimeUnit.DAYS.toMillis(2) + 1);
        int jobsThatWeek = 0;

        for (Job j : check)
        {
            if (j.getStartDate().after(minDate)
                    && j.getStartDate().before(maxDate) && !j.equals(theJob))
            {
                jobsThatWeek++;
            }
        }
        check.remove(checkAround);

        if (jobsThatWeek >= MAX_WEEKLY_JOBS)
        {
            throw new CalendarWeekFullException();
        }
    }

    public boolean removeJob(Job theJob)
    {
        return upcomingJobCollection.remove(theJob);
    }
}