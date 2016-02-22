package driver;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import model.Job;
import model.UrbanParkCalendar;

public abstract class SharedUserDriverFunctions
{
    public static void viewJob(Job theJob)
    {
        StringBuilder jobSummary = new StringBuilder();
        SimpleDateFormat d = new SimpleDateFormat();

        jobSummary.append(theJob.getJobTitle());
        jobSummary.append(' ');
        jobSummary.append(theJob.getAssociatedPark());
        jobSummary.append(' ');
        jobSummary.append(d.format(theJob.getStartDate()));
        jobSummary.append('-');
        jobSummary.append(d.format(theJob.getEndDate()));

        System.out.println(jobSummary);
    }

    public static void viewAllUpcomingJobs(UrbanParkCalendar theCalendar)
    {
        ArrayList<Job> allJobs = (ArrayList<Job>) theCalendar.getJobList();
        
        if (theCalendar.getJobList().isEmpty()) System.out.println("Sorry, there are no upcoming jobs");   

        for (int i = 0; i < allJobs.size(); i++)
        {
            System.out.println(i + " " + allJobs.get(i));
        }
    }
}
