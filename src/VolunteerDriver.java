import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Driver for Volunteer class
 * 
 * @author Lachezar, Bethany
 */
public class VolunteerDriver extends SharedUserDriverFunctions
{

    static String input;
    static int choice;
    static String[] parsedInput;

    // Data Structure to store everything in
    private static Volunteer myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Scanner myInput;

    public static void run(Volunteer theCurrentUser, Scanner in,
            UrbanParkCalendar UPCalendar)
    {
        myUPCalendar = UPCalendar;
        myUser = theCurrentUser;
        myInput = in;

        System.out.println("Welcome " + myUser.getEmail());

        while (choice != 3)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. View a summary of upcoming jobs");
            System.out.println("2. View jobs I am signed up for");
            System.out.println("3. Exit");

            input = myInput.nextLine();
            parsedInput = input.split(" ");

            try
            {
                choice = Integer.parseInt(
                        parsedInput[0].substring(0, parsedInput[0].length()));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }

            switch (choice)
            {
                case 1:
                    viewJobs();
                    break;
                case 2:
                    viewSignedUpJobs();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out
                            .println("Please enter one of the number options");
            }
        }
    }

    /**
     * Display all the jobs the user has signed up for.
     */
    public static void viewSignedUpJobs()
    {
        if (myUser.getVolunteeredForJobs().isEmpty())
        {
            System.out.println("Sorry, you have not volunteered for a job!\n");
        }
        else
        {
            for (Job job : myUser.getVolunteeredForJobs())
            {
                System.out.println(job.toString());
            }
        }
    }

    /**
     * Allows the current user to view all jobs.
     */
    public static void viewJobs()
    {
        ArrayList<Job> jobs = new ArrayList<Job>(myUPCalendar.getJobList());

        Date today = new Date();
        while (!input.equalsIgnoreCase("b"))
        { // while user wants to view jobs
//            int i = 1;
//            for (Job job : jobs)
//            {
//                if (job.getStartDate().after(today))
//                    ; // only show upcoming days
//                System.out.println(i++ + ") " + job.toString());
//            }
            
            viewAllUpcomingJobs(myUPCalendar);
            
            System.out.println(
                    "Enter b to go back, or enter job number to view details & sign up");
            input = myInput.nextLine();
            if (!input.equalsIgnoreCase("b"))
            { // user wants to view a jobs details
                viewJobDetails(jobs.get(Integer.parseInt(input) - 1));
            }
        }
    }

    /**
     * Shows the details of a job. Not fully working. At this point, it outputs
     * the first occurrence of a job in the job file.
     */
    public static void viewJobDetails(Job theJob)
    {
        // allow user to see details of the job and ask to volunteer for a job
        System.out.println(theJob.toString());

        System.out.println("Would you like to volunteer? \n"
                + "Enter Y for yes, or any other key to go back to summary of jobs");
        input = myInput.nextLine();
        if (input.equalsIgnoreCase("Y"))
        {
            System.out.println("Checking if you can Volunteer.");
            volunteer(theJob);
        }
    }

    /**
     * Lets the user sign up for the job.
     */
    public static void volunteer(Job theJob)
    {    
        try {
            myUser.canVolunteer(theJob);
            System.out.println("Congratulations! You have volunteered!");
        } catch (Exception e) {
            System.out.println("Sorry you may not volunteer since: " + e.getMessage());
        }

        System.out.println("Enter b go back to main menu");
        input = myInput.nextLine();
    }

}
