package driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.Job;
import model.Park;
import model.UrbanParkCalendar;
import model.Volunteer;

public abstract class SharedUserDriverFunctions
{
    /**
     * Prints a summary of a Job. A summary consists of the following: <br>
     * The Job's title <br>
     * The name of the park that the Job is in <br>
     * The Job's start and end dates.
     * 
     * @param theJob
     */
    public static void printJobSummary(Job theJob)
    {
        StringBuilder jobSummary = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        jobSummary.append(theJob.getJobTitle());
        jobSummary.append(' ');
        jobSummary.append(theJob.getParkName());
        jobSummary.append(' ');
        jobSummary.append(dateFormat.format(theJob.getStartDate()));
        jobSummary.append('-');
        jobSummary.append(dateFormat.format(theJob.getEndDate()));

        System.out.println(jobSummary);
    }

    /**
     * Prints a summary of a Job. Also appends the passed index to the front of
     * the job summary.<br>
     * A summary consists of the following: <br>
     * The Job's title <br>
     * The name of the park that the Job is in <br>
     * The Job's start and end dates.
     * 
     * @param theJob
     */
    public static void printJobSummary(int index, Job theJob)
    {
        StringBuilder jobSummary = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        jobSummary.append(index + ". ");
        jobSummary.append(theJob.getJobTitle());
        jobSummary.append(' ');
        jobSummary.append(theJob.getParkName());
        jobSummary.append(' ');
        jobSummary.append(dateFormat.format(theJob.getStartDate()));
        jobSummary.append('-');
        jobSummary.append(dateFormat.format(theJob.getEndDate()));

        System.out.println(jobSummary);
    }

    /**
     * Prints the summary of a Volunteer. This summary consists of:<br>
     * The volunteers name, listed as "LastName, FirstName"<br>
     * The volunteers email, enclosed in parenthesis.
     * 
     * @param theVolunteer
     */
    public static void printVolunteerSummary(Volunteer theVolunteer)
    {
        StringBuilder vSummary = new StringBuilder();

        vSummary.append(theVolunteer.getLastName());
        vSummary.append(", ");
        vSummary.append(theVolunteer.getFirstName());
        vSummary.append(' ');
        vSummary.append('(');
        vSummary.append(theVolunteer.getEmail());
        vSummary.append(')');

        System.out.println(vSummary);
    }

    /**
     * Prints the summary of a Volunteer. Also appends the index param to the
     * front. This summary consists of:<br>
     * The volunteers name, listed as "LastName, FirstName"<br>
     * The volunteers email, enclosed in parenthesis.
     * 
     * @param theVolunteer
     */
    public static void printVolunteerSummary(int index, Volunteer theVolunteer)
    {
        StringBuilder vSummary = new StringBuilder();

        vSummary.append(index + ". ");
        vSummary.append(theVolunteer.getLastName());
        vSummary.append(", ");
        vSummary.append(theVolunteer.getFirstName());
        vSummary.append(' ');
        vSummary.append('(');
        vSummary.append(theVolunteer.getEmail());
        vSummary.append(')');

        System.out.println(vSummary);
    }

    /**
     * Prints all upcoming jobs in an UrbanParkCalendar as a summary.<br>
     * Semantically equivalent to iterating over UrbanParkCalendar.getJobList()
     * and calling viewJobSummary on the current job per iteration.
     * 
     * @param theCalendar
     */
    public static void printAllUpcomingJobs(UrbanParkCalendar theCalendar)
    {
        ArrayList<Job> allJobs = new ArrayList<Job>(theCalendar.getJobList());

        if (allJobs.isEmpty())
        {
            System.out.println("Sorry, there are no upcoming jobs");
        }
        else
        {
            for (int i = 0; i < allJobs.size(); i++)
            {
                printJobSummary(i + 1, allJobs.get(i));
            }
        }
    }

    /**
     * Gets an integer input from a console. This method is guaranteed to return
     * something of type int.
     * 
     * @param in
     *            The Scanner object that is read from until an int is returned.
     * @param max
     *            The maximum acceptable int.
     * @return
     */
    public static int getIntegerInput(Scanner in, int max)
    {
        return getIntegerInput(in, 1, max);
    }

    public static int getIntegerInput(Scanner in, int min, int max)
    {
        String parseErrorMsg = "Please enter an integer between " + min
                + " and " + max;
        int toReturn = 0;
        boolean ableToReturn = false;

        do
        {
            String temp = in.nextLine();
            try
            {
                toReturn = Integer.parseInt(temp.trim());

                if (toReturn >= min && toReturn <= max)
                {
                    ableToReturn = true;
                }
                else
                {
                    System.out.println(parseErrorMsg);
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println(parseErrorMsg);
            }
        } while (!ableToReturn);

        return toReturn;
    }

    /**
     * Gets a date input from the console. This method will continually prompt
     * until it receives a well formatted date.
     * 
     * @param in
     *            The Scanner to get input from.
     * @return
     */
    public static Date getDateInput(Scanner in)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String parseErrorMsg = "Please enter a date (MM/DD/YYYY)";
        Date toReturn = null;
        boolean ableToReturn = false;

        do
        {
            String temp = in.nextLine();
            try
            {
                toReturn = format.parse(temp);
                ableToReturn = true;
            }
            catch (ParseException e)
            {
                System.out.println(parseErrorMsg);
            }
        } while (!ableToReturn);

        return toReturn;
    }

    /**
     * Prints the contents of a park ArrayList to the console.
     * 
     * @param theParks
     */
    public static void printParks(ArrayList<Park> theParks)
    {
        System.out.println();
        for (int i = 0; i < theParks.size(); i++)
        {
            System.out.println((i + 1) + ". " + theParks.get(i));
        }
        System.out.println();
    }

    /**
     * Prints the contents of a volunteer ArrayList to the console.
     * 
     * @param volunteers
     */
    public static void printVolunteers(ArrayList<Volunteer> volunteers)
    {
        System.out.println();
        if (volunteers.isEmpty())
        {
            System.out.println("There are no volunteers to view.");
        }
        for (int i = 0; i < volunteers.size(); i++)
        {
            printVolunteerSummary(i + 1, volunteers.get(i));
        }
        System.out.println();
    }

    /**
     * Prints the contents of a Job ArrayList to the console.
     * 
     * @param jobs
     */
    public static void printJobs(ArrayList<Job> jobs)
    {
        System.out.println();
        if (jobs.isEmpty())
        {
            System.out.println("There are no jobs to view.");
        }
        else
        {
            for (int i = 0; i < jobs.size(); i++)
            {
                printJobSummary(i + 1, jobs.get(i));
            }
        }
    }
}
