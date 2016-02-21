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
        choice = 0;
        
        // Display appropriate welcoming message first
        displayLogin();
        
        while (choice != 6)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. Submit a new job");
            System.out.println("2. Delete a job");
            System.out.println("3. Edit the details of a job.");
            System.out.println("4. View summary of upcoming jobs in my parks.");
            System.out.println(
                    "5. View the volunteers for a job in the parks that I manage");
            System.out.println("6. Exit.");

            choice = myInput.nextInt();
            myInput.nextLine();

            switch (choice)
            {
                case 1:
                    submitNewJob();
                    break;
                case 2:
                    deleteJob(myUPCalendar);
                    break;
                case 3:
                    editJob(myUPCalendar);
                    break;
                case 4:
                    viewJobsInParks(myUPCalendar);
                    break;
                case 5:
                    viewVolunteers(myUPCalendar);
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println(
                            "Please enter one of the numbered options");
            }
        }
//        myInput.close();
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
            System.out.println(
                    "Please select one of the parks you manage to add a job for that park");

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
                System.out.println(
                        "A job is allowed a maximum of 30 volunteers.");
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
                // Display message to user to indicate that the operation was successful
                System.out.println("Job was added successfully.\n");
                
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
     * @param theUPCalendar 
     */
    public static void deleteJob(UrbanParkCalendar theUPCalendar)
    {
        int theChoice;
        Park park = null;

        // Desired job to delete
        Job theJob = null;

        System.out.println(
                "Please select one of the parks you manage to delete a job from that park");

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

        ArrayList<Job> jobs = new ArrayList<Job>(theUPCalendar.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to delete.");
        }
        else
        {
            System.out.println(
                    "\nPlease enter the number of the job you would like to delete\n");
            for (int i = 0; i < jobs.size(); i++)
            {
                // Check to see if selected park corresponds to the
                // associated park for the job we are currently on
                if (jobs.get(i).getAssociatedPark() == park.getParkName())
                {
                    System.out.println((i + 1) + ") " + jobs.get(i));
                }    
            }
            System.out.print("Enter job number:");

            theChoice = myInput.nextInt();
            myInput.nextLine();

            // Get desired job to delete
            theJob = jobs.get(theChoice - 1);
            
            // Delete job from Calendar
            theUPCalendar.removeJob(theJob);
            
            // Display message to user to indicate that the operation was successful
            System.out.println("Job was deleted successfully.\n");
        }
    }

    /**
     * 
     * @param myUser
     * @param myUPCalendar
     * @author Lachezar
     * @param theUPCalendar 
     */
    public static void editJob(UrbanParkCalendar theUPCalendar)
    {
        String jobTitle;
        String jobDescription;
        String startDate;
        String endDate;
        int maxVolunteers;
        Park park = null;
        int option = 0;
        int theChoice;

        // Desired job to edit
        Job jobToEdit = null;

        System.out.println("Edit a Job");

        System.out.println(
                "Please select one of the parks you manage to edit a job from that park");

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

        ArrayList<Job> jobs = new ArrayList<Job>(theUPCalendar.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to edit.");
            System.out.println();
        }
        else
        {
            System.out.println(
                    "Please enter the number of the job you would like to edit");
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                // Check to see if selected park corresponds to the
                // associated park for the job we are currently on
                if (jobs.get(i).getAssociatedPark() == park.getParkName())
                {
                    System.out.println((i + 1) + ") " + jobs.get(i));
                }
                
            }
            System.out.print("Enter job number:");

            theChoice = myInput.nextInt();
            myInput.nextLine();

            // Get desired job to delete
            jobToEdit = jobs.get(theChoice - 1);
            
            while (option != 6)
            {
                System.out.println("Enter one of the options below:");
                System.out.println("1. Edit job title");
                System.out.println("2. Edit job description");
                System.out.println("3. Edit start date");
                System.out.println("4. Edit end date");
                System.out.println("5. Edit maximum number of volunteers");
                System.out.println("6. Back");

                option = myInput.nextInt();
                myInput.nextLine();

                switch (option)
                {
                    case 1:
                        System.out.print("Please enter job title: ");
                        jobTitle = myInput.nextLine();
                        theUPCalendar.editJobTitle(jobToEdit, jobTitle);
                        // Display message to user to indicate that the edit was successful
                        System.out.println("Job title was modified successfully.\n");
                        break;
                    case 2:
                        System.out.print("Job Description: ");
                        jobDescription = myInput.nextLine();
                        theUPCalendar.editJobDesc(jobToEdit, jobDescription);
                        // Display message to user to indicate that the edit was successful
                        System.out.println("Job description was modified successfully.\n");
                        break;
                    case 3:
                        System.out.print("Start Date (MM/DD/YYYY): ");
                        startDate = myInput.nextLine();
                        theUPCalendar.editJobStartDate(jobToEdit, startDate);
                        // Display message to user to indicate that the edit was successful
                        System.out.println("Job start date was modified successfully.\n");
                        break;
                    case 4:
                        System.out.print("End Date (MM/DD/YYYY): ");
                        endDate = myInput.nextLine();
                        theUPCalendar.editJobEndDate(jobToEdit, endDate);
                        // Display message to  user to indicate that the edit was successful
                        System.out.println("Job end date was modified successfully.\n");
                        break;
                    case 5:
                        System.out.println("Please Enter the maximum number of volunteers:");
                        maxVolunteers = myInput.nextInt();
                        myInput.nextLine();
                        if (maxVolunteers > 30)
                        {
                            System.out.println("A job is allowed a maximum of 30 volunteers.");
                            maxVolunteers = 30;
                        }
                        theUPCalendar.editMaxVol(jobToEdit, maxVolunteers);
                        // Display message to user to indicate that the edit was successful
                        System.out.println("Maximum number of volunteers for selected job "
                                         + "was changed successfully.\n");
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println(
                                "Please enter one of the numbered options");
                }
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
     * @param myUPCalendar2 
     * @param theUPCalendar 
     */
    public static void viewJobsInParks(UrbanParkCalendar theUPCalendar)
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
        
        ArrayList<Job> jobs = new ArrayList<Job>(theUPCalendar.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to view.\n");
        }
        else
        {
            System.out.println("\nJob(s) in " + park.getParkName() + ":");
            for (int i = 0; i < jobs.size(); i++)
            {
                // Check to see if selected park corresponds to the
                // associated park for the job we are currently on
                if (jobs.get(i).getAssociatedPark() == park.getParkName())
                {
                    System.out.println((i + 1) + ") " + jobs.get(i));
                }
                
            }
        }
    }

    /**
     * 
     * @param myUser
     * @param uPCalendar
     * @author Lachezar
     */
    private static void viewVolunteers(UrbanParkCalendar theUPCalendar)
    {
        Park park = null;
        Job job = null;
        int theChoice;

        System.out.println(
                "Please select one of the parks you manage to view the jobs");

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
        System.out.println();
        // Get desired park
        park = parks.get(theChoice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(theUPCalendar.getJobList());
    
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
                // Check to see if selected park corresponds to the
                // associated park for the job we are currently on
                
                if (jobs.get(i).getAssociatedPark() == park.getParkName())
                {   
                    System.out.println((i + 1) + ") " + jobs.get(i));
                    System.out.println();
                }
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
            if (volunteers.size() == 0)
            {
                System.out.println("There are currently no volunteers signed up.\n");
            }
            else
            {
                System.out.println("\nVolunteer(s):");
                for (int i = 0; i < volunteers.size(); i++)
                {
                    System.out.println((i + 1) + ") " + volunteers.get(i));
                }
                System.out.println();
            }
        }
    }

    public static void displayLogin()
    {
        System.out.println("Welcome " + myUser.getFirstName() + " " +
                myUser.getLastName() + "!\n" + "Logged in as: Park Manager\n");
    }
}
