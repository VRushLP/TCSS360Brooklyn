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
import model.WorkLoad;

/**
 * Driver for Volunteer class
 * 
 * @author Lachezar, Bethany, Robert
 */
public class VolunteerDriver extends SharedUserDriverFunctions
{
    private static final int MAX_MENU_OPTION = 3;

    static int choice;

    private static final int VOL_EASY = 1;
    private static final int VOL_MEDIUM = 2;
    private static final int VOL_DIFFICULT = 3;

    private static Volunteer myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Scanner myInput;

    public static void run(Volunteer theCurrentUser, Scanner in,
            UrbanParkCalendar UPCalendar)
    {
        myUPCalendar = UPCalendar;
        myUser = theCurrentUser;
        myInput = in;

        while (choice != MAX_MENU_OPTION)
        {
            displayLogin();

            System.out.println("Enter one of the options below:");
            System.out.println("1. View a summary of upcoming jobs");
            System.out.println("2. View jobs I am signed up for");
            System.out.println("3. Exit");

            choice = getIntegerInput(myInput, MAX_MENU_OPTION);

            switch (choice)
            {
                case 1:
                    viewUpcomingJobs();
                    break;
                case 2:
                    viewSignedUpJobs();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
            }
        }
    }

    public static void viewUpcomingJobs()
    {
        printAllUpcomingJobs(myUPCalendar);
        ArrayList<Job> upcomingJobs = new ArrayList<Job>(
                myUPCalendar.getJobList());
        System.out
                .println("Enter a number of job to view more details, or enter "
                        + "0 to go back to the main menu.");

        choice = getIntegerInput(myInput, upcomingJobs.size());
        if (choice != 0)
        {
            viewJobDetails(upcomingJobs.get(choice - 1));
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
                printJobSummary(job);
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
        System.out.print("Would you like to volunteer for this job? \n"
                + "Enter 1 for yes, or 0 for no and to go back to the main menu: ");

        choice = getIntegerInput(myInput, 0, 1);
        if (choice == 1)
        {
            System.out.println(
                    "Enter 1 to volunteer for light work, 2 for medium work, or 3 for difficult work");
            int difficulty = getIntegerInput(myInput, 3);
            System.out.println("Checking if you can volunteer for that job...");
            switch (difficulty)
            {
                case VOL_EASY:
                    volunteer(theJob, WorkLoad.LIGHT);
                case VOL_MEDIUM:
                    volunteer(theJob, WorkLoad.MEDIUM);
                case VOL_DIFFICULT:
                    volunteer(theJob, WorkLoad.DIFFICULT);
            }
        }
    }

    /**
     * Allows the user to sign up for a job if the job if they have not already
     * signed up for this job and the job is not full of volunteers.
     */
    public static void volunteer(Job theJob, WorkLoad theWorkLoad)
    {
        try
        {
            myUser.volunteerForJob(theJob, theWorkLoad);
            System.out.println("Congratulations! You have volunteered for "
                    + theJob.getJobTitle() + "!");
        }
        catch (AlreadyVolunteeredException e)
        {
            System.out.println("You already volunteered for that Job!");
        }
        catch (ConflictingJobCommitmentException e)
        {
            System.out.println(
                    "Sorry, you already have another Job scheduled for the same date.");
        }
        catch (JobIsFullException e)
        {
            System.out.println("Sorry, that Job is full.");
        }
        catch (JobToThePastException e)
        {
            System.out.println("Sorry, that Job has already occured.");
        }
    }

    /**
     * @author Robert
     */
    public static void displayLogin()
    {
        System.out.println("\nWelcome " + myUser.getFirstName() + " "
                + myUser.getLastName() + "!\n" + "Logged in as: "
                + myUser.getEmail() + " (Park Manager)\n");
    }
}
