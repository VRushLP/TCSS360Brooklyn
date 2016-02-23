package driver;

import java.util.ArrayList;
import java.util.Scanner;

import exception.AlreadyVolunteeredException;
import exception.ConflictingJobCommitmentException;
import exception.JobIsFullException;
import exception.JobToThePastException;
import model.Job;
import model.UrbanParkCalendar;
import model.Volunteer;

/**
 * Driver for Volunteer class
 * 
 * @author Lachezar, Bethany
 */
public class VolunteerDriver extends SharedUserDriverFunctions
{
    private static int MAX_MENU_OPTION = 3;

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

        while (choice != MAX_MENU_OPTION)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. View a summary of upcoming jobs");
            System.out.println("2. View jobs I am signed up for");
            System.out.println("3. Exit");

            choice = getIntegerInput(myInput, MAX_MENU_OPTION);

            switch (choice)
            {
                case 1:
                    // viewAllUpcomingJobs(myUPCalendar);
                    viewUpcomingJobs();
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

    public static void viewUpcomingJobs()
    {
        printAllUpcomingJobs(myUPCalendar);
        ArrayList<Job> allJobs = new ArrayList<Job>(myUPCalendar.getJobList());
        System.out
                .println("Enter a number of job to view more details, or enter "
                        + "B to go back");
        input = myInput.nextLine();
        if (!input.equalsIgnoreCase("B"))
        {
            viewJobDetails(allJobs.get(Integer.parseInt(input)));
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
     * Allows user to view the details of a job.
     */
    public static void viewJobDetails(Job theJob)
    {
        // allow user to see details of the job and ask to volunteer for a job
        System.out.println(theJob.toString());
        System.out.println("Would you like to volunteer? \n"
                + "Enter Y for yes, or any other key to go back to menu");

        input = myInput.nextLine();
        if (input.equalsIgnoreCase("Y"))
        {
            System.out.println("Checking if you can Volunteer.");
            volunteer(theJob);
        }
    }

    /**
     * Allows the user to sign up for a job if the job if they have not already
     * signed up for this job and the job is not full of volunteers.
     */
    public static void volunteer(Job theJob)
    {
        try
        {
            myUser.volunteerForJob(theJob);
            theJob.addVolunteer(myUser);
            System.out.println("Congratulations! You have volunteered");
        }
        catch (AlreadyVolunteeredException e)
        {
            System.out.println("You already volunteered for that Job!");
        }
        catch (ConflictingJobCommitmentException e)
        {
            System.out
                    .println("Sorry, you already have another Job scheduled for the same date.");
        }
        catch (JobIsFullException e)
        {
            System.out.println("Sorry, that Job is full.");
        }
        catch (JobToThePastException e)
        {
            System.out.println("Sorry, that Job has already occured.");
        }

        System.out.println("Enter b go back to main menu");
        input = myInput.nextLine();
    }

    public static void displayLogin()
    {
        System.out.println("Welcome " + myUser.getFirstName() + " "
                + myUser.getLastName() + "!\n" + "Logged in as: "
                + myUser.getEmail() + " (Volunteer)\n");
    }
}
