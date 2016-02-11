import java.util.ArrayList;
import java.util.Scanner;

public class ParkManagerDriver
{
    static String input;
    static int choice;
    static String[] parsedInput;

    public static void run(ParkManager theCurrentUser, Scanner in)
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
                choice = Integer.parseInt(parsedInput[0].substring(0,
                        parsedInput[0].length()));
                // TODO catch NumberFormatExceptions here.
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            switch (choice)
            {
                case 1:
                    submitNewJob(theCurrentUser);
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
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out
                            .println("Please enter one of the number options");
            }
        }
    }

    // Added content in method
    public static void submitNewJob(ParkManager theCurrentUser)
    {
        System.out.println("Submit job");
        // Prompt user to enter data
        // Validate data (helper method)
        // Add job by calling theCurrentUser.
        
        // Get the parks that current park manager manages
        ArrayList<Park> parks = (ArrayList<Park>) theCurrentUser.getParks();
        
        // For testing purposes only
        Park park = new Park("Gasworks Park", theCurrentUser);
        ParkManager pm = new ParkManager("pmtest@test.com", "theFirstName",
                "theLastName", park);
        
        
        
        
        // Park manager will be presented with a list of parks he/she manages
        // Then, the PM will have to choose one of those
        // After selection, the PM is prompted to type in the job details
        // After job has been added under this PM, the Calendar needs to be updated also
        // to reflect precisely that
        System.out.println("Please select one of the parks you would like to"
                + "create a job for");
        System.out.println(park.toString());
        // Assume that user made a selection
        System.out.println("Job Location: ");
        System.out.println("Start Date: ");
        System.out.println("End Date: ");
        System.out.println("Description: ");
        System.out.println("Work Category: ");
        
        
    }

    public static void deleteJob()
    {
        System.out.println("Entered deleteJob().");
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
