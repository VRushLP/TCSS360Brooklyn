package driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import model.Job;
import model.Park;
import model.ParkManager;
import model.UrbanParkCalendar;
import model.Volunteer;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
import exception.JobTimeTravelException;
import exception.JobToTheFutureException;
import exception.JobToThePastException;
import exception.JobTooLongException;

public class ParkManagerDriver extends SharedUserDriverFunctions
{
    private static int choice;

    // Data Structure to store everything in
    private static ParkManager myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Scanner myInput;

    /**
     * @param in
     *            Scanner from calling method.
     * @param UPCalendar
     *            The global Urban Parks Calendar
     * @author Lachezar, Robert
     */
    public static void run(ParkManager theUser, Scanner in,
            UrbanParkCalendar UPCalendar)
    {
        myUPCalendar = UPCalendar;
        myUser = theUser;
        myInput = in;

        System.out.println("Welcome " + theUser.getEmail());

        choice = 0;

        while (choice != 6)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. Submit a new job");
            System.out.println("2. Delete a job");
            System.out.println("3. Edit the details of a job.");
            System.out.println("4. View summary of upcoming jobs in my parks.");
            System.out
                    .println("5. View the volunteers for a job in the parks that I manage");
            System.out.println("6. Exit.");

            choice = myInput.nextInt();
            myInput.nextLine();

            switch (choice)
            {
                case 1:
                    submitNewJob();
                    break;
                case 2:
                    deleteJob();
                    break;
                case 3:
                    editJob();
                    break;
                case 4:
                    viewJobsInParks();
                    break;
                case 5:
                    viewVolunteers();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out
                            .println("Please enter one of the numbered options");
            }
        }
        myInput.close();
    }

    /**
     * @param myUser
     * @param myUPCalendar
     * @author Lachezar
     */
    public static void submitNewJob()
    {
        String jobTitle;
        String jobDescription;
        String startDate;
        String endDate;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        int maxVolunteers;
        int theChoice;

        Park park = null;
        // Business Rule #1: ensure that the total number of pending jobs
        // is not currently 30
        if (myUPCalendar.getJobList().size() == UrbanParkCalendar.MAX_JOBS)
        {
            System.out.println("Error: A job cannot be added."
                    + " The maximum number of pending jobs has been reached ("
                    + UrbanParkCalendar.MAX_JOBS + ").");
        }
        else
        {
            System.out
                    .println("Please select one of the parks you manage to add a job for that park");

            ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

            for (int i = 0; i < parks.size(); i++)
            {
                System.out.println((i + 1) + ") " + parks.get(i));
            }

            System.out.print("Enter park number:");

            theChoice = myInput.nextInt();
            myInput.nextLine();
            park = parks.get(theChoice - 1);

            System.out.print("Please enter job title: ");
            jobTitle = myInput.nextLine();
            // System.out.println();
            System.out.print("Job description: ");
            jobDescription = myInput.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            startDate = myInput.nextLine();

            System.out.print("End Date (MM/DD/YYYY): ");
            endDate = myInput.nextLine();

            System.out.print("Maximum number of volunteers (Up to 30): ");
            maxVolunteers = myInput.nextInt();
            myInput.nextLine();

            if (maxVolunteers > 30)
            {
                System.out
                        .println("A job is allowed a maximum of 30 volunteers.");
                maxVolunteers = 30;
            }

            Job job = null;
            try
            {
                job = new Job(park, maxVolunteers, format.parse(startDate),
                        format.parse(endDate), jobTitle, jobDescription);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            // Update calendar first

            try
            {
                myUPCalendar.addJob(job);
            }
            catch (CalendarWeekFullException | CalendarFullException
                    | JobTooLongException | JobTimeTravelException
                    | JobToThePastException | JobToTheFutureException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param myUser
     * @param myUPCalendar
     * @author Lachezar
     */
    public static void deleteJob()
    {
        int theChoice;
        Park park = null;

        // Desired job to delete
        Job theJob = null;

        System.out
                .println("Please select one of the parks you manage to delete a job from that park");

        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
        }

        System.out.print("Enter park number:");

        theChoice = myInput.nextInt();
        myInput.nextLine();

        // Get desired park
        park = parks.get(theChoice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to delete.");
        }
        else
        {
            System.out
                    .println("Please enter the number of the job you would like to delete");
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
            System.out.print("Enter job number:");

            theChoice = myInput.nextInt();
            myInput.nextLine();

            // Get desired job to delete
            theJob = jobs.get(theChoice - 1);

            // Delete job
            // myUser.deleteJob(myUPCalendar, theJob, park);
        }
    }

    /**
     * 
     * @param myUser
     * @param myUPCalendar
     * @author Lachezar
     */
    public static void editJob()
    {
        final String jobTitle;
        final String jobDescription;
        String startDate;
        String endDate;
        int maxVolunteers;
        Park park = null;

        int theChoice;

        // Desired job to edit
        Job jobToEdit = null;

        System.out.println("Edit a Job");

        System.out
                .println("Please select one of the parks you manage to edit a job from that park");

        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
        }

        System.out.print("Enter park number:");

        theChoice = myInput.nextInt();
        myInput.nextLine();

        // Get desired park
        park = parks.get(theChoice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to edit.");
            System.out.println();
        }
        else
        {
            System.out
                    .println("Please enter the number of the job you would like to edit");
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
            System.out.print("Enter job number:");

            theChoice = myInput.nextInt();
            myInput.nextLine();

            // Get desired job to delete
            jobToEdit = jobs.get(theChoice - 1);

            System.out.print("Please enter job title: ");
            jobTitle = myInput.nextLine();
            // System.out.println();
            System.out.print("Job description: ");
            jobDescription = myInput.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            startDate = myInput.nextLine();

            System.out.print("End Date (MM/DD/YYYY): ");
            endDate = myInput.nextLine();

            System.out
                    .println("Please Enter the maximum number of volunteers:");

            maxVolunteers = myInput.nextInt();
            myInput.nextLine();

            if (maxVolunteers > 30)
            {
                System.out
                        .println("A job is allowed a maximum of 30 volunteers.");
                maxVolunteers = 30;
            }

            // myUser.editJob(myUPCalendar, jobToEdit, park, maxVolunteers,
            // startDate, endDate, jobTitle, jobDescription);
        }
    }

    /**
     * 
     * @param myUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void viewJobsInParks()
    {
        Park park = null;
        int theChoice;

        System.out.println("Please select one of the parks you manage to view "
                + "a summary of all upcoming jobs in that park");

        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
        }
        System.out.print("Enter park number:");

        theChoice = myInput.nextInt();
        myInput.nextLine();

        // Get desired park
        park = parks.get(theChoice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to view.");
        }
        else
        {
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
        }
    }

    /**
     * 
     * @param myUser
     * @param uPCalendar
     * @author Lachezar
     */
    private static void viewVolunteers()
    {
        Park park = null;
        Job job = null;
        int theChoice;

        System.out
                .println("Please select one of the parks you manage to view the jobs");

        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
            System.out.println();
        }
        System.out.print("Enter park number:");

        theChoice = myInput.nextInt();
        myInput.nextLine();
        // Get desired park
        park = parks.get(theChoice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to view.");
            System.out.println();
        }
        else
        {
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
                System.out.println();
            }
            System.out.print("Enter job number:");

            theChoice = myInput.nextInt();
            myInput.nextLine();

            // Get desired job
            job = jobs.get(theChoice - 1);

            ArrayList<Volunteer> volunteers = new ArrayList<Volunteer>(
                    job.getVolunteers());

            // Print volunteer information from collection of volunteers for
            // selected job
            for (int i = 0; i < volunteers.size(); i++)
            {
                System.out.println((i + 1) + ") " + volunteers.get(i));
            }
        }
    }

    public void displayLogin()
    {
        System.out.println("Welcome Park Manager" + myUser.getEmail() + "!");
    }
}
