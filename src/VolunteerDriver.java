import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
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
    private static Map<String, Job> jobList;
    private static BufferedReader inputFileReader;

    public static void run(Volunteer theCurrentUser, Scanner in)
    {
        System.out.println("Welcome " + theCurrentUser.getEmail());

        while (choice != 3)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. View a summary of upcoming jobs");
            System.out.println("2. View jobs I am signed up for");
            System.out.println("3. Exit");

            input = in.nextLine();
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
                    viewJobs2(in);
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

    public static void viewSignedUpJobs()
    {
        System.out.println("Entered viewSignedUpJobs().");

    }
    
    // work in progress to fix view jobs method
    public static void viewJobs2(Scanner in) {
        viewJobs();
        input = in.nextLine();
        while (!input.equalsIgnoreCase("b")) { // options to go back
            // 1. get job at index, print all details of job
            System.out.println("All details of a job will be printed here");
            // 2. give option to volunteer for job
            // 3. if back entered, go back to menu screen
            System.out.println("Enter b to go back");
            input = in.nextLine();
        }
        
        
        // view job details for a specific job
    }

    /**
     * View a summary of all upcoming jobs.
     */
    public static void viewJobs()
    {
        // a temporary counter for the job, won't be used in the future
        int jobCounter = 0;
        System.out.println("If would like to view more details about a job, "
                + "please enter the number of the job. "
                + "Here is a summary of all upcoming jobs:\n");

        // WHERE WOULD THIS BE?
        // Get all jobs from csv file and put it in a map
        jobList = new HashMap<>();
        File inputFile = new File(inputPath);
        try
        {
            inputFileReader = new BufferedReader(new FileReader(inputFile));
            String nextLine;

            while ((nextLine = inputFileReader.readLine()) != null)
            {
                // System.out.println(nextLine);
                String[] splitLine = nextLine.split(",");
                if (splitLine.length != 5)
                    continue;
                String jobLocation = splitLine[0];
                String jobStartDate = splitLine[1];
                String jobEndDate = splitLine[2];
                String jobDescription = splitLine[3];
                // String jobCategory = splitLine[4];

                System.out.println(
                        jobCounter + ") " + "Job Location: " + jobLocation);
                System.out.println("Job Start Date: " + jobStartDate);
                System.out.println("Job End Date: " + jobEndDate);
                System.out.println("Job Description: " + jobDescription);

                // Print two new lines to separate visually each job
                System.out.println();
                System.out.println();

                // increment job counter
                jobCounter++;

                // jobList.put(jobLocation, Job b);
            }
            System.out.println(
                    "Enter a job number to view more details");
            System.out.println();
            // Call viewJobDetails() method
//            viewJobDetails();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputFileReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Shows the details of a job. Not fully working. At this point, it outputs
     * the first occurrence of a job in the job file.
     */
    public static void viewJobDetails()
    {
        // WHERE WOULD THIS BE?
        // Get all jobs from csv file and put it in a map

        File inputFile = new File(inputPath);
        try
        {
            inputFileReader = new BufferedReader(new FileReader(inputFile));
            String nextLine;

            while ((nextLine = inputFileReader.readLine()) != null)
            {
                // System.out.println(nextLine);
                String[] splitLine = nextLine.split(",");
                if (splitLine.length != 5)
                    continue;
                String jobLocation = splitLine[0];
                String jobStartDate = splitLine[1];
                String jobEndDate = splitLine[2];
                String jobDescription = splitLine[3];
                String jobCategory = splitLine[4];

                System.out.println("Job Details:");
                System.out.println("Job Location: " + jobLocation);
                System.out.println("Job Start Date: " + jobStartDate);
                System.out.println("Job End Date: " + jobEndDate);
                System.out.println("Job Description: " + jobDescription);
                System.out.println("Job Category: " + jobCategory);
                System.out.println();

                break;
                // jobList.put(jobLocation, Job b);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputFileReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
