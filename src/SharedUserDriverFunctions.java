

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class SharedUserDriverFunctions
{
    public void viewJob(Job theJob)
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

    public void viewAllUpcomingJobs(UrbanParkCalendar theCalendar)
    {
        ArrayList<Job> allJobs = (ArrayList<Job>) theCalendar.getJobList();

        for (int i = 0; i < allJobs.size(); i++)
        {
            System.out.println(i + " " + allJobs.get(i));
        }
    }
}
