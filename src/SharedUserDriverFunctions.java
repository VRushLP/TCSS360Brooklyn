
import java.util.ArrayList;
import java.util.Scanner;

public abstract class SharedUserDriverFunctions
{
    public void viewJob(Job theJob)
    {
        System.out.println(theJob.toString());
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
