import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ParkManagerDriver
{
    static String input;
    static int choice;
    static String[] parsedInput;

    /**
     * 
     * @param theCurrentUser
     * @param in
     * @param UPCalendar
     * @author Lachezar, Robert
     */
    public static void run(ParkManager theCurrentUser, Scanner in,
            UrbanParkCalendar UPCalendar)
    {
        System.out.println("Welcome " + theCurrentUser.getEmail());

        // FOR TESTING PURPOSES
        Park p1 = new Park("Gasworks Park", theCurrentUser);
        Park p2 = new Park("Ravenna Park", theCurrentUser);
        theCurrentUser.addParkToManager(p1);
        theCurrentUser.addParkToManager(p2);
        // TODO

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

            choice = in.nextInt();
            in.nextLine();

            switch (choice)
            {
                case 1:
                    submitNewJob(theCurrentUser, UPCalendar, in);
                    break;
                case 2:
                    deleteJob(theCurrentUser, UPCalendar, in);
                    break;
                case 3:
                    editJob(theCurrentUser, UPCalendar, in);
                    break;
                case 4:
                    viewJobsInParks(theCurrentUser, UPCalendar, in);
                    break;
                case 5:
                    viewVolunteers(theCurrentUser, UPCalendar, in);
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println(
                            "Please enter one of the numbered options");
            }
        }
    }

    /**
     * @param theCurrentUser
     * @param theUPCalendar
     * @author Lachezar
     */
    public static void submitNewJob(ParkManager theCurrentUser,
            UrbanParkCalendar theUPCalendar, Scanner in)
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
        if (theUPCalendar.getJobList().size() == UrbanParkCalendar.MAX_JOBS)
        {
            System.out.println("Error: A job cannot be added."
                    + " The maximum number of pending jobs has been reached ("
                    + UrbanParkCalendar.MAX_JOBS + ").");
        }
        else
        {
            System.out.println(
                    "Please select one of the parks you manage to add a job for that park");

            ArrayList<Park> parks = new ArrayList<Park>(
                    theCurrentUser.getParks());

            System.out.println();
            for (int i = 0; i < parks.size(); i++)
            {
                System.out.println((i + 1) + ") " + parks.get(i));
                System.out.println();
            }

            System.out.print("Enter park number:");

            theChoice = in.nextInt();
            in.nextLine();
            park = parks.get(theChoice - 1);

            System.out.print("Please enter job title: ");
            jobTitle = in.nextLine();
            // System.out.println();
            System.out.print("Job description: ");
            jobDescription = in.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            startDate = in.nextLine();

            // Validate entered start date (business rule #5)
            if (!validateStartDate(startDate))
            {
                System.out.println("Error: Start date cannot be in the past or"
                        + " more than 3 months from today's date. ");
                System.out.print("Enter new Start Date (MM/DD/YYYY): ");
                startDate = in.nextLine();
            }

            System.out.print("End Date (MM/DD/YYYY): ");
            endDate = in.nextLine();

            // Validate entered start and end dates (business rule #4)

            // TO DO: Keep examining user input even if
            // an error message has already been displayed
            if (!validateJobDuration(startDate, endDate))
            {
                System.out.println("Error: A job may not be scheduled that "
                        + "lasts more than two days.");
                System.out.print("Enter new end date (MM/DD/YYYY): ");
                endDate = in.nextLine();
            }

            System.out.print("Maximum number of volunteers: ");
            maxVolunteers = in.nextInt();
            in.nextLine();

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
            // Update calendar
            theUPCalendar.addJob(job);
        }
    }

    /**
     * 
     * @param theCurrentUser
     * @param theUPCalendar
     * @author Lachezar
     */
    public static void deleteJob(ParkManager theCurrentUser,
            UrbanParkCalendar theUPCalendar, Scanner in)
    {
        int theChoice;
        Park park = null;

        // Desired job to delete
        Job theJob = null;

        System.out.println(
                "Please select one of the parks you manage to delete a job from that park");

        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
            System.out.println();
        }

        System.out.print("Enter park number:");

        theChoice = in.nextInt();
        in.nextLine();

        // Get desired park
        park = parks.get(theChoice - 1);

        ArrayList<Job> jobs = new ArrayList<Job>(park.getJobList());

        if (jobs.size() == 0)
        {
            System.out.println("There are no jobs to delete.");
            System.out.println();
        }
        else
        {
            System.out.println();
            System.out.println(
                    "Please enter the number of the job you would like to delete");
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
            System.out.print("Enter job number:");

            theChoice = in.nextInt();
            in.nextLine();

            // Get desired job to delete
            theJob = jobs.get(theChoice - 1);

            // Delete job
            theCurrentUser.deleteJob(theUPCalendar, theJob, park);
        }
    }

    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void editJob(ParkManager theCurrentUser,
            UrbanParkCalendar uPCalendar, Scanner in)
    {
        final String jobTitle;
        final String jobDescription;
        String startDate;
        String endDate;
        int maxVolunteers;
        Park park = null;

        int theChoice;

        // Desired job to delete
        Job jobToEdit = null;

        System.out.println("Edit a Job");

        System.out.println(
                "Please select one of the parks you manage to edit a job from that park");

        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
        }

        System.out.print("Enter park number:");

        theChoice = in.nextInt();
        in.nextLine();

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
            System.out.println();
            System.out.println(
                    "Please enter the number of the job you would like to edit");
            System.out.println();
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
            }
            System.out.print("Enter job number:");

            theChoice = in.nextInt();
            in.nextLine();

            // Get desired job to delete
            jobToEdit = jobs.get(theChoice - 1);

            System.out.print("Please enter job title: ");
            jobTitle = in.nextLine();
            // System.out.println();
            System.out.print("Job description: ");
            jobDescription = in.nextLine();
            System.out.print("Start Date (MM/DD/YYYY): ");
            startDate = in.nextLine();

            // Validate entered start date (business rule #5)
            if (!validateStartDate(startDate))
            {
                System.out.println("Error: Start date cannot be in the past or"
                        + " more than 3 months from today's date. ");
                System.out.print("Enter new Start Date (MM/DD/YYYY): ");
                startDate = in.nextLine();
            }

            System.out.print("End Date (MM/DD/YYYY): ");
            endDate = in.nextLine();

            // Validate entered start and end dates (business rule #4)
            if (!validateJobDuration(startDate, endDate))
            {
                System.out.println("Error: A job may not be scheduled that "
                        + "lasts more than two days.");
                System.out.print("Enter new End Date (MM/DD/YYYY): ");
                endDate = in.nextLine();
            }

            System.out
                    .println("Please Enter the maximum number of volunteers:");

            maxVolunteers = in.nextInt();
            in.nextLine();

            theCurrentUser.editJob(uPCalendar, jobToEdit, park, maxVolunteers,
                    startDate, endDate, jobTitle, jobDescription);
        }
    }

    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    public static void viewJobsInParks(ParkManager theCurrentUser,
            UrbanParkCalendar uPCalendar, Scanner in)
    {
        Park park = null;
        int theChoice;

        System.out.println("View a summary of all jobs in my parks");
        System.out.println();
        System.out.println("Please select one of the parks you manage to view "
                + "a summary of all upcoming jobs in that park");

        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
        }
        System.out.print("Enter park number:");

        theChoice = in.nextInt();
        in.nextLine();

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
            for (int i = 0; i < jobs.size(); i++)
            {
                System.out.println((i + 1) + ") " + jobs.get(i));
                System.out.println();
            }
        }
    }

    /**
     * 
     * @param theCurrentUser
     * @param uPCalendar
     * @author Lachezar
     */
    private static void viewVolunteers(ParkManager theCurrentUser,
            UrbanParkCalendar uPCalendar, Scanner in)
    {
        Park park = null;
        Job job = null;

        System.out.println("View volunteers for a job at one of my parks");
        System.out.println();
        System.out.println("Please select one of the parks you manage to view "
                + "the jobs");

        ArrayList<Park> parks = new ArrayList<Park>(theCurrentUser.getParks());

        System.out.println();
        for (int i = 0; i < parks.size(); i++)
        {
            System.out.println((i + 1) + ") " + parks.get(i));
            System.out.println();
        }
        System.out.print("Enter park number:");

        input = in.nextLine();
        parsedInput = input.split(" ");

        try
        {
            choice = Integer.parseInt(
                    parsedInput[0].substring(0, parsedInput[0].length()));
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter one of the numbered options.");
        }
        // Get desired park
        park = parks.get(choice - 1);

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

            input = in.nextLine();
            parsedInput = input.split(" ");

            try
            {
                choice = Integer.parseInt(
                        parsedInput[0].substring(0, parsedInput[0].length()));
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter one of the numbered options.");
            }

            // Get desired job
            job = jobs.get(choice - 1);

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

    /**
     * This helper method enforces business rule #5.
     * 
     * @author Lachezar
     */
    private static boolean validateStartDate(String dateStart)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        float diffDays;
        Date startDate = null;
        try
        {
            startDate = format.parse(dateStart);

        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        // Get system's current date
        Calendar calobj = Calendar.getInstance();

        // Set current time to 00:00:00 so that we can compare with entered
        // start date
        calobj.set(Calendar.HOUR_OF_DAY, 0);
        calobj.set(Calendar.MINUTE, 0);
        calobj.set(Calendar.SECOND, 0);

        Date currentDate = calobj.getTime();

        // Get the difference between start date and current date in
        // milliseconds
        float diff = startDate.getTime() - currentDate.getTime();

        // Difference in days
        diffDays = diff / (24 * 60 * 60 * 1000);
        // System.out.println("Diff in days: " + diffDays);
        if (diffDays < 0 || diffDays > 90)
        {
            return false;
        }
        return true;

    }

    /**
     * This helper method enforces business rule #4.
     * 
     * @author Lachezar
     */
    private static boolean validateJobDuration(String dateStart, String dateEnd)
    {
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        try
        {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateEnd);
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        // Get the difference in milliseconds
        float diff = endDate.getTime() - startDate.getTime();

        // Difference in days
        float diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays > 2)
        {
            return false;
        }
        return true;
    }

}
