import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class ParkManagerDriver
{
    static String input;
    static int choice;
    static String[] parsedInput;

    public static void run(ParkManager theCurrentUser, Scanner in,
            UrbanParkCalendar UPCalendar)
    {
        System.out.println("Welcome " + theCurrentUser.getEmail());

        while (choice != 5)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. Submit a new job");
            System.out.println("2. Delete a job");
            System.out.println("3. Edit the details of a job.");
            System.out
                    .println("4. View  summary of upcoming jobs in my parks.");
            System.out.println("5. Exit");

            input = in.nextLine();
            parsedInput = input.split(" ");

            try
            {
                choice = Integer.parseInt(
                        parsedInput[0].substring(0, parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here.
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            switch (choice)
            {
                case 1:
                    submitNewJob(theCurrentUser, UPCalendar);
                    break;
                case 2:
                    deleteJob(theCurrentUser, UPCalendar);
                    break;
                case 3:
                    editJob();
                    break;
                case 4:
                    viewJobsInParks();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out
                            .println("Please enter one of the number options");
            }
        }
    }

    public static void submitNewJob(ParkManager theCurrentUser,
            UrbanParkCalendar uPCalendar)
    {
        Scanner in = new Scanner(System.in);
        final String jobTitle;
        final String jobDescription;
        final String startDate;
        final String endDate;
        final int maxVolunteers;
        System.out.println("Submit New Job");

        System.out.println(
                "Please select one of the parks you manage to add a job for that park");
        Collection<Park> parks = new ArrayList<Park>();
        Iterator itr = parks.iterator();
        Park park = null;
        while (itr.hasNext())
        {

            park = (Park) itr.next();
            System.out.println(park);

        }

        System.out.print("Enter number:");

        input = in.nextLine();
        parsedInput = input.split(" ");

        try
        {
            choice = Integer.parseInt(
                    parsedInput[0].substring(0, parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.print("Please enter job title: ");
        jobTitle = in.nextLine();
        // System.out.println();
        System.out.print("Job description: ");
        jobDescription = in.nextLine();
        System.out.print("Start Date (MM/DD/YYYY): ");
        startDate = in.nextLine();
        System.out.print("End Date (MM/DD/YYYY): ");
        endDate = in.nextLine();
        System.out.print("Maximum number of volunteers: ");
        maxVolunteers = in.nextInt();

        // Call create job method in Park Manager class
        // and pass in the park that the PM wants to add a job to
        theCurrentUser.createJob(uPCalendar, choice, park, maxVolunteers,
                startDate, endDate, jobTitle, jobDescription);

        in.close();
    }

    public static void deleteJob(ParkManager theCurrentUser,
            UrbanParkCalendar uPCalendar)
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Delete a Job");

        System.out.println(
                "Please select one of the parks that you manage to delete a job from that park");

        // Commented out lines should not cause a problem once the
        // theCurrentUser has actually parks
        // to manage

        // theCurrentUser.viewAllJobs(uPCalendar);

        int selectedJob = 0;

        // Throws a null pointer exception at the moment
        // ArrayList<Park> prks = (ArrayList<Park>) theCurrentUser.getParks();
        //
        // Park park = null;
        // Iterator itr = prks.iterator();

        // while (itr.hasNext())
        // {
        //
        // park = (Park) itr.next();
        // System.out.println(park);
        //
        // }

        System.out.print("Enter park number:");

        input = in.nextLine();
        parsedInput = input.split(" ");

        try
        {
            choice = Integer.parseInt(
                    parsedInput[0].substring(0, parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Display jobs from selected park
        // int count = 0; // not a good thing to do
        // while (itr.hasNext())
        // {
        // if (count == choice)
        // {
        // park = (Park) itr.next();
        // }
        //
        //
        // }
        System.out.println("Jobs at park number " + choice);

        // Get all upcoming jobs for the selected park
        // ArrayList<Job> jobs = (ArrayList<Job>) park.getJobList();
        // Job job = null;
        // Iterator itr2 = jobs.iterator();
        // while (itr2.hasNext())
        // {
        //
        // job = (Job) itr2.next();
        // System.out.println(job.toString());
        //
        // }

        System.out.print("Enter job number to delete:");

        input = in.nextLine();
        parsedInput = input.split(" ");

        try
        {
            selectedJob = Integer.parseInt(
                    parsedInput[0].substring(0, parsedInput[0].length()));
            // TODO catch NumberFormatExceptions here. Not needed?
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // call appropriate method in Park Manager class
        // theCurrentUser.deleteJob(uPCalendar, selectedJob, park);

        in.close();
    }

    public static void editJob()
    {
        System.out.println("Entered editJob().");
    }

    public static void viewJobsInParks()
    {
        System.out.println("Entered viewJobsInParks().");
    }
}
