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

import model.AbstractUser;
import model.Job;
import model.Park;
import model.ParkManager;
import model.UrbanParkCalendar;
import model.UrbanParkStaffMember;
import model.User;
import model.Volunteer;

public class PrimaryDriver
{
    private static final String EXIT_STRING = "quit";

    // Input file name.
    private static final String[] filePaths = { "calendar.ser",
            "loginList.ser", "jobList.ser" };

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

        // fabricateInformation(); // TODO reset the .ser files
        deserializeData();

        Scanner in = new Scanner(System.in);

        // Prompt user to log in
        System.out.println(UPCalendar.getJobList().size());
        System.out.println(UPCalendar.getAllVolunteers().size());
        System.out.println("Please enter your email to log in: ");
        String userInput;

        System.out.println(loginList.keySet());
        
        do
        {
            userInput = in.nextLine();
            // Hardcoded versions for easier testing.
            // userName = "presidenttheo@whitehouse.gov"; //Park Manager
            // userName = "rmfarc@email.net"; //Volunteer
            // userName = "thedude@aol.com"; //Urban Park Staff Member

            User currentUser = login(userInput);

            if (currentUser instanceof ParkManager)
            {
                ParkManagerDriver
                        .run((ParkManager) currentUser, in, UPCalendar);
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
            else if (!userInput.equalsIgnoreCase(EXIT_STRING))
            {
                System.out
                        .println("Login failed. Please try again or type 'Quit' to terminate the program.");
            }
            else
            {
                System.out.println("Thank you for using Urban Parks.");
            }
        } while (!userInput.equalsIgnoreCase(EXIT_STRING));
        in.close();

        serializeData();
    }

    private static AbstractUser login(String theUserName)
    {
        return loginList.get(theUserName);
    }

    private static void fabricateInformation()
    {
        // Users
        ParkManager theDude = new ParkManager("thedude@aol.com", "Jeff",
                "Bridges");
        addUserInformation(theDude);
        Park wildWaves = new Park("Wild Waves Theme Park");
        Park dashPoint = new Park("Dash Point State Park");
        theDude.addParkToManager(wildWaves);
        theDude.addParkToManager(dashPoint);

        UrbanParkStaffMember obama = new UrbanParkStaffMember(
                "potus@whitehouse.gov", "Barack", "Obama");
        addUserInformation(obama);

        Volunteer robert = new Volunteer("rmfarc@uw.edu", "Robert", "Ferguson");
        addUserInformation(robert);

        // Dates so jobs are always in the future.
        Date tomorrow = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(1));
        Date dayAfterTomorrow = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(2));
        Date twoDaysAfterTomorrow = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(3));
        Date threeDaysAfterTomorrow = new Date(System.currentTimeMillis()
                + TimeUnit.DAYS.toMillis(4));

        // Actual jobs
        Job bigfoot = new Job(dashPoint, 10, 10, 10, tomorrow, tomorrow,
                "Bigfoot Hunting", "We'll get him this time.");
        Job yetis = new Job(dashPoint, 10, 10, 10, dayAfterTomorrow, dayAfterTomorrow,
                "Yeti Extermination", "They're everywhere!.");
        Job garbageCollect = new Job(wildWaves, 10, 10, 10, dayAfterTomorrow,
                twoDaysAfterTomorrow, "Garbage Collection",
                "Not as exciting, I know");
        Job sweep = new Job(wildWaves, 10, 10, 10, twoDaysAfterTomorrow,
                threeDaysAfterTomorrow, "Sweeping up the beach.",
                "Getting rid of the sand. It gets /everywhere/");

        try
        {
            UPCalendar.addJob(bigfoot);
            UPCalendar.addJob(yetis);
            UPCalendar.addJob(garbageCollect);
            UPCalendar.addJob(sweep);
        }
        catch (CalendarWeekFullException | CalendarFullException
                | JobTooLongException | JobTimeTravelException
                | JobToThePastException | JobToTheFutureException
                | DuplicateJobExistsException e)
        {
            System.err.println("Error in Fabricating Jobs.");
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
        serializeData();
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

            for (String s : allLogins)
            {
                User current = loginList.get(s);
                if (current instanceof Volunteer)
                {
                    UPCalendar.addVolunteer((Volunteer) current);
                }
                if (current instanceof ParkManager)
                {
                    ArrayList<Park> parks = new ArrayList<>(
                            ((ParkManager) current).getParks());

                    for (Park p : parks)
                    {
                        for (Job j : UPCalendar.getJobList())
                        {
                            if (p.getParkName().equalsIgnoreCase(
                                    j.getParkName())
                                    && !p.hasJob(j))
                            {
                                p.addJob(j);
                            }
                        }
                    }
                }
            }

            System.out.println("Objects read successfully.");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
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
