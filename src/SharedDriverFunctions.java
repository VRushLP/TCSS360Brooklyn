import java.util.ArrayList;
import java.util.Scanner;

public abstract class SharedDriverFunctions
{
    abstract public void run(User theUser, Scanner in,
            UrbanParkCalendar UPCalendar);

    public void viewJob(Job theJob)
    {
        System.out.println(theJob.toString());
    }

    public void viewAllJobs(UrbanParkCalendar theCalendar)
    {
        ArrayList<Job> allJobs = (ArrayList<Job>) theCalendar.getJobList();

        for (int i = 0; i < allJobs.size(); i++)
        {
            System.out.println(i + " " + allJobs.get(i));
        }
    }

}
