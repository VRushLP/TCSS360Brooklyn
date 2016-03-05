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
    private static final int MAIN_MENU_OPTIONS = 3;

    static int choice;

    private static final int VOL_EASY = 1;
    private static final int VOL_MEDIUM = 2;
    private static final int VOL_DIFFICULT = 3;

    private static Volunteer myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Scanner myInput;

    /**
     * Runs the VolunteerDriver from the PrimaryDriver.
     * 
     * @param currentUser
     *            The Volunteer running the program.
     * @param in
     *            The scanner object that input is received from.
     * @param theUPCalendar
     *            The UrbanParkCalendar containing the job and user information.
     */
    public static void run(Volunteer theCurrentUser, Scanner in,
            UrbanParkCalendar UPCalendar)
    {
        myUPCalendar = UPCalendar;
        myUser = theCurrentUser;
        myInput = in;

        while (choice != MAIN_MENU_OPTIONS)
        {
            displayLogin();

            System.out.println("Enter one of the options below:");
            System.out.println("1. View a summary of upcoming jobs");
            System.out.println("2. View jobs I am signed up for");
            System.out.println("3. Exit");

            choice = getIntegerInput(myInput, MAIN_MENU_OPTIONS);

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

    /**
     * Prints a list of all upcoming jobs that are in the originally passed
     * UrbanParkCalendar.
     */
    public static void viewUpcomingJobs()
    {

        ArrayList<Job> upcomingJobs = new ArrayList<Job>(
                myUPCalendar.getJobList());

        if (!upcomingJobs.isEmpty())
        {
            do
            {
                printAllUpcomingJobs(myUPCalendar);
                System.out.println(
                        "Enter the number of a job to view more details, or enter "
                                + "0 to go back to the main menu.");

                choice = getIntegerInput(myInput, 0, upcomingJobs.size());
                if (choice != 0)
                {
                    viewJobDetails(upcomingJobs.get(choice - 1));
                }
            } while (choice != 0);
        }

    }

    /**
     * Display all the jobs the user has signed up for.
     */
    public static void viewSignedUpJobs()
    {
        if (myUser.getVolunteeredForJobs().isEmpty())
        {
            System.out.println("Sorry, you have not volunteered for a job!");
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
        System.out.println("Would you like to volunteer for this job? \n"
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
                    break;
                case VOL_MEDIUM:
                    volunteer(theJob, WorkLoad.MEDIUM);
                    break;
                case VOL_DIFFICULT:
                    volunteer(theJob, WorkLoad.DIFFICULT);
                    break;
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
            System.out.println(
                    "Sorry, there are no slots left at that work load.");
        }
        catch (JobToThePastException e)
        {
            System.out.println("Sorry, that Job has already occured.");
        }
        System.out.println("You may try to volunteer for another job:");
    }

    /**
     * Displays the login information of a user.<br>
     * This includes, first name, last name, email, and what sort of user they
     * are.
     */
    public static void displayLogin()
    {
        System.out.println("\nWelcome " + myUser.getFirstName() + " "
                + myUser.getLastName() + "!\n" + "Logged in as: "
                + myUser.getEmail() + " (Volunteer)\n");
    }
}
