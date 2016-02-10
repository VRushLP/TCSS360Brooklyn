import java.util.Scanner;

public class ParkManagerDriver
{
    static String input;
    static int choice;

    public static void run(ParkManager theCurrentUser, Scanner in)
    {
        System.out.println("Welcome " + theCurrentUser.email);

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
            choice = Integer.parseInt(input.substring(0, 1));

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
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out
                            .println("Please enter one of the number options");
            }
        }
    }

    public static void submitNewJob()
    {
        System.out.println("Entered submitNewJob().");
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
