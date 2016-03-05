package driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import exception.CalendarFullException;
import exception.CalendarWeekFullException;
import exception.DuplicateJobExistsException;
import exception.JobTimeTravelException;
import exception.JobToTheFutureException;
import exception.JobToThePastException;
import exception.JobTooLongException;
import exception.JobWorksTooHardException;

import model.AbstractUser;
import model.Job;
import model.Park;
import model.ParkManager;
import model.UrbanParkCalendar;
import model.UrbanParkStaffMember;
import model.User;
import model.Volunteer;

/**
 * Primary Driver for the UrbanPark program.
 * 
 * @author Lachezar, Bethany, Robert
 */
public class PrimaryDriver
{
    private static final String EXIT_COMMAND = "quit";
    private static final String RESET_COMMAND = "reset";
    private static final String DEBUG_COMMAND = "debug";

    // Input file name.
    private static final String[] filePaths = { "calendar.ser", "loginList.ser",
            "jobList.ser" };

    // Data Structure to store everything in
    private static Map<String, AbstractUser> loginList;
    private static InputStream inputFileReader;
    private static OutputStream outputFileWriter;

    // A calendar field
    private static UrbanParkCalendar UPCalendar;

    private PrimaryDriver()
    {
    }

    /*
     * Main begins here!
     */
    public static void main(String[] args)
    {
        UPCalendar = new UrbanParkCalendar();
        loginList = new HashMap<>();

        deserializeData();

        Scanner in = new Scanner(System.in);

        // Prompt user to log in
        System.out.println("Please enter your email to log in: ");
        String userInput;

        do
        {
            userInput = in.nextLine();
            User currentUser = login(userInput);

            if (currentUser instanceof ParkManager)
            {
                ParkManagerDriver.run((ParkManager) currentUser, in,
                        UPCalendar);
                break;
            }
            else if (currentUser instanceof UrbanParkStaffMember)
            {
                UrbanParkStaffMemberDriver.run(
                        (UrbanParkStaffMember) currentUser, in, UPCalendar);
                break;
            }
            else if (currentUser instanceof Volunteer)
            {
                VolunteerDriver.run((Volunteer) currentUser, in, UPCalendar);
                break;
            }
            else if (userInput.equalsIgnoreCase(RESET_COMMAND))
            {
                resetInformation();
                System.out.println("All information reset.");
            }
            else if (userInput.equalsIgnoreCase(DEBUG_COMMAND))
            {
                System.out.println(UPCalendar.getJobList().size());
                System.out.println(UPCalendar.getAllVolunteers().size());
                System.out.println(loginList.keySet());
            }
            else if (!userInput.equalsIgnoreCase(EXIT_COMMAND))
            {
                System.out.println(
                        "Login failed. Please try again or type 'Quit' to terminate the program.");
            }
            else
            {
                System.out.println("Thank you for using Urban Parks.");
            }
        } while (!userInput.equalsIgnoreCase(EXIT_COMMAND));
        in.close();
        // Save data before quitting.
        serializeData();
    }

    /**
     * Retrieves given username from the login list
     * 
     * @return the abstract user
     */
    private static AbstractUser login(String theUserName)
    {
        return loginList.get(theUserName);
    }

    /**
     * Resets serialized data to default state.
     */
    private static void resetInformation()
    {
        System.out.println("Resetting data");
        UPCalendar = new UrbanParkCalendar();

        // Staff Member
        addUserInformation(new UrbanParkStaffMember("potus@whitehouse.gov",
                "Barack", "Obama"));

        // Park Managers
        ParkManager theDude = new ParkManager("thedude@aol.com", "Jeff",
                "Bridges");
        addUserInformation(theDude);
        Park wildWaves = new Park("Wild Waves Theme Park", theDude);
        Park dashPoint = new Park("Dash Point State Park", theDude);

        // TODO Add more ParkManagers here!

        // Volunteers
        addUserInformation(
                new Volunteer("rmfarc@uw.edu", "Robert", "Ferguson"));
        addUserInformation(
                new Volunteer("arc@gmail.com", "Ashley", "Ferguson"));
                // TODO Add more Volunteers here!

        // Dates so jobs are always in the future.
        Long dayInMillis = TimeUnit.DAYS.toMillis(1);

        Date tomorrow = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        Date dayAfterTomorrow = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2));
        Date twoDaysAfterTomorrow = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3));
        Date threeDaysAfterTomorrow = new Date(
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(4));

        // Actual jobs // TODO Make 25 more of these fuckers
        try
        {
            UPCalendar.addJob(new Job(dashPoint, 10, 10, 10, tomorrow, tomorrow,
                    "Bigfoot Hunting", "We'll get him this time."));
            UPCalendar.addJob(new Job(dashPoint, 10, 10, 10, dayAfterTomorrow,
                    dayAfterTomorrow, "Yeti Extermination",
                    "They're everywhere!."));
            UPCalendar.addJob(new Job(wildWaves, 10, 10, 10, dayAfterTomorrow,
                    twoDaysAfterTomorrow, "Garbage Collection",
                    "Not as exciting, I know"));
            UPCalendar
                    .addJob(new Job(wildWaves, 10, 10, 10, twoDaysAfterTomorrow,
                            threeDaysAfterTomorrow, "Sweeping up the beach",
                            "Getting rid of the sand. It gets /everywhere/"));
        }
        catch (CalendarWeekFullException | CalendarFullException
                | JobTooLongException | JobTimeTravelException
                | JobToThePastException | JobToTheFutureException
                | DuplicateJobExistsException | JobWorksTooHardException e)
        {
            System.err.println(
                    "Error in adding jobs to Calendar during reset process.");
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
        System.out.println("Writing data out to files.");
        serializeData();
        deserializeData();
    }

    private static void addUserInformation(AbstractUser theUser)
    {
        loginList.put(theUser.getEmail(), theUser);
        if (theUser instanceof Volunteer)
        {
            UPCalendar.addVolunteer((Volunteer) theUser);
        }
    }

    @SuppressWarnings("unchecked")
    private static void deserializeData()
    {
        System.out.println("Attempting to read data from files.");
        try
        {
            Object readObject = new Object();

            inputFileReader = new FileInputStream(filePaths[0]);
            ObjectInputStream objectReader = new ObjectInputStream(
                    inputFileReader);
            readObject = objectReader.readObject();
            if (readObject instanceof UrbanParkCalendar)
            {
                UPCalendar = (UrbanParkCalendar) readObject;
                readObject = new Object();
            }
            objectReader.close();
            inputFileReader.close();

            inputFileReader = new FileInputStream(filePaths[1]);
            objectReader = new ObjectInputStream(inputFileReader);
            readObject = objectReader.readObject();
            if (readObject instanceof HashMap)
            {
                loginList = (Map<String, AbstractUser>) readObject;
                readObject = new Object();
            }
            objectReader.close();
            inputFileReader.close();

            inputFileReader = new FileInputStream(filePaths[2]);
            objectReader = new ObjectInputStream(inputFileReader);
            readObject = objectReader.readObject();
            if (readObject instanceof Collection)
            {
                UPCalendar.overrideJobCollection((Collection<Job>) readObject);
            }
            objectReader.close();
            inputFileReader.close();

            Set<String> allLogins = loginList.keySet();
            ArrayList<ParkManager> managers = new ArrayList<>();

            for (String s : allLogins)
            {
                User current = loginList.get(s);
                if (current instanceof Volunteer)
                {
                    UPCalendar.addVolunteer((Volunteer) current);
                }
                else if (current instanceof ParkManager)
                {
                    managers.add((ParkManager) current);
                }
            }

            for (ParkManager pm : managers)
            {
                ArrayList<Park> parks = new ArrayList<>(pm.getParks());
                for (Park p : parks)
                {
                    UPCalendar.updatePark(p);
                }
            }
            System.out.println("Files read successfully.");
        }
        catch (ClassNotFoundException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void serializeData()
    {
        try
        {
            outputFileWriter = new FileOutputStream(filePaths[0]);
            ObjectOutputStream objectWriter = new ObjectOutputStream(
                    outputFileWriter);
            objectWriter.writeObject(UPCalendar);
            objectWriter.close();
            outputFileWriter.close();

            outputFileWriter = new FileOutputStream(filePaths[1]);
            objectWriter = new ObjectOutputStream(outputFileWriter);
            objectWriter.writeObject(loginList);
            objectWriter.close();
            outputFileWriter.close();

            outputFileWriter = new FileOutputStream(filePaths[2]);
            objectWriter = new ObjectOutputStream(outputFileWriter);
            objectWriter.writeObject(UPCalendar.getJobList());
            objectWriter.close();
            outputFileWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
