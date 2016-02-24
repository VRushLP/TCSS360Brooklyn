package driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.Job;
import model.Park;
import model.ParkManager;
import model.UrbanParkCalendar;
import model.Volunteer;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
import exception.DuplicateJobExistsException;
import exception.JobTimeTravelException;
import exception.JobToTheFutureException;
import exception.JobToThePastException;
import exception.JobTooLongException;

public class ParkManagerDriver extends SharedUserDriverFunctions
{
    private static int MAIN_MENU_CHOICES = 6;
    private static int EDIT_MENU_CHOICES = 5;
    private static int choice;

    // Data Structure to store everything in
    private static ParkManager myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Scanner myInput;

    /**
     * @param theUser
     *            The Park Manager currently running the driver.
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

        while (choice != MAIN_MENU_CHOICES)
        {
            // Display appropriate welcoming message first
            displayLogin();
            System.out.println("Enter one of the options below:");
            System.out.println("1. Submit a new job");
            System.out.println("2. Delete a job");
            System.out.println("3. Edit the details of a job.");
            System.out.println("4. View summary of upcoming jobs in my parks.");
            System.out
                    .println("5. View the volunteers for a job in a parks I manage");
            System.out.println("6. Exit.");

            choice = getIntegerInput(myInput, MAIN_MENU_CHOICES);
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
            }
        }
    }

    /**
     * @author Lachezar, Robert
     */
    public static void submitNewJob()
    {
        myUPCalendar.updateCalendar();
        if (myUPCalendar.getJobList().size() == UrbanParkCalendar.MAX_JOBS)
        {
            System.out.println("Error: A new job cannot be added."
                    + " The maximum number of pending jobs has been reached ("
                    + UrbanParkCalendar.MAX_JOBS + ").");
        }
        else
        {
            System.out
                    .println("Please select one of the parks you manage to add a job for that park");

            ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());
            printParks(parks);

            System.out.print("Enter park number:");

            choice = getIntegerInput(myInput, parks.size());
            Park park = parks.get(choice - 1);

            System.out.print("Please enter job title: ");
            String jobTitle = myInput.nextLine();
            // System.out.println();
            System.out.print("Job description: ");
            String jobDescription = myInput.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            Date startDate = getDateInput(myInput);

            System.out.print("End Date (MM/DD/YYYY): ");
            Date endDate = getDateInput(myInput);

            System.out.print("Maximum number of volunteers (Up to 30): ");
            int maxVolunteers = myInput.nextInt();
            myInput.nextLine();

            if (maxVolunteers > Job.MAX_VOLUNTEER_NUM)
            {
                System.out.println("A job is allowed a maximum of "
                        + Job.MAX_VOLUNTEER_NUM + " volunteers.");
                maxVolunteers = Job.MAX_VOLUNTEER_NUM;
            }

            Job jobToAdd = new Job(park, maxVolunteers, startDate, endDate,
                    jobTitle, jobDescription);

            // Attempt to schedule job.
            attemptToAddJob(park, jobToAdd);
        }
    }

    /**
     * @author Lachezar, Robert
     */
    public static void deleteJob()
    {
        System.out
                .println("Please select one of the parks you manage to delete a job from that park");
        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

        printParks(parks);
        System.out.print("Enter park number:");
        choice = getIntegerInput(myInput, parks.size());

        // Get desired park
        Park park = parks.get(choice - 1);
        // Get list of jobs that are in that park.
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to delete.");
        }
        else
        {
            System.out
                    .println("\nPlease enter the number of the job you would like to delete\n");
            printJobs(jobs);
            System.out.print("Enter job number:");

            choice = getIntegerInput(myInput, jobs.size());

            // Get desired job to delete
            Job theJob = jobs.get(choice - 1);

            // Delete job from Calendar
            myUPCalendar.removeJob(theJob);
            // Then delete it from the park
            park.removeJob(theJob);

            // Display message that the request was successful
            System.out.println("Job was deleted successfully.\n");
        }
    }

    /**
     * @author Lachezar, Robert
     */
    public static void editJob()
    {
        System.out.println("Please select one of the parks you manage:");

        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());
        printParks(parks);

        System.out.print("Enter park number:");
        choice = getIntegerInput(myInput, parks.size());

        // Get desired park
        Park park = parks.get(choice - 1);
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to edit in that park.");
        }
        else
        {
            System.out
                    .println("Please enter the number of the job you would like to edit");
            System.out.println();
            printJobs(jobs);

            System.out.print("Enter job number:");
            choice = getIntegerInput(myInput, jobs.size());

            // Get desired job to delete
            Job jobToEdit = jobs.get(choice - 1);

            attemptToEditJob(park, jobToEdit);
        }
    }

    /**
     * @author Lachezar, Robert
     */
    public static void viewJobsInParks()
    {
        System.out.println("Please select one of the parks you manage to view "
                + "a summary of all upcoming jobs in that park");

        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());

        printParks(parks);
        System.out.print("Enter park number:");

        // Get desired park
        choice = getIntegerInput(myInput, parks.size());
        Park park = parks.get(choice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());
        System.out.println("Jobs in " + park.getParkName());
        printJobs(jobs);
    }

    /**
     * @author Lachezar, Robert
     */
    private static void viewVolunteers()
    {
        System.out
                .println("Please select one of the parks you manage to view the jobs");

        // Get desired park
        ArrayList<Park> parks = new ArrayList<Park>(myUser.getParks());
        printParks(parks);
        System.out.print("Enter park number:");
        choice = getIntegerInput(myInput, parks.size());
        Park park = parks.get(choice - 1);
        // Get jobs from that park
        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to view.");
        }
        else
        {
            printJobs(jobs);
            System.out.print("Enter job number:");
            choice = getIntegerInput(myInput, jobs.size());

            // Get desired job
            Job job = jobs.get(choice - 1);

            ArrayList<Volunteer> volunteers = new ArrayList<Volunteer>(
                    job.getVolunteers());

            // Print volunteer information from collection of volunteers for
            // selected job
            printVolunteers(volunteers);
        }
    }

    // TODO Not sure this saves jobs properly in the park.
    /**
     * Prompts the user for job edits until they are done.
     */
    private static void attemptToEditJob(Park park, Job jobToEdit)
    {
        do
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. Edit job title");
            System.out.println("2. Edit job description");
            System.out.println("3. Edit job date(s)");
            System.out.println("4. Edit maximum number of volunteers");
            System.out.println("5. Finish");
            choice = getIntegerInput(myInput, EDIT_MENU_CHOICES);

            switch (choice)
            {
                case 1:
                    System.out.print("Please enter job title: ");
                    String newJobTitle = myInput.nextLine();
                    try
                    {
                        jobToEdit = myUPCalendar.editJobTitle(park, jobToEdit,
                                newJobTitle);
                        // Display message to user to indicate that the edit
                        // was successful
                        System.out
                                .println("Job title was modified successfully.\n");
                    }
                    catch (DuplicateJobExistsException e)
                    {
                        System.out
                                .println("Your edit would cause a duplicate job to exist!");
                    }

                    break;
                case 2:
                    System.out.print("Job Description: ");
                    String newJobDescription = myInput.nextLine();
                    try
                    {
                        jobToEdit = myUPCalendar.editJobDesc(park, jobToEdit,
                                newJobDescription);
                        // Display message to user to indicate that the edit
                        // was successful
                        System.out
                                .println("Job description was modified successfully.\n");
                    }
                    catch (DuplicateJobExistsException e)
                    {
                        System.out
                                .println("Your edit would cause a duplicate job to exist!");
                    }

                    break;
                case 3:
                    System.out.print("New start date (MM/DD/YYYY): ");
                    Date startDate = getDateInput(myInput);
                    System.out.print("New end date (MM/DD/YYYY): ");
                    Date endDate = getDateInput(myInput);
                    try
                    {
                        jobToEdit = myUPCalendar.editJobDates(park, jobToEdit,
                                startDate, endDate);
                        // Display message to user to indicate that the edit
                        // was successful
                        System.out
                                .println("Job start date was modified successfully.\n");
                    }
                    catch (JobToThePastException e)
                    {
                        System.out
                                .println("Your edit would cause the job to have already occured!");
                    }
                    catch (JobToTheFutureException e)
                    {
                        System.out
                                .println("Your edit puts the job too far out in the future.");
                    }
                    catch (JobTooLongException e)
                    {
                        System.out
                                .println("Your edit causes the job to last too long.");
                    }
                    catch (CalendarWeekFullException e)
                    {
                        System.out
                                .println("Your edit causes an overflow in that calendar week.");
                    }
                    catch (JobTimeTravelException e)
                    {
                        System.out
                                .println("Your edit would cause your job to end before it began!");
                    }

                    break;
                case 4:
                    System.out
                            .println("Please enter the a new maximum number of volunteers (Up to 30):");

                    int maxVolunteers = getIntegerInput(myInput,
                            Job.MAX_VOLUNTEER_NUM);

                    jobToEdit = myUPCalendar.editMaxVol(park, jobToEdit,
                            maxVolunteers);
                    System.out
                            .println("Maximum number of volunteers for selected job "
                                    + "was changed to "
                                    + jobToEdit.getMaxVolunteers() + ".\n");
                    break;
                case 5:
                    System.out.println("Finished editing job");
                    break;
                default:
                    System.out
                            .println("Please enter one of the numbered options");
                    break;
            }
        } while (choice != EDIT_MENU_CHOICES);

    }

    /**
     * @author Robert
     */
    private static void attemptToAddJob(Park park, Job jobToAdd)
    {
        try
        {
            myUPCalendar.addJob(jobToAdd);
            park.addJob(jobToAdd);
            // Display message to indicate that the add was successful
            System.out.println("Job was added successfully!\n");

        }
        catch (CalendarWeekFullException e)
        {
            System.out
                    .println("The week you're trying to add a job to is too full.");
        }
        catch (CalendarFullException e)
        {
            System.out.println("The Calendar is too full to add your job");
        }
        catch (JobTooLongException e)
        {
            System.out.println("The job you entered is too long.");
        }
        catch (JobTimeTravelException e)
        {
            System.out
                    .println("A job's end date must occur after it's start date.");
        }
        catch (JobToThePastException e)
        {
            System.out.println("The date of your job has already occured.");
        }
        catch (JobToTheFutureException e)
        {
            System.out.println("The date you specified is too far out.");
        }
        catch (DuplicateJobExistsException e)
        {
            System.out
                    .println("Your job appears to be a duplicate of a job that already exists.");
        }
    }

    /**
     * @author Robert
     */
    public static void displayLogin()
    {
        System.out.println("Welcome " + myUser.getFirstName() + " "
                + myUser.getLastName() + "!\n" + "Logged in as: "
                + myUser.getEmail() + " (Park Manager)\n");
    }
}
