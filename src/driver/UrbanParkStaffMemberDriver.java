package driver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import model.Job;
import model.UrbanParkCalendar;
import model.UrbanParkStaffMember;
import model.User;
import model.Volunteer;

public class UrbanParkStaffMemberDriver extends SharedUserDriverFunctions
{
    private static final int MAIN_MENU_OPTIONS = 3;

    static int choice;

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
            displayLogin();

            System.out.println("Enter one of the options below:");
            System.out.println("1. Search volunteers by last name");
            System.out.println("2. View Jobs");
            System.out.println("3. Exit");

            choice = getIntegerInput(myInput, MAIN_MENU_OPTIONS);

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
            }
        }

    }

    private static void viewJobs()
    {
        ArrayList<Job> jobs = new ArrayList<Job>(myUPCalendar.getJobList());

        if (!jobs.isEmpty())
        {
            do
            {
                printAllUpcomingJobs(myUPCalendar);
                System.out.println(
                        "Enter 0 to go back, or enter a number to view that job in greater detail.");
                choice = getIntegerInput(myInput, 0, jobs.size());
                if (choice != 0)
                { // user wants to view a jobs details
                    System.out.println((jobs.get(choice - 1)));
                }
            } while (choice != 0);
        }
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
            System.out.println(
                    toPrint.size() + " result(s) for last name " + toSearch);
            printVolunteers(toPrint);
        }
    }

    static Collection<Volunteer> scanVolunteers(String lastName)
    {
        ArrayList<Volunteer> toPrint = new ArrayList<>();
        ArrayList<User> toScan = new ArrayList<>(
                myUPCalendar.getAllVolunteers());

        for (User currentUser : toScan)
        {
            if (currentUser instanceof Volunteer && ((Volunteer) currentUser)
                    .getLastName().equalsIgnoreCase(lastName))
            {
                toPrint.add((Volunteer) currentUser);
            }
        }
        return toPrint;
    }

    /**
     * @author Robert
     */
    public static void displayLogin()
    {
        System.out.println("\nWelcome " + myUser.getFirstName() + " "
                + myUser.getLastName() + "!\n" + "Logged in as: "
                + myUser.getEmail() + " (Park Manager)\n");
    }
}