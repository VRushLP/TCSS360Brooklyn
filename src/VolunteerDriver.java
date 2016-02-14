import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Driver for Volunteer class
 * 
 * @author Lachezar
 *
 */
public class VolunteerDriver
{
    
    // add back for view summary of upcoming jobs
    // allow user to view details about the job
    
    // volunteer only in upcoming jobs, so that volunteers
    // can not sign up for previous jobs?
    
    static String input;
    static int choice;
    static String[] parsedInput;

    // Input file name.
    private static final String inputPath = "./jobs.txt";

    // Data Structure to store everything in
    private static Volunteer myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Map<String, Job> jobList;
    private static ArrayList<Job> jobList2;
    private static BufferedReader inputFileReader;
    private static Scanner myInput;

    public static void run(Volunteer theCurrentUser, Scanner in, UrbanParkCalendar UPCalendar)
    {
        myUPCalendar = UPCalendar;
        myUser = theCurrentUser;
        myInput = in;
        
        System.out.println("Welcome " + theCurrentUser.getEmail());

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
                // TODO catch NumberFormatExceptions here.
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }

            switch (choice)
            {
                case 1:
//                    viewJobs();
                    viewJobs();
                    break;
                case 2:
                    viewSignedUpJobs(theCurrentUser);
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
    public static void viewSignedUpJobs(Volunteer theCurrentUser)
    {
        if (theCurrentUser.getVolunteeredForJobs().isEmpty()) {
            System.out.println("Sorry, you have not volunteered for a job!\n");
        } else {
            for (Job job: theCurrentUser.getVolunteeredForJobs()) {
                System.out.println(job.toString());
            }
        }

    }
    
    /**
     * Allows the current user to view all jobs.
     */
    public static void viewJobs() {
        ArrayList<Job> jobs = new ArrayList<Job>(myUPCalendar.getJobList()); // cast as arrayList for easy job viewing
        Date today = new Date();
        while (!input.equalsIgnoreCase("b")) { // while user wants to view jobs
            int i = 0;
            for (Job job: jobs) {
                if (job.getStartDate().after(today)); // only show upcoming days
                System.out.println(i++ + ") " + job.toString());
            }
            System.out.println("Enter b to go back, or enter job number to view details & sign up");
            input = myInput.nextLine();
            if (!input.equalsIgnoreCase("B")) { // user wants to view a jobs details
                viewJobDetails(jobs.get(Integer.parseInt(input)));
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
        if (input.equalsIgnoreCase("Y")) {
             volunteer(theJob);
            // make user enter back to go back
            System.out.println("TheCurrent User wants to volunteer");
        }
       
    }
    
    public static void volunteer(Job theJob) {
        boolean canSignUp = true;
        ArrayList<Job> jobs = new ArrayList<Job>(myUser.getVolunteeredForJobs());
//        for (Job i: jobs) {
//            // make sure jobs aren't on same day for business rule
//            if (i.getStartDate().getDay() == theJob.getStartDate().getDay()) {
//                canSignUp = false;
//            }
//        }
        if (myUser.getVolunteeredForJobs().contains(theJob)) {
            canSignUp = false;
        } else if (theJob.getVolunteers().size() >= theJob.getMaxVolunteers()) {
            canSignUp = false;
        }
        
        if (canSignUp == false) {
            System.out.println("Sorry, you are not able to sign up for this job");
        } else {
            theJob.addVolunteer(myUser);
            myUser.volunteerForJob(theJob);
            System.out.println("Congratulations! You have volunteered");
        }
        
        System.out.println("Enter b go back to main menu");
        input = myInput.nextLine();
    }
}
