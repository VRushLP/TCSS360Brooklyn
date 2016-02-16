import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class UrbanParkStaffMemberDriver
{
    static String input;
    static int choice;
    static String[] parsedInput;

    // Data Structure to store everything in
    private static UrbanParkStaffMember myUser;
    private static UrbanParkCalendar myUPCalendar;
    private static Scanner myInput;

    public static void run(UrbanParkStaffMember currentUser, Scanner in,
            UrbanParkCalendar theUPCalendar)
    {
        myUser = currentUser;
        myUPCalendar = theUPCalendar;
        myInput = in;

        /*
         * 10. As an Urban Parks staff member , I want to search volunteers by
         * last name. 11. As an Urban Parks staff member, I want to view a
         * summary of all upcoming jobs. 12. As an Urban Parks staff member, I
         * want to view details of a selected upcoming job
         */

        while (choice != 3)
        {
            System.out.println("Enter one of the options below:");
            System.out.println("1. Search volunteers by last name");
            System.out.println("2. View Jobs");
            System.out.println("3. Exit");

            input = myInput.nextLine();
            parsedInput = input.split(" ");

            try
            {
                choice = Integer.parseInt(
                        parsedInput[0].substring(0, parsedInput[0].length()));
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }

            switch (choice)
            {
                case 1:
                    searchVolunteers();
                    break;
                case 2:
                    viewJobs();
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

    private static void viewJobs()
    {
        ArrayList<Job> jobs = new ArrayList<Job>(myUPCalendar.getJobList());
        int i = 1;

        if (myUPCalendar.getJobList().isEmpty())
        {
            System.out.println("There are no Jobs currently in the system.\n");
        }
        else
        {
            for (Job job : jobs)
            {
                System.out.print(i++ + " " + job.getJobTitle() + " ");
                System.out.println(job.getJobDescription());
            }
        }

        do
        {
            System.out.println(
                    "Enter b to go back, or enter a job number to view in greater detail.");
            input = myInput.nextLine();
            if (!input.equalsIgnoreCase("b"))
            { // user wants to view a jobs details
                System.out.println((jobs.get(Integer.parseInt(input) - 1)));
            }
        } while (!input.equalsIgnoreCase("b"));
    }

    private static void searchVolunteers()
    {
        System.out.println("What name would you like to search for?");
        String toSearch = myInput.nextLine();
        ArrayList<Volunteer> toPrint = new ArrayList<>(
                scanVolunteers(toSearch));

        if (toPrint.isEmpty())
        {
            System.out.println(
                    "There are no volunteers with the last name " + toSearch);
        }
        else
        {
            System.out.print(
                    toPrint.size() + " results for last name " + toSearch);

            for (Volunteer v : toPrint)
            {
                System.out.println(v.toString());
            }
        }
    }

    static Collection<Volunteer> scanVolunteers(String lastName)
    {
        ArrayList<Volunteer> toPrint = new ArrayList<>();
        ArrayList<User> iterable = new ArrayList<>(myUPCalendar.getAllUsers());

        for (User currentUser : iterable)
        {
            if (currentUser instanceof Volunteer && ((Volunteer) currentUser)
                    .getLastName().equalsIgnoreCase(lastName))
            {
                toPrint.add((Volunteer) currentUser);
            }
        }

        return toPrint;
    }
}